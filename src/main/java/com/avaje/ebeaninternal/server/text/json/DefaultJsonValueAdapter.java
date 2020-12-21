/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.text.json;

import com.avaje.ebean.text.json.JsonValueAdapter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DefaultJsonValueAdapter
implements JsonValueAdapter {
    private final SimpleDateFormat dateTimeProto;

    public DefaultJsonValueAdapter(String dateTimeFormat) {
        this.dateTimeProto = new SimpleDateFormat(dateTimeFormat);
    }

    public DefaultJsonValueAdapter() {
        this("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    }

    private SimpleDateFormat dtFormat() {
        return (SimpleDateFormat)this.dateTimeProto.clone();
    }

    public String jsonFromDate(java.sql.Date date) {
        return date.toString();
    }

    public String jsonFromTimestamp(Timestamp date) {
        return this.dtFormat().format(date);
    }

    public java.sql.Date jsonToDate(String jsonDate) {
        return java.sql.Date.valueOf(jsonDate);
    }

    public Timestamp jsonToTimestamp(String jsonDateTime) {
        try {
            Date d = this.dtFormat().parse(jsonDateTime);
            return new Timestamp(d.getTime());
        }
        catch (Exception e) {
            String m = "Error parsing Datetime[" + jsonDateTime + "]";
            throw new RuntimeException(m, e);
        }
    }
}

