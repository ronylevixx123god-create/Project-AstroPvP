/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.compilex.CosmicCrate;

import com.compilex.CosmicCrate.cmds.CommandCosmicCrate;
import com.compilex.CosmicCrate.cmds.CommandGiveCC;
import com.compilex.CosmicCrate.database.SQLConnectionThread;
import com.compilex.CosmicCrate.database.SQLQueryThread;
import com.compilex.CosmicCrate.libs.Version;
import com.compilex.CosmicCrate.listeners.BlockListener;
import com.compilex.CosmicCrate.listeners.HeroicMysteryCrateListener;
import com.compilex.CosmicCrate.listeners.InventoryListener;
import com.compilex.CosmicCrate.listeners.MysteryCrateListener;
import com.compilex.CosmicCrate.listeners.PlayerListener;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class CosmicCrate
extends JavaPlugin {
    public static CopyOnWriteArrayList<String> sql_query;
    public static HashMap<String, List<Version>> claimedCosmicCrates;
    private static CosmicCrate plugin;
    private static SQLQueryThread sqlWorker;

    public void onEnable() {
        plugin = this;
        this.registerSQL();
        this.registerListeners();
        this.registerCommands();
        this.loadDatabase();
    }

    public void onDisable() {
        sqlWorker.interrupt();
        sqlWorker.doWork();
    }

    public static CosmicCrate get() {
        return plugin;
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents((Listener)new PlayerListener(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new InventoryListener(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new BlockListener(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new MysteryCrateListener(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new HeroicMysteryCrateListener(), (Plugin)this);
    }

    private void registerCommands() {
        this.getCommand("cosmiccrate").setExecutor((CommandExecutor)new CommandCosmicCrate());
        this.getCommand("givecc").setExecutor((CommandExecutor)new CommandGiveCC());
    }

    private void registerSQL() {
        sql_query = new CopyOnWriteArrayList();
        sqlWorker = new SQLQueryThread();
        sqlWorker.start();
        sqlWorker.executeQuery("CREATE TABLE IF NOT EXISTS ccrates(uuid varchar(36), username varchar(16), version varchar(32))");
    }

    private void loadDatabase() {
        claimedCosmicCrates = new HashMap();
        int x = 0;
        try {
            ResultSet rs = SQLConnectionThread.getResultSet("SELECT * FROM ccrates");
            while (rs.next()) {
                String uuid = rs.getString("uuid");
                Version v = null;
                try {
                    v = Version.valueOf(rs.getString("version"));
                }
                catch (IllegalArgumentException iae) {
                    iae.printStackTrace();
                }
                if (v == null) continue;
                List<Object> claimedCrates = claimedCosmicCrates.containsKey(uuid) ? claimedCosmicCrates.get(uuid) : new ArrayList();
                claimedCrates.add((Object)v);
                claimedCosmicCrates.put(uuid, claimedCrates);
                ++x;
            }
            rs.close();
            Bukkit.getLogger().info("[CosmicCrate] Loaded " + x + " claimed Cosmic Crate metadata values!");
        }
        catch (Exception e) {
            Bukkit.getLogger().info("Failed to download Cosmic Crate data. Stack trace:");
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin((Plugin)this);
        }
    }
}

