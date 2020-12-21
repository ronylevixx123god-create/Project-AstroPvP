/*
 * Decompiled with CFR 0.150.
 */
package com.zachsthings.libcomponents.config.typeconversions;

import com.zachsthings.libcomponents.config.ConfigUtil;
import com.zachsthings.libcomponents.config.typeconversions.TypeConversion;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class MapTypeConversion
extends TypeConversion {
    @Override
    protected Object cast(Class<?> target, Type[] neededGenerics, Object value) {
        Map raw = (Map)value;
        HashMap<Object, Object> values = new HashMap<Object, Object>();
        for (Map.Entry entry : raw.entrySet()) {
            values.put(ConfigUtil.smartCast(neededGenerics[0], entry.getKey()), ConfigUtil.smartCast(neededGenerics[1], entry.getValue()));
        }
        return values;
    }

    @Override
    public boolean isApplicable(Class<?> target, Object value) {
        return target.equals(Map.class) && value instanceof Map;
    }

    @Override
    protected int getParametersRequired() {
        return 2;
    }
}

