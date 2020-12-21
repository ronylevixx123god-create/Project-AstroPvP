/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.type;

import com.avaje.ebean.text.json.JsonValueAdapter;
import com.avaje.ebeaninternal.server.type.CtCompoundTypeScalarList;
import com.avaje.ebeaninternal.server.type.DataBind;
import com.avaje.ebeaninternal.server.type.DataReader;
import com.avaje.ebeaninternal.server.type.ScalarType;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.PersistenceException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ScalarTypeLdapTimestamp<T>
implements ScalarType<T> {
    private static final String timestampLDAPFormat = "yyyyMMddHHmmss'Z'";
    private final ScalarType<T> baseType;

    public ScalarTypeLdapTimestamp(ScalarType<T> baseType) {
        this.baseType = baseType;
    }

    @Override
    public T toBeanType(Object value) {
        if (value == null) {
            return null;
        }
        if (!(value instanceof String)) {
            String msg = "Expecting a String type but got " + value.getClass() + " value[" + value + "]";
            throw new PersistenceException(msg);
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(timestampLDAPFormat);
            Date date = sdf.parse((String)value);
            return this.baseType.parseDateTime(date.getTime());
        }
        catch (Exception e) {
            String msg = "Error parsing LDAP timestamp " + value;
            throw new PersistenceException(msg, e);
        }
    }

    @Override
    public Object toJdbcType(Object value) {
        if (value == null) {
            return null;
        }
        Object ts = this.baseType.toJdbcType(value);
        if (!(ts instanceof Timestamp)) {
            String msg = "Expecting a Timestamp type but got " + value.getClass() + " value[" + value + "]";
            throw new PersistenceException(msg);
        }
        Timestamp t = (Timestamp)ts;
        SimpleDateFormat sdf = new SimpleDateFormat(timestampLDAPFormat);
        return sdf.format(t);
    }

    @Override
    public void bind(DataBind b, T value) throws SQLException {
        this.baseType.bind(b, value);
    }

    @Override
    public int getJdbcType() {
        return 12;
    }

    @Override
    public int getLength() {
        return this.baseType.getLength();
    }

    @Override
    public Class<T> getType() {
        return this.baseType.getType();
    }

    @Override
    public boolean isDateTimeCapable() {
        return this.baseType.isDateTimeCapable();
    }

    @Override
    public boolean isJdbcNative() {
        return false;
    }

    @Override
    public void loadIgnore(DataReader dataReader) {
        this.baseType.loadIgnore(dataReader);
    }

    @Override
    public String format(Object v) {
        return this.baseType.format(v);
    }

    @Override
    public String formatValue(T t) {
        return this.baseType.formatValue(t);
    }

    @Override
    public T parse(String value) {
        return this.baseType.parse(value);
    }

    @Override
    public T parseDateTime(long systemTimeMillis) {
        return this.baseType.parseDateTime(systemTimeMillis);
    }

    @Override
    public T read(DataReader dataReader) throws SQLException {
        return this.baseType.read(dataReader);
    }

    @Override
    public void accumulateScalarTypes(String propName, CtCompoundTypeScalarList list) {
        this.baseType.accumulateScalarTypes(propName, list);
    }

    @Override
    public String jsonToString(T value, JsonValueAdapter ctx) {
        return this.baseType.jsonToString(value, ctx);
    }

    @Override
    public T jsonFromString(String value, JsonValueAdapter ctx) {
        return this.baseType.jsonFromString(value, ctx);
    }

    @Override
    public Object readData(DataInput dataInput) throws IOException {
        return this.baseType.readData(dataInput);
    }

    @Override
    public void writeData(DataOutput dataOutput, Object v) throws IOException {
        this.baseType.writeData(dataOutput, v);
    }

    @Override
    public int getLuceneType() {
        return this.baseType.getLuceneType();
    }

    @Override
    public Object luceneFromIndexValue(Object value) {
        return this.baseType.luceneFromIndexValue(value);
    }

    @Override
    public Object luceneToIndexValue(Object value) {
        return this.baseType.luceneToIndexValue(value);
    }
}

