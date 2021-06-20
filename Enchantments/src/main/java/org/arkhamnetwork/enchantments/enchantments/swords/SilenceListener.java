package org.arkhamnetwork.enchantments.enchantments.swords;

import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

final class SilenceListener implements Listener {
    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onEntityDamage(final EntityDamageEvent e) {
        if (e.getFinalDamage() > 0.0 && e.getEntity() instanceof Player && e.getEntity().hasMetadata("noDefenseProcs")) {
            if (AstroItemSkinsAPI.hasSkinEquipped(((Player) e.getEntity()).getKiller(), SkinType.CUPIDS_HEART)) {
                e.setDamage(e.getDamage() * 1.05);
            } else {
                e.setDamage(e.getDamage() * 1.02);
            }
        }
    }
}
