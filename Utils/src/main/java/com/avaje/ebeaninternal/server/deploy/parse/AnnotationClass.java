/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  javax.xml.bind.annotation.XmlRootElement
 *  javax.xml.bind.annotation.XmlType
 */
package com.avaje.ebeaninternal.server.deploy.parse;

import com.avaje.ebean.Query;
import com.avaje.ebean.annotation.CacheStrategy;
import com.avaje.ebean.annotation.LdapDomain;
import com.avaje.ebean.annotation.NamedUpdate;
import com.avaje.ebean.annotation.NamedUpdates;
import com.avaje.ebean.annotation.UpdateMode;
import com.avaje.ebean.config.TableName;
import com.avaje.ebeaninternal.server.core.ReferenceOptions;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.CompoundUniqueContraint;
import com.avaje.ebeaninternal.server.deploy.DeployNamedQuery;
import com.avaje.ebeaninternal.server.deploy.DeployNamedUpdate;
import com.avaje.ebeaninternal.server.deploy.parse.AnnotationParser;
import com.avaje.ebeaninternal.server.deploy.parse.DeployBeanInfo;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class AnnotationClass
extends AnnotationParser {
    public AnnotationClass(DeployBeanInfo<?> info) {
        super(info);
    }

    @Override
    public void parse() {
        this.read(this.descriptor.getBeanType());
        this.setTableName();
    }

    private void setTableName() {
        if (this.descriptor.isBaseTableType()) {
            TableName tableName = this.namingConvention.getTableName(this.descriptor.getBeanType());
            this.descriptor.setBaseTable(tableName);
        }
    }

    private String[] parseLdapObjectclasses(String objectclasses) {
        if (objectclasses == null || objectclasses.length() == 0) {
            return null;
        }
        return objectclasses.split(",");
    }

    private boolean isXmlElement(Class<?> cls) {
        XmlRootElement rootElement = cls.getAnnotation(XmlRootElement.class);
        if (rootElement != null) {
            return true;
        }
        XmlType xmlType = cls.getAnnotation(XmlType.class);
        return xmlType != null;
    }

    private void read(Class<?> cls) {
        CacheStrategy cacheStrategy;
        NamedUpdate namedUpdate;
        NamedUpdates namedUpdates;
        NamedQuery namedQuery;
        NamedQueries namedQueries;
        UpdateMode updateMode;
        UniqueConstraint[] uniqueConstraints;
        Table table;
        UniqueConstraint uc;
        Entity entity;
        LdapDomain ldapDomain = cls.getAnnotation(LdapDomain.class);
        if (ldapDomain != null) {
            this.descriptor.setName(cls.getSimpleName());
            this.descriptor.setEntityType(BeanDescriptor.EntityType.LDAP);
            this.descriptor.setLdapBaseDn(ldapDomain.baseDn());
            this.descriptor.setLdapObjectclasses(this.parseLdapObjectclasses(ldapDomain.objectclass()));
        }
        if ((entity = cls.getAnnotation(Entity.class)) != null) {
            if (entity.name().equals("")) {
                this.descriptor.setName(cls.getSimpleName());
            } else {
                this.descriptor.setName(entity.name());
            }
        } else if (this.isXmlElement(cls)) {
            this.descriptor.setName(cls.getSimpleName());
            this.descriptor.setEntityType(BeanDescriptor.EntityType.XMLELEMENT);
        }
        Embeddable embeddable = cls.getAnnotation(Embeddable.class);
        if (embeddable != null) {
            this.descriptor.setEntityType(BeanDescriptor.EntityType.EMBEDDED);
            this.descriptor.setName("Embeddable:" + cls.getSimpleName());
        }
        if ((uc = cls.getAnnotation(UniqueConstraint.class)) != null) {
            this.descriptor.addCompoundUniqueConstraint(new CompoundUniqueContraint(uc.columnNames()));
        }
        if ((table = cls.getAnnotation(Table.class)) != null && (uniqueConstraints = table.uniqueConstraints()) != null) {
            for (UniqueConstraint c : uniqueConstraints) {
                this.descriptor.addCompoundUniqueConstraint(new CompoundUniqueContraint(c.columnNames()));
            }
        }
        if ((updateMode = cls.getAnnotation(UpdateMode.class)) != null) {
            this.descriptor.setUpdateChangesOnly(updateMode.updateChangesOnly());
        }
        if ((namedQueries = cls.getAnnotation(NamedQueries.class)) != null) {
            this.readNamedQueries(namedQueries);
        }
        if ((namedQuery = cls.getAnnotation(NamedQuery.class)) != null) {
            this.readNamedQuery(namedQuery);
        }
        if ((namedUpdates = cls.getAnnotation(NamedUpdates.class)) != null) {
            this.readNamedUpdates(namedUpdates);
        }
        if ((namedUpdate = cls.getAnnotation(NamedUpdate.class)) != null) {
            this.readNamedUpdate(namedUpdate);
        }
        if ((cacheStrategy = cls.getAnnotation(CacheStrategy.class)) != null) {
            this.readCacheStrategy(cacheStrategy);
        }
    }

    private void readCacheStrategy(CacheStrategy cacheStrategy) {
        boolean useCache = cacheStrategy.useBeanCache();
        boolean readOnly = cacheStrategy.readOnly();
        String warmingQuery = cacheStrategy.warmingQuery();
        ReferenceOptions opt = new ReferenceOptions(useCache, readOnly, warmingQuery);
        this.descriptor.setReferenceOptions(opt);
        if (!Query.UseIndex.DEFAULT.equals((Object)cacheStrategy.useIndex())) {
            this.descriptor.setUseIndex(cacheStrategy.useIndex());
        }
    }

    private void readNamedQueries(NamedQueries namedQueries) {
        NamedQuery[] queries = namedQueries.value();
        for (int i = 0; i < queries.length; ++i) {
            this.readNamedQuery(queries[i]);
        }
    }

    private void readNamedQuery(NamedQuery namedQuery) {
        DeployNamedQuery q = new DeployNamedQuery(namedQuery);
        this.descriptor.add(q);
    }

    private void readNamedUpdates(NamedUpdates updates) {
        NamedUpdate[] updateArray = updates.value();
        for (int i = 0; i < updateArray.length; ++i) {
            this.readNamedUpdate(updateArray[i]);
        }
    }

    private void readNamedUpdate(NamedUpdate update) {
        DeployNamedUpdate upd = new DeployNamedUpdate(update);
        this.descriptor.add(upd);
    }
}

