/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.patches.FinalBattleCommand;
import org.arkhamnetwork.Arkkit.patches.FinalBattleListener;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.plugin.Plugin;

public class FinalBattle
extends ArkPlugin {
    protected static final String wooflessFactionID = "2";
    protected static final String prestonFactionID = "3";
    protected static boolean wooflessRespawn = true;
    protected static boolean prestonRespawn = true;

    public FinalBattle(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.registerCommand(new FinalBattleCommand("finalbattle", "/<command>", "Admin commands."));
        this.registerListener(new FinalBattleListener());
    }

    @Override
    public void disable() {
    }
}

