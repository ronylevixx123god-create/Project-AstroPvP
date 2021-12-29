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
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package org.arkhamnetwork.enchantments.enchantments.heroic;

import de.inventivegames.particle.ParticleEffect;
import org.arkhamnetwork.enchantments.libs.PlayerPotionEffects;
import org.arkhamnetwork.enchantments.libs.SpecificCustomEnchantment;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HeroicDodge
extends HeroicCustomEnchantment {
    public HeroicDodge() {
        super("Ethereal Dodge", "Dodge", GeneralUtil.boots, 2);
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (player.hasMetadata("neutralized"))
            return;
        if (player.hasMetadata("noDefenseProcs"))
            return;
        if (e instanceof EntityDamageByEntityEvent) {
            double chance = 0.05 * (double)level;
            boolean sneaking = ((Player)player).isSneaking();
            if (sneaking) {
                chance += 0.2;
            }
            if (Math.random() < chance) {
                e.setCancelled(true);
                e.setDamage(0.0);
                ((Player)player).sendMessage((Object)ChatColor.YELLOW + "*DODGE*");
                try {
                    ParticleEffect.CLOUD.sendToPlayers(Bukkit.getOnlinePlayers(), player.getLocation().add(0.0, 1.0, 0.0), 0.2f, 0.2f, 0.2f, 0.1f, 10);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                player.getWorld().playSound(player.getLocation(), Sound.BAT_TAKEOFF, 1.0f, 0.75f);
                double chanceForSpeed = 5 + (level - 1) * 2;
                if (Math.random() < chanceForSpeed * 0.01) {
                    PlayerPotionEffects.trackPotionEffect((Player)player, new SpecificCustomEnchantment(this, level), new PotionEffect(PotionEffectType.SPEED, 60, Math.max(2, 2 + level / 2)));
                }
            }
        } else if (e != null && e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            if (player.getFallDistance() > 5.0f) {
                ((Player)player).sendMessage((Object)ChatColor.YELLOW + "*FALL DODGED*");
            }
            e.setDamage(0.0);
            e.setCancelled(true);
        }
    }
}

