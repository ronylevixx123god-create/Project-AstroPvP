/*
 * Decompiled with CFR 0.150.
 */
package com.zachsthings.libcomponents;

import com.zachsthings.libcomponents.AbstractComponent;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface AnnotationHandler<T extends Annotation> {
    public boolean handle(AbstractComponent var1, Field var2, T var3);
}

