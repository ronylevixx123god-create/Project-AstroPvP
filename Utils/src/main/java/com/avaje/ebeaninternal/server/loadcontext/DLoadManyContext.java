/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.loadcontext;

import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebean.bean.BeanCollectionLoader;
import com.avaje.ebean.bean.ObjectGraphNode;
import com.avaje.ebean.bean.PersistenceContext;
import com.avaje.ebeaninternal.api.LoadManyContext;
import com.avaje.ebeaninternal.api.LoadManyRequest;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.loadcontext.DLoadContext;
import com.avaje.ebeaninternal.server.loadcontext.DLoadWeakList;
import com.avaje.ebeaninternal.server.querydefn.OrmQueryProperties;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class DLoadManyContext
implements LoadManyContext,
BeanCollectionLoader {
    protected final DLoadContext parent;
    protected final String fullPath;
    private final BeanDescriptor<?> desc;
    private final BeanPropertyAssocMany<?> property;
    private final String path;
    private final int batchSize;
    private final OrmQueryProperties queryProps;
    private final DLoadWeakList<BeanCollection<?>> weakList;

    public DLoadManyContext(DLoadContext parent, BeanPropertyAssocMany<?> p, String path, int batchSize, OrmQueryProperties queryProps) {
        this.parent = parent;
        this.property = p;
        this.desc = p.getBeanDescriptor();
        this.path = path;
        this.batchSize = batchSize;
        this.queryProps = queryProps;
        this.weakList = new DLoadWeakList();
        this.fullPath = parent.getRelativePath() == null ? path : parent.getRelativePath() + "." + path;
    }

    @Override
    public void configureQuery(SpiQuery<?> query) {
        query.setParentState(this.parent.getParentState());
        query.setParentNode(this.getObjectGraphNode());
        if (this.queryProps != null) {
            this.queryProps.configureManyQuery(query);
        }
        if (this.parent.isUseAutofetchManager()) {
            query.setAutofetch(true);
        }
    }

    @Override
    public ObjectGraphNode getObjectGraphNode() {
        int pos = this.path.lastIndexOf(46);
        if (pos == -1) {
            return this.parent.getObjectGraphNode(null);
        }
        String parentPath = this.path.substring(0, pos);
        return this.parent.getObjectGraphNode(parentPath);
    }

    @Override
    public String getFullPath() {
        return this.fullPath;
    }

    @Override
    public PersistenceContext getPersistenceContext() {
        return this.parent.getPersistenceContext();
    }

    @Override
    public int getBatchSize() {
        return this.batchSize;
    }

    @Override
    public BeanPropertyAssocMany<?> getBeanProperty() {
        return this.property;
    }

    @Override
    public BeanDescriptor<?> getBeanDescriptor() {
        return this.desc;
    }

    public String getPath() {
        return this.path;
    }

    @Override
    public String getName() {
        return this.parent.getEbeanServer().getName();
    }

    public void register(BeanCollection<?> bc) {
        int pos = this.weakList.add(bc);
        bc.setLoader(pos, this);
    }

    @Override
    public void loadMany(BeanCollection<?> bc, boolean onlyIds) {
        int position = bc.getLoaderIndex();
        List<BeanCollection<?>> loadBatch = this.weakList.getLoadBatch(position, this.batchSize);
        LoadManyRequest req = new LoadManyRequest(this, loadBatch, null, this.batchSize, true, onlyIds);
        this.parent.getEbeanServer().loadMany(req);
    }

    @Override
    public void loadSecondaryQuery(OrmQueryRequest<?> parentRequest, int requestedBatchSize, boolean all) {
        List<BeanCollection<?>> batch;
        while ((batch = this.weakList.getNextBatch(requestedBatchSize)).size() != 0) {
            LoadManyRequest req = new LoadManyRequest(this, batch, parentRequest.getTransaction(), requestedBatchSize, false, false);
            this.parent.getEbeanServer().loadMany(req);
            if (all) continue;
            break;
        }
    }
}

