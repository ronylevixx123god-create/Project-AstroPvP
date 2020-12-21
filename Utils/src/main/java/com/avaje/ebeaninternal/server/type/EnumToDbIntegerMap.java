/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.type;

import com.avaje.ebeaninternal.server.type.DataBind;
import com.avaje.ebeaninternal.server.type.DataReader;
import com.avaje.ebeaninternal.server.type.EnumToDbValueMap;
import java.sql.SQLException;
import javax.persistence.PersistenceException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class EnumToDbIntegerMap
extends EnumToDbValueMap<Integer> {
    @Override
    public int getDbType() {
        return 4;
    }

    public EnumToDbIntegerMap add(Object beanValue, String stringDbValue) {
        try {
            Integer value = Integer.valueOf(stringDbValue);
            this.addInternal(beanValue, value);
            return this;
        }
        catch (Exception e) {
            String msg = "Error converted enum type[" + beanValue.getClass().getName();
            msg = msg + "] enum value[" + beanValue + "] string value [" + stringDbValue + "]";
            msg = msg + " to an Integer.";
            throw new PersistenceException(msg, e);
        }
    }

    @Override
    public void bind(DataBind b, Object value) throws SQLException {
        if (value == null) {
            b.setNull(4);
        } else {
            Integer s = (Integer)this.getDbValue(value);
            b.setInt(s);
        }
    }

    @Override
    public Object read(DataReader dataReader) throws SQLException {
        Integer i = dataReader.getInt();
        if (i == null) {
            return null;
        }
        return this.getBeanValue(i);
    }
}

