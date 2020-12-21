/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.worldedit.Vector
 */
package com.sk89q.worldguard.blacklist.loggers;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.blacklist.events.BlacklistEvent;
import com.sk89q.worldguard.blacklist.events.BlockBreakBlacklistEvent;
import com.sk89q.worldguard.blacklist.events.BlockInteractBlacklistEvent;
import com.sk89q.worldguard.blacklist.events.BlockPlaceBlacklistEvent;
import com.sk89q.worldguard.blacklist.events.DestroyWithBlacklistEvent;
import com.sk89q.worldguard.blacklist.events.ItemAcquireBlacklistEvent;
import com.sk89q.worldguard.blacklist.events.ItemDropBlacklistEvent;
import com.sk89q.worldguard.blacklist.events.ItemUseBlacklistEvent;
import com.sk89q.worldguard.blacklist.loggers.BlacklistLoggerHandler;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseLoggerHandler
implements BlacklistLoggerHandler {
    private final String dsn;
    private final String user;
    private final String pass;
    private final String table;
    private final String worldName;
    private Connection conn;
    private final Logger logger;

    public DatabaseLoggerHandler(String dsn, String user, String pass, String table, String worldName, Logger logger) {
        this.dsn = dsn;
        this.user = user;
        this.pass = pass;
        this.table = table;
        this.worldName = worldName;
        this.logger = logger;
    }

    private Connection getConnection() throws SQLException {
        if (this.conn == null || this.conn.isClosed()) {
            this.conn = DriverManager.getConnection(this.dsn, this.user, this.pass);
        }
        return this.conn;
    }

    private void logEvent(String event, LocalPlayer player, Vector pos, int item, String comment) {
        try {
            Connection conn = this.getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + this.table + "(event, world, player, x, y, z, item, time, comment) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, event);
            stmt.setString(2, this.worldName);
            stmt.setString(3, player.getName());
            stmt.setInt(4, pos.getBlockX());
            stmt.setInt(5, pos.getBlockY());
            stmt.setInt(6, pos.getBlockZ());
            stmt.setInt(7, item);
            stmt.setInt(8, (int)(System.currentTimeMillis() / 1000L));
            stmt.setString(9, comment);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            this.logger.log(Level.SEVERE, "Failed to log blacklist event to database: " + e.getMessage());
        }
    }

    @Override
    public void logEvent(BlacklistEvent event, String comment) {
        if (event instanceof BlockBreakBlacklistEvent) {
            BlockBreakBlacklistEvent evt = (BlockBreakBlacklistEvent)event;
            this.logEvent("BREAK", evt.getPlayer(), evt.getPosition(), evt.getType(), comment);
        } else if (event instanceof BlockPlaceBlacklistEvent) {
            BlockPlaceBlacklistEvent evt = (BlockPlaceBlacklistEvent)event;
            this.logEvent("PLACE", evt.getPlayer(), evt.getPosition(), evt.getType(), comment);
        } else if (event instanceof BlockInteractBlacklistEvent) {
            BlockInteractBlacklistEvent evt = (BlockInteractBlacklistEvent)event;
            this.logEvent("INTERACT", evt.getPlayer(), evt.getPosition(), evt.getType(), comment);
        } else if (event instanceof DestroyWithBlacklistEvent) {
            DestroyWithBlacklistEvent evt = (DestroyWithBlacklistEvent)event;
            this.logEvent("DESTROY_WITH", evt.getPlayer(), evt.getPosition(), evt.getType(), comment);
        } else if (event instanceof ItemAcquireBlacklistEvent) {
            ItemAcquireBlacklistEvent evt = (ItemAcquireBlacklistEvent)event;
            this.logEvent("ACQUIRE", evt.getPlayer(), evt.getPlayer().getPosition(), evt.getType(), comment);
        } else if (event instanceof ItemDropBlacklistEvent) {
            ItemDropBlacklistEvent evt = (ItemDropBlacklistEvent)event;
            this.logEvent("DROP", evt.getPlayer(), evt.getPlayer().getPosition(), evt.getType(), comment);
        } else if (event instanceof ItemUseBlacklistEvent) {
            ItemUseBlacklistEvent evt = (ItemUseBlacklistEvent)event;
            this.logEvent("USE", evt.getPlayer(), evt.getPlayer().getPosition(), evt.getType(), comment);
        } else {
            this.logEvent("UNKNOWN", event.getPlayer(), event.getPlayer().getPosition(), -1, comment);
        }
    }

    @Override
    public void close() {
        try {
            if (this.conn != null && !this.conn.isClosed()) {
                this.conn.close();
            }
        }
        catch (SQLException sQLException) {
            // empty catch block
        }
    }
}

