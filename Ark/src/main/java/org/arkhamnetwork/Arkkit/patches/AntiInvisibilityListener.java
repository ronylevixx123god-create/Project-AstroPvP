/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.BrewEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.PotionMeta
 *  org.bukkit.potion.PotionEffectType
 */
package org.arkhamnetwork.Arkkit.patches;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffectType;

final class AntiInvisibilityListener
implements Listener {
    AntiInvisibilityListener() {
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=false)
    public void onBrewEvent(BrewEvent e) {
        boolean night_vision = false;
        boolean fermented_spider_eye = false;
        for (ItemStack is : e.getContents().getContents()) {
            PotionMeta pm;
            if (is == null) continue;
            if (is.getType() == Material.FERMENTED_SPIDER_EYE) {
                fermented_spider_eye = true;
                continue;
            }
            if (is.getType() != Material.POTION || !(pm = (PotionMeta)is.getItemMeta()).hasCustomEffect(PotionEffectType.NIGHT_VISION) && !this.isNightVisionPotion(is)) continue;
            night_vision = true;
        }
        if (e.getContents().getIngredient() != null && e.getContents().getIngredient().getType() == Material.FERMENTED_SPIDER_EYE) {
            fermented_spider_eye = true;
        }
        if (e.getContents().getIngredient() != null && e.getContents().getIngredient().getType() == Material.POTION && (((PotionMeta)e.getContents().getIngredient().getItemMeta()).hasCustomEffect(PotionEffectType.NIGHT_VISION) || this.isNightVisionPotion(e.getContents().getIngredient()))) {
            night_vision = true;
        }
        if (night_vision && fermented_spider_eye) {
            e.setCancelled(true);
            for (HumanEntity he : e.getContents().getViewers()) {
                if (!(he instanceof Player)) continue;
                ((Player)he).sendMessage((Object)ChatColor.RED + "You " + (Object)ChatColor.UNDERLINE + "cannot" + (Object)ChatColor.RED + " brew invisibility potions.");
                ((Player)he).closeInventory();
            }
        }
    }

    private boolean isNightVisionPotion(ItemStack is) {
        short dura;
        return is.getType() == Material.POTION && ((dura = is.getDurability()) == 8198 || dura == 8230 || dura == 8262 || dura == 16390 || dura == 16422 || dura == 16454);
    }
}

