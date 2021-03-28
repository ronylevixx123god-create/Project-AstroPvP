/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  me.cosmicpvp.CosmicGlobalKits.libs.GlobalKits
 *  org.apache.commons.lang.StringUtils
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.inventory.ClickType
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.inventory.InventoryCloseEvent
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 *  org.bukkit.event.player.PlayerDropItemEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerMoveEvent
 *  org.bukkit.event.player.PlayerPickupItemEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.Potion
 *  org.bukkit.potion.PotionType
 */
package me.ifamasssxd.cosmicduel.listeners;

import com.google.common.collect.Lists;
import me.cosmicpvp.CosmicGlobalKits.libs.GlobalKits;
import me.ifamasssxd.cosmicduel.CosmicDuel;
import me.ifamasssxd.cosmicduel.struct.CustomGKit;
import me.ifamasssxd.cosmicduel.struct.CustomKit;
import me.ifamasssxd.cosmicduel.struct.DuelPlayer;
import me.ifamasssxd.cosmicduel.struct.Kit;
import me.ifamasssxd.cosmicduel.utils.DuelUtils;
import me.ifamasssxd.cosmicduel.utils.KitUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomKitListener
implements Listener {
    public static final String CUSTOM_KIT_NAME = "Customize Your Kit";
    private List<PotionType> invalidPotionTypes = Lists.newArrayList(PotionType.SLOWNESS, PotionType.POISON, PotionType.INSTANT_DAMAGE, PotionType.REGEN);

    @EventHandler
    public void onPlayerClose(InventoryCloseEvent event) {
        Player pl = (Player)event.getPlayer();
        DuelPlayer duelPlayer = DuelPlayer.getDuelPlayer(pl.getPlayer());
        if (duelPlayer != null) {
            if ((duelPlayer.getCurrentlyModifying() != null || duelPlayer.getGKitCurrentlyModifying() != null) && event.getInventory().getName().equals(CUSTOM_KIT_NAME)) {
                if (pl.getItemOnCursor() != null) {
                    pl.setItemOnCursor((ItemStack)null);
                }
                ArrayList<ItemStack> kitItems = new ArrayList<ItemStack>();
                for (ItemStack is : pl.getInventory()) {
                    if (is == null || is.getType() == Material.AIR || !DuelUtils.isValidKitItem(is) || duelPlayer.getGKitCurrentlyModifying() != null && duelPlayer.getGKitCurrentlyModifying().getGKitItems().contains((Object)is)) continue;
                    kitItems.add(is);
                }
                String saveMessage = (Object)ChatColor.GRAY + "You can middle click this kit in the menu to set as default.";
                if (duelPlayer.getCurrentlyModifying() != null) {
                    CustomKit kit = duelPlayer.getCurrentlyModifying();
                    kit.setKitItems(kitItems);
                    duelPlayer.setCurrentlyModifying(null);
                    if (kit.isDefault()) {
                        saveMessage = (Object)ChatColor.GRAY + "Saving kit as default " + StringUtils.capitalize((String)kit.getKitType().getName(true)) + " Kit.";
                    }
                } else if (duelPlayer.getGKitCurrentlyModifying() != null) {
                    CustomGKit gkit = duelPlayer.getGKitCurrentlyModifying();
                    gkit.setItems(kitItems);
                    duelPlayer.setGKitCurrentlyModifying(null);
                    if (gkit.isDefault()) {
                        saveMessage = (Object)ChatColor.GRAY + "Saving kit as default " + StringUtils.capitalize((String)gkit.getGlobalKit().getKitName()) + " GKit.";
                    }
                }
                pl.getInventory().clear();
                pl.playSound(pl.getLocation(), Sound.LEVEL_UP, 1.0f, 1.4f);
                pl.sendMessage((Object)ChatColor.GREEN + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.GREEN + "Custom Kit saved.");
                pl.sendMessage(saveMessage);
                Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)CosmicDuel.get(), ((Player)pl)::updateInventory, 1L);
                duelPlayer.saveData(true);
            } else if (event.getInventory().getName().equals("Custom Kits")) {
                if (pl.hasMetadata("not_closing")) {
                    pl.removeMetadata("not_closing", (Plugin)CosmicDuel.get());
                    return;
                }
                if (duelPlayer.isPendingDeletion()) {
                    duelPlayer.saveData(true);
                }
                duelPlayer.setKitModifying(null);
                duelPlayer.setGKitModifying(null);
            }
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onPlayerMove(PlayerInteractEvent event) {
        DuelPlayer duelPlayer = DuelPlayer.getDuelPlayer(event.getPlayer());
        if (duelPlayer == null) {
            return;
        }
        if (duelPlayer.isModifying()) {
            event.setCancelled(true);
            event.getPlayer().openInventory(duelPlayer.createCustomInventory());
            event.getPlayer().closeInventory();
        }
    }

    /*@EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.getTo().getBlockX() == event.getFrom().getBlockX() && event.getTo().getBlockY() == event.getFrom().getBlockY() && event.getTo().getBlockZ() == event.getFrom().getBlockZ()) {
            return;
        }
        DuelPlayer duelPlayer = DuelPlayer.getDuelPlayer(event.getPlayer());
        if (duelPlayer == null) {
            if (event.getPlayer().getTicksLived() >= 100) {
                event.getPlayer().kickPlayer((Object)ChatColor.RED + "Your Duel Data seems to be malformed, please try re-logging to fix this issue.");
            }
            return;
        }
        if (duelPlayer.isModifying()) {
            event.setCancelled(true);
            event.getPlayer().openInventory(duelPlayer.createCustomInventory());
            event.getPlayer().closeInventory();
        }
    }*/

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onPlayerTakeDamageWhileModifying(EntityDamageEvent event) {
        Player player;
        DuelPlayer dp;
        if (event.getEntity() instanceof Player && (dp = DuelPlayer.getDuelPlayer(player = (Player)event.getEntity())) != null && dp.isModifying()) {
            event.setCancelled(true);
            player.closeInventory();
            player.sendMessage((Object)ChatColor.RED + "Closing editor due to damage taken.");
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onPlayerLeave(PlayerQuitEvent event) {
        DuelPlayer duelPlayer = DuelPlayer.getDuelPlayer(event.getPlayer());
        if (duelPlayer != null && duelPlayer.isModifying()) {
            event.getPlayer().closeInventory();
        }
    }

    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent event) {
        DuelPlayer duelPlayer = DuelPlayer.getDuelPlayer(event.getPlayer());
        if (duelPlayer != null && duelPlayer.isModifying()) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onPlayerDrop(PlayerPickupItemEvent event) {
        DuelPlayer duelPlayer = DuelPlayer.getDuelPlayer(event.getPlayer());
        if (duelPlayer != null && duelPlayer.isModifying()) {
            event.setCancelled(true);
        }
        if (event.getPlayer().getOpenInventory() != null && (event.getPlayer().getOpenInventory().getTopInventory().getName().equals("Select a Duel Kit") || event.getPlayer().getOpenInventory().getTopInventory().getName().equals("Custom Kits"))) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        DuelPlayer duelPlayer = DuelPlayer.getDuelPlayer(event.getPlayer());
        if (duelPlayer != null && duelPlayer.isModifying()) {
            event.setCancelled(true);
            event.setMessage("/null");
        }
    }

    @EventHandler(priority=EventPriority.LOW)
    public void onInventoryClick(InventoryClickEvent event) {
        DuelPlayer duelPlayer;
        Player player = (Player)event.getWhoClicked();
        if (event.getInventory().getName().equals(CUSTOM_KIT_NAME)) {
            boolean validCursorItem;
            event.setCancelled(true);
            event.setResult(Event.Result.DENY);
            if (event.getClick().isShiftClick()) {
                return;
            }
            DuelPlayer duelPlayer2 = DuelPlayer.getDuelPlayer(player);
            if (duelPlayer2.getCurrentlyModifying() == null && duelPlayer2.getGKitCurrentlyModifying() == null) {
                player.closeInventory();
                return;
            }
            ItemStack clickedItem = event.getCurrentItem();
            ItemStack cursorItem = event.getCursor();
            boolean validClickedItem = clickedItem != null && clickedItem.getType() != Material.AIR;
            boolean bl = validCursorItem = cursorItem != null && cursorItem.getType() != Material.AIR;
            if ((event.getClickedInventory() == null || event.getRawSlot() < 0) && validCursorItem) {
                player.setItemOnCursor((ItemStack)null);
                player.playSound(player.getLocation(), Sound.LAVA_POP, 1.0f, 1.3f);
                player.updateInventory();
                return;
            }
            if (event.getClickedInventory() == null) {
                return;
            }
            if (event.getClickedInventory().equals((Object)event.getInventory())) {
                if (validClickedItem) {
                    if (this.isValidPickupItem(clickedItem)) {
                        Potion potion;
                        if (clickedItem.getType() == Material.POTION && (potion = Potion.fromItemStack((ItemStack)clickedItem)) != null && potion.getType() == PotionType.INSTANT_HEAL) {
                            if (this.isDoubleClicking(player)) {
                                boolean added = false;
                                for (int i = 0; i < player.getInventory().getSize(); ++i) {
                                    if (player.getInventory().getItem(i) != null && player.getInventory().getItem(i).getType() != Material.AIR) continue;
                                    player.getInventory().setItem(i, clickedItem.clone());
                                    added = true;
                                }
                                if (added) {
                                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0f, 1.4f);
                                }
                            }
                            player.setMetadata("previous_pot_click", (MetadataValue)new FixedMetadataValue((Plugin)CosmicDuel.get(), (Object)System.currentTimeMillis()));
                        }
                        player.setItemOnCursor(clickedItem.clone());
                        player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0f, 1.3f);
                        player.updateInventory();
                    } else {
                        player.sendMessage((Object)ChatColor.RED + "You cannot modify this item.");
                    }
                }
            } else {
                if (validCursorItem && validClickedItem) {
                    if (this.canPlaceItem(player, cursorItem, true) && this.isValidPickupItem(clickedItem)) {
                        event.setCancelled(false);
                        player.playSound(player.getLocation(), Sound.CLICK, 1.0f, 1.4f);
                    }
                    return;
                }
                if (!validClickedItem && validCursorItem && this.canPlaceItem(player, cursorItem, true)) {
                    event.getClickedInventory().setItem(event.getSlot(), cursorItem);
                    player.playSound(player.getLocation(), Sound.CLICK, 1.0f, 0.8f);
                }
                if (validClickedItem && this.isValidPickupItem(clickedItem)) {
                    event.setCancelled(false);
                }
            }
        } else if (event.getInventory().getName().equals("Select a Duel Kit")) {
            event.setCancelled(true);
            ItemStack item = event.getCurrentItem();
            if (item == null || item.getType() == Material.AIR || !item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) {
                return;
            }
            String itemName = item.getItemMeta().getDisplayName();
            if (itemName.startsWith((Object)ChatColor.YELLOW + ChatColor.BOLD.toString())) {
                String kitName = itemName.substring(4, itemName.length());
                if (kitName.startsWith("GKIT: ")) {
                    GlobalKits globalKit = KitUtils.getGlobalKitsFromItemName(kitName = kitName.split("GKIT: ")[1]);
                    if (globalKit != null) {
                        KitUtils.openCustomGKit(player, globalKit);
                    }
                } else {
                    Kit kit = Kit.getByName(kitName, true);
                    if (kit == null) {
                        return;
                    }
                    KitUtils.openCustomKits(player, kit);
                }
            }
        } else if (event.getInventory().getName().equals("Custom Kits")) {
            event.setCancelled(true);
            event.setResult(Event.Result.DENY);
            int slot = event.getRawSlot();
            ItemStack item2 = event.getCurrentItem();
            if (item2 == null || !item2.hasItemMeta() || !item2.getItemMeta().hasDisplayName()) {
                return;
            }
            DuelPlayer duelPlayer2 = DuelPlayer.getDuelPlayer(player);
            if (duelPlayer2.getKitModifying() == null && duelPlayer2.getGKitModifying() == null) {
                player.closeInventory();
                return;
            }
            String name = item2.getItemMeta().getDisplayName();
            if (name.contains("Create Custom Kit") || name.endsWith("Create Custom GKit")) {
                if (duelPlayer2.getKitModifying() != null) {
                    KitUtils.openNewCustomKit(player, duelPlayer2.getKitModifying());
                } else if (duelPlayer2.getGKitModifying() != null) {
                    KitUtils.openNewCustomGKit(player, duelPlayer2.getGKitModifying());
                }
            } else if (name.startsWith((Object)ChatColor.YELLOW + ChatColor.BOLD.toString() + "Custom Kit #")) {
                if (duelPlayer2.getKitModifying() != null) {
                    CustomKit kit2;
                    List<CustomKit> customKits = duelPlayer2.getCustomKits(duelPlayer2.getKitModifying());
                    if (customKits.isEmpty()) {
                        return;
                    }
                    int customKitSlot = slot;
                    if (customKits.size() < 3) {
                        customKitSlot = slot - 1;
                    }
                    if (customKits.size() > customKitSlot && (kit2 = customKits.get(customKitSlot)) != null) {
                        if (event.getClick() == ClickType.RIGHT) {
                            duelPlayer2.removeCustomKit(kit2);
                            duelPlayer2.setPendingDeletion(true);
                            player.sendMessage((Object)ChatColor.RED + "Custom Kit has been deleted.");
                            event.getClickedInventory().setItem(event.getRawSlot(), (ItemStack)null);
                            player.setMetadata("not_closing", (MetadataValue)new FixedMetadataValue((Plugin)CosmicDuel.get(), (Object)""));
                            KitUtils.openCustomKits(player, kit2.getKitType());
                            return;
                        }
                        if (event.getClick() == ClickType.MIDDLE) {
                            if (kit2.isDefault()) {
                                player.sendMessage((Object)ChatColor.RED + "This kit is already your default " + kit2.getKitType().getName(true) + " kit.");
                            }
                            duelPlayer2.setDefaultKit(kit2);
                            player.setMetadata("not_closing", (MetadataValue)new FixedMetadataValue((Plugin)CosmicDuel.get(), (Object)""));
                            KitUtils.openCustomKits(player, kit2.getKitType());
                            player.sendMessage((Object)ChatColor.GREEN + "Default " + StringUtils.capitalize((String)kit2.getKitType().getName(true)) + " Duel Kit set.");
                            return;
                        }
                        KitUtils.openCustomKitEditor(player, kit2);
                    }
                } else if (duelPlayer2.getGKitModifying() != null) {
                    CustomGKit kit3;
                    List<CustomGKit> customKits2 = duelPlayer2.getCustomGkits(duelPlayer2.getGKitModifying());
                    if (customKits2.isEmpty()) {
                        return;
                    }
                    int customKitSlot = slot - 1;
                    if (customKits2.size() > customKitSlot && (kit3 = customKits2.get(customKitSlot)) != null) {
                        if (event.getClick() == ClickType.RIGHT) {
                            duelPlayer2.removeCustomGKit(kit3);
                            duelPlayer2.setPendingDeletion(true);
                            player.sendMessage((Object)ChatColor.RED + "Custom GKit has been deleted.");
                            event.getClickedInventory().setItem(event.getRawSlot(), (ItemStack)null);
                            player.setMetadata("not_closing", (MetadataValue)new FixedMetadataValue((Plugin)CosmicDuel.get(), (Object)""));
                            KitUtils.openCustomGKit(player, kit3.getGlobalKit());
                            return;
                        }
                        if (event.getClick() == ClickType.MIDDLE) {
                            if (kit3.isDefault()) {
                                player.sendMessage((Object)ChatColor.RED + "This kit is already your default kit.");
                            }
                            duelPlayer2.setDefaultGKit(kit3);
                            player.setMetadata("not_closing", (MetadataValue)new FixedMetadataValue((Plugin)CosmicDuel.get(), (Object)""));
                            KitUtils.openCustomGKit(player, kit3.getGlobalKit());
                            player.sendMessage((Object)ChatColor.GREEN + "Default " + kit3.getGlobalKit().getKitName() + " Duel Kit set.");
                            return;
                        }
                        KitUtils.openCustomGKitEditor(player, kit3);
                    }
                }
            } else if (item2.getType() == Material.ARROW && (duelPlayer2.getKitModifying() != null || duelPlayer2.getGKitModifying() != null)) {
                player.openInventory(duelPlayer2.createCustomInventory());
            }
        } else if ((player.getOpenInventory().getTopInventory() == null || player.getOpenInventory().getTopInventory().getName().equals("container.crafting")) && (duelPlayer = DuelPlayer.getDuelPlayer(player)).isModifying()) {
            event.setCancelled(true);
            player.openInventory(duelPlayer.createCustomInventory());
        }
    }

    private boolean isValidPickupItem(ItemStack is) {
        return is.getType() != Material.GOLDEN_APPLE && is.getType() != Material.ARROW && !DuelUtils.isArmor(is) && !DuelUtils.isWeapon(is);
    }

    private boolean canPlaceItem(Player player, ItemStack item, boolean alert) {
        DuelPlayer duelPlayer = DuelPlayer.getDuelPlayer(player);
        HashMap originalKitPotionCount = new HashMap();
        if (duelPlayer.getCurrentlyModifying() != null) {
            duelPlayer.getCurrentlyModifying().getKitType().getItems().stream().filter(is -> {
                if (is == null) {
                    return false;
                }
                return is.getType() == Material.POTION;
            }).forEach(is -> {
                Potion potion = Potion.fromItemStack((ItemStack)is);
                if (potion == null) {
                    return;
                }
                if (originalKitPotionCount.containsKey((Object)potion.getType())) {
                    originalKitPotionCount.put(potion.getType(), (Integer)originalKitPotionCount.get((Object)potion.getType()) + is.getAmount());
                    return;
                }
                originalKitPotionCount.put(potion.getType(), is.getAmount());
            });
        }
        if (item.getType() != Material.POTION) {
            return true;
        }
        Potion potion = Potion.fromItemStack((ItemStack)item);
        PotionType type = potion.getType();
        if (type == null) {
            return true;
        }
        if (!this.invalidPotionTypes.contains((Object)type)) {
            return true;
        }
        if (!originalKitPotionCount.containsKey((Object)type)) {
            return true;
        }
        return (Integer)originalKitPotionCount.get((Object)type) > this.getPotionsByType(player, type).size();
    }

    private boolean isDoubleClicking(Player player) {
        if (player.hasMetadata("previous_pot_click") && player.getMetadata("previous_pot_click").size() > 0) {
            long time = ((MetadataValue)player.getMetadata("previous_pot_click").get(0)).asLong();
            if (System.currentTimeMillis() - time <= 200L) {
                return true;
            }
        }
        return false;
    }

    public List<ItemStack> getPotionsByType(Player player, PotionType potionType) {
        ArrayList retr = Lists.newArrayList();
        for (ItemStack item : player.getInventory()) {
            PotionType type;
            Potion potion;
            if (item == null || item.getType() != Material.POTION || (potion = Potion.fromItemStack((ItemStack)item)) == null || (type = potion.getType()) != potionType) continue;
            retr.add(item);
        }
        return retr;
    }
}

