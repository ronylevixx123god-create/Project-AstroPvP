/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.io;

import com.google.common.annotations.Beta;
import java.io.Closeable;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

@Beta
public final class Closeables {
    private static final Logger logger = Logger.getLogger(Closeables.class.getName());

    private Closeables() {
    }

    public static void close(@Nullable Closeable closeable, boolean swallowIOException) throws IOException {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        }
        catch (IOException e) {
            if (swallowIOException) {
                logger.log(Level.WARNING, "IOException thrown while closing Closeable.", e);
            }
            throw e;
        }
    }

    public static void closeQuietly(@Nullable Closeable closeable) {
        try {
            Closeables.close(closeable, true);
        }
        catch (IOException e) {
            logger.log(Level.SEVERE, "IOException should not have been thrown.", e);
        }
    }
}

