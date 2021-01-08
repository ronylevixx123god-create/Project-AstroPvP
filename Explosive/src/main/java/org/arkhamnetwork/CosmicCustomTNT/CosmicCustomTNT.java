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

import com.compilex.CosmicCustomCreepers.cmds.CommandCCC;
import com.compilex.CosmicCustomCreepers.listeners.CreeperListener;
import lombok.Getter;
import org.arkhamnetwork.CosmicCustomTNT.cmds.CommandCTNT;
import org.arkhamnetwork.CosmicCustomTNT.listeners.TNTListener;
import org.bukkit.plugin.java.JavaPlugin;

public class CosmicCustomTNT
extends JavaPlugin {
    @Getter private static CosmicCustomTNT plugi = null;
    @Getter private CreeperListener creeperListener;

    public void onEnable() {
        plugi = this;
        this.registerCommands();
        this.registerListeners();
    }

    public void onDisable() {
    }

    public static CosmicCustomTNT get() {
        return plugi;
    }

    private void registerListeners() {
        this.getServer().getPluginManager().registerEvents(new TNTListener(), this);
        this.creeperListener = new CreeperListener();
        this.getServer().getPluginManager().registerEvents(this.creeperListener, this);
    }

    private void registerCommands() {
        this.getCommand("ctnt").setExecutor(new CommandCTNT());
        this.getCommand("ccc").setExecutor(new CommandCCC());
    }
}

