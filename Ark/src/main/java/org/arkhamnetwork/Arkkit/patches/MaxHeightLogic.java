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

public class MaxHeightLogic
        extends ArkPlugin {
    protected static boolean factions = false;

    public MaxHeightLogic(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        factions = Bukkit.getPluginManager().isPluginEnabled("Factions");
        this.registerListener(new MaxHeightLogicListener());
    }

    @Override
    public void disable() {
    }
}

