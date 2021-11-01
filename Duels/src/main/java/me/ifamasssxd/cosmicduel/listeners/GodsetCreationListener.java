/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.rit.sucy.CustomEnchantment
 *  com.rit.sucy.EnchantmentAPI
 *  com.rit.sucy.service.ERomanNumeral
 *  org.arkhamnetwork.enchantments.util.TieredEnchantments
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.me.astropvp.cratekeys.inventory.ClickType
 *  org.bukkit.event.me.astropvp.cratekeys.inventory.InventoryClickEvent
 *  org.bukkit.event.me.astropvp.cratekeys.inventory.InventoryCloseEvent
 *  org.bukkit.me.astropvp.cratekeys.inventory.ItemStack
 *  org.bukkit.me.astropvp.cratekeys.inventory.meta.ItemMeta
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package me.ifamasssxd.cosmicduel.listeners;

import com.google.common.collect.Lists;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import com.rit.sucy.service.ERomanNumeral;
import me.ifamasssxd.cosmicduel.CosmicDuel;
import me.ifamasssxd.cosmicduel.godset.CustomGodSet;
import me.ifamasssxd.cosmicduel.godset.EditState;
import me.ifamasssxd.cosmicduel.godset.EnchantSlots;
import me.ifamasssxd.cosmicduel.struct.DuelPlayer;
import me.ifamasssxd.cosmicduel.utils.DuelUtils;
import me.ifamasssxd.cosmicduel.utils.EnchantUtils;
import me.ifamasssxd.cosmicduel.utils.StringUtils;
import org.arkhamnetwork.enchantments.util.TieredEnchantments;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class GodsetCreationListener
implements Listener {
    public static String BOOK_RARITY_INVENTORY_NAME = "Duel Enchantments";

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onPlayerInventoryClick(InventoryClickEvent event) {
        Player player = (Player)event.getWhoClicked();
        ItemStack item = event.getCurrentItem();
        if (event.getInventory().getName().equals("Duel God Set")) {
            event.setCancelled(true);
            event.setResult(Event.Result.DENY);
            if (item == null) {
                return;
            }
            if (item.getType() == Material.AIR) {
                return;
            }
            if (event.getRawSlot() + 1 > event.getInventory().getSize()) {
                return;
            }
            if (!DuelUtils.isValidRankedMaterial(item)) {
                return;
            }
            DuelPlayer duelPlayer = DuelPlayer.getDuelPlayer(player);
            if (duelPlayer == null) {
                return;
            }
            CustomGodSet godSet = duelPlayer.getCustomGodSet();
            if (godSet == null) {
                player.closeInventory();
                return;
            }
            if (event.getClick() != ClickType.MIDDLE) {
                godSet.setMaterialEditting(item.getType());
                player.setMetadata("exemptEdit", (MetadataValue)new FixedMetadataValue((Plugin)CosmicDuel.get(), (Object)""));
                godSet.setEditState(EditState.CATEGORIES);
                player.openInventory(godSet.getBookRarityMenu());
                return;
            }
            int maxSlots = godSet.getMaxSlots(item.getType());
            int usedSlots = godSet.getSlotsUsed(item.getType());
            if (usedSlots <= 0) {
                player.playSound(player.getLocation(), Sound.FIZZ, 1.0f, 1.1f);
                player.sendMessage((Object)ChatColor.RED + "This item does not have any Custom Enchants.");
                return;
            }
            godSet.getCustomEnchants().put(item.getType(), Lists.newArrayList());
            ItemMeta im = item.getItemMeta();
            ArrayList lore = Lists.newArrayList();
            for (int i = 0; i < maxSlots; ++i) {
                lore.add((Object)ChatColor.GRAY + ChatColor.ITALIC.toString() + "<EMPTY>");
            }
            if (maxSlots > 0) {
                lore.add("");
                lore.add((Object)ChatColor.GREEN + ChatColor.BOLD.toString() + maxSlots + (Object)ChatColor.GREEN + " Enchant Slots Left");
                lore.add("");
                lore.add((Object)ChatColor.GRAY + "Left-Click to add enchants.");
                lore.add((Object)ChatColor.GRAY + "Middle-Click to clear custom enchants.");
                im.setLore((List)lore);
                event.getCurrentItem().setItemMeta(im);
            }
            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0f, 0.5f);
            player.sendMessage((Object)ChatColor.AQUA + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.AQUA + "Enchantments cleared for " + StringUtils.getFormattedName(item));
            return;
        }
        if (event.getInventory().getName().equals(BOOK_RARITY_INVENTORY_NAME)) {
            event.setCancelled(true);
            event.setResult(Event.Result.DENY);
            if (item == null) {
                return;
            }
            if (item.getType() == Material.AIR) {
                return;
            }
            if (!item.hasItemMeta()) {
                return;
            }
            if (!item.getItemMeta().hasDisplayName()) {
                return;
            }
            if (event.getRawSlot() + 1 > event.getInventory().getSize()) {
                return;
            }
            DuelPlayer duelPlayer = DuelPlayer.getDuelPlayer(player);
            if (duelPlayer == null) {
                return;
            }
            CustomGodSet godSet = duelPlayer.getCustomGodSet();
            if (godSet == null || godSet.getMaterialEditting() == null) {
                player.closeInventory();
                return;
            }
            ItemMeta im = item.getItemMeta();
            if (!im.getDisplayName().contains(" Enchantments")) {
                return;
            }
            String enchantTypeName = im.getDisplayName().split(" Enchantments")[0];
            EnchantSlots rarity = EnchantSlots.fromName(ChatColor.stripColor((String)enchantTypeName));
            if (rarity == null) {
                return;
            }
            player.setMetadata("exemptEdit", (MetadataValue)new FixedMetadataValue((Plugin)CosmicDuel.get(), (Object)""));
            godSet.setEditState(EditState.ENCHANTS);
            player.openInventory(godSet.getEnchantmentInventory(rarity, godSet.getMaterialEditting()));
            return;
        }
        if (!event.getInventory().getName().equals("Duel Enchants")) {
            return;
        }
        event.setCancelled(true);
        event.setResult(Event.Result.DENY);
        if (item == null) {
            return;
        }
        if (item.getType() == Material.AIR) {
            return;
        }
        if (!item.hasItemMeta()) {
            return;
        }
        if (!item.getItemMeta().hasDisplayName()) {
            return;
        }
        DuelPlayer duelPlayer = DuelPlayer.getDuelPlayer(player);
        if (duelPlayer == null) {
            return;
        }
        CustomGodSet godSet = duelPlayer.getCustomGodSet();
        if (godSet == null || godSet.getMaterialEditting() == null) {
            player.closeInventory();
            return;
        }
        String name = ChatColor.stripColor((String)item.getItemMeta().getDisplayName());
        CustomEnchantment encht = EnchantmentAPI.getEnchantment((String)(name = name.substring(0, name.lastIndexOf(" "))));
        if (encht == null) {
            Bukkit.getLogger().info("Unable to get enchant for " + name);
            return;
        }
        if (!encht.canEnchantOnto(new ItemStack(godSet.getMaterialEditting()))) {
            return;
        }
        List<CustomEnchantment> currentEnchants = godSet.getCurrentEnchants(godSet.getMaterialEditting());
        EnchantSlots slots = EnchantSlots.getEnchantRarity(TieredEnchantments.getEnchantmentTier((String)encht.name()));
        int slotsUsed = godSet.getSlotsUsed(godSet.getMaterialEditting());
        int maxSlots = godSet.getMaxSlots(godSet.getMaterialEditting());
        if (currentEnchants.contains((Object)encht)) {
            item.removeEnchantment(EnchantUtils.getCustomEnchant());
            godSet.getCustomEnchants(godSet.getMaterialEditting()).remove(encht.name());
            player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0f, 0.8f);
            godSet.setChanged(true);
            if (slots == null) {
                return;
            }
            int slotsLeft = maxSlots - slotsUsed;
            player.sendMessage((Object)slots.getNameColor() + ChatColor.BOLD.toString() + "- " + (Object)slots.getNameColor() + encht.name() + " " + ERomanNumeral.numeralOf((int)encht.getMaxLevel()) + " [" + StringUtils.getFormattedName(godSet.getMaterialEditting()) + "] (" + (slotsLeft += slots.getSlotCost()) + " slots left)");
            return;
        }
        if (slots == null) {
            return;
        }
        if (maxSlots > 0 && slotsUsed + slots.getSlotCost() > maxSlots) {
            player.sendMessage((Object)ChatColor.RED + "You do not have enough enchant points left on this item.");
            player.sendMessage((Object)ChatColor.GRAY + "Enchant Slots Left: " + (maxSlots - slotsUsed));
            return;
        }
        item.addUnsafeEnchantment(EnchantUtils.getCustomEnchant(), 1);
        godSet.getCustomEnchants(godSet.getMaterialEditting()).add(encht.name());
        int slotsLeft = maxSlots - slotsUsed;
        player.sendMessage((Object)slots.getNameColor() + ChatColor.BOLD.toString() + "+ " + (Object)slots.getNameColor() + encht.name() + " " + ERomanNumeral.numeralOf((int)encht.getMaxLevel()) + " [" + StringUtils.getFormattedName(godSet.getMaterialEditting()) + "] (" + (slotsLeft -= slots.getSlotCost()) + " slots left)");
        player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0f, 1.4f);
        godSet.setChanged(true);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player)event.getPlayer();
        if (player.hasMetadata("exemptEdit")) {
            player.removeMetadata("exemptEdit", (Plugin)CosmicDuel.get());
            return;
        }
        DuelPlayer duelPlayer = DuelPlayer.getDuelPlayer(player);
        if (duelPlayer == null) {
            return;
        }
        if (duelPlayer.getCustomGodSet() == null) {
            return;
        }
        CustomGodSet godset = duelPlayer.getCustomGodSet();
        if (godset.getMaterialEditting() == null) {
            return;
        }
        if (godset.getEditState() == EditState.ENCHANTS) {
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)CosmicDuel.get(), () -> {
                godset.setEditState(EditState.CATEGORIES);
                player.openInventory(godset.getBookRarityMenu());
            }, 1L);
            return;
        }
        if (godset.getEditState() == EditState.CATEGORIES) {
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)CosmicDuel.get(), () -> {
                godset.setEditState(null);
                player.openInventory(godset.getEditInventory());
            }, 1L);
            return;
        }
        if (godset.getMaterialEditting() == null) {
            return;
        }
        if (godset.isChanged()) {
            player.sendMessage((Object)ChatColor.GREEN + "Duel Godset changes saved.");
        }
        godset.setMaterialEditting(null);
    }
}

