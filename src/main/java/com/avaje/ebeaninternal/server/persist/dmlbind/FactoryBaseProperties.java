/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.persist.dmlbind;

import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyCompound;
import com.avaje.ebeaninternal.server.persist.dml.DmlMode;
import com.avaje.ebeaninternal.server.persist.dmlbind.Bindable;
import com.avaje.ebeaninternal.server.persist.dmlbind.BindableCompound;
import com.avaje.ebeaninternal.server.persist.dmlbind.FactoryProperty;
import java.util.ArrayList;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class FactoryBaseProperties {
    private final FactoryProperty factoryProperty;

    public FactoryBaseProperties(boolean bindEncryptDataFirst) {
        this.factoryProperty = new FactoryProperty(bindEncryptDataFirst);
    }

    public void create(List<Bindable> list, BeanDescriptor<?> desc, DmlMode mode, boolean withLobs) {
        this.add(desc.propertiesBaseScalar(), list, desc, mode, withLobs);
        BeanPropertyCompound[] compoundProps = desc.propertiesBaseCompound();
        for (int i = 0; i < compoundProps.length; ++i) {
            BeanProperty[] props = compoundProps[i].getScalarProperties();
            ArrayList<Bindable> newList = new ArrayList<Bindable>(props.length);
            this.add(props, newList, desc, mode, withLobs);
            BindableCompound compoundBindable = new BindableCompound(compoundProps[i], newList);
            list.add(compoundBindable);
        }
    }

    private void add(BeanProperty[] props, List<Bindable> list, BeanDescriptor<?> desc, DmlMode mode, boolean withLobs) {
        for (int i = 0; i < props.length; ++i) {
            Bindable item = this.factoryProperty.create(props[i], mode, withLobs);
            if (item == null) continue;
            list.add(item);
        }
    }
}

