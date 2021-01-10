/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.gui.CosmicGUI
 *  com.cosmicpvp.cosmicutils.gui.InventoryItem
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.ItemBuilder
 *  com.google.common.collect.Lists
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.compilex.CosmicCrate.menus;

import com.compilex.CosmicCrate.CosmicCrate;
import com.compilex.CosmicCrate.api.CCAPI;
import com.compilex.CosmicCrate.libs.CrateCategory;
import com.compilex.CosmicCrate.libs.Version;
import com.compilex.CosmicCrate.menus.CrateCategoryMenu;
import com.compilex.CosmicCrate.utils.LocalItemUtils;
import com.cosmicpvp.cosmicutils.gui.CosmicGUI;
import com.cosmicpvp.cosmicutils.gui.InventoryItem;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class CrateStorageMenu
extends CosmicGUI {
    public CrateStorageMenu(Player player) {
        super(player, "Cosmic Crate Storage", 27);
    }

    public void redraw() {
        AtomicInteger slot = new AtomicInteger(0);
        for (CrateCategory category : CrateCategory.values()) {
            if (category == CrateCategory.CC_2019) continue;
            ArrayList lore = Lists.newArrayList((Object[])new String[]{""});
            lore.add((Object)category.getColor() + CC.Bold + category.getYear() + " Crates:");
            category.getCrates().forEach(v -> lore.add((Object)category.getColor() + CC.Bold + " * " + CC.White + v.crateName));
            lore.add("");
            lore.add(CC.Gray + "Click to view Cosmic Crates from " + CC.GrayU + category.getYear() + CC.Gray + "!");
            this.setItem(slot.getAndIncrement(), new ItemBuilder(Material.ENDER_CHEST, category.getDisplayName(), new String[0]).lore((List)lore).build(), () -> new CrateCategoryMenu(this.player, category).setParentGUI(this).openGUI((JavaPlugin)CosmicCrate.get()));
        }
        int startIndex = 18;
        for (Version version : CrateCategory.CC_2019.getCrates()) {
            this.setItem(startIndex++, version.getDisplayItem(this.player), () -> {
                if (!version.hasPermission(this.player)) {
                    this.player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "You do not have access to this Cosmic Crate! Purchase it (when available) at " + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com");
                    return;
                }
                if (CCAPI.hasClaimedCosmicCrate(this.player, version)) {
                    this.player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "You already claimed the " + ChatColor.UNDERLINE.toString() + ChatColor.BOLD.toString() + version.name() + (Object)ChatColor.RED + " Cosmic Crate!");
                } else {
                    if (this.player.getInventory().firstEmpty() != -1) {
                        CCAPI.claimCosmicCrate(this.player, version);
                    } else {
                        this.player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "Your inventory is full!");
                        this.player.playSound(this.player.getLocation(), Sound.VILLAGER_NO, 1.0f, 0.6f);
                    }
                    this.closeWithDelay();
                }
            });
        }
        for (int i = 0; i < this.size; ++i) {
            ItemStack item = this.inventory.getItem(i);
            if (item != null && item.getType() != Material.AIR) continue;
            this.setItem(i, new InventoryItem(LocalItemUtils.getMysteryPlaceholder()));
        }
    }
}

