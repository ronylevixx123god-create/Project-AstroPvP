/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.persist.dmlbind;

import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.generatedproperty.GeneratedProperty;
import com.avaje.ebeaninternal.server.persist.dml.DmlMode;
import com.avaje.ebeaninternal.server.persist.dmlbind.Bindable;
import com.avaje.ebeaninternal.server.persist.dmlbind.BindableEncryptedProperty;
import com.avaje.ebeaninternal.server.persist.dmlbind.BindableProperty;
import com.avaje.ebeaninternal.server.persist.dmlbind.BindablePropertyInsertGenerated;
import com.avaje.ebeaninternal.server.persist.dmlbind.BindablePropertyUpdateGenerated;

public class FactoryProperty {
    private final boolean bindEncryptDataFirst;

    public FactoryProperty(boolean bindEncryptDataFirst) {
        this.bindEncryptDataFirst = bindEncryptDataFirst;
    }

    public Bindable create(BeanProperty prop, DmlMode mode, boolean withLobs) {
        if (DmlMode.INSERT.equals((Object)mode) && !prop.isDbInsertable()) {
            return null;
        }
        if (DmlMode.UPDATE.equals((Object)mode) && !prop.isDbUpdatable()) {
            return null;
        }
        if (prop.isLob()) {
            if (DmlMode.WHERE.equals((Object)mode) || !withLobs) {
                return null;
            }
            return prop.isDbEncrypted() ? new BindableEncryptedProperty(prop, this.bindEncryptDataFirst) : new BindableProperty(prop);
        }
        GeneratedProperty gen = prop.getGeneratedProperty();
        if (gen != null) {
            if (DmlMode.INSERT.equals((Object)mode)) {
                if (gen.includeInInsert()) {
                    return new BindablePropertyInsertGenerated(prop, gen);
                }
                return null;
            }
            if (DmlMode.UPDATE.equals((Object)mode)) {
                if (gen.includeInUpdate()) {
                    return new BindablePropertyUpdateGenerated(prop, gen);
                }
                return null;
            }
        }
        return prop.isDbEncrypted() ? new BindableEncryptedProperty(prop, this.bindEncryptDataFirst) : new BindableProperty(prop);
    }
}

