/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.gui.CosmicGUI
 *  com.cosmicpvp.cosmicutils.gui.InventoryItem
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.InventoryUtils
 *  com.cosmicpvp.cosmicutils.utils.ItemBuilder
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
import com.compilex.CosmicCrate.utils.LocalItemUtils;
import com.cosmicpvp.cosmicutils.gui.CosmicGUI;
import com.cosmicpvp.cosmicutils.gui.InventoryItem;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.InventoryUtils;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class CrateCategoryMenu
extends CosmicGUI {
    private CrateCategory category;

    public CrateCategoryMenu(Player player, CrateCategory category) {
        super(player, category.getYear() + " Cosmic Crates", InventoryUtils.fitSize((int)(category.getCrates().size() + 1)));
        this.category = category;
    }

    public void redraw() {
        AtomicInteger slot = new AtomicInteger(0);
        Arrays.stream(Version.values()).filter(ver -> ver.getCategory() == this.category).forEach(ver -> this.setItem(slot.getAndIncrement(), new ItemBuilder(ver.getDisplayItem(this.player)).build(), () -> {
            if (!ver.hasPermission(this.player)) {
                this.player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "You do not have access to this Cosmic Crate! Purchase it (when available) at " + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com");
                return;
            }
            if (CCAPI.hasClaimedCosmicCrate(this.player, ver)) {
                this.player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "You already claimed the " + ChatColor.UNDERLINE.toString() + ChatColor.BOLD.toString() + ver.name() + (Object)ChatColor.RED + " Cosmic Crate!");
            } else if (this.player.getInventory().firstEmpty() != -1) {
                CCAPI.claimCosmicCrate(this.player, ver);
            } else {
                this.player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "Your inventory is full!");
                this.player.playSound(this.player.getLocation(), Sound.VILLAGER_NO, 1.0f, 0.6f);
            }
            this.closeWithDelay();
        }));
        this.setItem(this.size - 1, new ItemBuilder(Material.ARROW, CC.Red + "Main Menu", new String[]{CC.Gray + "Click to view main Cosmic Crate category!"}).build(), () -> {
            this.parentGUI.openGUI((JavaPlugin)CosmicCrate.get());
            this.parentGUI.redraw();
        });
        for (int i = 0; i < this.size; ++i) {
            ItemStack item = this.inventory.getItem(i);
            if (item != null && item.getType() != Material.AIR) continue;
            this.setItem(i, new InventoryItem(LocalItemUtils.getMysteryPlaceholder()));
        }
    }
}

