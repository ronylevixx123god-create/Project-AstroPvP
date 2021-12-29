/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.nms.MinecraftServerUtils
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.enchantments.enchantments.swords;

import com.cosmicpvp.cosmicutils.nms.MinecraftServerUtils;
import org.arkhamnetwork.enchantments.Enchantments;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

final class DominateEffectListener
implements Listener {
    DominateEffectListener() {
    }

    @EventHandler(priority=EventPriority.LOW)
    public void onEntityDamageByEntiy(EntityDamageByEntityEvent e) {
        if (e.getDamager().hasMetadata(Dominate.dominateEffectLevel_metadata)) {
            long expireTick = ((MetadataValue)e.getDamager().getMetadata(Dominate.dominateEffectExpire_metadata).get(0)).asLong();
            if ((long)MinecraftServerUtils.getCurrentServerTick() < expireTick) {
                int dominateLevel = ((MetadataValue)e.getDamager().getMetadata(Dominate.dominateEffectLevel_metadata).get(0)).asInt();
                double mod = 1.0 - (double)(dominateLevel * 5 / 100);
                e.setDamage(e.getDamage() * mod);
            } else {
                e.getDamager().removeMetadata(Dominate.dominateEffectExpire_metadata, (Plugin)Enchantments.getInstance());
                e.getDamager().removeMetadata(Dominate.dominateEffectLevel_metadata, (Plugin)Enchantments.getInstance());
            }
        }
    }
}

