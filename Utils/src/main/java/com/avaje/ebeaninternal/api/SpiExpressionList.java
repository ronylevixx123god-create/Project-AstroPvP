/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.api;

import com.avaje.ebean.ExpressionFactory;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebeaninternal.api.ManyWhereJoins;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
import java.util.ArrayList;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface SpiExpressionList<T>
extends ExpressionList<T> {
    public boolean isLuceneResolvable(LuceneResolvableRequest var1);

    public SpiLuceneExpr createLuceneExpr(SpiExpressionRequest var1, SpiLuceneExpr.ExprOccur var2);

    public void trimPath(int var1);

    public void setExpressionFactory(ExpressionFactory var1);

    public void containsMany(BeanDescriptor<?> var1, ManyWhereJoins var2);

    public boolean isEmpty();

    public String buildSql(SpiExpressionRequest var1);

    public ArrayList<Object> buildBindValues(SpiExpressionRequest var1);

    public int queryPlanHash(BeanQueryRequest<?> var1);
}

