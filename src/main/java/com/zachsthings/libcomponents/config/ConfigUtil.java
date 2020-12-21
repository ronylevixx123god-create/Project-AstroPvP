/*
 * Decompiled with CFR 0.150.
 */
package com.zachsthings.libcomponents.config;

import com.zachsthings.libcomponents.config.typeconversions.BooleanTypeConversion;
import com.zachsthings.libcomponents.config.typeconversions.EnumTypeConversion;
import com.zachsthings.libcomponents.config.typeconversions.ListTypeConversion;
import com.zachsthings.libcomponents.config.typeconversions.MapTypeConversion;
import com.zachsthings.libcomponents.config.typeconversions.NumberTypeConversion;
import com.zachsthings.libcomponents.config.typeconversions.SameTypeConversion;
import com.zachsthings.libcomponents.config.typeconversions.SetTypeConversion;
import com.zachsthings.libcomponents.config.typeconversions.StringTypeConversion;
import com.zachsthings.libcomponents.config.typeconversions.TypeConversion;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ConfigUtil {
    private static final List<TypeConversion> typeConversions = new ArrayList<TypeConversion>(Arrays.asList(new SameTypeConversion(), new StringTypeConversion(), new BooleanTypeConversion(), new NumberTypeConversion(), new EnumTypeConversion(), new SetTypeConversion(), new ListTypeConversion(), new MapTypeConversion()));

    public static Object smartCast(Type genericType, Object value) {
        TypeConversion conversion;
        Type[] neededGenerics;
        if (value == null) {
            return null;
        }
        Class target = null;
        if (genericType != null && genericType instanceof ParameterizedType) {
            ParameterizedType type = (ParameterizedType)genericType;
            Type raw = type.getRawType();
            if (raw instanceof Class) {
                target = (Class)raw;
            }
            neededGenerics = type.getActualTypeArguments();
        } else {
            if (genericType instanceof Class) {
                target = (Class)genericType;
            }
            neededGenerics = new Type[]{};
        }
        if (target == null) {
            return null;
        }
        Object ret = null;
        Iterator<TypeConversion> i$ = typeConversions.iterator();
        while (i$.hasNext() && (ret = (conversion = i$.next()).handle(target, neededGenerics, value)) == null) {
        }
        return ret;
    }

    public static void registerTypeConversion(TypeConversion conversion) {
        typeConversions.add(conversion);
    }

    public static Object prepareSerialization(Object obj) {
        if (obj instanceof Collection) {
            obj = new ArrayList(obj);
        }
        return obj;
    }
}

