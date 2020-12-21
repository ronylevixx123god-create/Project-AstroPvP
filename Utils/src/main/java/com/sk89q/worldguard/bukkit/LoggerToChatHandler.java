/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.worldguard.bukkit;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class LoggerToChatHandler
extends Handler {
    private CommandSender player;

    public LoggerToChatHandler(CommandSender player) {
        this.player = player;
    }

    @Override
    public void close() {
    }

    @Override
    public void flush() {
    }

    @Override
    public void publish(LogRecord record) {
        this.player.sendMessage((Object)((Object)ChatColor.GRAY) + record.getLevel().getName() + ": " + (Object)((Object)ChatColor.WHITE) + record.getMessage());
    }
}

