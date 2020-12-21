/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.type;

import com.avaje.ebean.config.ScalarTypeConverter;
import com.avaje.ebean.text.json.JsonValueAdapter;
import com.avaje.ebeaninternal.server.type.CtCompoundTypeScalarList;
import com.avaje.ebeaninternal.server.type.DataBind;
import com.avaje.ebeaninternal.server.type.DataReader;
import com.avaje.ebeaninternal.server.type.ScalarType;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.SQLException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ScalarTypeWrapper<B, S>
implements ScalarType<B> {
    private final ScalarType<S> scalarType;
    private final ScalarTypeConverter<B, S> converter;
    private final Class<B> wrapperType;
    private final B nullValue;

    public ScalarTypeWrapper(Class<B> wrapperType, ScalarType<S> scalarType, ScalarTypeConverter<B, S> converter) {
        this.scalarType = scalarType;
        this.converter = converter;
        this.nullValue = converter.getNullValue();
        this.wrapperType = wrapperType;
    }

    public String toString() {
        return "ScalarTypeWrapper " + this.wrapperType + " to " + this.scalarType.getType();
    }

    @Override
    public Object readData(DataInput dataInput) throws IOException {
        Object v = this.scalarType.readData(dataInput);
        return this.converter.wrapValue(v);
    }

    @Override
    public void writeData(DataOutput dataOutput, Object v) throws IOException {
        S sv = this.converter.unwrapValue(v);
        this.scalarType.writeData(dataOutput, sv);
    }

    @Override
    public void bind(DataBind b, B value) throws SQLException {
        if (value == null) {
            this.scalarType.bind(b, null);
        } else {
            S sv = this.converter.unwrapValue(value);
            this.scalarType.bind(b, sv);
        }
    }

    @Override
    public int getJdbcType() {
        return this.scalarType.getJdbcType();
    }

    @Override
    public int getLength() {
        return this.scalarType.getLength();
    }

    @Override
    public Class<B> getType() {
        return this.wrapperType;
    }

    @Override
    public boolean isDateTimeCapable() {
        return this.scalarType.isDateTimeCapable();
    }

    @Override
    public boolean isJdbcNative() {
        return false;
    }

    @Override
    public String format(Object v) {
        return this.formatValue((B)v);
    }

    @Override
    public String formatValue(B v) {
        S sv = this.converter.unwrapValue(v);
        return this.scalarType.formatValue(sv);
    }

    @Override
    public B parse(String value) {
        S sv = this.scalarType.parse(value);
        if (sv == null) {
            return this.nullValue;
        }
        return this.converter.wrapValue(sv);
    }

    @Override
    public B parseDateTime(long systemTimeMillis) {
        S sv = this.scalarType.parseDateTime(systemTimeMillis);
        if (sv == null) {
            return this.nullValue;
        }
        return this.converter.wrapValue(sv);
    }

    @Override
    public void loadIgnore(DataReader dataReader) {
        dataReader.incrementPos(1);
    }

    @Override
    public B read(DataReader dataReader) throws SQLException {
        S sv = this.scalarType.read(dataReader);
        if (sv == null) {
            return this.nullValue;
        }
        return this.converter.wrapValue(sv);
    }

    @Override
    public B toBeanType(Object value) {
        if (value == null) {
            return this.nullValue;
        }
        if (this.getType().isAssignableFrom(value.getClass())) {
            return (B)value;
        }
        if (value instanceof String) {
            return this.parse((String)value);
        }
        S sv = this.scalarType.toBeanType(value);
        return this.converter.wrapValue(sv);
    }

    @Override
    public Object toJdbcType(Object value) {
        S sv = this.converter.unwrapValue(value);
        if (sv == null) {
            return this.nullValue;
        }
        return this.scalarType.toJdbcType(sv);
    }

    @Override
    public void accumulateScalarTypes(String propName, CtCompoundTypeScalarList list) {
        list.addScalarType(propName, this);
    }

    public ScalarType<?> getScalarType() {
        return this;
    }

    @Override
    public String jsonToString(B value, JsonValueAdapter ctx) {
        S sv = this.converter.unwrapValue(value);
        return this.scalarType.jsonToString(sv, ctx);
    }

    @Override
    public B jsonFromString(String value, JsonValueAdapter ctx) {
        S s = this.scalarType.jsonFromString(value, ctx);
        return this.converter.wrapValue(s);
    }

    @Override
    public Object luceneFromIndexValue(Object value) {
        Object s = this.scalarType.luceneFromIndexValue(value);
        return this.converter.wrapValue(s);
    }

    @Override
    public Object luceneToIndexValue(Object value) {
        S sv = this.converter.unwrapValue(value);
        return this.scalarType.luceneToIndexValue(sv);
    }

    @Override
    public int getLuceneType() {
        return this.scalarType.getLuceneType();
    }
}

