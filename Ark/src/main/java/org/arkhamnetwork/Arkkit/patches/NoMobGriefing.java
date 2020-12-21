/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.patches.NoMobGriefingTask;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.plugin.Plugin;

public class NoMobGriefing
extends ArkPlugin {
    public NoMobGriefing(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.registerTask(new NoMobGriefingTask().runTaskTimer(this.getBukkitPlugin(), 100L, 1200L), NoMobGriefingTask.class);
    }

    @Override
    public void disable() {
    }
}

