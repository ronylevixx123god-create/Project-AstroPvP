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
 *  org.bukkit.inventory.ItemStack
 */
package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class HeroicTank
extends HeroicCustomEnchantment {
    public HeroicTank() {
        super("Reinforced Tank", "Tank");
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (!(player instanceof Player)) {
            return;
        }
        if (player.hasMetadata("neutralized"))
            return;
        ItemStack i = null;
        if (attacker instanceof Player) {
            Player pAttacker = (Player)attacker;
            i = pAttacker.getItemInHand();
        }
        double damage_negation = (double)level * 0.0225;
        if (i != null && i.getType().name().contains("_AXE") && e.getDamage() * (1.0 - damage_negation) > 1.0) {
            e.setDamage(e.getDamage() * (1.0 - damage_negation));
            Location l = player.getLocation();
            WorldUtil.sendWorldEffect((Block)l.getBlock(), (int)42, (int)2001);
        }
    }
}

