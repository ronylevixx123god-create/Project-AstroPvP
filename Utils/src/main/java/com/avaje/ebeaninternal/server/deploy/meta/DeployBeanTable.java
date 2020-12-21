/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy.meta;

import com.avaje.ebeaninternal.server.deploy.BeanDescriptorMap;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocOne;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class DeployBeanTable {
    private final Class<?> beanType;
    private String baseTable;
    private List<DeployBeanProperty> idProperties;

    public DeployBeanTable(Class<?> beanType) {
        this.beanType = beanType;
    }

    public String getBaseTable() {
        return this.baseTable;
    }

    public void setBaseTable(String baseTable) {
        this.baseTable = baseTable;
    }

    public BeanProperty[] createIdProperties(BeanDescriptorMap owner) {
        BeanProperty[] props = new BeanProperty[this.idProperties.size()];
        for (int i = 0; i < this.idProperties.size(); ++i) {
            props[i] = this.createProperty(owner, this.idProperties.get(i));
        }
        return props;
    }

    private BeanProperty createProperty(BeanDescriptorMap owner, DeployBeanProperty prop) {
        if (prop instanceof DeployBeanPropertyAssocOne) {
            return new BeanPropertyAssocOne(owner, (DeployBeanPropertyAssocOne)prop);
        }
        return new BeanProperty(prop);
    }

    public void setIdProperties(List<DeployBeanProperty> idProperties) {
        this.idProperties = idProperties;
    }

    public Class<?> getBeanType() {
        return this.beanType;
    }
}

