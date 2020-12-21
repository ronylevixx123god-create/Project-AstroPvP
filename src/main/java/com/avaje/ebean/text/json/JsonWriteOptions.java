/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.text.json;

import com.avaje.ebean.text.PathProperties;
import com.avaje.ebean.text.json.JsonValueAdapter;
import com.avaje.ebean.text.json.JsonWriteBeanVisitor;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class JsonWriteOptions {
    protected String callback;
    protected JsonValueAdapter valueAdapter;
    protected Map<String, JsonWriteBeanVisitor<?>> visitorMap;
    protected PathProperties pathProperties;

    public JsonWriteOptions copy() {
        JsonWriteOptions copy = new JsonWriteOptions();
        copy.callback = this.callback;
        copy.valueAdapter = this.valueAdapter;
        copy.pathProperties = this.pathProperties;
        if (this.visitorMap != null) {
            copy.visitorMap = new HashMap(this.visitorMap);
        }
        return copy;
    }

    public String getCallback() {
        return this.callback;
    }

    public JsonWriteOptions setCallback(String callback) {
        this.callback = callback;
        return this;
    }

    public JsonValueAdapter getValueAdapter() {
        return this.valueAdapter;
    }

    public JsonWriteOptions setValueAdapter(JsonValueAdapter valueAdapter) {
        this.valueAdapter = valueAdapter;
        return this;
    }

    public JsonWriteOptions setRootPathVisitor(JsonWriteBeanVisitor<?> visitor) {
        return this.setPathVisitor(null, visitor);
    }

    public JsonWriteOptions setPathVisitor(String path, JsonWriteBeanVisitor<?> visitor) {
        if (this.visitorMap == null) {
            this.visitorMap = new HashMap();
        }
        this.visitorMap.put(path, visitor);
        return this;
    }

    public JsonWriteOptions setPathProperties(String path, Set<String> propertiesToInclude) {
        if (this.pathProperties == null) {
            this.pathProperties = new PathProperties();
        }
        this.pathProperties.put(path, propertiesToInclude);
        return this;
    }

    public JsonWriteOptions setPathProperties(String path, String propertiesToInclude) {
        return this.setPathProperties(path, this.parseProps(propertiesToInclude));
    }

    public JsonWriteOptions setRootPathProperties(String propertiesToInclude) {
        return this.setPathProperties(null, this.parseProps(propertiesToInclude));
    }

    public JsonWriteOptions setRootPathProperties(Set<String> propertiesToInclude) {
        return this.setPathProperties(null, propertiesToInclude);
    }

    private Set<String> parseProps(String propertiesToInclude) {
        LinkedHashSet<String> props = new LinkedHashSet<String>();
        String[] split = propertiesToInclude.split(",");
        for (int i = 0; i < split.length; ++i) {
            String s = split[i].trim();
            if (s.length() <= 0) continue;
            props.add(s);
        }
        return props;
    }

    public Map<String, JsonWriteBeanVisitor<?>> getVisitorMap() {
        return this.visitorMap;
    }

    public void setPathProperties(PathProperties pathProperties) {
        this.pathProperties = pathProperties;
    }

    public PathProperties getPathProperties() {
        return this.pathProperties;
    }
}

