/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.loadcontext;

import com.avaje.ebean.bean.BeanLoader;
import com.avaje.ebean.bean.EntityBeanIntercept;
import com.avaje.ebean.bean.ObjectGraphNode;
import com.avaje.ebean.bean.PersistenceContext;
import com.avaje.ebeaninternal.api.LoadBeanContext;
import com.avaje.ebeaninternal.api.LoadBeanRequest;
import com.avaje.ebeaninternal.api.LoadContext;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.loadcontext.DLoadContext;
import com.avaje.ebeaninternal.server.loadcontext.DLoadWeakList;
import com.avaje.ebeaninternal.server.querydefn.OrmQueryProperties;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class DLoadBeanContext
implements LoadBeanContext,
BeanLoader {
    protected final DLoadContext parent;
    protected final BeanDescriptor<?> desc;
    protected final String path;
    protected final String fullPath;
    private final DLoadWeakList<EntityBeanIntercept> weakList;
    private final OrmQueryProperties queryProps;
    private int batchSize;

    public DLoadBeanContext(DLoadContext parent, BeanDescriptor<?> desc, String path, int batchSize, OrmQueryProperties queryProps) {
        this.parent = parent;
        this.desc = desc;
        this.path = path;
        this.batchSize = batchSize;
        this.queryProps = queryProps;
        this.weakList = new DLoadWeakList();
        this.fullPath = parent.getRelativePath() == null ? path : parent.getRelativePath() + "." + path;
    }

    @Override
    public void configureQuery(SpiQuery<?> query, String lazyLoadProperty) {
        query.setParentState(this.parent.getParentState());
        query.setParentNode(this.getObjectGraphNode());
        query.setLazyLoadProperty(lazyLoadProperty);
        if (this.queryProps != null) {
            this.queryProps.configureBeanQuery(query);
        }
        if (this.parent.isUseAutofetchManager()) {
            query.setAutofetch(true);
        }
    }

    @Override
    public String getFullPath() {
        return this.fullPath;
    }

    @Override
    public PersistenceContext getPersistenceContext() {
        return this.parent.getPersistenceContext();
    }

    public OrmQueryProperties getQueryProps() {
        return this.queryProps;
    }

    public ObjectGraphNode getObjectGraphNode() {
        return this.parent.getObjectGraphNode(this.path);
    }

    public String getPath() {
        return this.path;
    }

    @Override
    public String getName() {
        return this.parent.getEbeanServer().getName();
    }

    @Override
    public int getBatchSize() {
        return this.batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    @Override
    public BeanDescriptor<?> getBeanDescriptor() {
        return this.desc;
    }

    public LoadContext getGraphContext() {
        return this.parent;
    }

    public void register(EntityBeanIntercept ebi) {
        int pos = this.weakList.add(ebi);
        ebi.setBeanLoader(pos, this);
    }

    @Override
    public void loadBean(EntityBeanIntercept ebi) {
        if (this.desc.lazyLoadMany(ebi)) {
            return;
        }
        int position = ebi.getBeanLoaderIndex();
        List<EntityBeanIntercept> batch = this.weakList.getLoadBatch(position, this.batchSize);
        LoadBeanRequest req = new LoadBeanRequest(this, batch, null, this.batchSize, true, ebi.getLazyLoadProperty());
        this.parent.getEbeanServer().loadBean(req);
    }

    @Override
    public void loadSecondaryQuery(OrmQueryRequest<?> parentRequest, int requestedBatchSize, boolean all) {
        List<EntityBeanIntercept> batch;
        while ((batch = this.weakList.getNextBatch(requestedBatchSize)).size() != 0) {
            LoadBeanRequest req = new LoadBeanRequest(this, batch, parentRequest.getTransaction(), requestedBatchSize, false, null);
            this.parent.getEbeanServer().loadBean(req);
            if (all) continue;
            break;
        }
    }
}

