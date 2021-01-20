/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 */
package org.arkhamnetwork.Arkkit.patches;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

final class GoldenKnifeListener
        implements Listener {
    GoldenKnifeListener() {
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) throws Exception {
        LivingEntity le;
        if (e.getDamager() instanceof LivingEntity && GoldenKnife.isGoldenKnife((le = (LivingEntity) e.getDamager()).getEquipment().getItemInHand())) {
            le.getEquipment().setItemInHand(null);
            le.getWorld().playSound(le.getLocation(), Sound.ITEM_BREAK, 1.0f, 1.0f);
            le.getWorld().playSound(le.getLocation(), Sound.ENDERMAN_SCREAM, 1.2f, 0.1f);
            ((LivingEntity) e.getEntity()).setHealth(0.0);
            //ParticleEffect.playEffect(ParticleEffect.FIREWORKS_SPARK, e.getEntity().getLocation(), 0.4f, 50);
            de.inventivegames.particle.ParticleEffect.PORTAL.sendToPlayer((Player) e.getEntity(), e.getEntity().getLocation(), 0, 0, 0, 0.7f, 16);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.hasItem() && GoldenKnife.isGoldenKnife(e.getItem()) && e.hasBlock() && (e.getClickedBlock().getType() == Material.GOLD_BLOCK || e.getClickedBlock().getType() == Material.IRON_BLOCK)) {
            e.setCancelled(true);
        }
    }
}

