/*
 * Decompiled with CFR 0.150.
 */
package com.cosmicpvp.cosmicutils.utils;

import com.google.common.collect.ImmutableList;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class MojangUUIDFetcher
implements Callable<Map<String, UUID>> {
    private final List<String> names;

    public MojangUUIDFetcher(List<String> names, boolean rateLimiting) {
        this.names = ImmutableList.copyOf(names);
    }

    public MojangUUIDFetcher(List<String> names) {
        this(names, true);
    }

    @Override
    public Map<String, UUID> call() throws Exception {
        return new CompilexUUIDFetcher(this.names).call();
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

    @Deprecated
    public static UUID getUUIDOfSync(String name) throws Exception {
        return CompilexUUIDFetcher.getUUIDOf_sync(name);
    }

    public static void getUUIDOf(String name, Consumer<UUID> callback) {
        CompletableFuture.runAsync(() -> {
            try {
                UUID uuid = CompilexUUIDFetcher.getUUIDOf(name);
                if (callback != null) {
                    callback.accept(uuid);
                    return;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            callback.accept(null);
        });
    }

    public static String uuidToName(UUID uuid) {
        if (uuid != null) {
            return CompilexUUIDFetcher.getNameOf(uuid);
        }
        return null;
    }

    public static void getNameFromUUID(UUID uuid, Consumer<String> callback) {
        CompletableFuture.runAsync(() -> {
            String cached = CompilexUUIDFetcher.getNameOf(uuid);
            if (cached != null) {
                if (callback != null) {
                    callback.accept(cached);
                }
                return;
            }
        });
    }

    public static String fullUUID(String uuid) {
        uuid = MojangUUIDFetcher.cleanUUID(uuid);
        uuid = uuid.substring(0, 8) + "-" + uuid.substring(8, 12) + "-" + uuid.substring(12, 16) + "-" + uuid.substring(16, 20) + "-" + uuid.substring(20, 32);
        return uuid;
    }

    public static String cleanUUID(String uuid) {
        return uuid.replaceAll("-", "");
    }
}

