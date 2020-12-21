/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy.generatedproperty;

import com.avaje.ebeaninternal.server.deploy.BeanProperty;

public interface GeneratedProperty {
    public Object getInsertValue(BeanProperty var1, Object var2);

    public Object getUpdateValue(BeanProperty var1, Object var2);

    public boolean includeInUpdate();

    public boolean includeInInsert();

    public boolean isDDLNotNullable();
}

