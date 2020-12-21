/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.type;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class TypeReflectHelper {
    public static Class<?>[] getParams(Class<?> cls, Class<?> matchRawType) {
        Type[] types = TypeReflectHelper.getParamType(cls, matchRawType);
        Class[] result = new Class[types.length];
        for (int i = 0; i < result.length; ++i) {
            result[i] = TypeReflectHelper.getClass(types[i]);
        }
        return result;
    }

    public static Class<?> getClass(Type type) {
        if (type instanceof ParameterizedType) {
            return TypeReflectHelper.getClass(((ParameterizedType)type).getRawType());
        }
        return (Class)type;
    }

    private static Type[] getParamType(Class<?> cls, Class<?> matchRawType) {
        Type[] gis = cls.getGenericInterfaces();
        for (int i = 0; i < gis.length; ++i) {
            ParameterizedType paramType;
            Type rawType;
            Type type = gis[i];
            if (!(type instanceof ParameterizedType) || !(rawType = (paramType = (ParameterizedType)type).getRawType()).equals(matchRawType)) continue;
            return paramType.getActualTypeArguments();
        }
        return null;
    }
}

