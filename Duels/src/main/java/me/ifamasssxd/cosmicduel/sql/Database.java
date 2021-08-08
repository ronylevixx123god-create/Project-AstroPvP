/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.plugin.Plugin
 */
package me.ifamasssxd.cosmicduel.sql;

import me.ifamasssxd.cosmicduel.CosmicDuel;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Collections;

public class Database {
    String url;
    String host;
    String username;
    String password;
    String database;
    protected Connection connection;

    public Database(CosmicDuel instance) {
        this(instance.getConfig().getString("sql.hostname"), instance.getConfig().getString("sql.username"), instance.getConfig().getString("sql.password"), instance.getConfig().getString("sql.database"));
    }

    public Database(String host, String username, String password, String database) {
        this.username = username;
        this.password = password;
        this.database = database;
        this.url = "jdbc:mysql://" + host + ":3308/" + database + "?autoReconnect=true";
    }

    public Database(String host, String username, String password) {
        this.username = username;
        this.password = password;
        this.url = "jdbc:mysql://" + host + ":3308?autoReconnect=true";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void executeUpdate(String update, boolean async) {
        if (async && Bukkit.isPrimaryThread()) {
            Bukkit.getScheduler().scheduleAsyncDelayedTask((Plugin)CosmicDuel.get(), () -> this.executeUpdate(update, false));
            return;
        }
        try {
            PreparedStatement pst = this.getConnection().prepareStatement(update);
            try {
                pst.executeUpdate();
            }
            finally {
                if (Collections.singletonList(pst).get(0) != null) {
                    pst.close();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        try {
            Connection connect = this.getConnection();
            return connect != null;
        }
        catch (Exception e) {
            return false;
        }
    }

    public synchronized Connection getConnection() {
        try {
            if (this.connection != null) {
                if (this.connection.isValid(1)) {
                    return this.connection;
                }
                this.connection.close();
            }
            this.connection = DriverManager.getConnection(this.url, this.username, this.password);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return this.connection;
    }
}

