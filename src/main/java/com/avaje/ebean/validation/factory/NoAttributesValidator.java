/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.validation.factory;

import com.avaje.ebean.validation.factory.Validator;

public abstract class NoAttributesValidator
implements Validator {
    private static final Object[] EMPTY = new Object[0];

    public Object[] getAttributes() {
        return EMPTY;
    }
}

