/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.type;

import com.avaje.ebean.text.json.JsonValueAdapter;
import com.avaje.ebeaninternal.server.type.CtCompoundTypeScalarList;
import com.avaje.ebeaninternal.server.type.DataReader;
import com.avaje.ebeaninternal.server.type.ScalarType;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class ScalarTypeBase<T>
implements ScalarType<T> {
    protected final Class<T> type;
    protected final boolean jdbcNative;
    protected final int jdbcType;

    public ScalarTypeBase(Class<T> type, boolean jdbcNative, int jdbcType) {
        this.type = type;
        this.jdbcNative = jdbcNative;
        this.jdbcType = jdbcType;
    }

    @Override
    public int getLength() {
        return 0;
    }

    @Override
    public boolean isJdbcNative() {
        return this.jdbcNative;
    }

    @Override
    public int getJdbcType() {
        return this.jdbcType;
    }

    @Override
    public Class<T> getType() {
        return this.type;
    }

    @Override
    public String format(Object v) {
        return this.formatValue(v);
    }

    public boolean isDbNull(Object value) {
        return value == null;
    }

    public Object getDbNullValue(Object value) {
        return value;
    }

    @Override
    public void loadIgnore(DataReader dataReader) {
        dataReader.incrementPos(1);
    }

    @Override
    public void accumulateScalarTypes(String propName, CtCompoundTypeScalarList list) {
        list.addScalarType(propName, this);
    }

    @Override
    public String jsonToString(T value, JsonValueAdapter ctx) {
        return this.formatValue(value);
    }

    @Override
    public T jsonFromString(String value, JsonValueAdapter ctx) {
        return this.parse(value);
    }
}

