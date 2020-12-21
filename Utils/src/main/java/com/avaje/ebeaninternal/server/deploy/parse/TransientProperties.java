/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy.parse;

import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocOne;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class TransientProperties {
    public void process(DeployBeanDescriptor<?> desc) {
        List<DeployBeanProperty> props = desc.propertiesBase();
        for (int i = 0; i < props.size(); ++i) {
            DeployBeanProperty prop = props.get(i);
            if (prop.isDbRead() || prop.isDbInsertable() || prop.isDbUpdateable()) continue;
            prop.setTransient(true);
        }
        List<DeployBeanPropertyAssocOne<?>> ones = desc.propertiesAssocOne();
        for (int i = 0; i < ones.size(); ++i) {
            DeployBeanPropertyAssocOne<?> prop = ones.get(i);
            if (prop.getBeanTable() != null || prop.isEmbedded()) continue;
            prop.setTransient(true);
        }
        List<DeployBeanPropertyAssocMany<?>> manys = desc.propertiesAssocMany();
        for (int i = 0; i < manys.size(); ++i) {
            DeployBeanPropertyAssocMany<?> prop = manys.get(i);
            if (prop.getBeanTable() != null) continue;
            prop.setTransient(true);
        }
    }
}

