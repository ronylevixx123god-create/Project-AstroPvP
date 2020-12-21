/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.expression;

import com.avaje.ebean.Expression;
import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebeaninternal.api.ManyWhereJoins;
import com.avaje.ebeaninternal.api.SpiExpression;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.expression.LuceneAwareExpression;
import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
final class NotExpression
implements SpiExpression,
LuceneAwareExpression {
    private static final long serialVersionUID = 5648926732402355781L;
    private static final String NOT = "not (";
    private final SpiExpression exp;

    NotExpression(Expression exp) {
        this.exp = (SpiExpression)exp;
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
        this.exp.containsMany(desc, manyWhereJoin);
    }

    @Override
    public void addBindValues(SpiExpressionRequest request) {
        this.exp.addBindValues(request);
    }

    @Override
    public void addSql(SpiExpressionRequest request) {
        request.append(NOT);
        this.exp.addSql(request);
        request.append(") ");
    }

    @Override
    public int queryAutoFetchHash() {
        int hc = NotExpression.class.getName().hashCode();
        hc = hc * 31 + this.exp.queryAutoFetchHash();
        return hc;
    }

    @Override
    public int queryPlanHash(BeanQueryRequest<?> request) {
        int hc = NotExpression.class.getName().hashCode();
        hc = hc * 31 + this.exp.queryPlanHash(request);
        return hc;
    }

    @Override
    public int queryBindHash() {
        return this.exp.queryBindHash();
    }
}

