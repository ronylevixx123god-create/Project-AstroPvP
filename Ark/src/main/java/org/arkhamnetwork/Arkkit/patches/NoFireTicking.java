/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.patches.NoFireTickingTask;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.plugin.Plugin;

public class NoFireTicking
extends ArkPlugin {
    public NoFireTicking(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.registerTask(new NoFireTickingTask().runTaskTimer(this.getBukkitPlugin(), 100L, 1200L), NoFireTickingTask.class);
    }

    @Override
    public void disable() {
    }
}

