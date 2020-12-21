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
import com.avaje.ebeaninternal.server.expression.LogicExpressionLucene;
import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
abstract class LogicExpression
implements SpiExpression {
    private static final long serialVersionUID = 616860781960645251L;
    static final String AND = " and ";
    static final String OR = " or ";
    private final SpiExpression expOne;
    private final SpiExpression expTwo;
    private final String joinType;

    LogicExpression(String joinType, Expression expOne, Expression expTwo) {
        this.joinType = joinType;
        this.expOne = (SpiExpression)expOne;
        this.expTwo = (SpiExpression)expTwo;
    }

    @Override
    public boolean isLuceneResolvable(LuceneResolvableRequest req) {
        return this.expOne.isLuceneResolvable(req) && this.expTwo.isLuceneResolvable(req);
    }

    @Override
    public SpiLuceneExpr createLuceneExpr(SpiExpressionRequest request) {
        return new LogicExpressionLucene().addLuceneQuery(this.joinType, request, this.expOne, this.expTwo);
    }

    @Override
    public void containsMany(BeanDescriptor<?> desc, ManyWhereJoins manyWhereJoin) {
        this.expOne.containsMany(desc, manyWhereJoin);
        this.expTwo.containsMany(desc, manyWhereJoin);
    }

    @Override
    public void addBindValues(SpiExpressionRequest request) {
        this.expOne.addBindValues(request);
        this.expTwo.addBindValues(request);
    }

    @Override
    public void addSql(SpiExpressionRequest request) {
        request.append("(");
        this.expOne.addSql(request);
        request.append(this.joinType);
        this.expTwo.addSql(request);
        request.append(") ");
    }

    @Override
    public int queryAutoFetchHash() {
        int hc = LogicExpression.class.getName().hashCode() + this.joinType.hashCode();
        hc = hc * 31 + this.expOne.queryAutoFetchHash();
        hc = hc * 31 + this.expTwo.queryAutoFetchHash();
        return hc;
    }

    @Override
    public int queryPlanHash(BeanQueryRequest<?> request) {
        int hc = LogicExpression.class.getName().hashCode() + this.joinType.hashCode();
        hc = hc * 31 + this.expOne.queryPlanHash(request);
        hc = hc * 31 + this.expTwo.queryPlanHash(request);
        return hc;
    }

    @Override
    public int queryBindHash() {
        int hc = this.expOne.queryBindHash();
        hc = hc * 31 + this.expTwo.queryBindHash();
        return hc;
    }

    static class Or
    extends LogicExpression {
        private static final long serialVersionUID = -6871993143194094819L;

        Or(Expression expOne, Expression expTwo) {
            super(LogicExpression.OR, expOne, expTwo);
        }
    }

    static class And
    extends LogicExpression {
        private static final long serialVersionUID = -3832889676798526444L;

        And(Expression expOne, Expression expTwo) {
            super(LogicExpression.AND, expOne, expTwo);
        }
    }
}

