/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Charsets
 */
package org.arkhamnetwork.permissions.manager;

import com.google.common.base.Charsets;
import java.util.UUID;

@Deprecated
public class CacheManager {
    @Deprecated
    public static UUID getSpoofedUUIDFromName(String name) {
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes(Charsets.UTF_8));
    }
}

