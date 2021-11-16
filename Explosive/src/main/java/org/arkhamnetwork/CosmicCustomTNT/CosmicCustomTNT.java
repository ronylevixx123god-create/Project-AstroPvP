/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 */
package org.arkhamnetwork.CosmicCustomTNT;

import org.arkhamnetwork.CosmicCustomTNT.cmds.CommandCTNT;
import org.arkhamnetwork.CosmicCustomTNT.listeners.TNTListener;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class CosmicCustomTNT
extends JavaPlugin {
    private static CosmicCustomTNT plugi = null;

    public void onEnable() {
        plugi = this;
        plugi.registerCommands();
        this.registerListeners();
    }

    public void onDisable() {
    }

    public static CosmicCustomTNT get() {
        return plugi;
    }

    private void registerListeners() {
        this.getServer().getPluginManager().registerEvents((Listener)new TNTListener(), (Plugin)this);
    }

    private void registerCommands() {
        this.getCommand("ctnt").setExecutor((CommandExecutor)new CommandCTNT());
    }
}

