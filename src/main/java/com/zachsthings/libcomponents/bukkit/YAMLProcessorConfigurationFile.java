/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.util.yaml.YAMLNode
 *  com.sk89q.util.yaml.YAMLProcessor
 */
package com.zachsthings.libcomponents.bukkit;

import com.sk89q.util.yaml.YAMLNode;
import com.sk89q.util.yaml.YAMLProcessor;
import com.zachsthings.libcomponents.bukkit.YAMLNodeConfigurationNode;
import com.zachsthings.libcomponents.config.ConfigurationFile;
import java.io.IOException;

public class YAMLProcessorConfigurationFile
extends YAMLNodeConfigurationNode
implements ConfigurationFile {
    private final YAMLProcessor wrapped;

    public YAMLProcessorConfigurationFile(YAMLProcessor wrapped) {
        super((YAMLNode)wrapped);
        this.wrapped = wrapped;
    }

    public void load() throws IOException {
        this.wrapped.load();
    }

    public boolean save() {
        return this.wrapped.save();
    }
}

