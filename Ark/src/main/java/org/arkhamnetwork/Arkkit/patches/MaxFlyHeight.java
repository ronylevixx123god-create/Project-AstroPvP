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

public class MaxFlyHeight
        extends ArkPlugin {
    public MaxFlyHeight(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.registerTask(new MaxFlyHeightTask().runTaskTimer(Arkkit.get(), 2L, 2L), MaxFlyHeightTask.class);
    }

    @Override
    public void disable() {
    }
}

