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
import com.avaje.ebeaninternal.server.transaction.IndexEvent;
import com.avaje.ebeaninternal.server.transaction.RemoteTransactionEvent;
import java.io.IOException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface LuceneIndexManager {
    public void start();

    public void shutdown();

    public boolean isLuceneAvailable();

    public LuceneClusterIndexSync getClusterIndexSync();

    public void processEvent(RemoteTransactionEvent var1, SpiTransaction var2);

    public void processEvent(IndexEvent var1);

    public Query.UseIndex getDefaultUseIndex();

    public LIndex create(IndexDefn<?> var1, BeanDescriptor<?> var2) throws IOException;

    public LIndex getIndex(String var1);

    public SpiEbeanServer getServer();

    public void setServer(SpiEbeanServer var1);

    public void addIndex(LIndex var1) throws IOException;

    public String getIndexDirectory(String var1);

    public void notifyCluster(IndexEvent var1);
}

