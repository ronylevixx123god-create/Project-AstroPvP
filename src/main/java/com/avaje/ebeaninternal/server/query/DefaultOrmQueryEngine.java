/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.query;

import com.avaje.ebean.Query;
import com.avaje.ebean.QueryIterator;
import com.avaje.ebean.Transaction;
import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebean.event.BeanFinder;
import com.avaje.ebeaninternal.api.BeanIdList;
import com.avaje.ebeaninternal.server.core.CopyBeanCollection;
import com.avaje.ebeaninternal.server.core.OrmQueryEngine;
import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptorManager;
import com.avaje.ebeaninternal.server.deploy.CopyContext;
import com.avaje.ebeaninternal.server.query.CQueryEngine;
import java.util.Collection;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class DefaultOrmQueryEngine
implements OrmQueryEngine {
    private final CQueryEngine queryEngine;

    public DefaultOrmQueryEngine(BeanDescriptorManager descMgr, CQueryEngine queryEngine) {
        this.queryEngine = queryEngine;
    }

    @Override
    public <T> int findRowCount(OrmQueryRequest<T> request) {
        return this.queryEngine.findRowCount(request);
    }

    @Override
    public <T> BeanIdList findIds(OrmQueryRequest<T> request) {
        return this.queryEngine.findIds(request);
    }

    @Override
    public <T> QueryIterator<T> findIterate(OrmQueryRequest<T> request) {
        Transaction t = request.getTransaction();
        t.flushBatch();
        return this.queryEngine.findIterate(request);
    }

    @Override
    public <T> BeanCollection<T> findMany(OrmQueryRequest<T> request) {
        Query query = request.getQuery();
        if (query.isUseQueryCache() || query.isLoadBeanCache()) {
            query.setSharedInstance();
        }
        BeanCollection<T> result = null;
        Transaction t = request.getTransaction();
        t.flushBatch();
        BeanFinder<T> finder = request.getBeanFinder();
        result = finder != null ? finder.findMany(request) : this.queryEngine.findMany(request);
        if (query.isLoadBeanCache()) {
            BeanDescriptor<T> descriptor = request.getBeanDescriptor();
            Collection<T> c = result.getActualDetails();
            for (T bean : c) {
                descriptor.cachePut(bean, query.isSharedInstance());
            }
        }
        if (query.isSharedInstance() && !result.isEmpty()) {
            if (query.isUseQueryCache()) {
                request.putToQueryCache(result);
            }
            if (Boolean.FALSE.equals(query.isReadOnly())) {
                CopyContext ctx = new CopyContext(request.isVanillaMode(), false);
                result = new CopyBeanCollection<T>(result, request.getBeanDescriptor(), ctx, 5).copy();
            }
        }
        return result;
    }

    @Override
    public <T> T findId(OrmQueryRequest<T> request) {
        BeanFinder<T> finder;
        Object result = null;
        Transaction t = request.getTransaction();
        if (t.isBatchFlushOnQuery()) {
            t.flushBatch();
        }
        if ((result = (finder = request.getBeanFinder()) != null ? (Object)finder.find(request) : (Object)this.queryEngine.find(request)) != null && request.isUseBeanCache()) {
            BeanDescriptor<Object> descriptor = request.getBeanDescriptor();
            descriptor.cachePut(result, request.isUseBeanCacheReadOnly());
        }
        return result;
    }
}

