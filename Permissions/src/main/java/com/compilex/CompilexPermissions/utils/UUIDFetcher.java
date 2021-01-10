/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CompilexUUIDFetcher
 *  com.google.common.collect.ImmutableList
 */
package com.compilex.CompilexPermissions.utils;

import com.google.common.collect.ImmutableList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;

public class UUIDFetcher
implements Callable<Map<String, UUID>> {
    private final List<String> names;
    public static final HashMap<String, UUID> cachedUUID = new HashMap();
    public static final HashMap<UUID, String> reverseCachedUUID = new HashMap();

    public UUIDFetcher(List<String> names, boolean rateLimiting) {
        this.names = ImmutableList.copyOf(names);
    }

    public UUIDFetcher(List<String> names) {
        this(names, true);
    }

    @Override
    @Deprecated
    public Map<String, UUID> call() throws Exception {
        return new CompilexUUIDFetcher(this.names).call();
    }

    @Deprecated
    public static UUID getUUIDOf(String name) throws Exception {
        return CompilexUUIDFetcher.getUUIDOf((String)name);
    }

    @Deprecated
    public static String getNameOf(UUID uuid) throws Exception {
        return CompilexUUIDFetcher.getNameOf((UUID)uuid);
    }

    @Deprecated
    public static void cacheUUID(String playerName, UUID playerUUID) {
    }
}

