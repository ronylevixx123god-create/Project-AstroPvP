/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy.meta;

import com.avaje.ebeaninternal.server.deploy.BeanCascadeInfo;
import com.avaje.ebeaninternal.server.deploy.BeanTable;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
import com.avaje.ebeaninternal.server.deploy.meta.DeployTableJoin;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class DeployBeanPropertyAssoc<T>
extends DeployBeanProperty {
    Class<T> targetType;
    BeanCascadeInfo cascadeInfo = new BeanCascadeInfo();
    BeanTable beanTable;
    DeployTableJoin tableJoin = new DeployTableJoin();
    boolean isOuterJoin = false;
    String extraWhere;
    String mappedBy;

    public DeployBeanPropertyAssoc(DeployBeanDescriptor<?> desc, Class<T> targetType) {
        super(desc, targetType, null, null);
        this.targetType = targetType;
    }

    @Override
    public boolean isScalar() {
        return false;
    }

    public Class<T> getTargetType() {
        return this.targetType;
    }

    public boolean isOuterJoin() {
        return this.isOuterJoin;
    }

    public void setOuterJoin(boolean isOuterJoin) {
        this.isOuterJoin = isOuterJoin;
    }

    public String getExtraWhere() {
        return this.extraWhere;
    }

    public void setExtraWhere(String extraWhere) {
        this.extraWhere = extraWhere;
    }

    public DeployTableJoin getTableJoin() {
        return this.tableJoin;
    }

    public BeanTable getBeanTable() {
        return this.beanTable;
    }

    public void setBeanTable(BeanTable beanTable) {
        this.beanTable = beanTable;
        this.getTableJoin().setTable(beanTable.getBaseTable());
    }

    public BeanCascadeInfo getCascadeInfo() {
        return this.cascadeInfo;
    }

    public String getMappedBy() {
        return this.mappedBy;
    }

    public void setMappedBy(String mappedBy) {
        if (!"".equals(mappedBy)) {
            this.mappedBy = mappedBy;
        }
    }
}

