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
 *  org.bukkit.event.entity.EntityDamageEvent
 */
package org.arkhamnetwork.enchantments.enchantments.swords;

import com.rit.sucy.CustomEnchantment;
import de.inventivegames.particle.ParticleEffect;
import org.arkhamnetwork.enchantments.enchantments.axes.Corrupt;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Random;

public class Inversion
extends CustomEnchantment {
    public Inversion() {
        super("Inversion", GeneralUtil.swords, 2);
        this.max = 4;
        this.base = 17.0;
        this.interval = 4.0;
    }

    public void applyDefenseEffect(LivingEntity entityHit, LivingEntity attacker, int level, EntityDamageEvent e) {
        float procChance = (float)(0.05 * (double)level);
        if (Math.random() <= (double)procChance) {
            Player playerHit = (Player)entityHit;
            double heal = new Random().nextInt(3) + 1;
            if (heal >= 1.0) {
                if (Corrupt.isCorrupted((LivingEntity)playerHit) && Math.random() < (double)((float)Corrupt.getCorruptLevel((LivingEntity)playerHit) * 0.2f)) {
                    playerHit.damage(heal);
                    playerHit.sendMessage((Object)ChatColor.DARK_PURPLE + "* CORRUPTED [" + (Object)ChatColor.RED + heal + (Object)ChatColor.DARK_PURPLE + " DMG] *");
                    return;
                }
                GeneralUtil.healPlayer((LivingEntity)playerHit, heal);
                e.setDamage(0.0);
                e.setCancelled(true);
                try {
                    ParticleEffect.SPELL.sendToPlayers(Bukkit.getOnlinePlayers(), playerHit.getLocation().add(0.0, 1.0, 0.0), 0.45f, 0.45f, 0.45f, 0.1f, 20);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

                playerHit.playSound(playerHit.getLocation(), Sound.PISTON_EXTEND, 0.8f, 2.0f);
            }
        }
    }
}

