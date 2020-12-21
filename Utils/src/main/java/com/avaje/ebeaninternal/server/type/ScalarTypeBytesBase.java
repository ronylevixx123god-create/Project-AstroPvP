/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.type;

import com.avaje.ebean.text.TextException;
import com.avaje.ebeaninternal.server.type.DataBind;
import com.avaje.ebeaninternal.server.type.ScalarTypeBase;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.SQLException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class ScalarTypeBytesBase
extends ScalarTypeBase<byte[]> {
    protected ScalarTypeBytesBase(boolean jdbcNative, int jdbcType) {
        super(byte[].class, jdbcNative, jdbcType);
    }

    public Object convertFromBytes(byte[] bytes) {
        return bytes;
    }

    public byte[] convertToBytes(Object value) {
        return (byte[])value;
    }

    @Override
    public void bind(DataBind b, byte[] value) throws SQLException {
        if (value == null) {
            b.setNull(this.jdbcType);
        } else {
            b.setBytes(value);
        }
    }

    @Override
    public Object toJdbcType(Object value) {
        return value;
    }

    @Override
    public byte[] toBeanType(Object value) {
        return (byte[])value;
    }

    @Override
    public String formatValue(byte[] t) {
        throw new TextException("Not supported");
    }

    @Override
    public byte[] parse(String value) {
        throw new TextException("Not supported");
    }

    @Override
    public byte[] parseDateTime(long systemTimeMillis) {
        throw new TextException("Not supported");
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
        int len = dataInput.readInt();
        byte[] buf = new byte[len];
        dataInput.readFully(buf, 0, buf.length);
        return buf;
    }

    @Override
    public void writeData(DataOutput dataOutput, Object v) throws IOException {
        if (v == null) {
            dataOutput.writeBoolean(false);
        } else {
            byte[] bytes = this.convertToBytes(v);
            dataOutput.writeInt(bytes.length);
            dataOutput.write(bytes);
        }
    }
}

