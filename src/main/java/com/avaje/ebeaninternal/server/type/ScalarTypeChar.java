/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.type;

import com.avaje.ebean.text.json.JsonValueAdapter;
import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
import com.avaje.ebeaninternal.server.type.DataBind;
import com.avaje.ebeaninternal.server.type.DataReader;
import com.avaje.ebeaninternal.server.type.EscapeJson;
import com.avaje.ebeaninternal.server.type.ScalarTypeBaseVarchar;
import java.sql.SQLException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ScalarTypeChar
extends ScalarTypeBaseVarchar<Character> {
    public ScalarTypeChar() {
        super(Character.TYPE, false, 12);
    }

    @Override
    public Character convertFromDbString(String dbValue) {
        return Character.valueOf(dbValue.charAt(0));
    }

    @Override
    public String convertToDbString(Character beanValue) {
        return beanValue.toString();
    }

    @Override
    public void bind(DataBind b, Character value) throws SQLException {
        if (value == null) {
            b.setNull(12);
        } else {
            String s = BasicTypeConverter.toString(value);
            b.setString(s);
        }
    }

    @Override
    public Character read(DataReader dataReader) throws SQLException {
        String string = dataReader.getString();
        if (string == null || string.length() == 0) {
            return null;
        }
        return Character.valueOf(string.charAt(0));
    }

    @Override
    public Object toJdbcType(Object value) {
        return BasicTypeConverter.toString(value);
    }

    @Override
    public Character toBeanType(Object value) {
        String s = BasicTypeConverter.toString(value);
        return Character.valueOf(s.charAt(0));
    }

    @Override
    public String formatValue(Character t) {
        return t.toString();
    }

    @Override
    public Character parse(String value) {
        return Character.valueOf(value.charAt(0));
    }

    @Override
    public Character jsonFromString(String value, JsonValueAdapter ctx) {
        return Character.valueOf(value.charAt(0));
    }

    @Override
    public String jsonToString(Character value, JsonValueAdapter ctx) {
        return EscapeJson.escapeQuote(value.toString());
    }
}

