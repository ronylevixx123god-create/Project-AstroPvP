package net.astropvp.core.listeners;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.cosmicpvp.cosmicutils.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageFixes implements Listener {

    /*@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void dmgDebuf(final EntityDamageByEntityEvent event) {
        if (event.getEntityType() != EntityType.PLAYER || event.getDamager().getType() != EntityType.PLAYER) {
            return;
        }
        final Player damager = (Player) event.getDamager();
        final Player personBeingHit = (Player) event.getEntity();
        if (damager.getItemInHand().getType() != Material.DIAMOND_SWORD && damager.getItemInHand().getType() != Material.GOLD_SWORD) {
            event.setDamage(event.getDamage() * 0.50);
        }
    }*/

/*    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void specialSet(final EntityDamageByEntityEvent event) {
        if (event.getEntityType() != EntityType.PLAYER || event.getDamager().getType() != EntityType.PLAYER) {
            return;
        }
        final Player damager = (Player) event.getDamager();
        final Player personBeingHit = (Player) event.getEntity();
        if (CosmicArmorSetsAPI.getArmorSet(damager) == ArmorSet.DIMENSIONAL_TRAVELER && CosmicArmorSetsAPI.getBonusCrystalsEquipped(damager).isEmpty()||
                CosmicArmorSetsAPI.getArmorSet(damager) == ArmorSet.DRAGON_SLAYER && CosmicArmorSetsAPI.getBonusCrystalsEquipped(damager).isEmpty()
                        || CosmicArmorSetsAPI.getArmorSet(damager) == ArmorSet.ENGINEER && CosmicArmorSetsAPI.getBonusCrystalsEquipped(damager).isEmpty()
                || CosmicArmorSetsAPI.getArmorSet(damager) == ArmorSet.SUPREME && CosmicArmorSetsAPI.getBonusCrystalsEquipped(damager).isEmpty()
                        || CosmicArmorSetsAPI.getArmorSet(damager) == ArmorSet.MOTHER_OF_YIJKI && CosmicArmorSetsAPI.getBonusCrystalsEquipped(damager).isEmpty()
                || CosmicArmorSetsAPI.getArmorSet(damager) == ArmorSet.YETI && CosmicArmorSetsAPI.getBonusCrystalsEquipped(damager).isEmpty()) {
            event.setDamage(event.getDamage() * 0.05);
        }
    }*/

/*    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void dmgDebuf1(final EntityDamageByEntityEvent event) {
        if (event.getEntity() == null) {
            return;
        }
        if (event.getDamager() == null) {
            return;
        }
        if (event.getEntityType() != EntityType.PLAYER || event.getDamager().getType() != EntityType.PLAYER) {
            return;
        }
        final Player damager = (Player) event.getDamager();
        if (damager.getInventory().getHelmet() == null) return;
        if (damager.getInventory().getChestplate() == null) return;
        if (damager.getInventory().getLeggings() == null) return;
        if (damager.getInventory().getBoots() == null) return;
        if (damager.getInventory().getHelmet().getType() == Material.DIAMOND_HELMET &&
                damager.getInventory().getChestplate().getType() == Material.DIAMOND_CHESTPLATE &&
                damager.getInventory().getLeggings().getType() == Material.DIAMOND_LEGGINGS &&
                damager.getInventory().getBoots().getType() == Material.DIAMOND_BOOTS) {
            event.setDamage(event.getDamage() * 0.30);
        }

    }
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void dmgDebuf2(final EntityDamageByEntityEvent event) {
        if (event.getEntity() == null) {
            return;
        }
        if (event.getDamager() == null) {
            return;
        }

        if (event.getEntityType() != EntityType.PLAYER || event.getDamager().getType() != EntityType.PLAYER) {
            return;
        }
        final Player personBeingHit = (Player) event.getEntity();
        if (personBeingHit.getInventory().getHelmet() == null) return;
        if (personBeingHit.getInventory().getChestplate() == null) return;
        if (personBeingHit.getInventory().getLeggings() == null) return;
        if (personBeingHit.getInventory().getBoots() == null) return;
        if (personBeingHit.getInventory().getHelmet().getType() == Material.DIAMOND_HELMET  ||
                personBeingHit.getInventory().getChestplate().getType() == Material.DIAMOND_CHESTPLATE ||
                personBeingHit.getInventory().getLeggings().getType() == Material.DIAMOND_LEGGINGS ||
                personBeingHit.getInventory().getBoots().getType() == Material.DIAMOND_BOOTS) {
            event.setDamage(event.getDamage() * 2.0);
        }
    }*/

    public boolean isCritical(Player p) {
        return (p.getVelocity().getY() + 0.0784000015258789) <= 0;
    }



}
