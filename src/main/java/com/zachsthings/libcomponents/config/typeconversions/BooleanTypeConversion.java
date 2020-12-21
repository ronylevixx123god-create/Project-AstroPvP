/*
 * Decompiled with CFR 0.150.
 */
package com.zachsthings.libcomponents.config.typeconversions;

import com.zachsthings.libcomponents.config.typeconversions.TypeConversion;
import java.lang.reflect.Type;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class BooleanTypeConversion
extends TypeConversion {
    @Override
    protected Object cast(Class<?> target, Type[] neededGenerics, Object value) {
        if (value instanceof Boolean) {
            return value;
        }
        return Boolean.parseBoolean(value.toString());
    }

    @Override
    public boolean isApplicable(Class<?> target, Object value) {
        return Boolean.TYPE.isAssignableFrom(target) || Boolean.class.isAssignableFrom(target);
    }

    @Override
    protected int getParametersRequired() {
        return 0;
    }
}

