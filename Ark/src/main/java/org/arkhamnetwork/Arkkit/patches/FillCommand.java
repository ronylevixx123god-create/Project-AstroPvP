/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

public class FillCommand
        extends ArkPlugin {
    public FillCommand(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.registerCommand(new CommandFill("fill", "/<command> [args]", "Automatically fill nearby dispensers with me.astropvp.cratekeys.items from me.astropvp.cratekeys.inventory.", Arrays.asList("tntfill")));
    }

    @Override
    public void disable() {
    }
}

