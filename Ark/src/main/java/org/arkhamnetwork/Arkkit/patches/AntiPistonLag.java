/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.AntiPistonLagListener;
import org.arkhamnetwork.Arkkit.patches.AntiPistonLagTask;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.plugin.Plugin;

public class AntiPistonLag
extends ArkPlugin {
    public AntiPistonLag(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.registerListener(new AntiPistonLagListener());
        this.registerTask(new AntiPistonLagTask().runTaskTimer((Plugin)Arkkit.get(), 1200L, 1200L), AntiPistonLagTask.class);
    }

    @Override
    public void disable() {
    }
}

