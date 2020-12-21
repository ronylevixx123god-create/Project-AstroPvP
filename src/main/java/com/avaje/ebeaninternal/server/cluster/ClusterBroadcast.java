/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.cluster;

import com.avaje.ebeaninternal.server.cluster.ClusterManager;
import com.avaje.ebeaninternal.server.transaction.RemoteTransactionEvent;

public interface ClusterBroadcast {
    public void startup(ClusterManager var1);

    public void shutdown();

    public void broadcast(RemoteTransactionEvent var1);
}

