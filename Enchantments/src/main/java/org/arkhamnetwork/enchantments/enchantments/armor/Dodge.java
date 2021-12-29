/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.rit.sucy.CustomEnchantment
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 */
package org.arkhamnetwork.enchantments.enchantments.armor;

import com.rit.sucy.CustomEnchantment;
import de.inventivegames.particle.ParticleEffect;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class Dodge
extends CustomEnchantment {
    public Dodge() {
        super("Dodge", GeneralUtil.armor, 2);
        this.max = 5;
        this.base = 20.0;
        this.interval = 5.0;
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (player.hasMetadata("neutralized"))
            return;
        if (player.hasMetadata("noDefenseProcs"))
            return;
        if (e instanceof EntityDamageByEntityEvent) {
            double chance = 0.025 * (double)level;
            boolean sneaking = ((Player)player).isSneaking();
            if (sneaking) {
                chance += 0.15;
            }
            if (Math.random() < chance) {
                e.setCancelled(true);
                e.setDamage(0.0);
                ((Player)player).sendMessage((Object)ChatColor.YELLOW + "*DODGE*");
                try {
                    ParticleEffect.CLOUD.sendToPlayers(Bukkit.getOnlinePlayers(), player.getLocation().add(0.0, 0.1, 0.0), 0.2f, 0.2f, 0.2f, 0.1f, 10);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                player.getWorld().playSound(player.getLocation(), Sound.BAT_TAKEOFF, 1.0f, 0.75f);
            }
        }
    }
}

