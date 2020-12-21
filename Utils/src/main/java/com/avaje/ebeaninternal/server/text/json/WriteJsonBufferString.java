/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.text.json;

import com.avaje.ebeaninternal.server.text.json.WriteJsonBuffer;

public class WriteJsonBufferString
implements WriteJsonBuffer {
    private final StringBuilder buffer = new StringBuilder(256);

    public WriteJsonBufferString append(String content) {
        this.buffer.append(content);
        return this;
    }

    public String getBufferOutput() {
        return this.buffer.toString();
    }

    public String toString() {
        return this.buffer.toString();
    }
}

