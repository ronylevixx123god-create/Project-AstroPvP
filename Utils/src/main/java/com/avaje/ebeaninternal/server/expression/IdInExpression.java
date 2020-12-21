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
import com.avaje.ebeaninternal.server.deploy.id.IdBinder;
import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
import com.avaje.ebeaninternal.util.DefaultExpressionRequest;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class IdInExpression
implements SpiExpression {
    private static final long serialVersionUID = 1L;
    private final List<?> idList;

    public IdInExpression(List<?> idList) {
        this.idList = idList;
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
        BeanDescriptor<?> descriptor = r.getBeanDescriptor();
        IdBinder idBinder = descriptor.getIdBinder();
        for (int i = 0; i < this.idList.size(); ++i) {
            idBinder.addIdInBindValue(request, this.idList.get(i));
        }
    }

    public void addSqlNoAlias(SpiExpressionRequest request) {
        DefaultExpressionRequest r = (DefaultExpressionRequest)request;
        BeanDescriptor<?> descriptor = r.getBeanDescriptor();
        IdBinder idBinder = descriptor.getIdBinder();
        request.append(descriptor.getIdBinder().getBindIdInSql(null));
        String inClause = idBinder.getIdInValueExpr(this.idList.size());
        request.append(inClause);
    }

    @Override
    public void addSql(SpiExpressionRequest request) {
        DefaultExpressionRequest r = (DefaultExpressionRequest)request;
        BeanDescriptor<?> descriptor = r.getBeanDescriptor();
        IdBinder idBinder = descriptor.getIdBinder();
        request.append(descriptor.getIdBinderInLHSSql());
        String inClause = idBinder.getIdInValueExpr(this.idList.size());
        request.append(inClause);
    }

    @Override
    public int queryAutoFetchHash() {
        int hc = IdInExpression.class.getName().hashCode();
        hc = hc * 31 + this.idList.size();
        return hc;
    }

    @Override
    public int queryPlanHash(BeanQueryRequest<?> request) {
        return this.queryAutoFetchHash();
    }

    @Override
    public int queryBindHash() {
        return ((Object)this.idList).hashCode();
    }
}

