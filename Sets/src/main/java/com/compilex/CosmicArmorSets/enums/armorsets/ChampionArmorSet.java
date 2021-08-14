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
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_HELMET, (short) 0, "&6&lChampion Crown", CC.Gray + "The Champion Crown", "", "&6&lCHAMPION SET BONUS", "&6&l* &6You deal 40% more damage.", "&6&l* &6Champion Wrath Ability", "&6&l* &6Auto Bless.", "&6&l* &6You take 20% less damage.", "&7(&oRequires all 4 Champion items.&7)"), new LinkedList(Arrays.asList(EnchantmentAPI.getEnchantment("Alien Implants"), EnchantmentAPI.getEnchantment("Planetary Deathbringer"), EnchantmentAPI.getEnchantment("Drunk"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Hardened"), EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Dodge"), EnchantmentAPI.getEnchantment("Guardians"), EnchantmentAPI.getEnchantment("Sticky"), EnchantmentAPI.getEnchantment("Implants"), EnchantmentAPI.getEnchantment("Repair Guard")))));
    }

    @Override
    public ItemStack getChestplate(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_CHESTPLATE, (short) 0, "&6&lChampion Body", CC.Gray + "The Champion Body", "", "&6&lCHAMPION SET BONUS", "&6&l* &6You deal 40% more damage.", "&6&l* &6Champion Wrath Ability", "&6&l* &6Auto Bless.", "&6&l* &6You take 20% less damage.", "&7(&oRequires all 4 Champion items.&7)"), new LinkedList(Arrays.asList(EnchantmentAPI.getEnchantment("Godly Overload"), EnchantmentAPI.getEnchantment("Paladin Armored"), EnchantmentAPI.getEnchantment("Blood Lust"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Arrow Deflect"), EnchantmentAPI.getEnchantment("Dodge"), EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Aegis"), EnchantmentAPI.getEnchantment("Spirit Link"), EnchantmentAPI.getEnchantment("Leadership")))));
    }

    @Override
    public ItemStack getLeggings(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_LEGGINGS, (short) 0, "&6&lChampion Jeans", CC.Gray + "The Champion Jeans", "", "&6&lCHAMPION SET BONUS", "&6&l* &6You deal 40% more damage.", "&6&l* &6Champion Wrath Ability", "&6&l* &6Auto Bless.", "&6&l* &6You take 20% less damage.", "&7(&oRequires all 4 Champion items.&7)"), new LinkedList(Arrays.asList(EnchantmentAPI.getEnchantment("Paladin Armored"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Clarity"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Nature Wrath"), EnchantmentAPI.getEnchantment("Lucky"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Obsidianshield"), EnchantmentAPI.getEnchantment("Cactus"), EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Metaphysical"), EnchantmentAPI.getEnchantment("Death God"), EnchantmentAPI.getEnchantment("Dodge")))));
    }

    @Override
    public ItemStack getBoots(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_BOOTS, (short) 0, "&6&lChampion Kicks", CC.Gray + "The Champion Boots", "", "&6&lCHAMPION SET BONUS", "&6&l* &6You deal 40% more damage.", "&6&l* &6Champion Wrath Ability", "&6&l* &6Auto Bless.", "&6&l* &6You take 20% less damage.", "&7(&oRequires all 4 Champion items.&7)"), new LinkedList(Arrays.asList(EnchantmentAPI.getEnchantment("Ethereal Dodge"), EnchantmentAPI.getEnchantment("Divine Enlighted"), EnchantmentAPI.getEnchantment("Gears"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Ender Walker"), EnchantmentAPI.getEnchantment("Hardened"), EnchantmentAPI.getEnchantment("Rocket Escape"), EnchantmentAPI.getEnchantment("Springs"), EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Dodge"), EnchantmentAPI.getEnchantment("Sticky"), EnchantmentAPI.getEnchantment("Diminish")))));
    }

    @Override
    public List<String> getEquipDescription() {
        return TranslateList.translateList(ChatColor.GOLD + "" + ChatColor.BOLD + "",
                "&6&lCHAMPION SET BONUS: "
                , "&6&l➥ &6+40% DMG"
                , "&6&l➥ &6Enemies deal 20% less damage"
                , "&6&l➥ &6Champion Wrath Ability"
                , "&6&l➥ &6Auto Bless"
        , "");
    }

    @Override
    public boolean onPlayerDamaged(Player damaged, EntityDamageByEntityEvent event) {
        if (this.isBlockedOutpostWorld(damaged)) {
            return false;
        }
        if (!(event.getEntity() instanceof Player)) {
            return false;
        }
        if (event.getEntity() instanceof Player) {
            this.removeResistance((Player) event.getEntity());
        }
        if (event.isCancelled()) {
            return false;
        }
        this.removeResistance(damaged);
        event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.80);
        return true;
    }

    @Override
    public boolean onPlayerAttacked(Player damager, EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return false;
        }
        this.removeResistance(damager);
        if (this.isBlockedOutpostWorld(damager)) {
            return false;
        }
        if (event.getEntity() instanceof Player && CosmicArmorSetsAPI.hasInfiniteLuck((Player) event.getEntity(), 3, damager)) {
            return false;
        }
        event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.40);
        if (Math.random() <= 0.02) {
            this.activateChampionAbility(damager, (Player) event.getEntity());
        }
        return true;
    }

    @Override
    public boolean onPlayerDamagedBonus(Player damaged, int multiplier, EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return false;
        }
        if (event.isCancelled()) {
            return false;
        }
        if (this.isBlockedOutpostWorld(damaged)) {
            return false;
        }
        this.modifyDamage(damaged, event, -5, multiplier);
        return false;
    }

    @Override
    public boolean onPlayerAttackedBonus(Player damager, int applied, EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return false;
        }
        if (this.isBlockedOutpostWorld(damager)) {
            return false;
        }
        if (Math.random() <= 0.006 * (double) applied) {
            this.activateChampionAbility(damager, (Player) event.getEntity());
        }
        return this.modifyDamage(damager, event, 10, applied);
    }

    @Override
    public void onEquip(Player player) {
        PlayerPotionEffects.untrackPotionEffect(player, PotionEffectType.SLOW);
        PlayerPotionEffects.untrackPotionEffect(player, PotionEffectType.SLOW_DIGGING);
    }

    @Override
    public void onUnequip(Player player) {
        PlayerPotionEffects.untrackPotionEffect(player, PotionEffectType.SLOW);
        PlayerPotionEffects.untrackPotionEffect(player, PotionEffectType.SLOW_DIGGING);
    }

    public void activateChampionAbility(final Player player, final Player target) {
        if (target.hasMetadata("banshee_scrolls") || target.hasMetadata("bloodHoundNoBless") || player.hasMetadata("banshee_active")) {
            return;
        }


        player.sendMessage("");
        player.sendMessage(CC.translate("&6&l* CHAMPION WRATH PROC *"));
        player.sendMessage(CC.translate("&a&lBUFF: &cBanshee RKO Proc &aActivated"));
        player.sendMessage(CC.translate("&a&lBUFF: &cAnti Utility Proc &aActivated"));
        player.sendMessage(CC.translate("&a&lBUFF: &cWorld Destroyer Ticks &aActivated"));
        player.sendMessage(CC.translate("&4&lTARGET: &f") + target.getName());
        player.sendMessage("");
        target.sendMessage(CC.translate(("&6&l* CHAMPION WRATH &7[&c" + player.getName() + "&7]")));
        player.setMetadata("noDefenseProcs", new FixedMetadataValue(CosmicSkyInventoryPets.get(), true));
        player.setMetadata("immune_freeze", new FixedMetadataValue(CosmicSkyInventoryPets.get(), true));
        target.setMetadata("banshee_scrolls", new FixedMetadataValue(CosmicSkyInventoryPets.get(), true));
        target.playSound(target.getLocation(), Sound.BLAZE_DEATH, 10.0f, 10.0f);
        PlayerPotionEffects.trackPotionEffect(player, new SpecificCustomEnchantment(new Gears(), 5), new PotionEffect(PotionEffectType.SPEED, 160, 4));
        new BukkitRunnable() {

            public void run() {
                player.removeMetadata("noDefenseProcs", CosmicSkyInventoryPets.get());
                player.removeMetadata("immune_freeze", CosmicSkyInventoryPets.get());
                player.removeMetadata("banshee_scrolls", CosmicSkyInventoryPets.get());
                target.sendMessage(CC.translate(("&6&l* CHAMPION WRATH FROM &7" + player.getName() + " &c&lEXPIRED &6&l*")));
                PlayerPotionEffects.untrackPotionEffect(player, PotionEffectType.SPEED);
                PlayerPotionEffects.trackPotionEffect(player, new SpecificCustomEnchantment(new Gears(), 3), new PotionEffect(PotionEffectType.SPEED, 160, 3));
            }
        }.runTaskLater(CosmicSkyInventoryPets.get(), 160L);
    }

    @Override
    public void onPlayerEvent(Event paramEvent) {
    }
}

