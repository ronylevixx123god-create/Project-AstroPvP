package com.compilex.CosmicArmorSets.listeners;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.CosmicArmorSetsPlugin;
import com.compilex.CosmicArmorSets.enums.AbilityArmorSet;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.compilex.CosmicArmorSets.enums.CachedArmorSet;
import com.compilex.CosmicArmorSets.enums.OtherPlayerEventListenerSet;
import com.compilex.CosmicArmorSets.enums.armorsets.DimensionalTravelerSet;
import com.compilex.CosmicArmorSets.enums.armorsets.EngineerSet;
import com.compilex.CosmicArmorSets.enums.armorsets.SupremeArmorSet;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.UpgradeUtils;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class SetBonusListener
        implements Listener {

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        if (event.getMessage() != null && event.getMessage().equals("/help armorset"))
            event.setMessage("/armorset help");
    }

    @EventHandler
    public void onPlayerSetBonus(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        ArmorSet stored = CosmicArmorSetsAPI.getCachedArmorSet(player);
        if (stored != null) {
            stored.getArmorSetInfo().onUnequip(player);
            player.removeMetadata("armorSet", CosmicArmorSetsPlugin.get_());
            player.removeMetadata("armorLock", CosmicArmorSetsPlugin.get_());
        }
        ((DimensionalTravelerSet) ArmorSet.DIMENSIONAL_TRAVELER.getArmorSetInfo()).onPlayerEvent(event);
        ((EngineerSet) ArmorSet.ENGINEER.getArmorSetInfo()).onPlayerEvent(event);


        CachedArmorSet<AbilityArmorSet> armor = CosmicArmorSetsPlugin.get_().getPlayerAbilityArmorSets().remove(player);
        if (armor != null)
            armor.getSet().getArmorSetInfo().onUnequip(player);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
        ((SupremeArmorSet) ArmorSet.SUPREME.getArmorSetInfo()).onPlayerEvent(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        if (event.getTo() != Material.AIR) {
            ((DimensionalTravelerSet) ArmorSet.DIMENSIONAL_TRAVELER.getArmorSetInfo()).onPlayerEvent(event);
            ((EngineerSet) ArmorSet.ENGINEER.getArmorSetInfo()).onPlayerEvent(event);

        }
    }


    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        double damageAtStart = event.getDamage(EntityDamageEvent.DamageModifier.BASE);
        Player damager = null;
        if (event.getDamager() instanceof org.bukkit.entity.Arrow && ((Projectile) event.getDamager()).getShooter() instanceof Player) {
            damager = (Player) ((Projectile) event.getDamager()).getShooter();
        } else if (event.getDamager() instanceof Player) {
            damager = (Player) event.getDamager();
        }

        if (damager != null) {
            ArmorSet damagerArmorSet = CosmicArmorSetsAPI.getArmorSet(damager);
            if (damagerArmorSet != null && (event.getDamager() instanceof Player || (damagerArmorSet.getArmorSetInfo().supportsBowDamage() && event.getDamager() instanceof Projectile))) {
                damagerArmorSet.getArmorSetInfo().onPlayerAttacked(damager, event);
            }
            if (damagerArmorSet == null) {
                AbilityArmorSet set = CosmicArmorSetsAPI.getAbilityArmorSet(damager);
                if (set != null && (event.getDamager() instanceof Player || (set.getArmorSetInfo().supportsBowDamage() && event.getDamager() instanceof Projectile))) {
                    set.getArmorSetInfo().onPlayerAttacked(damager, event);
                }
            }
            if (!(damager.hasMetadata("blackout"))) {
                Map<ArmorSet, Integer> bonuses = CosmicArmorSetsAPI.getBonusCrystalsEquippedWithCorruptionIncluded(damager, event.getEntity());
                if (!bonuses.isEmpty()) {
                    for (Map.Entry<ArmorSet, Integer> ent : bonuses.entrySet()) {
                        ent.getKey().getArmorSetInfo().onPlayerAttackedBonus(damager, ent.getValue().intValue(), event);
                    }
                }
            }
        }
        if (event.getEntity() instanceof Player) {
            if (event.getDamage(EntityDamageEvent.DamageModifier.BASE) != damageAtStart) {
                int reduceLevel = UpgradeUtils.getUpgradeLevel((Player) event.getEntity(), "HEROIC_ARMOR_SET");
                if (reduceLevel > 0) {
                    event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * ((reduceLevel == 1) ? 0.9D : ((reduceLevel == 2) ? 0.85D : ((reduceLevel == 3) ? 0.8D : ((reduceLevel == 4) ? 0.75D : 1.0D)))));
                }
            }
            Player damaged = (Player) event.getEntity();
            ArmorSet damagedArmorSet = CosmicArmorSetsAPI.getArmorSet(damaged);
            if (damagedArmorSet != null) {
                damagedArmorSet.getArmorSetInfo().onPlayerDamaged(damaged, event);
            } else {
                AbilityArmorSet abilityArmorSet = CosmicArmorSetsAPI.getAbilityArmorSet(damaged);
                if (abilityArmorSet != null) {
                    abilityArmorSet.getArmorSetInfo().onPlayerDamaged(damaged, event);
                }
            }
            if (!damaged.hasMetadata("blackout")) {
                Map<ArmorSet, Integer> bonuses = CosmicArmorSetsAPI.getBonusCrystalsEquippedWithCorruptionIncluded(damaged, damager);
                if (!bonuses.isEmpty())
                    for (Map.Entry<ArmorSet, Integer> ent : bonuses.entrySet()) {
                        ent.getKey().getArmorSetInfo().onPlayerDamagedBonus(damaged, ent.getValue(), event);
                    }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player extraDamage = (Player) event.getEntity();
            ArmorSet set = CosmicArmorSetsAPI.getArmorSet(extraDamage);
            if (set != null && set.getArmorSetInfo() instanceof OtherPlayerEventListenerSet) {
                ((OtherPlayerEventListenerSet) set.getArmorSetInfo()).onPlayerEvent(event);
            } else if (set == null) {
                AbilityArmorSet abilityArmorSet = CosmicArmorSetsAPI.getCachedAbilityArmorSet(extraDamage);
                if (abilityArmorSet != null && abilityArmorSet.getArmorSetInfo() instanceof OtherPlayerEventListenerSet)
                    ((OtherPlayerEventListenerSet) abilityArmorSet.getArmorSetInfo()).onPlayerEvent(event);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamage(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            Player extraDamage = (Player) event.getEntity();
            ArmorSet set = CosmicArmorSetsAPI.getArmorSet(extraDamage);
            if (set != null && set.getArmorSetInfo() instanceof OtherPlayerEventListenerSet)
                ((OtherPlayerEventListenerSet) set.getArmorSetInfo()).onPlayerEvent(event);
        }
    }
}


