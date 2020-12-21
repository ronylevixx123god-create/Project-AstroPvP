/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebean.config.ScalarTypeConverter;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyCompoundRoot;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
import com.avaje.ebeaninternal.server.type.CtCompoundProperty;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class BeanPropertyCompoundScalar
extends BeanProperty {
    private final BeanPropertyCompoundRoot rootProperty;
    private final CtCompoundProperty ctProperty;
    private final ScalarTypeConverter typeConverter;

    public BeanPropertyCompoundScalar(BeanPropertyCompoundRoot rootProperty, DeployBeanProperty scalarDeploy, CtCompoundProperty ctProperty, ScalarTypeConverter<?, ?> typeConverter) {
        super(scalarDeploy);
        this.rootProperty = rootProperty;
        this.ctProperty = ctProperty;
        this.typeConverter = typeConverter;
    }

    @Override
    public Object getValue(Object valueObject) {
        if (this.typeConverter != null) {
            valueObject = this.typeConverter.unwrapValue(valueObject);
        }
        return this.ctProperty.getValue(valueObject);
    }

    @Override
    public void setValue(Object bean, Object value) {
        this.setValueInCompound(bean, value, false);
    }

    public void setValueInCompound(Object bean, Object value, boolean intercept) {
        Object compoundValue = this.ctProperty.setValue(bean, value);
        if (compoundValue != null) {
            if (this.typeConverter != null) {
                compoundValue = this.typeConverter.wrapValue(compoundValue);
            }
            if (intercept) {
                this.rootProperty.setRootValueIntercept(bean, compoundValue);
            } else {
                this.rootProperty.setRootValue(bean, compoundValue);
            }
        }
    }

    @Override
    public void setValueIntercept(Object bean, Object value) {
        this.setValueInCompound(bean, value, true);
    }

    @Override
    public Object getValueIntercept(Object bean) {
        return this.getValue(bean);
    }

    @Override
    public Object elGetReference(Object bean) {
        return this.getValue(bean);
    }

    @Override
    public Object elGetValue(Object bean) {
        return this.getValue(bean);
    }

    @Override
    public void elSetReference(Object bean) {
        super.elSetReference(bean);
    }

    @Override
    public void elSetValue(Object bean, Object value, boolean populate, boolean reference) {
        super.elSetValue(bean, value, populate, reference);
    }
}

