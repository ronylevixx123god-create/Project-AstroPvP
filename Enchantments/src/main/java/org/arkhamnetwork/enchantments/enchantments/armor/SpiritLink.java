/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.rit.sucy.CustomEnchantment
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 */
package org.arkhamnetwork.enchantments.enchantments.armor;

import com.rit.sucy.CustomEnchantment;
import de.inventivegames.particle.ParticleEffect;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class SpiritLink
extends CustomEnchantment {
    public SpiritLink() {
        super("Spirit Link", GeneralUtil.chestplates, 2);
        this.max = 5;
        this.base = 15.0;
        this.interval = 5.0;
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        double chance = (double)level * 0.1;
        if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK && Math.random() < chance) {
            if (player.hasMetadata("neutralized"))
                return;
            Player playerHurt = (Player)player;
            double healAmount = Math.min(4.0, e.getDamage() / (double)(this.max - (level - 1)) / 4.0);
            double radius = level * 2;
            for (Entity ent : player.getNearbyEntities(radius, radius, radius)) {
                Player playerNear;
                if (!(ent instanceof Player) || !this.isAlly(playerHurt, playerNear = (Player)ent)) continue;
//                GeneralUtil.healPlayer((LivingEntity)playerNear, healAmount);
                try {
//                    ParticleEffect.HEART.sendToPlayers(Bukkit.getOnlinePlayers(), playerNear.getEyeLocation().add(0.0, 0.35, 0.0), 0.0f, (float) Math.random(), 0.0f, 0.05f, 1);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            try {
//                ParticleEffect.HEART.sendToPlayers(Bukkit.getOnlinePlayers(), player.getLocation().add(0.0, 0.5, 0.0), (float)Math.random() / 2.0f, (float)Math.random() / 2.0f,(float)Math.random() / 2.0f, 0.05f, 3);
            } catch (Exception exception) {
                exception.printStackTrace();
            }

        }
    }

    private boolean isAlly(Player p1, Player p2) {
        return GeneralUtil.isAlly(p1, p2);
    }
}

