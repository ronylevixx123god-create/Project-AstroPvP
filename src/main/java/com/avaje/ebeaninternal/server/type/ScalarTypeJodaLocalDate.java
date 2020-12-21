/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.joda.time.LocalDate
 */
package com.avaje.ebeaninternal.server.type;

import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
import com.avaje.ebeaninternal.server.type.ScalarTypeBaseDate;
import java.util.Date;
import org.joda.time.LocalDate;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ScalarTypeJodaLocalDate
extends ScalarTypeBaseDate<LocalDate> {
    public ScalarTypeJodaLocalDate() {
        super(LocalDate.class, false, 91);
    }

    @Override
    public LocalDate convertFromDate(java.sql.Date ts) {
        return new LocalDate(ts.getTime());
    }

    @Override
    public java.sql.Date convertToDate(LocalDate t) {
        return new java.sql.Date(t.toDateMidnight().getMillis());
    }

    @Override
    public Object toJdbcType(Object value) {
        if (value instanceof LocalDate) {
            return new java.sql.Date(((LocalDate)value).toDateMidnight().getMillis());
        }
        return BasicTypeConverter.toDate(value);
    }

    @Override
    public LocalDate toBeanType(Object value) {
        if (value instanceof Date) {
            return new LocalDate(((Date)value).getTime());
        }
        return (LocalDate)value;
    }

    @Override
    public LocalDate parseDateTime(long systemTimeMillis) {
        return new LocalDate(systemTimeMillis);
    }
}

