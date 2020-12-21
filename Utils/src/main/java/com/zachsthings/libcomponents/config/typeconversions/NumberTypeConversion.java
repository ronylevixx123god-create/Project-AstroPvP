/*
 * Decompiled with CFR 0.150.
 */
package com.zachsthings.libcomponents.config.typeconversions;

import com.zachsthings.libcomponents.config.typeconversions.TypeConversion;
import java.lang.reflect.Type;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class NumberTypeConversion
extends TypeConversion {
    @Override
    protected Object cast(Class<?> target, Type[] neededGenerics, Object rawVal) {
        Number value = (Number)rawVal;
        if (target.equals(Number.class)) {
            return value;
        }
        if (target.equals(Integer.TYPE) || target.equals(Integer.class)) {
            if (value instanceof Integer) {
                return value;
            }
            return value.intValue();
        }
        if (target.equals(Byte.TYPE) || target.equals(Byte.class)) {
            if (value instanceof Byte) {
                return value;
            }
            return value.byteValue();
        }
        if (target.equals(Long.TYPE) || target.equals(Long.class)) {
            if (value instanceof Long) {
                return value;
            }
            return value.longValue();
        }
        if (target.equals(Double.TYPE) || target.equals(Double.class)) {
            if (value instanceof Double) {
                return value;
            }
            return value.doubleValue();
        }
        if (target.equals(Float.TYPE) || target.equals(Float.class)) {
            if (value instanceof Float) {
                return value;
            }
            return Float.valueOf(value.floatValue());
        }
        if (target.equals(Short.TYPE) || target.equals(Short.class)) {
            if (value instanceof Short) {
                return value;
            }
            return value.shortValue();
        }
        return null;
    }

    @Override
    public boolean isApplicable(Class<?> target, Object value) {
        return (target.isPrimitive() || Number.class.isAssignableFrom(target)) && !Boolean.TYPE.isAssignableFrom(target);
    }

    @Override
    public int getParametersRequired() {
        return 0;
    }
}

