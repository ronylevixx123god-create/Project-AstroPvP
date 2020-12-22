/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.customevents.CustomCosmicPlayerEvent
 *  com.cosmicpvp.cosmicutils.utils.ItemUtils
 *  com.google.common.collect.Lists
 *  org.apache.commons.lang.StringEscapeUtils
 *  org.apache.commons.lang.StringUtils
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.player.AsyncPlayerChatEvent
 *  org.bukkit.event.player.PlayerDropItemEvent
 *  org.bukkit.event.player.PlayerInteractEntityEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerItemHeldEvent
 *  org.bukkit.event.player.PlayerPickupItemEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.customevents.CustomCosmicPlayerEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.chat_filter.ChatUtils;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.arkhamnetwork.Arkkit.utils.ItemUtils;
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
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class LoreTag
extends ArkPlugin {
    private static ItemStack loreTagItem;
    public static String pending_lore_name;
    public static String lore_confirm_metadata;
    protected static final String PREVIOUS_LORE_PREFIX;
    protected static final String LORE_PREFIX;

    public LoreTag(Plugin p) {
        super(p);
    }

    @Override
    public void enable() throws IOException {
        this.registerListener(new CustomLoreListener());
        this.registerCommand(new CommandGiveLoreTag("giveloretag", "/<command>", "Spawn item lore tag."));
        this.registerCommand(new CommandEditLore("editlore", "/<command>", "Edit an item lore directly."));
        this.registerCommand(new CommandEditName("editname", "/<command>", "Edit an item name directly."));
        super.enable();
    }

    @Override
    public void disable() throws IOException {
        this.unregisterListeners();
        this.unregisterCommands();
        super.disable();
    }

    public static boolean isLoreTag(ItemStack i) {
        return com.cosmicpvp.cosmicutils.utils.ItemUtils.areItemsSimilarIgnoringIID((ItemStack)LoreTag.getLoreTagItem(), (ItemStack)i);
    }

    public static ItemStack getLoreTagItem() {
        if (loreTagItem == null) {
            loreTagItem = ItemUtils.buildItem(Material.INK_SACK, (short)1, ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "Item Lore Crystal " + (Object)ChatColor.GRAY + "(Right Click)", (Object)ChatColor.GRAY + "Apply a custom line of lore", (Object)ChatColor.GRAY + "to customize your equipment.", "", (Object)ChatColor.GOLD + ChatColor.BOLD.toString() + "* " + (Object)ChatColor.GRAY + "Limited to 1 custom line of lore per item.");
        }
        return loreTagItem.clone();
    }

    static {
        pending_lore_name = "pending_lore_name";
        lore_confirm_metadata = "lore_confirmation";
        PREVIOUS_LORE_PREFIX = (Object)ChatColor.GOLD + ChatColor.BOLD.toString() + "LORE ";
        LORE_PREFIX = (Object)ChatColor.GOLD + ChatColor.BOLD.toString() + "~";
    }

    class CustomLoreListener
    implements Listener {
        CustomLoreListener() {
        }

        @EventHandler
        public void onPlayerInteract(PlayerInteractEvent e) {
            if (e.hasItem() && LoreTag.isLoreTag(e.getItem())) {
                Player pl = e.getPlayer();
                e.setCancelled(true);
                e.setUseItemInHand(Event.Result.DENY);
                pl.updateInventory();
                if (pl.hasMetadata(pending_lore_name)) {
                    pl.sendMessage((Object)ChatColor.RED + "You already have a pending Item Lore Crystal queued.");
                    pl.sendMessage((Object)ChatColor.GRAY + "Type '" + (Object)ChatColor.RED + "cancel" + (Object)ChatColor.GRAY + "' to unqueue this opperation.");
                    return;
                }
                int amt = e.getItem().getAmount();
                if (amt == 1) {
                    pl.setItemInHand(null);
                } else if (amt > 1) {
                    ItemStack i = e.getItem();
                    i.setAmount(i.getAmount() - 1);
                    pl.setItemInHand(i);
                }
                pl.setMetadata(pending_lore_name, (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)true));
                pl.sendMessage("");
                pl.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "     Lore Crystal Usage");
                pl.sendMessage((Object)ChatColor.GOLD + "1. " + (Object)ChatColor.GRAY + "Hold the ITEM you'd like to modify.");
                pl.sendMessage((Object)ChatColor.GOLD + "2. " + (Object)ChatColor.GRAY + "Send the new lore line as a chat message");
                pl.sendMessage((Object)ChatColor.GOLD + "3. " + (Object)ChatColor.GRAY + "Confirm the preview of the new lore line that is displayed.");
                pl.sendMessage("");
                pl.playSound(pl.getLocation(), Sound.BAT_TAKEOFF, 1.0f, 0.5f);
            }
        }

        private boolean isAlreadyTagged(ItemStack item) {
            if (item == null || item.getType() == Material.AIR) {
                return false;
            }
            ItemMeta im = item.getItemMeta();
            if (!im.hasLore()) {
                return false;
            }
            for (String loreLine : im.getLore()) {
                if (!loreLine.startsWith(LORE_PREFIX) && !loreLine.startsWith(PREVIOUS_LORE_PREFIX)) continue;
                return true;
            }
            return false;
        }

        public ItemStack applyCustomLore(ItemStack item, String customLore) {
            ItemMeta im = item.getItemMeta();
            List<String> lore = im.hasLore() ? im.getLore() : Lists.newArrayList();
            ArrayList<String> newLore = new ArrayList<String>();
            String line = LORE_PREFIX + (Object)ChatColor.GOLD + customLore;
            boolean hadLore = false;
            for (String s : lore) {
                if ((s.startsWith(LORE_PREFIX) || s.startsWith(PREVIOUS_LORE_PREFIX)) && !hadLore) {
                    newLore.add(line);
                    hadLore = true;
                    continue;
                }
                newLore.add(s);
            }
            if (!hadLore) {
                newLore.add(line);
            }
            im.setLore(newLore);
            item.setItemMeta(im);
            return item;
        }

        @EventHandler(priority=EventPriority.LOWEST)
        public void onAsyncPlayerChat(AsyncPlayerChatEvent e) {
            if (e.getPlayer().hasMetadata(pending_lore_name)) {
                Player pl = e.getPlayer();
                e.setCancelled(true);
                if (e.getPlayer().hasMetadata(lore_confirm_metadata) && e.getMessage().equalsIgnoreCase("confirm")) {
                    String customLore = ((MetadataValue)pl.getMetadata(lore_confirm_metadata).get(0)).asString();
                    ItemStack is = pl.getItemInHand();
                    pl.setItemInHand(this.applyCustomLore(is, customLore));
                    pl.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.YELLOW + "Your ITEM has been modified by adding the following lore line: \"" + customLore + (Object)ChatColor.YELLOW + "\"");
                    pl.removeMetadata(pending_lore_name, (Plugin)Arkkit.get());
                    pl.removeMetadata(lore_confirm_metadata, (Plugin)Arkkit.get());
                    pl.playSound(pl.getLocation(), Sound.LEVEL_UP, 1.0f, 0.5f);
                    e.setMessage("");
                    Bukkit.getPluginManager().callEvent((Event)new CustomCosmicPlayerEvent(pl, "ITEM_USE", new Object[]{"LORETAG"}));
                    return;
                }
                if (e.getMessage().equalsIgnoreCase("cancel") || e.getPlayer().hasMetadata(lore_confirm_metadata) && !e.getMessage().equalsIgnoreCase("confirm")) {
                    pl.removeMetadata(lore_confirm_metadata, (Plugin)Arkkit.get());
                    pl.removeMetadata(pending_lore_name, (Plugin)Arkkit.get());
                    pl.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "** " + (Object)ChatColor.RED + "You have unqueued your Lore Crystal for usage.");
                    if (pl.getInventory().firstEmpty() != -1) {
                        pl.getInventory().addItem(new ItemStack[]{LoreTag.getLoreTagItem()});
                    } else {
                        pl.sendMessage((Object)ChatColor.RED + "(!) You had no room in your inventory, the loretag has been dropped at your feet.");
                        pl.getWorld().dropItem(pl.getLocation(), LoreTag.getLoreTagItem());
                    }
                } else if (pl.getItemInHand() != null) {
                    ItemStack is = pl.getItemInHand();
                    if (is == null || is.getType() == Material.AIR) {
                        pl.sendMessage((Object)ChatColor.RED + "Hold the ITEM you'd like to apply custom lore to.");
                        pl.sendMessage((Object)ChatColor.GRAY + "Type '" + (Object)ChatColor.RED + "cancel" + (Object)ChatColor.GRAY + "' to unqueue this opperation.");
                        return;
                    }
                    if (e.getMessage().contains("\u00a7") || e.getMessage().contains("&")) {
                        pl.sendMessage((Object)ChatColor.RED + "You cannot use color codes in your line of lore.");
                        return;
                    }
                    if (!StringUtils.isAlphanumeric((String)e.getMessage().replace(" ", "").replace("?", "").replace(".", "").replace("!", ""))) {
                        pl.sendMessage((Object)ChatColor.RED + "Your custom lore line must only contain alpha numeric characters!");
                        return;
                    }
                    if (!ItemNametagListener.canRename.contains((Object)is.getType())) {
                        pl.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "INVALID ITEM -- WEAPONS AND ARMOR ONLY!");
                        pl.sendMessage((Object)ChatColor.RED + "Hold the ITEM you'd like to apply custom lore to.");
                        pl.sendMessage((Object)ChatColor.GRAY + "Type '" + (Object)ChatColor.RED + "cancel" + (Object)ChatColor.GRAY + "' to unqueue this opperation.");
                        return;
                    }
                    String custom_name = StringEscapeUtils.escapeSql((String)e.getMessage());
                    if (e.getMessage().length() <= 0 || custom_name.length() <= 0) {
                        pl.sendMessage((Object)ChatColor.RED + "Your custom lore line must contain atleast 1 character.");
                        return;
                    }
                    if (custom_name.length() >= 64) {
                        pl.sendMessage((Object)ChatColor.RED + "Your custom lore line cannot exceed 64 characters.");
                        return;
                    }
                    if (custom_name.contains("[") || custom_name.contains("]")) {
                        pl.sendMessage((Object)ChatColor.RED + "You cannot use [ or ] brackets in item lore.");
                        return;
                    }
                    if (ChatUtils.isSwearMessage(custom_name)) {
                        pl.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.RED + "Your custom lore line contains illegal phrases.");
                        pl.sendMessage((Object)ChatColor.GRAY + "Problematic phrases: " + ChatUtils.getFilteredSwearMessage(custom_name, true));
                        pl.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "WARNING: " + (Object)ChatColor.GRAY + "Attempting to bypass this filter will result in your item disappearing.");
                        return;
                    }
                    pl.sendMessage(ChatColor.YELLOW.toString() + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.YELLOW + "Lore Line Preview: " + (Object)ChatColor.GOLD + custom_name);
                    pl.sendMessage((Object)ChatColor.GRAY + "Type '" + (Object)ChatColor.GREEN + "confirm" + (Object)ChatColor.GRAY + "' if this looks correct, otherwise type '" + (Object)ChatColor.RED + "cancel" + (Object)ChatColor.GRAY + "' to start over.");
                    if (this.isAlreadyTagged(is)) {
                        pl.sendMessage((Object)ChatColor.YELLOW + "NOTE: " + (Object)ChatColor.GRAY + "This will overwrite the existing custom LORE on the item!");
                    }
                    pl.setMetadata(lore_confirm_metadata, (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)custom_name));
                } else {
                    pl.sendMessage((Object)ChatColor.RED + "Hold the ITEM you'd like to apply a custom line of lore to.");
                    pl.sendMessage((Object)ChatColor.GRAY + "Type '" + (Object)ChatColor.RED + "cancel" + (Object)ChatColor.GRAY + "' to unqueue this opperation.");
                }
                e.setMessage("");
            }
        }

        @EventHandler(priority=EventPriority.LOWEST)
        public void onPlayerQuit(PlayerQuitEvent e) {
            Player pl = e.getPlayer();
            if (pl.hasMetadata(pending_lore_name)) {
                if (pl.getInventory().firstEmpty() != -1) {
                    pl.getInventory().addItem(new ItemStack[]{LoreTag.getLoreTagItem()});
                }
                pl.removeMetadata(pending_lore_name, (Plugin)Arkkit.get());
                pl.removeMetadata(lore_confirm_metadata, (Plugin)Arkkit.get());
            }
        }

        @EventHandler
        public void onInventoryClick(InventoryClickEvent e) {
            Player pl = (Player)e.getWhoClicked();
            if (pl.hasMetadata(lore_confirm_metadata)) {
                pl.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "** " + (Object)ChatColor.RED + "Lore Crystal cancelled because you clicked an item.");
                if (pl.getInventory().firstEmpty() != -1) {
                    pl.getInventory().addItem(new ItemStack[]{LoreTag.getLoreTagItem()});
                } else {
                    pl.sendMessage((Object)ChatColor.RED + "(!) You had no room in your inventory, the Lore Crystal has been dropped at your feet.");
                    pl.getWorld().dropItem(pl.getLocation(), LoreTag.getLoreTagItem());
                }
                pl.removeMetadata(lore_confirm_metadata, (Plugin)Arkkit.get());
                pl.removeMetadata(pending_lore_name, (Plugin)Arkkit.get());
            }
        }

        @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
        public void onPlayerPickupItem(PlayerPickupItemEvent e) {
            Player pl = e.getPlayer();
            if (pl.hasMetadata(lore_confirm_metadata)) {
                pl.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "** " + (Object)ChatColor.RED + "Lore Crystal cancelled because you picked up an item.");
                if (pl.getInventory().firstEmpty() != -1) {
                    pl.getInventory().addItem(new ItemStack[]{LoreTag.getLoreTagItem()});
                } else {
                    pl.sendMessage((Object)ChatColor.RED + "(!) You had no room in your inventory, the Lore Crystal has been dropped at your feet.");
                    pl.getWorld().dropItem(pl.getLocation(), LoreTag.getLoreTagItem());
                }
                pl.removeMetadata(lore_confirm_metadata, (Plugin)Arkkit.get());
                pl.removeMetadata(pending_lore_name, (Plugin)Arkkit.get());
            }
        }

        @EventHandler(priority=EventPriority.LOWEST)
        public void onPlayerDropItem(PlayerDropItemEvent e) {
            Player pl = e.getPlayer();
            if (pl.hasMetadata(lore_confirm_metadata)) {
                pl.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "** " + (Object)ChatColor.RED + "Lore Crystal cancelled because you dropped an item.");
                if (pl.getInventory().firstEmpty() != -1) {
                    pl.getInventory().addItem(new ItemStack[]{LoreTag.getLoreTagItem()});
                } else {
                    pl.sendMessage((Object)ChatColor.RED + "(!) You had no room in your inventory, the Lore Crystal has been dropped at your feet.");
                    pl.getWorld().dropItem(pl.getLocation(), LoreTag.getLoreTagItem());
                }
                pl.removeMetadata(lore_confirm_metadata, (Plugin)Arkkit.get());
                pl.removeMetadata(pending_lore_name, (Plugin)Arkkit.get());
            }
        }

        @EventHandler(priority=EventPriority.LOWEST)
        public void onPlayerItemHeldEvent(PlayerItemHeldEvent e) {
            Player pl = e.getPlayer();
            if (pl.hasMetadata(lore_confirm_metadata)) {
                pl.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "** " + (Object)ChatColor.RED + "Lore Crystal cancelled because you changed your held item.");
                if (pl.getInventory().firstEmpty() != -1) {
                    pl.getInventory().addItem(new ItemStack[]{LoreTag.getLoreTagItem()});
                } else {
                    pl.sendMessage((Object)ChatColor.RED + "(!) You had no room in your inventory, the Lore Crystal has been dropped at your feet.");
                    pl.getWorld().dropItem(pl.getLocation(), LoreTag.getLoreTagItem());
                }
                pl.removeMetadata(lore_confirm_metadata, (Plugin)Arkkit.get());
                pl.removeMetadata(pending_lore_name, (Plugin)Arkkit.get());
            }
        }

        @EventHandler
        public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
            if (e.getPlayer().getItemInHand() != null && LoreTag.isLoreTag(e.getPlayer().getItemInHand())) {
                e.setCancelled(true);
            }
        }
    }
}

