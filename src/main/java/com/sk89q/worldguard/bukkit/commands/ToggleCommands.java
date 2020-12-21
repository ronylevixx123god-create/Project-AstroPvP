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
import com.sk89q.worldguard.bukkit.BukkitUtil;
import com.sk89q.worldguard.bukkit.ConfigurationManager;
import com.sk89q.worldguard.bukkit.WorldConfiguration;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class ToggleCommands {
    private final WorldGuardPlugin plugin;

    public ToggleCommands(WorldGuardPlugin plugin) {
        this.plugin = plugin;
    }

    @Command(aliases={"stopfire"}, usage="[<world>]", desc="Disables all fire spread temporarily", max=1)
    @CommandPermissions(value={"worldguard.fire-toggle.stop"})
    public void stopFire(CommandContext args, CommandSender sender) throws CommandException {
        World world = args.argsLength() == 0 ? this.plugin.checkPlayer(sender).getWorld() : this.plugin.matchWorld(sender, args.getString(0));
        WorldConfiguration wcfg = this.plugin.getGlobalStateManager().get(world);
        if (!wcfg.fireSpreadDisableToggle) {
            this.plugin.getServer().broadcastMessage((Object)((Object)ChatColor.YELLOW) + "Fire spread has been globally disabled for '" + world.getName() + "' by " + this.plugin.toName(sender) + ".");
        } else {
            sender.sendMessage((Object)((Object)ChatColor.YELLOW) + "Fire spread was already globally disabled.");
        }
        wcfg.fireSpreadDisableToggle = true;
    }

    @Command(aliases={"allowfire"}, usage="[<world>]", desc="Allows all fire spread temporarily", max=1)
    @CommandPermissions(value={"worldguard.fire-toggle.stop"})
    public void allowFire(CommandContext args, CommandSender sender) throws CommandException {
        World world = args.argsLength() == 0 ? this.plugin.checkPlayer(sender).getWorld() : this.plugin.matchWorld(sender, args.getString(0));
        WorldConfiguration wcfg = this.plugin.getGlobalStateManager().get(world);
        if (wcfg.fireSpreadDisableToggle) {
            this.plugin.getServer().broadcastMessage((Object)((Object)ChatColor.YELLOW) + "Fire spread has been globally for '" + world.getName() + "' re-enabled by " + this.plugin.toName(sender) + ".");
        } else {
            sender.sendMessage((Object)((Object)ChatColor.YELLOW) + "Fire spread was already globally enabled.");
        }
        wcfg.fireSpreadDisableToggle = false;
    }

    @Command(aliases={"halt-activity", "stoplag", "haltactivity"}, desc="Attempts to cease as much activity in order to stop lag", flags="c", max=0)
    @CommandPermissions(value={"worldguard.halt-activity"})
    public void stopLag(CommandContext args, CommandSender sender) throws CommandException {
        ConfigurationManager configManager = this.plugin.getGlobalStateManager();
        boolean bl = configManager.activityHaltToggle = !args.hasFlag('c');
        if (configManager.activityHaltToggle) {
            if (!(sender instanceof Player)) {
                sender.sendMessage((Object)((Object)ChatColor.YELLOW) + "ALL intensive server activity halted.");
            }
            this.plugin.getServer().broadcastMessage((Object)((Object)ChatColor.YELLOW) + "ALL intensive server activity halted by " + this.plugin.toName(sender) + ".");
            for (World world : this.plugin.getServer().getWorlds()) {
                int removed = 0;
                for (Entity entity : world.getEntities()) {
                    if (!BukkitUtil.isIntensiveEntity(entity)) continue;
                    entity.remove();
                    ++removed;
                }
                if (removed <= 10) continue;
                sender.sendMessage("" + removed + " entities (>10) auto-removed from " + world.getName());
            }
        } else {
            if (!(sender instanceof Player)) {
                sender.sendMessage((Object)((Object)ChatColor.YELLOW) + "ALL intensive server activity no longer halted.");
            }
            this.plugin.getServer().broadcastMessage((Object)((Object)ChatColor.YELLOW) + "ALL intensive server activity is now allowed.");
        }
    }
}

