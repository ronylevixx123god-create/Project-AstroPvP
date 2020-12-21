/*
 * Decompiled with CFR 0.150.
 */
package com.zachsthings.libcomponents.config;

import com.zachsthings.libcomponents.config.ConfigurationNode;
import java.io.IOException;

public interface ConfigurationFile
extends ConfigurationNode {
    public void load() throws IOException;

    public boolean save();
}

