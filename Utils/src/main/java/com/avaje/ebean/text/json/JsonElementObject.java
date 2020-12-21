/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.text.json;

import com.avaje.ebean.text.json.JsonElement;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class JsonElementObject
implements JsonElement {
    private final Map<String, JsonElement> map = new LinkedHashMap<String, JsonElement>();

    public void put(String key, JsonElement value) {
        this.map.put(key, value);
    }

    public JsonElement get(String key) {
        return this.map.get(key);
    }

    public JsonElement getValue(String key) {
        return this.map.get(key);
    }

    public Set<String> keySet() {
        return this.map.keySet();
    }

    public Set<Map.Entry<String, JsonElement>> entrySet() {
        return this.map.entrySet();
    }

    public String toString() {
        return this.map.toString();
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public String toPrimitiveString() {
        return null;
    }
}

