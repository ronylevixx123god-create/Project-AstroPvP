/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.enhance.agent;

import com.avaje.ebean.enhance.agent.AnnotationInfo;
import com.avaje.ebean.enhance.agent.AnnotationInfoVisitor;
import com.avaje.ebean.enhance.agent.EnhanceContext;
import com.avaje.ebean.enhance.agent.FieldMeta;
import com.avaje.ebean.enhance.agent.LocalFieldVisitor;
import com.avaje.ebean.enhance.agent.MethodMeta;
import com.avaje.ebean.enhance.asm.AnnotationVisitor;
import com.avaje.ebean.enhance.asm.ClassVisitor;
import com.avaje.ebean.enhance.asm.EmptyVisitor;
import com.avaje.ebean.enhance.asm.FieldVisitor;
import com.avaje.ebean.enhance.asm.MethodVisitor;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ClassMeta {
    private static final Logger logger = Logger.getLogger(ClassMeta.class.getName());
    private static final String OBJECT_CLASS = Object.class.getName().replace('.', '/');
    private final PrintStream logout;
    private final int logLevel;
    private final boolean subclassing;
    private String className;
    private String superClassName;
    private ClassMeta superMeta;
    private boolean hasGroovyInterface;
    private boolean hasScalaInterface;
    private boolean hasEntityBeanInterface;
    private boolean alreadyEnhanced;
    private boolean hasEqualsOrHashcode;
    private boolean hasDefaultConstructor;
    private HashSet<String> existingMethods = new HashSet();
    private HashSet<String> existingSuperMethods = new HashSet();
    private LinkedHashMap<String, FieldMeta> fields = new LinkedHashMap();
    private HashSet<String> classAnnotation = new HashSet();
    private AnnotationInfo annotationInfo = new AnnotationInfo(null);
    private ArrayList<MethodMeta> methodMetaList = new ArrayList();
    private final EnhanceContext enhanceContext;

    public ClassMeta(EnhanceContext enhanceContext, boolean subclassing, int logLevel, PrintStream logout) {
        this.enhanceContext = enhanceContext;
        this.subclassing = subclassing;
        this.logLevel = logLevel;
        this.logout = logout;
    }

    public EnhanceContext getEnhanceContext() {
        return this.enhanceContext;
    }

    public Set<String> getClassAnnotations() {
        return this.classAnnotation;
    }

    public AnnotationInfo getAnnotationInfo() {
        return this.annotationInfo;
    }

    public AnnotationInfo getInterfaceTransactionalInfo(String methodName, String methodDesc) {
        AnnotationInfo annotationInfo = null;
        for (int i = 0; i < this.methodMetaList.size(); ++i) {
            MethodMeta meta = this.methodMetaList.get(i);
            if (!meta.isMatch(methodName, methodDesc)) continue;
            if (annotationInfo != null) {
                String msg = "Error in [" + this.className + "] searching the transactional methods[" + this.methodMetaList + "] found more than one match for the transactional method:" + methodName + " " + methodDesc;
                logger.log(Level.SEVERE, msg);
                this.log(msg);
                continue;
            }
            annotationInfo = meta.getAnnotationInfo();
            if (!this.isLog(9)) continue;
            this.log("... found transactional info from interface " + this.className + " " + methodName + " " + methodDesc);
        }
        return annotationInfo;
    }

    public boolean isCheckSuperClassForEntity() {
        if (this.isEntity()) {
            return !this.superClassName.equals(OBJECT_CLASS);
        }
        return false;
    }

    public String toString() {
        return this.className;
    }

    public boolean isTransactional() {
        return this.classAnnotation.contains("Lcom/avaje/ebean/annotation/Transactional;");
    }

    public ArrayList<MethodMeta> getMethodMeta() {
        return this.methodMetaList;
    }

    public void setClassName(String className, String superClassName) {
        this.className = className;
        this.superClassName = superClassName;
    }

    public String getSuperClassName() {
        return this.superClassName;
    }

    public boolean isSubclassing() {
        return this.subclassing;
    }

    public boolean isLog(int level) {
        return level <= this.logLevel;
    }

    public void log(String msg) {
        if (this.className != null) {
            msg = "cls: " + this.className + "  msg: " + msg;
        }
        this.logout.println("transform> " + msg);
    }

    public void logEnhanced() {
        String m = "enhanced ";
        if (this.hasScalaInterface()) {
            m = m + " (scala)";
        }
        if (this.hasGroovyInterface()) {
            m = m + " (groovy)";
        }
        this.log(m);
    }

    public boolean isInheritEqualsFromSuper() {
        return !this.subclassing && this.isSuperClassEntity();
    }

    public ClassMeta getSuperMeta() {
        return this.superMeta;
    }

    public void setSuperMeta(ClassMeta superMeta) {
        this.superMeta = superMeta;
    }

    public void setHasEqualsOrHashcode(boolean hasEqualsOrHashcode) {
        this.hasEqualsOrHashcode = hasEqualsOrHashcode;
    }

    public boolean hasEqualsOrHashCode() {
        return this.hasEqualsOrHashcode;
    }

    public boolean isFieldPersistent(String fieldName) {
        FieldMeta f = this.fields.get(fieldName);
        if (f != null) {
            return f.isPersistent();
        }
        if (this.superMeta == null) {
            return false;
        }
        return this.superMeta.isFieldPersistent(fieldName);
    }

    public List<FieldMeta> getLocalFields() {
        ArrayList<FieldMeta> list = new ArrayList<FieldMeta>();
        for (FieldMeta fm : this.fields.values()) {
            if (fm.isObjectArray()) continue;
            list.add(fm);
        }
        return list;
    }

    public List<FieldMeta> getInheritedFields() {
        return this.getInheritedFields(new ArrayList<FieldMeta>());
    }

    private List<FieldMeta> getInheritedFields(List<FieldMeta> list) {
        if (list == null) {
            list = new ArrayList<FieldMeta>();
        }
        if (this.superMeta != null) {
            this.superMeta.addFieldsForInheritance(list);
        }
        return list;
    }

    private void addFieldsForInheritance(List<FieldMeta> list) {
        if (this.isEntity()) {
            list.addAll(0, this.fields.values());
            if (this.superMeta != null) {
                this.superMeta.addFieldsForInheritance(list);
            }
        }
    }

    public List<FieldMeta> getAllFields() {
        List<FieldMeta> list = this.getLocalFields();
        this.getInheritedFields(list);
        return list;
    }

    public void addFieldGetSetMethods(ClassVisitor cv) {
        if (this.isEntityEnhancementRequired()) {
            for (FieldMeta fm : this.fields.values()) {
                fm.addGetSetMethods(cv, this);
            }
        }
    }

    public boolean isEntity() {
        if (this.classAnnotation.contains("Ljavax/persistence/Entity;")) {
            return true;
        }
        if (this.classAnnotation.contains("Ljavax/persistence/Embeddable;")) {
            return true;
        }
        if (this.classAnnotation.contains("Ljavax/persistence/MappedSuperclass;")) {
            return true;
        }
        return this.classAnnotation.contains("Lcom/avaje/ebean/annotation/LdapDomain;");
    }

    public boolean isEntityEnhancementRequired() {
        if (this.alreadyEnhanced) {
            return false;
        }
        return this.isEntity();
    }

    public String getClassName() {
        return this.className;
    }

    public boolean isSuperClassEntity() {
        if (this.superMeta == null) {
            return false;
        }
        return this.superMeta.isEntity();
    }

    public void addClassAnnotation(String desc) {
        this.classAnnotation.add(desc);
    }

    public void addExistingSuperMethod(String methodName, String methodDesc) {
        this.existingSuperMethods.add(methodName + methodDesc);
    }

    public void addExistingMethod(String methodName, String methodDesc) {
        this.existingMethods.add(methodName + methodDesc);
    }

    public boolean isExistingMethod(String methodName, String methodDesc) {
        return this.existingMethods.contains(methodName + methodDesc);
    }

    public boolean isExistingSuperMethod(String methodName, String methodDesc) {
        return this.existingSuperMethods.contains(methodName + methodDesc);
    }

    public MethodVisitor createMethodVisitor(MethodVisitor mv, int access, String name, String desc) {
        MethodMeta methodMeta = new MethodMeta(this.annotationInfo, access, name, desc);
        this.methodMetaList.add(methodMeta);
        return new MethodReader(mv, methodMeta);
    }

    public FieldVisitor createLocalFieldVisitor(String name, String desc) {
        return this.createLocalFieldVisitor(null, null, name, desc);
    }

    public FieldVisitor createLocalFieldVisitor(ClassVisitor cv, FieldVisitor fv, String name, String desc) {
        String fieldClass = this.subclassing ? this.superClassName : this.className;
        FieldMeta fieldMeta = new FieldMeta(this, name, desc, fieldClass);
        LocalFieldVisitor localField = new LocalFieldVisitor(cv, fv, fieldMeta);
        if (name.startsWith("_ebean")) {
            if (this.isLog(0)) {
                this.log("... ignore field " + name);
            }
        } else {
            this.fields.put(localField.getName(), fieldMeta);
        }
        return localField;
    }

    public boolean isAlreadyEnhanced() {
        return this.alreadyEnhanced;
    }

    public void setAlreadyEnhanced(boolean alreadyEnhanced) {
        this.alreadyEnhanced = alreadyEnhanced;
    }

    public boolean hasDefaultConstructor() {
        return this.hasDefaultConstructor;
    }

    public void setHasDefaultConstructor(boolean hasDefaultConstructor) {
        this.hasDefaultConstructor = hasDefaultConstructor;
    }

    public String getDescription() {
        StringBuilder sb = new StringBuilder();
        this.appendDescription(sb);
        return sb.toString();
    }

    private void appendDescription(StringBuilder sb) {
        sb.append(this.className);
        if (this.superMeta != null) {
            sb.append(" : ");
            this.superMeta.appendDescription(sb);
        }
    }

    public boolean hasScalaInterface() {
        return this.hasScalaInterface;
    }

    public void setScalaInterface(boolean hasScalaInterface) {
        this.hasScalaInterface = hasScalaInterface;
    }

    public boolean hasEntityBeanInterface() {
        return this.hasEntityBeanInterface;
    }

    public void setEntityBeanInterface(boolean hasEntityBeanInterface) {
        this.hasEntityBeanInterface = hasEntityBeanInterface;
    }

    public boolean hasGroovyInterface() {
        return this.hasGroovyInterface;
    }

    public void setGroovyInterface(boolean hasGroovyInterface) {
        this.hasGroovyInterface = hasGroovyInterface;
    }

    private static final class MethodReader
    extends EmptyVisitor {
        final MethodVisitor mv;
        final MethodMeta methodMeta;

        MethodReader(MethodVisitor mv, MethodMeta methodMeta) {
            this.mv = mv;
            this.methodMeta = methodMeta;
        }

        public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
            AnnotationVisitor av = this.mv.visitAnnotation(desc, visible);
            return new AnnotationInfoVisitor(null, this.methodMeta.annotationInfo, av);
        }
    }
}

