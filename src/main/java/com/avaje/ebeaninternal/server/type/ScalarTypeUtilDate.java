/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.type;

import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
import com.avaje.ebeaninternal.server.type.DataBind;
import com.avaje.ebeaninternal.server.type.DataReader;
import com.avaje.ebeaninternal.server.type.ScalarTypeBaseDate;
import com.avaje.ebeaninternal.server.type.ScalarTypeBaseDateTime;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class ScalarTypeUtilDate {

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static class DateType
    extends ScalarTypeBaseDate<Date> {
        public DateType() {
            super(Date.class, false, 91);
        }

        @Override
        public Date convertFromDate(java.sql.Date ts) {
            return new Date(ts.getTime());
        }

        @Override
        public java.sql.Date convertToDate(Date t) {
            return new java.sql.Date(t.getTime());
        }

        @Override
        public Object toJdbcType(Object value) {
            return BasicTypeConverter.toDate(value);
        }

        @Override
        public Date toBeanType(Object value) {
            return BasicTypeConverter.toUtilDate(value);
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static class TimestampType
    extends ScalarTypeBaseDateTime<Date> {
        public TimestampType() {
            super(Date.class, false, 93);
        }

        @Override
        public Date read(DataReader dataReader) throws SQLException {
            Timestamp timestamp = dataReader.getTimestamp();
            if (timestamp == null) {
                return null;
            }
            return new Date(timestamp.getTime());
        }

        @Override
        public void bind(DataBind b, Date value) throws SQLException {
            if (value == null) {
                b.setNull(93);
            } else {
                Timestamp timestamp = new Timestamp(value.getTime());
                b.setTimestamp(timestamp);
            }
        }

        @Override
        public Object toJdbcType(Object value) {
            return BasicTypeConverter.toTimestamp(value);
        }

        @Override
        public Date toBeanType(Object value) {
            return BasicTypeConverter.toUtilDate(value);
        }

        @Override
        public Date convertFromTimestamp(Timestamp ts) {
            return new Date(ts.getTime());
        }

        @Override
        public Timestamp convertToTimestamp(Date t) {
            return new Timestamp(t.getTime());
        }

        @Override
        public Date parseDateTime(long systemTimeMillis) {
            return new Date(systemTimeMillis);
        }

        @Override
        public Object luceneFromIndexValue(Object value) {
            Long l = (Long)value;
            return new Date(l);
        }

        @Override
        public Object luceneToIndexValue(Object value) {
            return ((Date)value).getTime();
        }
    }
}

