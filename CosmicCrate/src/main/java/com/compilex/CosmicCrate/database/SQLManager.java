// 
// Decompiled by Procyon v0.5.36
// 

package com.compilex.CosmicCrate.database;

import java.io.File;
import org.bukkit.Bukkit;
import java.util.logging.Level;
import java.util.concurrent.CopyOnWriteArrayList;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class SQLManager
{
    public static Plugin plugin;
    public static YamlConfiguration sqlconfig;
    public static String user;
    public static String database;
    public static String password;
    public static String port;
    public static String hostname;
    public static String sqlurl;
    public static volatile CopyOnWriteArrayList<String> sql_query;
    
    public static void confirmSectionExists(final String section) {
        if (!SQLManager.sqlconfig.contains(section)) {
            SQLManager.sqlconfig.createSection(section);
            SQLManager.plugin.getLogger().log(Level.SEVERE, "SQLConfig.yml field " + section + " has not been set. Connection to database will not succeed. " + SQLManager.plugin.getName() + " shutting down...");
            Bukkit.getServer().getPluginManager().disablePlugin(SQLManager.plugin);
        }
    }
    
    public static boolean initializeSQL(final Plugin pl) {
        SQLManager.plugin = pl;
        SQLManager.sqlconfig = YamlConfiguration.loadConfiguration(new File(SQLManager.plugin.getDataFolder().getPath() + "/sqlconfig.yml"));
        confirmSectionExists("user");
        confirmSectionExists("database");
        confirmSectionExists("password");
        confirmSectionExists("port");
        confirmSectionExists("hostname");
        try {
            SQLManager.sqlconfig.save(SQLManager.plugin.getDataFolder().getPath() + "/sqlconfig.yml");
            SQLManager.user = SQLManager.sqlconfig.getString("user");
            SQLManager.database = SQLManager.sqlconfig.getString("database");
            SQLManager.password = SQLManager.sqlconfig.getString("password");
            SQLManager.port = SQLManager.sqlconfig.getString("port");
            SQLManager.hostname = SQLManager.sqlconfig.getString("hostname");
            SQLManager.sqlurl = "jdbc:mysql://" + SQLManager.hostname + ":" + SQLManager.port + "/" + SQLManager.database + "?autoReconnect=true";
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    static {
        SQLManager.sql_query = new CopyOnWriteArrayList<String>();
    }
}
