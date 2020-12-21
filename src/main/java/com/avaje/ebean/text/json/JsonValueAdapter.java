/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.text.json;

import java.sql.Date;
import java.sql.Timestamp;

public interface JsonValueAdapter {
    public String jsonFromDate(Date var1);

    public String jsonFromTimestamp(Timestamp var1);

    public Date jsonToDate(String var1);

    public Timestamp jsonToTimestamp(String var1);
}

