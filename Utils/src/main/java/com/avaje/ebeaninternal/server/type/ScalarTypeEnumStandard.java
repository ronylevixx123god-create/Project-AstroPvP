/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.type;

import com.avaje.ebean.text.TextException;
import com.avaje.ebean.text.json.JsonValueAdapter;
import com.avaje.ebeaninternal.server.query.LuceneIndexDataReader;
import com.avaje.ebeaninternal.server.type.DataBind;
import com.avaje.ebeaninternal.server.type.DataReader;
import com.avaje.ebeaninternal.server.type.EscapeJson;
import com.avaje.ebeaninternal.server.type.ScalarTypeBase;
import com.avaje.ebeaninternal.server.type.ScalarTypeEnum;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.SQLException;
import java.util.EnumSet;

public class ScalarTypeEnumStandard {

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static abstract class EnumBase
    extends ScalarTypeBase {
        protected final Class enumType;

        public EnumBase(Class<?> type, boolean jdbcNative, int jdbcType) {
            super(type, jdbcNative, jdbcType);
            this.enumType = type;
        }

        @Override
        public String format(Object t) {
            return t.toString();
        }

        @Override
        public String formatValue(Object t) {
            return t.toString();
        }

        @Override
        public Object parse(String value) {
            return Enum.valueOf(this.enumType, value);
        }

        @Override
        public Object parseDateTime(long systemTimeMillis) {
            throw new TextException("Not Supported");
        }

        @Override
        public boolean isDateTimeCapable() {
            return false;
        }

        @Override
        public Object jsonFromString(String value, JsonValueAdapter ctx) {
            return this.parse(value);
        }

        @Override
        public String jsonToString(Object value, JsonValueAdapter ctx) {
            return EscapeJson.escapeQuote(value.toString());
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
            String s = dataInput.readUTF();
            return this.parse(s);
        }

        @Override
        public void writeData(DataOutput dataOutput, Object v) throws IOException {
            if (v == null) {
                dataOutput.writeBoolean(false);
            } else {
                dataOutput.writeBoolean(true);
                dataOutput.writeUTF(this.format(v));
            }
        }
    }

    public static class OrdinalEnum
    extends EnumBase
    implements ScalarTypeEnum {
        private final Object[] enumArray;

        public OrdinalEnum(Class enumType) {
            super((Class<?>)enumType, false, 4);
            this.enumArray = EnumSet.allOf(enumType).toArray();
        }

        public String getContraintInValues() {
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            for (int i = 0; i < this.enumArray.length; ++i) {
                Enum e = (Enum)this.enumArray[i];
                if (i > 0) {
                    sb.append(",");
                }
                sb.append(e.ordinal());
            }
            sb.append(")");
            return sb.toString();
        }

        public void bind(DataBind b, Object value) throws SQLException {
            if (value == null) {
                b.setNull(4);
            } else {
                Enum e = (Enum)value;
                b.setInt(e.ordinal());
            }
        }

        public Object read(DataReader dataReader) throws SQLException {
            if (dataReader instanceof LuceneIndexDataReader) {
                String s = dataReader.getString();
                return s == null ? null : this.parse(s);
            }
            Integer ordinal = dataReader.getInt();
            if (ordinal == null) {
                return null;
            }
            if (ordinal < 0 || ordinal >= this.enumArray.length) {
                String m = "Unexpected ordinal [" + ordinal + "] out of range [" + this.enumArray.length + "]";
                throw new IllegalStateException(m);
            }
            return this.enumArray[ordinal];
        }

        public Object toJdbcType(Object beanValue) {
            if (beanValue == null) {
                return null;
            }
            Enum e = (Enum)beanValue;
            return e.ordinal();
        }

        public Object toBeanType(Object dbValue) {
            if (dbValue == null) {
                return null;
            }
            int ordinal = (Integer)dbValue;
            if (ordinal < 0 || ordinal >= this.enumArray.length) {
                String m = "Unexpected ordinal [" + ordinal + "] out of range [" + this.enumArray.length + "]";
                throw new IllegalStateException(m);
            }
            return this.enumArray[ordinal];
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static class StringEnum
    extends EnumBase
    implements ScalarTypeEnum {
        private final int length;

        public StringEnum(Class enumType) {
            super((Class<?>)enumType, false, 12);
            this.length = this.maxValueLength(enumType);
        }

        @Override
        public String getContraintInValues() {
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            T[] ea = this.enumType.getEnumConstants();
            for (int i = 0; i < ea.length; ++i) {
                Enum e = (Enum)ea[i];
                if (i > 0) {
                    sb.append(",");
                }
                sb.append("'").append(e.toString()).append("'");
            }
            sb.append(")");
            return sb.toString();
        }

        private int maxValueLength(Class<?> enumType) {
            int maxLen = 0;
            ?[] ea = enumType.getEnumConstants();
            for (int i = 0; i < ea.length; ++i) {
                Enum e = (Enum)ea[i];
                maxLen = Math.max(maxLen, e.toString().length());
            }
            return maxLen;
        }

        @Override
        public int getLength() {
            return this.length;
        }

        @Override
        public void bind(DataBind b, Object value) throws SQLException {
            if (value == null) {
                b.setNull(12);
            } else {
                b.setString(value.toString());
            }
        }

        @Override
        public Object read(DataReader dataReader) throws SQLException {
            String string = dataReader.getString();
            if (string == null) {
                return null;
            }
            return Enum.valueOf(this.enumType, string);
        }

        @Override
        public Object toJdbcType(Object beanValue) {
            if (beanValue == null) {
                return null;
            }
            Enum e = (Enum)beanValue;
            return e.toString();
        }

        @Override
        public Object toBeanType(Object dbValue) {
            if (dbValue == null) {
                return null;
            }
            return Enum.valueOf(this.enumType, (String)dbValue);
        }
    }
}

