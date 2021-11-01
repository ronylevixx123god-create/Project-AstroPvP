/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.me.astropvp.cratekeys.inventory.InventoryClickEvent
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 */
package com.conaxgames.factions.utils.listeners;

import java.util.HashSet;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class VIPCommandBlocker
implements Listener {
    HashSet<String> blacklisted_commands = new HashSet();
    HashSet<String> vips = new HashSet();

    public VIPCommandBlocker() {
        this.vips.add("tbnrfrags");
        this.vips.add("woofless");
        this.vips.add("vikkstar123");
        this.vips.add("lachlan");
        this.blacklisted_commands.add("/ecreatehome");
        this.blacklisted_commands.add("/createhome");
        for (String vip : this.vips) {
            this.blacklisted_commands.add("/msg " + vip);
            this.blacklisted_commands.add("/w " + vip);
            this.blacklisted_commands.add("/m " + vip);
            this.blacklisted_commands.add("/t " + vip);
            this.blacklisted_commands.add("/pm " + vip);
            this.blacklisted_commands.add("/emsg " + vip);
            this.blacklisted_commands.add("/epm " + vip);
            this.blacklisted_commands.add("/tell " + vip);
            this.blacklisted_commands.add("/etell " + vip);
            this.blacklisted_commands.add("/whisper " + vip);
            this.blacklisted_commands.add("/ewhisper " + vip);
            this.blacklisted_commands.add("/marry " + vip);
            this.blacklisted_commands.add("/epay " + vip);
            this.blacklisted_commands.add("/pay " + vip);
            this.blacklisted_commands.add("/party invite " + vip);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=false)
    public void onPlayerCommandPreProcess(PlayerCommandPreprocessEvent e) {
        Player pl = e.getPlayer();
        if (pl.isOp()) {
            return;
        }
        for (String s : this.blacklisted_commands) {
            if (!e.getMessage().toLowerCase().startsWith(s.toLowerCase())) continue;
            e.setCancelled(true);
            e.setMessage("null");
            pl.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!)" + (Object)ChatColor.RED + " You " + (Object)ChatColor.UNDERLINE + "cannot" + (Object)ChatColor.RED + " use that command on " + s + "!");
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=false)
    public void onPlayerFlyCommand(PlayerCommandPreprocessEvent e) {
        if (e.getPlayer().isOp()) {
            return;
        }
        if ((e.getMessage().toLowerCase().equals("/fly") || e.getMessage().toLowerCase().equals("/efly") || e.getMessage().toLowerCase().equals("/essentials:fly") || e.getMessage().toLowerCase().equals("/essentials:efly")) && !e.getPlayer().isOnGround()) {
            e.getPlayer().sendMessage((Object)ChatColor.RED + "You cannot use /fly if you are not on a solid block.");
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void commandAliases(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().toLowerCase().equalsIgnoreCase("/effects") || e.getMessage().toLowerCase().equalsIgnoreCase("/trail") || e.getMessage().toLowerCase().equalsIgnoreCase("/trails") || e.getMessage().toLowerCase().equalsIgnoreCase("/particle") || e.getMessage().toLowerCase().equalsIgnoreCase("/powers")) {
            e.setMessage("/upack menu");
            return;
        }
        if (e.getMessage().toLowerCase().equalsIgnoreCase("/effect") && !e.getPlayer().isOp()) {
            e.setMessage("/upack menu");
            return;
        }
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onInventoryClick(InventoryClickEvent e) {
        Player pl = (Player)e.getWhoClicked();
        if (ChatColor.stripColor((String)pl.getOpenInventory().getTopInventory().getTitle()).equalsIgnoreCase("Cosmic Effects")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
        }
    }
}

