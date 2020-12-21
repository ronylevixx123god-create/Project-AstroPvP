/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageEvent
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.arkhamnetwork.Arkkit.utils.WorldGuardUtils;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

final class NoSafezoneMobsListener
implements Listener {
    NoSafezoneMobsListener() {
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof LivingEntity && e.getEntityType() != EntityType.PLAYER && WorldGuardUtils.isPvPDisabled(e.getEntity().getLocation())) {
            ParticleEffect.playEffect(ParticleEffect.FIREWORKS_SPARK, e.getEntity().getLocation(), 0.65f, 20);
            e.setCancelled(true);
            e.setDamage(0.0);
            e.getEntity().remove();
        }
    }
}

