/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.plugin.Plugin;

public class NoWhitelist
        extends ArkPlugin {
    public NoWhitelist(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.registerTask(new NoWhitelistTask().runTaskTimer(Arkkit.get(), 60L, 60L), NoWhitelist.class);
    }

    @Override
    public void disable() {
    }
}

