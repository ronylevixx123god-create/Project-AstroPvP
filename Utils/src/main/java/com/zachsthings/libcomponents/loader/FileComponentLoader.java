/*
 * Decompiled with CFR 0.150.
 */
package com.zachsthings.libcomponents.loader;

import com.zachsthings.libcomponents.loader.AbstractComponentLoader;
import java.io.File;
import java.util.logging.Logger;

public abstract class FileComponentLoader
extends AbstractComponentLoader {
    protected FileComponentLoader(Logger logger, File configDir) {
        super(logger, configDir);
    }

    public String formatPath(String path) {
        if (path.length() < 6) {
            return path;
        }
        return path.substring(0, path.length() - 6).replaceAll("/", ".");
    }
}

