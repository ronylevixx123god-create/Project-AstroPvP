/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.expression;

import com.avaje.ebean.ExampleExpression;
import com.avaje.ebean.LikeType;
import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebeaninternal.api.ManyWhereJoins;
import com.avaje.ebeaninternal.api.SpiExpression;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.expression.FilterExprPath;
import com.avaje.ebeaninternal.server.expression.LikeExpression;
import com.avaje.ebeaninternal.server.expression.SimpleExpression;
import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
import java.util.ArrayList;
import java.util.Iterator;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class DefaultExampleExpression
implements SpiExpression,
ExampleExpression {
    private static final long serialVersionUID = 1L;
    private final Object entity;
    private boolean caseInsensitive;
    private LikeType likeType;
    private boolean includeZeros;
    private ArrayList<SpiExpression> list;
    private final FilterExprPath pathPrefix;

    public DefaultExampleExpression(FilterExprPath pathPrefix, Object entity, boolean caseInsensitive, LikeType likeType) {
        this.pathPrefix = pathPrefix;
        this.entity = entity;
        this.caseInsensitive = caseInsensitive;
        this.likeType = likeType;
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
    public void containsMany(BeanDescriptor<?> desc, ManyWhereJoins whereManyJoins) {
        if (this.list != null) {
            for (int i = 0; i < this.list.size(); ++i) {
                this.list.get(i).containsMany(desc, whereManyJoins);
            }
        }
    }

    @Override
    public ExampleExpression includeZeros() {
        this.includeZeros = true;
        return this;
    }

    @Override
    public ExampleExpression caseInsensitive() {
        this.caseInsensitive = true;
        return this;
    }

    @Override
    public ExampleExpression useStartsWith() {
        this.likeType = LikeType.STARTS_WITH;
        return this;
    }

    @Override
    public ExampleExpression useContains() {
        this.likeType = LikeType.CONTAINS;
        return this;
    }

    @Override
    public ExampleExpression useEndsWith() {
        this.likeType = LikeType.ENDS_WITH;
        return this;
    }

    @Override
    public ExampleExpression useEqualTo() {
        this.likeType = LikeType.EQUAL_TO;
        return this;
    }

    public String getPropertyName() {
        return null;
    }

    @Override
    public void addBindValues(SpiExpressionRequest request) {
        for (int i = 0; i < this.list.size(); ++i) {
            SpiExpression item = this.list.get(i);
            item.addBindValues(request);
        }
    }

    @Override
    public void addSql(SpiExpressionRequest request) {
        if (!this.list.isEmpty()) {
            request.append("(");
            for (int i = 0; i < this.list.size(); ++i) {
                SpiExpression item = this.list.get(i);
                if (i > 0) {
                    request.append(" and ");
                }
                item.addSql(request);
            }
            request.append(") ");
        }
    }

    @Override
    public int queryAutoFetchHash() {
        return DefaultExampleExpression.class.getName().hashCode();
    }

    @Override
    public int queryPlanHash(BeanQueryRequest<?> request) {
        this.list = this.buildExpressions(request);
        int hc = DefaultExampleExpression.class.getName().hashCode();
        for (int i = 0; i < this.list.size(); ++i) {
            hc = hc * 31 + this.list.get(i).queryPlanHash(request);
        }
        return hc;
    }

    @Override
    public int queryBindHash() {
        int hc = DefaultExampleExpression.class.getName().hashCode();
        for (int i = 0; i < this.list.size(); ++i) {
            hc = hc * 31 + this.list.get(i).queryBindHash();
        }
        return hc;
    }

    private ArrayList<SpiExpression> buildExpressions(BeanQueryRequest<?> request) {
        ArrayList<SpiExpression> list = new ArrayList<SpiExpression>();
        OrmQueryRequest r = (OrmQueryRequest)request;
        BeanDescriptor beanDescriptor = r.getBeanDescriptor();
        Iterator<BeanProperty> propIter = beanDescriptor.propertiesAll();
        while (propIter.hasNext()) {
            BeanProperty beanProperty = propIter.next();
            String propName = beanProperty.getName();
            Object value = beanProperty.getValue(this.entity);
            if (!beanProperty.isScalar() || value == null) continue;
            if (value instanceof String) {
                list.add(new LikeExpression(this.pathPrefix, propName, (String)value, this.caseInsensitive, this.likeType));
                continue;
            }
            if (!this.includeZeros && this.isZero(value)) continue;
            list.add(new SimpleExpression(this.pathPrefix, propName, SimpleExpression.Op.EQ, value));
        }
        return list;
    }

    private boolean isZero(Object value) {
        Number num;
        double doubleValue;
        return value instanceof Number && (doubleValue = (num = (Number)value).doubleValue()) == 0.0;
    }
}

