/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.common;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.config.ServerConfig;

public interface BootupEbeanManager {
    public EbeanServer createServer(ServerConfig var1);

    public EbeanServer createServer(String var1);

    public void shutdown();
}

