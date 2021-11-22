// 
// Decompiled by Procyon v0.5.36
// 

package com.compilex.CosmicCrate;

import com.compilex.CosmicCrate.cmds.CommandGiveCC;
import com.compilex.CosmicCrate.libs.CosmicCrateData;
import com.compilex.CosmicCrate.managers.CosmicCrateManager;
import lombok.Getter;
import org.bukkit.command.CommandExecutor;
import com.compilex.CosmicCrate.cmds.CommandCosmicCrate;
import com.compilex.CosmicCrate.listeners.BlockListener;
import com.compilex.CosmicCrate.listeners.InventoryListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.Listener;
import com.compilex.CosmicCrate.listeners.PlayerListener;
import org.bukkit.Bukkit;

import java.util.List;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.bukkit.plugin.java.JavaPlugin;

public class CosmicCrate extends JavaPlugin
{
    public static CopyOnWriteArrayList<String> sql_query;
    public static HashMap<String, List<CosmicCrateData>> claimedCosmicCrates;
    private static CosmicCrate instance;
    @Getter private CosmicCrateManager cosmicCrateManager;

    public void onEnable() {
        instance = this;
        this.registerListeners();
        this.registerCommands();
        cosmicCrateManager = new CosmicCrateManager();
    }
    
    public void onDisable() {

    }
    
    public static CosmicCrate get() {
        return instance;
    }
    
    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents((Listener)new PlayerListener(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new InventoryListener(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new BlockListener(), (Plugin)this);
    }


    private void registerCommands() {
        this.getCommand("cosmiccrate").setExecutor((CommandExecutor)new CommandCosmicCrate());
        this.getCommand("givecc").setExecutor((CommandExecutor)new CommandGiveCC());
    }

    

}
