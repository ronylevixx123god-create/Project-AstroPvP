/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.expression;

import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import com.avaje.ebeaninternal.server.expression.AbstractExpression;
import com.avaje.ebeaninternal.server.expression.FilterExprPath;
import com.avaje.ebeaninternal.server.expression.LuceneAwareExpression;
import com.avaje.ebeaninternal.server.expression.SimpleExpressionLucene;
import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
import com.avaje.ebeaninternal.server.type.ScalarType;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
class SimpleExpression
extends AbstractExpression
implements LuceneAwareExpression {
    private static final long serialVersionUID = -382881395755603790L;
    private final Op type;
    private final Object value;

    public SimpleExpression(FilterExprPath pathPrefix, String propertyName, Op type, Object value) {
        super(pathPrefix, propertyName);
        this.type = type;
        this.value = value;
    }

    @Override
    public boolean isLuceneResolvable(LuceneResolvableRequest req) {
        String propertyName = this.getPropertyName();
        if (!req.indexContains(propertyName)) {
            return false;
        }
        ElPropertyValue prop = req.getBeanDescriptor().getElGetValue(propertyName);
        if (prop == null) {
            return false;
        }
        BeanProperty beanProperty = prop.getBeanProperty();
        ScalarType<?> scalarType = beanProperty.getScalarType();
        if (scalarType == null) {
            return false;
        }
        int luceneType = scalarType.getLuceneType();
        if (7 == luceneType) {
            return false;
        }
        if (0 == luceneType) {
            return Op.EQ.equals((Object)this.type) || Op.NOT_EQ.equals((Object)this.type);
        }
        return !Op.NOT_EQ.equals((Object)this.type);
    }

    @Override
    public SpiLuceneExpr createLuceneExpr(SpiExpressionRequest request) {
        String propertyName = this.getPropertyName();
        ElPropertyValue prop = this.getElProp(request);
        return new SimpleExpressionLucene().addLuceneQuery(request, this.type, propertyName, this.value, prop);
    }

    @Override
    public void addBindValues(SpiExpressionRequest request) {
        ElPropertyValue prop = this.getElProp(request);
        if (prop != null) {
            if (prop.isAssocId()) {
                Object[] ids = prop.getAssocOneIdValues(this.value);
                if (ids != null) {
                    for (int i = 0; i < ids.length; ++i) {
                        request.addBindValue(ids[i]);
                    }
                }
                return;
            }
            if (prop.isDbEncrypted()) {
                String encryptKey = prop.getBeanProperty().getEncryptKey().getStringValue();
                request.addBindValue(encryptKey);
            } else if (prop.isLocalEncrypted()) {
                // empty if block
            }
        }
        request.addBindValue(this.value);
    }

    @Override
    public void addSql(SpiExpressionRequest request) {
        String propertyName = this.getPropertyName();
        ElPropertyValue prop = this.getElProp(request);
        if (prop != null) {
            if (prop.isAssocId()) {
                request.append(prop.getAssocOneIdExpr(propertyName, this.type.bind()));
                return;
            }
            if (prop.isDbEncrypted()) {
                String dsql = prop.getBeanProperty().getDecryptSql();
                request.append(dsql).append(this.type.bind());
                return;
            }
        }
        request.append(propertyName).append(this.type.bind());
    }

    @Override
    public int queryAutoFetchHash() {
        int hc = SimpleExpression.class.getName().hashCode();
        hc = hc * 31 + this.propName.hashCode();
        hc = hc * 31 + this.type.name().hashCode();
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

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static enum Op {
        EQ(" = ? ", " = "),
        NOT_EQ(" <> ? ", " <> "),
        LT(" < ? ", " < "),
        LT_EQ(" <= ? ", " <= "),
        GT(" > ? ", " > "),
        GT_EQ(" >= ? ", " >= ");

        String exp;
        String shortDesc;

        private Op(String exp, String shortDesc) {
            this.exp = exp;
            this.shortDesc = shortDesc;
        }

        public String bind() {
            return this.exp;
        }

        public String shortDesc() {
            return this.shortDesc;
        }
    }
}

