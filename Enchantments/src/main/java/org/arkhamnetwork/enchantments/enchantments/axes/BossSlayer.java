/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.nms.MinecraftServerUtils
 *  com.rit.sucy.CustomEnchantment
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.MetadataValue
 */
package org.arkhamnetwork.enchantments.enchantments.axes;

import com.cosmicpvp.cosmicutils.nms.MinecraftServerUtils;
import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;

public class BossSlayer
extends CustomEnchantment {
    public BossSlayer() {
        super("Boss Slayer", GeneralUtil.axe, 2);
        this.max = 5;
        this.base = 10.0;
        this.interval = 10.0;
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        Player p = (Player)player;
        if (playerHit instanceof Player) {
            return;
        }
        ItemStack used = p.getItemInHand();
        if (used == null || !used.getType().name().endsWith("_AXE")) {
            return;
        }
        if (p.hasMetadata("lastItemHeldChange") && (long)MinecraftServerUtils.getCurrentServerTick() - ((MetadataValue)p.getMetadata("lastItemHeldChange").get(0)).asLong() <= 5L) {
            return;
        }
        if (playerHit.hasMetadata("boss")) {
            e.setDamage(e.getDamage() * (1.0 + 0.075 * (double)level));
        }
    }
}

