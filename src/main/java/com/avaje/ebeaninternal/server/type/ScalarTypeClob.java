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
public class ScalarTypeClob
extends ScalarTypeBaseVarchar<String> {
    static final int clobBufferSize = 512;
    static final int stringInitialSize = 512;

    protected ScalarTypeClob(boolean jdbcNative, int jdbcType) {
        super(String.class, jdbcNative, jdbcType);
    }

    public ScalarTypeClob() {
        super(String.class, true, 2005);
    }

    @Override
    public String convertFromDbString(String dbValue) {
        return dbValue;
    }

    @Override
    public String convertToDbString(String beanValue) {
        return beanValue;
    }

    @Override
    public void bind(DataBind b, String value) throws SQLException {
        if (value == null) {
            b.setNull(12);
        } else {
            b.setString(value);
        }
    }

    @Override
    public String read(DataReader dataReader) throws SQLException {
        return dataReader.getStringClob();
    }

    @Override
    public Object toJdbcType(Object value) {
        return BasicTypeConverter.toString(value);
    }

    @Override
    public String toBeanType(Object value) {
        return BasicTypeConverter.toString(value);
    }

    @Override
    public String formatValue(String t) {
        return t;
    }

    @Override
    public String parse(String value) {
        return value;
    }

    @Override
    public String jsonFromString(String value, JsonValueAdapter ctx) {
        return value;
    }

    @Override
    public String jsonToString(String value, JsonValueAdapter ctx) {
        return EscapeJson.escapeQuote(value);
    }

    @Override
    public int getLuceneType() {
        return 0;
    }

    @Override
    public Object luceneFromIndexValue(Object value) {
        return value;
    }

    @Override
    public Object luceneToIndexValue(Object value) {
        return value;
    }
}

