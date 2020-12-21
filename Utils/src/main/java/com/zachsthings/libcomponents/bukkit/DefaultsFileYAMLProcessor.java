/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.util.yaml.YAMLProcessor
 */
package com.zachsthings.libcomponents.bukkit;

import com.sk89q.util.yaml.YAMLProcessor;
import java.io.InputStream;

public class DefaultsFileYAMLProcessor
extends YAMLProcessor {
    private final String file;

    public DefaultsFileYAMLProcessor(String file, boolean writeDefaults) {
        super(null, writeDefaults);
        this.file = file;
    }

    public InputStream getInputStream() {
        return ((Object)((Object)this)).getClass().getResourceAsStream("/defaults/" + this.file);
    }
}

