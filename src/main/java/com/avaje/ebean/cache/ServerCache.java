/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.cache;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.cache.ServerCacheOptions;
import com.avaje.ebean.cache.ServerCacheStatistics;

public interface ServerCache {
    public void init(EbeanServer var1);

    public ServerCacheOptions getOptions();

    public void setOptions(ServerCacheOptions var1);

    public Object get(Object var1);

    public Object put(Object var1, Object var2);

    public Object putIfAbsent(Object var1, Object var2);

    public Object remove(Object var1);

    public void clear();

    public int size();

    public int getHitRatio();

    public ServerCacheStatistics getStatistics(boolean var1);
}

