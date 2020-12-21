/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.patches.NoHoppersListener;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.plugin.Plugin;

public class NoHoppers
extends ArkPlugin {
    public NoHoppers(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.registerListener(new NoHoppersListener());
    }

    @Override
    public void disable() {
    }
}

