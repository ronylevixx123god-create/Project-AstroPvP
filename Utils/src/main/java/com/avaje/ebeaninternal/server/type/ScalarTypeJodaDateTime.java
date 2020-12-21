/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.joda.time.DateTime
 */
package com.avaje.ebeaninternal.server.type;

import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
import com.avaje.ebeaninternal.server.type.ScalarTypeBaseDateTime;
import java.sql.Timestamp;
import java.util.Date;
import org.joda.time.DateTime;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ScalarTypeJodaDateTime
extends ScalarTypeBaseDateTime<DateTime> {
    public ScalarTypeJodaDateTime() {
        super(DateTime.class, false, 93);
    }

    @Override
    public DateTime convertFromTimestamp(Timestamp ts) {
        return new DateTime(ts.getTime());
    }

    @Override
    public Timestamp convertToTimestamp(DateTime t) {
        return new Timestamp(t.getMillis());
    }

    @Override
    public Object toJdbcType(Object value) {
        if (value instanceof DateTime) {
            return new Timestamp(((DateTime)value).getMillis());
        }
        return BasicTypeConverter.toTimestamp(value);
    }

    @Override
    public DateTime toBeanType(Object value) {
        if (value instanceof Date) {
            return new DateTime(((Date)value).getTime());
        }
        return (DateTime)value;
    }
}

