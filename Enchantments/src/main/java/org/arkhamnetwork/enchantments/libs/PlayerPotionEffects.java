package org.arkhamnetwork.enchantments.libs;

import org.bukkit.entity.*;
import java.util.concurrent.*;
import java.util.function.*;
import org.arkhamnetwork.enchantments.cmds.*;
import org.arkhamnetwork.Arkkit.plugins.*;
import org.arkhamnetwork.Arkkit.utils.*;
import org.bukkit.potion.*;
import org.bukkit.*;
import java.util.*;
import org.arkhamnetwork.enchantments.*;
import com.compilex.CosmicDungeons.*;
import org.arkhamnetwork.enchantments.mastery.*;
import com.compilex.CosmicDungeons.dungeons.libs.*;
import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.listeners.*;

public class PlayerPotionEffects
{
    String playerName;
    UUID playerUUID;
    Player bukkitPlayer;
    ConcurrentHashMap<SpecificCustomEnchantment, CopyOnWriteArrayList<AbstractPotionEffect>> effects;
    
    public PlayerPotionEffects(final Player p) {
        this.bukkitPlayer = p;
        this.playerName = p.getName();
        this.playerUUID = p.getUniqueId();
        this.effects = new ConcurrentHashMap<SpecificCustomEnchantment, CopyOnWriteArrayList<AbstractPotionEffect>>();
    }
    
    public Player getPlayer() {
        return this.bukkitPlayer;
    }
    
    public void addPotionEffect(final SpecificCustomEnchantment ce, final PotionEffect pe) {
        this.addPotionEffect(ce, pe, null);
    }
    
    public void addPotionEffect(final SpecificCustomEnchantment ce, final PotionEffect pe, final Consumer<AbstractPotionEffect> consumer) {
        boolean hasBetterActiveEffect = false;
        for (final Map.Entry<SpecificCustomEnchantment, CopyOnWriteArrayList<AbstractPotionEffect>> effects : this.effects.entrySet()) {
            if (hasBetterActiveEffect) {
                break;
            }
            for (final AbstractPotionEffect ape : effects.getValue()) {
                if (ape.getEffectType().equals((Object)pe.getType()) && ape.getPotionLevel() >= pe.getAmplifier()) {
                    if (CommandApply.debug) {
                        Bukkit.getLogger().info("[PPE] Skipped adding of potionEffect from " + pe.toString() + " to " + this.getPlayerName() + " from " + ce.toString() + " due to existing LVL:" + ape.getPotionLevel() + " of effect from " + effects.getKey().toString() + "!");
                    }
                    hasBetterActiveEffect = true;
                    break;
                }
            }
        }
        AbstractPotionEffect retr;
        if (this.effects.containsKey(ce)) {
            final CopyOnWriteArrayList<AbstractPotionEffect> effects2 = this.effects.get(ce);
            effects2.add(retr = new AbstractPotionEffect(pe.getType(), pe.getAmplifier()));
            this.effects.put(ce, effects2);
        }
        else {
            final ConcurrentHashMap<SpecificCustomEnchantment, CopyOnWriteArrayList<AbstractPotionEffect>> effects3 = this.effects;
            final AbstractPotionEffect[] a = { null };
            retr = (a[0] = new AbstractPotionEffect(pe.getType(), pe.getAmplifier()));
            effects3.put(ce, new CopyOnWriteArrayList<AbstractPotionEffect>(Arrays.asList(a)));
        }
        if (ArkPlugins.PARKOUR_REGION.getArkPlugin().isRunning()) {
            final String regionName = WorldGuardUtils.getRegionName(this.getPlayer().getLocation());
            if (regionName.contains("parkour") && !regionName.contains("enchants")) {
                return;
            }
        }
        if (!hasBetterActiveEffect) {
            if (consumer != null) {
                consumer.accept(retr);
            }
            this.applyPotionEffects(true);
            if (CommandApply.debug) {
                Bukkit.getLogger().info("[PPE] Added " + pe.getType().getName() + ":" + pe.getAmplifier() + " to " + this.getPlayerName() + " trackedEffects of " + ce.toString());
            }
        }
    }
    
    public void removePotionEffect(final SpecificCustomEnchantment ce, final PotionEffectType effectType) {
        final CopyOnWriteArrayList<AbstractPotionEffect> pEffects = this.effects.containsKey(ce) ? this.effects.get(ce) : null;
        if (pEffects != null) {
            for (final AbstractPotionEffect effect : pEffects) {
                if (effect.getEffectType().equals((Object)effectType)) {
                    this.getPlayer().removePotionEffect(effect.getEffectType());
                    pEffects.remove(effect);
                    if (!CommandApply.debug) {
                        continue;
                    }
                    Bukkit.getLogger().info("[PPE] Removed " + effect.toString() + " from " + this.getPlayerName() + " due to (PARTIAL) removal of " + ce.toString());
                }
            }
            if (pEffects.size() > 0) {
                this.effects.put(ce, pEffects);
            }
            else {
                this.effects.remove(ce);
            }
        }
    }
    
    public void removePotionEffect(final PotionEffectType effectType, final boolean alertChanges) {
        for (final Map.Entry<SpecificCustomEnchantment, CopyOnWriteArrayList<AbstractPotionEffect>> data : this.effects.entrySet()) {
            if (data.getValue() != null) {
                final CopyOnWriteArrayList<AbstractPotionEffect> pEffects = data.getValue();
                for (final AbstractPotionEffect effect : pEffects) {
                    if (effect.getEffectType().equals((Object)effectType)) {
                        if (alertChanges) {
                            this.getPlayer().sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "[-] " + ChatColor.RED + "" + data.getKey().getEnchantment().name() + " " + RomanNumber.toRoman(data.getKey().getEnchantmentLevel()) + ":" + ChatColor.GRAY + " removing " + effectType.getName() + " " + RomanNumber.toRoman(effect.getPotionLevel() + 1));
                        }
                        pEffects.remove(effect);
                        if (!CommandApply.debug) {
                            continue;
                        }
                        Bukkit.getLogger().info("[PPE] Removed " + effect.toString() + " of " + data.getKey().toString() + " from " + this.getPlayerName() + " due to SPECIFIC untracking.");
                    }
                }
                if (pEffects.size() > 0) {
                    this.effects.put(data.getKey(), pEffects);
                }
                else {
                    this.effects.remove(data.getKey());
                }
                this.getPlayer().removePotionEffect(effectType);
            }
        }
    }
    
    public void removePotionEffect(final SpecificCustomEnchantment ce) {
        final CopyOnWriteArrayList<AbstractPotionEffect> effects = this.effects.get(ce);
        if (effects != null) {
            final HashSet<PotionEffectType> potionEffectsToNotRemove = new HashSet<PotionEffectType>();
            if (effects.size() > 1) {
                final HashMap<String, Integer> potionEffectCounts = new HashMap<String, Integer>();
                for (final AbstractPotionEffect effect : effects) {
                    potionEffectCounts.put(effect.toString(), potionEffectCounts.containsKey(effect.toString()) ? (potionEffectCounts.get(effect.toString()) + 1) : 1);
                    if (potionEffectCounts.get(effect.toString()) > 1) {
                        potionEffectsToNotRemove.add(effect.getEffectType());
                    }
                }
            }
            this.effects.remove(ce);
            final int highestLevelOfEnchantment = this.getHighestEnchantmentLevel(ce.getEnchantment());
            if (highestLevelOfEnchantment < ce.getEnchantmentLevel()) {
                for (final AbstractPotionEffect effect : effects) {
                    if (!potionEffectsToNotRemove.contains(effect.getEffectType())) {
                        this.getPlayer().removePotionEffect(effect.getEffectType());
                        if (!CommandApply.debug) {
                            continue;
                        }
                        Bukkit.getLogger().info("[PPE] Removed " + effect.toString() + " from " + this.getPlayerName() + " due to removal of " + ce.toString());
                    }
                }
            }
            if (potionEffectsToNotRemove.size() > 0) {
                for (int i = 0; i < effects.size(); ++i) {
                    final PotionEffectType effectType = effects.get(i).getEffectType();
                    if (potionEffectsToNotRemove.contains(effectType)) {
                        effects.remove(i);
                        potionEffectsToNotRemove.remove(effectType);
                    }
                }
                this.effects.put(new SpecificCustomEnchantment(ce.getEnchantment(), ce.getEnchantmentLevel()), effects);
            }
            if (highestLevelOfEnchantment > 0 || this.effects.containsKey(ce)) {
                this.applyPotionEffects(true);
            }
        }
    }
    
    public void applyPotionEffects(final boolean alertChanges) {
        final Player p = this.getPlayer();
        if (p == null) {
            return;
        }
        final boolean parkourRegion = ArkPlugins.PARKOUR_REGION.getArkPlugin().isRunning();
        String regionName = null;
        if (parkourRegion) {
            regionName = WorldGuardUtils.getRegionName(p.getLocation());
            if (regionName.contains("parkour") && !regionName.contains("enchants")) {
                return;
            }
        }
        if (Enchantments.getInstance().dungeonsEnabled) {
            final DungeonPlayer dp = DungeonsAPI.get().getPlayerDungeonData(p);
            if (dp != null && dp.getCurrentDungeon() != null) {
                if (regionName == null) {
                    regionName = WorldGuardUtils.getRegionName(p.getLocation());
                }
                if (regionName.startsWith("parkour") || regionName.contains("maze") || regionName.equals("maze")) {
                    return;
                }
            }
        }
        for (final Map.Entry<SpecificCustomEnchantment, CopyOnWriteArrayList<AbstractPotionEffect>> data : this.effects.entrySet()) {
            for (AbstractPotionEffect ape : data.getValue()) {
                if (ape.getEffectType() == PotionEffectType.HEALTH_BOOST && MasteryMortalCoil.isAffected(p)) {
                    final int mortalCoilLevel = MasteryMortalCoil.getMortalCoilLevel(p);
                    final int allowedHealthBoostLevel = ape.getPotionLevel() - (mortalCoilLevel + 1);
                    if (allowedHealthBoostLevel <= 0) {
                        continue;
                    }
                    ape = new AbstractPotionEffect(PotionEffectType.HEALTH_BOOST, allowedHealthBoostLevel);
                }
                if (!p.hasPotionEffect(ape.getEffectType())) {
                    p.addPotionEffect(new PotionEffect(ape.getEffectType(), Integer.MAX_VALUE, ape.getPotionLevel(), true));
                    if (!alertChanges) {
                        continue;
                    }
                    p.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "[+] " + ChatColor.GREEN + "" + data.getKey().getEnchantment().name() + " " + RomanNumber.toRoman(data.getKey().getEnchantmentLevel()) + ":" + ChatColor.GRAY + " applying " + ape.getEffectType().getName() + " " + RomanNumber.toRoman(ape.getPotionLevel() + 1));
                }
                else {
                    for (final PotionEffect pe : p.getActivePotionEffects()) {
                        if (pe.getType().equals((Object)ape.getEffectType()) && ape.getPotionLevel() > pe.getAmplifier() && pe.getDuration() > 100000) {
                            p.removePotionEffect(ape.getEffectType());
                            p.addPotionEffect(new PotionEffect(ape.getEffectType(), Integer.MAX_VALUE, ape.getPotionLevel(), true));
                            if (!alertChanges) {
                                continue;
                            }
                            if (data.getKey().getEnchantment().name().equals("Gears") && data.getKey().getEnchantmentLevel() == 4) {
                                continue;
                            }
                            p.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "[+] " + ChatColor.GREEN + "" + data.getKey().getEnchantment().name() + " " + RomanNumber.toRoman(data.getKey().getEnchantmentLevel()) + ":" + ChatColor.GRAY + " applying " + ape.getEffectType().getName() + " " + RomanNumber.toRoman(ape.getPotionLevel() + 1));
                        }
                    }
                }
            }
        }
    }
    
    public boolean hasPotionEffect(final PotionEffectType pet, final int level) {
        for (final CopyOnWriteArrayList<AbstractPotionEffect> activeEffects : this.effects.values()) {
            for (final AbstractPotionEffect ape : activeEffects) {
                if (ape.getEffectType() == pet && ape.getPotionLevel() == level) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private int getHighestEnchantmentLevel(final CustomEnchantment ce) {
        int highest = 0;
        for (final SpecificCustomEnchantment sce : this.effects.keySet()) {
            if (sce.getEnchantment().equals((Object)ce) && sce.getEnchantmentLevel() > highest) {
                highest = sce.getEnchantmentLevel();
            }
        }
        return highest;
    }
    
    public static PlayerPotionEffects getTrackedPotionEffects(final Player p) {
        return PlayerPotionEffectListener.getPlayerPotionEffectTracker().computeIfAbsent(p.getUniqueId(), def -> new PlayerPotionEffects(p));
    }
    
    public static void trackPotionEffect(final Player p, final SpecificCustomEnchantment ce, final PotionEffect pe) {
        getTrackedPotionEffects(p).addPotionEffect(ce, pe);
    }
    
    public static void trackPotionEffect(final Player p, final SpecificCustomEnchantment ce, final PotionEffect pe, final Consumer<AbstractPotionEffect> addedEffectConsumer) {
        getTrackedPotionEffects(p).addPotionEffect(ce, pe, addedEffectConsumer);
    }
    
    public static void untrackPotionEffect(final Player p, final SpecificCustomEnchantment ce) {
        final PlayerPotionEffects effects = getTrackedPotionEffects(p);
        if (effects == null) {
            return;
        }
        effects.removePotionEffect(ce);
    }
    
    public static void untrackPotionEffect(final Player p, final SpecificCustomEnchantment ce, final PotionEffectType specificEffect) {
        final PlayerPotionEffects effects = getTrackedPotionEffects(p);
        if (effects == null) {
            return;
        }
        effects.removePotionEffect(ce, specificEffect);
    }
    
    public static void untrackPotionEffect(final Player p, final PotionEffectType specificEffect) {
        final PlayerPotionEffects effects = getTrackedPotionEffects(p);
        if (effects == null) {
            return;
        }
        effects.removePotionEffect(specificEffect, true);
    }
    
    public static void applyPotionEffects(final Player p, final boolean alertChanges) {
        getTrackedPotionEffects(p).applyPotionEffects(alertChanges);
    }
    
    public static void resetPotionTracker(final Player p) {
        getTrackedPotionEffects(p).effects.clear();
    }
    
    public String getPlayerName() {
        return this.playerName;
    }
    
    public UUID getPlayerUUID() {
        return this.playerUUID;
    }
    
    public Player getBukkitPlayer() {
        return this.bukkitPlayer;
    }
    
    public void setBukkitPlayer(final Player bukkitPlayer) {
        this.bukkitPlayer = bukkitPlayer;
    }
    
    public ConcurrentHashMap<SpecificCustomEnchantment, CopyOnWriteArrayList<AbstractPotionEffect>> getEffects() {
        return this.effects;
    }
}
