/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.plugin.Plugin;

public class GiveAll
        extends ArkPlugin {
    public GiveAll(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.registerCommand(new CommandGiveAll("giveall", "/<command> [args]", "Give all players on server an item."));
    }

    @Override
    public void disable() {
    }
}

