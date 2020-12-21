/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.cache;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.cache.ServerCache;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface ServerCacheManager {
    public void init(EbeanServer var1);

    public boolean isBeanCaching(Class<?> var1);

    public ServerCache getBeanCache(Class<?> var1);

    public ServerCache getQueryCache(Class<?> var1);

    public void clear(Class<?> var1);

    public void clearAll();
}

