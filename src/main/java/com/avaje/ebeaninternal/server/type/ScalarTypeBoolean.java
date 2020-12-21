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

public class ScalarTypeBoolean {

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static abstract class BooleanBase
    extends ScalarTypeBase<Boolean> {
        public BooleanBase(boolean jdbcNative, int jdbcType) {
            super(Boolean.class, jdbcNative, jdbcType);
        }

        @Override
        public String formatValue(Boolean t) {
            return t.toString();
        }

        @Override
        public Boolean parse(String value) {
            return Boolean.valueOf(value);
        }

        @Override
        public Boolean parseDateTime(long systemTimeMillis) {
            throw new TextException("Not Supported");
        }

        @Override
        public boolean isDateTimeCapable() {
            return false;
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
            boolean val = dataInput.readBoolean();
            return val;
        }

        @Override
        public void writeData(DataOutput dataOutput, Object v) throws IOException {
            Boolean val = (Boolean)v;
            if (val == null) {
                dataOutput.writeBoolean(false);
            } else {
                dataOutput.writeBoolean(true);
                dataOutput.writeBoolean(val);
            }
        }
    }

    public static class StringBoolean
    extends BooleanBase {
        private final String trueValue;
        private final String falseValue;

        public StringBoolean(String trueValue, String falseValue) {
            super(false, 12);
            this.trueValue = trueValue;
            this.falseValue = falseValue;
        }

        public int getLength() {
            return Math.max(this.trueValue.length(), this.falseValue.length());
        }

        public void bind(DataBind b, Boolean value) throws SQLException {
            if (value == null) {
                b.setNull(12);
            } else {
                b.setString(this.toString(value));
            }
        }

        public Boolean read(DataReader dataReader) throws SQLException {
            String string = dataReader.getString();
            if (string == null) {
                return null;
            }
            if (string.equals(this.trueValue)) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }

        public Object toJdbcType(Object value) {
            return this.toString(value);
        }

        public String toString(Object value) {
            if (value == null) {
                return null;
            }
            Boolean b = (Boolean)value;
            if (b.booleanValue()) {
                return this.trueValue;
            }
            return this.falseValue;
        }

        public Boolean toBeanType(Object value) {
            if (value == null) {
                return null;
            }
            if (value instanceof Boolean) {
                return (Boolean)value;
            }
            if (this.trueValue.equals(value)) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }
    }

    public static class IntBoolean
    extends BooleanBase {
        private final Integer trueValue;
        private final Integer falseValue;

        public IntBoolean(Integer trueValue, Integer falseValue) {
            super(false, 4);
            this.trueValue = trueValue;
            this.falseValue = falseValue;
        }

        public int getLength() {
            return 1;
        }

        public void bind(DataBind b, Boolean value) throws SQLException {
            if (value == null) {
                b.setNull(4);
            } else {
                b.setInt(this.toInteger(value));
            }
        }

        public Boolean read(DataReader dataReader) throws SQLException {
            Integer i = dataReader.getInt();
            if (i == null) {
                return null;
            }
            if (i.equals(this.trueValue)) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }

        public Object toJdbcType(Object value) {
            return this.toInteger(value);
        }

        public Integer toInteger(Object value) {
            if (value == null) {
                return null;
            }
            Boolean b = (Boolean)value;
            if (b.booleanValue()) {
                return this.trueValue;
            }
            return this.falseValue;
        }

        public Boolean toBeanType(Object value) {
            if (value == null) {
                return null;
            }
            if (value instanceof Boolean) {
                return (Boolean)value;
            }
            if (this.trueValue.equals(value)) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }
    }

    public static class BitBoolean
    extends BooleanBase {
        public BitBoolean() {
            super(true, -7);
        }

        public Boolean toBeanType(Object value) {
            return BasicTypeConverter.toBoolean(value);
        }

        public Object toJdbcType(Object value) {
            return BasicTypeConverter.toBoolean(value);
        }

        public void bind(DataBind b, Boolean value) throws SQLException {
            if (value == null) {
                b.setNull(-7);
            } else {
                b.setBoolean(value);
            }
        }

        public Boolean read(DataReader dataReader) throws SQLException {
            return dataReader.getBoolean();
        }
    }

    public static class Native
    extends BooleanBase {
        public Native() {
            super(true, 16);
        }

        public Boolean toBeanType(Object value) {
            return BasicTypeConverter.toBoolean(value);
        }

        public Object toJdbcType(Object value) {
            return BasicTypeConverter.convert(value, this.jdbcType);
        }

        public void bind(DataBind b, Boolean value) throws SQLException {
            if (value == null) {
                b.setNull(16);
            } else {
                b.setBoolean(value);
            }
        }

        public Boolean read(DataReader dataReader) throws SQLException {
            return dataReader.getBoolean();
        }
    }
}

