/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.text.json;

import com.avaje.ebean.text.json.JsonElement;
import java.util.ArrayList;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class JsonElementArray
implements JsonElement {
    private final List<JsonElement> values = new ArrayList<JsonElement>();

    public List<JsonElement> getValues() {
        return this.values;
    }

    public void add(JsonElement value) {
        this.values.add(value);
    }

    public String toString() {
        return this.values.toString();
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

