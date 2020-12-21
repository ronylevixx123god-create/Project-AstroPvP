/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.TaperSlotsTask;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.plugin.Plugin;

public class TaperSlots
extends ArkPlugin {
    public TaperSlots(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.registerTask(new TaperSlotsTask().runTaskTimer((Plugin)Arkkit.get(), 300L, 600L), TaperSlotsTask.class);
    }

    @Override
    public void disable() {
    }
}

