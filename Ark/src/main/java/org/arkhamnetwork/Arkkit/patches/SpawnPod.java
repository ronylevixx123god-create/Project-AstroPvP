/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.plugin.Plugin;

public class SpawnPod
        extends ArkPlugin {
    public SpawnPod(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.registerListener(new SpawnPodListener());
        this.registerCommand(new CommandSpawnPod("spawnpod", "/<command>", "Force a spawn pod."));
    }

    @Override
    public void disable() {
    }
}

