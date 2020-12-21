/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.enhance.agent;

import com.avaje.ebean.enhance.agent.ClassMeta;
import com.avaje.ebean.enhance.agent.ClassMetaReaderVisitor;
import com.avaje.ebean.enhance.agent.EnhanceContext;
import com.avaje.ebean.enhance.asm.ClassReader;
import java.util.HashMap;
import java.util.Map;

public class ClassMetaReader {
    private Map<String, ClassMeta> cache = new HashMap<String, ClassMeta>();
    private final EnhanceContext enhanceContext;

    public ClassMetaReader(EnhanceContext enhanceContext) {
        this.enhanceContext = enhanceContext;
    }

    public ClassMeta get(boolean readMethodAnnotations, String name, ClassLoader classLoader) throws ClassNotFoundException {
        return this.getWithCache(readMethodAnnotations, name, classLoader);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private ClassMeta getWithCache(boolean readMethodAnnotations, String name, ClassLoader classLoader) throws ClassNotFoundException {
        Map<String, ClassMeta> map = this.cache;
        synchronized (map) {
            ClassMeta meta = this.cache.get(name);
            if (meta == null && (meta = this.readFromResource(readMethodAnnotations, name, classLoader)) != null) {
                ClassMeta superMeta;
                if (meta.isCheckSuperClassForEntity() && (superMeta = this.getWithCache(readMethodAnnotations, meta.getSuperClassName(), classLoader)) != null && superMeta.isEntity()) {
                    meta.setSuperMeta(superMeta);
                }
                this.cache.put(name, meta);
            }
            return meta;
        }
    }

    private ClassMeta readFromResource(boolean readMethodAnnotations, String className, ClassLoader classLoader) throws ClassNotFoundException {
        byte[] classBytes = this.enhanceContext.getClassBytes(className, classLoader);
        if (classBytes == null) {
            this.enhanceContext.log(1, "Class [" + className + "] not found.");
            return null;
        }
        if (this.enhanceContext.isLog(3)) {
            this.enhanceContext.log(className, "read ClassMeta");
        }
        ClassReader cr = new ClassReader(classBytes);
        ClassMetaReaderVisitor ca = new ClassMetaReaderVisitor(readMethodAnnotations, this.enhanceContext);
        cr.accept(ca, 0);
        return ca.getClassMeta();
    }
}

