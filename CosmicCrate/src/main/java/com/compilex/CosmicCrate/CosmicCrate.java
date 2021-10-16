// 
// Decompiled by Procyon v0.5.36
// 

package com.compilex.CosmicCrate;

import java.sql.ResultSet;
import java.util.ArrayList;
import com.compilex.CosmicCrate.database.SQLConnectionThread;
import com.compilex.CosmicCrate.cmds.CommandGiveCC;
import me.ifamasssxd.cosmicmysterychest.CosmicMysteryChest;
import me.ifamasssxd.cosmicmysterychest.struct.MysteryChest;
import org.bukkit.command.CommandExecutor;
import com.compilex.CosmicCrate.cmds.CommandCosmicCrate;
import com.compilex.CosmicCrate.listeners.BlockListener;
import com.compilex.CosmicCrate.listeners.InventoryListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.Listener;
import com.compilex.CosmicCrate.listeners.PlayerListener;
import org.bukkit.Bukkit;
import com.compilex.CosmicCrate.database.SQLQueryThread;
import com.compilex.CosmicCrate.libs.Version;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import org.bukkit.plugin.java.JavaPlugin;

public class CosmicCrate extends JavaPlugin
{
    public static CopyOnWriteArrayList<String> sql_query;
    public static HashMap<String, List<Version>> claimedCosmicCrates;
    private static CosmicCrate plugin;
    private static SQLQueryThread sqlWorker;
    private static Map<String, MysteryChest> chests;

    public void onEnable() {
        (CosmicCrate.plugin = this).registerSQL();
        this.registerListeners();
        this.registerCommands();
        this.loadDatabase();
        chests =  CosmicMysteryChest.get().getChestManager().getMysteryChestMap();
    }
    
    public void onDisable() {
        CosmicCrate.sqlWorker.interrupt();
        CosmicCrate.sqlWorker.doWork();
    }
    
    public static CosmicCrate get() {
        return CosmicCrate.plugin;
    }
    
    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents((Listener)new PlayerListener(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new InventoryListener(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new BlockListener(), (Plugin)this);
    }

    public static Map<String, MysteryChest> getChests() {
        return chests;
    }

    private void registerCommands() {
        this.getCommand("cosmiccrate").setExecutor((CommandExecutor)new CommandCosmicCrate());
        this.getCommand("givecc").setExecutor((CommandExecutor)new CommandGiveCC());
    }
    
    private void registerSQL() {
        CosmicCrate.sql_query = new CopyOnWriteArrayList<String>();
        (CosmicCrate.sqlWorker = new SQLQueryThread()).start();
        CosmicCrate.sqlWorker.executeQuery("CREATE TABLE IF NOT EXISTS ccrates(uuid varchar(36), username varchar(16), version varchar(32))");
    }
    
    private void loadDatabase() {
        CosmicCrate.claimedCosmicCrates = new HashMap<String, List<Version>>();
        int x = 0;
        try {
            final ResultSet rs = SQLConnectionThread.getResultSet("SELECT * FROM ccrates");
            while (rs.next()) {
                final String uuid = rs.getString("uuid");
                Version v = null;
                try {
                    v = Version.valueOf(rs.getString("version"));
                }
                catch (IllegalArgumentException iae) {
                    iae.printStackTrace();
                }
                if (v != null) {
                    final List<Version> claimedCrates = CosmicCrate.claimedCosmicCrates.containsKey(uuid) ? CosmicCrate.claimedCosmicCrates.get(uuid) : new ArrayList<Version>();
                    claimedCrates.add(v);
                    CosmicCrate.claimedCosmicCrates.put(uuid, claimedCrates);
                    ++x;
                }
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
