/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.io;

import java.io.IOException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface OutputSupplier<T> {
    public T getOutput() throws IOException;
}

