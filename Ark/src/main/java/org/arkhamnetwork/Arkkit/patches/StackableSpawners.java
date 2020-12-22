/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.StackableSpawnerListener;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.plugin.Plugin;

import java.io.IOException;

public class StackableSpawners
extends ArkPlugin {
    public static short maxSpawnersPerBlock = (short)10;

    public StackableSpawners(Plugin p) {
        super(p);
    }

    @Override
    public void enable() throws IOException {
        super.enable();
        maxSpawnersPerBlock = (short)Arkkit.get().getConfig().getInt("settings.stackableSpawners.maxSpawnersPerBlock", 10);
        this.registerListener(new StackableSpawnerListener());
    }

    @Override
    public void disable() throws IOException {
        super.disable();
    }
}

