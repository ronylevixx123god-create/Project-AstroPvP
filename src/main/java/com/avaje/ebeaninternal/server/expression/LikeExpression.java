/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.expression;

import com.avaje.ebean.LikeType;
import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import com.avaje.ebeaninternal.server.expression.AbstractExpression;
import com.avaje.ebeaninternal.server.expression.FilterExprPath;
import com.avaje.ebeaninternal.server.expression.LikeExpressionLucene;
import com.avaje.ebeaninternal.server.expression.LuceneAwareExpression;
import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
class LikeExpression
extends AbstractExpression
implements LuceneAwareExpression {
    private static final long serialVersionUID = -5398151809111172380L;
    private final String val;
    private final boolean caseInsensitive;
    private final LikeType type;

    LikeExpression(FilterExprPath pathPrefix, String propertyName, String value, boolean caseInsensitive, LikeType type) {
        super(pathPrefix, propertyName);
        this.caseInsensitive = caseInsensitive;
        this.type = type;
        this.val = value;
    }

    @Override
    public boolean isLuceneResolvable(LuceneResolvableRequest req) {
        String propertyName = this.getPropertyName();
        if (LikeType.ENDS_WITH.equals((Object)this.type)) {
            return false;
        }
        return req.indexContains(propertyName);
    }

    @Override
    public SpiLuceneExpr createLuceneExpr(SpiExpressionRequest request) {
        String propertyName = this.getPropertyName();
        return new LikeExpressionLucene().createLuceneExpr(request, propertyName, this.type, this.caseInsensitive, this.val);
    }

    @Override
    public void addBindValues(SpiExpressionRequest request) {
        ElPropertyValue prop = this.getElProp(request);
        if (prop != null && prop.isDbEncrypted()) {
            String encryptKey = prop.getBeanProperty().getEncryptKey().getStringValue();
            request.addBindValue(encryptKey);
        }
        String bindValue = LikeExpression.getValue(this.val, this.caseInsensitive, this.type);
        request.addBindValue(bindValue);
    }

    @Override
    public void addSql(SpiExpressionRequest request) {
        String propertyName;
        String pname = propertyName = this.getPropertyName();
        ElPropertyValue prop = this.getElProp(request);
        if (prop != null && prop.isDbEncrypted()) {
            pname = prop.getBeanProperty().getDecryptProperty(propertyName);
        }
        if (this.caseInsensitive) {
            request.append("lower(").append(pname).append(")");
        } else {
            request.append(pname);
        }
        if (this.type.equals((Object)LikeType.EQUAL_TO)) {
            request.append(" = ? ");
        } else {
            request.append(" like ? ");
        }
    }

    @Override
    public int queryAutoFetchHash() {
        int hc = LikeExpression.class.getName().hashCode();
        hc = hc * 31 + (this.caseInsensitive ? 0 : 1);
        hc = hc * 31 + this.propName.hashCode();
        return hc;
    }

    @Override
    public int queryPlanHash(BeanQueryRequest<?> request) {
        return this.queryAutoFetchHash();
    }

    @Override
    public int queryBindHash() {
        return this.val.hashCode();
    }

    private static String getValue(String value, boolean caseInsensitive, LikeType type) {
        if (caseInsensitive) {
            value = value.toLowerCase();
        }
        switch (type) {
            case RAW: {
                return value;
            }
            case STARTS_WITH: {
                return value + "%";
            }
            case ENDS_WITH: {
                return "%" + value;
            }
            case CONTAINS: {
                return "%" + value + "%";
            }
            case EQUAL_TO: {
                return value;
            }
        }
        throw new RuntimeException("LikeType " + (Object)((Object)type) + " missed?");
    }
}

