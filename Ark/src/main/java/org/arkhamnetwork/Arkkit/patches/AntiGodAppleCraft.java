/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.patches.AntiGodAppleCraftListener;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.plugin.Plugin;

public class AntiGodAppleCraft
extends ArkPlugin {
    public AntiGodAppleCraft(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.registerListener(new AntiGodAppleCraftListener());
    }

    @Override
    public void disable() {
    }
}

