/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface EnumMapping {
    public String nameValuePairs();

    public boolean integerType() default false;

    public int length() default 0;
}

