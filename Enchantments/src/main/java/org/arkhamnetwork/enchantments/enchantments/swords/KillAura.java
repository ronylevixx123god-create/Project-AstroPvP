/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.rit.sucy.CustomEnchantment
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.bukkit.Location
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.enchantments.enchantments.swords;

import com.rit.sucy.CustomEnchantment;
import de.inventivegames.particle.ParticleEffect;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class KillAura
extends CustomEnchantment {
    public KillAura() {
        super("Kill Aura", GeneralUtil.swords, 2);
        this.max = 5;
        this.base = 15.0;
        this.interval = 6.0;
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        double random = Math.random();
        double chance = 0.05 * (double)enchantLevel;
        if (target.hasMetadata("monsterAmount") && random < chance) {
            int mobsToKill = enchantLevel == 5 && Math.random() < 0.05 ? 3 : 2;
            target.setMetadata("killAura", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)mobsToKill));
            try {
                ParticleEffect.LARGE_EXPLODE.sendToPlayers(Bukkit.getOnlinePlayers(), target.getLocation().add(0.0, 0.5, 0.0), 0.55f, 0.55f, 0.55f, 0.1f, 3);
            } catch (Exception exception) {
                exception.printStackTrace();
            }

        }
    }
}

