/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.type;

import com.avaje.ebeaninternal.server.type.ScalarTypeBaseVarchar;
import java.util.TimeZone;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ScalarTypeTimeZone
extends ScalarTypeBaseVarchar<TimeZone> {
    public ScalarTypeTimeZone() {
        super(TimeZone.class);
    }

    @Override
    public int getLength() {
        return 20;
    }

    @Override
    public TimeZone convertFromDbString(String dbValue) {
        return TimeZone.getTimeZone(dbValue);
    }

    @Override
    public String convertToDbString(TimeZone beanValue) {
        return beanValue.getID();
    }

    @Override
    public String formatValue(TimeZone v) {
        return v.toString();
    }

    @Override
    public TimeZone parse(String value) {
        return TimeZone.getTimeZone(value);
    }
}

