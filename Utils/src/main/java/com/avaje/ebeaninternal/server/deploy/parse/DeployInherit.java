/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy.parse;

import com.avaje.ebeaninternal.server.core.BootupClasses;
import com.avaje.ebeaninternal.server.deploy.InheritInfo;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.parse.DeployInheritInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Inheritance;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class DeployInherit {
    private final Map<Class<?>, DeployInheritInfo> deployMap = new LinkedHashMap();
    private final Map<Class<?>, InheritInfo> finalMap = new LinkedHashMap();
    private final BootupClasses bootupClasses;

    public DeployInherit(BootupClasses bootupClasses) {
        this.bootupClasses = bootupClasses;
        this.initialise();
    }

    public void process(DeployBeanDescriptor<?> desc) {
        InheritInfo inheritInfo = this.finalMap.get(desc.getBeanType());
        desc.setInheritInfo(inheritInfo);
    }

    private void initialise() {
        ArrayList<Class<?>> entityList = this.bootupClasses.getEntities();
        this.findInheritClasses(entityList);
        this.buildDeployTree();
        this.buildFinalTree();
    }

    private void findInheritClasses(List<Class<?>> entityList) {
        for (Class<?> cls : entityList) {
            if (!this.isInheritanceClass(cls)) continue;
            DeployInheritInfo info = this.createInfo(cls);
            this.deployMap.put(cls, info);
        }
    }

    private void buildDeployTree() {
        for (DeployInheritInfo info : this.deployMap.values()) {
            if (info.isRoot()) continue;
            DeployInheritInfo parent = this.getInfo(info.getParent());
            parent.addChild(info);
        }
    }

    private void buildFinalTree() {
        for (DeployInheritInfo deploy : this.deployMap.values()) {
            if (!deploy.isRoot()) continue;
            this.createFinalInfo(null, null, deploy);
        }
    }

    private InheritInfo createFinalInfo(InheritInfo root, InheritInfo parent, DeployInheritInfo deploy) {
        InheritInfo node = new InheritInfo(root, parent, deploy);
        if (parent != null) {
            parent.addChild(node);
        }
        this.finalMap.put(node.getType(), node);
        if (root == null) {
            root = node;
        }
        Iterator<DeployInheritInfo> it = deploy.children();
        while (it.hasNext()) {
            DeployInheritInfo childDeploy = it.next();
            this.createFinalInfo(root, node, childDeploy);
        }
        return node;
    }

    private DeployInheritInfo getInfo(Class<?> cls) {
        return this.deployMap.get(cls);
    }

    private DeployInheritInfo createInfo(Class<?> cls) {
        DiscriminatorValue dv;
        DiscriminatorColumn da;
        Inheritance ia;
        DeployInheritInfo info = new DeployInheritInfo(cls);
        Class<?> parent = this.findParent(cls);
        if (parent != null) {
            info.setParent(parent);
        }
        if ((ia = cls.getAnnotation(Inheritance.class)) != null) {
            ia.strategy();
        }
        if ((da = cls.getAnnotation(DiscriminatorColumn.class)) != null) {
            info.setDiscriminatorColumn(da.name());
            DiscriminatorType discriminatorType = da.discriminatorType();
            if (discriminatorType.equals((Object)DiscriminatorType.INTEGER)) {
                info.setDiscriminatorType(4);
            } else {
                info.setDiscriminatorType(12);
            }
            info.setDiscriminatorLength(da.length());
        }
        if ((dv = cls.getAnnotation(DiscriminatorValue.class)) != null) {
            info.setDiscriminatorValue(dv.value());
        }
        return info;
    }

    private Class<?> findParent(Class<?> cls) {
        Class<?> superCls = cls.getSuperclass();
        if (this.isInheritanceClass(superCls)) {
            return superCls;
        }
        return null;
    }

    private boolean isInheritanceClass(Class<?> cls) {
        if (cls.equals(Object.class)) {
            return false;
        }
        Inheritance a = cls.getAnnotation(Inheritance.class);
        if (a != null) {
            return true;
        }
        return this.isInheritanceClass(cls.getSuperclass());
    }
}

