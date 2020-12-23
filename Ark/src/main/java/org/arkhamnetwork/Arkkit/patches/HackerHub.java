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
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class HackerHub
        extends ArkPlugin {
    protected static Essentials ess;

    public HackerHub(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        if (!Bukkit.getPluginManager().isPluginEnabled("Essentials")) {
            Bukkit.getLogger().info("[Arkkit (HackerHub)] Failed to enable, Essentials.jar not found!");
            this.kill();
            return;
        }
        ess = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
        this.registerListener(new HackerHubListener());
    }

    @Override
    public void disable() {
    }
}

