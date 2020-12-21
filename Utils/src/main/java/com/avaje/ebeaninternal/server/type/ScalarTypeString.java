/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.type;

import com.avaje.ebean.text.json.JsonValueAdapter;
import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
import com.avaje.ebeaninternal.server.type.DataBind;
import com.avaje.ebeaninternal.server.type.DataReader;
import com.avaje.ebeaninternal.server.type.EscapeJson;
import com.avaje.ebeaninternal.server.type.ScalarTypeBase;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.SQLException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ScalarTypeString
extends ScalarTypeBase<String> {
    public ScalarTypeString() {
        super(String.class, true, 12);
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
        return dataReader.getString();
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
    public String parseDateTime(long systemTimeMillis) {
        return String.valueOf(systemTimeMillis);
    }

    @Override
    public boolean isDateTimeCapable() {
        return true;
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

    @Override
    public Object readData(DataInput dataInput) throws IOException {
        if (!dataInput.readBoolean()) {
            return null;
        }
        return dataInput.readUTF();
    }

    @Override
    public void writeData(DataOutput dataOutput, Object v) throws IOException {
        String value = (String)v;
        if (value == null) {
            dataOutput.writeBoolean(false);
        } else {
            dataOutput.writeBoolean(true);
            dataOutput.writeUTF(value);
        }
    }
}

