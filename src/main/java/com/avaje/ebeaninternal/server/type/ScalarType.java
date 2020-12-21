/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.type;

import com.avaje.ebean.text.StringFormatter;
import com.avaje.ebean.text.StringParser;
import com.avaje.ebean.text.json.JsonValueAdapter;
import com.avaje.ebeaninternal.server.type.DataBind;
import com.avaje.ebeaninternal.server.type.DataReader;
import com.avaje.ebeaninternal.server.type.ScalarDataReader;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.SQLException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface ScalarType<T>
extends StringParser,
StringFormatter,
ScalarDataReader<T> {
    public int getLength();

    public boolean isJdbcNative();

    public int getJdbcType();

    public Class<T> getType();

    @Override
    public T read(DataReader var1) throws SQLException;

    @Override
    public void loadIgnore(DataReader var1);

    @Override
    public void bind(DataBind var1, T var2) throws SQLException;

    public Object toJdbcType(Object var1);

    public T toBeanType(Object var1);

    public String formatValue(T var1);

    @Override
    public String format(Object var1);

    public T parse(String var1);

    public T parseDateTime(long var1);

    public boolean isDateTimeCapable();

    public String jsonToString(T var1, JsonValueAdapter var2);

    public T jsonFromString(String var1, JsonValueAdapter var2);

    public Object readData(DataInput var1) throws IOException;

    public void writeData(DataOutput var1, Object var2) throws IOException;

    public Object luceneToIndexValue(Object var1);

    public Object luceneFromIndexValue(Object var1);

    public int getLuceneType();
}

