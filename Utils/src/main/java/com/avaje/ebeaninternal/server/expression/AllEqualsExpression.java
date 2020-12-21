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
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
class AllEqualsExpression
implements SpiExpression {
    private static final long serialVersionUID = -8691773558205937025L;
    private final Map<String, Object> propMap;
    private final FilterExprPath pathPrefix;

    AllEqualsExpression(FilterExprPath pathPrefix, Map<String, Object> propMap) {
        this.pathPrefix = pathPrefix;
        this.propMap = propMap;
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
        if (this.propMap != null) {
            for (String propertyName : this.propMap.keySet()) {
                ElPropertyDeploy elProp = desc.getElPropertyDeploy(this.name(propertyName));
                if (elProp == null || !elProp.containsMany()) continue;
                manyWhereJoin.add(elProp);
            }
        }
    }

    @Override
    public void addBindValues(SpiExpressionRequest request) {
        if (this.propMap.isEmpty()) {
            return;
        }
        for (Object value : this.propMap.values()) {
            if (value == null) continue;
            request.addBindValue(value);
        }
    }

    @Override
    public void addSql(SpiExpressionRequest request) {
        if (this.propMap.isEmpty()) {
            return;
        }
        request.append("(");
        Set<Map.Entry<String, Object>> entries = this.propMap.entrySet();
        Iterator<Map.Entry<String, Object>> it = entries.iterator();
        int count = 0;
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            Object value = entry.getValue();
            String propName = entry.getKey();
            if (count > 0) {
                request.append("and ");
            }
            request.append(this.name(propName));
            if (value == null) {
                request.append(" is null ");
            } else {
                request.append(" = ? ");
            }
            ++count;
        }
        request.append(")");
    }

    @Override
    public int queryAutoFetchHash() {
        int hc = AllEqualsExpression.class.getName().hashCode();
        Set<Map.Entry<String, Object>> entries = this.propMap.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            Object value = entry.getValue();
            String propName = entry.getKey();
            hc = hc * 31 + propName.hashCode();
            hc = hc * 31 + (value == null ? 0 : 1);
        }
        return hc;
    }

    @Override
    public int queryPlanHash(BeanQueryRequest<?> request) {
        return this.queryAutoFetchHash();
    }

    @Override
    public int queryBindHash() {
        return this.queryAutoFetchHash();
    }
}

