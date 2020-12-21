/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy.parse;

import com.avaje.ebean.annotation.LdapAttribute;
import com.avaje.ebean.annotation.PrivateOwned;
import com.avaje.ebean.annotation.Where;
import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebean.config.NamingConvention;
import com.avaje.ebean.config.TableName;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptorManager;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanTable;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.deploy.meta.DeployTableJoin;
import com.avaje.ebeaninternal.server.deploy.meta.DeployTableJoinColumn;
import com.avaje.ebeaninternal.server.deploy.parse.AnnotationParser;
import com.avaje.ebeaninternal.server.deploy.parse.DeployBeanInfo;
import com.avaje.ebeaninternal.server.lib.util.StringHelper;
import java.util.Iterator;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class AnnotationAssocManys
extends AnnotationParser {
    private final BeanDescriptorManager factory;

    public AnnotationAssocManys(DeployBeanInfo<?> info, BeanDescriptorManager factory) {
        super(info);
        this.factory = factory;
    }

    @Override
    public void parse() {
        Iterator<DeployBeanProperty> it = this.descriptor.propertiesAll();
        while (it.hasNext()) {
            DeployBeanProperty prop = it.next();
            if (!(prop instanceof DeployBeanPropertyAssocMany)) continue;
            this.read((DeployBeanPropertyAssocMany)prop);
        }
    }

    private void read(DeployBeanPropertyAssocMany<?> prop) {
        LdapAttribute ldapAttribute;
        JoinTable joinTable;
        JoinColumns joinColumns;
        Where where;
        MapKey mapKey;
        OrderBy orderBy;
        ManyToMany manyToMany;
        OneToMany oneToMany = this.get(prop, OneToMany.class);
        if (oneToMany != null) {
            this.readToOne(oneToMany, prop);
            PrivateOwned privateOwned = this.get(prop, PrivateOwned.class);
            if (privateOwned != null) {
                prop.setModifyListenMode(BeanCollection.ModifyListenMode.REMOVALS);
                prop.getCascadeInfo().setDelete(privateOwned.cascadeRemove());
            }
        }
        if ((manyToMany = this.get(prop, ManyToMany.class)) != null) {
            this.readToMany(manyToMany, prop);
        }
        if ((orderBy = this.get(prop, OrderBy.class)) != null) {
            prop.setFetchOrderBy(orderBy.value());
        }
        if ((mapKey = this.get(prop, MapKey.class)) != null) {
            prop.setMapKey(mapKey.name());
        }
        if ((where = this.get(prop, Where.class)) != null) {
            prop.setExtraWhere(where.clause());
        }
        BeanTable beanTable = prop.getBeanTable();
        JoinColumn joinColumn = this.get(prop, JoinColumn.class);
        if (joinColumn != null) {
            prop.getTableJoin().addJoinColumn(true, joinColumn, beanTable);
        }
        if ((joinColumns = this.get(prop, JoinColumns.class)) != null) {
            prop.getTableJoin().addJoinColumn(true, joinColumns.value(), beanTable);
        }
        if ((joinTable = this.get(prop, JoinTable.class)) != null) {
            if (prop.isManyToMany()) {
                this.readJoinTable(joinTable, prop);
            } else {
                prop.getTableJoin().addJoinColumn(true, joinTable.joinColumns(), beanTable);
            }
        }
        if ((ldapAttribute = this.get(prop, LdapAttribute.class)) != null) {
            this.readLdapAttribute(ldapAttribute, prop);
        }
        if (prop.getMappedBy() != null) {
            return;
        }
        if (prop.isManyToMany()) {
            this.manyToManyDefaultJoins(prop);
            return;
        }
        if (!prop.getTableJoin().hasJoinColumns() && beanTable != null) {
            NamingConvention nc = this.factory.getNamingConvention();
            String fkeyPrefix = null;
            if (nc.isUseForeignKeyPrefix()) {
                fkeyPrefix = nc.getColumnFromProperty(this.descriptor.getBeanType(), this.descriptor.getName());
            }
            BeanTable owningBeanTable = this.factory.getBeanTable(this.descriptor.getBeanType());
            owningBeanTable.createJoinColumn(fkeyPrefix, prop.getTableJoin(), false);
        }
    }

    private void readJoinTable(JoinTable joinTable, DeployBeanPropertyAssocMany<?> prop) {
        String intTableName = this.getFullTableName(joinTable);
        DeployTableJoin intJoin = new DeployTableJoin();
        intJoin.setTable(intTableName);
        intJoin.addJoinColumn(true, joinTable.joinColumns(), prop.getBeanTable());
        DeployTableJoin destJoin = prop.getTableJoin();
        destJoin.addJoinColumn(false, joinTable.inverseJoinColumns(), prop.getBeanTable());
        intJoin.setType("left outer join");
        DeployTableJoin inverseDest = destJoin.createInverse(intTableName);
        prop.setIntersectionJoin(intJoin);
        prop.setInverseJoin(inverseDest);
    }

    private String getFullTableName(JoinTable joinTable) {
        StringBuilder sb = new StringBuilder();
        if (!StringHelper.isNull(joinTable.catalog())) {
            sb.append(joinTable.catalog()).append(".");
        }
        if (!StringHelper.isNull(joinTable.schema())) {
            sb.append(joinTable.schema()).append(".");
        }
        sb.append(joinTable.name());
        return sb.toString();
    }

    private void manyToManyDefaultJoins(DeployBeanPropertyAssocMany<?> prop) {
        String fkCol;
        int i;
        String intTableName = null;
        DeployTableJoin intJoin = prop.getIntersectionJoin();
        if (intJoin == null) {
            intJoin = new DeployTableJoin();
            prop.setIntersectionJoin(intJoin);
        } else {
            intTableName = intJoin.getTable();
        }
        BeanTable localTable = this.factory.getBeanTable(this.descriptor.getBeanType());
        BeanTable otherTable = this.factory.getBeanTable(prop.getTargetType());
        String localTableName = localTable.getUnqualifiedBaseTable();
        String otherTableName = otherTable.getUnqualifiedBaseTable();
        if (intTableName == null) {
            intTableName = this.getM2MJoinTableName(localTable, otherTable);
            intJoin.setTable(intTableName);
            intJoin.setType("left outer join");
        }
        DeployTableJoin destJoin = prop.getTableJoin();
        if (intJoin.hasJoinColumns() && destJoin.hasJoinColumns()) {
            return;
        }
        if (!intJoin.hasJoinColumns()) {
            BeanProperty[] localIds = localTable.getIdProperties();
            for (i = 0; i < localIds.length; ++i) {
                fkCol = localTableName + "_" + localIds[i].getDbColumn();
                intJoin.addJoinColumn(new DeployTableJoinColumn(localIds[i].getDbColumn(), fkCol));
            }
        }
        if (!destJoin.hasJoinColumns()) {
            BeanProperty[] otherIds = otherTable.getIdProperties();
            for (i = 0; i < otherIds.length; ++i) {
                fkCol = otherTableName + "_" + otherIds[i].getDbColumn();
                destJoin.addJoinColumn(new DeployTableJoinColumn(fkCol, otherIds[i].getDbColumn()));
            }
        }
        DeployTableJoin inverseDest = destJoin.createInverse(intTableName);
        prop.setInverseJoin(inverseDest);
    }

    private String errorMsgMissingBeanTable(Class<?> type, String from) {
        return "Error with association to [" + type + "] from [" + from + "]. Is " + type + " registered?";
    }

    private void readToMany(ManyToMany propAnn, DeployBeanPropertyAssocMany<?> manyProp) {
        manyProp.setMappedBy(propAnn.mappedBy());
        manyProp.setFetchType(propAnn.fetch());
        this.setCascadeTypes(propAnn.cascade(), manyProp.getCascadeInfo());
        Class targetType = propAnn.targetEntity();
        if (targetType.equals(Void.TYPE)) {
            targetType = manyProp.getTargetType();
        } else {
            manyProp.setTargetType(targetType);
        }
        BeanTable assoc = this.factory.getBeanTable(targetType);
        if (assoc == null) {
            String msg = this.errorMsgMissingBeanTable(targetType, manyProp.getFullBeanName());
            throw new RuntimeException(msg);
        }
        manyProp.setManyToMany(true);
        manyProp.setModifyListenMode(BeanCollection.ModifyListenMode.ALL);
        manyProp.setBeanTable(assoc);
        manyProp.getTableJoin().setType("left outer join");
    }

    private void readToOne(OneToMany propAnn, DeployBeanPropertyAssocMany<?> manyProp) {
        manyProp.setMappedBy(propAnn.mappedBy());
        manyProp.setFetchType(propAnn.fetch());
        this.setCascadeTypes(propAnn.cascade(), manyProp.getCascadeInfo());
        Class targetType = propAnn.targetEntity();
        if (targetType.equals(Void.TYPE)) {
            targetType = manyProp.getTargetType();
        } else {
            manyProp.setTargetType(targetType);
        }
        BeanTable assoc = this.factory.getBeanTable(targetType);
        if (assoc == null) {
            String msg = this.errorMsgMissingBeanTable(targetType, manyProp.getFullBeanName());
            throw new RuntimeException(msg);
        }
        manyProp.setBeanTable(assoc);
        manyProp.getTableJoin().setType("left outer join");
    }

    private String getM2MJoinTableName(BeanTable lhsTable, BeanTable rhsTable) {
        TableName lhs = new TableName(lhsTable.getBaseTable());
        TableName rhs = new TableName(rhsTable.getBaseTable());
        TableName joinTable = this.namingConvention.getM2MJoinTableName(lhs, rhs);
        return joinTable.getQualifiedName();
    }
}

