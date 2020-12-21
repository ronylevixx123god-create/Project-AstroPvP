/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.validation;

import com.avaje.ebean.validation.ValidatorMeta;
import com.avaje.ebean.validation.factory.PatternValidatorFactory;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ValidatorMeta(factory=PatternValidatorFactory.class)
@Target(value={ElementType.FIELD, ElementType.METHOD})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface Pattern {
    public String regex() default "";

    public int flags() default 0;
}

