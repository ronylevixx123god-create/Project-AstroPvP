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
public class ScalarTypeLong
extends ScalarTypeBase<Long> {
    public ScalarTypeLong() {
        super(Long.class, true, -5);
    }

    @Override
    public void bind(DataBind b, Long value) throws SQLException {
        if (value == null) {
            b.setNull(-5);
        } else {
            b.setLong(value);
        }
    }

    @Override
    public Long read(DataReader dataReader) throws SQLException {
        return dataReader.getLong();
    }

    @Override
    public Object toJdbcType(Object value) {
        return BasicTypeConverter.toLong(value);
    }

    @Override
    public Long toBeanType(Object value) {
        return BasicTypeConverter.toLong(value);
    }

    @Override
    public String formatValue(Long t) {
        return t.toString();
    }

    @Override
    public Long parse(String value) {
        return Long.valueOf(value);
    }

    @Override
    public Long parseDateTime(long systemTimeMillis) {
        return systemTimeMillis;
    }

    @Override
    public boolean isDateTimeCapable() {
        return true;
    }

    @Override
    public int getLuceneType() {
        return 2;
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
        long val = dataInput.readLong();
        return val;
    }

    @Override
    public void writeData(DataOutput dataOutput, Object v) throws IOException {
        Long value = (Long)v;
        if (value == null) {
            dataOutput.writeBoolean(false);
        } else {
            dataOutput.writeBoolean(true);
            dataOutput.writeLong(value);
        }
    }
}

