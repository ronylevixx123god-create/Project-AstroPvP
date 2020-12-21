/*
 * Decompiled with CFR 0.150.
 */
package com.zachsthings.libcomponents.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.FIELD})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface Setting {
    public String value();
}

