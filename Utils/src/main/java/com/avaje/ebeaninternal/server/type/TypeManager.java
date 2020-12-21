/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.type;

import com.avaje.ebeaninternal.server.type.CtCompoundType;
import com.avaje.ebeaninternal.server.type.ScalarDataReader;
import com.avaje.ebeaninternal.server.type.ScalarType;
import com.avaje.ebeaninternal.server.type.reflect.CheckImmutableResponse;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface TypeManager {
    public CheckImmutableResponse checkImmutable(Class<?> var1);

    public ScalarDataReader<?> recursiveCreateScalarDataReader(Class<?> var1);

    public ScalarType<?> recursiveCreateScalarTypes(Class<?> var1);

    public void add(ScalarType<?> var1);

    public CtCompoundType<?> getCompoundType(Class<?> var1);

    public ScalarType<?> getScalarType(int var1);

    public <T> ScalarType<T> getScalarType(Class<T> var1);

    public <T> ScalarType<T> getScalarType(Class<T> var1, int var2);

    public ScalarType<?> createEnumScalarType(Class<?> var1);
}

