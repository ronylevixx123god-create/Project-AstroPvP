/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ParamTypeHelper {
    public static TypeInfo getTypeInfo(Type genericType) {
        if (genericType instanceof ParameterizedType) {
            return ParamTypeHelper.getParamTypeInfo((ParameterizedType)genericType);
        }
        Class<?> entityType = ParamTypeHelper.getBeanType(genericType);
        if (entityType != null) {
            return new TypeInfo(ManyType.NONE, entityType);
        }
        return null;
    }

    private static TypeInfo getParamTypeInfo(ParameterizedType paramType) {
        Type argType;
        Class<?> beanType;
        Type rawType = paramType.getRawType();
        ManyType manyType = ParamTypeHelper.getManyType(rawType);
        if (ManyType.NONE.equals((Object)manyType)) {
            return null;
        }
        Type[] typeArguments = paramType.getActualTypeArguments();
        if (typeArguments.length == 1 && (beanType = ParamTypeHelper.getBeanType(argType = typeArguments[0])) != null) {
            return new TypeInfo(manyType, beanType);
        }
        return null;
    }

    private static Class<?> getBeanType(Type argType) {
        if (argType instanceof Class) {
            return (Class)argType;
        }
        return null;
    }

    private static ManyType getManyType(Type rawType) {
        if (List.class.equals((Object)rawType)) {
            return ManyType.LIST;
        }
        if (Set.class.equals((Object)rawType)) {
            return ManyType.SET;
        }
        if (Map.class.equals((Object)rawType)) {
            return ManyType.MAP;
        }
        return ManyType.NONE;
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static class TypeInfo {
        private final ManyType manyType;
        private final Class<?> beanType;

        private TypeInfo(ManyType manyType, Class<?> beanType) {
            this.manyType = manyType;
            this.beanType = beanType;
        }

        public boolean isManyType() {
            return !ManyType.NONE.equals((Object)this.manyType);
        }

        public ManyType getManyType() {
            return this.manyType;
        }

        public Class<?> getBeanType() {
            return this.beanType;
        }

        public String toString() {
            if (this.isManyType()) {
                return (Object)((Object)this.manyType) + " " + this.beanType;
            }
            return this.beanType.toString();
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static enum ManyType {
        LIST,
        SET,
        MAP,
        NONE;

    }
}

