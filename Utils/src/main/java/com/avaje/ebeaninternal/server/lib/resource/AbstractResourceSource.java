/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.lib.resource;

import com.avaje.ebeaninternal.server.lib.resource.ResourceContent;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

public abstract class AbstractResourceSource {
    public String readString(ResourceContent content, int bufSize) throws IOException {
        int len;
        if (content == null) {
            throw new NullPointerException("content is null?");
        }
        StringWriter writer = new StringWriter();
        InputStream is = content.getInputStream();
        InputStreamReader reader = new InputStreamReader(is);
        char[] buf = new char[bufSize];
        while ((len = reader.read(buf, 0, buf.length)) != -1) {
            writer.write(buf, 0, len);
        }
        reader.close();
        return writer.toString();
    }

    public byte[] readBytes(ResourceContent content, int bufSize) throws IOException {
        int len;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        InputStream is = content.getInputStream();
        byte[] buf = new byte[bufSize];
        while ((len = is.read(buf, 0, buf.length)) != -1) {
            bytes.write(buf, 0, len);
        }
        is.close();
        return bytes.toByteArray();
    }
}

