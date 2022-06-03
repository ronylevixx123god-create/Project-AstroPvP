package org.arkhamnetwork.enchantments.enchantments.swords;

import org.bukkit.event.entity.*;
import org.bukkit.metadata.*;
import com.cosmicpvp.cosmicutils.nms.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.plugin.*;
import org.bukkit.event.*;

final class DominateEffectListener implements Listener {
    @EventHandler(
            priority = EventPriority.LOW
    )
    public void onEntityDamageByEntiy(EntityDamageByEntityEvent e) {
        if (e.getDamager().hasMetadata(Dominate.dominateEffectLevel_metadata)) {
            long expireTick = ((MetadataValue)e.getDamager().getMetadata(Dominate.dominateEffectExpire_metadata).get(0)).asLong();
            if ((long)MinecraftServerUtils.getCurrentServerTick() < expireTick) {
                int dominateLevel = ((MetadataValue)e.getDamager().getMetadata(Dominate.dominateEffectLevel_metadata).get(0)).asInt();
                double mod = 1.0D - (double)(dominateLevel * 5 / 100);
                e.setDamage(EntityDamageByEntityEvent.DamageModifier.BASE,e.getDamage(EntityDamageByEntityEvent.DamageModifier.BASE) * mod);
            } else {
                e.getDamager().removeMetadata(Dominate.dominateEffectExpire_metadata, Enchantments.getInstance());
                e.getDamager().removeMetadata(Dominate.dominateEffectLevel_metadata, Enchantments.getInstance());
            }
        }

    }
}
