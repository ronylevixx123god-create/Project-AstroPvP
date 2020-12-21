/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.lucene;

import com.avaje.ebean.Query;
import com.avaje.ebean.config.lucene.IndexDefn;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebeaninternal.server.cluster.LuceneClusterIndexSync;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.lucene.LIndex;
import com.avaje.ebeaninternal.server.lucene.LuceneIndexManager;
import com.avaje.ebeaninternal.server.transaction.IndexEvent;
import com.avaje.ebeaninternal.server.transaction.RemoteTransactionEvent;
import java.io.IOException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class NoLuceneIndexManager
implements LuceneIndexManager {
    @Override
    public void start() {
    }

    @Override
    public void shutdown() {
    }

    @Override
    public void setServer(SpiEbeanServer server) {
    }

    @Override
    public boolean isLuceneAvailable() {
        return false;
    }

    @Override
    public void processEvent(RemoteTransactionEvent txnEvent, SpiTransaction t) {
        throw new RuntimeException("Never Called");
    }

    @Override
    public void processEvent(IndexEvent indexEvent) {
        throw new RuntimeException("Never Called");
    }

    @Override
    public LuceneClusterIndexSync getClusterIndexSync() {
        throw new RuntimeException("Never Called");
    }

    @Override
    public void notifyCluster(IndexEvent event) {
        throw new RuntimeException("Never Called");
    }

    @Override
    public void addIndex(LIndex index) throws IOException {
        throw new RuntimeException("Never Called");
    }

    @Override
    public LIndex getIndex(String defnName) {
        throw new RuntimeException("Never Called");
    }

    @Override
    public LIndex create(IndexDefn<?> indexDefn, BeanDescriptor<?> descriptor) throws IOException {
        throw new RuntimeException("Never Called");
    }

    @Override
    public Query.UseIndex getDefaultUseIndex() {
        return Query.UseIndex.NO;
    }

    public LIndex getIndexByTypeAndName(Class<?> beanType, String name) {
        throw new RuntimeException("Never Called");
    }

    @Override
    public String getIndexDirectory(String indexName) {
        throw new RuntimeException("Never Called");
    }

    @Override
    public SpiEbeanServer getServer() {
        throw new RuntimeException("Never Called");
    }
}

