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
import com.avaje.ebeaninternal.server.el.ElPropertyDeploy;
import com.avaje.ebeaninternal.server.expression.FilterExprPath;
import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
class BetweenPropertyExpression
implements SpiExpression {
    private static final long serialVersionUID = 2078918165221454910L;
    private static final String BETWEEN = " between ";
    private final FilterExprPath pathPrefix;
    private final String lowProperty;
    private final String highProperty;
    private final Object value;

    BetweenPropertyExpression(FilterExprPath pathPrefix, String lowProperty, String highProperty, Object value) {
        this.pathPrefix = pathPrefix;
        this.lowProperty = lowProperty;
        this.highProperty = highProperty;
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

    protected String name(String propName) {
        if (this.pathPrefix == null) {
            return propName;
        }
        String path = this.pathPrefix.getPath();
        if (path == null || path.length() == 0) {
            return propName;
        }
        return path + "." + propName;
    }

    @Override
    public void containsMany(BeanDescriptor<?> desc, ManyWhereJoins manyWhereJoin) {
        ElPropertyDeploy elProp = desc.getElPropertyDeploy(this.name(this.lowProperty));
        if (elProp != null && elProp.containsMany()) {
            manyWhereJoin.add(elProp);
        }
        if ((elProp = desc.getElPropertyDeploy(this.name(this.highProperty))) != null && elProp.containsMany()) {
            manyWhereJoin.add(elProp);
        }
    }

    @Override
    public void addBindValues(SpiExpressionRequest request) {
        request.addBindValue(this.value);
    }

    @Override
    public void addSql(SpiExpressionRequest request) {
        request.append(" ? ").append(BETWEEN).append(this.name(this.lowProperty)).append(" and ").append(this.name(this.highProperty));
    }

    @Override
    public int queryAutoFetchHash() {
        int hc = BetweenPropertyExpression.class.getName().hashCode();
        hc = hc * 31 + this.lowProperty.hashCode();
        hc = hc * 31 + this.highProperty.hashCode();
        return hc;
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

