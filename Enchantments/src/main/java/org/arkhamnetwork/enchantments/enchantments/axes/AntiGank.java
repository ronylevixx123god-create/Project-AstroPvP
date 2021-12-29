/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.nms.MinecraftServerUtils
 *  com.cosmicpvp.cosmicutils.utils.WorldUtil
 *  com.rit.sucy.CustomEnchantment
 *  org.bukkit.Location
 *  org.bukkit.block.Block
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.enchantments.enchantments.axes;

import com.cosmicpvp.cosmicutils.nms.MinecraftServerUtils;
import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;

public class AntiGank
extends CustomEnchantment {
    private static final String antiGankNextReset_MetadataKey = "antiGank_nextReset";
    private static final String antiGankUniqueEnemies_MetadataKey = "antiGank_uniqueEnemies";
    private static final int ticksToCheck = 120;

    public AntiGank() {
        super("Anti Gank", GeneralUtil.axe, 7);
        this.max = 4;
        this.base = 20.0;
        this.interval = 5.0;
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        HashSet enemies;
        if (player.hasMetadata(antiGankNextReset_MetadataKey)) {
            if ((long)MinecraftServerUtils.getCurrentServerTick() > ((MetadataValue)player.getMetadata(antiGankNextReset_MetadataKey).get(0)).asLong()) {
                player.removeMetadata(antiGankUniqueEnemies_MetadataKey, (Plugin)Enchantments.getInstance());
                player.setMetadata(antiGankNextReset_MetadataKey, (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)(MinecraftServerUtils.getCurrentServerTick() + 120)));
                return;
            }
        } else {
            player.setMetadata(antiGankNextReset_MetadataKey, (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)(MinecraftServerUtils.getCurrentServerTick() + 120)));
        }
        HashSet hashSet = enemies = player.hasMetadata(antiGankUniqueEnemies_MetadataKey) ? (HashSet)((MetadataValue)player.getMetadata(antiGankUniqueEnemies_MetadataKey).get(0)).value() : new HashSet();
        if (enemies.size() >= 6 - level) {
            e.setDamage(e.getDamage() * Math.min(1.5, 1.0 + 0.1 * (double)enemies.size()));
            Location l = playerHit.getLocation().add(0.0, 1.0, 0.0);
            WorldUtil.sendWorldEffect((Block)l.getBlock(), (int)86, (int)2001);
            return;
        }
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (!(attacker instanceof Player)) {
            return;
        }
        if (player.hasMetadata(antiGankNextReset_MetadataKey)) {
            if ((long)MinecraftServerUtils.getCurrentServerTick() > ((MetadataValue)player.getMetadata(antiGankNextReset_MetadataKey).get(0)).asLong()) {
                player.removeMetadata(antiGankUniqueEnemies_MetadataKey, (Plugin)Enchantments.getInstance());
            }
        } else {
            player.setMetadata(antiGankNextReset_MetadataKey, (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)(MinecraftServerUtils.getCurrentServerTick() + 120)));
        }
        HashSet enemies = player.hasMetadata(antiGankUniqueEnemies_MetadataKey) ? (HashSet)((MetadataValue)player.getMetadata(antiGankUniqueEnemies_MetadataKey).get(0)).value() : new HashSet();
        Player pAttacker = (Player)attacker;
        enemies.add(pAttacker.getUniqueId());
        player.setMetadata(antiGankUniqueEnemies_MetadataKey, (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)enemies));
    }
}

