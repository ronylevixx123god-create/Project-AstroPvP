/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.itembuilder.CommandItemBuilder
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  lilypad.client.connect.api.event.EventListener
 *  lilypad.client.connect.api.event.MessageEvent
 *  lilypad.client.connect.api.request.Request
 *  lilypad.client.connect.api.request.RequestException
 *  lilypad.client.connect.api.request.impl.MessageRequest
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 *  org.bukkit.inventory.ItemStack
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.itembuilder.CommandItemBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.ItemStack;

public class KitLegacyListener implements Listener {
    @EventHandler(priority=EventPriority.LOW)
    public void onPlayerCommandPreProcess(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().toLowerCase().equalsIgnoreCase("/kit legacy")) {
            e.setMessage("/null");
            Player p = e.getPlayer();
            if (!p.hasPermission("cosmicpvp.heroic")) {
                p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.RED + "/kit legacy is only available to Top Rank+ users.");
                p.sendMessage((Object)ChatColor.GRAY + "Please also note /kit legacy is limited to 1-use per account, not per rank, and must be executed on a planet where the user has Top Rank+");
                return;
            }
            if (p.getInventory().firstEmpty() == -1) {
                p.sendMessage(CC.RedB + "(!) " + CC.Red + "You do not have enough space in your inventory!");
                return;
            }
            if (!new File("kit_legacy").exists() && !p.isOp()) {
                p.sendMessage(CC.RedB + "(!) " + CC.Red + "/kit legacy is not yet available!");
                p.sendMessage(CC.Gray + "You will be able to claim /kit legacy on " + CC.Red + "Sunday, March 3rd, from 11AM - 11PM CST!");
                p.sendMessage(CC.Gray + "This kit can only be redeemed " + (Object)ChatColor.UNDERLINE + "ONCE" + CC.Gray + " per account. Contains either a Tier 1, 2, or 3 rank item for the upcoming CosmicPvP: Legacy Galaxy.");
                return;
            }
            if (KitLegacy.redeemedUUIDs.contains(p.getUniqueId().toString())) {
                p.sendMessage(CC.RedB + "(!) " + CC.Red + "You have already redeemed your /kit legacy on this account!");
                return;
            }
            CommandItemBuilder cmdItem = new CommandItemBuilder();
            String rankName = "Pilot";
            int rankTier = 1;
            if (Math.random() < 0.025) {
                rankName = "Engineer";
                rankTier = 3;
            } else if (Math.random() < 0.2) {
                rankName = "Specialist";
                rankTier = 2;
            } else {
                rankName = "Pilot";
                rankTier = 1;
            }
            cmdItem.setCommand("queue legacyplanet ip:0.0.0.0 package:Kit Legacy id:kitlegacy_{playerName} username:{playerName} uuid:{playerUUID} rankquest giveitems {playerName} " + rankTier);
            cmdItem.setMessage("&e&l(!) &eYou have successfully redeemed a &b&lCosmicPvP: &c&lLegacy &erank! (&7" + rankName + "&e), login to legacy when it launches and run /confirm!");
            cmdItem.setItemName("&6&lLEGACY RANK \"&f" + rankName + "&6&l\"");
            cmdItem.setItemLore(new String[]{"&7Right-click this item to unlock", "&7the " + rankName + " Rank (Tier " + rankTier + ") on the", "&7unreleased Legacy Alien Planet.", "", "&b&lCosmicPvP: &c&lLegacy &7launches on", "&7March 8th, 3:00pm CST"});
            KitLegacy.redeemedUUIDs.add(p.getUniqueId().toString());
            p.sendMessage(CC.GoldB + "(!) " + CC.Gold + "You have redeemed your /kit legacy!");
            p.sendMessage(CC.Gray + "Whoever uses the LEGACY RANK item you have received will automatically have that rank on CosmicPvP: Legacy's release!");
            p.getInventory().addItem(new ItemStack[]{cmdItem.build()});
        }
    }
}

