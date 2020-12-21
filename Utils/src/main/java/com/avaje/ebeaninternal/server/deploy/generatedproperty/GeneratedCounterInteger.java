/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy.generatedproperty;

import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.generatedproperty.GeneratedProperty;

public class GeneratedCounterInteger
implements GeneratedProperty {
    public Object getInsertValue(BeanProperty prop, Object bean) {
        return 1;
    }

    public Object getUpdateValue(BeanProperty prop, Object bean) {
        Integer i = (Integer)prop.getValue(bean);
        return i + 1;
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

