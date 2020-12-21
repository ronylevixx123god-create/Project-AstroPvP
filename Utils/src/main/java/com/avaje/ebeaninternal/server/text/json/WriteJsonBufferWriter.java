/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.text.json;

import com.avaje.ebean.text.TextException;
import com.avaje.ebeaninternal.server.text.json.WriteJsonBuffer;
import java.io.IOException;
import java.io.Writer;

public class WriteJsonBufferWriter
implements WriteJsonBuffer {
    private final Writer buffer;

    public WriteJsonBufferWriter(Writer buffer) {
        this.buffer = buffer;
    }

    public WriteJsonBufferWriter append(String content) {
        try {
            this.buffer.write(content);
            return this;
        }
        catch (IOException e) {
            throw new TextException(e);
        }
    }
}

