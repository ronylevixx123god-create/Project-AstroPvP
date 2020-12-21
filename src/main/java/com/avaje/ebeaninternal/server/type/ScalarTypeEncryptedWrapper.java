/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.type;

import com.avaje.ebean.text.json.JsonValueAdapter;
import com.avaje.ebeaninternal.server.type.CtCompoundTypeScalarList;
import com.avaje.ebeaninternal.server.type.DataBind;
import com.avaje.ebeaninternal.server.type.DataEncryptSupport;
import com.avaje.ebeaninternal.server.type.DataReader;
import com.avaje.ebeaninternal.server.type.ScalarType;
import com.avaje.ebeaninternal.server.type.ScalarTypeBytesBase;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.SQLException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ScalarTypeEncryptedWrapper<T>
implements ScalarType<T> {
    private final ScalarType<T> wrapped;
    private final DataEncryptSupport dataEncryptSupport;
    private final ScalarTypeBytesBase byteArrayType;

    public ScalarTypeEncryptedWrapper(ScalarType<T> wrapped, ScalarTypeBytesBase byteArrayType, DataEncryptSupport dataEncryptSupport) {
        this.wrapped = wrapped;
        this.byteArrayType = byteArrayType;
        this.dataEncryptSupport = dataEncryptSupport;
    }

    @Override
    public Object readData(DataInput dataInput) throws IOException {
        return this.wrapped.readData(dataInput);
    }

    @Override
    public void writeData(DataOutput dataOutput, Object v) throws IOException {
        this.wrapped.writeData(dataOutput, v);
    }

    @Override
    public T read(DataReader dataReader) throws SQLException {
        byte[] data = dataReader.getBytes();
        String formattedValue = this.dataEncryptSupport.decryptObject(data);
        if (formattedValue == null) {
            return null;
        }
        return this.wrapped.parse(formattedValue);
    }

    private byte[] encrypt(T value) {
        String formatValue = this.wrapped.formatValue(value);
        return this.dataEncryptSupport.encryptObject(formatValue);
    }

    @Override
    public void bind(DataBind b, T value) throws SQLException {
        byte[] encryptedValue = this.encrypt(value);
        this.byteArrayType.bind(b, encryptedValue);
    }

    @Override
    public int getJdbcType() {
        return this.byteArrayType.getJdbcType();
    }

    @Override
    public int getLength() {
        return this.byteArrayType.getLength();
    }

    @Override
    public Class<T> getType() {
        return this.wrapped.getType();
    }

    @Override
    public boolean isDateTimeCapable() {
        return this.wrapped.isDateTimeCapable();
    }

    @Override
    public boolean isJdbcNative() {
        return false;
    }

    @Override
    public void loadIgnore(DataReader dataReader) {
        this.wrapped.loadIgnore(dataReader);
    }

    @Override
    public String format(Object v) {
        return this.formatValue(v);
    }

    @Override
    public String formatValue(T v) {
        return this.wrapped.formatValue(v);
    }

    @Override
    public T parse(String value) {
        return this.wrapped.parse(value);
    }

    @Override
    public T parseDateTime(long systemTimeMillis) {
        return this.wrapped.parseDateTime(systemTimeMillis);
    }

    @Override
    public T toBeanType(Object value) {
        return this.wrapped.toBeanType(value);
    }

    @Override
    public Object toJdbcType(Object value) {
        return this.wrapped.toJdbcType(value);
    }

    @Override
    public void accumulateScalarTypes(String propName, CtCompoundTypeScalarList list) {
        this.wrapped.accumulateScalarTypes(propName, list);
    }

    @Override
    public String jsonToString(T value, JsonValueAdapter ctx) {
        return this.wrapped.jsonToString(value, ctx);
    }

    @Override
    public T jsonFromString(String value, JsonValueAdapter ctx) {
        return this.wrapped.jsonFromString(value, ctx);
    }

    @Override
    public int getLuceneType() {
        return this.wrapped.getLuceneType();
    }

    @Override
    public Object luceneFromIndexValue(Object value) {
        return this.wrapped.luceneFromIndexValue(value);
    }

    @Override
    public Object luceneToIndexValue(Object value) {
        return this.wrapped.luceneToIndexValue(value);
    }
}

