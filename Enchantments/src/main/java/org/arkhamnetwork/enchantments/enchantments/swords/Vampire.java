/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.MaskUtils
 *  com.rit.sucy.CustomEnchantment
 *  org.bukkit.Bukkit
 *  org.bukkit.Sound
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.enchantments.enchantments.swords;

import com.cosmicpvp.cosmicutils.utils.MaskUtils;
import com.rit.sucy.CustomEnchantment;
import me.ifamasssxd.cosmicmasks.MaskType;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;

public class Vampire
extends CustomEnchantment {
    public Vampire() {
        super("Vampire", GeneralUtil.swords, 2);
        this.max = 3;
        this.base = 25.0;
        this.interval = 0.0;
    }

    public void applyEffect(final LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        double chance = 0.05 * (double)level;
        final double healthGain = level;
        if (Math.random() < chance) {
            if (playerHit instanceof Player && MaskType.hasEquipped((Player)((Player)playerHit), (String)"NECROMANCER")) {
                return;
            }
            Bukkit.getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), new Runnable(){

                @Override
                public void run() {
                    if (player.isDead() || !player.isValid()) {
                        return;
                    }
                    if (GeneralUtil.healPlayer(player, healthGain)) {
                        ((Player)player).playSound(player.getLocation(), Sound.DRINK, 0.75f, 2.0f);
                    }
                }
            }, 60L);
            ((Player)player).playSound(player.getLocation(), Sound.DRINK, 0.75f, 2.0f);
        }
    }
}

