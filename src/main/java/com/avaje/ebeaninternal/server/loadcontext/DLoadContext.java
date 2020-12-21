/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.loadcontext;

import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebean.bean.EntityBeanIntercept;
import com.avaje.ebean.bean.ObjectGraphNode;
import com.avaje.ebean.bean.ObjectGraphOrigin;
import com.avaje.ebean.bean.PersistenceContext;
import com.avaje.ebeaninternal.api.LoadContext;
import com.avaje.ebeaninternal.api.LoadSecondaryQuery;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssoc;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import com.avaje.ebeaninternal.server.loadcontext.DLoadBeanContext;
import com.avaje.ebeaninternal.server.loadcontext.DLoadManyContext;
import com.avaje.ebeaninternal.server.querydefn.OrmQueryProperties;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class DLoadContext
implements LoadContext {
    private final SpiEbeanServer ebeanServer;
    private final BeanDescriptor<?> rootDescriptor;
    private final Map<String, DLoadBeanContext> beanMap = new HashMap<String, DLoadBeanContext>();
    private final Map<String, DLoadManyContext> manyMap = new HashMap<String, DLoadManyContext>();
    private final DLoadBeanContext rootBeanContext;
    private final int parentState;
    private final int defaultBatchSize;
    private PersistenceContext persistenceContext;
    private String relativePath;
    private ObjectGraphOrigin origin;
    private Map<String, ObjectGraphNode> nodePathMap = new HashMap<String, ObjectGraphNode>();
    private boolean useAutofetchManager;
    private List<OrmQueryProperties> secQuery;

    public DLoadContext(SpiEbeanServer ebeanServer, BeanDescriptor<?> rootDescriptor, int defaultBatchSize, int parentState, SpiQuery<?> query) {
        this.defaultBatchSize = defaultBatchSize;
        this.ebeanServer = ebeanServer;
        this.rootDescriptor = rootDescriptor;
        this.rootBeanContext = new DLoadBeanContext(this, rootDescriptor, null, defaultBatchSize, null);
        this.parentState = parentState;
        ObjectGraphNode node = query.getParentNode();
        if (node != null) {
            this.origin = node.getOriginQueryPoint();
            this.relativePath = node.getPath();
        }
        this.useAutofetchManager = query.getAutoFetchManager() != null;
    }

    @Override
    public int getSecondaryQueriesMinBatchSize(OrmQueryRequest<?> parentRequest, int defaultQueryBatch) {
        if (this.secQuery == null) {
            return -1;
        }
        int maxBatch = 0;
        for (int i = 0; i < this.secQuery.size(); ++i) {
            int batchSize = this.secQuery.get(i).getQueryFetchBatch();
            if (batchSize == 0) {
                batchSize = defaultQueryBatch;
            }
            maxBatch = Math.max(maxBatch, batchSize);
        }
        return maxBatch;
    }

    @Override
    public void executeSecondaryQueries(OrmQueryRequest<?> parentRequest, int defaultQueryBatch) {
        if (this.secQuery != null) {
            for (int i = 0; i < this.secQuery.size(); ++i) {
                OrmQueryProperties properties = this.secQuery.get(i);
                int batchSize = properties.getQueryFetchBatch();
                if (batchSize == 0) {
                    batchSize = defaultQueryBatch;
                }
                LoadSecondaryQuery load = this.getLoadSecondaryQuery(properties.getPath());
                load.loadSecondaryQuery(parentRequest, batchSize, properties.isQueryFetchAll());
            }
        }
    }

    private LoadSecondaryQuery getLoadSecondaryQuery(String path) {
        LoadSecondaryQuery beanLoad = this.beanMap.get(path);
        if (beanLoad == null) {
            beanLoad = this.manyMap.get(path);
        }
        return beanLoad;
    }

    @Override
    public void registerSecondaryQueries(SpiQuery<?> query) {
        List<OrmQueryProperties> lazyQueries;
        this.secQuery = query.removeQueryJoins();
        if (this.secQuery != null) {
            for (int i = 0; i < this.secQuery.size(); ++i) {
                OrmQueryProperties props = this.secQuery.get(i);
                this.registerSecondaryQuery(props);
            }
        }
        if ((lazyQueries = query.removeLazyJoins()) != null) {
            for (int i = 0; i < lazyQueries.size(); ++i) {
                OrmQueryProperties lazyProps = lazyQueries.get(i);
                this.registerSecondaryQuery(lazyProps);
            }
        }
    }

    private void registerSecondaryQuery(OrmQueryProperties props) {
        String propName = props.getPath();
        ElPropertyValue elGetValue = this.rootDescriptor.getElGetValue(propName);
        boolean many = elGetValue.getBeanProperty().containsMany();
        this.registerSecondaryNode(many, props);
    }

    @Override
    public ObjectGraphNode getObjectGraphNode(String path) {
        ObjectGraphNode node = this.nodePathMap.get(path);
        if (node == null) {
            node = this.createObjectGraphNode(path);
            this.nodePathMap.put(path, node);
        }
        return node;
    }

    private ObjectGraphNode createObjectGraphNode(String path) {
        if (this.relativePath != null) {
            path = path == null ? this.relativePath : this.relativePath + "." + path;
        }
        return new ObjectGraphNode(this.origin, path);
    }

    @Override
    public boolean isUseAutofetchManager() {
        return this.useAutofetchManager;
    }

    public String getRelativePath() {
        return this.relativePath;
    }

    protected SpiEbeanServer getEbeanServer() {
        return this.ebeanServer;
    }

    @Override
    public int getParentState() {
        return this.parentState;
    }

    @Override
    public PersistenceContext getPersistenceContext() {
        return this.persistenceContext;
    }

    @Override
    public void setPersistenceContext(PersistenceContext persistenceContext) {
        this.persistenceContext = persistenceContext;
    }

    @Override
    public void register(String path, EntityBeanIntercept ebi) {
        this.getBeanContext(path).register(ebi);
    }

    @Override
    public void register(String path, BeanCollection<?> bc) {
        this.getManyContext(path).register(bc);
    }

    @Override
    public DLoadBeanContext getBeanContext(String path) {
        if (path == null) {
            return this.rootBeanContext;
        }
        DLoadBeanContext beanContext = this.beanMap.get(path);
        if (beanContext == null) {
            beanContext = this.createBeanContext(path, this.defaultBatchSize, null);
            this.beanMap.put(path, beanContext);
        }
        return beanContext;
    }

    private void registerSecondaryNode(boolean many, OrmQueryProperties props) {
        int batchSize;
        String path = props.getPath();
        int lazyJoinBatch = props.getLazyFetchBatch();
        int n = batchSize = lazyJoinBatch > 0 ? lazyJoinBatch : this.defaultBatchSize;
        if (many) {
            DLoadManyContext manyContext = this.createManyContext(path, batchSize, props);
            this.manyMap.put(path, manyContext);
        } else {
            DLoadBeanContext beanContext = this.createBeanContext(path, batchSize, props);
            this.beanMap.put(path, beanContext);
        }
    }

    @Override
    public DLoadManyContext getManyContext(String path) {
        if (path == null) {
            throw new RuntimeException("path is null?");
        }
        DLoadManyContext ctx = this.manyMap.get(path);
        if (ctx == null) {
            ctx = this.createManyContext(path, this.defaultBatchSize, null);
            this.manyMap.put(path, ctx);
        }
        return ctx;
    }

    private DLoadManyContext createManyContext(String path, int batchSize, OrmQueryProperties queryProps) {
        BeanPropertyAssocMany p = (BeanPropertyAssocMany)this.getBeanProperty(this.rootDescriptor, path);
        return new DLoadManyContext(this, p, path, batchSize, queryProps);
    }

    private DLoadBeanContext createBeanContext(String path, int batchSize, OrmQueryProperties queryProps) {
        BeanPropertyAssoc p = (BeanPropertyAssoc)this.getBeanProperty(this.rootDescriptor, path);
        BeanDescriptor targetDescriptor = p.getTargetDescriptor();
        return new DLoadBeanContext(this, targetDescriptor, path, batchSize, queryProps);
    }

    private BeanProperty getBeanProperty(BeanDescriptor<?> desc, String path) {
        return desc.getBeanPropertyFromPath(path);
    }
}

