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
import java.sql.Time;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ScalarTypeTime
extends ScalarTypeBase<Time> {
    public ScalarTypeTime() {
        super(Time.class, true, 92);
    }

    @Override
    public void bind(DataBind b, Time value) throws SQLException {
        if (value == null) {
            b.setNull(92);
        } else {
            b.setTime(value);
        }
    }

    @Override
    public Time read(DataReader dataReader) throws SQLException {
        return dataReader.getTime();
    }

    @Override
    public Object toJdbcType(Object value) {
        return BasicTypeConverter.toTime(value);
    }

    @Override
    public Time toBeanType(Object value) {
        return BasicTypeConverter.toTime(value);
    }

    @Override
    public String formatValue(Time v) {
        return v.toString();
    }

    @Override
    public Time parse(String value) {
        return Time.valueOf(value);
    }

    @Override
    public Time parseDateTime(long systemTimeMillis) {
        return new Time(systemTimeMillis);
    }

    @Override
    public boolean isDateTimeCapable() {
        return true;
    }

    @Override
    public int getLuceneType() {
        return 0;
    }

    @Override
    public Object luceneFromIndexValue(Object value) {
        return this.parse((String)value);
    }

    @Override
    public Object luceneToIndexValue(Object value) {
        return this.format(value);
    }

    @Override
    public Object readData(DataInput dataInput) throws IOException {
        if (!dataInput.readBoolean()) {
            return null;
        }
        String val = dataInput.readUTF();
        return this.parse(val);
    }

    @Override
    public void writeData(DataOutput dataOutput, Object v) throws IOException {
        Time value = (Time)v;
        if (value == null) {
            dataOutput.writeBoolean(false);
        } else {
            dataOutput.writeBoolean(true);
            dataOutput.writeUTF(this.format(value));
        }
    }
}

