/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.plugin.Plugin;

public class Thanksgiving
        extends ArkPlugin {
    public Thanksgiving(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.registerListener(new ThanksgivingListener());
        this.registerTask(new ThanksgivingTask().runTaskTimer(Arkkit.get(), 2400L, 3200L), Thanksgiving.class);
        this.registerCommand(new ThanksgivingCommand("thanksgiving", "/<command>", "Spawns special thanksgiving turkey mob."));
    }

    @Override
    public void disable() {
    }
}

