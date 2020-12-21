/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.lucene.analysis.Analyzer
 *  org.apache.lucene.document.Document
 *  org.apache.lucene.index.IndexWriter
 *  org.apache.lucene.index.Term
 *  org.apache.lucene.search.BooleanClause$Occur
 *  org.apache.lucene.search.BooleanQuery
 *  org.apache.lucene.search.IndexSearcher
 *  org.apache.lucene.search.Query
 *  org.apache.lucene.search.ScoreDoc
 *  org.apache.lucene.search.TermQuery
 *  org.apache.lucene.search.TopDocs
 */
package com.avaje.ebeaninternal.server.lucene;

import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.lucene.DocFieldWriter;
import com.avaje.ebeaninternal.server.lucene.IndexUpdates;
import com.avaje.ebeaninternal.server.lucene.LIndex;
import com.avaje.ebeaninternal.server.lucene.LIndexSearch;
import com.avaje.ebeaninternal.server.lucene.PersistenceLuceneException;
import com.avaje.ebeaninternal.server.transaction.BeanDelta;
import com.avaje.ebeaninternal.server.transaction.BeanDeltaList;
import com.avaje.ebeaninternal.server.transaction.BeanPersistIds;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.PersistenceException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class LIndexDeltaHandler {
    private static final Logger logger = Logger.getLogger(LIndexDeltaHandler.class.getName());
    private final LIndex index;
    private final LIndexSearch search;
    private final IndexSearcher searcher;
    private final IndexWriter indexWriter;
    private final Analyzer analyzer;
    private final BeanDescriptor<?> beanDescriptor;
    private final DocFieldWriter docFieldWriter;
    private final IndexUpdates indexUpdates;
    private final List<BeanDelta> deltaBeans;
    private final Document document = new Document();
    private Set<Object> deltaBeanKeys;
    private int deltaCount;
    private int insertCount;
    private int updateCount;
    private int deleteCount;
    private int deleteByIdCount;

    public LIndexDeltaHandler(LIndex index, LIndexSearch search, IndexWriter indexWriter, Analyzer analyzer, BeanDescriptor<?> beanDescriptor, DocFieldWriter docFieldWriter, IndexUpdates indexUpdates) {
        this.index = index;
        this.search = search;
        this.searcher = search.getIndexSearcher();
        this.indexWriter = indexWriter;
        this.analyzer = analyzer;
        this.beanDescriptor = beanDescriptor;
        this.docFieldWriter = docFieldWriter;
        this.indexUpdates = indexUpdates;
        BeanDeltaList deltaList = indexUpdates.getDeltaList();
        this.deltaBeans = deltaList == null ? null : deltaList.getDeltaBeans();
    }

    public int process() {
        BeanPersistIds beanPersistIds;
        this.deltaBeanKeys = this.processDeltaBeans();
        this.deltaCount = this.deltaBeanKeys.size();
        BeanPersistIds deleteById = this.indexUpdates.getDeleteIds();
        if (deleteById != null) {
            this.deleteByIdCount = this.processDeletes(deleteById.getDeleteIds());
        }
        if ((beanPersistIds = this.indexUpdates.getBeanPersistIds()) != null) {
            this.deleteCount = this.processDeletes(beanPersistIds.getDeleteIds());
            this.processInserts(beanPersistIds.getInsertIds());
            this.processUpdates(beanPersistIds.getUpdateIds());
        }
        String msg = String.format("Lucene update index %s deltas[%s] insert[%s] update[%s] delete[%s]", this.index, this.deltaCount, this.insertCount, this.updateCount, this.deleteCount + this.deleteByIdCount);
        logger.info(msg);
        return this.deltaCount + this.insertCount + this.updateCount + this.deleteCount + this.deleteByIdCount;
    }

    private void processUpdates(List<Serializable> updateIds) {
        if (updateIds == null || updateIds.isEmpty()) {
            return;
        }
        ArrayList<Serializable> filterIdList = new ArrayList<Serializable>();
        for (int i = 0; i < updateIds.size(); ++i) {
            Serializable id = updateIds.get(i);
            if (this.deltaBeanKeys.contains(id)) continue;
            filterIdList.add(id);
        }
        if (!filterIdList.isEmpty()) {
            SpiQuery<?> ormQuery = this.index.createQuery();
            ormQuery.where().idIn(filterIdList);
            List list = ormQuery.findList();
            for (int i = 0; i < list.size(); ++i) {
                Object bean = list.get(i);
                try {
                    Object id = this.beanDescriptor.getId(bean);
                    Term term = this.index.createIdTerm(id);
                    this.docFieldWriter.writeValue(bean, this.document);
                    this.indexWriter.updateDocument(term, this.document);
                    continue;
                }
                catch (Exception e) {
                    throw new PersistenceException(e);
                }
            }
            this.updateCount = list.size();
        }
    }

    private void processInserts(List<Serializable> insertIds) {
        if (insertIds == null || insertIds.isEmpty()) {
            return;
        }
        SpiQuery<?> ormQuery = this.index.createQuery();
        ormQuery.where().idIn(insertIds);
        List list = ormQuery.findList();
        for (int i = 0; i < list.size(); ++i) {
            Object bean = list.get(i);
            try {
                this.docFieldWriter.writeValue(bean, this.document);
                this.indexWriter.addDocument(this.document);
                continue;
            }
            catch (Exception e) {
                throw new PersistenceException(e);
            }
        }
        this.insertCount = list.size();
    }

    private int processDeletes(List<Serializable> deleteIds) {
        if (deleteIds == null || deleteIds.isEmpty()) {
            return 0;
        }
        for (int i = 0; i < deleteIds.size(); ++i) {
            Serializable id = deleteIds.get(i);
            Term term = this.index.createIdTerm(id);
            try {
                this.indexWriter.deleteDocuments(term);
                continue;
            }
            catch (Exception e) {
                throw new PersistenceLuceneException(e);
            }
        }
        return deleteIds.size();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Set<Object> processDeltaBeans() {
        if (this.deltaBeans == null) {
            return Collections.emptySet();
        }
        try {
            LinkedHashMap<Object, Object> beanMap = this.getBeans();
            for (int i = 0; i < this.deltaBeans.size(); ++i) {
                BeanDelta deltaBean = this.deltaBeans.get(i);
                Object id = deltaBean.getId();
                Object bean = beanMap.get(id);
                if (bean == null) {
                    throw new PersistenceLuceneException("Unmatched bean " + deltaBean.getId());
                }
                deltaBean.apply(bean);
                this.docFieldWriter.writeValue(bean, this.document);
                try {
                    Term term = this.index.createIdTerm(id);
                    this.indexWriter.updateDocument(term, this.document, this.analyzer);
                    continue;
                }
                catch (Exception e) {
                    throw new PersistenceLuceneException(e);
                }
            }
            Set<Object> set = beanMap.keySet();
            return set;
        }
        finally {
            this.closeResources();
        }
    }

    private void closeResources() {
        try {
            this.search.releaseClose();
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, "Error with IndexReader decRef()", e);
        }
    }

    private LinkedHashMap<Object, Object> getBeans() {
        Query query = this.createQuery();
        LinkedHashMap<Object, Object> beanMap = new LinkedHashMap<Object, Object>();
        try {
            TopDocs topDocs = this.searcher.search(query, this.deltaBeans.size() * 2);
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            for (int i = 0; i < scoreDocs.length; ++i) {
                int doc = scoreDocs[i].doc;
                Document document = this.searcher.doc(doc);
                Object bean = this.index.readDocument(document);
                Object id = this.beanDescriptor.getId(bean);
                beanMap.put(id, bean);
            }
            return beanMap;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Query createQuery() {
        BooleanQuery b = new BooleanQuery();
        for (int i = 0; i < this.deltaBeans.size(); ++i) {
            BeanDelta d = this.deltaBeans.get(i);
            Object id = d.getId();
            Term term = this.index.createIdTerm(id);
            TermQuery tq = new TermQuery(term);
            b.add((Query)tq, BooleanClause.Occur.SHOULD);
        }
        return b;
    }
}

