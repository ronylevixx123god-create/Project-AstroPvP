/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.type;

import com.avaje.ebean.text.TextException;
import com.avaje.ebean.text.json.JsonValueAdapter;
import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
import com.avaje.ebeaninternal.server.type.DataBind;
import com.avaje.ebeaninternal.server.type.DataReader;
import com.avaje.ebeaninternal.server.type.ScalarTypeBase;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.SQLException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ScalarTypeInteger
extends ScalarTypeBase<Integer> {
    public ScalarTypeInteger() {
        super(Integer.class, true, 4);
    }

    @Override
    public void bind(DataBind b, Integer value) throws SQLException {
        if (value == null) {
            b.setNull(4);
        } else {
            b.setInt(value);
        }
    }

    @Override
    public Integer read(DataReader dataReader) throws SQLException {
        return dataReader.getInt();
    }

    @Override
    public Object readData(DataInput dataInput) throws IOException {
        return dataInput.readInt();
    }

    @Override
    public void writeData(DataOutput dataOutput, Object v) throws IOException {
        dataOutput.writeInt((Integer)v);
    }

    @Override
    public Object toJdbcType(Object value) {
        return BasicTypeConverter.toInteger(value);
    }

    @Override
    public Integer toBeanType(Object value) {
        return BasicTypeConverter.toInteger(value);
    }

    @Override
    public String formatValue(Integer v) {
        return v.toString();
    }

    @Override
    public Integer parse(String value) {
        return Integer.valueOf(value);
    }

    @Override
    public Integer parseDateTime(long systemTimeMillis) {
        throw new TextException("Not Supported");
    }

    @Override
    public boolean isDateTimeCapable() {
        return false;
    }

    @Override
    public String jsonToString(Integer value, JsonValueAdapter ctx) {
        return value.toString();
    }

    @Override
    public Integer jsonFromString(String value, JsonValueAdapter ctx) {
        return Integer.valueOf(value);
    }

    @Override
    public int getLuceneType() {
        return 1;
    }

    @Override
    public Object luceneFromIndexValue(Object value) {
        return value;
    }

    @Override
    public Object luceneToIndexValue(Object value) {
        return value;
    }
}

