/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy.generatedproperty;

import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.generatedproperty.GeneratedProperty;

public class GeneratedUpdateLong
implements GeneratedProperty {
    public Object getInsertValue(BeanProperty prop, Object bean) {
        return System.currentTimeMillis();
    }

    public Object getUpdateValue(BeanProperty prop, Object bean) {
        return System.currentTimeMillis();
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

