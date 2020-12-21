/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy.generatedproperty;

import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.generatedproperty.GeneratedProperty;

public class GeneratedCounter
implements GeneratedProperty {
    final int numberType;

    public GeneratedCounter(int numberType) {
        this.numberType = numberType;
    }

    public Object getInsertValue(BeanProperty prop, Object bean) {
        Integer i = 1;
        return BasicTypeConverter.convert(i, this.numberType);
    }

    public Object getUpdateValue(BeanProperty prop, Object bean) {
        Number currVal = (Number)prop.getValue(bean);
        Integer nextVal = currVal.intValue() + 1;
        return BasicTypeConverter.convert(nextVal, this.numberType);
    }

    public boolean includeInUpdate() {
        return true;
    }

    public boolean includeInInsert() {
        return true;
    }

    public boolean isDDLNotNullable() {
        return true;
    }
}

