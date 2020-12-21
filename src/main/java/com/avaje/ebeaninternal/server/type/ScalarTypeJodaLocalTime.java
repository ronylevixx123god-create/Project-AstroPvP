/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.joda.time.DateTimeZone
 *  org.joda.time.LocalTime
 */
package com.avaje.ebeaninternal.server.type;

import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
import com.avaje.ebeaninternal.server.type.DataBind;
import com.avaje.ebeaninternal.server.type.DataReader;
import com.avaje.ebeaninternal.server.type.ScalarTypeBase;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalTime;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ScalarTypeJodaLocalTime
extends ScalarTypeBase<LocalTime> {
    public ScalarTypeJodaLocalTime() {
        super(LocalTime.class, false, 92);
    }

    @Override
    public void bind(DataBind b, LocalTime value) throws SQLException {
        if (value == null) {
            b.setNull(92);
        } else {
            Time sqlTime = new Time(value.getMillisOfDay());
            b.setTime(sqlTime);
        }
    }

    @Override
    public LocalTime read(DataReader dataReader) throws SQLException {
        Time sqlTime = dataReader.getTime();
        if (sqlTime == null) {
            return null;
        }
        return new LocalTime((Object)sqlTime, DateTimeZone.UTC);
    }

    @Override
    public Object toJdbcType(Object value) {
        if (value instanceof LocalTime) {
            return new Time(((LocalTime)value).getMillisOfDay());
        }
        return BasicTypeConverter.toTime(value);
    }

    @Override
    public LocalTime toBeanType(Object value) {
        if (value instanceof Date) {
            return new LocalTime(value, DateTimeZone.UTC);
        }
        return (LocalTime)value;
    }

    @Override
    public String formatValue(LocalTime v) {
        return v.toString();
    }

    @Override
    public LocalTime parse(String value) {
        return new LocalTime((Object)value);
    }

    @Override
    public LocalTime parseDateTime(long systemTimeMillis) {
        return new LocalTime(systemTimeMillis);
    }

    @Override
    public boolean isDateTimeCapable() {
        return true;
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
        String val = dataInput.readUTF();
        return this.parse(val);
    }

    @Override
    public void writeData(DataOutput dataOutput, Object v) throws IOException {
        Time value = (Time)v;
        if (value == null) {
            dataOutput.writeBoolean(false);
        } else {
            dataOutput.writeBoolean(true);
            dataOutput.writeUTF(this.format(value));
        }
    }
}

