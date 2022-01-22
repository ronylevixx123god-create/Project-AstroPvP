/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.net.astropvp.armorsets.utils.UpgradeUtils
 *  org.bukkit.Material
 *  org.bukkit.entity.Arrow
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.event.Event
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityChangeBlockEvent
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageModifier
 *  org.bukkit.event.entity.FoodLevelChangeEvent
 *  org.bukkit.event.player.PlayerChangedWorldEvent
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.plugin.Plugin
 */
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
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.bryside.weaponrunes.enums.WeaponBonusType;
import me.bryside.weaponrunes.utils.WeaponBonusUtils;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
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
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.Objects;

public class SetBonusListener
        implements Listener {
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        if (event.getMessage() != null && event.getMessage().equals("/help armorset")) {
            event.setMessage("/armorset help");
        }
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
        if (armor != null) {
            armor.getSet().getArmorSetInfo().onUnequip(player);
        }
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
    public void onPlayerDamage(EntityDamageByEntityEvent event) throws Exception {
        double damageAtStart = event.getDamage(EntityDamageEvent.DamageModifier.BASE);
        Player damager = null;
        if (event.getDamager() instanceof Arrow && ((Projectile) event.getDamager()).getShooter() instanceof Player) {
            damager = (Player) ((Projectile) event.getDamager()).getShooter();
        } else if (event.getDamager() instanceof Player) {
            damager = (Player) event.getDamager();
        }
        if (damager != null) {
            Map<ArmorSet, Integer> bonuses = CosmicArmorSetsAPI.getBonusCrystalsEquippedWithCorruptionIncluded(damager, event.getEntity());
            AbilityArmorSet set;
            ArmorSet damagerArmorSet = CosmicArmorSetsAPI.getArmorSet(damager);
            if (bonuses == null) return;

            if (AstroItemSkinsAPI.hasSkinEquipped((Player) event.getDamager(), SkinType.COSMIC_CITIZEN_BOOTS))
                event.setDamage(event.getDamage() * 1.2);
            if (damagerArmorSet != null && (event.getDamager() instanceof Player || damagerArmorSet.getArmorSetInfo().supportsBowDamage() && event.getDamager() instanceof Projectile)) {
                damagerArmorSet.getArmorSetInfo().onPlayerAttacked(damager, event);
            }
            if (damagerArmorSet == null && (set = CosmicArmorSetsAPI.getAbilityArmorSet(damager)) != null && (event.getDamager() instanceof Player || set.getArmorSetInfo().supportsBowDamage() && event.getDamager() instanceof Projectile)) {
                set.getArmorSetInfo().onPlayerAttacked(damager, event);
            }
            if (!(bonuses).isEmpty()) {
                for (Map.Entry<ArmorSet, Integer> ent : bonuses.entrySet()) {
                    ent.getKey().getArmorSetInfo().onPlayerAttackedBonus(damager, ent.getValue(), event);
                }
            }
        }
        if (event.getEntity() instanceof Player) {
            Player damaged = (Player) event.getEntity();
            ArmorSet damagedArmorSet = CosmicArmorSetsAPI.getArmorSet(damaged);
            Map<ArmorSet, Integer> bonuses = CosmicArmorSetsAPI.getBonusCrystalsEquippedWithCorruptionIncluded(damaged, damager);

            if (damagedArmorSet != null) {
                damagedArmorSet.getArmorSetInfo().onPlayerDamaged(damaged, event);
            } else {
                AbilityArmorSet abilityArmorSet = CosmicArmorSetsAPI.getAbilityArmorSet(damaged);
                if (abilityArmorSet != null) {
                    abilityArmorSet.getArmorSetInfo().onPlayerDamaged(damaged, event);
                }
            }
            if (CosmicArmorSetsAPI.getBonusCrystalsEquippedWithCorruptionIncluded(damaged, damager) == null)
                return;
            if (damager == null) return;
            if (damager.getItemInHand() != null) {
                if (WeaponBonusUtils.hasWeaponBonus(damager.getItemInHand())) {
                    if (WeaponBonusUtils.getWeaponBonusByItem(damager.getItemInHand()) == WeaponBonusType.DEMON && Math.random() < 0.05) {
                        assert bonuses != null;
                        if (bonuses.containsKey(ArmorSet.ENGINEER)) {
                            if (bonuses.get(ArmorSet.ENGINEER) == 1) {
                                bonuses.remove(ArmorSet.ENGINEER);
                            } else {
                                bonuses.put(ArmorSet.ENGINEER, bonuses.get(ArmorSet.ENGINEER) - 1);
                            }
                        }
                        event.getDamager().sendMessage(CC.translate("&6&l* &4&lDemon Weaponbonus &7Blocked " + event.getEntity().getName() + "'s &6Engineer Crystal &l*"));
                    }
                }
            }
            if (bonuses != null && !bonuses.isEmpty()) {
                for (Map.Entry<ArmorSet, Integer> ent : bonuses.entrySet()) {
                    if (ent.getKey() == ArmorSet.ENGINEER && WeaponBonusUtils.hasWeaponBonus(damager.getItemInHand()) && WeaponBonusUtils.getWeaponBonusByItem(damager.getItemInHand()) == WeaponBonusType.DEMON && Math.random() < 0.15)
                        continue;
                    ent.getKey().getArmorSetInfo().onPlayerDamagedBonus(damaged, ent.getValue(), event);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            AbilityArmorSet abilityArmorSet;
            Player extraDamage = (Player) event.getEntity();
            ArmorSet set = CosmicArmorSetsAPI.getArmorSet(extraDamage);
            if (set != null && set.getArmorSetInfo() instanceof OtherPlayerEventListenerSet) {
                ((OtherPlayerEventListenerSet) set.getArmorSetInfo()).onPlayerEvent(event);
            } else if (set == null && (abilityArmorSet = CosmicArmorSetsAPI.getCachedAbilityArmorSet(extraDamage)) != null && abilityArmorSet.getArmorSetInfo() instanceof OtherPlayerEventListenerSet) {
                ((OtherPlayerEventListenerSet) abilityArmorSet.getArmorSetInfo()).onPlayerEvent(event);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamage(FoodLevelChangeEvent event) {
        ArmorSet set;
        if (event.getEntity() instanceof Player && (set = CosmicArmorSetsAPI.getArmorSet((Player) event.getEntity())) != null && set.getArmorSetInfo() instanceof OtherPlayerEventListenerSet) {
            ((OtherPlayerEventListenerSet) set.getArmorSetInfo()).onPlayerEvent(event);
        }
    }
}

