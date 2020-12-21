/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy.parse;

import com.avaje.ebean.annotation.EmbeddedColumns;
import com.avaje.ebean.annotation.Where;
import com.avaje.ebean.config.NamingConvention;
import com.avaje.ebean.validation.NotNull;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptorManager;
import com.avaje.ebeaninternal.server.deploy.BeanTable;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.deploy.parse.AnnotationParser;
import com.avaje.ebeaninternal.server.deploy.parse.DeployBeanInfo;
import com.avaje.ebeaninternal.server.lib.util.StringHelper;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class AnnotationAssocOnes
extends AnnotationParser {
    private final BeanDescriptorManager factory;

    public AnnotationAssocOnes(DeployBeanInfo<?> info, BeanDescriptorManager factory) {
        super(info);
        this.factory = factory;
    }

    @Override
    public void parse() {
        Iterator<DeployBeanProperty> it = this.descriptor.propertiesAll();
        while (it.hasNext()) {
            DeployBeanProperty prop = it.next();
            if (!(prop instanceof DeployBeanPropertyAssocOne)) continue;
            this.readAssocOne((DeployBeanPropertyAssocOne)prop);
        }
    }

    private void readAssocOne(DeployBeanPropertyAssocOne<?> prop) {
        JoinTable joinTable;
        JoinColumns joinColumns;
        NotNull notNull;
        Where where;
        Id id;
        Column column;
        EmbeddedId emId;
        Embedded embedded;
        OneToOne oneToOne;
        ManyToOne manyToOne = this.get(prop, ManyToOne.class);
        if (manyToOne != null) {
            this.readManyToOne(manyToOne, prop);
        }
        if ((oneToOne = this.get(prop, OneToOne.class)) != null) {
            this.readOneToOne(oneToOne, prop);
        }
        if ((embedded = this.get(prop, Embedded.class)) != null) {
            this.readEmbedded(embedded, prop);
        }
        if ((emId = this.get(prop, EmbeddedId.class)) != null) {
            prop.setEmbedded(true);
            prop.setId(true);
            prop.setNullable(false);
        }
        if ((column = this.get(prop, Column.class)) != null && !this.isEmpty(column.name())) {
            prop.setDbColumn(column.name());
        }
        if ((id = this.get(prop, Id.class)) != null) {
            prop.setEmbedded(true);
            prop.setId(true);
            prop.setNullable(false);
        }
        if ((where = this.get(prop, Where.class)) != null) {
            prop.setExtraWhere(where.clause());
        }
        if ((notNull = this.get(prop, NotNull.class)) != null) {
            prop.setNullable(false);
            prop.getTableJoin().setType("join");
        }
        BeanTable beanTable = prop.getBeanTable();
        JoinColumn joinColumn = this.get(prop, JoinColumn.class);
        if (joinColumn != null) {
            prop.getTableJoin().addJoinColumn(false, joinColumn, beanTable);
            if (!joinColumn.updatable()) {
                prop.setDbUpdateable(false);
            }
        }
        if ((joinColumns = this.get(prop, JoinColumns.class)) != null) {
            prop.getTableJoin().addJoinColumn(false, joinColumns.value(), beanTable);
        }
        if ((joinTable = this.get(prop, JoinTable.class)) != null) {
            prop.getTableJoin().addJoinColumn(false, joinTable.joinColumns(), beanTable);
        }
        this.info.setBeanJoinType(prop, prop.isNullable());
        if (!prop.getTableJoin().hasJoinColumns() && beanTable != null && prop.getMappedBy() == null) {
            NamingConvention nc = this.factory.getNamingConvention();
            String fkeyPrefix = null;
            if (nc.isUseForeignKeyPrefix()) {
                fkeyPrefix = nc.getColumnFromProperty(this.beanType, prop.getName());
            }
            beanTable.createJoinColumn(fkeyPrefix, prop.getTableJoin(), true);
        }
    }

    private String errorMsgMissingBeanTable(Class<?> type, String from) {
        return "Error with association to [" + type + "] from [" + from + "]. Is " + type + " registered?";
    }

    private void readManyToOne(ManyToOne propAnn, DeployBeanProperty prop) {
        DeployBeanPropertyAssocOne beanProp = (DeployBeanPropertyAssocOne)prop;
        this.setCascadeTypes(propAnn.cascade(), beanProp.getCascadeInfo());
        BeanTable assoc = this.factory.getBeanTable(beanProp.getPropertyType());
        if (assoc == null) {
            String msg = this.errorMsgMissingBeanTable(beanProp.getPropertyType(), prop.getFullBeanName());
            throw new RuntimeException(msg);
        }
        beanProp.setBeanTable(assoc);
        beanProp.setDbInsertable(true);
        beanProp.setDbUpdateable(true);
        beanProp.setNullable(propAnn.optional());
        beanProp.setFetchType(propAnn.fetch());
    }

    private void readOneToOne(OneToOne propAnn, DeployBeanPropertyAssocOne<?> prop) {
        prop.setOneToOne(true);
        prop.setDbInsertable(true);
        prop.setDbUpdateable(true);
        prop.setNullable(propAnn.optional());
        prop.setFetchType(propAnn.fetch());
        prop.setMappedBy(propAnn.mappedBy());
        if (!"".equals(propAnn.mappedBy())) {
            prop.setOneToOneExported(true);
        }
        this.setCascadeTypes(propAnn.cascade(), prop.getCascadeInfo());
        BeanTable assoc = this.factory.getBeanTable(prop.getPropertyType());
        if (assoc == null) {
            String msg = this.errorMsgMissingBeanTable(prop.getPropertyType(), prop.getFullBeanName());
            throw new RuntimeException(msg);
        }
        prop.setBeanTable(assoc);
    }

    private void readEmbedded(Embedded propAnn, DeployBeanPropertyAssocOne<?> prop) {
        AttributeOverrides attrOverrides;
        Map<String, String> propMap;
        prop.setEmbedded(true);
        prop.setDbInsertable(true);
        prop.setDbUpdateable(true);
        EmbeddedColumns columns = this.get(prop, EmbeddedColumns.class);
        if (columns != null) {
            String propColumns = columns.columns();
            propMap = StringHelper.delimitedToMap(propColumns, ",", "=");
            prop.getDeployEmbedded().putAll(propMap);
        }
        if ((attrOverrides = this.get(prop, AttributeOverrides.class)) != null) {
            propMap = new HashMap<String, String>();
            AttributeOverride[] aoArray = attrOverrides.value();
            for (int i = 0; i < aoArray.length; ++i) {
                String propName = aoArray[i].name();
                String columnName = aoArray[i].column().name();
                ((HashMap)propMap).put(propName, columnName);
            }
            prop.getDeployEmbedded().putAll(propMap);
        }
    }
}

