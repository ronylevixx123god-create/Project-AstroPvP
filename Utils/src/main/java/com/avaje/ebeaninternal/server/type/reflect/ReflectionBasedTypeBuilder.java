/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.type.reflect;

import com.avaje.ebeaninternal.server.type.ScalarType;
import com.avaje.ebeaninternal.server.type.ScalarTypeWrapper;
import com.avaje.ebeaninternal.server.type.TypeManager;
import com.avaje.ebeaninternal.server.type.reflect.ImmutableMeta;
import com.avaje.ebeaninternal.server.type.reflect.ReflectionBasedCompoundType;
import com.avaje.ebeaninternal.server.type.reflect.ReflectionBasedCompoundTypeProperty;
import com.avaje.ebeaninternal.server.type.reflect.ReflectionBasedScalarTypeConverter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ReflectionBasedTypeBuilder {
    private final TypeManager typeManager;

    public ReflectionBasedTypeBuilder(TypeManager typeManager) {
        this.typeManager = typeManager;
    }

    public ScalarType<?> buildScalarType(ImmutableMeta meta) {
        if (meta.isCompoundType()) {
            throw new RuntimeException("Must be scalar");
        }
        Constructor<?> constructor = meta.getConstructor();
        Class<?> logicalType = constructor.getDeclaringClass();
        Method[] readers = meta.getReaders();
        Class<?> returnType = readers[0].getReturnType();
        ScalarType<?> scalarType = this.typeManager.recursiveCreateScalarTypes(returnType);
        ReflectionBasedScalarTypeConverter r = new ReflectionBasedScalarTypeConverter(constructor, readers[0]);
        ScalarTypeWrapper st = new ScalarTypeWrapper(logicalType, scalarType, r);
        return st;
    }

    public ReflectionBasedCompoundType buildCompound(ImmutableMeta meta) {
        Constructor<?> constructor = meta.getConstructor();
        Method[] readers = meta.getReaders();
        ReflectionBasedCompoundTypeProperty[] props = new ReflectionBasedCompoundTypeProperty[readers.length];
        for (int i = 0; i < readers.length; ++i) {
            Class<?> returnType = readers[i].getReturnType();
            this.typeManager.recursiveCreateScalarDataReader(returnType);
            String name = this.getPropertyName(readers[i]);
            props[i] = new ReflectionBasedCompoundTypeProperty(name, readers[i], returnType);
        }
        return new ReflectionBasedCompoundType(constructor, props);
    }

    private String getPropertyName(Method method) {
        String name = method.getName();
        if (name.startsWith("is")) {
            return this.lowerFirstChar(name.substring(2));
        }
        if (name.startsWith("get")) {
            return this.lowerFirstChar(name.substring(3));
        }
        String msg = "Expecting method " + name + " to start with is or get " + " so as to follow bean specification?";
        throw new RuntimeException(msg);
    }

    private String lowerFirstChar(String name) {
        return Character.toLowerCase(name.charAt(0)) + name.substring(1);
    }
}

