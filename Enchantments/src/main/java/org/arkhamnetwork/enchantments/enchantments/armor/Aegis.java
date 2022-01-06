/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.nms.MinecraftServerUtils
 *  com.rit.sucy.CustomEnchantment
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.bukkit.Location
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.enchantments.enchantments.armor;

import com.cosmicpvp.cosmicutils.nms.MinecraftServerUtils;
import com.rit.sucy.CustomEnchantment;
import de.inventivegames.particle.ParticleEffect;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;

public class Aegis
extends CustomEnchantment {
    private static final String aegisNextReset_MetadataKey = "aegis_nextReset";
    private static final String aegisUniqueEnemies_MetadataKey = "aegis_uniqueEnemies";

    public Aegis() {
        super("Aegis", GeneralUtil.armor, 4);
        this.max = 6;
        this.base = 20.0;
        this.interval = 5.0;
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (!(attacker instanceof Player)) {
            return;
        }
        int ticksToCheck = 100;
        if (player.hasMetadata(aegisNextReset_MetadataKey)) {
            if ((long)MinecraftServerUtils.getCurrentServerTick() > ((MetadataValue)player.getMetadata(aegisNextReset_MetadataKey).get(0)).asLong()) {
                player.removeMetadata(aegisUniqueEnemies_MetadataKey, (Plugin)Enchantments.getInstance());
                player.setMetadata(aegisNextReset_MetadataKey, (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)(MinecraftServerUtils.getCurrentServerTick() + ticksToCheck)));
            }
        } else {
            player.setMetadata(aegisNextReset_MetadataKey, (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)(MinecraftServerUtils.getCurrentServerTick() + ticksToCheck)));
        }
        HashSet enemies = player.hasMetadata(aegisUniqueEnemies_MetadataKey) ? (HashSet)((MetadataValue)player.getMetadata(aegisUniqueEnemies_MetadataKey).get(0)).value() : new HashSet();
        Player pAttacker = (Player)attacker;
        if (enemies.size() > 8 - level && !enemies.contains(pAttacker.getUniqueId())) {
            //e.setDamage(e.getDamage() * 0.5);
            try {
                ParticleEffect.MAGIC_CRIT.sendToPlayers(Bukkit.getOnlinePlayers(), player.getEyeLocation(), 0.6f, 0.6f, 0.6f, 0.1f, 16);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return;
        }
        enemies.add(pAttacker.getUniqueId());
        player.setMetadata(aegisUniqueEnemies_MetadataKey, (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)enemies));
    }
}

