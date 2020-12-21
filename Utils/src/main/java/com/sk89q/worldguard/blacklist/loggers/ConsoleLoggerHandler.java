/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.worldedit.blocks.ItemType
 */
package com.sk89q.worldguard.blacklist.loggers;

import com.sk89q.worldedit.blocks.ItemType;
import com.sk89q.worldguard.blacklist.events.BlacklistEvent;
import com.sk89q.worldguard.blacklist.events.BlockBreakBlacklistEvent;
import com.sk89q.worldguard.blacklist.events.BlockInteractBlacklistEvent;
import com.sk89q.worldguard.blacklist.events.BlockPlaceBlacklistEvent;
import com.sk89q.worldguard.blacklist.events.DestroyWithBlacklistEvent;
import com.sk89q.worldguard.blacklist.events.ItemAcquireBlacklistEvent;
import com.sk89q.worldguard.blacklist.events.ItemDropBlacklistEvent;
import com.sk89q.worldguard.blacklist.events.ItemUseBlacklistEvent;
import com.sk89q.worldguard.blacklist.loggers.BlacklistLoggerHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsoleLoggerHandler
implements BlacklistLoggerHandler {
    private String worldName;
    private final Logger logger;

    public ConsoleLoggerHandler(String worldName, Logger logger) {
        this.worldName = worldName;
        this.logger = logger;
    }

    @Override
    public void logEvent(BlacklistEvent event, String comment) {
        if (event instanceof BlockBreakBlacklistEvent) {
            BlockBreakBlacklistEvent evt = (BlockBreakBlacklistEvent)event;
            this.logger.log(Level.INFO, "[" + this.worldName + "] " + event.getPlayer().getName() + " tried to break " + ConsoleLoggerHandler.getFriendlyItemName(evt.getType()) + (comment != null ? " (" + comment + ")" : ""));
        } else if (event instanceof BlockPlaceBlacklistEvent) {
            BlockPlaceBlacklistEvent evt = (BlockPlaceBlacklistEvent)event;
            this.logger.log(Level.INFO, "[" + this.worldName + "] " + event.getPlayer().getName() + " tried to place " + ConsoleLoggerHandler.getFriendlyItemName(evt.getType()) + (comment != null ? " (" + comment + ")" : ""));
        } else if (event instanceof BlockInteractBlacklistEvent) {
            BlockInteractBlacklistEvent evt = (BlockInteractBlacklistEvent)event;
            this.logger.log(Level.INFO, "[" + this.worldName + "] " + event.getPlayer().getName() + " tried to interact with " + ConsoleLoggerHandler.getFriendlyItemName(evt.getType()) + (comment != null ? " (" + comment + ")" : ""));
        } else if (event instanceof DestroyWithBlacklistEvent) {
            DestroyWithBlacklistEvent evt = (DestroyWithBlacklistEvent)event;
            this.logger.log(Level.INFO, "[" + this.worldName + "] " + event.getPlayer().getName() + " tried to destroy with " + ConsoleLoggerHandler.getFriendlyItemName(evt.getType()) + (comment != null ? " (" + comment + ")" : ""));
        } else if (event instanceof ItemAcquireBlacklistEvent) {
            ItemAcquireBlacklistEvent evt = (ItemAcquireBlacklistEvent)event;
            this.logger.log(Level.INFO, "[" + this.worldName + "] " + event.getPlayer().getName() + " tried to acquire " + ConsoleLoggerHandler.getFriendlyItemName(evt.getType()) + (comment != null ? " (" + comment + ")" : ""));
        } else if (event instanceof ItemDropBlacklistEvent) {
            ItemDropBlacklistEvent evt = (ItemDropBlacklistEvent)event;
            this.logger.log(Level.INFO, "[" + this.worldName + "] " + event.getPlayer().getName() + " tried to drop " + ConsoleLoggerHandler.getFriendlyItemName(evt.getType()) + (comment != null ? " (" + comment + ")" : ""));
        } else if (event instanceof ItemUseBlacklistEvent) {
            ItemUseBlacklistEvent evt = (ItemUseBlacklistEvent)event;
            this.logger.log(Level.INFO, "[" + this.worldName + "] " + event.getPlayer().getName() + " tried to use " + ConsoleLoggerHandler.getFriendlyItemName(evt.getType()) + (comment != null ? " (" + comment + ")" : ""));
        } else {
            this.logger.log(Level.INFO, "[" + this.worldName + "] " + event.getPlayer().getName() + " caught unknown event: " + event.getClass().getCanonicalName());
        }
    }

    private static String getFriendlyItemName(int id) {
        ItemType type = ItemType.fromID((int)id);
        if (type != null) {
            return type.getName() + " (#" + id + ")";
        }
        return "#" + id;
    }

    @Override
    public void close() {
    }
}

