/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.enhance.agent;

import com.avaje.ebean.enhance.agent.AnnotationInfo;

public class MethodMeta {
    final int access;
    final String name;
    final String desc;
    final AnnotationInfo annotationInfo;

    public MethodMeta(AnnotationInfo classAnnotationInfo, int access, String name, String desc) {
        this.annotationInfo = new AnnotationInfo(classAnnotationInfo);
        this.access = access;
        this.name = name;
        this.desc = desc;
    }

    public String toString() {
        return this.name + " " + this.desc;
    }

    public boolean isMatch(String methodName, String methodDesc) {
        return this.name.equals(methodName) && this.desc.equals(methodDesc);
    }

    public AnnotationInfo getAnnotationInfo() {
        return this.annotationInfo;
    }
}

