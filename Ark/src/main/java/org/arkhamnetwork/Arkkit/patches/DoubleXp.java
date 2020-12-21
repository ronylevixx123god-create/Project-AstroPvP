/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.DoubleXpListener;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.plugin.Plugin;

public class DoubleXp
extends ArkPlugin {
    protected static double xpMod = 2.0;

    public DoubleXp(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        xpMod = Arkkit.get().getConfig().getDouble("settings.double_xp.xpMod");
        this.registerListener(new DoubleXpListener());
    }

    @Override
    public void disable() {
    }
}

