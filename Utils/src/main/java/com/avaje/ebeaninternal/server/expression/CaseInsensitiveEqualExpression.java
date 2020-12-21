/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.expression;

import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import com.avaje.ebeaninternal.server.expression.AbstractExpression;
import com.avaje.ebeaninternal.server.expression.CaseInsensitiveEqualExpressionLucene;
import com.avaje.ebeaninternal.server.expression.FilterExprPath;
import com.avaje.ebeaninternal.server.expression.LuceneAwareExpression;
import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
class CaseInsensitiveEqualExpression
extends AbstractExpression
implements LuceneAwareExpression {
    private static final long serialVersionUID = -6406036750998971064L;
    private final String value;

    CaseInsensitiveEqualExpression(FilterExprPath pathPrefix, String propertyName, String value) {
        super(pathPrefix, propertyName);
        this.value = value.toLowerCase();
    }

    @Override
    public boolean isLuceneResolvable(LuceneResolvableRequest req) {
        return true;
    }

    @Override
    public SpiLuceneExpr createLuceneExpr(SpiExpressionRequest request) {
        String propertyName = this.getPropertyName();
        return new CaseInsensitiveEqualExpressionLucene().createLuceneExpr(request, propertyName, this.value);
    }

    @Override
    public void addBindValues(SpiExpressionRequest request) {
        ElPropertyValue prop = this.getElProp(request);
        if (prop != null && prop.isDbEncrypted()) {
            String encryptKey = prop.getBeanProperty().getEncryptKey().getStringValue();
            request.addBindValue(encryptKey);
        }
        request.addBindValue(this.value);
    }

    @Override
    public void addSql(SpiExpressionRequest request) {
        String propertyName;
        String pname = propertyName = this.getPropertyName();
        ElPropertyValue prop = this.getElProp(request);
        if (prop != null && prop.isDbEncrypted()) {
            pname = prop.getBeanProperty().getDecryptProperty(propertyName);
        }
        request.append("lower(").append(pname).append(") =? ");
    }

    @Override
    public int queryAutoFetchHash() {
        int hc = CaseInsensitiveEqualExpression.class.getName().hashCode();
        hc = hc * 31 + this.propName.hashCode();
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

