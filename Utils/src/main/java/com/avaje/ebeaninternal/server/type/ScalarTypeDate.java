/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.type;

import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
import com.avaje.ebeaninternal.server.type.DataBind;
import com.avaje.ebeaninternal.server.type.DataReader;
import com.avaje.ebeaninternal.server.type.ScalarTypeBaseDate;
import java.sql.Date;
import java.sql.SQLException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ScalarTypeDate
extends ScalarTypeBaseDate<Date> {
    public ScalarTypeDate() {
        super(Date.class, true, 91);
    }

    @Override
    public Date convertFromDate(Date date) {
        return date;
    }

    @Override
    public Date convertToDate(Date t) {
        return t;
    }

    @Override
    public void bind(DataBind b, Date value) throws SQLException {
        if (value == null) {
            b.setNull(91);
        } else {
            b.setDate(value);
        }
    }

    @Override
    public Date read(DataReader dataReader) throws SQLException {
        return dataReader.getDate();
    }

    @Override
    public Object toJdbcType(Object value) {
        return BasicTypeConverter.toDate(value);
    }

    @Override
    public Date toBeanType(Object value) {
        return BasicTypeConverter.toDate(value);
    }
}

