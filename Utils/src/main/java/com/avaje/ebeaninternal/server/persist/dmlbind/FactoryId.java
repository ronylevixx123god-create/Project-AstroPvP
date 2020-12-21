/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.persist.dmlbind;

import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.persist.dmlbind.BindableId;
import com.avaje.ebeaninternal.server.persist.dmlbind.BindableIdEmbedded;
import com.avaje.ebeaninternal.server.persist.dmlbind.BindableIdMap;
import com.avaje.ebeaninternal.server.persist.dmlbind.BindableIdScalar;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class FactoryId {
    public BindableId createId(BeanDescriptor<?> desc) {
        BeanProperty[] uids = desc.propertiesId();
        if (uids.length == 1) {
            if (!uids[0].isEmbedded()) {
                return new BindableIdScalar(uids[0]);
            }
            BeanPropertyAssocOne embId = (BeanPropertyAssocOne)uids[0];
            return new BindableIdEmbedded(embId, desc);
        }
        return new BindableIdMap(uids, desc);
    }
}

