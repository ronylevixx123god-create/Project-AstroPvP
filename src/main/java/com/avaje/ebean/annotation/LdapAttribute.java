/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.FIELD})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface LdapAttribute {
    public String name() default "";

    public Class<?> adapter() default void.class;

    public boolean insertable() default true;

    public boolean updatable() default true;
}

