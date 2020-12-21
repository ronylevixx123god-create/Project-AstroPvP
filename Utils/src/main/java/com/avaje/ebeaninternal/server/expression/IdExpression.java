/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.expression;

import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebeaninternal.api.ManyWhereJoins;
import com.avaje.ebeaninternal.api.SpiExpression;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
import com.avaje.ebeaninternal.util.DefaultExpressionRequest;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
class IdExpression
implements SpiExpression {
    private static final long serialVersionUID = -3065936341718489842L;
    private final Object value;

    IdExpression(Object value) {
        this.value = value;
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
    public void containsMany(BeanDescriptor<?> desc, ManyWhereJoins manyWhereJoin) {
    }

    @Override
    public void addBindValues(SpiExpressionRequest request) {
        DefaultExpressionRequest r = (DefaultExpressionRequest)request;
        Object[] bindIdValues = r.getBeanDescriptor().getBindIdValues(this.value);
        for (int i = 0; i < bindIdValues.length; ++i) {
            request.addBindValue(bindIdValues[i]);
        }
    }

    @Override
    public void addSql(SpiExpressionRequest request) {
        DefaultExpressionRequest r = (DefaultExpressionRequest)request;
        String idSql = r.getBeanDescriptor().getIdBinderIdSql();
        request.append(idSql).append(" ");
    }

    @Override
    public int queryAutoFetchHash() {
        return IdExpression.class.getName().hashCode();
    }

    @Override
    public int queryPlanHash(BeanQueryRequest<?> request) {
        return this.queryAutoFetchHash();
    }

    @Override
    public int queryBindHash() {
        return this.value.hashCode();
    }
}

