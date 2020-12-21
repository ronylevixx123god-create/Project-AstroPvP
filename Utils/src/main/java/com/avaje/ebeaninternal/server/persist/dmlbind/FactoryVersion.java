/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.persist.dmlbind;

import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.persist.dmlbind.Bindable;
import com.avaje.ebeaninternal.server.persist.dmlbind.BindableEmbedded;
import com.avaje.ebeaninternal.server.persist.dmlbind.BindableList;
import com.avaje.ebeaninternal.server.persist.dmlbind.BindableProperty;
import java.util.ArrayList;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class FactoryVersion {
    public Bindable create(BeanDescriptor<?> desc) {
        ArrayList<Bindable> verList = new ArrayList<Bindable>();
        BeanProperty[] vers = desc.propertiesVersion();
        for (int i = 0; i < vers.length; ++i) {
            verList.add(new BindableProperty(vers[i]));
        }
        BeanPropertyAssocOne<?>[] embedded = desc.propertiesEmbedded();
        for (int j = 0; j < embedded.length; ++j) {
            if (!embedded[j].isEmbeddedVersion()) continue;
            ArrayList<Bindable> bindList = new ArrayList<Bindable>();
            BeanProperty[] embProps = embedded[j].getProperties();
            for (int i = 0; i < embProps.length; ++i) {
                if (!embProps[i].isVersion()) continue;
                bindList.add(new BindableProperty(embProps[i]));
            }
            verList.add(new BindableEmbedded(embedded[j], bindList));
        }
        if (verList.size() == 0) {
            return null;
        }
        if (verList.size() == 1) {
            return (Bindable)verList.get(0);
        }
        return new BindableList(verList);
    }
}

