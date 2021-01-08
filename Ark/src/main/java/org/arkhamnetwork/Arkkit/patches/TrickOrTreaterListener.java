/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.itembuilder.MaskBuilder
 *  org.bukkit.Bukkit
 *  org.bukkit.Sound
 *  org.bukkit.entity.Entity
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.inventory.ItemStack
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.itembuilder.MaskBuilder;
import org.arkhamnetwork.Arkkit.libs.ArkPluginListener;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

final class TrickOrTreaterListener
        extends ArkPluginListener<TrickOrTreaters> {
    public TrickOrTreaterListener(TrickOrTreaters patch) {
        super(patch);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (this.patch.isTrickOrTreater(e.getEntity())) {
            e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.GHAST_MOAN, 1.0f, 1.1f);
            e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.ZOMBIE_IDLE, 1.0f, 1.3f);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDeath(EntityDeathEvent e) {
        if (this.patch.isTrickOrTreater(e.getEntity())) {
            ItemStack mask;
            e.getDrops().clear();
            e.getDrops().add(Halloween.getHalloweenCandy(ThreadLocalRandom.current().nextInt(20) + 10));
            if (Math.random() <= 0.01 && Bukkit.getPluginManager().isPluginEnabled("CosmicMasks") && (mask = new MaskBuilder("PUMPKIN_MONSTER").build()) != null) {
                e.getDrops().add(mask);
                Bukkit.getLogger().info("[TrickOrTreater] Dropping Halloween mask at " + e.getEntity().getLocation() + "!");
            }
        }
    }
}

