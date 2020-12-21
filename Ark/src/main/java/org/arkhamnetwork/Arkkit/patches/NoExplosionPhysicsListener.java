/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.massivecraft.factions.Board
 *  com.massivecraft.factions.FLocation
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.entity.Creeper
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.BlockPhysicsEvent
 *  org.bukkit.event.entity.EntityExplodeEvent
 */
package org.arkhamnetwork.Arkkit.patches;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.arkhamnetwork.Arkkit.utils.WorldGuardUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

final class NoExplosionPhysicsListener
implements Listener {
    NoExplosionPhysicsListener() {
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onEntityExplode(EntityExplodeEvent e) {
        if (e.getEntity() instanceof Creeper && WorldGuardUtils.getRegionName(e.getEntity().getLocation()).contains("warzone") && Board.getFactionAt((FLocation)new FLocation(e.getEntity().getLocation())).isWarZone()) {
            Creeper c = (Creeper)e.getEntity();
            int radius = c.isPowered() ? 6 : 3;
            int damage = c.isPowered() ? 12 : 6;
            e.setCancelled(true);
            for (Entity ent : e.getEntity().getNearbyEntities((double)radius, (double)radius, (double)radius)) {
                if (!(ent instanceof LivingEntity)) continue;
                ((LivingEntity)ent).damage((double)damage, (Entity)c);
            }
            c.getWorld().playSound(c.getLocation(), Sound.EXPLODE, 1.0f, 1.0f);
            ParticleEffect.playEffect(ParticleEffect.EXPLODE, c.getLocation().add(0.0, 1.0, 0.0), 1.0f, 6);
            c.remove();
        }
    }

    @EventHandler
    public void onBlockPhysics(BlockPhysicsEvent e) {
        if (e.getBlock().getType() == Material.AIR && WorldGuardUtils.getRegionName(e.getBlock().getLocation()).contains("warzone") && Board.getFactionAt((FLocation)new FLocation(e.getBlock().getLocation())).isWarZone()) {
            e.setCancelled(true);
        }
    }
}

