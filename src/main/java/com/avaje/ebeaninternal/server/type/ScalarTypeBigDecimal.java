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
import java.math.BigDecimal;
import java.sql.SQLException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ScalarTypeBigDecimal
extends ScalarTypeBase<BigDecimal> {
    public ScalarTypeBigDecimal() {
        super(BigDecimal.class, true, 3);
    }

    @Override
    public Object readData(DataInput dataInput) throws IOException {
        if (!dataInput.readBoolean()) {
            return null;
        }
        double val = dataInput.readDouble();
        return new BigDecimal(val);
    }

    @Override
    public void writeData(DataOutput dataOutput, Object v) throws IOException {
        BigDecimal b = (BigDecimal)v;
        if (b == null) {
            dataOutput.writeBoolean(false);
        } else {
            dataOutput.writeBoolean(true);
            dataOutput.writeDouble(b.doubleValue());
        }
    }

    @Override
    public void bind(DataBind b, BigDecimal value) throws SQLException {
        if (value == null) {
            b.setNull(3);
        } else {
            b.setBigDecimal(value);
        }
    }

    @Override
    public BigDecimal read(DataReader dataReader) throws SQLException {
        return dataReader.getBigDecimal();
    }

    @Override
    public Object toJdbcType(Object value) {
        return BasicTypeConverter.toBigDecimal(value);
    }

    @Override
    public BigDecimal toBeanType(Object value) {
        return BasicTypeConverter.toBigDecimal(value);
    }

    @Override
    public String formatValue(BigDecimal t) {
        return t.toPlainString();
    }

    @Override
    public BigDecimal parse(String value) {
        return new BigDecimal(value);
    }

    @Override
    public BigDecimal parseDateTime(long systemTimeMillis) {
        return BigDecimal.valueOf(systemTimeMillis);
    }

    @Override
    public boolean isDateTimeCapable() {
        return true;
    }

    @Override
    public Object luceneFromIndexValue(Object value) {
        Double v = (Double)value;
        return new BigDecimal(v);
    }

    @Override
    public Object luceneToIndexValue(Object value) {
        BigDecimal v = (BigDecimal)value;
        return v.doubleValue();
    }

    @Override
    public int getLuceneType() {
        return 3;
    }
}

