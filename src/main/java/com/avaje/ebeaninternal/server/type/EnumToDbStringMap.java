/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.type;

import com.avaje.ebeaninternal.server.type.DataBind;
import com.avaje.ebeaninternal.server.type.DataReader;
import com.avaje.ebeaninternal.server.type.EnumToDbValueMap;
import java.sql.SQLException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class EnumToDbStringMap
extends EnumToDbValueMap<String> {
    @Override
    public int getDbType() {
        return 12;
    }

    public EnumToDbStringMap add(Object beanValue, String dbValue) {
        this.addInternal(beanValue, dbValue);
        return this;
    }

    @Override
    public void bind(DataBind b, Object value) throws SQLException {
        if (value == null) {
            b.setNull(12);
        } else {
            String s = (String)this.getDbValue(value);
            b.setString(s);
        }
    }

    @Override
    public Object read(DataReader dataReader) throws SQLException {
        String s = dataReader.getString();
        if (s == null) {
            return null;
        }
        return this.getBeanValue(s);
    }
}

