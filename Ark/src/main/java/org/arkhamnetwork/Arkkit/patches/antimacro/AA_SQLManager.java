/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches.antimacro;

import java.io.File;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class AA_SQLManager {
    public static Plugin plugin;
    public static YamlConfiguration sqlconfig;
    public static String user;
    public static String database;
    public static String password;
    public static String port;
    public static String hostname;
    public static String sqlurl;
    public static volatile CopyOnWriteArrayList<String> sql_query;

    public static void confirmSectionExists(String section) {
        if (!sqlconfig.contains(section)) {
            sqlconfig.createSection(section);
            plugin.getLogger().log(Level.SEVERE, "AA_SQLConfig.yml field " + section + " has not been set. Connection to database will not succeed. " + plugin.getName() + " shutting down...");
            Bukkit.getServer().getPluginManager().disablePlugin(plugin);
        }
    }

    public static boolean initializeSQL(Plugin pl) {
        plugin = pl;
        sqlconfig = YamlConfiguration.loadConfiguration((File)new File(plugin.getDataFolder().getPath() + "/AA_sqlconfig.yml"));
        AA_SQLManager.confirmSectionExists("user");
        AA_SQLManager.confirmSectionExists("database");
        AA_SQLManager.confirmSectionExists("password");
        AA_SQLManager.confirmSectionExists("port");
        AA_SQLManager.confirmSectionExists("hostname");
        try {
            sqlconfig.save(plugin.getDataFolder().getPath() + "/AA_sqlconfig.yml");
            user = sqlconfig.getString("user");
            database = sqlconfig.getString("database");
            password = sqlconfig.getString("password");
            port = sqlconfig.getString("port");
            hostname = sqlconfig.getString("hostname");
            sqlurl = "jdbc:mysql://" + hostname + ":" + port + "/" + database + "?autoReconnect=true";
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    static {
        sql_query = new CopyOnWriteArrayList();
    }
}

