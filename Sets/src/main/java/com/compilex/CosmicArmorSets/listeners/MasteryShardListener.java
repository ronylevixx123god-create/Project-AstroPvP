/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder
 *  com.cosmicpvp.cosmicutils.nbt.NBTTagListBuilder
 *  com.cosmicpvp.cosmicutils.net.astropvp.armorsets.utils.CC
 *  com.cosmicpvp.cosmicutils.net.astropvp.armorsets.utils.ItemUtils
 *  com.cosmicpvp.cosmicutils.net.astropvp.armorsets.utils.NBTWrapper
 *  com.google.common.collect.Lists
 *  net.minecraft.server.v1_7_R4.NBTBase
 *  org.arkhamnetwork.enchantments.EnchantmentBook
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package com.compilex.CosmicArmorSets.listeners;

import com.compilex.CosmicArmorSets.enums.AbilityArmorSet;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.nbt.NBTTagListBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.google.common.collect.Lists;
import net.minecraft.server.v1_8_R3.NBTBase;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class MasteryShardListener
        implements Listener {
    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();
        if (clicked == null || clicked.getType() != Material.BOOK) {
            return;
        }
        ItemStack cursor = event.getCursor();
        if (cursor == null || cursor.getType() == Material.AIR) {
            return;
        }
        if (event.getWhoClicked().getInventory() == null || event.getWhoClicked().getOpenInventory() == null || !event.getWhoClicked().getInventory().equals(event.getWhoClicked().getOpenInventory().getBottomInventory())) {
            return;
        }
        if (event.getWhoClicked().getWorld().getName().startsWith("world_duels")) {
            return;
        }
        NBTWrapper cursorWrapper = new NBTWrapper(cursor);
        if (cursorWrapper.getString("cosmicType").equals("MASTERY_SHARD") && EnchantmentBook.isEnchantmentBook(clicked)) {
            AbilityArmorSet set;
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            NBTTagCompoundBuilder builder = cursorWrapper.getCompound("conaxData");
            if (builder != null && builder.hasKey("masteryShard") && (set = AbilityArmorSet.fromName(builder.getString("masteryShard"))) != null) {
                NBTTagListBuilder shardList;
                NBTWrapper clickedWrapper = new NBTWrapper(clicked);
                NBTTagCompoundBuilder clickedCosmicData = clickedWrapper.getCompoundOrCreate("conaxData");
                NBTTagListBuilder nBTTagListBuilder = shardList = !clickedCosmicData.hasKey("masteryShard") ? new NBTTagListBuilder() : new NBTTagListBuilder(clickedCosmicData.getNBTBase("masteryShard"));
                if (shardList.contains(set.name())) {
                    player.sendMessage(CC.RedB + "(!) " + CC.Red + "This book already has this Mastery Shard equipped!");
                    return;
                }
                shardList.addString(set.name());
                clickedCosmicData.setNBTBase("masteryShard", shardList.build());
                clickedWrapper.setNBTBase("conaxData", clickedCosmicData.get());
                ItemStack newBook = clickedWrapper.build();
                ItemMeta im = newBook.getItemMeta();
                List<String> lore = im.getLore() == null ? Lists.newArrayList() : im.getLore();
                lore.add(CC.GoldB + "* Mastery Shard " + CC.Gold + "(" + set.getDisplayName() + CC.Gold + ")");
                im.setLore(lore);
                newBook.setItemMeta(im);
                player.setItemOnCursor(null);
                event.setCurrentItem(newBook);
                player.sendMessage(CC.GoldB + "(!) " + CC.GoldU + ChatColor.stripColor(set.getDisplayName()) + " Mastery Shard" + CC.Gold + " applied to your " + ItemUtils.getDisplayName(clicked) + CC.Gold + "!");
                player.sendMessage(CC.Gray + "This book is now powerful enough to attempt to apply to the " + set.getDisplayName() + CC.Gray + " /mkit items!");
                player.playSound(player.getLocation(), Sound.BAT_TAKEOFF, 1.0f, 1.1f);
                return;
            }
        }
    }
}

