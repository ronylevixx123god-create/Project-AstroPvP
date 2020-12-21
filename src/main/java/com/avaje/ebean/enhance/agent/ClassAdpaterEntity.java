/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.enhance.agent;

import com.avaje.ebean.enhance.agent.ClassMeta;
import com.avaje.ebean.enhance.agent.ConstructorAdapter;
import com.avaje.ebean.enhance.agent.DefaultConstructor;
import com.avaje.ebean.enhance.agent.EnhanceConstants;
import com.avaje.ebean.enhance.agent.EnhanceContext;
import com.avaje.ebean.enhance.agent.IndexFieldWeaver;
import com.avaje.ebean.enhance.agent.InterceptField;
import com.avaje.ebean.enhance.agent.MarkerField;
import com.avaje.ebean.enhance.agent.MethodEquals;
import com.avaje.ebean.enhance.agent.MethodFieldAdapter;
import com.avaje.ebean.enhance.agent.MethodIsEmbeddedNewOrDirty;
import com.avaje.ebean.enhance.agent.MethodNewInstance;
import com.avaje.ebean.enhance.agent.MethodPropertyChangeListener;
import com.avaje.ebean.enhance.agent.MethodSetEmbeddedLoaded;
import com.avaje.ebean.enhance.agent.NoEnhancementRequiredException;
import com.avaje.ebean.enhance.asm.AnnotationVisitor;
import com.avaje.ebean.enhance.asm.ClassAdapter;
import com.avaje.ebean.enhance.asm.ClassVisitor;
import com.avaje.ebean.enhance.asm.FieldVisitor;
import com.avaje.ebean.enhance.asm.MethodVisitor;

public class ClassAdpaterEntity
extends ClassAdapter
implements EnhanceConstants {
    private final EnhanceContext enhanceContext;
    private final ClassLoader classLoader;
    private final ClassMeta classMeta;
    private boolean firstMethod = true;

    public ClassAdpaterEntity(ClassVisitor cv, ClassLoader classLoader, EnhanceContext context) {
        super(cv);
        this.classLoader = classLoader;
        this.enhanceContext = context;
        this.classMeta = context.createClassMeta();
    }

    public void logEnhanced() {
        this.classMeta.logEnhanced();
    }

    public boolean isLog(int level) {
        return this.classMeta.isLog(level);
    }

    public void log(String msg) {
        this.classMeta.log(msg);
    }

    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.classMeta.setClassName(name, superName);
        int n = 1 + interfaces.length;
        String[] c = new String[n];
        for (int i = 0; i < interfaces.length; ++i) {
            c[i] = interfaces[i];
            if (c[i].equals("com/avaje/ebean/bean/EntityBean")) {
                this.classMeta.setEntityBeanInterface(true);
            }
            if (c[i].equals("scala/ScalaObject")) {
                this.classMeta.setScalaInterface(true);
            }
            if (!c[i].equals("groovy/lang/GroovyObject")) continue;
            this.classMeta.setGroovyInterface(true);
        }
        if (this.classMeta.hasEntityBeanInterface()) {
            c = interfaces;
        } else {
            c[c.length - 1] = "com/avaje/ebean/bean/EntityBean";
        }
        if (!superName.equals("java/lang/Object")) {
            ClassMeta superMeta;
            if (this.classMeta.isLog(7)) {
                this.classMeta.log("read information about superClasses " + superName + " to see if it is entity/embedded/mappedSuperclass");
            }
            if ((superMeta = this.enhanceContext.getSuperMeta(superName, this.classLoader)) != null && superMeta.isEntity()) {
                this.classMeta.setSuperMeta(superMeta);
                if (this.classMeta.isLog(1)) {
                    this.classMeta.log("entity extends " + superMeta.getDescription());
                }
            } else if (this.classMeta.isLog(7)) {
                if (superMeta == null) {
                    this.classMeta.log("unable to read superMeta for " + superName);
                } else {
                    this.classMeta.log("superMeta " + superName + " is not an entity/embedded/mappedsuperclass " + superMeta.getClassAnnotations());
                }
            }
        }
        super.visit(version, access, name, signature, superName, c);
    }

    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        this.classMeta.addClassAnnotation(desc);
        return super.visitAnnotation(desc, visible);
    }

    private boolean isEbeanFieldMarker(String name, String desc, String signature) {
        if (name.equals("_EBEAN_MARKER")) {
            if (!desc.equals("Ljava/lang/String;")) {
                String m = "Error: _EBEAN_MARKER field of wrong type? " + desc;
                this.classMeta.log(m);
            }
            return true;
        }
        return false;
    }

    private boolean isPropertyChangeListenerField(String name, String desc, String signature) {
        return desc.equals("Ljava/beans/PropertyChangeSupport;");
    }

    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        if ((access & 8) != 0) {
            if (this.isEbeanFieldMarker(name, desc, signature)) {
                this.classMeta.setAlreadyEnhanced(true);
                if (this.isLog(2)) {
                    this.log("Found ebean marker field " + name + " " + value);
                }
            } else if (this.isLog(2)) {
                this.log("Skip intercepting static field " + name);
            }
            return super.visitField(access, name, desc, signature, value);
        }
        if (this.isPropertyChangeListenerField(name, desc, signature)) {
            if (this.isLog(1)) {
                this.classMeta.log("Found existing PropertyChangeSupport field " + name);
            }
            return super.visitField(access, name, desc, signature, value);
        }
        if ((access & 0x80) != 0) {
            if (this.isLog(2)) {
                this.log("Skip intercepting transient field " + name);
            }
            return super.visitField(access, name, desc, signature, value);
        }
        FieldVisitor fv = super.visitField(access, name, desc, signature, value);
        return this.classMeta.createLocalFieldVisitor(this.cv, fv, name, desc);
    }

    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv;
        if (this.firstMethod) {
            if (!this.classMeta.isEntityEnhancementRequired()) {
                throw new NoEnhancementRequiredException();
            }
            if (this.classMeta.hasEntityBeanInterface()) {
                this.log("Enhancing when EntityBean interface already exists!");
            }
            String marker = MarkerField.addField(this.cv, this.classMeta.getClassName());
            if (this.isLog(4)) {
                this.log("... add marker field \"" + marker + "\"");
            }
            if (!this.classMeta.isSuperClassEntity()) {
                if (this.isLog(4)) {
                    this.log("... add intercept and identity fields");
                }
                InterceptField.addField(this.cv, this.enhanceContext.isTransientInternalFields());
                MethodEquals.addIdentityField(this.cv);
            }
            this.firstMethod = false;
        }
        this.classMeta.addExistingMethod(name, desc);
        if (this.isDefaultConstructor(name, desc)) {
            mv = super.visitMethod(1, name, desc, signature, exceptions);
            return new ConstructorAdapter(mv, this.classMeta, desc);
        }
        mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (this.interceptEntityMethod(access, name, desc, signature, exceptions)) {
            return new MethodFieldAdapter(mv, this.classMeta, name + " " + desc);
        }
        return mv;
    }

    public void visitEnd() {
        if (!this.classMeta.isEntityEnhancementRequired()) {
            throw new NoEnhancementRequiredException();
        }
        if (!this.classMeta.hasDefaultConstructor()) {
            DefaultConstructor.add(this.cv, this.classMeta);
        }
        MarkerField.addGetMarker(this.cv, this.classMeta.getClassName());
        if (!this.classMeta.isSuperClassEntity()) {
            InterceptField.addGetterSetter(this.cv, this.classMeta.getClassName());
            MethodPropertyChangeListener.addMethod(this.cv, this.classMeta);
        }
        this.classMeta.addFieldGetSetMethods(this.cv);
        IndexFieldWeaver.addMethods(this.cv, this.classMeta);
        MethodSetEmbeddedLoaded.addMethod(this.cv, this.classMeta);
        MethodIsEmbeddedNewOrDirty.addMethod(this.cv, this.classMeta);
        MethodNewInstance.addMethod(this.cv, this.classMeta);
        this.enhanceContext.addClassMeta(this.classMeta);
        super.visitEnd();
    }

    private boolean isDefaultConstructor(String name, String desc) {
        if (name.equals("<init>")) {
            if (desc.equals("()V")) {
                this.classMeta.setHasDefaultConstructor(true);
            }
            return true;
        }
        return false;
    }

    private boolean interceptEntityMethod(int access, String name, String desc, String signature, String[] exceptions) {
        if ((access & 8) != 0) {
            if (this.isLog(2)) {
                this.log("Skip intercepting static method " + name);
            }
            return false;
        }
        if (name.equals("hashCode") && desc.equals("()I")) {
            this.classMeta.setHasEqualsOrHashcode(true);
            return true;
        }
        if (name.equals("equals") && desc.equals("(Ljava/lang/Object;)Z")) {
            this.classMeta.setHasEqualsOrHashcode(true);
            return true;
        }
        return !name.equals("toString") || !desc.equals("()Ljava/lang/String;");
    }
}

