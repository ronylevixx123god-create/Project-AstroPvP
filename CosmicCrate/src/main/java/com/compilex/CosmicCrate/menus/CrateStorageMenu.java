// 
// Decompiled by Procyon v0.5.36
// 

package com.compilex.CosmicCrate.menus;

import com.compilex.CosmicCrate.CosmicCrate;
import org.bukkit.inventory.ItemStack;
import com.cosmicpvp.cosmicutils.gui.InventoryItem;
import com.compilex.CosmicCrate.utils.LocalItemUtils;
import org.bukkit.Sound;
import com.compilex.CosmicCrate.api.CCAPI;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.concurrent.atomic.AtomicInteger;
import org.bukkit.entity.Player;
import com.cosmicpvp.cosmicutils.gui.CosmicGUI;

public class CrateStorageMenu extends CosmicGUI
{
    public CrateStorageMenu(final Player player) {
        super(player, "Astro Crate Storage", 27);
    }
    
    public void redraw() {
        final AtomicInteger slot = new AtomicInteger(0);
        AtomicInteger startIndex = new AtomicInteger(18);

        CosmicCrate.get().getCosmicCrateManager().getCosmicCrates().forEach((string, crate) -> {
            this.setItem(startIndex.getAndIncrement(), CosmicCrate.get().getCosmicCrateManager().getDisplayItem(player, string), () -> {
                if (!CosmicCrate.get().getCosmicCrateManager().hasPermission(this.player, string)) {
                    this.player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "You do not have access to this Astro Crate! Purchase it (when available) at " + ChatColor.UNDERLINE + "buy.astropvp.net");
                    return;
                }
                else {

                    if (this.player.getInventory().firstEmpty() != -1) {
                        CCAPI.claimCosmicCrate(this.player, string);
                    }
                    else {
                        this.player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "Your inventory is full!");
                        this.player.playSound(this.player.getLocation(), Sound.VILLAGER_NO, 1.0f, 0.6f);
                    }
                    this.closeWithDelay();
                    return;
                }
            });
        });

        for (int i = 0; i < this.size; ++i) {
            final ItemStack item = this.inventory.getItem(i);
            if (item == null || item.getType() == Material.AIR) {
                this.setItem(i, new InventoryItem(LocalItemUtils.getMysteryPlaceholder()));
            }
        }
    }
}
