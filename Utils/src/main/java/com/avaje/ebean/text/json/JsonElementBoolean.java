/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.text.json;

import com.avaje.ebean.text.json.JsonElement;

public class JsonElementBoolean
implements JsonElement {
    public static final JsonElementBoolean TRUE = new JsonElementBoolean(true);
    public static final JsonElementBoolean FALSE = new JsonElementBoolean(false);
    private final boolean value;

    private JsonElementBoolean(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return this.value;
    }

    public String toString() {
        return Boolean.toString(this.value);
    }

    public boolean isPrimitive() {
        return true;
    }

    public String toPrimitiveString() {
        return Boolean.toString(this.value);
    }
}

