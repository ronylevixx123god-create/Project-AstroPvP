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
public class ScalarTypeBytesEncrypted
implements ScalarType<byte[]> {
    private final ScalarTypeBytesBase baseType;
    private final DataEncryptSupport dataEncryptSupport;

    public ScalarTypeBytesEncrypted(ScalarTypeBytesBase baseType, DataEncryptSupport dataEncryptSupport) {
        this.baseType = baseType;
        this.dataEncryptSupport = dataEncryptSupport;
    }

    @Override
    public void bind(DataBind b, byte[] value) throws SQLException {
        value = this.dataEncryptSupport.encrypt(value);
        this.baseType.bind(b, value);
    }

    @Override
    public int getJdbcType() {
        return this.baseType.getJdbcType();
    }

    @Override
    public int getLength() {
        return this.baseType.getLength();
    }

    @Override
    public Class<byte[]> getType() {
        return byte[].class;
    }

    @Override
    public boolean isDateTimeCapable() {
        return this.baseType.isDateTimeCapable();
    }

    @Override
    public boolean isJdbcNative() {
        return this.baseType.isJdbcNative();
    }

    @Override
    public void loadIgnore(DataReader dataReader) {
        this.baseType.loadIgnore(dataReader);
    }

    @Override
    public String format(Object v) {
        throw new RuntimeException("Not used");
    }

    @Override
    public String formatValue(byte[] v) {
        throw new RuntimeException("Not used");
    }

    @Override
    public byte[] parse(String value) {
        return this.baseType.parse(value);
    }

    @Override
    public byte[] parseDateTime(long systemTimeMillis) {
        return this.baseType.parseDateTime(systemTimeMillis);
    }

    @Override
    public byte[] read(DataReader dataReader) throws SQLException {
        byte[] data = (byte[])this.baseType.read(dataReader);
        data = this.dataEncryptSupport.decrypt(data);
        return data;
    }

    @Override
    public byte[] toBeanType(Object value) {
        return this.baseType.toBeanType(value);
    }

    @Override
    public Object toJdbcType(Object value) {
        return this.baseType.toJdbcType(value);
    }

    @Override
    public void accumulateScalarTypes(String propName, CtCompoundTypeScalarList list) {
        this.baseType.accumulateScalarTypes(propName, list);
    }

    @Override
    public String jsonToString(byte[] value, JsonValueAdapter ctx) {
        return this.baseType.jsonToString(value, ctx);
    }

    @Override
    public byte[] jsonFromString(String value, JsonValueAdapter ctx) {
        return (byte[])this.baseType.jsonFromString(value, ctx);
    }

    @Override
    public Object readData(DataInput dataInput) throws IOException {
        int len = dataInput.readInt();
        byte[] value = new byte[len];
        dataInput.readFully(value);
        return value;
    }

    @Override
    public void writeData(DataOutput dataOutput, Object v) throws IOException {
        byte[] value = (byte[])v;
        dataOutput.writeInt(value.length);
        dataOutput.write(value);
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

