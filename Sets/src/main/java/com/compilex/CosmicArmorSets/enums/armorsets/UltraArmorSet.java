/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.rit.sucy.CustomEnchantment
 *  com.rit.sucy.EnchantmentAPI
 *  me.ifamasssxd.cosmicsky.inventorypets.CosmicSkyInventoryPets
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
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
 *  org.bukkit.scheduler.BukkitRunnable
 */
package com.compilex.CosmicArmorSets.enums.armorsets;

import com.compilex.CosmicArmorSets.enums.ArmorSetInfo;
import com.compilex.CosmicArmorSets.enums.OtherPlayerEventListenerSet;
import com.compilex.CosmicArmorSets.utils.ItemUtils;
import com.compilex.CosmicArmorSets.utils.TranslateList;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.rit.sucy.EnchantmentAPI;
import me.ifamasssxd.cosmicsky.inventorypets.CosmicSkyInventoryPets;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class UltraArmorSet
        extends ArmorSetInfo
        implements OtherPlayerEventListenerSet {
    public static List<Block> blockList = new ArrayList<Block>();

    @Override
    public ItemStack getHelmet(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_HELMET, (short) 0, "&7&lUltra Helmet", CC.Gray + "The Ultra Leggings", "", "&7&lULTRA SET BONUS", "&7&l* &7You deal 35% more damage.", "&7&l* &7Enemies deal 20% less damage to you.", "&7&l* &740% Holy Ultra Negation.", "&7&l* &710% Chance to proc Ultra Rage.", "&7&l* &7x1.5 World Destroyer Duration.", "&7&l* &750% Chance to Bypass Utility Belt.", "&7(&oRequires all 4 Ultra items.&7)"), new LinkedList(Arrays.asList(EnchantmentAPI.getEnchantment("Godly Overload"), EnchantmentAPI.getEnchantment("Paladin Armored"), EnchantmentAPI.getEnchantment("Blood Lust"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Arrow Deflect"), EnchantmentAPI.getEnchantment("Dodge"), EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Aegis"), EnchantmentAPI.getEnchantment("Spirit Link"), EnchantmentAPI.getEnchantment("Leadership")))));
    }

    @Override
    public ItemStack getChestplate(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_CHESTPLATE, (short) 0, "&7&lUltra Body", CC.Gray + "The Ultra Leggings", "", "&7&lULTRA SET BONUS", "&7&l* &7You deal 35% more damage.", "&7&l* &7Enemies deal 20% less damage to you.", "&7&l* &740% Holy Ultra Negation.", "&7&l* &710% Chance to proc Ultra Rage.", "&7&l* &7x1.5 World Destroyer Duration.", "&7&l* &750% Chance to Bypass Utility Belt.", "&7(&oRequires all 4 Ultra items.&7)"), new LinkedList(Arrays.asList(EnchantmentAPI.getEnchantment("Godly Overload"), EnchantmentAPI.getEnchantment("Paladin Armored"), EnchantmentAPI.getEnchantment("Blood Lust"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Arrow Deflect"), EnchantmentAPI.getEnchantment("Dodge"), EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Aegis"), EnchantmentAPI.getEnchantment("Spirit Link"), EnchantmentAPI.getEnchantment("Leadership")))));
    }

    @Override
    public ItemStack getLeggings(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_LEGGINGS, (short) 0, "&7&lUltra Jeans", CC.Gray + "The Ultra Leggings", "", "&7&lULTRA SET BONUS", "&7&l* &7You deal 35% more damage.", "&7&l* &7Enemies deal 20% less damage to you.", "&7&l* &740% Holy Ultra Negation.", "&7&l* &710% Chance to proc Ultra Rage.", "&7&l* &7x1.5 World Destroyer Duration.", "&7&l* &750% Chance to Bypass Utility Belt.", "&7(&oRequires all 4 Ultra items.&7)"), new LinkedList(Arrays.asList(EnchantmentAPI.getEnchantment("Godly Overload"), EnchantmentAPI.getEnchantment("Paladin Armored"), EnchantmentAPI.getEnchantment("Blood Lust"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Arrow Deflect"), EnchantmentAPI.getEnchantment("Dodge"), EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Aegis"), EnchantmentAPI.getEnchantment("Spirit Link"), EnchantmentAPI.getEnchantment("Leadership")))));
    }

    @Override
    public ItemStack getBoots(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_BOOTS, (short) 0, "&7&lUltra Kicks", CC.Gray + "The Ultra Leggings", "", "&7&lULTRA SET BONUS", "&7&l* &7You deal 35% more damage.", "&7&l* &7Enemies deal 20% less damage to you.", "&7&l* &740% Holy Ultra Negation.", "&7&l* &710% Chance to proc Ultra Rage.", "&7&l* &7x1.5 World Destroyer Duration.", "&7&l* &750% Chance to Bypass Utility Belt.", "&7(&oRequires all 4 Ultra items.&7)"), new LinkedList(Arrays.asList(EnchantmentAPI.getEnchantment("Godly Overload"), EnchantmentAPI.getEnchantment("Paladin Armored"), EnchantmentAPI.getEnchantment("Blood Lust"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Arrow Deflect"), EnchantmentAPI.getEnchantment("Dodge"), EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Aegis"), EnchantmentAPI.getEnchantment("Spirit Link"), EnchantmentAPI.getEnchantment("Leadership")))));
    }

    @Override
    public List<String> getEquipDescription() {
        return TranslateList.translateList(ChatColor.GRAY + "" + ChatColor.BOLD +
                "",
                "&7&lULTRA SET BONUS",
                "&7&l➥ &7You deal 35% more damage.",
                "&7&l➥ &7Enemies deal 20% less damage to you.",
                "&7&l➥ &740% Holy Ultra Negation.",
                "&7&l➥ &710% Chance to proc Ultra Rage.",
                "&7&l➥ &7x1.5 World Destroyer Duration.",
                "&7&l➥ &750% Chance to Bypass Utility Belt."
        , "");
    }

    @Override
    public boolean onPlayerDamaged(Player damaged, EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return false;
        }
        if (this.isBlockedOutpostWorld(damaged)) {
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
        if (this.isBlockedOutpostWorld(damager)) {
            return false;
        }
        event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.30);
        this.removeResistance(damager);
        if (Math.random() <= 0.065) {
            this.activateUltraAbility(damager, (Player) event.getEntity());
            event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE)*1.4);
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
        this.modifyDamage(damaged, event, -5.0, multiplier);
        return false;
    }

    @Override
    public boolean onPlayerAttackedBonus(Player damager, int applied, EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return false;
        }
        this.removeResistance(damager);
        if (this.isBlockedOutpostWorld(damager)) {
            return false;
        }
        if (Math.random() <= 0.004 * (double) applied) {
            this.activateUltraAbility(damager, (Player) event.getEntity());
            event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE)*1.4);

        }
        this.modifyDamage(damager, event, 10.0, applied);
        return false;
    }

    @Override
    public void onEquip(Player player) {
    }

    @Override
    public void onUnequip(Player player) {
    }

    public void activateUltraAbility(Player player, final Player target) {
        target.sendMessage(CC.translate("&e&l*&4&l*&e&l* &7&lULTRA STRIKED &f[&c" + player.getName() + "&f] &c&l*&4&l*&e&l*"));
        player.sendMessage(CC.translate("&e&l*&4&l*&e&l* &f&lULTRA STRIKE &7x2 &f&lDMG &e&l*&4&l*&e&l*"));
        target.setMetadata("ultra", new FixedMetadataValue(CosmicSkyInventoryPets.get(), true));
        //target.setMetadata("bansheed", new FixedMetadataValue(CosmicSkyInventoryPets.get(), true));
        new BukkitRunnable() {

            public void run() {
                target.removeMetadata("ultra", CosmicSkyInventoryPets.get());
                //target.removeMetadata("bansheed", CosmicSkyInventoryPets.get());
            }
        }.runTaskLaterAsynchronously(CosmicSkyInventoryPets.get(), 160L);
    }

    @Override
    public void onPlayerEvent(Event paramEvent) {
    }
}

