/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.enhance.asm;

import com.avaje.ebean.enhance.asm.AnnotationVisitor;
import com.avaje.ebean.enhance.asm.Attribute;

public interface FieldVisitor {
    public AnnotationVisitor visitAnnotation(String var1, boolean var2);

    public void visitAttribute(Attribute var1);

    public void visitEnd();
}

