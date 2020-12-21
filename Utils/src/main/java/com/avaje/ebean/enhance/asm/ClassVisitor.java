/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.enhance.asm;

import com.avaje.ebean.enhance.asm.AnnotationVisitor;
import com.avaje.ebean.enhance.asm.Attribute;
import com.avaje.ebean.enhance.asm.FieldVisitor;
import com.avaje.ebean.enhance.asm.MethodVisitor;

public interface ClassVisitor {
    public void visit(int var1, int var2, String var3, String var4, String var5, String[] var6);

    public void visitSource(String var1, String var2);

    public void visitOuterClass(String var1, String var2, String var3);

    public AnnotationVisitor visitAnnotation(String var1, boolean var2);

    public void visitAttribute(Attribute var1);

    public void visitInnerClass(String var1, String var2, String var3, int var4);

    public FieldVisitor visitField(int var1, String var2, String var3, String var4, Object var5);

    public MethodVisitor visitMethod(int var1, String var2, String var3, String var4, String[] var5);

    public void visitEnd();
}

