/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.earth2me.essentials.User
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.AsyncPlayerChatEvent
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 *  org.bukkit.event.player.PlayerDropItemEvent
 *  org.bukkit.event.player.PlayerItemHeldEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.earth2me.essentials.User;
import java.math.BigDecimal;
import java.util.ArrayList;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.SmarterEssentials;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

final class SellSanity
implements Listener {
    final int loreSizeThreshold = 1;

    SellSanity() {
    }

    @EventHandler(priority=EventPriority.LOW)
    public void onPlayerCommandPreProcess(PlayerCommandPreprocessEvent e) {
        double toPay;
        String cmd = e.getMessage().toLowerCase();
        if ((cmd.startsWith("/pay") || cmd.startsWith("/epay")) && cmd.contains(" ") && cmd.split(" ").length >= 3 && (toPay = Double.parseDouble(cmd.split(" ")[2])) <= 0.0) {
            e.setCancelled(true);
            e.setMessage("/null");
            e.getPlayer().sendMessage((Object)ChatColor.RED + "You cannot /pay negative $ amounts!");
            return;
        }
        if (cmd.startsWith("/sell") || cmd.startsWith("/esell")) {
            Player p = e.getPlayer();
            if (p.hasMetadata("sellConfirmed")) {
                p.removeMetadata("sellConfirmed", (Plugin)Arkkit.get());
                return;
            }
            if (p.hasMetadata("sellConfirmListener")) {
                e.setCancelled(true);
                e.setMessage("/null");
                p.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "You already have a pending /sell action.");
                p.sendMessage((Object)ChatColor.GRAY + "Type " + ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "YES" + (Object)ChatColor.GRAY + " to confirm or " + ChatColor.RED.toString() + ChatColor.BOLD.toString() + "NO" + (Object)ChatColor.GRAY + " to cancel.");
                return;
            }
            User u = SmarterEssentials.ess3.getUser(p);
            String[] args = cmd.substring(cmd.indexOf(" ") + 1).split(" ");
            if (args.length == 0 || args[0].equals("/sell")) {
                return;
            }
            if (args[0].equals("hand") && p.getItemInHand().getType() == Material.AIR) {
                return;
            }
            ArrayList<ItemStack> uniqueItems = new ArrayList<ItemStack>();
            try {
                for (ItemStack i : SmarterEssentials.ess3.getItemDb().getMatching(u, args)) {
                    BigDecimal worth;
                    if (!i.hasItemMeta() || !i.getItemMeta().hasLore()) continue;
                    int n = i.getItemMeta().getLore().size();
                    this.getClass();
                    if (n <= 1 || (worth = SmarterEssentials.ess3.getWorth().getPrice(i)) == null) continue;
                    uniqueItems.add(i);
                }
            }
            catch (Exception e1) {
                e1.printStackTrace();
            }
            if (uniqueItems.size() > 0) {
                String sellCmd = e.getMessage().replace("/", "");
                e.setCancelled(true);
                e.setMessage("/null");
                p.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.YELLOW + "Are you sure you wish to /sell the following items?");
                for (ItemStack i : uniqueItems) {
                    p.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "* " + (i.getItemMeta().hasDisplayName() ? i.getItemMeta().getDisplayName() : i.getType().name()));
                }
                p.sendMessage((Object)ChatColor.GRAY + "Type " + ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "YES" + (Object)ChatColor.GRAY + " to confirm or " + ChatColor.RED.toString() + ChatColor.BOLD.toString() + "NO" + (Object)ChatColor.GRAY + " to cancel.");
                p.setMetadata("sellConfirmListener", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)sellCmd));
            }
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        if (e.getPlayer().hasMetadata("sellConfirmListener")) {
            e.setCancelled(true);
            if (e.getMessage().equalsIgnoreCase("yes")) {
                String sellCmd = ((MetadataValue)e.getPlayer().getMetadata("sellConfirmListener").get(0)).asString();
                e.getPlayer().setMetadata("sellConfirmed", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)sellCmd));
                e.getPlayer().performCommand(sellCmd);
                e.getPlayer().sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.GREEN + "You have confirmed your /sell action.");
            } else {
                e.getPlayer().sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "You have cancelled your /sell action.");
            }
            e.getPlayer().removeMetadata("sellConfirmListener", (Plugin)Arkkit.get());
        }
    }

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent e) {
        if (e.getPlayer().hasMetadata("sellConfirmListener")) {
            e.getPlayer().removeMetadata("sellConfirmed", (Plugin)Arkkit.get());
            e.getPlayer().removeMetadata("sellConfirmListener", (Plugin)Arkkit.get());
            e.getPlayer().sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "You have cancelled your /sell action.");
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e) {
        if (e.getPlayer().hasMetadata("sellConfirmListener")) {
            e.getPlayer().removeMetadata("sellConfirmed", (Plugin)Arkkit.get());
            e.getPlayer().removeMetadata("sellConfirmListener", (Plugin)Arkkit.get());
            e.getPlayer().sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "You have cancelled your /sell action.");
        }
    }
}

