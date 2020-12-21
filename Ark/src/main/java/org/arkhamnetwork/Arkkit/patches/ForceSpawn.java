/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import java.util.ArrayList;
import java.util.Arrays;
import org.arkhamnetwork.Arkkit.patches.CommandForceSpawn;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.plugin.Plugin;

public class ForceSpawn
extends ArkPlugin {
    public ForceSpawn(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.registerCommand(new CommandForceSpawn("forcespawn", "/<command> [args]", "Forces all players in a radius to be teleported to the main world's spawn.", new ArrayList<String>(Arrays.asList("sendtospawn"))));
    }

    @Override
    public void disable() {
    }
}

