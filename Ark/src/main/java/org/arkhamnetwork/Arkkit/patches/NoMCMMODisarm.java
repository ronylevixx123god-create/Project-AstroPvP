/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.plugin.Plugin;

public class NoMCMMODisarm
        extends ArkPlugin {
    public NoMCMMODisarm(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.registerListener(new NoMCMMODisarmListener());
    }

    @Override
    public void disable() {
    }
}

