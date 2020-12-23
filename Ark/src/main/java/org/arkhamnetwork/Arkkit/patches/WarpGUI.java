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

public class WarpGUI
        extends ArkPlugin {
    private final WarpGUIManager warpManager = new WarpGUIManager();

    public WarpGUI(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        if (!Bukkit.getPluginManager().isPluginEnabled("FactionsPlus")) {
            this.kill();
            Bukkit.getLogger().info("No FactionsPlus detected.. killing");
            return;
        }
        this.registerListener(new WarpGUIListener(this.warpManager));
        this.warpManager.loadHomes();
    }

    @Override
    public void disable() {
        this.warpManager.saveWarps();
        this.unregisterListeners();
    }
}

