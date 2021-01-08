/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.FPlayers
 *  com.massivecraft.factions.iface.RelationParticipator
 *  com.massivecraft.factions.struct.Relation
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 */
package org.arkhamnetwork.Arkkit.patches.commandcooldown;

import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.struct.Relation;
import org.arkhamnetwork.Arkkit.patches.WildernessCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandCooldownListener
        implements Listener {
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerCommandPreProcessLOWEST(PlayerCommandPreprocessEvent e) {
        String originalCMD = e.getMessage().replace("/", "").toLowerCase();
        String cmd = e.getMessage().replace("/", "").toLowerCase();
        if (cmd.startsWith("f invite") || cmd.startsWith("party invite") || cmd.startsWith("f disband") || cmd.startsWith("f focus") || cmd.startsWith("f target")) {
            cmd = cmd.split(" ")[1];
        } else if (cmd.contains(" ")) {
            cmd = cmd.substring(0, cmd.indexOf(" "));
        }
        if (e.getMessage().equalsIgnoreCase("/heal/")) {
            return;
        }
        if (originalCMD.startsWith("feed ") && e.getPlayer().hasPermission("essentials.feed.others")) {
            String playerTarget = originalCMD.split(" ")[1];
            Player p = Bukkit.getPlayer(playerTarget);
            Player pl = e.getPlayer();
            if (p != null && p.isOnline() && !pl.isOp() && !FPlayers.i.get(p).getRelationTo(FPlayers.i.get(pl)).isAtLeast(Relation.ALLY)) {
                e.getPlayer().sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "(!) " + ChatColor.RED + "You can only /feed players in ALLY+ factions.");
                e.setCancelled(true);
                e.setMessage("/null");
                return;
            }
            if (CommandCooldowns.get().getCooldownManager().hasCooldown(pl, "feed <player>")) {
                pl.sendMessage(ChatColor.RED + "You " + ChatColor.UNDERLINE + "cannot" + ChatColor.RED + " use /feed <player> for another " + this.getPrettyCooldown(CommandCooldowns.get().getCooldownManager().getCooldownMillisRemaining(pl, "feed <player>")) + "!");
                e.setCancelled(true);
                return;
            }
            CommandCooldowns.get().getCooldownManager().addCooldown(e.getPlayer(), "feed <player>", 300000L);
            return;
        }
        if (CommandCooldowns.get().getCooldownManager().isRegistered(cmd)) {
            Player pl = e.getPlayer();
            if (pl.hasPermission("arkhamcooldowns.bypass." + cmd)) {
                return;
            }
            if (CommandCooldowns.get().getCooldownManager().hasCooldown(pl, cmd)) {
                pl.sendMessage(ChatColor.RED + "You " + ChatColor.UNDERLINE + "cannot" + ChatColor.RED + " use /" + originalCMD + " for another " + this.getPrettyCooldown(CommandCooldowns.get().getCooldownManager().getCooldownMillisRemaining(pl, cmd)) + "!");
                e.setCancelled(true);
                return;
            }
            if ((cmd.equals("invite") || cmd.equals("disband") || cmd.equals("focus") || cmd.equals("target")) && CommandCooldowns.get().getCooldownManager().isRegistered(cmd)) {
                CommandCooldowns.get().getCooldownManager().addCooldown(e.getPlayer(), cmd);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerCommandPreProcessHIGHEST(PlayerCommandPreprocessEvent e) {
        String cmd = e.getMessage().replace("/", "").toLowerCase();
        if (cmd.startsWith("f invite") || cmd.startsWith("party invite") || cmd.startsWith("f disband") || cmd.startsWith("f focus") || cmd.startsWith("f target")) {
            cmd = cmd.split(" ")[1];
        } else if (cmd.contains(" ")) {
            cmd = cmd.substring(0, cmd.indexOf(" "));
        }
        if (CommandCooldowns.get().getCooldownManager().isRegistered(cmd)) {
            if (cmd.equals("wilderness") && !WildernessCommand.canUseWilderness(e.getPlayer())) {
                return;
            }
            CommandCooldowns.get().getCooldownManager().addCooldown(e.getPlayer(), cmd);
        }
    }

    private String getPrettyCooldown(long milliseconds) {
        int seconds = (int) (milliseconds / 1000L) % 60;
        int minutes = (int) (milliseconds / 60000L % 60L);
        int hours = (int) (milliseconds / 3600000L % 24L);
        if (hours > 0) {
            return hours + "h, " + minutes + "m, " + seconds + "s";
        }
        if (minutes > 0) {
            return minutes + "m, " + seconds + "s";
        }
        if (seconds > 0) {
            return seconds + "s";
        }
        return milliseconds + "ms";
    }
}

