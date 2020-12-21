/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy.generatedproperty;

import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.generatedproperty.GeneratedProperty;

public class GeneratedCounterLong
implements GeneratedProperty {
    public Object getInsertValue(BeanProperty prop, Object bean) {
        return 1L;
    }

    public Object getUpdateValue(BeanProperty prop, Object bean) {
        Long i = (Long)prop.getValue(bean);
        return i + 1L;
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

