/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.text.json;

import com.avaje.ebean.text.json.JsonReadOptions;
import com.avaje.ebean.text.json.JsonWriteOptions;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface JsonContext {
    public <T> T toBean(Class<T> var1, String var2);

    public <T> T toBean(Class<T> var1, Reader var2);

    public <T> T toBean(Class<T> var1, String var2, JsonReadOptions var3);

    public <T> T toBean(Class<T> var1, Reader var2, JsonReadOptions var3);

    public <T> List<T> toList(Class<T> var1, String var2);

    public <T> List<T> toList(Class<T> var1, String var2, JsonReadOptions var3);

    public <T> List<T> toList(Class<T> var1, Reader var2);

    public <T> List<T> toList(Class<T> var1, Reader var2, JsonReadOptions var3);

    public Object toObject(Type var1, Reader var2, JsonReadOptions var3);

    public Object toObject(Type var1, String var2, JsonReadOptions var3);

    public void toJsonWriter(Object var1, Writer var2);

    public void toJsonWriter(Object var1, Writer var2, boolean var3);

    public void toJsonWriter(Object var1, Writer var2, boolean var3, JsonWriteOptions var4);

    public void toJsonWriter(Object var1, Writer var2, boolean var3, JsonWriteOptions var4, String var5);

    public String toJsonString(Object var1);

    public String toJsonString(Object var1, boolean var2);

    public String toJsonString(Object var1, boolean var2, JsonWriteOptions var3);

    public String toJsonString(Object var1, boolean var2, JsonWriteOptions var3, String var4);

    public boolean isSupportedType(Type var1);
}

