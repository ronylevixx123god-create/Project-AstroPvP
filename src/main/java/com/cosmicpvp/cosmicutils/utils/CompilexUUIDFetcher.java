/*
 * Decompiled with CFR 0.150.
 */
package com.cosmicpvp.cosmicutils.utils;

import com.cosmicpvp.cosmicutils.CosmicUtils;
import com.cosmicpvp.cosmicutils.utils.CompilexUUIDFetcherListener;
import com.google.common.collect.ImmutableList;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class CompilexUUIDFetcher
implements Callable<Map<String, UUID>> {
    private static final double PROFILES_PER_REQUEST = 100.0;
    private static final String PROFILE_URL = "https://api.mojang.com/profiles/minecraft";
    private final JSONParser jsonParser = new JSONParser();
    private final List<String> names;
    private final boolean rateLimiting;
    public static final HashMap<String, UUID> fast_cachedUUID = new HashMap();
    public static final HashMap<UUID, String> fast_reverseCachedUUID = new HashMap();
    public static final HashMap<String, UUID> cachedUUID = new HashMap();
    public static final HashMap<UUID, String> reverseCachedUUID = new HashMap();

    public CompilexUUIDFetcher(List<String> names, boolean rateLimiting) {
        this.names = ImmutableList.copyOf(names);
        this.rateLimiting = rateLimiting;
    }

    public CompilexUUIDFetcher(List<String> names) {
        this(names, true);
    }

    @Override
    public Map<String, UUID> call() throws Exception {
        if (Bukkit.isPrimaryThread()) {
            Bukkit.getLogger().info("[CompilexUUIDFetcher] Skipping UUIDFetcher call() function being executed from the main server thread for: " + this.names.get(0));
            return null;
        }
        HashMap<String, UUID> uuidMap = new HashMap<String, UUID>();
        int requests = (int)Math.ceil((double)this.names.size() / 100.0);
        for (int i = 0; i < requests; ++i) {
            HttpURLConnection connection = CompilexUUIDFetcher.createConnection();
            connection.setConnectTimeout(4000);
            connection.setReadTimeout(4000);
            String body = JSONArray.toJSONString(this.names.subList(i * 100, Math.min((i + 1) * 100, this.names.size())));
            CompilexUUIDFetcher.writeBody(connection, body);
            JSONArray array = (JSONArray)this.jsonParser.parse(new InputStreamReader(connection.getInputStream()));
            for (Object profile : array) {
                JSONObject jsonProfile = (JSONObject)profile;
                String id = (String)jsonProfile.get("id");
                String name = (String)jsonProfile.get("name");
                UUID uuid = CompilexUUIDFetcher.getUUID(id);
                uuidMap.put(name.toLowerCase(), uuid);
                CompilexUUIDFetcher.cacheUUID(name, uuid);
            }
            if (!this.rateLimiting || i == requests - 1) continue;
            Thread.sleep(100L);
        }
        return uuidMap;
    }

    private static void writeBody(HttpURLConnection connection, String body) throws Exception {
        OutputStream stream = connection.getOutputStream();
        stream.write(body.getBytes());
        stream.flush();
        stream.close();
    }

    private static HttpURLConnection createConnection() throws Exception {
        URL url = new URL(PROFILE_URL);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setUseCaches(false);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        return connection;
    }

    private static UUID getUUID(String id) {
        return UUID.fromString(id.substring(0, 8) + "-" + id.substring(8, 12) + "-" + id.substring(12, 16) + "-" + id.substring(16, 20) + "-" + id.substring(20, 32));
    }

    public static byte[] toBytes(UUID uuid) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
        byteBuffer.putLong(uuid.getMostSignificantBits());
        byteBuffer.putLong(uuid.getLeastSignificantBits());
        return byteBuffer.array();
    }

    public static UUID fromBytes(byte[] array) {
        if (array.length != 16) {
            throw new IllegalArgumentException("Illegal byte array length: " + array.length);
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(array);
        long mostSignificant = byteBuffer.getLong();
        long leastSignificant = byteBuffer.getLong();
        return new UUID(mostSignificant, leastSignificant);
    }

    public static UUID getUUIDOf(String name) {
        return CompilexUUIDFetcher.getCachedUUIDOf(name, true);
    }

    public static UUID getUUIDOf_sync(String name) {
        return CompilexUUIDFetcher.getCachedUUIDOf(name, false);
    }

    public static UUID getCachedUUIDOf(String name, boolean mojangQuery) {
        if (name == null) {
            return null;
        }
        UUID found_fast = fast_cachedUUID.get(name = name.toLowerCase());
        if (found_fast != null) {
            return found_fast;
        }
        UUID found = cachedUUID.get(name);
        if (found != null) {
            fast_cachedUUID.put(name, found);
            return found;
        }
        Player op = Bukkit.getPlayer(name);
        if (op != null && (op.getLastPlayed() != 0L || op.isOnline())) {
            UUID offlinePlayer_UUID = op.getUniqueId();
            CompilexUUIDFetcher.cacheUUID(name, offlinePlayer_UUID);
            Bukkit.getLogger().info("[CompilexUUIDFetcher] Cached UUID of " + name + " to " + offlinePlayer_UUID + " by local OfflinePlayer data object.");
            return offlinePlayer_UUID;
        }
        if (mojangQuery) {
            try {
                UUID uuid = (UUID)new CompilexUUIDFetcher(Arrays.asList(name)).call().get(name);
                Bukkit.getLogger().info("[CompilexUUIDFetcher] Resolved " + name + " to " + uuid + " with a Mojang Query.");
                return uuid;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String getNameOf(UUID uuid) {
        if (uuid == null) {
            return null;
        }
        if (fast_reverseCachedUUID.containsKey(uuid)) {
            return fast_reverseCachedUUID.get(uuid);
        }
        String name = reverseCachedUUID.get(uuid);
        if (name != null) {
            name = name.toLowerCase();
            fast_reverseCachedUUID.put(uuid, name);
            return name;
        }
        Player op = Bukkit.getPlayer((UUID)uuid);
        if (op != null && (op.getLastPlayed() != 0L || op.isOnline())) {
            String offlinePlayer_name = op.getName().toLowerCase();
            reverseCachedUUID.put(uuid, offlinePlayer_name);
            Bukkit.getLogger().info("[CompilexUUIDFetcher] Cached NAME of " + uuid + " to " + offlinePlayer_name + " by local OfflinePlayer data object.");
            return offlinePlayer_name;
        }
        return null;
    }

    public static void cacheUUID(String playerName, UUID playerUUID) {
        cachedUUID.put(playerName.toLowerCase(), playerUUID);
        fast_cachedUUID.put(playerName.toLowerCase(), playerUUID);
        reverseCachedUUID.put(playerUUID, playerName.toLowerCase());
        fast_reverseCachedUUID.put(playerUUID, playerName.toLowerCase());
    }

    public static void cacheUUIDAndBroadcast(String playerName, UUID playerUUID) {
        CompilexUUIDFetcher.cacheUUID(playerName, playerUUID);
        CompilexUUIDFetcherListener.syncPlayerUUID(playerName, playerUUID);
    }

    public static void generatePlayerUUIDCache() {
        HashMap<String, Long> nameUUIDLastUse = new HashMap<String, Long>();
        HashMap<String, Long> claimedNames = new HashMap<String, Long>();
        HashMap<String, String> currentPairings = new HashMap<String, String>();
        Statement statement = null;
        ResultSet results = null;
        try {
            int uuid_duplicates = 0;
            int name_duplicates = 0;
            long queryStart = System.currentTimeMillis();
            Bukkit.getLogger().info("[CompilexUUIDFetcher] Querying SEEN table to build global player UUID cache...");
            statement = CosmicUtils.get().getSeenDatabase().getConnection().prepareStatement("SELECT uuid,name,time FROM users WHERE time IS NOT NULL");
            results = statement.executeQuery();
            long queryEnd = System.currentTimeMillis();
            Bukkit.getLogger().info("[CompilexUUIDFetcher] SEEN table query completed in... " + (queryEnd - queryStart) + "ms");
            while (results.next()) {
                try {
                    String uuidString = results.getString("uuid");
                    String name = results.getString("name").toLowerCase();
                    long time = results.getLong("time");
                    Long current = (Long)nameUUIDLastUse.get(uuidString);
                    if (current != null && time < current) {
                        ++uuid_duplicates;
                        continue;
                    }
                    nameUUIDLastUse.put(uuidString, time);
                    Long claimedName = (Long)claimedNames.get(name);
                    if (claimedName != null && claimedName > time) {
                        ++name_duplicates;
                        continue;
                    }
                    String oldNameOwner = (String)currentPairings.get(name);
                    if (oldNameOwner != null && !oldNameOwner.equals(uuidString)) {
                        reverseCachedUUID.remove(UUID.fromString(oldNameOwner));
                        ++name_duplicates;
                    }
                    claimedNames.put(name, time);
                    currentPairings.put(name, uuidString);
                    reverseCachedUUID.put(UUID.fromString(uuidString), name);
                }
                catch (Exception err) {
                    err.printStackTrace();
                }
            }
            for (Map.Entry<UUID, String> data : reverseCachedUUID.entrySet()) {
                cachedUUID.put(data.getValue(), data.getKey());
            }
            Bukkit.getLogger().info("[CompilexUUIDFetcher] Loaded " + cachedUUID.size() + " unique name:UUID pairings into memory. Filtered " + uuid_duplicates + " duplicate UUID, and " + name_duplicates + " duplicate name entries in " + (System.currentTimeMillis() - queryEnd) + "ms");
        }
        catch (Exception err) {
            err.printStackTrace();
        }
        try {
            results.close();
            statement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        nameUUIDLastUse.clear();
    }
}

