/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.me.astropvp.cratekeys.inventory.InventoryClickEvent
 *  org.bukkit.event.me.astropvp.cratekeys.inventory.InventoryCloseEvent
 *  org.bukkit.event.me.astropvp.cratekeys.inventory.InventoryOpenEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.event.player.PlayerPickupItemEvent
 *  org.bukkit.me.astropvp.cratekeys.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 */
package me.ifamasssxd.cosmicduel.listeners;

import me.ifamasssxd.cosmicduel.CosmicDuel;
import me.ifamasssxd.cosmicduel.DuelAPI;
import me.ifamasssxd.cosmicduel.struct.Arena;
import me.ifamasssxd.cosmicduel.struct.QueueManager;
import me.ifamasssxd.cosmicduel.struct.enums.CancelReason;
import me.ifamasssxd.cosmicduel.struct.enums.DuelCreateState;
import me.ifamasssxd.cosmicduel.struct.enums.DuelState;
import me.ifamasssxd.cosmicduel.struct.newduel.NewDuel;
import me.ifamasssxd.cosmicduel.utils.EnchantUtils;
import me.ifamasssxd.cosmicduel.utils.KitUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class DuelInventoryListener
implements Listener {
    @EventHandler(priority=EventPriority.HIGHEST)
    public void onInventoryClickSettings(InventoryClickEvent e) {
        ItemStack item;
        Player p = (Player)e.getWhoClicked();
        if (e.getInventory().getTitle().equalsIgnoreCase("Duel Settings")) {
            NewDuel duel;
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            if (e.getCurrentItem() == null) {
                return;
            }
            if (DuelAPI.hasDuel(p) && (duel = DuelAPI.getDuel(p)).getCreateState() == DuelCreateState.SETTINGS) {
                return;
            }
        } else if (e.getInventory().getTitle().endsWith(" Duel Settings")) {
            e.setResult(Event.Result.DENY);
            e.setCancelled(true);
            if (e.getCurrentItem() != null && e.getCurrentItem().getType() == Material.STAINED_GLASS_PANE && e.getCurrentItem().getDurability() == 5 && e.getCurrentItem().getItemMeta().hasDisplayName() && e.getCurrentItem().getItemMeta().getDisplayName().contains("ACCEPT")) {
                DuelAPI.acceptInvite(p, e.getInventory().getTitle().split("'s")[0], false);
                return;
            }
        }
        if (!e.isCancelled() && e.getCurrentItem() != null && e.getCurrentItem().containsEnchantment(EnchantUtils.getCustomEnchant())) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            e.getCurrentItem().removeEnchantment(EnchantUtils.getCustomEnchant());
        }
        if (!(DuelAPI.hasDuel(p) && p.getWorld().getName().equals(Arena.getDuelWorldName()) || e.isCancelled() || e.getInventory().getName().equals("Customize Your Kit") || (item = e.getCurrentItem()) == null || item.getType() == Material.AIR || !this.isDuelKitItem(item))) {
            e.getClickedInventory().removeItem(new ItemStack[]{item});
            for (ItemStack items : e.getClickedInventory()) {
                if (items == null || items.getType() == Material.AIR || !this.isDuelKitItem(items)) continue;
                e.getClickedInventory().removeItem(new ItemStack[]{items});
            }
            boolean found = false;
            ItemStack[] armor = p.getInventory().getArmorContents();
            for (int i = 0; i < armor.length; ++i) {
                ItemStack is = armor[i];
                if (is == null || !this.isDuelKitItem(is)) continue;
                armor[i] = null;
                found = true;
            }
            if (found) {
                p.getInventory().setArmorContents(armor);
                p.updateInventory();
            }
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
        }
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        for (ItemStack item : event.getPlayer().getInventory()) {
            if (item == null || !this.isDuelKitItem(item)) continue;
            event.getPlayer().getInventory().removeItem(new ItemStack[]{item});
        }
        boolean change = false;
        ItemStack[] armor = event.getPlayer().getInventory().getArmorContents();
        for (int i = 0; i < armor.length; ++i) {
            if (armor[i] == null || !this.isDuelKitItem(armor[i])) continue;
            change = true;
            armor[i] = null;
        }
        if (change) {
            event.getPlayer().getInventory().setArmorContents(armor);
            event.getPlayer().updateInventory();
        }
    }

    public void clearItems(Player player) {
        for (ItemStack item : player.getInventory()) {
            if (item == null || !this.isDuelKitItem(item)) continue;
            player.getInventory().removeItem(new ItemStack[]{item});
        }
        boolean change = false;
        ItemStack[] armor = player.getInventory().getArmorContents();
        for (int i = 0; i < armor.length; ++i) {
            if (armor[i] == null || !this.isDuelKitItem(armor[i])) continue;
            change = true;
            armor[i] = null;
        }
        if (change) {
            player.getInventory().setArmorContents(armor);
            player.updateInventory();
        }
    }

    private boolean isDuelKitItem(ItemStack item) {
        return item.hasItemMeta() && item.getItemMeta().hasLore() && (item.getItemMeta().getLore().contains(KitUtils.ITEM_WATERMARK) || item.getItemMeta().getLore().contains(KitUtils.ENVOY_WATERMARK));
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player)e.getWhoClicked();
        if (e.getInventory().getName().startsWith("Duel Items:")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            p.closeInventory();
            return;
        }
        if (e.getInventory().getName().contains(p.getName()) && e.getInventory().getName().length() >= 32 && !e.getInventory().getName().contains("/")) {
            if (DuelAPI.hasDuel(p)) {
                NewDuel duel = DuelAPI.getDuel(p);
                if (!duel.getCreateState().equals((Object)DuelCreateState.STAKES)) {
                    e.setResult(Event.Result.DENY);
                    e.setCancelled(true);
                    return;
                }
                e.setCancelled(true);
                e.setResult(Event.Result.DENY);
                duel.getStakeInventory().onInventoryEvent(e);
            } else if (!e.getWhoClicked().hasMetadata("close_exempt")) {
                e.setCancelled(true);
                p.closeInventory();
            }
        }
    }

    @EventHandler
    public void onThrowPotionFromInventory(InventoryClickEvent event) {
        Player player = (Player)event.getWhoClicked();
        NewDuel duel = DuelAPI.getDuel(player);
        if (duel != null && duel.getDuelState() == DuelState.PLAYING && event.getClickedInventory() == null && event.getCursor() != null && event.getCursor().getType() == Material.GLASS_BOTTLE) {
            event.setCancelled(true);
            player.setItemOnCursor((ItemStack)null);
            player.updateInventory();
            player.playSound(player.getLocation(), Sound.LAVA_POP, 1.0f, 1.0f);
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onPlayerPickupItem(PlayerPickupItemEvent e) {
        NewDuel d;
        Player p = e.getPlayer();
        if (DuelAPI.hasDuel(p) && (d = DuelAPI.getDuel(p)).getCreateState() == DuelCreateState.STAKES) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerOpen(InventoryOpenEvent event) {
        Player player = (Player)event.getPlayer();
        NewDuel duel = DuelAPI.getDuel(player);
        if (duel != null && duel.getDuelState() != DuelState.IN_QUEUE && duel.getDuelState() != DuelState.WAITING) {
            if (player.getWorld().getName().equals(Arena.getDuelWorldName())) {
                return;
            }
            event.setCancelled(true);
            player.sendMessage((Object)ChatColor.RED + "You cannot open inventories outside of the duel arena.");
        }
    }

    @EventHandler
    public void onPlayerClose(InventoryCloseEvent e) {
        block12: {
            Player p;
            block16: {
                QueueManager queueManager;
                block17: {
                    block15: {
                        NewDuel d;
                        block13: {
                            block14: {
                                block11: {
                                    NewDuel d2;
                                    p = (Player)e.getPlayer();
                                    if (p.hasMetadata("close_exempt")) {
                                        p.removeMetadata("close_exempt", (Plugin)CosmicDuel.get());
                                        return;
                                    }
                                    queueManager = CosmicDuel.get().getQueueManager();
                                    if (!e.getInventory().getName().equalsIgnoreCase("Duel Settings")) break block11;
                                    if (DuelAPI.hasDuel(p) && (d2 = DuelAPI.getDuel(p)).getCreateState() == DuelCreateState.SETTINGS) {
                                        p.sendMessage((Object)ChatColor.YELLOW + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.YELLOW + "You closed the " + (Object)ChatColor.YELLOW + ChatColor.UNDERLINE.toString() + "SETTINGS" + (Object)ChatColor.YELLOW + " selection.");
                                        p.sendMessage((Object)ChatColor.GRAY + "The duel has been cancelled.");
                                        DuelAPI.cancelDuel(d2, CancelReason.CLOSED_INVENTORY);
                                    }
                                    break block12;
                                }
                                if (!e.getInventory().getName().equalsIgnoreCase("Select an Arena")) break block13;
                                if (!DuelAPI.hasDuel(p)) break block14;
                                NewDuel d3 = DuelAPI.getDuel(p);
                                if (d3.getCreateState() == DuelCreateState.ARENA) {
                                    p.sendMessage((Object)ChatColor.YELLOW + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.YELLOW + "You closed the " + (Object)ChatColor.YELLOW + ChatColor.UNDERLINE.toString() + "ARENA" + (Object)ChatColor.YELLOW + " selection.");
                                    p.sendMessage((Object)ChatColor.GRAY + "The duel has been cancelled.");
                                    DuelAPI.cancelDuel(d3, CancelReason.CLOSED_INVENTORY);
                                }
                                break block12;
                            }
                            NewDuel preparing = queueManager.getPreparingDuel(p);
                            if (preparing == null) break block12;
                            queueManager.removeDuelFromPrepareQueue(preparing);
                            p.sendMessage((Object)ChatColor.RED + "You have closed the Arena Selection menu and cancelled the Global Queued Duel.");
                            break block12;
                        }
                        if (!e.getInventory().getName().contains(p.getName()) || e.getInventory().getName().length() < 32) break block15;
                        if (DuelAPI.hasDuel(p) && (d = DuelAPI.getDuel(p)).getCreateState() == DuelCreateState.STAKES) {
                            d.sendMessage((Object)ChatColor.YELLOW + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.YELLOW + p.getName() + " closed the " + (Object)ChatColor.YELLOW + ChatColor.UNDERLINE.toString() + "STAKES" + (Object)ChatColor.YELLOW + " selection.", false);
                            d.sendMessage((Object)ChatColor.GRAY + "The duel has been cancelled.", false);
                            d.setCreateState(DuelCreateState.DONE);
                            d.getStakeInventory().closeInventory(d);
                            DuelAPI.cancelDuel(d, CancelReason.CLOSED_INVENTORY);
                        }
                        break block12;
                    }
                    if (!e.getInventory().getName().equalsIgnoreCase("Select a Kit") && !e.getInventory().getName().equals("Select a Duel GKit")) break block16;
                    if (!DuelAPI.hasDuel(p)) break block17;
                    NewDuel d = DuelAPI.getDuel(p);
                    if (d.getCreateState() == DuelCreateState.KIT) {
                        d.sendMessage((Object)ChatColor.YELLOW + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.YELLOW + p.getName() + " closed the " + (Object)ChatColor.YELLOW + ChatColor.UNDERLINE.toString() + "KITS" + (Object)ChatColor.YELLOW + " selection.", false);
                        d.sendMessage((Object)ChatColor.GRAY + "The duel has been cancelled.", false);
                        DuelAPI.cancelDuel(d, CancelReason.CLOSED_INVENTORY);
                    }
                    break block12;
                }
                NewDuel preparing = queueManager.getPreparingDuel(p);
                if (preparing == null) break block12;
                queueManager.removeDuelFromPrepareQueue(preparing);
                p.sendMessage((Object)ChatColor.RED + "You have closed the Kit Selection menu and cancelled the Global Queued Duel.");
                break block12;
            }
            if (e.getInventory().getName().endsWith("Duel Settings")) {
                if (DuelAPI.hasPendingInvite(p)) {
                    DuelAPI.declineDuel(p, e.getInventory().getName().split("'s")[0], true);
                }
            } else if (e.getInventory().getName().contains("'s Death Loot")) {
                for (ItemStack is : e.getInventory()) {
                    if (is == null || is.getType() == Material.AIR) continue;
                    if (p.getInventory().firstEmpty() == -1) {
                        p.getWorld().dropItem(p.getLocation(), is);
                        continue;
                    }
                    p.getInventory().addItem(new ItemStack[]{is});
                }
            }
        }
    }
}

