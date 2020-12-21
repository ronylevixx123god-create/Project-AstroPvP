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
public class ScalarTypeByte
extends ScalarTypeBase<Byte> {
    public ScalarTypeByte() {
        super(Byte.class, true, -6);
    }

    @Override
    public void bind(DataBind b, Byte value) throws SQLException {
        if (value == null) {
            b.setNull(-6);
        } else {
            b.setByte(value);
        }
    }

    @Override
    public Byte read(DataReader dataReader) throws SQLException {
        return dataReader.getByte();
    }

    @Override
    public Object toJdbcType(Object value) {
        return BasicTypeConverter.toByte(value);
    }

    @Override
    public Byte toBeanType(Object value) {
        return BasicTypeConverter.toByte(value);
    }

    @Override
    public String formatValue(Byte t) {
        return t.toString();
    }

    @Override
    public Byte parse(String value) {
        throw new TextException("Not supported");
    }

    @Override
    public Byte parseDateTime(long systemTimeMillis) {
        throw new TextException("Not Supported");
    }

    @Override
    public boolean isDateTimeCapable() {
        return false;
    }

    @Override
    public int getLuceneType() {
        return 7;
    }

    @Override
    public Object luceneFromIndexValue(Object value) {
        byte[] ba = new byte[]{(Byte)value};
        return ba;
    }

    @Override
    public Object luceneToIndexValue(Object value) {
        return ((byte[])value)[0];
    }

    @Override
    public Object readData(DataInput dataInput) throws IOException {
        if (!dataInput.readBoolean()) {
            return null;
        }
        byte val = dataInput.readByte();
        return val;
    }

    @Override
    public void writeData(DataOutput dataOutput, Object v) throws IOException {
        Byte val = (Byte)v;
        if (val == null) {
            dataOutput.writeBoolean(false);
        } else {
            dataOutput.writeBoolean(true);
            dataOutput.writeByte(val.byteValue());
        }
    }
}

