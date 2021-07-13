/*
 * Decompiled with CFR 0.150.
 */
package io.apice.clientcomms.handshake;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HandshakeContainer {
    @SerializedName(value="name")
    public String name;
    @SerializedName(value="uuid")
    public String uuid;
    @SerializedName(value="mojang_token_hash")
    public String mojang_token_hash;
    @SerializedName(value="version")
    public String version;
    @SerializedName(value="client_time")
    public Long client_time;
    @SerializedName(value="client_random")
    public Long client_random;
    @SerializedName(value="server_time")
    public Long server_time;
    @SerializedName(value="server_random")
    public Long server_random;
    @SerializedName(value="lifespan")
    public Long lifespan;
    @SerializedName(value="hmac")
    public String hmac;
    public transient Long create_time_nanos;
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[0-9a-zA-Z_]{1,16}$");
    protected static final Pattern UUID_PATTERN = Pattern.compile("^[0-9a-f]{32}$");
    private static final Pattern MOJANG_TOKEN_HASH_PATTERN = Pattern.compile("^[0-9a-f]{128}$");
    private static final Pattern MD5_PATTERN = Pattern.compile("^[0-9a-f]{32}$");
    private static final Pattern HMAC_PATTERN = Pattern.compile("^[0-9a-f]{128}$");
    public static final String MAC_SPEC = "HmacSHA512";
    public static final char[] HEX_TABLE = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    public static final Gson gsonMin;
    public static final Gson gson;

    public void checkSanity_client() {
        if (this.name == null || !USERNAME_PATTERN.matcher(this.name).matches()) {
            throw new IllegalStateException("Missing / Invalid name");
        }
        if (this.uuid == null || !UUID_PATTERN.matcher(this.uuid).matches()) {
            throw new IllegalStateException("Missing / Invalid uuid");
        }
        if (this.mojang_token_hash == null || !MOJANG_TOKEN_HASH_PATTERN.matcher(this.mojang_token_hash).matches()) {
            throw new IllegalStateException("Missing / Invalid mojang_token_hash");
        }
        if (this.version == null || !MD5_PATTERN.matcher(this.version).matches()) {
            throw new IllegalStateException("Missing / Invalid version");
        }
        if (this.client_time == null || this.client_time <= 0L) {
            throw new IllegalStateException("Missing / Invalid client_time");
        }
        if (this.client_random == null) {
            throw new IllegalStateException("Missing / Invalid client_random");
        }
    }

    public void checkSanity_server(boolean check_hmac) {
        if (this.server_time == null || this.server_time <= 0L) {
            throw new IllegalStateException("Missing / Invalid server_time");
        }
        if (this.server_random == null) {
            throw new IllegalStateException("Missing / Invalid server_random");
        }
        if (this.lifespan == null || this.lifespan <= 0L) {
            throw new IllegalStateException("Missing / Invalid lifespan");
        }
        if (check_hmac && (this.hmac == null || !HMAC_PATTERN.matcher(this.hmac).matches())) {
            throw new IllegalStateException("Missing / Invalid hmac");
        }
    }

    public boolean isValid() {
        return this.isValidFor(0L);
    }

    public boolean isValidFor(long dur_ms) {
        return this.isValidFor(dur_ms, TimeUnit.MILLISECONDS);
    }

    public long validFor(TimeUnit unit) {
        if (this.create_time_nanos != null) {
            return this.lifespan * 1000000L - (System.nanoTime() - this.create_time_nanos);
        }
        return (this.lifespan - (System.currentTimeMillis() - this.server_time)) * 1000000L;
    }

    public boolean isValidFor(long dur, TimeUnit dur_unit) {
        return this.validFor(dur_unit) >= dur;
    }

    public String encodeWithoutHMAC() {
        String hmac = this.hmac;
        try {
            this.hmac = null;
            String string = gsonMin.toJson(this);
            return string;
        }
        finally {
            this.hmac = hmac;
        }
    }

    public String encode() {
        return gsonMin.toJson(this);
    }

    public static Mac getMACInstance() {
        try {
            return Mac.getInstance(MAC_SPEC);
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static SecretKeySpec getKeySpec(byte[] key) {
        SecretKeySpec keySpec = new SecretKeySpec(key, MAC_SPEC);
        return keySpec;
    }

    public boolean checkHMAC(Mac mac) {
        String hmac = this.hmac;
        if (hmac == null) {
            return false;
        }
        String hmac2 = this.computeHMAC(mac);
        return hmac2.equals(hmac);
    }

    public String computeHMAC(Mac mac) {
        String encoded = this.encodeWithoutHMAC();
        byte[] encoded_data = encoded.getBytes(StandardCharsets.UTF_8);
        mac.reset();
        mac.update(encoded_data);
        byte[] hmac2_raw = mac.doFinal();
        String hmac2 = HandshakeContainer.toHexString(hmac2_raw);
        return hmac2;
    }

    public boolean equals_client(HandshakeContainer o) {
        return Objects.equals(this.name, o.name) && Objects.equals(this.uuid, o.uuid) && Objects.equals(this.mojang_token_hash, o.mojang_token_hash) && Objects.equals(this.version, o.version) && Objects.equals(this.client_time, o.client_time) && Objects.equals(this.client_random, o.client_random);
    }

    public boolean equals_server(HandshakeContainer o) {
        return Objects.equals(this.server_time, o.server_time) && Objects.equals(this.server_random, o.server_random) && Objects.equals(this.lifespan, o.lifespan) && Objects.equals(this.hmac, o.hmac);
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof HandshakeContainer)) {
            return false;
        }
        HandshakeContainer o = (HandshakeContainer)other;
        return this.equals_client(o) && this.equals_server(o);
    }

    public int hashCode() {
        return this.encode().hashCode();
    }

    public String toString() {
        return gson.toJson(this);
    }

    public HandshakeContainer clone() {
        HandshakeContainer r = new HandshakeContainer();
        r.name = this.name;
        r.uuid = this.uuid;
        r.mojang_token_hash = this.mojang_token_hash;
        r.version = this.version;
        r.client_time = this.client_time;
        r.client_random = this.client_random;
        r.server_time = this.server_time;
        r.server_random = this.server_random;
        r.lifespan = this.lifespan;
        r.hmac = this.hmac;
        r.create_time_nanos = this.create_time_nanos;
        return r;
    }

    public static String toHexString(byte[] b) {
        return HandshakeContainer.toHexString(b, 0, b.length);
    }

    public static String toHexString(byte[] b, int off, int len) {
        char[] out = new char[len << 1];
        char[] t = HEX_TABLE;
        int i = 0;
        len += off;
        while (off < len) {
            int v = b[off] & 0xFF;
            out[i++] = t[v >>> 4];
            out[i++] = t[v & 0xF];
            ++off;
        }
        return new String(out);
    }

    static {
        GsonBuilder builder = new GsonBuilder();
        builder.disableHtmlEscaping();
        gsonMin = builder.create();
        builder.setPrettyPrinting();
        gson = builder.create();
    }

    public static class MojangToken {
        @SerializedName(value="uuid")
        public String uuid;
        @SerializedName(value="mojang_token")
        public String mojang_token;
        public static final String HASH_ALGO = "SHA-512";

        public String encode() {
            return gsonMin.toJson(this);
        }

        public static MessageDigest getHashAlgoInstance() {
            try {
                return MessageDigest.getInstance(HASH_ALGO);
            }
            catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }

        public String computeHash(MessageDigest md) {
            String encoded = this.encode();
            byte[] encoded_data = encoded.getBytes(StandardCharsets.UTF_8);
            md.reset();
            md.update(encoded_data);
            byte[] hash_raw = md.digest();
            String hash = HandshakeContainer.toHexString(hash_raw);
            return hash;
        }

        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof MojangToken)) {
                return false;
            }
            MojangToken o = (MojangToken)other;
            return Objects.equals(this.uuid, o.uuid) && Objects.equals(this.mojang_token, o.mojang_token);
        }

        public int hashCode() {
            return this.encode().hashCode();
        }

        public String toString() {
            return gson.toJson(this);
        }

        public void checkSanity() {
            if (this.uuid == null || !UUID_PATTERN.matcher(this.uuid).matches()) {
                throw new IllegalStateException("Missing / Invalid uuid");
            }
            if (this.mojang_token == null || this.mojang_token.length() == 0 || this.mojang_token.equals("0")) {
                throw new IllegalStateException("Missing / Invalid mojang_token");
            }
        }
    }
}

