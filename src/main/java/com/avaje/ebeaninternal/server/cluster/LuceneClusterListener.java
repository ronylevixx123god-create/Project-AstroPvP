/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.cluster;

public interface LuceneClusterListener {
    public int getPort();

    public void startup();

    public void shutdown();
}

