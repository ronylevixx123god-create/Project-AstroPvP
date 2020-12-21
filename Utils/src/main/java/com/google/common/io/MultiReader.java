/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.io;

import com.google.common.base.Preconditions;
import com.google.common.io.InputSupplier;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
class MultiReader
extends Reader {
    private final Iterator<? extends InputSupplier<? extends Reader>> it;
    private Reader current;

    MultiReader(Iterator<? extends InputSupplier<? extends Reader>> readers) throws IOException {
        this.it = readers;
        this.advance();
    }

    private void advance() throws IOException {
        this.close();
        if (this.it.hasNext()) {
            this.current = this.it.next().getInput();
        }
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        if (this.current == null) {
            return -1;
        }
        int result = this.current.read(cbuf, off, len);
        if (result == -1) {
            this.advance();
            return this.read(cbuf, off, len);
        }
        return result;
    }

    @Override
    public long skip(long n) throws IOException {
        Preconditions.checkArgument(n >= 0L, "n is negative");
        if (n > 0L) {
            while (this.current != null) {
                long result = this.current.skip(n);
                if (result > 0L) {
                    return result;
                }
                this.advance();
            }
        }
        return 0L;
    }

    @Override
    public boolean ready() throws IOException {
        return this.current != null && this.current.ready();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void close() throws IOException {
        if (this.current != null) {
            try {
                this.current.close();
            }
            finally {
                this.current = null;
            }
        }
    }
}

