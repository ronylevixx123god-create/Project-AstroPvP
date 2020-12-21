/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.text.json;

import com.avaje.ebean.text.json.JsonElement;
import com.avaje.ebean.text.json.JsonElementArray;
import com.avaje.ebean.text.json.JsonElementBoolean;
import com.avaje.ebean.text.json.JsonElementNull;
import com.avaje.ebean.text.json.JsonElementNumber;
import com.avaje.ebean.text.json.JsonElementObject;
import com.avaje.ebean.text.json.JsonElementString;
import com.avaje.ebeaninternal.server.text.json.ReadJsonContext;

public class ReadJsonRawReader {
    private final ReadJsonContext ctx;

    public ReadJsonRawReader(ReadJsonContext ctx) {
        this.ctx = ctx;
    }

    public JsonElement readUnknownValue() {
        return this.readValue();
    }

    private JsonElement readValue() {
        this.ctx.ignoreWhiteSpace();
        char c = this.ctx.nextChar();
        switch (c) {
            case '{': {
                return this.readObject();
            }
            case '[': {
                return this.readArray();
            }
            case '\"': {
                return this.readString();
            }
        }
        return this.readUnquoted(c);
    }

    private JsonElement readArray() {
        JsonElementArray a = new JsonElementArray();
        do {
            JsonElement value = this.readValue();
            a.add(value);
        } while (this.ctx.readArrayNext());
        return a;
    }

    private JsonElement readObject() {
        JsonElementObject o = new JsonElementObject();
        while (this.ctx.readKeyNext()) {
            String key = this.ctx.getTokenKey();
            JsonElement value = this.readValue();
            o.put(key, value);
            if (this.ctx.readValueNext()) continue;
            break;
        }
        return o;
    }

    private JsonElement readString() {
        String s = this.ctx.readQuotedValue();
        return new JsonElementString(s);
    }

    private JsonElement readUnquoted(char c) {
        String s = this.ctx.readUnquotedValue(c);
        if ("null".equals(s)) {
            return JsonElementNull.NULL;
        }
        if ("true".equals(s)) {
            return JsonElementBoolean.TRUE;
        }
        if ("false".equals(s)) {
            return JsonElementBoolean.FALSE;
        }
        return new JsonElementNumber(s);
    }
}

