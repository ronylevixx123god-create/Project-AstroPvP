/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.type.reflect;

import com.avaje.ebean.config.CompoundType;
import com.avaje.ebean.config.CompoundTypeProperty;
import com.avaje.ebeaninternal.server.type.reflect.ReflectionBasedCompoundTypeProperty;
import java.lang.reflect.Constructor;
import java.util.Arrays;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ReflectionBasedCompoundType
implements CompoundType {
    private final Constructor<?> constructor;
    private final ReflectionBasedCompoundTypeProperty[] props;

    public ReflectionBasedCompoundType(Constructor<?> constructor, ReflectionBasedCompoundTypeProperty[] props) {
        this.constructor = constructor;
        this.props = props;
    }

    public String toString() {
        return "ReflectionBasedCompoundType " + this.constructor + " " + Arrays.toString(this.props);
    }

    public Object create(Object[] propertyValues) {
        try {
            return this.constructor.newInstance(propertyValues);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public CompoundTypeProperty[] getProperties() {
        return this.props;
    }

    public Class<?> getPropertyType(int i) {
        return this.props[i].getPropertyType();
    }

    public Class<?> getCompoundType() {
        return this.constructor.getDeclaringClass();
    }
}

