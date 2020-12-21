/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.type;

import com.avaje.ebean.text.TextException;
import com.avaje.ebean.text.json.JsonValueAdapter;
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
public abstract class ScalarTypeBaseVarchar<T>
extends ScalarTypeBase<T> {
    public ScalarTypeBaseVarchar(Class<T> type) {
        super(type, false, 12);
    }

    public ScalarTypeBaseVarchar(Class<T> type, boolean jdbcNative, int jdbcType) {
        super(type, jdbcNative, jdbcType);
    }

    @Override
    public abstract String formatValue(T var1);

    @Override
    public abstract T parse(String var1);

    public abstract T convertFromDbString(String var1);

    public abstract String convertToDbString(T var1);

    @Override
    public void bind(DataBind b, T value) throws SQLException {
        if (value == null) {
            b.setNull(12);
        } else {
            String s = this.convertToDbString(value);
            b.setString(s);
        }
    }

    @Override
    public T read(DataReader dataReader) throws SQLException {
        String s = dataReader.getString();
        if (s == null) {
            return null;
        }
        return this.convertFromDbString(s);
    }

    @Override
    public T toBeanType(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof String) {
            return this.parse((String)value);
        }
        return (T)value;
    }

    @Override
    public Object toJdbcType(Object value) {
        if (value instanceof String) {
            return this.parse((String)value);
        }
        return value;
    }

    @Override
    public T parseDateTime(long systemTimeMillis) {
        throw new TextException("Not Supported");
    }

    @Override
    public boolean isDateTimeCapable() {
        return false;
    }

    @Override
    public String format(Object v) {
        return this.formatValue(v);
    }

    @Override
    public T jsonFromString(String value, JsonValueAdapter ctx) {
        return this.parse(value);
    }

    public String toJsonString(Object value, JsonValueAdapter ctx) {
        String s = this.format(value);
        return EscapeJson.escapeQuote(s);
    }

    @Override
    public Object luceneFromIndexValue(Object value) {
        String v = (String)value;
        return this.convertFromDbString(v);
    }

    @Override
    public Object luceneToIndexValue(Object value) {
        return this.convertToDbString(value);
    }

    @Override
    public int getLuceneType() {
        return 0;
    }

    @Override
    public Object readData(DataInput dataInput) throws IOException {
        if (!dataInput.readBoolean()) {
            return null;
        }
        String val = dataInput.readUTF();
        return this.convertFromDbString(val);
    }

    @Override
    public void writeData(DataOutput dataOutput, Object v) throws IOException {
        Object value = v;
        if (value == null) {
            dataOutput.writeBoolean(false);
        } else {
            dataOutput.writeBoolean(true);
            String s = this.convertToDbString(value);
            dataOutput.writeUTF(s);
        }
    }
}

