/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.type;

import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
import com.avaje.ebeaninternal.server.type.DataBind;
import com.avaje.ebeaninternal.server.type.ScalarTypeBaseDateTime;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ScalarTypeCalendar
extends ScalarTypeBaseDateTime<Calendar> {
    public ScalarTypeCalendar(int jdbcType) {
        super(Calendar.class, false, jdbcType);
    }

    @Override
    public void bind(DataBind b, Calendar value) throws SQLException {
        if (value == null) {
            b.setNull(93);
        } else {
            Calendar date = value;
            if (this.jdbcType == 93) {
                Timestamp timestamp = new Timestamp(date.getTimeInMillis());
                b.setTimestamp(timestamp);
            } else {
                Date d = new Date(date.getTimeInMillis());
                b.setDate(d);
            }
        }
    }

    @Override
    public Calendar convertFromTimestamp(Timestamp ts) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ts.getTime());
        return calendar;
    }

    @Override
    public Timestamp convertToTimestamp(Calendar t) {
        return new Timestamp(t.getTimeInMillis());
    }

    @Override
    public Object toJdbcType(Object value) {
        return BasicTypeConverter.convert(value, this.jdbcType);
    }

    @Override
    public Calendar toBeanType(Object value) {
        return BasicTypeConverter.toCalendar(value);
    }
}

