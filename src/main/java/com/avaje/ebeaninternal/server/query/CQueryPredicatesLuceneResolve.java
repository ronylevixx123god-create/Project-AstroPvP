/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.lucene.search.MatchAllDocsQuery
 *  org.apache.lucene.search.Query
 *  org.apache.lucene.search.Sort
 */
package com.avaje.ebeaninternal.server.query;

import com.avaje.ebean.OrderBy;
import com.avaje.ebeaninternal.api.BindParams;
import com.avaje.ebeaninternal.api.SpiExpressionList;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebeaninternal.server.core.LuceneOrmQueryRequest;
import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
import com.avaje.ebeaninternal.server.expression.PersistenceLuceneParseException;
import com.avaje.ebeaninternal.server.lucene.LIndex;
import com.avaje.ebeaninternal.server.lucene.LLuceneSortResolve;
import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
import com.avaje.ebeaninternal.util.DefaultExpressionRequest;
import java.util.logging.Logger;
import javax.persistence.PersistenceException;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class CQueryPredicatesLuceneResolve {
    private static final Logger logger = Logger.getLogger(CQueryPredicatesLuceneResolve.class.getName());
    private final OrmQueryRequest<?> request;
    private final SpiQuery<?> query;
    private final BindParams bindParams;

    public CQueryPredicatesLuceneResolve(OrmQueryRequest<?> request) {
        this.request = request;
        this.query = request.getQuery();
        this.bindParams = this.query.getBindParams();
    }

    public boolean isLuceneResolvable() {
        LIndex luceneIndex = this.request.getLuceneIndex();
        if (luceneIndex == null) {
            return false;
        }
        if (this.bindParams != null) {
            return false;
        }
        if (this.query.getHavingExpressions() != null) {
            return false;
        }
        LuceneResolvableRequest req = new LuceneResolvableRequest(this.request.getBeanDescriptor(), luceneIndex);
        LLuceneSortResolve lucenSortResolve = new LLuceneSortResolve(req, this.query.getOrderBy());
        if (!lucenSortResolve.isResolved()) {
            logger.info("Lucene Index can't support sort/orderBy of [" + lucenSortResolve.getUnsortableField() + "]");
            return false;
        }
        Sort luceneSort = lucenSortResolve.getSort();
        OrderBy<?> orderBy = this.query.getOrderBy();
        String sortDesc = orderBy == null ? "" : orderBy.toStringFormat();
        SpiExpressionList<?> whereExp = this.query.getWhereExpressions();
        if (whereExp == null) {
            MatchAllDocsQuery q = new MatchAllDocsQuery();
            this.request.setLuceneOrmQueryRequest(new LuceneOrmQueryRequest((Query)q, luceneSort, "MatchAllDocs", sortDesc));
            return true;
        }
        if (!whereExp.isLuceneResolvable(req)) {
            return false;
        }
        try {
            DefaultExpressionRequest whereReq = new DefaultExpressionRequest(this.request, luceneIndex);
            SpiLuceneExpr luceneExpr = whereExp.createLuceneExpr(whereReq, SpiLuceneExpr.ExprOccur.MUST);
            Query luceneQuery = luceneExpr.mergeLuceneQuery();
            String luceneDesc = luceneExpr.getDescription();
            this.request.setLuceneOrmQueryRequest(new LuceneOrmQueryRequest(luceneQuery, luceneSort, luceneDesc, sortDesc));
            return true;
        }
        catch (PersistenceLuceneParseException e) {
            String msg = "Failed to parse the Query using Lucene";
            throw new PersistenceException(msg, e);
        }
    }
}

