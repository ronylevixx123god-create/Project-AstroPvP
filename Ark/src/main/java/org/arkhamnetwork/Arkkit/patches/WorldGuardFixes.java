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

public class WorldGuardFixes
        extends ArkPlugin {
    public WorldGuardFixes(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        if (!Bukkit.getPluginManager().isPluginEnabled("WorldGuard")) {
            Bukkit.getLogger().info("[WorldGuardFixes] Missing WorldGuard.jar dependency!");
            this.kill();
            return;
        }
        this.registerListener(new WorldGuardFixesListener());
    }

    @Override
    public void disable() {
    }
}

