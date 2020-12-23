/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.earth2me.essentials.Essentials
 *  org.bukkit.Bukkit
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.earth2me.essentials.Essentials;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class FakeVanish
        extends ArkPlugin {
    public Essentials essentials;
    private int taskID = -1;

    public FakeVanish(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.essentials = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
        this.taskID = Arkkit.get().getServer().getScheduler().scheduleSyncRepeatingTask(Arkkit.get(), new FakeVanishTask(this.essentials), 20L, 60L);
    }

    @Override
    public void disable() {
        if (this.taskID != -1) {
            Arkkit.get().getServer().getScheduler().cancelTask(this.taskID);
        }
    }
}

