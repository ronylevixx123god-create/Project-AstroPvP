/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.type;

import com.avaje.ebean.text.TextException;
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
public class ScalarTypeShort
extends ScalarTypeBase<Short> {
    public ScalarTypeShort() {
        super(Short.class, true, 5);
    }

    @Override
    public void bind(DataBind b, Short value) throws SQLException {
        if (value == null) {
            b.setNull(5);
        } else {
            b.setShort(value);
        }
    }

    @Override
    public Short read(DataReader dataReader) throws SQLException {
        return dataReader.getShort();
    }

    @Override
    public Object toJdbcType(Object value) {
        return BasicTypeConverter.toShort(value);
    }

    @Override
    public Short toBeanType(Object value) {
        return BasicTypeConverter.toShort(value);
    }

    @Override
    public String formatValue(Short v) {
        return v.toString();
    }

    @Override
    public Short parse(String value) {
        return Short.valueOf(value);
    }

    @Override
    public Short parseDateTime(long systemTimeMillis) {
        throw new TextException("Not Supported");
    }

    @Override
    public boolean isDateTimeCapable() {
        return false;
    }

    @Override
    public int getLuceneType() {
        return 1;
    }

    @Override
    public Object luceneFromIndexValue(Object value) {
        return Short.valueOf(value.toString());
    }

    @Override
    public Object luceneToIndexValue(Object value) {
        return ((Short)value).intValue();
    }

    @Override
    public Object readData(DataInput dataInput) throws IOException {
        if (!dataInput.readBoolean()) {
            return null;
        }
        short val = dataInput.readShort();
        return val;
    }

    @Override
    public void writeData(DataOutput dataOutput, Object v) throws IOException {
        Short value = (Short)v;
        if (value == null) {
            dataOutput.writeBoolean(false);
        } else {
            dataOutput.writeBoolean(true);
            dataOutput.writeShort(value.shortValue());
        }
    }
}

