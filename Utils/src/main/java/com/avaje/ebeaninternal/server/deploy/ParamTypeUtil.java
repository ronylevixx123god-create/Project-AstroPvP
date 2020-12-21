/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ParamTypeUtil {
    public static Class<?> findParamType(Class<?> cls, Class<?> matchType) {
        Type genericSuperclass;
        Type paramType = ParamTypeUtil.matchByInterfaces(cls, matchType);
        if (paramType == null && (genericSuperclass = cls.getGenericSuperclass()) != null) {
            paramType = ParamTypeUtil.matchParamType(genericSuperclass, matchType);
        }
        if (paramType instanceof Class) {
            return (Class)paramType;
        }
        return null;
    }

    private static Type matchParamType(Type type, Class<?> matchType) {
        ParameterizedType pt;
        Type rawType;
        boolean isAssignable;
        if (type instanceof ParameterizedType && (isAssignable = matchType.isAssignableFrom((Class)(rawType = (pt = (ParameterizedType)type).getRawType())))) {
            Type[] typeArguments = pt.getActualTypeArguments();
            if (typeArguments.length != 1) {
                String m = "Expecting only 1 generic paramater but got " + typeArguments.length + " for " + type;
                throw new RuntimeException(m);
            }
            return typeArguments[0];
        }
        return null;
    }

    private static Type matchByInterfaces(Class<?> cls, Class<?> matchType) {
        Type[] gis = cls.getGenericInterfaces();
        for (int i = 0; i < gis.length; ++i) {
            Type match = ParamTypeUtil.matchParamType(gis[i], matchType);
            if (match == null) continue;
            return match;
        }
        return null;
    }
}

