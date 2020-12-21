/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.expression;

import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import com.avaje.ebeaninternal.server.expression.AbstractExpression;
import com.avaje.ebeaninternal.server.expression.FilterExprPath;
import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
class NullExpression
extends AbstractExpression {
    private static final long serialVersionUID = 4246991057451128269L;
    private final boolean notNull;

    NullExpression(FilterExprPath pathPrefix, String propertyName, boolean notNull) {
        super(pathPrefix, propertyName);
        this.notNull = notNull;
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
    public void addBindValues(SpiExpressionRequest request) {
    }

    @Override
    public void addSql(SpiExpressionRequest request) {
        String propertyName = this.getPropertyName();
        String nullExpr = this.notNull ? " is not null " : " is null ";
        ElPropertyValue prop = this.getElProp(request);
        if (prop != null && prop.isAssocId()) {
            request.append(prop.getAssocOneIdExpr(propertyName, nullExpr));
            return;
        }
        request.append(propertyName).append(nullExpr);
    }

    @Override
    public int queryAutoFetchHash() {
        int hc = NullExpression.class.getName().hashCode();
        hc = hc * 31 + (this.notNull ? 1 : 0);
        hc = hc * 31 + this.propName.hashCode();
        return hc;
    }

    @Override
    public int queryPlanHash(BeanQueryRequest<?> request) {
        return this.queryAutoFetchHash();
    }

    @Override
    public int queryBindHash() {
        return this.notNull ? 1 : 0;
    }
}

