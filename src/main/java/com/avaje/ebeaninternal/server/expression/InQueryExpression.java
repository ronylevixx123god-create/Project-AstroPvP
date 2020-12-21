/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.expression;

import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebeaninternal.server.expression.AbstractExpression;
import com.avaje.ebeaninternal.server.expression.FilterExprPath;
import com.avaje.ebeaninternal.server.query.CQuery;
import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
import java.util.ArrayList;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
class InQueryExpression
extends AbstractExpression {
    private static final long serialVersionUID = 666990277309851644L;
    private final SpiQuery<?> subQuery;
    private transient CQuery<?> compiledSubQuery;

    public InQueryExpression(FilterExprPath pathPrefix, String propertyName, SpiQuery<?> subQuery) {
        super(pathPrefix, propertyName);
        this.subQuery = subQuery;
    }

    @Override
    public boolean isLuceneResolvable(LuceneResolvableRequest req) {
        return false;
    }

    @Override
    public SpiLuceneExpr createLuceneExpr(SpiExpressionRequest request) {
        return null;
    }

    @Override
    public int queryAutoFetchHash() {
        int hc = InQueryExpression.class.getName().hashCode();
        hc = hc * 31 + this.propName.hashCode();
        hc = hc * 31 + this.subQuery.queryAutofetchHash();
        return hc;
    }

    @Override
    public int queryPlanHash(BeanQueryRequest<?> request) {
        this.compiledSubQuery = this.compileSubQuery(request);
        int hc = InQueryExpression.class.getName().hashCode();
        hc = hc * 31 + this.propName.hashCode();
        hc = hc * 31 + this.subQuery.queryPlanHash(request);
        return hc;
    }

    private CQuery<?> compileSubQuery(BeanQueryRequest<?> queryRequest) {
        SpiEbeanServer ebeanServer = (SpiEbeanServer)queryRequest.getEbeanServer();
        return ebeanServer.compileQuery(this.subQuery, queryRequest.getTransaction());
    }

    @Override
    public int queryBindHash() {
        return this.subQuery.queryBindHash();
    }

    @Override
    public void addSql(SpiExpressionRequest request) {
        String subSelect = this.compiledSubQuery.getGeneratedSql();
        subSelect = subSelect.replace('\n', ' ');
        String propertyName = this.getPropertyName();
        request.append(" (");
        request.append(propertyName);
        request.append(") in (");
        request.append(subSelect);
        request.append(") ");
    }

    @Override
    public void addBindValues(SpiExpressionRequest request) {
        ArrayList<Object> bindParams = this.compiledSubQuery.getPredicates().getWhereExprBindValues();
        if (bindParams == null) {
            return;
        }
        for (int i = 0; i < bindParams.size(); ++i) {
            request.addBindValue(bindParams.get(i));
        }
    }
}

