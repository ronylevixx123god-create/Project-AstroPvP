/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.text.json;

import com.avaje.ebean.text.json.JsonReadBeanVisitor;
import com.avaje.ebean.text.json.JsonValueAdapter;
import java.util.LinkedHashMap;
import java.util.Map;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class JsonReadOptions {
    protected JsonValueAdapter valueAdapter;
    protected Map<String, JsonReadBeanVisitor<?>> visitorMap = new LinkedHashMap();

    public JsonValueAdapter getValueAdapter() {
        return this.valueAdapter;
    }

    public Map<String, JsonReadBeanVisitor<?>> getVisitorMap() {
        return this.visitorMap;
    }

    public JsonReadOptions setValueAdapter(JsonValueAdapter valueAdapter) {
        this.valueAdapter = valueAdapter;
        return this;
    }

    public JsonReadOptions addRootVisitor(JsonReadBeanVisitor<?> visitor) {
        return this.addVisitor(null, visitor);
    }

    public JsonReadOptions addVisitor(String path, JsonReadBeanVisitor<?> visitor) {
        this.visitorMap.put(path, visitor);
        return this;
    }
}

