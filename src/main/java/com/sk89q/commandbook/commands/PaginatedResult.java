/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.minecraft.util.commands.CommandException
 */
package com.sk89q.commandbook.commands;

import com.sk89q.minecraft.util.commands.CommandException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public abstract class PaginatedResult<T> {
    private final String header;
    protected static final int PER_PAGE = 6;

    public PaginatedResult(String header) {
        this.header = header;
    }

    public void display(CommandSender sender, Collection<? extends T> results, int page) throws CommandException {
        this.display(sender, (List<? extends T>)new ArrayList<T>(results), page);
    }

    public void display(CommandSender sender, List<? extends T> results, int page) throws CommandException {
        if (results.size() == 0) {
            throw new CommandException("No results match!");
        }
        int maxPages = results.size() / 6;
        if (--page < 0 || page > maxPages) {
            throw new CommandException("Unknown page selected! " + (maxPages + 1) + " total pages.");
        }
        sender.sendMessage((Object)((Object)ChatColor.YELLOW) + this.header + " (page " + (page + 1) + "/" + (maxPages + 1) + ")");
        for (int i = 6 * page; i < 6 * page + 6 && i < results.size(); ++i) {
            sender.sendMessage(ChatColor.YELLOW.toString() + this.format(results.get(i)));
        }
    }

    public abstract String format(T var1);
}

