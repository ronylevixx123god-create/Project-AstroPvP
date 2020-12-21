/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.joda.time.DateMidnight
 */
package com.avaje.ebeaninternal.server.type;

import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
import com.avaje.ebeaninternal.server.type.ScalarTypeBaseDate;
import java.util.Date;
import org.joda.time.DateMidnight;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ScalarTypeJodaDateMidnight
extends ScalarTypeBaseDate<DateMidnight> {
    public ScalarTypeJodaDateMidnight() {
        super(DateMidnight.class, false, 91);
    }

    @Override
    public DateMidnight convertFromDate(java.sql.Date ts) {
        return new DateMidnight(ts.getTime());
    }

    @Override
    public java.sql.Date convertToDate(DateMidnight t) {
        return new java.sql.Date(t.getMillis());
    }

    @Override
    public Object toJdbcType(Object value) {
        if (value instanceof DateMidnight) {
            return new java.sql.Date(((DateMidnight)value).getMillis());
        }
        return BasicTypeConverter.toDate(value);
    }

    @Override
    public DateMidnight toBeanType(Object value) {
        if (value instanceof Date) {
            return new DateMidnight(((Date)value).getTime());
        }
        return (DateMidnight)value;
    }
}

