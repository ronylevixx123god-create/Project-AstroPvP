/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.annotation;

import com.avaje.ebean.Query;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface CacheStrategy {
    public boolean useBeanCache() default true;

    public boolean readOnly() default false;

    public String warmingQuery() default "";

    public Query.UseIndex useIndex() default Query.UseIndex.DEFAULT;
}

