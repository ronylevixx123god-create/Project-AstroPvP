/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.io;

import com.google.common.annotations.Beta;
import java.io.IOException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@Beta
public interface ByteProcessor<T> {
    public boolean processBytes(byte[] var1, int var2, int var3) throws IOException;

    public T getResult();
}

