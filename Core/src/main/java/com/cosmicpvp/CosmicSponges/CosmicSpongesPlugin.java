/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.worldguard.bukkit.WorldGuardPlugin
 *  org.bukkit.Bukkit
 *  org.bukkit.Chunk
 *  org.bukkit.World
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.cosmicpvp.CosmicSponges;

import com.cosmicpvp.CosmicSponges.CosmicSpongesAPI;
import com.cosmicpvp.CosmicSponges.cmds.CommandCSponge;
import com.cosmicpvp.CosmicSponges.libs.SpongeChunk;
import com.cosmicpvp.CosmicSponges.listeners.BlockFlowListener;
import com.cosmicpvp.CosmicSponges.listeners.ChunkListener;
import com.cosmicpvp.CosmicSponges.listeners.SpongeModifyListener;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import java.io.File;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class CosmicSpongesPlugin
extends JavaPlugin {
    private File spongeDirectory;
    private static CosmicSpongesPlugin _;

    public void onEnable() {
        _ = this;
        CosmicSpongesAPI.setCachedSpongeChunks(new HashMap<String, SpongeChunk>());
        this.buildDB();
        this.registerListeners();
        this.registerCommands();
        for (World w : Bukkit.getWorlds()) {
            for (Chunk c : w.getLoadedChunks()) {
                CosmicSpongesAPI.cacheSpongeChunk(c);
            }
        }
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "arkunload lava_sponge");
        WorldGuardPlugin.inst().getConfig().set("simulation.sponge.enable", false);
        for (World w : Bukkit.getWorlds()) {
            WorldGuardPlugin.inst().getGlobalStateManager().get(w).simulateSponge = false;
        }
        CosmicSpongesAPI.debug = false;
    }

    public void onDisable() {
        CosmicSpongesAPI.saveSpongeChunks();
    }

    private void registerListeners() {
        this.getServer().getPluginManager().registerEvents(new ChunkListener(), this);
        this.getServer().getPluginManager().registerEvents(new BlockFlowListener(), this);
        this.getServer().getPluginManager().registerEvents(new SpongeModifyListener(), this);
    }

    private void registerCommands() {
        this.getCommand("csponge").setExecutor(new CommandCSponge());
    }

    private void buildDB() {
        this.spongeDirectory = new File("plugins/CosmicSponges/spongeChunks");
        if (!this.spongeDirectory.exists()) {
            this.spongeDirectory.mkdirs();
        }
        for (World w : Bukkit.getWorlds()) {
            File subDir = new File("plugins/CosmicSponges/spongeChunks/" + w.getName());
            subDir.mkdir();
        }
    }

    public File getSpongeDirectory() {
        return this.spongeDirectory;
    }

    public static CosmicSpongesPlugin get_() {
        return _;
    }
}

