/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.lucene.analysis.Analyzer
 *  org.apache.lucene.document.Document
 *  org.apache.lucene.index.IndexDeletionPolicy
 *  org.apache.lucene.index.IndexWriter
 *  org.apache.lucene.index.IndexWriter$MaxFieldLength
 *  org.apache.lucene.index.Term
 *  org.apache.lucene.store.Directory
 *  org.apache.lucene.store.FSDirectory
 */
package com.avaje.ebeaninternal.server.lucene;

import com.avaje.ebean.Junction;
import com.avaje.ebean.Query;
import com.avaje.ebean.QueryListener;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.lucene.DocFieldWriter;
import com.avaje.ebeaninternal.server.lucene.HoldAwareIndexDeletionPolicy;
import com.avaje.ebeaninternal.server.lucene.IndexUpdates;
import com.avaje.ebeaninternal.server.lucene.LIndex;
import com.avaje.ebeaninternal.server.lucene.LIndexCommitInfo;
import com.avaje.ebeaninternal.server.lucene.LIndexDeltaHandler;
import com.avaje.ebeaninternal.server.lucene.LIndexFileInfo;
import com.avaje.ebeaninternal.server.lucene.LIndexIoSearcher;
import com.avaje.ebeaninternal.server.lucene.LIndexIoSearcherDefault;
import com.avaje.ebeaninternal.server.lucene.LIndexSearch;
import com.avaje.ebeaninternal.server.lucene.LIndexVersion;
import com.avaje.ebeaninternal.server.lucene.LIndexWork;
import com.avaje.ebeaninternal.server.lucene.LuceneIndexManager;
import com.avaje.ebeaninternal.server.lucene.PersistenceLuceneException;
import com.avaje.ebeaninternal.server.querydefn.OrmQueryDetail;
import com.avaje.ebeaninternal.server.transaction.IndexEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.PersistenceException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexDeletionPolicy;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class LIndexIo {
    private static final Logger logger = Logger.getLogger(LIndexIo.class.getName());
    private final LuceneIndexManager manager;
    private final String indexDir;
    private final LIndex index;
    private final Analyzer analyzer;
    private final IndexWriter.MaxFieldLength maxFieldLength;
    private final Class<?> beanType;
    private final OrmQueryDetail ormQueryDetail;
    private final Directory directory;
    private final BeanDescriptor<?> beanDescriptor;
    private final IndexWriter indexWriter;
    private final LIndexIoSearcher ioSearcher;
    private final HoldAwareIndexDeletionPolicy commitDeletionPolicy;
    private final String[] updateProps;
    private final Object writeMonitor = new Object();
    private final Object workQueueMonitor = new Object();
    private final ArrayList<LIndexWork> workQueue = new ArrayList();
    private final ArrayList<Runnable> notifyCommitRunnables = new ArrayList();
    private long lastUpdateTime;
    private long queueCommitStart;
    private int queueCommitCount;
    private int totalCommitCount;
    private long totalCommitNanos;
    private long totalPostCommitNanos;

    public LIndexIo(LuceneIndexManager manager, String indexDir, LIndex index, String[] updateProps) throws IOException {
        this.manager = manager;
        this.indexDir = indexDir;
        this.index = index;
        this.updateProps = updateProps;
        this.analyzer = index.getAnalyzer();
        this.maxFieldLength = index.getMaxFieldLength();
        this.beanType = index.getBeanType();
        this.ormQueryDetail = index.getOrmQueryDetail();
        this.directory = this.createDirectory();
        this.beanDescriptor = index.getBeanDescriptor();
        this.commitDeletionPolicy = new HoldAwareIndexDeletionPolicy(indexDir);
        this.indexWriter = this.createIndexWriter();
        this.ioSearcher = this.createIoSearcher();
    }

    public LIndexVersion getLastestVersion() {
        return this.ioSearcher.getLastestVersion();
    }

    public long getLastVersion() {
        return this.commitDeletionPolicy.getLastVersion();
    }

    public LIndexCommitInfo obtainLastIndexCommitIfNewer(long remoteIndexVersion) {
        return this.commitDeletionPolicy.obtainLastIndexCommitIfNewer(remoteIndexVersion);
    }

    public File getIndexDir() {
        return new File(this.indexDir);
    }

    public LIndexFileInfo getLocalFile(String fileName) {
        File f = new File(this.indexDir, fileName);
        return new LIndexFileInfo(f);
    }

    public void refresh(boolean nearRealTime) {
        this.ioSearcher.refresh(nearRealTime);
    }

    public LIndexFileInfo getFile(long remoteIndexVersion, String fileName) {
        this.commitDeletionPolicy.touch(remoteIndexVersion);
        File f = new File(this.indexDir, fileName);
        return new LIndexFileInfo(f);
    }

    public void releaseIndexCommit(long remoteIndexVersion) {
        this.commitDeletionPolicy.releaseIndexCommit(remoteIndexVersion);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void shutdown() {
        Object object = this.writeMonitor;
        synchronized (object) {
            String msg;
            try {
                if (this.queueCommitStart > 0L) {
                    this.indexWriter.commit();
                }
            }
            catch (Exception e) {
                msg = "Error committing queued changes for IndexWriter for " + this.indexDir;
                logger.log(Level.SEVERE, msg, e);
                e.printStackTrace();
            }
            finally {
                try {
                    this.indexWriter.close();
                }
                catch (Exception e) {
                    msg = "Error closing IndexWriter for " + this.indexDir;
                    logger.log(Level.SEVERE, msg, e);
                    e.printStackTrace();
                }
            }
        }
    }

    protected void manage(LuceneIndexManager indexManager) {
        this.processWorkQueue();
        this.commit(false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void addWorkToQueue(LIndexWork work) {
        Object object = this.workQueueMonitor;
        synchronized (object) {
            this.workQueue.add(work);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void processWorkQueue() {
        Object object = this.workQueueMonitor;
        synchronized (object) {
            if (!this.workQueue.isEmpty()) {
                Enum maxWorkType = null;
                for (int i = 0; i < this.workQueue.size(); ++i) {
                    LIndexWork work = this.workQueue.get(i);
                    if (maxWorkType != null && maxWorkType.ordinal() >= work.getWorkType().ordinal()) continue;
                    maxWorkType = work.getWorkType();
                }
                List workQueueClone = (List)this.workQueue.clone();
                this.workQueue.clear();
                Callable<Integer> workCallable = this.getWorkCallable((LIndexWork.WorkType)maxWorkType, workQueueClone);
                FutureTask<Integer> ft = new FutureTask<Integer>(workCallable);
                for (int i = 0; i < workQueueClone.size(); ++i) {
                    ((LIndexWork)workQueueClone.get(i)).getFuture().setTask(ft);
                }
                this.manager.getServer().getBackgroundExecutor().execute(ft);
            }
        }
    }

    private Callable<Integer> getWorkCallable(LIndexWork.WorkType maxWorkType, List<LIndexWork> workQueueClone) {
        switch (maxWorkType) {
            case REBUILD: {
                return this.newRebuildCallable(workQueueClone);
            }
            case QUERY_UPDATE: {
                return this.newQueryUpdateCallable(workQueueClone);
            }
            case TXN_UPDATE: {
                return this.newTxnUpdateCallable(workQueueClone);
            }
        }
        throw new IllegalStateException("Unknown workType " + (Object)((Object)maxWorkType));
    }

    private Callable<Integer> newTxnUpdateCallable(List<LIndexWork> workQueueClone) {
        final List<LIndexWork> updates = workQueueClone;
        return new Callable<Integer>(){

            public String toString() {
                return "TxnUpdate";
            }

            @Override
            public Integer call() throws IOException {
                int totalDocs = 0;
                for (int i = 0; i < updates.size(); ++i) {
                    LIndexWork lIndexWork = (LIndexWork)updates.get(i);
                    IndexUpdates indexUpdates = lIndexWork.getIndexUpdates();
                    LIndexDeltaHandler h = LIndexIo.this.createDeltaHandler(indexUpdates);
                    totalDocs += h.process();
                }
                LIndexIo.this.queueCommit(updates);
                return totalDocs;
            }
        };
    }

    private Callable<Integer> newRebuildCallable(List<LIndexWork> workQueueClone) {
        return new QueryUpdater(true, workQueueClone);
    }

    private Callable<Integer> newQueryUpdateCallable(List<LIndexWork> workQueueClone) {
        return new QueryUpdater(false, workQueueClone);
    }

    private LIndexDeltaHandler createDeltaHandler(IndexUpdates indexUpdates) {
        LIndexSearch search = this.getIndexSearch();
        IndexWriter indexWriter = this.indexWriter;
        DocFieldWriter docFieldWriter = this.index.createDocFieldWriter();
        return new LIndexDeltaHandler(this.index, search, indexWriter, this.analyzer, this.beanDescriptor, docFieldWriter, indexUpdates);
    }

    public LIndexSearch getIndexSearch() {
        return this.ioSearcher.getIndexSearch();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void queueCommit(List<LIndexWork> workQueueClone) {
        Object object = this.workQueueMonitor;
        synchronized (object) {
            if (this.queueCommitStart == 0L) {
                this.queueCommitStart = System.currentTimeMillis();
            }
            ++this.queueCommitCount;
            for (LIndexWork w : workQueueClone) {
                this.notifyCommitRunnables.add(w.getFuture().getCommitRunnable());
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void addNotifyCommitRunnable(Runnable r) {
        Object object = this.workQueueMonitor;
        synchronized (object) {
            this.notifyCommitRunnables.add(r);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected long getQueueCommitStart(boolean reset) {
        Object object = this.workQueueMonitor;
        synchronized (object) {
            long start = this.queueCommitStart;
            if (reset) {
                this.queueCommitStart = 0L;
                this.queueCommitCount = 0;
            }
            return start;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean commit(boolean force) {
        Object object = this.writeMonitor;
        synchronized (object) {
            long start = 0L;
            long count = 0L;
            ArrayList<Runnable> notifyRunnables = new ArrayList<Runnable>();
            Object object2 = this.workQueueMonitor;
            synchronized (object2) {
                start = this.queueCommitStart;
                count = this.queueCommitCount;
                this.queueCommitStart = 0L;
                this.queueCommitCount = 0;
                notifyRunnables.addAll(this.notifyCommitRunnables);
                this.notifyCommitRunnables.clear();
            }
            try {
                if (!force && start == 0L) {
                    if (!notifyRunnables.isEmpty()) {
                        for (int i = 0; i < notifyRunnables.size(); ++i) {
                            this.addNotifyCommitRunnable((Runnable)notifyRunnables.get(i));
                        }
                    }
                    return false;
                }
                if (logger.isLoggable(Level.INFO)) {
                    String delayMsg;
                    if (this.queueCommitStart > 0L) {
                        long delay = System.currentTimeMillis() - start;
                        delayMsg = " queueDelayMillis:" + delay + " queueCount:" + count;
                    } else {
                        delayMsg = "";
                    }
                    String m = "Lucene commit " + this.indexDir + delayMsg;
                    logger.info(m);
                }
                long nanoStart = System.nanoTime();
                this.indexWriter.commit();
                long nanoCommit = System.nanoTime();
                long nanoCommitExe = nanoCommit - nanoStart;
                this.ioSearcher.postCommit();
                for (int i = 0; i < notifyRunnables.size(); ++i) {
                    ((Runnable)notifyRunnables.get(i)).run();
                }
                long nanoPostCommitExe = System.nanoTime() - nanoCommitExe;
                ++this.totalCommitCount;
                this.totalCommitNanos += nanoCommitExe;
                this.totalPostCommitNanos += nanoPostCommitExe;
                IndexEvent indexEvent = new IndexEvent(1, this.index.getName());
                this.manager.notifyCluster(indexEvent);
                return true;
            }
            catch (IOException e) {
                String msg = "Error committing changes on index " + this.indexDir;
                throw new PersistenceLuceneException(msg, e);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private int rebuildIndex(List<LIndexWork> workQueueClone) throws IOException {
        Object object = this.writeMonitor;
        synchronized (object) {
            int n;
            logger.info("Lucene rebuild " + this.indexDir);
            try {
                this.indexWriter.deleteAll();
                this.lastUpdateTime = System.currentTimeMillis();
                SpiQuery<?> query = this.createQuery();
                WriteListener writeListener = new WriteListener(this.index, this.indexWriter, false);
                query.setListener(writeListener);
                this.manager.getServer().findList(query, null);
                n = writeListener.getCount();
                this.queueCommit(workQueueClone);
                this.commit(false);
            }
            catch (Throwable throwable) {
                this.queueCommit(workQueueClone);
                this.commit(false);
                throw throwable;
            }
            return n;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private int updateIndex(List<LIndexWork> workQueueClone) throws IOException {
        Object object = this.writeMonitor;
        synchronized (object) {
            int n;
            logger.info("Lucene update " + this.indexDir);
            try {
                long updateTime = System.currentTimeMillis();
                SpiQuery<?> query = this.createUpdateQuery();
                this.lastUpdateTime = updateTime;
                WriteListener writeListener = new WriteListener(this.index, this.indexWriter, true);
                query.setListener(writeListener);
                this.manager.getServer().findList(query, null);
                n = writeListener.getCount();
                this.queueCommit(workQueueClone);
            }
            catch (Throwable throwable) {
                this.queueCommit(workQueueClone);
                throw throwable;
            }
            return n;
        }
    }

    private SpiQuery<?> createUpdateQuery() {
        SpiQuery<?> q = this.createQuery();
        Junction disjunction = q.where().disjunction();
        Timestamp lastUpdate = new Timestamp(this.lastUpdateTime);
        for (int i = 0; i < this.updateProps.length; ++i) {
            disjunction.ge(this.updateProps[i], lastUpdate);
        }
        return q;
    }

    protected SpiQuery<?> createQuery() {
        SpiEbeanServer server = this.manager.getServer();
        SpiQuery query = (SpiQuery)server.createQuery(this.beanType);
        query.setUseIndex(Query.UseIndex.NO);
        query.getDetail().tuneFetchProperties(this.ormQueryDetail);
        return query;
    }

    private Directory createDirectory() throws IOException {
        File dir = new File(this.indexDir);
        return FSDirectory.open((File)dir);
    }

    private IndexWriter createIndexWriter() {
        try {
            boolean create = true;
            return new IndexWriter(this.directory, this.analyzer, create, (IndexDeletionPolicy)this.commitDeletionPolicy, this.maxFieldLength);
        }
        catch (IOException e) {
            String msg = "Error getting Lucene IndexWriter for " + this.indexDir;
            throw new PersistenceLuceneException(msg, e);
        }
    }

    private LIndexIoSearcher createIoSearcher() {
        return new LIndexIoSearcherDefault(this.indexWriter, this.index.getName());
    }

    private static class WriteListener
    implements QueryListener {
        private final boolean updateMode;
        private final LIndex index;
        private final BeanDescriptor beanDescriptor;
        private final IndexWriter indexWriter;
        private final DocFieldWriter docFieldWriter;
        private final Document document = new Document();
        private int count;

        private WriteListener(LIndex index, IndexWriter indexWriter, boolean updateMode) {
            this.updateMode = updateMode;
            this.index = index;
            this.beanDescriptor = index.getBeanDescriptor();
            this.indexWriter = indexWriter;
            this.docFieldWriter = index.createDocFieldWriter();
        }

        public void process(Object bean) {
            try {
                if (this.updateMode) {
                    Object id = this.beanDescriptor.getId(bean);
                    Term term = this.index.createIdTerm(id);
                    this.indexWriter.deleteDocuments(term);
                }
                this.docFieldWriter.writeValue(bean, this.document);
                this.indexWriter.addDocument(this.document);
                ++this.count;
            }
            catch (Exception e) {
                throw new PersistenceException(e);
            }
        }

        public int getCount() {
            return this.count;
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    class QueryUpdater
    implements Callable<Integer> {
        private final boolean rebuild;
        private final List<LIndexWork> workQueueClone;

        private QueryUpdater(boolean rebuild, List<LIndexWork> workQueueClone) {
            this.rebuild = rebuild;
            this.workQueueClone = workQueueClone;
        }

        public String toString() {
            return this.rebuild ? "Rebuild" : "QueryUpdate";
        }

        @Override
        public Integer call() throws Exception {
            if (this.rebuild) {
                return LIndexIo.this.rebuildIndex(this.workQueueClone);
            }
            return LIndexIo.this.updateIndex(this.workQueueClone);
        }
    }
}

