/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class WorldBorderFixes
        extends ArkPlugin {
    public WorldBorderFixes(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        if (!Bukkit.getPluginManager().isPluginEnabled("WorldBorder")) {
            Bukkit.getLogger().info("[WorldBorderFixes] Missing WorldBorder.jar dependency!");
            this.kill();
            return;
        }
        this.registerListener(new WorldBorderFixesListener());
        try {
            this.registerListener(new CustomWorldBorderListener());
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    @Override
    public void disable() {
    }
}

