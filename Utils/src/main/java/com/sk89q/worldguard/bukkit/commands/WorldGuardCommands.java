/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.minecraft.util.commands.Command
 *  com.sk89q.minecraft.util.commands.CommandContext
 *  com.sk89q.minecraft.util.commands.CommandException
 *  com.sk89q.minecraft.util.commands.CommandPermissions
 */
package com.sk89q.worldguard.bukkit.commands;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import com.sk89q.worldguard.bukkit.LoggerToChatHandler;
import com.sk89q.worldguard.bukkit.ReportWriter;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.util.PastebinPoster;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WorldGuardCommands {
    private final WorldGuardPlugin plugin;

    public WorldGuardCommands(WorldGuardPlugin plugin) {
        this.plugin = plugin;
    }

    @Command(aliases={"version"}, desc="Get the WorldGuard version", max=0)
    public void version(CommandContext args, CommandSender sender) throws CommandException {
        sender.sendMessage((Object)((Object)ChatColor.YELLOW) + "WorldGuard " + this.plugin.getDescription().getVersion());
        sender.sendMessage((Object)((Object)ChatColor.YELLOW) + "http://www.sk89q.com");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Command(aliases={"reload"}, desc="Reload WorldGuard configuration", max=0)
    @CommandPermissions(value={"worldguard.reload"})
    public void reload(CommandContext args, CommandSender sender) throws CommandException {
        LoggerToChatHandler handler = null;
        Logger minecraftLogger = null;
        if (sender instanceof Player) {
            handler = new LoggerToChatHandler(sender);
            handler.setLevel(Level.ALL);
            minecraftLogger = Logger.getLogger("Minecraft");
            minecraftLogger.addHandler(handler);
        }
        try {
            this.plugin.getGlobalStateManager().unload();
            this.plugin.getGlobalRegionManager().unload();
            this.plugin.getGlobalStateManager().load();
            this.plugin.getGlobalRegionManager().preload();
            sender.sendMessage("WorldGuard configuration reloaded.");
        }
        catch (Throwable t) {
            sender.sendMessage("Error while reloading: " + t.getMessage());
        }
        finally {
            if (minecraftLogger != null) {
                minecraftLogger.removeHandler(handler);
            }
        }
    }

    @Command(aliases={"report"}, desc="Writes a report on WorldGuard", flags="p", max=0)
    @CommandPermissions(value={"worldguard.report"})
    public void report(CommandContext args, final CommandSender sender) throws CommandException {
        File dest = new File(this.plugin.getDataFolder(), "report.txt");
        ReportWriter report = new ReportWriter(this.plugin);
        try {
            report.write(dest);
            sender.sendMessage((Object)((Object)ChatColor.YELLOW) + "WorldGuard report written to " + dest.getAbsolutePath());
        }
        catch (IOException e) {
            throw new CommandException("Failed to write report: " + e.getMessage());
        }
        if (args.hasFlag('p')) {
            this.plugin.checkPermission(sender, "worldguard.report.pastebin");
            sender.sendMessage((Object)((Object)ChatColor.YELLOW) + "Now uploading to Pastebin...");
            PastebinPoster.paste(report.toString(), new PastebinPoster.PasteCallback(){

                @Override
                public void handleSuccess(String url) {
                    sender.sendMessage((Object)((Object)ChatColor.YELLOW) + "WorldGuard report (1 hour): " + url);
                }

                @Override
                public void handleError(String err) {
                    sender.sendMessage((Object)((Object)ChatColor.YELLOW) + "WorldGuard report pastebin error: " + err);
                }
            });
        }
    }

    @Command(aliases={"flushstates", "clearstates"}, usage="[player]", desc="Flush the state manager", max=1)
    @CommandPermissions(value={"worldguard.flushstates"})
    public void flushStates(CommandContext args, CommandSender sender) throws CommandException {
        if (args.argsLength() == 0) {
            this.plugin.getFlagStateManager().forgetAll();
            sender.sendMessage("Cleared all states.");
        } else {
            Player player = this.plugin.getServer().getPlayer(args.getString(0));
            if (player != null) {
                this.plugin.getFlagStateManager().forget(player);
                sender.sendMessage("Cleared states for player \"" + player.getName() + "\".");
            }
        }
    }
}

