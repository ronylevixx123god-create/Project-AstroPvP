/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.google.common.collect.Lists
 *  com.google.common.collect.MapMaker
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.inventory.InventoryCloseEvent
 *  org.bukkit.event.inventory.InventoryOpenEvent
 *  org.bukkit.event.player.PlayerPickupItemEvent
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches.trading;

import com.cosmicpvp.cosmicutils.utils.CC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.MapMaker;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class NewTradeListener
implements Listener {
    private Map<UUID, Long> expiringClickMap = new MapMaker().expireAfterWrite(1L, TimeUnit.SECONDS).makeMap();

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e) {
        if (e.getPlayer() instanceof Player && e.getPlayer().hasMetadata("compilexTradePartnerUUID") && !e.getInventory().getTitle().contains("  ")) {
            e.setCancelled(true);
            return;
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onInventoryClick_HIGH(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player && e.getWhoClicked().hasMetadata("compilexTradePartnerUUID")) {
            if (!e.getInventory().getTitle().contains("  ")) {
                e.getWhoClicked().closeInventory();
                return;
            }
            Player p1 = (Player)e.getWhoClicked();
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            if (e.getClickedInventory() == null || e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
                return;
            }
            if (e.getRawSlot() == 0) {
                Long expiration = this.expiringClickMap.get(p1.getUniqueId());
                if (expiration != null && expiration > System.currentTimeMillis()) {
                    p1.sendMessage(CC.Red + "You cannot click the accept pane that quickly!");
                    Bukkit.getLogger().info("Rate limiting " + p1.getName() + " from clicking accept, cooldown expiring: " + (expiration - System.currentTimeMillis()));
                    return;
                }
                this.expiringClickMap.put(p1.getUniqueId(), System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(1L));
                if (e.getInventory().getItem(0).getDurability() == NewTrade.gray_button.getDurability()) {
                    e.getInventory().setItem(0, NewTrade.green_button);
                } else {
                    e.getInventory().setItem(0, NewTrade.gray_button);
                }
                p1.updateInventory();
                return;
            }
            if (e.getClickedInventory().equals((Object)p1.getOpenInventory().getTopInventory())) {
                if (!NewTrade.getValidSlots().contains(e.getRawSlot())) {
                    return;
                }
                ItemStack i = e.getCurrentItem();
                e.setCurrentItem(null);
                p1.getInventory().setItem(p1.getInventory().firstEmpty(), i);
                Bukkit.getLogger().info("[(Arkkit) NewTrade] " + p1.getName() + " removing item: " + (Object)i + " in trade: " + e.getClickedInventory().getTitle());
            } else if (e.getClickedInventory().equals((Object)p1.getOpenInventory().getBottomInventory())) {
                int tradeSlot = NewTradeListener.getNextOpenTradeSlot(p1.getOpenInventory().getTopInventory());
                if (tradeSlot == -1) {
                    return;
                }
                ItemStack i = e.getCurrentItem().clone();
                e.setCurrentItem(null);
                p1.getOpenInventory().getTopInventory().setItem(tradeSlot, i);
                Bukkit.getLogger().info("[(Arkkit) NewTrade] " + p1.getName() + " adding item: " + (Object)i + " to trade: " + e.getClickedInventory().getTitle());
            }
            e.getInventory().setItem(0, NewTrade.gray_button);
            Player p2 = Bukkit.getPlayer((UUID)((UUID)((MetadataValue)p1.getMetadata("compilexTradePartnerUUID").get(0)).value()));
            p2.getOpenInventory().getTopInventory().setItem(0, NewTrade.gray_button);
        }
    }

    public static int getNextOpenTradeSlot(Inventory tradeInventory) {
        int lowest_open_slot = 999;
        for (int slot : NewTrade.getValidSlots()) {
            if (tradeInventory.getSize() <= slot || tradeInventory.getItem(slot) != null || slot >= lowest_open_slot) continue;
            lowest_open_slot = slot;
        }
        return lowest_open_slot == 999 ? -1 : lowest_open_slot;
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=false)
    public void onInventoryClick_MONITOR(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player && e.getWhoClicked().hasMetadata("compilexTradePartnerUUID")) {
            Long expiration;
            if (!e.getInventory().getTitle().contains("  ")) {
                e.getWhoClicked().closeInventory();
                return;
            }
            if (!e.isCancelled()) {
                e.setCancelled(true);
            }
            if (e.getResult() != Event.Result.DENY) {
                e.setResult(Event.Result.DENY);
            }
            if ((expiration = this.expiringClickMap.get(e.getWhoClicked().getUniqueId())) != null && expiration > System.currentTimeMillis()) {
                return;
            }
            Player p1 = (Player)e.getWhoClicked();
            Player p2 = Bukkit.getPlayer((UUID)((UUID)((MetadataValue)p1.getMetadata("compilexTradePartnerUUID").get(0)).value()));
            Inventory inv1 = p1.getOpenInventory().getTopInventory();
            Inventory inv2 = p2.getOpenInventory().getTopInventory();
            for (int slot : NewTrade.getValidSlots()) {
                if (inv2.getItem(slot) != null) {
                    int linked = NewTrade.getLinkedSlot(slot);
                    inv1.setItem(linked, inv2.getItem(slot));
                } else {
                    inv1.setItem(NewTrade.getLinkedSlot(slot).intValue(), new ItemStack(Material.AIR));
                }
                if (inv1.getItem(slot) != null) {
                    inv2.setItem(NewTrade.getLinkedSlot(slot).intValue(), inv1.getItem(slot));
                    continue;
                }
                inv2.setItem(NewTrade.getLinkedSlot(slot).intValue(), new ItemStack(Material.AIR));
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (e.getPlayer().hasMetadata("compilexTradePartnerUUID")) {
            Player p2;
            Player p1 = (Player)e.getPlayer();
            if (e.getInventory().getTitle().contains("  ") && (e.getInventory().getItem(0) != null && (e.getInventory().getItem(0).getDurability() != NewTrade.green_button.getDurability() || e.getInventory().getItem(0).getAmount() > 0) || e.getInventory().getItem(8) != null && (e.getInventory().getItem(8).getDurability() != NewTrade.green_button.getDurability() || e.getInventory().getItem(8).getAmount() > 0))) {
                ArrayList givingBack = Lists.newArrayList();
                for (int slot : NewTrade.getValidSlots()) {
                    if (e.getInventory().getItem(slot) == null || e.getInventory().getItem(slot).getType() == Material.STAINED_GLASS_PANE) continue;
                    p1.getInventory().addItem(new ItemStack[]{e.getInventory().getItem(slot)});
                    givingBack.add(e.getInventory().getItem(slot));
                }
                p1.sendMessage((Object)ChatColor.YELLOW + "" + (Object)ChatColor.BOLD + "Trade cancelled.");
                if (givingBack.size() > 0) {
                    Bukkit.getLogger().info("[(Arkkit) NewTrade] " + p1.getName() + " was returned items: " + Arrays.toString((Object[])givingBack.toArray((T[])new ItemStack[givingBack.size()])));
                }
            }
            UUID p2_uuid = (UUID)((MetadataValue)p1.getMetadata("compilexTradePartnerUUID").get(0)).value();
            p1.removeMetadata("compilexTradePartnerUUID", (Plugin)Arkkit.get());
            if (p1.hasMetadata("compilexTradeTaskID")) {
                Bukkit.getScheduler().cancelTask(((MetadataValue)p1.getMetadata("compilexTradeTaskID").get(0)).asInt());
                p1.removeMetadata("compilexTradeTaskID", (Plugin)Arkkit.get());
            }
            Player player = p2 = Bukkit.getPlayer((UUID)p2_uuid) != null ? Bukkit.getPlayer((UUID)p2_uuid) : null;
            if (p2 != null) {
                p2.removeMetadata("compilexTradeTaskID", (Plugin)Arkkit.get());
                p2.closeInventory();
            }
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onItemPickup(PlayerPickupItemEvent e) {
        if (e.getPlayer().hasMetadata("compilexTradePartnerUUID")) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onEntityDamage(EntityDamageEvent e) {
        Player p1;
        if (e.getEntity() instanceof Player && (p1 = (Player)e.getEntity()).hasMetadata("compilexTradePartnerUUID")) {
            p1.closeInventory();
        }
    }
}

