/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.cluster;

import com.avaje.ebeaninternal.server.cluster.ClusterManager;
import com.avaje.ebeaninternal.server.cluster.LuceneClusterIndexSync;
import com.avaje.ebeaninternal.server.cluster.LuceneClusterListener;

public interface LuceneClusterFactory {
    public LuceneClusterListener createListener(ClusterManager var1, int var2);

    public LuceneClusterIndexSync createIndexSync();
}

