/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.rit.sucy.CustomEnchantment
 *  org.arkhamnetwork.Arkkit.Arkkit
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.enchantments.enchantments.swords;

import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class ThunderingBlow
extends CustomEnchantment {
    public ThunderingBlow() {
        super("Thundering Blow", GeneralUtil.swords, 2);
        this.max = 3;
        this.base = 25.0;
        this.interval = 0.0;
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        double chance;
        double random = Math.random();
        double d = chance = level == 3 ? 0.05 : 0.0175 * (double)level;
        if (random < chance) {
            if (playerHit.hasMetadata("lastThunderingBlowProc") && System.currentTimeMillis() - ((MetadataValue)playerHit.getMetadata("lastThunderingBlowProc").get(0)).asLong() <= 2500L) {
                return;
            }
            playerHit.setMetadata("lastThunderingBlowProc", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)System.currentTimeMillis()));
            playerHit.getWorld().strikeLightningEffect(playerHit.getLocation());
            playerHit.damage(5.0);
        }
    }
}

