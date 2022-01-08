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
import org.arkhamnetwork.enchantments.files.CustomConfig;
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
        ItemStack i = null;
        if (attacker instanceof Player) {
            Player pAttacker = (Player)attacker;
            i = pAttacker.getItemInHand();
        }
        double damage_negation = CustomConfig.get().getDouble("heroicTankNegation");
        ;
        if (i != null && i.getType().name().contains("_AXE")) {
            e.setDamage(e.getDamage() * (1.0 - damage_negation));
        }
    }
}

