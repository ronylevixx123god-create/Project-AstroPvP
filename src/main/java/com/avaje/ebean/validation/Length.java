/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.validation;

import com.avaje.ebean.validation.ValidatorMeta;
import com.avaje.ebean.validation.factory.LengthValidatorFactory;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ValidatorMeta(factory=LengthValidatorFactory.class)
@Target(value={ElementType.FIELD, ElementType.METHOD})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface Length {
    public int min() default 0;

    public int max() default 0x7FFFFFFF;
}

