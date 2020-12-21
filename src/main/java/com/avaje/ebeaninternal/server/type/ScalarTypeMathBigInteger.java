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
import java.math.BigInteger;
import java.sql.SQLException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ScalarTypeMathBigInteger
extends ScalarTypeBase<BigInteger> {
    public ScalarTypeMathBigInteger() {
        super(BigInteger.class, false, -5);
    }

    @Override
    public void bind(DataBind b, BigInteger value) throws SQLException {
        if (value == null) {
            b.setNull(-5);
        } else {
            b.setLong(value.longValue());
        }
    }

    @Override
    public BigInteger read(DataReader dataReader) throws SQLException {
        Long l = dataReader.getLong();
        if (l == null) {
            return null;
        }
        return new BigInteger(String.valueOf(l));
    }

    @Override
    public Object toJdbcType(Object value) {
        return BasicTypeConverter.toLong(value);
    }

    @Override
    public BigInteger toBeanType(Object value) {
        return BasicTypeConverter.toMathBigInteger(value);
    }

    @Override
    public String formatValue(BigInteger v) {
        return v.toString();
    }

    @Override
    public BigInteger parse(String value) {
        return new BigInteger(value);
    }

    @Override
    public BigInteger parseDateTime(long systemTimeMillis) {
        return BigInteger.valueOf(systemTimeMillis);
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
        return BigInteger.valueOf((Long)value);
    }

    @Override
    public Object luceneToIndexValue(Object value) {
        return ((BigInteger)value).longValue();
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

