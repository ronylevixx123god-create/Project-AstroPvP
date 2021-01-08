/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Sound
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.ItemStack
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

final class CommandItemsListener
        implements Listener {
    CommandItemsListener() {
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.hasItem() && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && CommandItems.isCommandItem(e.getPlayer().getItemInHand())) {
            e.setCancelled(true);
            e.setUseItemInHand(Event.Result.DENY);
            e.setUseInteractedBlock(Event.Result.DENY);
            String cmd = CommandItems.getCommandItemCommand(e.getItem());
            String msg = CommandItems.getCommandItemMessage(e.getItem());
            if (cmd != null) {
                long itemID;
                Player p = e.getPlayer();
                cmd = cmd.replace("{playerName}", p.getName()).replace("{playerUUID}", p.getUniqueId().toString());
                ItemStack i = p.getItemInHand();
                if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning() && (itemID = ItemID.getItemID(e.getItem())) != -1L) {
                    ItemID.blacklistIID(itemID, "cmdItemRedeem/" + p.getName() + "/" + cmd);
                }
                if (i.getAmount() > 1) {
                    i.setAmount(i.getAmount() - 1);
                    p.setItemInHand(i);
                    p.updateInventory();
                } else {
                    p.setItemInHand(null);
                }
                Bukkit.getLogger().info("[CommandItems] Executing command: " + cmd + " for player: " + p.getName() + " (" + p.getUniqueId() + ")");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
                if (msg != null) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                    if (cmd.contains("legacy")) {
                        int rankID = 0;
                        try {
                            String[] s = cmd.split(" ");
                            rankID = Integer.parseInt(s[s.length - 1]);
                        } catch (Exception err) {
                            err.printStackTrace();
                        }
                        String rankName = null;
                        if (rankID == 1) {
                            rankName = ChatColor.GREEN + "Pilot (Tier 1)";
                        }
                        if (rankID == 2) {
                            rankName = ChatColor.AQUA + "Specialist (Tier 2)";
                        }
                        if (rankID == 3) {
                            rankName = ChatColor.BLUE + "Engineer (Tier 3)";
                        }
                        if (rankID == 4) {
                            rankName = ChatColor.GOLD + "Commander (Tier 4)";
                        }
                        if (rankID == 5) {
                            rankName = ChatColor.YELLOW + "" + ChatColor.BOLD + "Cosmonaut (Tier 5)";
                        }
                    }
                }
                p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1.0f, 1.0f);
            } else {
                Bukkit.getLogger().info("[CommandItems] Null command param found on commandItem: " + e.getItem() + " player: " + e.getPlayer().getName());
            }
        }
    }
}

