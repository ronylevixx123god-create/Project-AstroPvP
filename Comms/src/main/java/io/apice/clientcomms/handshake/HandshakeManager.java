package io.apice.clientcomms.handshake;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class HandshakeManager {
    private static final AtomicInteger executor_counter = new AtomicInteger();
    private static final ThreadPoolExecutor executors = new ThreadPoolExecutor(1, 2, 1L, TimeUnit.MINUTES, new LinkedBlockingDeque<Runnable>(), r -> {
        Thread t = new Thread(r, "Handshake Manager #" + executor_counter.incrementAndGet());
        t.setPriority(1);
        t.setDaemon(true);
        return t;
    });
    private static final ConcurrentHashMap<UUID, HandshakeContainer> cache = new ConcurrentHashMap(2);
    public static final String HMAC_REQUEST_URL = "http://cosmicclient.joemelsha.com/auth.php";

    public static void main(String[] args) throws Throwable {
        HandshakeContainer hs = HandshakeManager.fetch("melsha", UUID.fromString("3b8af631-183a-4912-a781-9389bed33e87"), "abcdef");
        System.out.println("result = " + hs.toString());
        byte[] key = Hex.decodeHex("32114520F5E90BE185F3A1BF540BD1EE9965700C55A6DF8C50E441AB6280F40C250BEBA4F059D0C09B5731F8061FC1B47376D208D63842E09271F831C126A49D".toCharArray());
        SecretKeySpec keySpec = HandshakeContainer.getKeySpec(key);
        Mac mac = HandshakeContainer.getMACInstance();
        mac.init(keySpec);
        System.out.println("client_hs_encoded = " + hs.encodeWithoutHMAC());
        System.out.println("checkSanity_client()");
        hs.checkSanity_client();
        System.out.println("checkSanity_server(true)");
        hs.checkSanity_server(true);
        System.out.println("isValid() = " + hs.isValid());
        System.out.println("computeHMAC = " + hs.computeHMAC(mac));
        System.out.println("checkHMAC = " + hs.checkHMAC(mac));
    }

    public static void prefetch(String name, UUID uuid_object, String mojang_token) {
        executors.execute(() -> {
            try {
                HandshakeManager.get(name, uuid_object, mojang_token, 5L, TimeUnit.SECONDS);
            }
            catch (Exception x) {
                x.printStackTrace();
            }
        });
    }

    public static HandshakeContainer get(String name, UUID uuid_object, String mojang_token, long dur, TimeUnit dur_unit) throws Exception {
        String uuid = uuid_object.toString().replace("-", "").toLowerCase();
        HandshakeContainer.MojangToken mt = new HandshakeContainer.MojangToken();
        mt.uuid = uuid;
        mt.mojang_token = mojang_token;
        mt.checkSanity();
        String mojang_token_hash = mt.computeHash(HandshakeContainer.MojangToken.getHashAlgoInstance());
        HandshakeContainer result = cache.compute(uuid_object, (k, v) -> {
            if (v != null) {
                if (v.name.equals(name) && v.uuid.equals(uuid) && v.mojang_token_hash.equals(mojang_token_hash) && v.isValid()) {
                    if (v.isValidFor(dur, dur_unit)) {
                        return v;
                    }
                } else {
                    v = null;
                }
            }
            try {
                v = HandshakeManager.fetch(name, uuid, mojang_token_hash);
            }
            catch (Exception x) {
                x.printStackTrace();
            }
            return v;
        });
        if (result != null) {
            result = result.clone();
        }
        return result;
    }

    public static HandshakeContainer fetch(String name, UUID uuid_object, String mojang_token) throws Exception {
        String uuid = uuid_object.toString().replace("-", "").toLowerCase();
        HandshakeContainer.MojangToken mt = new HandshakeContainer.MojangToken();
        mt.uuid = uuid;
        mt.mojang_token = mojang_token;
        mt.checkSanity();
        String mojang_token_hash = mt.computeHash(HandshakeContainer.MojangToken.getHashAlgoInstance());
        return HandshakeManager.fetch(name, uuid, mojang_token_hash);
    }

    public static HandshakeContainer fetch(String name, String uuid, String mojang_token_hash) throws Exception {
        SecureRandom sr = new SecureRandom();
        HandshakeContainer client_hs = new HandshakeContainer();
        client_hs.name = name;
        client_hs.uuid = uuid;
        client_hs.mojang_token_hash = mojang_token_hash;
        client_hs.version = "00000000000000000000000000000000";
        client_hs.client_random = sr.nextLong();
        long create_time_nanos = System.nanoTime();
        long client_Time = System.currentTimeMillis();
        client_hs.client_time = client_Time;
        client_hs.checkSanity_client();
        String client_hs_encoded = client_hs.encode();
        String server_hs_encoded = HandshakeManager.request(client_hs_encoded);
        HandshakeContainer server_hs = HandshakeContainer.gsonMin.fromJson(server_hs_encoded, HandshakeContainer.class);
        if (!server_hs.equals_client(client_hs)) {
            throw new IllegalStateException("Client fields handshake mismatch!");
        }
        server_hs.checkSanity_client();
        server_hs.checkSanity_server(true);
        server_hs.create_time_nanos = create_time_nanos;
        if (!server_hs.isValid()) {
            throw new IllegalStateException("Returned handshake is expired!");
        }
        return server_hs;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static String request(String client_hs_encoded) throws Exception {
        byte[] client_hs_encoded_data = client_hs_encoded.getBytes(StandardCharsets.UTF_8);
        URL url = new URL("http://cosmicclient.joemelsha.com/auth.php?r=" + Math.random());
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        try {
            byte[] result_data;
            InputStream in;
            String[] parts;
            int timeout = 60000;
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(timeout);
            connection.setInstanceFollowRedirects(false);
            connection.setAllowUserInteraction(false);
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Host", url.getHost());
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Cache-Control", "max-age=0");
            connection.setRequestProperty("Connection", "close");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Accept-Language", "en-US,en;q=0.8");
            connection.setRequestProperty("Content-Length", String.valueOf(client_hs_encoded_data.length));
            connection.connect();
            try (OutputStream out = connection.getOutputStream();){
                out.write(client_hs_encoded_data);
            }
            Charset charset = StandardCharsets.UTF_8;
            String contentType_decoded = null;
            String contentType = connection.getContentType();
            if (contentType != null && (parts = contentType.trim().split("\\;")).length > 0) {
                String p;
                contentType_decoded = p = parts[0].trim();
                if (parts.length > 1) {
                    String charsetStr = parts[1];
                    if ((charsetStr = charsetStr.trim()).toLowerCase().startsWith("charset") && (charsetStr = charsetStr.substring("charset".length()).trim()).startsWith("=")) {
                        charsetStr = charsetStr.substring(1).trim();
                        charset = Charset.forName(charsetStr);
                    }
                }
            }
            IOException error = null;
            try {
                in = connection.getInputStream();
            }
            catch (IOException x) {
                error = x;
                in = connection.getErrorStream();
            }
            try {
                int contentLength = connection.getContentLength();
                if (contentLength >= 0) {
                    int n;
                    result_data = new byte[contentLength];
                    for (int offset = 0; offset < contentLength; offset += n) {
                        n = in.read(result_data, offset, contentLength - offset);
                        if (n >= 0) continue;
                        throw new EOFException();
                    }
                } else {
                    int n;
                    ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
                    byte[] buffer = new byte[4096];
                    while ((n = in.read(buffer)) >= 0) {
                        baos.write(buffer, 0, n);
                    }
                    result_data = baos.toByteArray();
                }
            }
            finally {
                in.close();
            }
            String result = new String(result_data, charset);
            if (error != null) {
                throw new IOException("IO Error: \"" + result + "\"", error);
            }
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new IOException("Bad response " + responseCode + " " + connection.getResponseMessage() + ": \"" + result + "\"");
            }
            if (contentType_decoded != null && !contentType_decoded.equalsIgnoreCase("application/json")) {
                throw new IllegalStateException("Invalid Content-Type " + contentType_decoded + ": \"" + result + "\"");
            }
            String string = result;
            return string;
        }
        finally {
            connection.disconnect();
        }
    }
}

