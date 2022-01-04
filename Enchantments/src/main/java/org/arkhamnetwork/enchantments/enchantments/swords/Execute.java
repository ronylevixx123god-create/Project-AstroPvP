/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.WorldUtil
 *  com.rit.sucy.CustomEnchantment
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 */
package org.arkhamnetwork.enchantments.enchantments.swords;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.util.DamageIndicatorUtils;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Execute
extends CustomEnchantment {
    public Execute() {
        super("Execute", GeneralUtil.swords, 2);
        this.max = 7;
        this.base = 15.0;
        this.interval = 3.0;
    }

    public void applyEffect(LivingEntity player, LivingEntity target, int level, EntityDamageByEntityEvent e) {
        if (!(target instanceof Player)) {
            return;
        }
        if (target.getHealth() > 8.0) {
            return;
        }
        if (GeneralUtil.isEffectedByRage(target)) {
            return;
        }
        double random = Math.random();
        double chance = 0.04 * (double)level;
        double mod = 2.0;
        if (random < chance) {
            e.setDamage(e.getDamage() * mod);
            Location l = target.getLocation();
            DamageIndicatorUtils.sendDamageIndicator((Player) e.getEntity(), CC.AquaB + "* EXECUTE *", "EXECUTE");
            WorldUtil.sendWorldEffect((Block)l.getBlock(), (int)Material.REDSTONE_WIRE.getId(), (int)2001);
        }
    }
}

