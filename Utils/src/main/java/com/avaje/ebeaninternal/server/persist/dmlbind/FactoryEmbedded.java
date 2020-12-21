/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.persist.dmlbind;

import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.persist.dml.DmlMode;
import com.avaje.ebeaninternal.server.persist.dmlbind.Bindable;
import com.avaje.ebeaninternal.server.persist.dmlbind.BindableEmbedded;
import com.avaje.ebeaninternal.server.persist.dmlbind.FactoryProperty;
import java.util.ArrayList;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class FactoryEmbedded {
    private final FactoryProperty factoryProperty;

    public FactoryEmbedded(boolean bindEncryptDataFirst) {
        this.factoryProperty = new FactoryProperty(bindEncryptDataFirst);
    }

    public void create(List<Bindable> list, BeanDescriptor<?> desc, DmlMode mode, boolean withLobs) {
        BeanPropertyAssocOne<?>[] embedded = desc.propertiesEmbedded();
        for (int j = 0; j < embedded.length; ++j) {
            ArrayList<Bindable> bindList = new ArrayList<Bindable>();
            BeanProperty[] props = embedded[j].getProperties();
            for (int i = 0; i < props.length; ++i) {
                Bindable item = this.factoryProperty.create(props[i], mode, withLobs);
                if (item == null) continue;
                bindList.add(item);
            }
            list.add(new BindableEmbedded(embedded[j], bindList));
        }
    }
}

