/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.text.json;

import com.avaje.ebean.text.json.JsonElement;

public class JsonElementString
implements JsonElement {
    private final String value;

    public JsonElementString(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public String toString() {
        return this.value;
    }

    public boolean isPrimitive() {
        return true;
    }

    public String toPrimitiveString() {
        return this.value;
    }
}

