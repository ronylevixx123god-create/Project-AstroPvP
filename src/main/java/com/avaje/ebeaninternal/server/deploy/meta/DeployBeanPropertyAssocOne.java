/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy.meta;

import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanEmbedded;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssoc;
import com.avaje.ebeaninternal.server.deploy.meta.DeployTableJoinColumn;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class DeployBeanPropertyAssocOne<T>
extends DeployBeanPropertyAssoc<T> {
    boolean oneToOne;
    boolean oneToOneExported;
    boolean importedPrimaryKey;
    DeployBeanEmbedded deployEmbedded;

    public DeployBeanPropertyAssocOne(DeployBeanDescriptor<?> desc, Class<T> targetType) {
        super(desc, targetType);
    }

    public DeployBeanEmbedded getDeployEmbedded() {
        if (this.deployEmbedded == null) {
            this.deployEmbedded = new DeployBeanEmbedded();
        }
        return this.deployEmbedded;
    }

    @Override
    public String getDbColumn() {
        DeployTableJoinColumn[] columns = this.tableJoin.columns();
        if (columns.length == 1) {
            return columns[0].getLocalDbColumn();
        }
        return super.getDbColumn();
    }

    @Override
    public String getElPlaceHolder(BeanDescriptor.EntityType et) {
        return super.getElPlaceHolder(et);
    }

    public boolean isOneToOne() {
        return this.oneToOne;
    }

    public void setOneToOne(boolean oneToOne) {
        this.oneToOne = oneToOne;
    }

    public boolean isOneToOneExported() {
        return this.oneToOneExported;
    }

    public void setOneToOneExported(boolean oneToOneExported) {
        this.oneToOneExported = oneToOneExported;
    }

    public boolean isImportedPrimaryKey() {
        return this.importedPrimaryKey;
    }

    public void setImportedPrimaryKey(boolean importedPrimaryKey) {
        this.importedPrimaryKey = importedPrimaryKey;
    }
}

