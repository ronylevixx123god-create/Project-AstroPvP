/*
 * Decompiled with CFR 0.150.
 */
package org.yaml.snakeyaml.reader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackInputStream;
import java.io.Reader;

public class UnicodeReader
extends Reader {
    PushbackInputStream internalIn;
    InputStreamReader internalIn2 = null;
    private static final int BOM_SIZE = 3;

    public UnicodeReader(InputStream in) {
        this.internalIn = new PushbackInputStream(in, 3);
    }

    public String getEncoding() {
        return this.internalIn2.getEncoding();
    }

    protected void init() throws IOException {
        int unread;
        String encoding;
        if (this.internalIn2 != null) {
            return;
        }
        byte[] bom = new byte[3];
        int n = this.internalIn.read(bom, 0, bom.length);
        if (bom[0] == -17 && bom[1] == -69 && bom[2] == -65) {
            encoding = "UTF-8";
            unread = n - 3;
        } else if (bom[0] == -2 && bom[1] == -1) {
            encoding = "UTF-16BE";
            unread = n - 2;
        } else if (bom[0] == -1 && bom[1] == -2) {
            encoding = "UTF-16LE";
            unread = n - 2;
        } else {
            encoding = "UTF-8";
            unread = n;
        }
        if (unread > 0) {
            this.internalIn.unread(bom, n - unread, unread);
        }
        this.internalIn2 = new InputStreamReader((InputStream)this.internalIn, encoding);
    }

    public void close() throws IOException {
        this.init();
        this.internalIn2.close();
    }

    public int read(char[] cbuf, int off, int len) throws IOException {
        this.init();
        return this.internalIn2.read(cbuf, off, len);
    }
}

