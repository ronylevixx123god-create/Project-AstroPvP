// 
// Decompiled by Procyon v0.5.36
// 

package com.compilex.CosmicCrate.menus;

import org.bukkit.inventory.ItemStack;
import java.util.Iterator;
import com.cosmicpvp.cosmicutils.gui.InventoryItem;
import com.compilex.CosmicCrate.utils.LocalItemUtils;
import org.bukkit.Sound;
import com.compilex.CosmicCrate.api.CCAPI;
import org.bukkit.ChatColor;
import com.compilex.CosmicCrate.libs.Version;
import org.bukkit.plugin.java.JavaPlugin;
import com.compilex.CosmicCrate.CosmicCrate;
import java.util.List;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import org.bukkit.Material;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import com.compilex.CosmicCrate.libs.CrateCategory;
import java.util.concurrent.atomic.AtomicInteger;
import org.bukkit.entity.Player;
import com.cosmicpvp.cosmicutils.gui.CosmicGUI;

public class CrateStorageMenu extends CosmicGUI
{
    public CrateStorageMenu(final Player player) {
        super(player, "Cosmic Crate Storage", 27);
    }
    
    public void redraw() {
        final AtomicInteger slot = new AtomicInteger(0);
        for (final CrateCategory category : CrateCategory.values()) {
            if (category != CrateCategory.CC_2019) {
                final List<String> lore = (List<String>)Lists.newArrayList((Object[])new String[] { "" });
                lore.add(category.getColor() + CC.Bold + category.getYear() + " Crates:");
                category.getCrates().forEach(v -> lore.add(category.getColor() + CC.Bold + " * " + CC.White + v.crateName));
                lore.add("");
                lore.add(CC.Gray + "Click to view Cosmic Crates from " + CC.GrayU + category.getYear() + CC.Gray + "!");
                this.setItem(slot.getAndIncrement(), new ItemBuilder(Material.ENDER_CHEST, category.getDisplayName(), new String[0]).lore((List)lore).build(), () -> new CrateCategoryMenu(this.player, category).setParentGUI((CosmicGUI)this).openGUI((JavaPlugin)CosmicCrate.get()));
            }
        }
        int startIndex = 18;
        for (final Version version : CrateCategory.CC_2019.getCrates()) {
            final Version version2;
            this.setItem(startIndex++, version.getDisplayItem(this.player), () -> {
                if (!version2.hasPermission(this.player)) {
                    this.player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "You do not have access to this Cosmic Crate! Purchase it (when available) at " + ChatColor.UNDERLINE + "buy.cosmicpvp.com");
                    return;
                }
                else {
                    if (CCAPI.hasClaimedCosmicCrate(this.player, version2)) {
                        this.player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "You already claimed the " + ChatColor.UNDERLINE.toString() + ChatColor.BOLD.toString() + version2.name() + ChatColor.RED + " Cosmic Crate!");
                    }
                    else {
                        if (this.player.getInventory().firstEmpty() != -1) {
                            CCAPI.claimCosmicCrate(this.player, version2);
                        }
                        else {
                            this.player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "Your inventory is full!");
                            this.player.playSound(this.player.getLocation(), Sound.VILLAGER_NO, 1.0f, 0.6f);
                        }
                        this.closeWithDelay();
                    }
                    return;
                }
            });
        }
        for (int i = 0; i < this.size; ++i) {
            final ItemStack item = this.inventory.getItem(i);
            if (item == null || item.getType() == Material.AIR) {
                this.setItem(i, new InventoryItem(LocalItemUtils.getMysteryPlaceholder()));
            }
        }
    }
}
