/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.rit.sucy.CustomEnchantment
 *  com.rit.sucy.EnchantmentAPI
 *  me.ifamasssxd.cosmicsky.inventorypets.CosmicSkyInventoryPets
 *  org.arkhamnetwork.enchantments.enchantments.armor.Gears
 *  org.arkhamnetwork.enchantments.libs.PlayerPotionEffects
 *  org.arkhamnetwork.enchantments.libs.SpecificCustomEnchantment
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageModifier
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.scheduler.BukkitRunnable
 */
package com.compilex.CosmicArmorSets.enums.armorsets;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSetInfo;
import com.compilex.CosmicArmorSets.enums.OtherPlayerEventListenerSet;
import com.compilex.CosmicArmorSets.utils.ItemUtils;
import com.compilex.CosmicArmorSets.utils.TranslateList;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import me.ifamasssxd.cosmicsky.inventorypets.CosmicSkyInventoryPets;
import org.arkhamnetwork.enchantments.enchantments.armor.Gears;
import org.arkhamnetwork.enchantments.libs.PlayerPotionEffects;
import org.arkhamnetwork.enchantments.libs.SpecificCustomEnchantment;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ChampionArmorSet
        extends ArmorSetInfo
        implements OtherPlayerEventListenerSet {
    public static List<Block> blockList = new ArrayList<Block>();

    @Override
    public ItemStack getHelmet(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_HELMET, (short) 0, "&6&lChampion Crown", CC.Gray + "The Champion Crown", "", "&6&lCHAMPION SET BONUS", "&6&l* &6You deal 40% more damage.", "&6&l* &6Champion Wrath Ability", "&6&l* &6Auto Bless.", "&6&l* &6You take 20% less damage.", "&7(&oRequires all 4 Champion items.&7)"), new LinkedList<Object>(Arrays.asList((Object[]) new CustomEnchantment[]{EnchantmentAPI.getEnchantment((String) "Alien Implants"), EnchantmentAPI.getEnchantment((String) "Planetary Deathbringer"), EnchantmentAPI.getEnchantment((String) "Drunk"), EnchantmentAPI.getEnchantment((String) "Enlighted"), EnchantmentAPI.getEnchantment((String) "Armored"), EnchantmentAPI.getEnchantment((String) "Tank"), EnchantmentAPI.getEnchantment((String) "Angelic"), EnchantmentAPI.getEnchantment((String) "Hardened"), EnchantmentAPI.getEnchantment((String) "Valor"), EnchantmentAPI.getEnchantment((String) "Dodge"), EnchantmentAPI.getEnchantment((String) "Guardians"), EnchantmentAPI.getEnchantment((String) "Sticky"), EnchantmentAPI.getEnchantment((String) "Implants"), EnchantmentAPI.getEnchantment((String) "Repair Guard")}))));
    }

    @Override
    public ItemStack getChestplate(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_CHESTPLATE, (short) 0, "&6&lChampion Body", CC.Gray + "The Champion Body", "", "&6&lCHAMPION SET BONUS", "&6&l* &6You deal 40% more damage.", "&6&l* &6Champion Wrath Ability", "&6&l* &6Auto Bless.", "&6&l* &6You take 20% less damage.", "&7(&oRequires all 4 Champion items.&7)"), new LinkedList<Object>(Arrays.asList((Object[]) new CustomEnchantment[]{EnchantmentAPI.getEnchantment((String) "Godly Overload"), EnchantmentAPI.getEnchantment((String) "Paladin Armored"), EnchantmentAPI.getEnchantment((String) "Blood Lust"), EnchantmentAPI.getEnchantment((String) "Enlighted"), EnchantmentAPI.getEnchantment((String) "Deathbringer"), EnchantmentAPI.getEnchantment((String) "Armored"), EnchantmentAPI.getEnchantment((String) "Tank"), EnchantmentAPI.getEnchantment((String) "Angelic"), EnchantmentAPI.getEnchantment((String) "Arrow Deflect"), EnchantmentAPI.getEnchantment((String) "Dodge"), EnchantmentAPI.getEnchantment((String) "Valor"), EnchantmentAPI.getEnchantment((String) "Aegis"), EnchantmentAPI.getEnchantment((String) "Spirit Link"), EnchantmentAPI.getEnchantment((String) "Leadership")}))));
    }

    @Override
    public ItemStack getLeggings(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_LEGGINGS, (short) 0, "&6&lChampion Jeans", CC.Gray + "The Champion Jeans", "", "&6&lCHAMPION SET BONUS", "&6&l* &6You deal 40% more damage.", "&6&l* &6Champion Wrath Ability", "&6&l* &6Auto Bless.", "&6&l* &6You take 20% less damage.", "&7(&oRequires all 4 Champion items.&7)"), new LinkedList<Object>(Arrays.asList((Object[]) new CustomEnchantment[]{EnchantmentAPI.getEnchantment((String) "Paladin Armored"), EnchantmentAPI.getEnchantment((String) "Enlighted"), EnchantmentAPI.getEnchantment((String) "Clarity"), EnchantmentAPI.getEnchantment((String) "Deathbringer"), EnchantmentAPI.getEnchantment((String) "Nature Wrath"), EnchantmentAPI.getEnchantment((String) "Lucky"), EnchantmentAPI.getEnchantment((String) "Armored"), EnchantmentAPI.getEnchantment((String) "Angelic"), EnchantmentAPI.getEnchantment((String) "Obsidianshield"), EnchantmentAPI.getEnchantment((String) "Cactus"), EnchantmentAPI.getEnchantment((String) "Valor"), EnchantmentAPI.getEnchantment((String) "Metaphysical"), EnchantmentAPI.getEnchantment((String) "Death God"), EnchantmentAPI.getEnchantment((String) "Dodge")}))));
    }

    @Override
    public ItemStack getBoots(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_BOOTS, (short) 0, "&6&lChampion Kicks", CC.Gray + "The Champion Boots", "", "&6&lCHAMPION SET BONUS", "&6&l* &6You deal 40% more damage.", "&6&l* &6Champion Wrath Ability", "&6&l* &6Auto Bless.", "&6&l* &6You take 20% less damage.", "&7(&oRequires all 4 Champion items.&7)"), new LinkedList<Object>(Arrays.asList((Object[]) new CustomEnchantment[]{EnchantmentAPI.getEnchantment((String) "Ethereal Dodge"), EnchantmentAPI.getEnchantment((String) "Divine Enlighted"), EnchantmentAPI.getEnchantment((String) "Gears"), EnchantmentAPI.getEnchantment((String) "Deathbringer"), EnchantmentAPI.getEnchantment((String) "Tank"), EnchantmentAPI.getEnchantment((String) "Angelic"), EnchantmentAPI.getEnchantment((String) "Ender Walker"), EnchantmentAPI.getEnchantment((String) "Hardened"), EnchantmentAPI.getEnchantment((String) "Rocket Escape"), EnchantmentAPI.getEnchantment((String) "Springs"), EnchantmentAPI.getEnchantment((String) "Valor"), EnchantmentAPI.getEnchantment((String) "Dodge"), EnchantmentAPI.getEnchantment((String) "Sticky"), EnchantmentAPI.getEnchantment((String) "Diminish")}))));
    }

    @Override
    public List<String> getEquipDescription() {
        return TranslateList.translateList((Object) ChatColor.GOLD + "" + (Object) ChatColor.BOLD + "CHAMPION SET BONUS: ", "\u00a76\u00a7l* \u00a76+40% DMG", "\u00a76\u00a7l*\u00a76 Enemies deal 20% less damage", "\u00a76\u00a7l*\u00a76 Champion Wrath Ability", "\u00a76\u00a7l* \u00a76Auto Bless");
    }

    @Override
    public boolean onPlayerDamaged(Player damaged, EntityDamageByEntityEvent event) {
        if (this.isBlockedOutpostWorld((Entity) damaged)) {
            return false;
        }
        if (event.getEntity() instanceof Player) {
            this.removeResistance((Player) event.getEntity());
        }
        if (event.isCancelled()) {
            return false;
        }
        this.removeResistance(damaged);
        event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.7);
        return true;
    }

    @Override
    public boolean onPlayerAttacked(Player damager, EntityDamageByEntityEvent event) {
        this.removeResistance(damager);
        if (this.isBlockedOutpostWorld((Entity) damager)) {
            return false;
        }
        if (event.getEntity() instanceof Player && CosmicArmorSetsAPI.hasInfiniteLuck((Player) event.getEntity(), 3, damager)) {
            return false;
        }
        event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.45);
        if (Math.random() <= 0.05) {
            this.activateChampionAbility(damager, (Player) event.getEntity());
        }
        return true;
    }

    @Override
    public boolean onPlayerDamagedBonus(Player damaged, int multiplier, EntityDamageByEntityEvent event) {
        if (event.isCancelled()) {
            return false;
        }
        if (this.isBlockedOutpostWorld((Entity) damaged)) {
            return false;
        }
        this.modifyDamage(damaged, event, -5.0, multiplier);
        return false;
    }

    @Override
    public boolean onPlayerAttackedBonus(Player damager, int applied, EntityDamageByEntityEvent event) {
        if (this.isBlockedOutpostWorld((Entity) damager)) {
            return false;
        }
        if (Math.random() <= 0.005 * (double) applied) {
            this.activateChampionAbility(damager, (Player) event.getEntity());
        }
        return this.modifyDamage(damager, event, 12.0, applied);
    }

    @Override
    public void onEquip(Player player) {
        PlayerPotionEffects.untrackPotionEffect((Player) player, (PotionEffectType) PotionEffectType.SLOW);
        PlayerPotionEffects.untrackPotionEffect((Player) player, (PotionEffectType) PotionEffectType.SLOW_DIGGING);
    }

    @Override
    public void onUnequip(Player player) {
        PlayerPotionEffects.untrackPotionEffect((Player) player, (PotionEffectType) PotionEffectType.SLOW);
        PlayerPotionEffects.untrackPotionEffect((Player) player, (PotionEffectType) PotionEffectType.SLOW_DIGGING);
    }

    public void activateChampionAbility(final Player player, final Player target) {
        player.sendMessage("");
        player.sendMessage(CC.translate((String) "&6&l* CHAMPION WRATH PROC *"));
        player.sendMessage(CC.translate((String) "&a&lBUFF: &cBanshee RKO Proc &aActivated"));
        player.sendMessage(CC.translate((String) "&a&lBUFF: &cAnti Utility Proc &aActivated"));
        player.sendMessage(CC.translate((String) "&a&lBUFF: &cWorld Destroyer Ticks &aActivated"));
        player.sendMessage(CC.translate((String) "&4&lTARGET: &f") + target.getName());
        player.sendMessage("");
        target.sendMessage(CC.translate((String) ("&6&l* CHAMPION WRATH &7[&c" + player.getName() + "&7]")));
        player.setMetadata("immune_freeze", (MetadataValue) new FixedMetadataValue((Plugin) CosmicSkyInventoryPets.get(), (Object) true));
        player.setMetadata("banshee_active", (MetadataValue) new FixedMetadataValue((Plugin) CosmicSkyInventoryPets.get(), (Object) true));
        target.setMetadata("noDefenseProcs", (MetadataValue) new FixedMetadataValue((Plugin) CosmicSkyInventoryPets.get(), (Object) true));
        target.setMetadata("bansheed", (MetadataValue) new FixedMetadataValue((Plugin) CosmicSkyInventoryPets.get(), (Object) true));
        target.setMetadata("championed", (MetadataValue) new FixedMetadataValue((Plugin) CosmicSkyInventoryPets.get(), (Object) true));
        target.playSound(target.getLocation(), Sound.BLAZE_DEATH, 10.0f, 10.0f);
        PlayerPotionEffects.trackPotionEffect((Player) player, (SpecificCustomEnchantment) new SpecificCustomEnchantment((CustomEnchantment) new Gears(), 5), (PotionEffect) new PotionEffect(PotionEffectType.SPEED, 160, 4));
        new BukkitRunnable() {

            public void run() {
                player.removeMetadata("immune_freeze", (Plugin) CosmicSkyInventoryPets.get());
                player.removeMetadata("banshee_active", (Plugin) CosmicSkyInventoryPets.get());
                target.removeMetadata("noDefenseProcs", (Plugin) CosmicSkyInventoryPets.get());
                target.removeMetadata("bansheed", (Plugin) CosmicSkyInventoryPets.get());
                target.removeMetadata("championed", (Plugin) CosmicSkyInventoryPets.get());
                target.sendMessage(CC.translate((String) ("&6&l* CHAMPION WRATH FROM &7" + player.getName() + " &c&lEXPIRED &6&l*")));
                PlayerPotionEffects.untrackPotionEffect((Player) player, (PotionEffectType) PotionEffectType.SPEED);
                PlayerPotionEffects.trackPotionEffect((Player) player, (SpecificCustomEnchantment) new SpecificCustomEnchantment((CustomEnchantment) new Gears(), 3), (PotionEffect) new PotionEffect(PotionEffectType.SPEED, 160, 3));
            }
        }.runTaskLater((Plugin) CosmicSkyInventoryPets.get(), 160L);
    }

    @Override
    public void onPlayerEvent(Event paramEvent) {
    }
}

