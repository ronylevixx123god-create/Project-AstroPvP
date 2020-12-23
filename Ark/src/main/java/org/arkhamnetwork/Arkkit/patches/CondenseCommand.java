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

public class CondenseCommand
        extends ArkPlugin {
    public CondenseCommand(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.registerCommand(new CommandCondense("condense", "/<command>", "Condenses all materials in the inventory into blocks.", Arrays.asList("combine", "organize", "sort")));
    }

    @Override
    public void disable() {
    }
}

