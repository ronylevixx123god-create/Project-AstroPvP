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
public class ScalarTypeDouble
extends ScalarTypeBase<Double> {
    public ScalarTypeDouble() {
        super(Double.class, true, 8);
    }

    @Override
    public void bind(DataBind b, Double value) throws SQLException {
        if (value == null) {
            b.setNull(8);
        } else {
            b.setDouble(value);
        }
    }

    @Override
    public Double read(DataReader dataReader) throws SQLException {
        return dataReader.getDouble();
    }

    @Override
    public Object toJdbcType(Object value) {
        return BasicTypeConverter.toDouble(value);
    }

    @Override
    public Double toBeanType(Object value) {
        return BasicTypeConverter.toDouble(value);
    }

    @Override
    public String formatValue(Double t) {
        return t.toString();
    }

    @Override
    public Double parse(String value) {
        return Double.valueOf(value);
    }

    @Override
    public Double parseDateTime(long systemTimeMillis) {
        return systemTimeMillis;
    }

    @Override
    public boolean isDateTimeCapable() {
        return true;
    }

    public String toJsonString(Double value) {
        if (value.isInfinite() || value.isNaN()) {
            return "null";
        }
        return value.toString();
    }

    @Override
    public int getLuceneType() {
        return 3;
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
        double val = dataInput.readDouble();
        return val;
    }

    @Override
    public void writeData(DataOutput dataOutput, Object v) throws IOException {
        Double value = (Double)v;
        if (value == null) {
            dataOutput.writeBoolean(false);
        } else {
            dataOutput.writeBoolean(true);
            dataOutput.writeDouble(value);
        }
    }
}

