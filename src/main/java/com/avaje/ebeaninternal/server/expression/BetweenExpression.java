/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.expression;

import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import com.avaje.ebeaninternal.server.expression.AbstractExpression;
import com.avaje.ebeaninternal.server.expression.FilterExprPath;
import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
class BetweenExpression
extends AbstractExpression {
    private static final long serialVersionUID = 2078918165221454910L;
    private static final String BETWEEN = " between ";
    private final Object valueHigh;
    private final Object valueLow;

    BetweenExpression(FilterExprPath pathPrefix, String propertyName, Object valLo, Object valHigh) {
        super(pathPrefix, propertyName);
        this.valueLow = valLo;
        this.valueHigh = valHigh;
    }

    public boolean isLuceneResolvable(Set<String> indexedProperties) {
        return indexedProperties.contains(this.getPropertyName());
    }

    @Override
    public SpiLuceneExpr createLuceneExpr(SpiExpressionRequest request) {
        return null;
    }

    @Override
    public void addBindValues(SpiExpressionRequest request) {
        request.addBindValue(this.valueLow);
        request.addBindValue(this.valueHigh);
    }

    @Override
    public void addSql(SpiExpressionRequest request) {
        request.append(this.getPropertyName()).append(BETWEEN).append(" ? and ? ");
    }

    @Override
    public int queryAutoFetchHash() {
        int hc = BetweenExpression.class.getName().hashCode();
        hc = hc * 31 + this.propName.hashCode();
        return hc;
    }

    @Override
    public int queryPlanHash(BeanQueryRequest<?> request) {
        return this.queryAutoFetchHash();
    }

    @Override
    public int queryBindHash() {
        int hc = this.valueLow.hashCode();
        hc = hc * 31 + this.valueHigh.hashCode();
        return hc;
    }
}

