package org.arkhamnetwork.enchantments.enchantments.swords;

import com.bryside.astroattributes.DragonTitanAttributesAPI;
import com.bryside.astroattributes.enums.RuneType;
import com.bryside.astroattributes.objects.RuneCacher;
import org.arkhamnetwork.enchantments.Enchantments;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

final class RageListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDamaged(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof LivingEntity) {
            Player entity = (Player) event.getEntity();
            if (entity.hasMetadata("combo_pvp")) {
                if(RuneCacher.isContained((Player) event.getEntity()) && RuneCacher.getRuneCacher((Player) event.getEntity()).getRuneType() == RuneType.KRONOS) {
                    if(entity.getMetadata("combo_pvp").get(0).asInt() > 1) {
                        int newRageValue = entity.getMetadata("combo_pvp").get(0).asInt() - 2;
                        entity.setMetadata("combo_pvp", new FixedMetadataValue(Enchantments.getInstance(), newRageValue));
                    }
                } else {
                    entity.removeMetadata("combo_pvp", Enchantments.getInstance());
                    if (entity.hasMetadata("last_combo_pvp")) {
                        entity.removeMetadata("last_combo_pvp", Enchantments.getInstance());
                    }
                }
            }
            if (entity.hasMetadata("combo_pve")) {
                if (RuneCacher.isContained((Player) event.getEntity()) && RuneCacher.getRuneCacher((Player) event.getEntity()).getRuneType() == RuneType.KRONOS) {
                    if(entity.getMetadata("combo_pve").get(0).asInt() > 1) {
                        int newRageValue = entity.getMetadata("combo_pve").get(0).asInt() - 2;
                        entity.setMetadata("combo_pve", new FixedMetadataValue(Enchantments.getInstance(), newRageValue));
                    }
                } else {
                    entity.removeMetadata("combo_pve", Enchantments.getInstance());
                    if (entity.hasMetadata("last_combo_pve")) {
                        entity.removeMetadata("last_combo_pve", Enchantments.getInstance());
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player damager = (Player) event.getDamager();
            if (event.getEntity() instanceof Player) {
                int pvp_combo = damager.hasMetadata("combo_pvp") ? damager.getMetadata("combo_pvp").get(0).asInt() : 0;
                long last_pvp_combo = damager.hasMetadata("last_combo_pvp") ? damager.getMetadata("last_combo_pvp").get(0).asLong() : 0;
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
