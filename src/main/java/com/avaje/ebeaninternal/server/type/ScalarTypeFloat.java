/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.type;

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
public class ScalarTypeFloat
extends ScalarTypeBase<Float> {
    public ScalarTypeFloat() {
        super(Float.class, true, 7);
    }

    @Override
    public void bind(DataBind b, Float value) throws SQLException {
        if (value == null) {
            b.setNull(7);
        } else {
            b.setFloat(value.floatValue());
        }
    }

    @Override
    public Float read(DataReader dataReader) throws SQLException {
        return dataReader.getFloat();
    }

    @Override
    public Object toJdbcType(Object value) {
        return BasicTypeConverter.toFloat(value);
    }

    @Override
    public Float toBeanType(Object value) {
        return BasicTypeConverter.toFloat(value);
    }

    @Override
    public String formatValue(Float t) {
        return t.toString();
    }

    @Override
    public Float parse(String value) {
        return Float.valueOf(value);
    }

    @Override
    public Float parseDateTime(long systemTimeMillis) {
        return Float.valueOf(systemTimeMillis);
    }

    @Override
    public boolean isDateTimeCapable() {
        return true;
    }

    public String toJsonString(Float value) {
        if (value.isInfinite() || value.isNaN()) {
            return "null";
        }
        return value.toString();
    }

    @Override
    public int getLuceneType() {
        return 4;
    }

    @Override
    public Object luceneFromIndexValue(Object value) {
        return value;
    }

    @Override
    public Object luceneToIndexValue(Object value) {
        return value;
    }

    @Override
    public Object readData(DataInput dataInput) throws IOException {
        if (!dataInput.readBoolean()) {
            return null;
        }
        float val = dataInput.readFloat();
        return Float.valueOf(val);
    }

    @Override
    public void writeData(DataOutput dataOutput, Object v) throws IOException {
        Float value = (Float)v;
        if (value == null) {
            dataOutput.writeBoolean(false);
        } else {
            dataOutput.writeBoolean(true);
            dataOutput.writeFloat(value.floatValue());
        }
    }
}

