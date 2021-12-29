/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.rit.sucy.CustomEnchantment
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.bukkit.Location
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageEvent
 */
package org.arkhamnetwork.enchantments.enchantments.armor;

import com.rit.sucy.CustomEnchantment;
import de.inventivegames.particle.ParticleEffect;
import org.arkhamnetwork.enchantments.util.ArmorUtil;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Random;

public class Lucky
extends CustomEnchantment {
    public Lucky() {
        super("Lucky", GeneralUtil.armor, 3);
        this.max = 10;
        this.base = 25.0;
        this.interval = 10.0;
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (player instanceof Player && new Random().nextInt(400) <= level) {
            Player p = (Player)player;
            double dmg = e.getDamage();
            dmg *= (double)ArmorUtil.getArmorDamageNullificationPercent(ArmorUtil.getArmorValue(p));
            if (player.getHealth() - dmg <= 0.0) {
                e.setCancelled(true);
                e.setDamage(0.0);
                try {
                    ParticleEffect.CLOUD.sendToPlayers(Bukkit.getOnlinePlayers(), p.getLocation(), 0.3f, 0.3f, 0.3f, 0.1f, 30);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
}

