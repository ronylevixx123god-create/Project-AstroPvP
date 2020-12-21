/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.lucene.cluster;

import com.avaje.ebeaninternal.server.cluster.ClusterManager;
import com.avaje.ebeaninternal.server.cluster.LuceneClusterFactory;
import com.avaje.ebeaninternal.server.cluster.LuceneClusterIndexSync;
import com.avaje.ebeaninternal.server.cluster.LuceneClusterListener;
import com.avaje.ebeaninternal.server.lucene.cluster.SLuceneClusterSocketListener;
import com.avaje.ebeaninternal.server.lucene.cluster.SLuceneIndexSync;

public class SLuceneClusterFactory
implements LuceneClusterFactory {
    public LuceneClusterListener createListener(ClusterManager m, int port) {
        return new SLuceneClusterSocketListener(m, port);
    }

    public LuceneClusterIndexSync createIndexSync() {
        return new SLuceneIndexSync();
    }
}

