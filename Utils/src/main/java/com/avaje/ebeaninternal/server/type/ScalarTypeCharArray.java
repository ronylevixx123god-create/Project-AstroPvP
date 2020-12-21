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
public class ScalarTypeCharArray
extends ScalarTypeBaseVarchar<char[]> {
    public ScalarTypeCharArray() {
        super(char[].class, false, 12);
    }

    @Override
    public char[] convertFromDbString(String dbValue) {
        return dbValue.toCharArray();
    }

    @Override
    public String convertToDbString(char[] beanValue) {
        return new String(beanValue);
    }

    @Override
    public void bind(DataBind b, char[] value) throws SQLException {
        if (value == null) {
            b.setNull(12);
        } else {
            String s = BasicTypeConverter.toString(value);
            b.setString(s);
        }
    }

    @Override
    public char[] read(DataReader dataReader) throws SQLException {
        String string = dataReader.getString();
        if (string == null) {
            return null;
        }
        return string.toCharArray();
    }

    @Override
    public Object toJdbcType(Object value) {
        return BasicTypeConverter.toString(value);
    }

    @Override
    public char[] toBeanType(Object value) {
        String s = BasicTypeConverter.toString(value);
        return s.toCharArray();
    }

    @Override
    public String formatValue(char[] t) {
        return String.valueOf(t);
    }

    @Override
    public char[] parse(String value) {
        return value.toCharArray();
    }

    @Override
    public char[] jsonFromString(String value, JsonValueAdapter ctx) {
        return value.toCharArray();
    }

    @Override
    public String jsonToString(char[] value, JsonValueAdapter ctx) {
        return EscapeJson.escapeQuote(String.valueOf(value));
    }
}

