/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.patches.CommandSpigotDebug;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.plugin.Plugin;

public class SpigotDebug
extends ArkPlugin {
    public SpigotDebug(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.registerCommand(new CommandSpigotDebug("spigotdebug", "/<command>", "Toggles spigot debug boolean."));
    }

    @Override
    public void disable() {
    }
}

