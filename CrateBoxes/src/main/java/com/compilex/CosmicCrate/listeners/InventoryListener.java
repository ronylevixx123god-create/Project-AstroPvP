/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.inventory.InventoryCloseEvent
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package com.compilex.CosmicCrate.listeners;

import com.compilex.CosmicCrate.CosmicCrate;
import com.compilex.CosmicCrate.CosmicCrateLootSelector;
import com.compilex.CosmicCrate.libs.Version;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class InventoryListener
implements Listener {
    @EventHandler(priority=EventPriority.LOWEST)
    public void onCosmicCrateRewardClaim(InventoryClickEvent e) {
        if (e.getInventory().getTitle().startsWith("Cosmic Crate: ")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            final Player p = (Player)e.getWhoClicked();
            if (e.getClickedInventory() == null || !e.getClickedInventory().equals((Object)p.getOpenInventory().getTopInventory())) {
                return;
            }
            Version v = Version.valueOf(e.getInventory().getTitle().split(" ")[2].toUpperCase());
            if (!p.hasMetadata("ccrate_loot_selector")) {
                return;
            }
            CosmicCrateLootSelector ccls = (CosmicCrateLootSelector)((MetadataValue)p.getMetadata("ccrate_loot_selector").get(0)).value();
            if (v != null && e.getCurrentItem() != null) {
                if (ccls.phase == CosmicCrateLootSelector.CosmicCratePhase.LOOTING) {
                    ccls.conclude();
                    Bukkit.getScheduler().runTaskLater((Plugin)CosmicCrate.get(), new Runnable(){

                        @Override
                        public void run() {
                            p.closeInventory();
                        }
                    }, 10L);
                    return;
                }
                if (e.getCurrentItem().getType() == Material.ENDER_CHEST && e.getRawSlot() != 49) {
                    if (!ccls.hasSlotAnimation(e.getRawSlot())) {
                        ccls.runSlotAnimation(e.getRawSlot());
                    }
                } else if (e.getRawSlot() == 49) {
                    if (ccls.displayRewards.size() > 0) {
                        p.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "You cannot claim this item yet!");
                        return;
                    }
                    if (ccls.phase == CosmicCrateLootSelector.CosmicCratePhase.UNLOCKING_META) {
                        ccls.lootInventory.setItem(49, ccls.metaItem);
                        ccls.phase = CosmicCrateLootSelector.CosmicCratePhase.LOOTING;
                    }
                }
            }
        }
    }

    @EventHandler
    public void onCosmicCrateRedeemInventoryClose(InventoryCloseEvent e) {
        if (e.getInventory().getTitle().startsWith("Cosmic Crate: ") && e.getPlayer().hasMetadata("ccrate_loot_selector")) {
            Player p = (Player)e.getPlayer();
            CosmicCrateLootSelector ccls = (CosmicCrateLootSelector)((MetadataValue)p.getMetadata("ccrate_loot_selector").get(0)).value();
            ccls.conclude();
        }
    }
}

