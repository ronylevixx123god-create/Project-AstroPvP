/*
 * Decompiled with CFR 0.150.
 */
package com.zachsthings.libcomponents.config.typeconversions;

import com.zachsthings.libcomponents.config.typeconversions.TypeConversion;
import java.lang.reflect.Type;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class StringTypeConversion
extends TypeConversion {
    @Override
    protected Object cast(Class<?> target, Type[] neededGenerics, Object value) {
        return value.toString();
    }

    @Override
    public boolean isApplicable(Class<?> target, Object value) {
        return String.class.isAssignableFrom(target);
    }

    @Override
    public int getParametersRequired() {
        return 0;
    }
}

