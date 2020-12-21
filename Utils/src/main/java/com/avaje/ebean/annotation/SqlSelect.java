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
@Deprecated
public @interface SqlSelect {
    public String name() default "default";

    public String tableAlias() default "";

    public String query() default "";

    public String extend() default "";

    public String where() default "";

    public String having() default "";

    public String columnMapping() default "";

    public boolean debug() default false;
}

