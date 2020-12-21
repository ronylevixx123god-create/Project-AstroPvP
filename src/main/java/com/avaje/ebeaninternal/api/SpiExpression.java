/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.api;

import com.avaje.ebean.Expression;
import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebeaninternal.api.ManyWhereJoins;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface SpiExpression
extends Expression {
    public boolean isLuceneResolvable(LuceneResolvableRequest var1);

    public SpiLuceneExpr createLuceneExpr(SpiExpressionRequest var1);

    public void containsMany(BeanDescriptor<?> var1, ManyWhereJoins var2);

    public int queryAutoFetchHash();

    public int queryPlanHash(BeanQueryRequest<?> var1);

    public int queryBindHash();

    public void addSql(SpiExpressionRequest var1);

    public void addBindValues(SpiExpressionRequest var1);
}

