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

import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;
import me.ifamasssxd.cosmicduel.CosmicDuel;
import me.ifamasssxd.cosmicduel.DuelAPI;
import me.ifamasssxd.cosmicduel.struct.Arena;
import me.ifamasssxd.cosmicduel.struct.QueueManager;
import me.ifamasssxd.cosmicduel.struct.TeamData;
import me.ifamasssxd.cosmicduel.struct.enums.CancelReason;
import me.ifamasssxd.cosmicduel.struct.enums.DuelCreateState;
import me.ifamasssxd.cosmicduel.struct.enums.DuelState;
import me.ifamasssxd.cosmicduel.struct.newduel.NewDuel;
import me.ifamasssxd.cosmicduel.utils.EnchantUtils;
import me.ifamasssxd.cosmicduel.utils.KitUtils;
import org.bukkit.Bukkit;
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
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class DuelInventoryListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClickSettings(InventoryClickEvent e) {
        Player p = (Player)e.getWhoClicked();
        if (e.getInventory().getTitle().equalsIgnoreCase("Duel Settings")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            if (e.getCurrentItem() == null)
                return;
            if (DuelAPI.hasDuel(p)) {
                NewDuel duel = DuelAPI.getDuel(p);
                if (duel.getCreateState() == DuelCreateState.SETTINGS)
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
        if (!DuelAPI.hasDuel(p) && !e.isCancelled() && !e.getInventory().getName().equals("Customize Your Kit")) {
            ItemStack item = e.getCurrentItem();
            if (item != null && item.getType() != Material.AIR && isDuelKitItem(item)) {
                e.getClickedInventory().removeItem(new ItemStack[] { item });
                for (ListIterator<ItemStack> listIterator = e.getClickedInventory().iterator(); listIterator.hasNext(); ) {
                    ItemStack items = listIterator.next();
                    if (items != null && items.getType() != Material.AIR && isDuelKitItem(items))
                        e.getClickedInventory().removeItem(new ItemStack[] { items });
                }
                boolean found = false;
                ItemStack[] armor = p.getInventory().getArmorContents();
                for (int i = 0; i < armor.length; i++) {
                    ItemStack is = armor[i];
                    if (is != null && isDuelKitItem(is)) {
                        armor[i] = null;
                        found = true;
                    }
                }
                if (found) {
                    p.getInventory().setArmorContents(armor);
                    p.updateInventory();
                }
                e.setCancelled(true);
                e.setResult(Event.Result.DENY);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        for (ListIterator<ItemStack> listIterator = event.getPlayer().getInventory().iterator(); listIterator.hasNext(); ) {
            ItemStack item = listIterator.next();
            if (item != null && isDuelKitItem(item))
                event.getPlayer().getInventory().removeItem(new ItemStack[] { item });
        }
        boolean change = false;
        ItemStack[] armor = event.getPlayer().getInventory().getArmorContents();
        for (int i = 0; i < armor.length; i++) {
            if (armor[i] != null && isDuelKitItem(armor[i])) {
                change = true;
                armor[i] = null;
            }
        }
        if (change) {
            event.getPlayer().getInventory().setArmorContents(armor);
            event.getPlayer().updateInventory();
        }
    }

    public void clearItems(Player player) {
        for (ListIterator<ItemStack> listIterator = player.getInventory().iterator(); listIterator.hasNext(); ) {
            ItemStack item = listIterator.next();
            if (item != null && isDuelKitItem(item))
                player.getInventory().removeItem(new ItemStack[] { item });
        }
        boolean change = false;
        ItemStack[] armor = player.getInventory().getArmorContents();
        for (int i = 0; i < armor.length; i++) {
            if (armor[i] != null && isDuelKitItem(armor[i])) {
                change = true;
                armor[i] = null;
            }
        }
        if (change) {
            player.getInventory().setArmorContents(armor);
            player.updateInventory();
        }
    }

    private boolean isDuelKitItem(ItemStack item) {
        return (item.hasItemMeta() && item.getItemMeta().hasLore() && (item.getItemMeta().getLore().contains(KitUtils.ITEM_WATERMARK) || item.getItemMeta().getLore().contains(KitUtils.ENVOY_WATERMARK)));
    }

    private void updateSkullIcon(Inventory inv, NewDuel duel, Player player, TeamData enemy) {
        int index = 9;
        List<String> playerNames = Lists.newArrayList();
        for (int i = inv.getSize() - index; i < inv.getSize() - index + enemy.getTeamates().size() + 1; i++) {
            ItemStack item = inv.getItem(i);
            if (item != null && item.getItemMeta() != null)
                playerNames.add(ChatColor.stripColor(item.getItemMeta().getDisplayName()));
        }
        int index2 = 10;
        for (String playerName : playerNames) {
            index2--;
            ItemStack item = duel.getStakeInventory().getSkullItem(Bukkit.getPlayer(playerName));
            if (ChatColor.stripColor(item.getItemMeta().getDisplayName()).equalsIgnoreCase(player.getName())) {
                inv.setItem(inv.getSize() - index2, duel.getStakeInventory().getSelectedIcon(item));
                continue;
            }
            inv.setItem(inv.getSize() - index2, item);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player)e.getWhoClicked();
        if (e.getInventory().getName().startsWith("Duel Items:")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            p.closeInventory();
            return;
        }
        TeamData data = CosmicDuel.get().getTeamDataManager().tryFindTeam(p.getUniqueId());
        if (data != null) {
            if (e.getInventory().getName().contains(Bukkit.getPlayer(data.getLeaderUUID()).getName()) && e.getInventory().getName().length() >= 32 && !e.getInventory().getName().contains("/"))
                if (DuelAPI.hasDuel(p)) {
                    NewDuel duel = DuelAPI.getDuel(p);
                    if (duel instanceof me.ifamasssxd.cosmicduel.struct.newduel.TeamDuel) {
                        if (!duel.getCreateState().equals(DuelCreateState.STAKES)) {
                            e.setResult(Event.Result.DENY);
                            e.setCancelled(true);
                            return;
                        }
                        e.setCancelled(true);
                        e.setResult(Event.Result.DENY);
                        if (p == Bukkit.getPlayer(data.getLeaderUUID())) {
                            ItemStack item = e.getCurrentItem();
                            if (item != null && item.getType() != null && item.getType().equals(Material.SKULL_ITEM)) {
                                String enemyName = ChatColor.stripColor(item.getItemMeta().getDisplayName());
                                Player enemy = Bukkit.getPlayer(enemyName);
                                duel.getStakeInventory().setEnemyInventory(duel.getStakeInventory().getTeamInventory(p), enemy);
                                updateSkullIcon(duel.getStakeInventory().getTeamInventory(p), duel, enemy, CosmicDuel.get().getTeamDataManager().tryFindTeam(enemy.getUniqueId()));
                                for (UUID teamates : data.getTeamates()) {
                                    Bukkit.getPlayer(teamates).updateInventory();
                                    Bukkit.getPlayer(teamates).sendMessage(CC.translate("&a&l(!) &f" + p.getName() + " &ahas changed the inventory view to &c" + enemyName + "'s &ainventory."));
                                    Bukkit.getPlayer(teamates).playSound(Bukkit.getPlayer(teamates).getLocation(), Sound.CLICK, 0.75F, 0.75F);
                                }
                                p.updateInventory();
                                p.sendMessage(CC.translate("&a&l(!) &aYou have changed the inventory view to &c" + enemyName + "'s &ainventory."));
                                p.playSound(p.getLocation(), Sound.CLICK, 0.75F, 0.75F);
                            } else {
                                duel.getStakeInventory().onTeamInventoryEvent(e);
                            }
                        }
                    }
                } else if (!e.getWhoClicked().hasMetadata("close_exempt")) {
                    e.setCancelled(true);
                    p.closeInventory();
                }
        } else if (e.getInventory().getName().contains(p.getName()) && e.getInventory().getName().length() >= 32 && !e.getInventory().getName().contains("/")) {
            if (DuelAPI.hasDuel(p)) {
                NewDuel duel = DuelAPI.getDuel(p);
                if (!duel.getCreateState().equals(DuelCreateState.STAKES)) {
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
            player.playSound(player.getLocation(), Sound.LAVA_POP, 1.0F, 1.0F);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerPickupItem(PlayerPickupItemEvent e) {
        Player p = e.getPlayer();
        if (DuelAPI.hasDuel(p)) {
            NewDuel d = DuelAPI.getDuel(p);
            if (d.getCreateState() == DuelCreateState.STAKES)
                e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerOpen(InventoryOpenEvent event) {
        Player player = (Player)event.getPlayer();
        NewDuel duel = DuelAPI.getDuel(player);
        if (duel != null && duel.getDuelState() != DuelState.IN_QUEUE && duel.getDuelState() != DuelState.WAITING) {
            if (player.getWorld().getName().equals(Arena.getDuelWorldName()))
                return;
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "You cannot open inventories outside of the duel arena.");
        }
    }

    @EventHandler
    public void onPlayerClose(InventoryCloseEvent e) {
        Player p = (Player)e.getPlayer();
        TeamData data = CosmicDuel.get().getTeamDataManager().tryFindTeam(p.getUniqueId());
        if (p.hasMetadata("close_exempt")) {
            p.removeMetadata("close_exempt", (Plugin)CosmicDuel.get());
            return;
        }
        QueueManager queueManager = CosmicDuel.get().getQueueManager();
        if (e.getInventory().getName().equalsIgnoreCase("Duel Settings")) {
            if (DuelAPI.hasDuel(p)) {
                NewDuel d = DuelAPI.getDuel(p);
                if (d.getCreateState() == DuelCreateState.SETTINGS) {
                    p.sendMessage(ChatColor.YELLOW + ChatColor.BOLD.toString() + "(!) " + ChatColor.YELLOW + "You closed the " + ChatColor.YELLOW + ChatColor.UNDERLINE.toString() + "SETTINGS" + ChatColor.YELLOW + " selection.");
                    p.sendMessage(ChatColor.GRAY + "The duel has been cancelled.");
                    DuelAPI.cancelDuel(d, CancelReason.CLOSED_INVENTORY);
                }
            }
        } else if (e.getInventory().getName().equalsIgnoreCase("Select an Arena")) {
            if (DuelAPI.hasDuel(p)) {
                NewDuel d = DuelAPI.getDuel(p);
                if (d.getCreateState() == DuelCreateState.ARENA) {
                    p.sendMessage(ChatColor.YELLOW + ChatColor.BOLD.toString() + "(!) " + ChatColor.YELLOW + "You closed the " + ChatColor.YELLOW + ChatColor.UNDERLINE.toString() + "ARENA" + ChatColor.YELLOW + " selection.");
                    p.sendMessage(ChatColor.GRAY + "The duel has been cancelled.");
                    DuelAPI.cancelDuel(d, CancelReason.CLOSED_INVENTORY);
                }
            } else {
                NewDuel preparing = queueManager.getPreparingDuel(p);
                if (preparing != null) {
                    queueManager.removeDuelFromPrepareQueue(preparing);
                    p.sendMessage(ChatColor.RED + "You have closed the Arena Selection menu and cancelled the Global Queued Duel.");
                }
            }
        } else if (e.getInventory().getName().contains(p.getName()) && e.getInventory().getName().length() >= 32) {
            if (DuelAPI.hasDuel(p)) {
                NewDuel d = DuelAPI.getDuel(p);
                if (d.getCreateState() == DuelCreateState.STAKES) {
                    d.sendMessage(ChatColor.YELLOW + ChatColor.BOLD.toString() + "(!) " + ChatColor.YELLOW + p.getName() + " closed the " + ChatColor.YELLOW + ChatColor.UNDERLINE.toString() + "STAKES" + ChatColor.YELLOW + " selection.", false);
                    d.sendMessage(ChatColor.GRAY + "The duel has been cancelled.", false);
                    d.setCreateState(DuelCreateState.DONE);
                    d.getStakeInventory().closeInventory(d);
                    DuelAPI.cancelDuel(d, CancelReason.CLOSED_INVENTORY);
                }
            }
        } else if (data != null && e.getInventory().getName().contains(Bukkit.getPlayer(data.getLeaderUUID()).getName()) && e.getInventory().getName().length() >= 32) {
            String teamLeaderName = Bukkit.getPlayer(data.getLeaderUUID()).getName();
            NewDuel d = DuelAPI.getDuel(p);
            if (d instanceof me.ifamasssxd.cosmicduel.struct.newduel.TeamDuel &&
                    d.getCreateState() == DuelCreateState.STAKES) {
                d.sendMessage(ChatColor.YELLOW + ChatColor.BOLD.toString() + "(!) " + ChatColor.YELLOW + p.getName() + " closed the " + ChatColor.YELLOW + ChatColor.UNDERLINE.toString() + "STAKES" + ChatColor.YELLOW + " selection.", false);
                d.sendMessage(ChatColor.GRAY + "The duel has been cancelled.", false);
                d.setCreateState(DuelCreateState.DONE);
                d.getStakeInventory().closeInventory(d);
                DuelAPI.cancelDuel(d, CancelReason.CLOSED_INVENTORY);
            }
        } else if (e.getInventory().getName().equalsIgnoreCase("Select a Kit") || e.getInventory().getName().equals("Select a Duel GKit")) {
            if (DuelAPI.hasDuel(p)) {
                NewDuel d = DuelAPI.getDuel(p);
                if (d.getCreateState() == DuelCreateState.KIT) {
                    d.sendMessage(ChatColor.YELLOW + ChatColor.BOLD.toString() + "(!) " + ChatColor.YELLOW + p.getName() + " closed the " + ChatColor.YELLOW + ChatColor.UNDERLINE.toString() + "KITS" + ChatColor.YELLOW + " selection.", false);
                    d.sendMessage(ChatColor.GRAY + "The duel has been cancelled.", false);
                    DuelAPI.cancelDuel(d, CancelReason.CLOSED_INVENTORY);
                }
            } else {
                NewDuel preparing = queueManager.getPreparingDuel(p);
                if (preparing != null) {
                    queueManager.removeDuelFromPrepareQueue(preparing);
                    p.sendMessage(ChatColor.RED + "You have closed the Kit Selection menu and cancelled the Global Queued Duel.");
                }
            }
        } else if (e.getInventory().getName().endsWith("Duel Settings")) {
            if (DuelAPI.hasPendingInvite(p))
                DuelAPI.declineDuel(p, e.getInventory().getName().split("'s")[0], true);
        } else if (e.getInventory().getName().contains("'s Death Loot")) {
            for (ListIterator<ItemStack> listIterator = e.getInventory().iterator(); listIterator.hasNext(); ) {
                ItemStack is = listIterator.next();
                if (is != null && is.getType() != Material.AIR) {
                    if (p.getInventory().firstEmpty() == -1) {
                        p.getWorld().dropItem(p.getLocation(), is);
                        continue;
                    }
                    p.getInventory().addItem(new ItemStack[] { is });
                }
            }
        }
    }
}
