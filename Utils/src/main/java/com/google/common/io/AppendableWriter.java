/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.io;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;

class AppendableWriter
extends Writer {
    private final Appendable target;
    private boolean closed;

    AppendableWriter(Appendable target) {
        this.target = target;
    }

    public void write(char[] cbuf, int off, int len) throws IOException {
        this.checkNotClosed();
        this.target.append(new String(cbuf, off, len));
    }

    public void flush() throws IOException {
        this.checkNotClosed();
        if (this.target instanceof Flushable) {
            ((Flushable)((Object)this.target)).flush();
        }
    }

    public void close() throws IOException {
        this.closed = true;
        if (this.target instanceof Closeable) {
            ((Closeable)((Object)this.target)).close();
        }
    }

    public void write(int c) throws IOException {
        this.checkNotClosed();
        this.target.append((char)c);
    }

    public void write(String str) throws IOException {
        this.checkNotClosed();
        this.target.append(str);
    }

    public void write(String str, int off, int len) throws IOException {
        this.checkNotClosed();
        this.target.append(str, off, off + len);
    }

    public Writer append(char c) throws IOException {
        this.checkNotClosed();
        this.target.append(c);
        return this;
    }

    public Writer append(CharSequence charSeq) throws IOException {
        this.checkNotClosed();
        this.target.append(charSeq);
        return this;
    }

    public Writer append(CharSequence charSeq, int start, int end) throws IOException {
        this.checkNotClosed();
        this.target.append(charSeq, start, end);
        return this;
    }

    private void checkNotClosed() throws IOException {
        if (this.closed) {
            throw new IOException("Cannot write to a closed writer.");
        }
    }
}

