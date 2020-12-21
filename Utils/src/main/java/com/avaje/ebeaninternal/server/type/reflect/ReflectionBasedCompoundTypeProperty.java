/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.type.reflect;

import com.avaje.ebean.config.CompoundTypeProperty;
import java.lang.reflect.Method;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ReflectionBasedCompoundTypeProperty
implements CompoundTypeProperty {
    private static final Object[] NO_ARGS = new Object[0];
    private final Method reader;
    private final String name;
    private final Class<?> propertyType;

    public ReflectionBasedCompoundTypeProperty(String name, Method reader, Class<?> propertyType) {
        this.name = name;
        this.reader = reader;
        this.propertyType = propertyType;
    }

    public String toString() {
        return this.name;
    }

    @Override
    public int getDbType() {
        return 0;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public Object getValue(Object valueObject) {
        try {
            return this.reader.invoke(valueObject, NO_ARGS);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Class<?> getPropertyType() {
        return this.propertyType;
    }
}

