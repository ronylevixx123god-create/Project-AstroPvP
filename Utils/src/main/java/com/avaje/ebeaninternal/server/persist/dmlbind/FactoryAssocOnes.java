/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.persist.dmlbind;

import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.persist.dml.DmlMode;
import com.avaje.ebeaninternal.server.persist.dmlbind.Bindable;
import com.avaje.ebeaninternal.server.persist.dmlbind.BindableAssocOne;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class FactoryAssocOnes {
    /*
     * Enabled aggressive block sorting
     */
    public List<Bindable> create(List<Bindable> list, BeanDescriptor<?> desc, DmlMode mode) {
        BeanPropertyAssocOne<?>[] ones = desc.propertiesOneImported();
        int i = 0;
        while (true) {
            block7: {
                if (i >= ones.length) {
                    return list;
                }
                if (ones[i].isImportedPrimaryKey()) break block7;
                switch (mode) {
                    case WHERE: {
                        break;
                    }
                    case INSERT: {
                        if (ones[i].isInsertable()) break;
                        break block7;
                    }
                    case UPDATE: {
                        if (!ones[i].isUpdateable()) break block7;
                    }
                }
                list.add(new BindableAssocOne(ones[i]));
            }
            ++i;
        }
    }
}

