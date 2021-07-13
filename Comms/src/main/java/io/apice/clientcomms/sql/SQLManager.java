/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 */
package io.apice.clientcomms.sql;

import io.apice.clientcomms.ClientComms;
import io.apice.clientcomms.OCMCMessage;
import io.apice.clientcomms.sql.SQLQueryThread;
import java.io.File;
import java.util.logging.Level;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class SQLManager {
    public static Plugin plugin;
    public static YamlConfiguration sqlconfig;
    public static String user;
    public static String database;
    public static String password;
    public static String port;
    public static String hostname;
    public static String sqlurl;
    public static boolean sqlEnabled;
    public static SQLQueryThread queryThread;

    public static void confirmSectionExists(String section) {
        if (!sqlconfig.contains(section)) {
            sqlconfig.createSection(section);
            plugin.getLogger().log(Level.SEVERE, "SQLConfig.yml field " + section + " has not been set. Connection to database will not succeed. " + plugin.getName() + " Disabling SQL...");
        }
    }

    public static boolean initializeSQL(Plugin pl) {
        plugin = pl;
        sqlconfig = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder().getPath() + "/sqlconfig.yml"));
        SQLManager.confirmSectionExists("user");
        SQLManager.confirmSectionExists("database");
        SQLManager.confirmSectionExists("password");
        SQLManager.confirmSectionExists("port");
        SQLManager.confirmSectionExists("hostname");
        try {
            sqlconfig.save(plugin.getDataFolder().getPath() + "/sqlconfig.yml");
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
        queryThread = new SQLQueryThread();
        queryThread.start();
        return true;
    }

    public static void updatePlayerClientVersion(Player player, OCMCMessage incoming) {
        if (sqlEnabled) {
            if (incoming != null) {
                ClientComms.getQueryQueue().add("INSERT INTO playerData(playerUUID, playerName, clientVersion, lastLogin) VALUES('" + player.getUniqueId().toString() + "', '" + player.getName() + "', '" + incoming.getVersion() + "', '" + System.currentTimeMillis() + "') ON DUPLICATE KEY UPDATE clientVersion='" + incoming.getVersion() + "', playerName='" + player.getName() + "', lastLogin='" + System.currentTimeMillis() + "'");
            } else {
                ClientComms.getQueryQueue().add("INSERT INTO playerData(playerUUID, playerName, clientVersion, lastLogin) VALUES('" + player.getUniqueId().toString() + "', '" + player.getName() + "', '-1', '" + System.currentTimeMillis() + "') ON DUPLICATE KEY UPDATE playerName='" + player.getName() + "', lastLogin='" + System.currentTimeMillis() + "'");
            }
        }
    }

    static {
        sqlEnabled = false;
    }
}

