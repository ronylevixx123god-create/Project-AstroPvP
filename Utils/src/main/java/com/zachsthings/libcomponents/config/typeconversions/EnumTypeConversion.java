/*
 * Decompiled with CFR 0.150.
 */
package com.zachsthings.libcomponents.config.typeconversions;

import com.zachsthings.libcomponents.config.typeconversions.TypeConversion;
import java.lang.reflect.Type;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class EnumTypeConversion
extends TypeConversion {
    @Override
    protected Object cast(Class<?> target, Type[] neededGenerics, Object value) {
        try {
            return Enum.valueOf(target.asSubclass(Enum.class), value.toString().toUpperCase());
        }
        catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public boolean isApplicable(Class<?> target, Object value) {
        return target.isEnum();
    }

    @Override
    protected int getParametersRequired() {
        return 0;
    }
}

