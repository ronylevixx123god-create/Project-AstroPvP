/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.joda.time.LocalDateTime
 */
package com.avaje.ebeaninternal.server.type;

import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
import com.avaje.ebeaninternal.server.type.ScalarTypeBaseDateTime;
import java.sql.Timestamp;
import java.util.Date;
import org.joda.time.LocalDateTime;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ScalarTypeJodaLocalDateTime
extends ScalarTypeBaseDateTime<LocalDateTime> {
    public ScalarTypeJodaLocalDateTime() {
        super(LocalDateTime.class, false, 93);
    }

    @Override
    public LocalDateTime convertFromTimestamp(Timestamp ts) {
        return new LocalDateTime(ts.getTime());
    }

    @Override
    public Timestamp convertToTimestamp(LocalDateTime t) {
        return new Timestamp(t.toDateTime().getMillis());
    }

    @Override
    public Object toJdbcType(Object value) {
        if (value instanceof LocalDateTime) {
            return new Timestamp(((LocalDateTime)value).toDateTime().getMillis());
        }
        return BasicTypeConverter.toTimestamp(value);
    }

    @Override
    public LocalDateTime toBeanType(Object value) {
        if (value instanceof Date) {
            return new LocalDateTime(((Date)value).getTime());
        }
        return (LocalDateTime)value;
    }

    @Override
    public LocalDateTime parseDateTime(long systemTimeMillis) {
        return new LocalDateTime(systemTimeMillis);
    }
}

