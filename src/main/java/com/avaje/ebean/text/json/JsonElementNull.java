/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.text.json;

import com.avaje.ebean.text.json.JsonElement;

public class JsonElementNull
implements JsonElement {
    public static final JsonElementNull NULL = new JsonElementNull();

    private JsonElementNull() {
    }

    public String getValue() {
        return "null";
    }

    public String toString() {
        return "json null";
    }

    public boolean isPrimitive() {
        return true;
    }

    public String toPrimitiveString() {
        return null;
    }
}

