/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import java.util.Arrays;
import org.arkhamnetwork.Arkkit.patches.CommandNerfNetherSpawners;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.plugin.Plugin;

public class NerfNetherSpawners
extends ArkPlugin {
    public NerfNetherSpawners(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.registerCommand(new CommandNerfNetherSpawners("nerfnether", "/<command>", "Nerfs nether spawner quantity.", Arrays.asList("")));
    }

    @Override
    public void disable() {
    }
}

