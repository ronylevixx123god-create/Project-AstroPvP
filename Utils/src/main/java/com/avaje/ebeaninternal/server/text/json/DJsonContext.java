/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.text.json;

import com.avaje.ebean.text.TextException;
import com.avaje.ebean.text.json.JsonContext;
import com.avaje.ebean.text.json.JsonReadOptions;
import com.avaje.ebean.text.json.JsonValueAdapter;
import com.avaje.ebean.text.json.JsonWriteOptions;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.text.json.ReadJsonContext;
import com.avaje.ebeaninternal.server.text.json.ReadJsonSource;
import com.avaje.ebeaninternal.server.text.json.ReadJsonSourceReader;
import com.avaje.ebeaninternal.server.text.json.ReadJsonSourceString;
import com.avaje.ebeaninternal.server.text.json.WriteJsonBuffer;
import com.avaje.ebeaninternal.server.text.json.WriteJsonBufferString;
import com.avaje.ebeaninternal.server.text.json.WriteJsonBufferWriter;
import com.avaje.ebeaninternal.server.text.json.WriteJsonContext;
import com.avaje.ebeaninternal.util.ParamTypeHelper;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class DJsonContext
implements JsonContext {
    private final SpiEbeanServer server;
    private final JsonValueAdapter dfltValueAdapter;
    private final boolean dfltPretty;

    public DJsonContext(SpiEbeanServer server, JsonValueAdapter dfltValueAdapter, boolean dfltPretty) {
        this.server = server;
        this.dfltValueAdapter = dfltValueAdapter;
        this.dfltPretty = dfltPretty;
    }

    @Override
    public boolean isSupportedType(Type genericType) {
        return this.server.isSupportedType(genericType);
    }

    private ReadJsonSource createReader(Reader jsonReader) {
        return new ReadJsonSourceReader(jsonReader, 256, 512);
    }

    @Override
    public <T> T toBean(Class<T> cls, String json) {
        return this.toBean(cls, new ReadJsonSourceString(json), null);
    }

    @Override
    public <T> T toBean(Class<T> cls, Reader jsonReader) {
        return this.toBean(cls, this.createReader(jsonReader), null);
    }

    @Override
    public <T> T toBean(Class<T> cls, String json, JsonReadOptions options) {
        return this.toBean(cls, new ReadJsonSourceString(json), options);
    }

    @Override
    public <T> T toBean(Class<T> cls, Reader jsonReader, JsonReadOptions options) {
        return this.toBean(cls, this.createReader(jsonReader), options);
    }

    private <T> T toBean(Class<T> cls, ReadJsonSource src, JsonReadOptions options) {
        BeanDescriptor<T> d = this.getDecriptor(cls);
        ReadJsonContext ctx = new ReadJsonContext(src, this.dfltValueAdapter, options);
        return d.jsonReadBean(ctx, null);
    }

    @Override
    public <T> List<T> toList(Class<T> cls, String json) {
        return this.toList(cls, new ReadJsonSourceString(json), null);
    }

    @Override
    public <T> List<T> toList(Class<T> cls, String json, JsonReadOptions options) {
        return this.toList(cls, new ReadJsonSourceString(json), options);
    }

    @Override
    public <T> List<T> toList(Class<T> cls, Reader jsonReader) {
        return this.toList(cls, this.createReader(jsonReader), null);
    }

    @Override
    public <T> List<T> toList(Class<T> cls, Reader jsonReader, JsonReadOptions options) {
        return this.toList(cls, this.createReader(jsonReader), options);
    }

    private <T> List<T> toList(Class<T> cls, ReadJsonSource src, JsonReadOptions options) {
        try {
            BeanDescriptor<T> d = this.getDecriptor(cls);
            ArrayList<T> list = new ArrayList<T>();
            ReadJsonContext ctx = new ReadJsonContext(src, this.dfltValueAdapter, options);
            ctx.readArrayBegin();
            do {
                T bean;
                if ((bean = d.jsonReadBean(ctx, null)) == null) continue;
                list.add(bean);
            } while (ctx.readArrayNext());
            return list;
        }
        catch (RuntimeException e) {
            throw new TextException("Error parsing " + src, e);
        }
    }

    @Override
    public Object toObject(Type genericType, String json, JsonReadOptions options) {
        ParamTypeHelper.TypeInfo info = ParamTypeHelper.getTypeInfo(genericType);
        ParamTypeHelper.ManyType manyType = info.getManyType();
        switch (manyType) {
            case NONE: {
                return this.toBean(info.getBeanType(), json, options);
            }
            case LIST: {
                return this.toList(info.getBeanType(), json, options);
            }
        }
        String msg = "ManyType " + (Object)((Object)manyType) + " not supported yet";
        throw new TextException(msg);
    }

    @Override
    public Object toObject(Type genericType, Reader json, JsonReadOptions options) {
        ParamTypeHelper.TypeInfo info = ParamTypeHelper.getTypeInfo(genericType);
        ParamTypeHelper.ManyType manyType = info.getManyType();
        switch (manyType) {
            case NONE: {
                return this.toBean(info.getBeanType(), json, options);
            }
            case LIST: {
                return this.toList(info.getBeanType(), json, options);
            }
        }
        String msg = "ManyType " + (Object)((Object)manyType) + " not supported yet";
        throw new TextException(msg);
    }

    @Override
    public void toJsonWriter(Object o, Writer writer) {
        this.toJsonWriter(o, writer, this.dfltPretty, null, null);
    }

    @Override
    public void toJsonWriter(Object o, Writer writer, boolean pretty) {
        this.toJsonWriter(o, writer, pretty, null, null);
    }

    @Override
    public void toJsonWriter(Object o, Writer writer, boolean pretty, JsonWriteOptions options) {
        this.toJsonWriter(o, writer, pretty, null, null);
    }

    @Override
    public void toJsonWriter(Object o, Writer writer, boolean pretty, JsonWriteOptions options, String callback) {
        this.toJsonInternal(o, new WriteJsonBufferWriter(writer), pretty, options, callback);
    }

    @Override
    public String toJsonString(Object o) {
        return this.toJsonString(o, this.dfltPretty, null);
    }

    @Override
    public String toJsonString(Object o, boolean pretty) {
        return this.toJsonString(o, pretty, null);
    }

    @Override
    public String toJsonString(Object o, boolean pretty, JsonWriteOptions options) {
        return this.toJsonString(o, pretty, options, null);
    }

    @Override
    public String toJsonString(Object o, boolean pretty, JsonWriteOptions options, String callback) {
        WriteJsonBufferString b = new WriteJsonBufferString();
        this.toJsonInternal(o, b, pretty, options, callback);
        return b.getBufferOutput();
    }

    private void toJsonInternal(Object o, WriteJsonBuffer buffer, boolean pretty, JsonWriteOptions options, String requestCallback) {
        if (o instanceof Collection) {
            this.toJsonFromCollection((Collection)o, buffer, pretty, options, requestCallback);
        } else {
            BeanDescriptor<?> d = this.getDecriptor(o.getClass());
            WriteJsonContext ctx = new WriteJsonContext(buffer, pretty, this.dfltValueAdapter, options, requestCallback);
            d.jsonWrite(ctx, o);
            ctx.end();
        }
    }

    private <T> String toJsonFromCollection(Collection<T> c, WriteJsonBuffer buffer, boolean pretty, JsonWriteOptions options, String requestCallback) {
        Iterator<T> it = c.iterator();
        if (!it.hasNext()) {
            return null;
        }
        WriteJsonContext ctx = new WriteJsonContext(buffer, pretty, this.dfltValueAdapter, options, requestCallback);
        T o = it.next();
        BeanDescriptor<?> d = this.getDecriptor(o.getClass());
        ctx.appendArrayBegin();
        d.jsonWrite(ctx, o);
        while (it.hasNext()) {
            ctx.appendComma();
            T t = it.next();
            d.jsonWrite(ctx, t);
        }
        ctx.appendArrayEnd();
        ctx.end();
        return ctx.getJson();
    }

    private <T> BeanDescriptor<T> getDecriptor(Class<T> cls) {
        BeanDescriptor<T> d = this.server.getBeanDescriptor(cls);
        if (d == null) {
            String msg = "No BeanDescriptor found for " + cls;
            throw new RuntimeException(msg);
        }
        return d;
    }
}

