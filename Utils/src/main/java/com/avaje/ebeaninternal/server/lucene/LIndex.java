/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.lucene.analysis.Analyzer
 *  org.apache.lucene.document.Document
 *  org.apache.lucene.index.IndexWriter$MaxFieldLength
 *  org.apache.lucene.index.Term
 *  org.apache.lucene.queryParser.QueryParser
 *  org.apache.lucene.queryParser.QueryParser$Operator
 */
package com.avaje.ebeaninternal.server.lucene;

import com.avaje.ebean.bean.EntityBean;
import com.avaje.ebean.config.lucene.LuceneIndex;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebeaninternal.api.TransactionEventTable;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.lucene.DefaultLuceneIndexManager;
import com.avaje.ebeaninternal.server.lucene.DocFieldWriter;
import com.avaje.ebeaninternal.server.lucene.IndexUpdates;
import com.avaje.ebeaninternal.server.lucene.LIndexCommitInfo;
import com.avaje.ebeaninternal.server.lucene.LIndexFieldId;
import com.avaje.ebeaninternal.server.lucene.LIndexFields;
import com.avaje.ebeaninternal.server.lucene.LIndexFileInfo;
import com.avaje.ebeaninternal.server.lucene.LIndexIo;
import com.avaje.ebeaninternal.server.lucene.LIndexSearch;
import com.avaje.ebeaninternal.server.lucene.LIndexSync;
import com.avaje.ebeaninternal.server.lucene.LIndexUpdateFuture;
import com.avaje.ebeaninternal.server.lucene.LIndexVersion;
import com.avaje.ebeaninternal.server.lucene.LIndexWork;
import com.avaje.ebeaninternal.server.lucene.LuceneIndexManager;
import com.avaje.ebeaninternal.server.querydefn.OrmQueryDetail;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class LIndex
implements LuceneIndex {
    private final DefaultLuceneIndexManager manager;
    private final String name;
    private final Analyzer analyzer;
    private final IndexWriter.MaxFieldLength maxFieldLength;
    private final LIndexFields fieldDefn;
    private final BeanDescriptor<?> desc;
    private final OrmQueryDetail ormQueryDetail;
    private final LIndexIo indexIo;
    private final LIndexFieldId idField;
    private final Object syncMonitor = new Object();
    private boolean runningSync;
    private LIndexSync queuedSync;

    public LIndex(DefaultLuceneIndexManager manager, String indexName, String indexDir, Analyzer analyzer, IndexWriter.MaxFieldLength maxFieldLength, BeanDescriptor<?> desc, LIndexFields fieldDefn, String[] updateProps) throws IOException {
        this.manager = manager;
        this.name = desc.getFullName();
        this.analyzer = analyzer;
        this.maxFieldLength = maxFieldLength;
        this.desc = desc;
        this.fieldDefn = fieldDefn;
        this.idField = fieldDefn.getIdField();
        this.ormQueryDetail = fieldDefn.getOrmQueryDetail();
        this.indexIo = new LIndexIo(manager, indexDir, this, updateProps);
        manager.addIndex(this);
        fieldDefn.registerIndexWithProperties(this);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void syncFinished(boolean success) {
        Object object = this.syncMonitor;
        synchronized (object) {
            this.runningSync = false;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void queueSync(String masterHost) {
        Object object = this.syncMonitor;
        synchronized (object) {
            LIndexSync sync = new LIndexSync(this, masterHost);
            if (!this.runningSync) {
                this.runningSync = true;
                this.manager.execute(sync);
            } else {
                this.queuedSync = sync;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void manage(LuceneIndexManager indexManager) {
        Object object = this.syncMonitor;
        synchronized (object) {
            this.indexIo.manage(indexManager);
            if (!this.runningSync && this.queuedSync != null) {
                LIndexSync sync = this.queuedSync;
                this.runningSync = true;
                this.queuedSync = null;
                this.manager.execute(sync);
            }
        }
    }

    public void addNotifyCommitRunnable(Runnable r) {
        this.indexIo.addNotifyCommitRunnable(r);
    }

    public LIndexVersion getLastestVersion() {
        return this.indexIo.getLastestVersion();
    }

    public File getIndexDir() {
        return this.indexIo.getIndexDir();
    }

    public void refresh(boolean nearRealTime) {
        this.indexIo.refresh(nearRealTime);
    }

    public LIndexFileInfo getLocalFile(String fileName) {
        return this.indexIo.getLocalFile(fileName);
    }

    public LIndexCommitInfo obtainLastIndexCommitIfNewer(long remoteIndexVersion) {
        return this.indexIo.obtainLastIndexCommitIfNewer(remoteIndexVersion);
    }

    public void releaseIndexCommit(long remoteIndexVersion) {
        this.indexIo.releaseIndexCommit(remoteIndexVersion);
    }

    public LIndexFileInfo getFile(long remoteIndexVersion, String fileName) {
        return this.indexIo.getFile(remoteIndexVersion, fileName);
    }

    public Term createIdTerm(Object id) {
        return this.idField.createTerm(id);
    }

    public void shutdown() {
        this.indexIo.shutdown();
    }

    @Override
    public LIndexUpdateFuture rebuild() {
        LIndexUpdateFuture future = new LIndexUpdateFuture(this.desc.getBeanType());
        this.indexIo.addWorkToQueue(LIndexWork.newRebuild(future));
        return future;
    }

    @Override
    public LIndexUpdateFuture update() {
        LIndexUpdateFuture future = new LIndexUpdateFuture(this.desc.getBeanType());
        this.indexIo.addWorkToQueue(LIndexWork.newQueryUpdate(future));
        return future;
    }

    public String toString() {
        return this.name;
    }

    public String getName() {
        return this.name;
    }

    public Class<?> getBeanType() {
        return this.desc.getBeanType();
    }

    public BeanDescriptor<?> getBeanDescriptor() {
        return this.desc;
    }

    public LIndexSearch getIndexSearch() {
        return this.indexIo.getIndexSearch();
    }

    public Analyzer getAnalyzer() {
        return this.analyzer;
    }

    public IndexWriter.MaxFieldLength getMaxFieldLength() {
        return this.maxFieldLength;
    }

    public QueryParser createQueryParser(String fieldName) {
        QueryParser p = this.fieldDefn.createQueryParser(fieldName);
        p.setDefaultOperator(QueryParser.Operator.AND);
        return p;
    }

    public LIndexFields getIndexFieldDefn() {
        return this.fieldDefn;
    }

    public Set<String> getResolvePropertyNames() {
        return this.fieldDefn.getResolvePropertyNames();
    }

    public OrmQueryDetail getOrmQueryDetail() {
        return this.ormQueryDetail;
    }

    public Object readDocument(Document doc) {
        EntityBean bean = this.desc.createEntityBean();
        this.fieldDefn.readDocument(doc, bean);
        return bean;
    }

    public DocFieldWriter createDocFieldWriter() {
        return this.fieldDefn.createDocFieldWriter();
    }

    public SpiQuery<?> createQuery() {
        return this.indexIo.createQuery();
    }

    public LIndexUpdateFuture process(IndexUpdates indexUpdates) {
        List<TransactionEventTable.TableIUD> tableList = indexUpdates.getTableList();
        if (tableList != null && tableList.size() > 0) {
            boolean bulkDelete = false;
            for (int i = 0; i < tableList.size(); ++i) {
                TransactionEventTable.TableIUD bulkTableEvent = tableList.get(i);
                if (!bulkTableEvent.isDelete()) continue;
                bulkDelete = true;
            }
            if (bulkDelete) {
                return this.rebuild();
            }
            return this.update();
        }
        if (indexUpdates.isInvalidate()) {
            return this.update();
        }
        LIndexUpdateFuture f = new LIndexUpdateFuture(this.desc.getBeanType());
        this.indexIo.addWorkToQueue(LIndexWork.newTxnUpdate(f, indexUpdates));
        return f;
    }
}

