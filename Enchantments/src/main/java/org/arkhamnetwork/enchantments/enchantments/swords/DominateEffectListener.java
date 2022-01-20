package org.arkhamnetwork.enchantments.enchantments.swords;

import net.astropvp.damagehandler.AstroDamageHandler;
import org.bukkit.event.entity.*;
import org.bukkit.metadata.*;
import com.cosmicpvp.cosmicutils.nms.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.plugin.*;
import org.bukkit.event.*;

final class DominateEffectListener implements Listener
{
    @EventHandler(priority = EventPriority.LOW)
    public void onEntityDamageByEntiy(final EntityDamageByEntityEvent e) {
        if (e.getDamager().hasMetadata(Dominate.dominateEffectLevel_metadata)) {
            final long expireTick = e.getDamager().getMetadata(Dominate.dominateEffectExpire_metadata).get(0).asLong();
            if (MinecraftServerUtils.getCurrentServerTick() < expireTick) {
                final int dominateLevel = e.getDamager().getMetadata(Dominate.dominateEffectLevel_metadata).get(0).asInt();
                final double mod = 1.0 - dominateLevel * 5 / 100;
                AstroDamageHandler.getInstance().getDamageHandler().reduceDamage((double)(dominateLevel * 5), e, "&6&lDominate");
            }
            else {
                e.getDamager().removeMetadata(Dominate.dominateEffectExpire_metadata, (Plugin)Enchantments.getInstance());
                e.getDamager().removeMetadata(Dominate.dominateEffectLevel_metadata, (Plugin)Enchantments.getInstance());
            }
        }
    }
}
