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
import java.sql.SQLException;
import java.sql.Timestamp;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class ScalarTypeBaseDateTime<T>
extends ScalarTypeBase<T> {
    public ScalarTypeBaseDateTime(Class<T> type, boolean jdbcNative, int jdbcType) {
        super(type, jdbcNative, jdbcType);
    }

    public abstract Timestamp convertToTimestamp(T var1);

    public abstract T convertFromTimestamp(Timestamp var1);

    @Override
    public void bind(DataBind b, T value) throws SQLException {
        if (value == null) {
            b.setNull(93);
        } else {
            Timestamp ts = this.convertToTimestamp(value);
            b.setTimestamp(ts);
        }
    }

    @Override
    public T read(DataReader dataReader) throws SQLException {
        Timestamp ts = dataReader.getTimestamp();
        if (ts == null) {
            return null;
        }
        return this.convertFromTimestamp(ts);
    }

    @Override
    public String formatValue(T t) {
        Timestamp ts = this.convertToTimestamp(t);
        return ts.toString();
    }

    @Override
    public T parse(String value) {
        Timestamp ts = Timestamp.valueOf(value);
        return this.convertFromTimestamp(ts);
    }

    @Override
    public T parseDateTime(long systemTimeMillis) {
        Timestamp ts = new Timestamp(systemTimeMillis);
        return this.convertFromTimestamp(ts);
    }

    @Override
    public boolean isDateTimeCapable() {
        return true;
    }

    @Override
    public String jsonToString(T value, JsonValueAdapter ctx) {
        Timestamp ts = this.convertToTimestamp(value);
        return ctx.jsonFromTimestamp(ts);
    }

    @Override
    public T jsonFromString(String value, JsonValueAdapter ctx) {
        Timestamp ts = ctx.jsonToTimestamp(value);
        return this.convertFromTimestamp(ts);
    }

    @Override
    public int getLuceneType() {
        return 6;
    }

    @Override
    public Object luceneFromIndexValue(Object value) {
        Long l = (Long)value;
        Timestamp ts = new Timestamp(l);
        return this.convertFromTimestamp(ts);
    }

    @Override
    public Object luceneToIndexValue(Object value) {
        Timestamp ts = this.convertToTimestamp(value);
        return ts.getTime();
    }

    @Override
    public Object readData(DataInput dataInput) throws IOException {
        if (!dataInput.readBoolean()) {
            return null;
        }
        long val = dataInput.readLong();
        Timestamp ts = new Timestamp(val);
        return this.convertFromTimestamp(ts);
    }

    @Override
    public void writeData(DataOutput dataOutput, Object v) throws IOException {
        Object value = v;
        if (value == null) {
            dataOutput.writeBoolean(false);
        } else {
            dataOutput.writeBoolean(true);
            Timestamp ts = this.convertToTimestamp(value);
            dataOutput.writeLong(ts.getTime());
        }
    }
}

