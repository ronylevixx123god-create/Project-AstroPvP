/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.type;

import com.avaje.ebean.text.json.JsonValueAdapter;
import com.avaje.ebeaninternal.server.type.DataBind;
import com.avaje.ebeaninternal.server.type.DataReader;
import com.avaje.ebeaninternal.server.type.ScalarTypeBase;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class ScalarTypeBaseDate<T>
extends ScalarTypeBase<T> {
    public ScalarTypeBaseDate(Class<T> type, boolean jdbcNative, int jdbcType) {
        super(type, jdbcNative, jdbcType);
    }

    public abstract Date convertToDate(T var1);

    public abstract T convertFromDate(Date var1);

    @Override
    public void bind(DataBind b, T value) throws SQLException {
        if (value == null) {
            b.setNull(91);
        } else {
            Date date = this.convertToDate(value);
            b.setDate(date);
        }
    }

    @Override
    public T read(DataReader dataReader) throws SQLException {
        Date ts = dataReader.getDate();
        if (ts == null) {
            return null;
        }
        return this.convertFromDate(ts);
    }

    @Override
    public String formatValue(T t) {
        Date date = this.convertToDate(t);
        return date.toString();
    }

    @Override
    public T parse(String value) {
        Date date = Date.valueOf(value);
        return this.convertFromDate(date);
    }

    @Override
    public T parseDateTime(long systemTimeMillis) {
        Date ts = new Date(systemTimeMillis);
        return this.convertFromDate(ts);
    }

    @Override
    public boolean isDateTimeCapable() {
        return true;
    }

    @Override
    public String jsonToString(T value, JsonValueAdapter ctx) {
        Date date = this.convertToDate(value);
        return ctx.jsonFromDate(date);
    }

    @Override
    public T jsonFromString(String value, JsonValueAdapter ctx) {
        Date ts = ctx.jsonToDate(value);
        return this.convertFromDate(ts);
    }

    @Override
    public int getLuceneType() {
        return 5;
    }

    @Override
    public Object luceneFromIndexValue(Object value) {
        Long l = (Long)value;
        Date date = new Date(l);
        return this.convertFromDate(date);
    }

    @Override
    public Object luceneToIndexValue(Object value) {
        Date date = this.convertToDate(value);
        return date.getTime();
    }

    @Override
    public Object readData(DataInput dataInput) throws IOException {
        if (!dataInput.readBoolean()) {
            return null;
        }
        long val = dataInput.readLong();
        Date date = new Date(val);
        return this.convertFromDate(date);
    }

    @Override
    public void writeData(DataOutput dataOutput, Object v) throws IOException {
        Object value = v;
        if (value == null) {
            dataOutput.writeBoolean(false);
        } else {
            dataOutput.writeBoolean(true);
            Date date = this.convertToDate(value);
            dataOutput.writeLong(date.getTime());
        }
    }
}

