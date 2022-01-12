/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.WorldUtil
 *  org.bukkit.Location
 *  org.bukkit.block.Block
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 */
package org.arkhamnetwork.enchantments.enchantments.heroic;

import org.arkhamnetwork.enchantments.customfiles.CustomConfig;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class HeroicCactus
extends HeroicCustomEnchantment {
    public HeroicCactus() {
        super("Mighty Cactus", "Cactus");
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (player instanceof Player) {
            if (player.hasMetadata("noDefenseProcs"))
                return;
            if (player.hasMetadata("neutralized"))
                return;
            double thornNegateChance;
            Player pAttacker;
            Player p = (Player)player;
            if (attacker != null && attacker instanceof Player && (pAttacker = (Player)attacker) != null) {
                double heroicCactusChance = CustomConfig.get().getDouble("heroicCactusChance");

                if (Math.random() < heroicCactusChance) {
                    Location l = p.getLocation();
                    attacker.damage(0.5);
                }
            }
            double d = thornNegateChance = level == 1 ? 0.5 : 1.0;
            if (e.getCause() == EntityDamageEvent.DamageCause.THORNS && Math.random() < thornNegateChance) {
                e.setDamage(0.0);
            }
        }
    }
}

