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

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
class RawExpression
implements SpiExpression {
    private static final long serialVersionUID = 7973903141340334606L;
    private final String sql;
    private final Object[] values;

    RawExpression(String sql, Object[] values) {
        this.sql = sql;
        this.values = values;
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
        if (this.values != null) {
            for (int i = 0; i < this.values.length; ++i) {
                request.addBindValue(this.values[i]);
            }
        }
    }

    @Override
    public void addSql(SpiExpressionRequest request) {
        request.append(this.sql);
    }

    @Override
    public int queryAutoFetchHash() {
        int hc = RawExpression.class.getName().hashCode();
        hc = hc * 31 + this.sql.hashCode();
        return hc;
    }

    @Override
    public int queryPlanHash(BeanQueryRequest<?> request) {
        return this.queryAutoFetchHash();
    }

    @Override
    public int queryBindHash() {
        return this.sql.hashCode();
    }
}

