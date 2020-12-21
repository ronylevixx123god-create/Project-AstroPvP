/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.expression;

import com.avaje.ebeaninternal.api.ManyWhereJoins;
import com.avaje.ebeaninternal.api.SpiExpression;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.el.ElPropertyDeploy;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import com.avaje.ebeaninternal.server.expression.FilterExprPath;
import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class AbstractExpression
implements SpiExpression {
    private static final long serialVersionUID = 4072786211853856174L;
    protected final String propName;
    protected final FilterExprPath pathPrefix;

    protected AbstractExpression(FilterExprPath pathPrefix, String propName) {
        this.pathPrefix = pathPrefix;
        this.propName = propName;
    }

    protected String getPropertyName() {
        if (this.pathPrefix == null) {
            return this.propName;
        }
        String path = this.pathPrefix.getPath();
        if (path == null || path.length() == 0) {
            return this.propName;
        }
        return path + "." + this.propName;
    }

    @Override
    public boolean isLuceneResolvable(LuceneResolvableRequest req) {
        return false;
    }

    @Override
    public void containsMany(BeanDescriptor<?> desc, ManyWhereJoins manyWhereJoin) {
        ElPropertyDeploy elProp;
        String propertyName = this.getPropertyName();
        if (propertyName != null && (elProp = desc.getElPropertyDeploy(propertyName)) != null && elProp.containsMany()) {
            manyWhereJoin.add(elProp);
        }
    }

    protected ElPropertyValue getElProp(SpiExpressionRequest request) {
        String propertyName = this.getPropertyName();
        return request.getBeanDescriptor().getElGetValue(propertyName);
    }
}

