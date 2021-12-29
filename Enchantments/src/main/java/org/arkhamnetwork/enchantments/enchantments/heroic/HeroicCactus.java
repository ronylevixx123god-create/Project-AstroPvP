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

import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import org.bukkit.Location;
import org.bukkit.block.Block;
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
                pAttacker.damage(0.5 * (double)level);
                Location l = p.getLocation();
                WorldUtil.sendWorldEffect((Block)l.getBlock(), (int)81, (int)2001);
            }
            double d = thornNegateChance = level == 1 ? 0.5 : 1.0;
            if (e.getCause() == EntityDamageEvent.DamageCause.THORNS && Math.random() < thornNegateChance) {
                e.setCancelled(true);
                e.setDamage(0.0);
            }
        }
    }
}

