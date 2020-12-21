/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.api;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.Query;
import com.avaje.ebean.Transaction;
import com.avaje.ebean.TxScope;
import com.avaje.ebean.bean.BeanCollectionLoader;
import com.avaje.ebean.bean.BeanLoader;
import com.avaje.ebean.bean.CallStack;
import com.avaje.ebean.config.dbplatform.DatabasePlatform;
import com.avaje.ebeaninternal.api.LoadBeanRequest;
import com.avaje.ebeaninternal.api.LoadManyRequest;
import com.avaje.ebeaninternal.api.ScopeTrans;
import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebeaninternal.api.TransactionEventTable;
import com.avaje.ebeaninternal.server.autofetch.AutoFetchManager;
import com.avaje.ebeaninternal.server.core.PstmtBatch;
import com.avaje.ebeaninternal.server.core.SpiOrmQueryRequest;
import com.avaje.ebeaninternal.server.ddl.DdlGenerator;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.lucene.LuceneIndexManager;
import com.avaje.ebeaninternal.server.query.CQuery;
import com.avaje.ebeaninternal.server.query.CQueryEngine;
import com.avaje.ebeaninternal.server.transaction.RemoteTransactionEvent;
import java.lang.reflect.Type;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface SpiEbeanServer
extends EbeanServer,
BeanLoader,
BeanCollectionLoader {
    public boolean isDefaultDeleteMissingChildren();

    public boolean isDefaultUpdateNullProperties();

    public boolean isVanillaMode();

    public DatabasePlatform getDatabasePlatform();

    public PstmtBatch getPstmtBatch();

    public CallStack createCallStack();

    public DdlGenerator getDdlGenerator();

    public AutoFetchManager getAutoFetchManager();

    public LuceneIndexManager getLuceneIndexManager();

    public void clearQueryStatistics();

    public List<BeanDescriptor<?>> getBeanDescriptors();

    public <T> BeanDescriptor<T> getBeanDescriptor(Class<T> var1);

    public BeanDescriptor<?> getBeanDescriptorById(String var1);

    public List<BeanDescriptor<?>> getBeanDescriptors(String var1);

    public void externalModification(TransactionEventTable var1);

    public SpiTransaction createServerTransaction(boolean var1, int var2);

    public SpiTransaction getCurrentServerTransaction();

    public ScopeTrans createScopeTrans(TxScope var1);

    public SpiTransaction createQueryTransaction();

    public void remoteTransactionEvent(RemoteTransactionEvent var1);

    public <T> SpiOrmQueryRequest<T> createQueryRequest(Query.Type var1, Query<T> var2, Transaction var3);

    public <T> CQuery<T> compileQuery(Query<T> var1, Transaction var2);

    public CQueryEngine getQueryEngine();

    public <T> List<Object> findIdsWithCopy(Query<T> var1, Transaction var2);

    public <T> int findRowCountWithCopy(Query<T> var1, Transaction var2);

    public void loadBean(LoadBeanRequest var1);

    public void loadMany(LoadManyRequest var1);

    public int getLazyLoadBatchSize();

    public boolean isSupportedType(Type var1);
}

