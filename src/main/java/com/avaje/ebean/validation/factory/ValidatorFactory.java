/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.validation.factory;

import com.avaje.ebean.validation.factory.Validator;
import java.lang.annotation.Annotation;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface ValidatorFactory {
    public Validator create(Annotation var1, Class<?> var2);
}

