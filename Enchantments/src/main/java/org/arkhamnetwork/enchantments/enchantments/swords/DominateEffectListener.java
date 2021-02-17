package org.arkhamnetwork.enchantments.enchantments.swords;

import com.cosmicpvp.cosmicutils.nms.MinecraftServerUtils;
import org.arkhamnetwork.enchantments.Enchantments;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

final class DominateEffectListener implements Listener {
    @EventHandler(priority = EventPriority.LOW)
    public void onEntityDamageByEntiy(final EntityDamageByEntityEvent e) {
        if (e.getDamager().hasMetadata(Dominate.dominateEffectLevel_metadata)) {
            final long expireTick = e.getDamager().getMetadata(Dominate.dominateEffectExpire_metadata).get(0).asLong();
            if (MinecraftServerUtils.getCurrentServerTick() < expireTick) {
                final int dominateLevel = e.getDamager().getMetadata(Dominate.dominateEffectLevel_metadata).get(0).asInt();
                final double mod = 1.0 - dominateLevel * 5 / 100;
                //.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * mod);
            } else {
                e.getDamager().removeMetadata(Dominate.dominateEffectExpire_metadata, (Plugin) Enchantments.getInstance());
                e.getDamager().removeMetadata(Dominate.dominateEffectLevel_metadata, (Plugin) Enchantments.getInstance());
            }
        }
    }
}
