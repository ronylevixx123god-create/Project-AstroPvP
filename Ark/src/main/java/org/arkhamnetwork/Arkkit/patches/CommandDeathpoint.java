/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.FPlayers
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.World$Environment
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.player.PlayerTeleportEvent$TeleportCause
 */
package org.arkhamnetwork.Arkkit.patches;

import com.massivecraft.factions.FPlayers;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.arkhamnetwork.Arkkit.utils.WorldGuardUtils;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.List;

final class CommandDeathpoint
        extends AbstractCommand {
    public CommandDeathpoint(String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player pl;
        Player player = pl = sender instanceof Player ? (Player) sender : null;
        if (pl == null) {
            sender.sendMessage("This command can only be used by players.");
            return false;
        }
        if (!pl.hasPermission("arkkit.deathpoint") && !pl.hasPermission("essentials.back")) {
            pl.sendMessage(ChatColor.RED + "You do " + ChatColor.UNDERLINE + "not" + ChatColor.RED + " have permission to use this feature!");
            return true;
        }
        Bukkit.getLogger().info("[Deathpoint] " + pl.getName() + "'s power => " + FPlayers.i.get(pl).getPower());
        if (FPlayers.i.get(pl).getPower() <= -8.0) {
            pl.sendMessage(ChatColor.RED + "You cannot use deathpoint with a power level <= -8");
            return true;
        }
        if (Deathpoint.death_locations.containsKey(pl.getUniqueId())) {
            Location l = Deathpoint.death_locations.get(pl.getUniqueId());
            if (pl.hasMetadata("lastDeathWasSuicide")) {
                pl.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "(!) " + ChatColor.RED + "You cannot return to a deathpoint caused by /suicide.");
                return true;
            }
            if (l.getWorld().getName().equals("world_koth")) {
                pl.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "(!) " + ChatColor.RED + "You cannot return to a deathpoint inside KOTH.");
                return true;
            }
            if (l.getWorld().getEnvironment() == World.Environment.THE_END) {
                pl.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "(!) " + ChatColor.RED + "You cannot return to a deathpoint in The End dimension.");
                return true;
            }
            if (l.getWorld().getName().startsWith("cosmic-station")) {
                pl.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "(!) " + ChatColor.RED + "You cannot return to a deathpoint inside a Cosmic Outpost.");
                return true;
            }
            if (l.getBlock().getType() != Material.AIR && l.getBlock().getType() != Material.WATER && l.getBlock().getType() != Material.STATIONARY_WATER && l.getBlock().getType() != Material.LAVA && l.getBlock().getType() != Material.STATIONARY_LAVA) {
                pl.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "(!) " + ChatColor.RED + "A block is obstructing your deathpoint.");
                return true;
            }
            if (!l.getWorld().equals(pl.getWorld())) {
                pl.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "(!) " + ChatColor.RED + "You cannot use /back to teleport to a location in a different world.");
                pl.sendMessage(ChatColor.GRAY + "Teleport to the world you died in before using /back.");
                return true;
            }
            String regionName = WorldGuardUtils.getRegionName(l);
            if (regionName.equals("infernal_outpost_cap") || regionName.equals("frozen_outpost_cap")) {
                pl.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "(!) " + ChatColor.RED + "You cannot use /back to teleport to a /stronghold cap room.");
                return true;
            }
            if (regionName.endsWith("sell_room")) {
                pl.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "(!) " + ChatColor.RED + "You cannot use /back to teleport to a /stronghold sell room.");
                return true;
            }
            if (pl.teleport(l, PlayerTeleportEvent.TeleportCause.PLUGIN)) {
                Deathpoint.death_locations.remove(pl.getUniqueId());
                pl.sendMessage(ChatColor.GOLD + "You have returned to your last point of death.");
            }
        } else {
            pl.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "(!) " + ChatColor.RED + "You do not have a valid death point to return to.");
        }
        return true;
    }
}

