/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.rit.sucy.CustomEnchantment
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.enchantments.enchantments.armor;

import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class ArrowDeflect
extends CustomEnchantment {
    public ArrowDeflect() {
        super("Arrow Deflect", GeneralUtil.armor, 4);
        this.max = 4;
        this.base = 20.0;
        this.interval = 5.0;
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        EntityDamageByEntityEvent edbee;
        if (e instanceof EntityDamageByEntityEvent && (edbee = (EntityDamageByEntityEvent)e).getDamager() instanceof Projectile) {
            Projectile shooter = (Projectile)edbee.getDamager();
            if (shooter != null && shooter.hasMetadata("lethalSniper")) {
                int sniper = ((MetadataValue)shooter.getMetadata("lethalSniper").get(0)).asInt();
                double chance = 0.1 * (double)sniper;
                if (Math.random() <= chance) {
                    return;
                }
            }
            if (player.hasMetadata("lastArrowDamageEvent")) {
                long minDelayMilliseconds = level * 500;
                long last = ((MetadataValue)player.getMetadata("lastArrowDamageEvent").get(0)).asLong();
                if (System.currentTimeMillis() - last <= minDelayMilliseconds) {
                    try {
                        de.inventivegames.particle.ParticleEffect.SPELL.sendToPlayers(Bukkit.getOnlinePlayers(), player.getLocation().add(0.0, 1.0, 0.0), 0.6f, 0.6f, 0.6f, 0.1f, 20);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    ((Player)player).playSound(player.getLocation(), Sound.ITEM_BREAK, 0.7f, 0.2f);
                    e.setDamage(0.0);
                    edbee.getDamager().remove();
                    player.removeMetadata("lastArrowDamageEvent", (Plugin)Enchantments.getInstance());
                    return;
                }
            }
            player.setMetadata("lastArrowDamageEvent", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)System.currentTimeMillis()));
        }
    }
}

