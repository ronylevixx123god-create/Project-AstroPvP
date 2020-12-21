/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.validation.factory;

import com.avaje.ebean.validation.factory.NoAttributesValidator;
import com.avaje.ebean.validation.factory.Validator;
import com.avaje.ebean.validation.factory.ValidatorFactory;
import java.lang.annotation.Annotation;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class NotNullValidatorFactory
implements ValidatorFactory {
    public static final Validator NOT_NULL = new NotNullValidator();

    @Override
    public Validator create(Annotation annotation, Class<?> type) {
        return NOT_NULL;
    }

    public static class NotNullValidator
    extends NoAttributesValidator {
        public String getKey() {
            return "notnull";
        }

        public boolean isValid(Object value) {
            return value != null;
        }
    }
}

