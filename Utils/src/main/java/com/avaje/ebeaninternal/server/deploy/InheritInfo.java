/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebeaninternal.server.core.InternString;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.DbReadContext;
import com.avaje.ebeaninternal.server.deploy.InheritInfoVisitor;
import com.avaje.ebeaninternal.server.deploy.id.IdBinder;
import com.avaje.ebeaninternal.server.deploy.parse.DeployInheritInfo;
import com.avaje.ebeaninternal.server.query.SqlTreeProperties;
import com.avaje.ebeaninternal.server.subclass.SubClassUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.persistence.PersistenceException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class InheritInfo {
    private final String discriminatorStringValue;
    private final Object discriminatorValue;
    private final String discriminatorColumn;
    private final int discriminatorType;
    private final int discriminatorLength;
    private final String where;
    private final Class<?> type;
    private final ArrayList<InheritInfo> children = new ArrayList();
    private final HashMap<String, InheritInfo> discMap;
    private final HashMap<String, InheritInfo> typeMap;
    private final InheritInfo parent;
    private final InheritInfo root;
    private BeanDescriptor<?> descriptor;

    public InheritInfo(InheritInfo r, InheritInfo parent, DeployInheritInfo deploy) {
        this.parent = parent;
        this.type = deploy.getType();
        this.discriminatorColumn = InternString.intern(deploy.getDiscriminatorColumn(parent));
        this.discriminatorValue = deploy.getDiscriminatorObjectValue();
        this.discriminatorStringValue = deploy.getDiscriminatorStringValue();
        this.discriminatorType = deploy.getDiscriminatorType(parent);
        this.discriminatorLength = deploy.getDiscriminatorLength(parent);
        this.where = InternString.intern(deploy.getWhere());
        if (r == null) {
            this.root = this;
            this.discMap = new HashMap();
            this.typeMap = new HashMap();
            this.registerWithRoot(this);
        } else {
            this.root = r;
            this.discMap = null;
            this.typeMap = null;
            this.root.registerWithRoot(this);
        }
    }

    public void visitChildren(InheritInfoVisitor visitor) {
        for (int i = 0; i < this.children.size(); ++i) {
            InheritInfo child = this.children.get(i);
            visitor.visit(child);
            child.visitChildren(visitor);
        }
    }

    public boolean isSaveRecurseSkippable() {
        return this.root.isNodeSaveRecurseSkippable();
    }

    private boolean isNodeSaveRecurseSkippable() {
        if (!this.descriptor.isSaveRecurseSkippable()) {
            return false;
        }
        for (int i = 0; i < this.children.size(); ++i) {
            InheritInfo child = this.children.get(i);
            if (child.isNodeSaveRecurseSkippable()) continue;
            return false;
        }
        return true;
    }

    public boolean isDeleteRecurseSkippable() {
        return this.root.isNodeDeleteRecurseSkippable();
    }

    private boolean isNodeDeleteRecurseSkippable() {
        if (!this.descriptor.isDeleteRecurseSkippable()) {
            return false;
        }
        for (int i = 0; i < this.children.size(); ++i) {
            InheritInfo child = this.children.get(i);
            if (child.isNodeDeleteRecurseSkippable()) continue;
            return false;
        }
        return true;
    }

    public void setDescriptor(BeanDescriptor<?> descriptor) {
        this.descriptor = descriptor;
    }

    public BeanDescriptor<?> getBeanDescriptor() {
        return this.descriptor;
    }

    public BeanProperty findSubTypeProperty(String propertyName) {
        BeanProperty prop = null;
        int x = this.children.size();
        for (int i = 0; i < x; ++i) {
            InheritInfo childInfo = this.children.get(i);
            prop = childInfo.getBeanDescriptor().findBeanProperty(propertyName);
            if (prop == null) continue;
            return prop;
        }
        return null;
    }

    public void addChildrenProperties(SqlTreeProperties selectProps) {
        int x = this.children.size();
        for (int i = 0; i < x; ++i) {
            InheritInfo childInfo = this.children.get(i);
            selectProps.add(childInfo.descriptor.propertiesLocal());
            childInfo.addChildrenProperties(selectProps);
        }
    }

    public InheritInfo readType(DbReadContext ctx) throws SQLException {
        String discValue = ctx.getDataReader().getString();
        return this.readType(discValue);
    }

    public InheritInfo readType(String discValue) {
        if (discValue == null) {
            return null;
        }
        InheritInfo typeInfo = this.root.getType(discValue);
        if (typeInfo == null) {
            String m = "Inheritance type for discriminator value [" + discValue + "] was not found?";
            throw new PersistenceException(m);
        }
        return typeInfo;
    }

    public InheritInfo readType(Class<?> beanType) {
        InheritInfo typeInfo = this.root.getTypeByClass(beanType);
        if (typeInfo == null) {
            String m = "Inheritance type for bean type [" + beanType.getName() + "] was not found?";
            throw new PersistenceException(m);
        }
        return typeInfo;
    }

    public Object createBean(boolean vanillaMode) {
        return this.descriptor.createBean(vanillaMode);
    }

    public IdBinder getIdBinder() {
        return this.descriptor.getIdBinder();
    }

    public Class<?> getType() {
        return this.type;
    }

    public InheritInfo getRoot() {
        return this.root;
    }

    public InheritInfo getParent() {
        return this.parent;
    }

    public boolean isAbstract() {
        return this.discriminatorValue == null;
    }

    public boolean isRoot() {
        return this.parent == null;
    }

    public InheritInfo getType(String discValue) {
        return this.discMap.get(discValue);
    }

    private InheritInfo getTypeByClass(Class<?> beanType) {
        String clsName = SubClassUtil.getSuperClassName(beanType.getName());
        return this.typeMap.get(clsName);
    }

    private void registerWithRoot(InheritInfo info) {
        if (info.getDiscriminatorStringValue() != null) {
            String stringDiscValue = info.getDiscriminatorStringValue();
            this.discMap.put(stringDiscValue, info);
        }
        String clsName = SubClassUtil.getSuperClassName(info.getType().getName());
        this.typeMap.put(clsName, info);
    }

    public void addChild(InheritInfo childInfo) {
        this.children.add(childInfo);
    }

    public String getWhere() {
        return this.where;
    }

    public String getDiscriminatorColumn() {
        return this.discriminatorColumn;
    }

    public int getDiscriminatorType() {
        return this.discriminatorType;
    }

    public int getDiscriminatorLength() {
        return this.discriminatorLength;
    }

    public String getDiscriminatorStringValue() {
        return this.discriminatorStringValue;
    }

    public Object getDiscriminatorValue() {
        return this.discriminatorValue;
    }

    public String toString() {
        return "InheritInfo[" + this.type.getName() + "] disc[" + this.discriminatorStringValue + "]";
    }
}

