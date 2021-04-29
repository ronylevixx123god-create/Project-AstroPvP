package org.arkhamnetwork.enchantments.enchantments.swords;

import org.arkhamnetwork.enchantments.Enchantments;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

final class RageListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDamaged(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player entity = (Player) event.getEntity();
            if (entity.hasMetadata("combo_pvp")) {
                entity.removeMetadata("combo_pvp", Enchantments.getInstance());
            }
            if (entity.hasMetadata("last_combo_pvp")) {
                entity.removeMetadata("last_combo_pvp", Enchantments.getInstance());
            }
            if (entity.hasMetadata("combo_pve")) {
                entity.removeMetadata("combo_pve", Enchantments.getInstance());
            }
            if (entity.hasMetadata("last_combo_pve")) {
                entity.removeMetadata("last_combo_pve", Enchantments.getInstance());
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player damager = (Player) event.getDamager();
            if (event.getEntity() instanceof Player) {
                int pvp_combo = damager.hasMetadata("combo_pvp") ? damager.getMetadata("combo_pvp").get(0).asInt() : 0;
                long last_pvp_combo = damager.hasMetadata("last_combo_pvp")
                        ? damager.getMetadata("last_combo_pvp").get(0).asLong()
                        : 0;
                if (last_pvp_combo != 0 && last_pvp_combo - System.currentTimeMillis() <= -15000) {
                    pvp_combo = 0;
                }
                pvp_combo++;
                last_pvp_combo = System.currentTimeMillis();
                damager.setMetadata("last_combo_pvp",
                        new FixedMetadataValue(Enchantments.getInstance(), last_pvp_combo));
                damager.setMetadata("combo_pvp", new FixedMetadataValue(Enchantments.getInstance(), pvp_combo));
                return;
            }
            if (event.getEntity() instanceof LivingEntity) {
                int pve_combo = damager.hasMetadata("combo_pve") ? damager.getMetadata("combo_pve").get(0).asInt() : 0;
                long last_pve_combo = damager.hasMetadata("last_combo_pve")
                        ? damager.getMetadata("last_combo_pve").get(0).asLong()
                        : 0;
                if (last_pve_combo != 0 && last_pve_combo - System.currentTimeMillis() <= -15000) {
                    pve_combo = 0;
                }
                pve_combo++;
                last_pve_combo = System.currentTimeMillis();
                damager.setMetadata("last_combo_pve",
                        new FixedMetadataValue(Enchantments.getInstance(), last_pve_combo));
                damager.setMetadata("combo_pve", new FixedMetadataValue(Enchantments.getInstance(), pve_combo));
            }
        }
    }
}
