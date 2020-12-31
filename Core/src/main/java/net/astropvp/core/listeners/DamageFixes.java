package net.astropvp.core.listeners;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageFixes implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void dmgDebuf(final EntityDamageByEntityEvent event) {
        if (event.getEntityType() != EntityType.PLAYER || event.getDamager().getType() != EntityType.PLAYER) {
            return;
        }
        final Player damager = (Player) event.getDamager();
        final Player personBeingHit = (Player) event.getEntity();
        if (damager.getItemInHand().getType() == Material.DIAMOND_AXE || damager.getItemInHand().getType() == Material.GOLD_AXE) {
            event.setDamage(event.getDamage() * 0.50);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void specialSet(final EntityDamageByEntityEvent event) {
        if (event.getEntityType() != EntityType.PLAYER || event.getDamager().getType() != EntityType.PLAYER) {
            return;
        }
        final Player damager = (Player) event.getDamager();
        final Player personBeingHit = (Player) event.getEntity();
        if (CosmicArmorSetsAPI.getArmorSet(damager) == ArmorSet.ENGINEER || CosmicArmorSetsAPI.getArmorSet(damager) == ArmorSet.KOTH ||
                CosmicArmorSetsAPI.getArmorSet(damager) == ArmorSet.DIMENSIONAL_TRAVELER || CosmicArmorSetsAPI.getArmorSet(damager) == ArmorSet.DRAGON_SLAYER
        || CosmicArmorSetsAPI.getArmorSet(damager) == ArmorSet.ENGINEER || CosmicArmorSetsAPI.getArmorSet(damager) == ArmorSet.SUPREME
        || CosmicArmorSetsAPI.getArmorSet(damager) == ArmorSet.MOTHER_OF_YIJKI || CosmicArmorSetsAPI.getArmorSet(damager) == ArmorSet.YETI) {
            event.setDamage(event.getDamage() * 0.05);
        }
    }

}
