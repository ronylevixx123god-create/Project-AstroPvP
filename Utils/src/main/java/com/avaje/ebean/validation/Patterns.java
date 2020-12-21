/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.validation;

import com.avaje.ebean.validation.Pattern;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.FIELD, ElementType.METHOD})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface Patterns {
    public Pattern[] patterns();
}

