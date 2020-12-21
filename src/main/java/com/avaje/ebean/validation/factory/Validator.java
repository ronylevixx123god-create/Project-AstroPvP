/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.validation.factory;

public interface Validator {
    public String getKey();

    public Object[] getAttributes();

    public boolean isValid(Object var1);
}

