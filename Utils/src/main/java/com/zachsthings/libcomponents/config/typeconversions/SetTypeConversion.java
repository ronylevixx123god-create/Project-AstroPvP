/*
 * Decompiled with CFR 0.150.
 */
package com.zachsthings.libcomponents.config.typeconversions;

import com.zachsthings.libcomponents.config.ConfigUtil;
import com.zachsthings.libcomponents.config.typeconversions.TypeConversion;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class SetTypeConversion
extends TypeConversion {
    @Override
    protected Object cast(Class<?> target, Type[] neededGenerics, Object value) {
        HashSet<Object> values = new HashSet<Object>();
        Collection raw = (Collection)value;
        for (Object obj : raw) {
            values.add(ConfigUtil.smartCast(neededGenerics[0], obj));
        }
        return values;
    }

    @Override
    public boolean isApplicable(Class<?> target, Object value) {
        return target.equals(Set.class) && value instanceof Collection;
    }

    @Override
    public int getParametersRequired() {
        return 1;
    }
}

