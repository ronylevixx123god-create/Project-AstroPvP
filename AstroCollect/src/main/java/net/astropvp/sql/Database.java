package net.astropvp.sql;

import net.astropvp.CollectPlugin;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Collections;

public class Database {
    private Connection connection;
    private String host;
    private String username;
    private String password;
    private String database;
    private String url;

    public Database(CollectPlugin instance) {
        this.host = instance.getConfig().getString("hostname");
        this.username = instance.getConfig().getString("username");
        this.password = instance.getConfig().getString("password");
        this.database = instance.getConfig().getString("database");
        this.url = "jdbc:mysql://" + this.host + ":3308/" + this.database + "?autoReconnect=true";
    }

    public void executeUpdate(String update, boolean async) {
        if (async && Bukkit.isPrimaryThread()) {
            Bukkit.getScheduler().scheduleAsyncDelayedTask(CollectPlugin.getInstance(), () -> {
                this.executeUpdate(update, false);
            });
        } else {
            try {
                PreparedStatement pst = this.getConnection().prepareStatement(update);
                try {
                    pst.executeUpdate();
                } finally {
                    if (Collections.singletonList(pst).get(0) != null) {
                        pst.close();
                    }

                }
            } catch (Exception var8) {
                var8.printStackTrace();
            }

        }
    }

    public boolean isConnected() {
        try {
            Connection connect = this.getConnection();
            return connect != null;
        } catch (Exception var2) {
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
        } catch (Exception var2) {
            var2.printStackTrace();
        }

        return this.connection;
    }



}
