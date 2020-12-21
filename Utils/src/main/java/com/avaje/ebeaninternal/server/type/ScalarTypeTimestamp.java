/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.type;

import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
import com.avaje.ebeaninternal.server.type.DataBind;
import com.avaje.ebeaninternal.server.type.DataReader;
import com.avaje.ebeaninternal.server.type.ScalarTypeBaseDateTime;
import java.sql.SQLException;
import java.sql.Timestamp;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ScalarTypeTimestamp
extends ScalarTypeBaseDateTime<Timestamp> {
    public ScalarTypeTimestamp() {
        super(Timestamp.class, true, 93);
    }

    @Override
    public Timestamp convertFromTimestamp(Timestamp ts) {
        return ts;
    }

    @Override
    public Timestamp convertToTimestamp(Timestamp t) {
        return t;
    }

    @Override
    public void bind(DataBind b, Timestamp value) throws SQLException {
        if (value == null) {
            b.setNull(93);
        } else {
            b.setTimestamp(value);
        }
    }

    @Override
    public Timestamp read(DataReader dataReader) throws SQLException {
        return dataReader.getTimestamp();
    }

    @Override
    public Object toJdbcType(Object value) {
        return BasicTypeConverter.toTimestamp(value);
    }

    @Override
    public Timestamp toBeanType(Object value) {
        return BasicTypeConverter.toTimestamp(value);
    }
}

