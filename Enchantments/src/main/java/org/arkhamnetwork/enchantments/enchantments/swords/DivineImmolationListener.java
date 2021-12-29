/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.metadata.MetadataValue
 */
package org.arkhamnetwork.enchantments.enchantments.swords;

import de.inventivegames.particle.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.MetadataValue;

public class DivineImmolationListener
implements Listener {
    public DivineImmolationListener() {
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getEntity().hasMetadata("divineFire") && e.getCause() == EntityDamageEvent.DamageCause.WITHER && e.getEntity() instanceof LivingEntity) {
            int enchantLevel = ((MetadataValue)e.getEntity().getMetadata("divineFireLevel").get(0)).asInt();
            if (System.currentTimeMillis() - ((MetadataValue)e.getEntity().getMetadata("divineFire").get(0)).asLong() < (long)((4 + enchantLevel) * 1000)) {
                e.setCancelled(true);
                LivingEntity le = (LivingEntity)e.getEntity();
                le.damage((double)Math.min(7, 4 + enchantLevel));

                try {
                    ParticleEffect.FLAME.sendToPlayers(Bukkit.getOnlinePlayers(), le.getEyeLocation(), 0.15f, 0.15f, 0.15f, 0.1f, 30);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

                try {
                    ParticleEffect.LAVA.sendToPlayers(Bukkit.getOnlinePlayers(), le.getEyeLocation(), 0.5f, 0.5f, 0.5f, 0.1f, 20);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                if (le instanceof Player) {
                    ((Player)le).sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "** DIVINE IMMOLATION **");
                    ((Player)le).playSound(le.getLocation(), Sound.ZOMBIE_PIG_ANGRY, 0.6f, 0.8f);
                }
            }
        }
    }
}

