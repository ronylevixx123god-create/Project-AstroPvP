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
import java.util.Collection;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
class InExpression
extends AbstractExpression {
    private static final long serialVersionUID = 3150665801693551260L;
    private final Object[] values;

    InExpression(FilterExprPath pathPrefix, String propertyName, Collection<?> coll) {
        super(pathPrefix, propertyName);
        this.values = coll.toArray(new Object[coll.size()]);
    }

    InExpression(FilterExprPath pathPrefix, String propertyName, Object[] array) {
        super(pathPrefix, propertyName);
        this.values = array;
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
        ElPropertyValue prop = this.getElProp(request);
        if (prop != null && !prop.isAssocId()) {
            prop = null;
        }
        for (int i = 0; i < this.values.length; ++i) {
            if (prop == null) {
                request.addBindValue(this.values[i]);
                continue;
            }
            Object[] ids = prop.getAssocOneIdValues(this.values[i]);
            if (ids == null) continue;
            for (int j = 0; j < ids.length; ++j) {
                request.addBindValue(ids[j]);
            }
        }
    }

    @Override
    public void addSql(SpiExpressionRequest request) {
        if (this.values.length == 0) {
            request.append("1=0");
            return;
        }
        String propertyName = this.getPropertyName();
        ElPropertyValue prop = this.getElProp(request);
        if (prop != null && !prop.isAssocId()) {
            prop = null;
        }
        if (prop != null) {
            request.append(prop.getAssocIdInExpr(propertyName));
            String inClause = prop.getAssocIdInValueExpr(this.values.length);
            request.append(inClause);
        } else {
            request.append(propertyName);
            request.append(" in (?");
            for (int i = 1; i < this.values.length; ++i) {
                request.append(", ").append("?");
            }
            request.append(" ) ");
        }
    }

    @Override
    public int queryAutoFetchHash() {
        int hc = InExpression.class.getName().hashCode() + 31 * this.values.length;
        hc = hc * 31 + this.propName.hashCode();
        return hc;
    }

    @Override
    public int queryPlanHash(BeanQueryRequest<?> request) {
        return this.queryAutoFetchHash();
    }

    @Override
    public int queryBindHash() {
        int hc = 0;
        for (int i = 1; i < this.values.length; ++i) {
            hc = 31 * hc + this.values[i].hashCode();
        }
        return hc;
    }
}

