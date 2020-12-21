/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.annotation;

import com.avaje.ebean.annotation.NamedUpdate;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface NamedUpdates {
    public NamedUpdate[] value();
}

