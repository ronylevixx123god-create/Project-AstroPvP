/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.gui.CosmicGUI
 *  com.cosmicpvp.cosmicutils.gui.InventoryItem
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.InventoryUtils
 *  com.cosmicpvp.cosmicutils.utils.ItemBuilder
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.gui.CosmicGUI;
import com.cosmicpvp.cosmicutils.gui.InventoryItem;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.InventoryUtils;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.List;

class CandyGramClaimGUI
        extends CosmicGUI {
    public CandyGramClaimGUI(Player player, List<CandyGram> toClaim) {
        super(player, "Your Candy Grams", InventoryUtils.fitSize(toClaim.size()));
    }

    public void redraw() {
        this.inventory.clear();
        this.getInventoryItems().clear();
        ValentinesDay plugin = (ValentinesDay) ArkPlugins.VALENTINES_DAY.getArkPlugin();
        List<CandyGram> grams = plugin.pendingGrams.get(this.player.getUniqueId());
        if (grams != null) {
            int slot = 0;
            for (CandyGram gram : grams) {
                if (slot >= this.size) break;
                this.setItem(slot++, new InventoryItem(new ItemBuilder(gram.getItemStack()).addLoreLine("", CC.Gray + "Click to claim Candy Gram!").build()).click(() -> {
                    if (!grams.contains(gram)) {
                        return;
                    }
                    if (this.player.getInventory().firstEmpty() == -1) {
                        this.player.sendMessage(CC.Red + "You do not have space for this Candy Gram!");
                        return;
                    }
                    grams.remove(gram);
                    this.player.getInventory().addItem(gram.getItemStack());
                    this.player.updateInventory();
                    this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 0.4f, 1.4f);
                    ParticleEffect.sendToLocation(ParticleEffect.HEART, this.player.getLocation().add(0.0, 1.0, 0.0), 1.0f, 1.0f, 1.0f, 0.1f, 20);
                    if (grams.isEmpty()) {
                        plugin.pendingGrams.remove(this.player.getUniqueId());
                    }
                    this.redraw();
                }));
            }
        }
    }
}

