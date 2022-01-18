/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.net.astropvp.armorsets.utils.CC
 *  com.google.common.collect.Lists
 *  com.rit.sucy.CustomEnchantment
 *  com.rit.sucy.EnchantmentAPI
 *  org.arkhamnetwork.enchantments.EnchantmentBook
 *  org.arkhamnetwork.enchantments.events.CustomEnchantProcOnEntityEvent
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package com.compilex.CosmicArmorSets.enums.armorsets;

import com.compilex.CosmicArmorSets.CosmicArmorSetsPlugin;
import com.compilex.CosmicArmorSets.enums.ArmorSetInfo;
import com.compilex.CosmicArmorSets.enums.OtherPlayerEventListenerSet;
import com.compilex.CosmicArmorSets.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import org.arkhamnetwork.enchantments.events.CustomEnchantProcOnEntityEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Random;

public class DragonSlayerArmorSet
        extends ArmorSetInfo
        implements OtherPlayerEventListenerSet {
    private final ChatColor color = ChatColor.YELLOW;

    @Override
    public void onEquip(Player player) {
        player.setMetadata("mastery_reflect_enchant", new FixedMetadataValue(CosmicArmorSetsPlugin.get_(), 10));
        player.setMetadata("immune_silence", new FixedMetadataValue(CosmicArmorSetsPlugin.get_(), System.currentTimeMillis()));
        player.setMetadata("immune_freeze", new FixedMetadataValue(CosmicArmorSetsPlugin.get_(), System.currentTimeMillis()));
    }

    @Override
    public void onUnequip(Player player) {
        player.removeMetadata("mastery_reflect_enchant", CosmicArmorSetsPlugin.get_());
        player.removeMetadata("immune_silence", CosmicArmorSetsPlugin.get_());
        player.removeMetadata("immune_freeze", CosmicArmorSetsPlugin.get_());
    }

    @Override
    public ItemStack getHelmet(boolean applyTransmog) {
        ItemStack kothItem = this.createArmor(Material.DIAMOND_HELMET, "Decapitated Dragon Skull", "helmet");
        this.addEnchants(kothItem, Lists.newArrayList("Drunk", "Armored", "Deathbringer", "Tank", "Angelic", "Implants", "Valor", "Death God", "Repair Guard", "Ender Shift", "Paladin Armored", "Planetary Deathbringer", "Alien Implants"), 0.4);
        if (this.random.nextDouble() <= 0.3) {
            this.addEnchantment("Immortal", ItemUtils.getRandomArmorSetEnchantLevel("Immortal"), kothItem);
        }
        return applyTransmog ? EnchantmentBook.applyTransmogScroll(kothItem) : kothItem;
    }

    @Override
    public ItemStack getChestplate(boolean applyTransmog) {
        ItemStack kothItem = this.createArmor(Material.DIAMOND_CHESTPLATE, "Firey Chestplate of Dragons", "chestplate");
        this.addEnchants(kothItem, Lists.newArrayList("Overload", "Leadership", "Armored", "Tank", "Angelic", "Valor", "Aegis", "Dodge", "Frozen", "Enchant Reflect", "Godly Overload"), 0.4);
        if (this.random.nextDouble() <= 0.05) {
            this.addMaxedEnchant(this.getEnchantment("Enchant Reflect"), kothItem);
            this.addEnchantment("Heroic Enchant Reflect", ItemUtils.getRandomArmorSetEnchantLevel("Heroic Enchant Reflect"), kothItem);
        }
        return applyTransmog ? EnchantmentBook.applyTransmogScroll(kothItem) : kothItem;
    }

    @Override
    public ItemStack getLeggings(boolean applyTransmog) {
        ItemStack kothItem = this.createArmor(Material.DIAMOND_LEGGINGS, "Scorched Leggings of Dragons", "leggings");
        this.addEnchants(kothItem, Lists.newArrayList("Enlighted", "Lucky", "Obsidianshield", "Armored", "Tank", "Valor", "Angelic", "Arrow Deflect", "Clarity", "Cactus", "Divine Enlighted"), 0.4);
        if (Math.random() < 0.075) {
            this.addMaxedEnchant(EnchantmentAPI.getEnchantment("Nature Wrath"), kothItem);
        }
        return applyTransmog ? EnchantmentBook.applyTransmogScroll(kothItem) : kothItem;
    }

    public ItemStack addEnchants(ItemStack item, List<String> enchants, double heroic) {
        for (String cen : enchants) {
            CustomEnchantment ce = EnchantmentAPI.getEnchantment(cen);
            if (ce == null) continue;
            if (ce.heroicEnchantment) {
                if (heroic > 0.0 && Math.random() > heroic) continue;
                ce.nonHeroicVersion.addToItem(item, ce.nonHeroicVersion.getMaxLevel());
                ce.addToItem(item, 1 + new Random().nextInt(ce.getMaxLevel()));
                continue;
            }
            ce.addToItem(item, Math.min(ce.getMaxLevel(), Math.max(1, ce.getMaxLevel() - 2) + this.random.nextInt(3)));
        }
        return item;
    }

    public ItemStack addMaxedEnchant(CustomEnchantment enchantment, ItemStack item) {
        if (enchantment == null) {
            return item;
        }
        enchantment.addToItem(item, enchantment.getMaxLevel());
        return item;
    }

    public ItemStack addRandomLeveledEnchant(String enchantName, ItemStack item) {
        CustomEnchantment enchantment = EnchantmentAPI.getEnchantment(enchantName);
        if (enchantment == null) {
            return item;
        }
        enchantment.addToItem(item, (enchantment.getMaxLevel() > 1 ? this.random.nextInt(enchantment.getMaxLevel()) : 0) + 1);
        return item;
    }

    @Override
    public ItemStack getBoots(boolean applyTransmog) {
        ItemStack kothItem = this.createArmor(Material.DIAMOND_BOOTS, "Dragon Slayer Battle Boots", "boots");
        this.addEnchants(kothItem, Lists.newArrayList("Gears", "Rocket Escape", "Armored", "Tank", "Valor", "Angelic", "Springs", "Gaurdians", "Voodoo", "Ender Walker", "Ghost", "Ghostly Ghost"), 0.4);
        if (this.random.nextDouble() <= 0.075) {
            this.addEnchantment("Phoenix", ItemUtils.getRandomArmorSetEnchantLevel("Phoenix"), kothItem);
        }
        return applyTransmog ? EnchantmentBook.applyTransmogScroll(kothItem) : kothItem;
    }

    @Override
    public List<String> getEquipDescription() {
        return Lists.newArrayList(CC.YellowB + "DRAGON SLAYER SET BONUS:", CC.YellowB + " * " + CC.Yellow + "+15% OUTGOING DAMAGE", CC.YellowB + " * " + CC.Yellow + "-20% INCOMING DAMAGE", CC.YellowB + " * " + CC.Yellow + "Immune to Freezes", CC.YellowB + " * " + CC.Yellow + "Mastery Enchant Reflect X", CC.YellowB + " * " + CC.Yellow + "Negate 75% of Enemy Silence", CC.YellowB + " * " + CC.Yellow + "25% Mastery Enchant Negation");
    }

    @Override
    public boolean onPlayerDamaged(Player damaged, EntityDamageByEntityEvent event) {
        return !this.isBlockedOutpostWorld(damaged);
    }

    @Override
    public boolean onPlayerAttacked(Player damager, EntityDamageByEntityEvent event) {
        if (this.isBlockedOutpostWorld(damager)) {
            return false;
        }
        if (event.getEntity() instanceof Player) {
            // empty if block
        }
        return true;
    }

    @Override
    public boolean onPlayerDamagedBonus(Player damaged, int multiplier, EntityDamageByEntityEvent event) {
        if (this.isBlockedOutpostWorld(damaged)) {
            return false;
        }
        return this.modifyDamage(damaged, event, -5.0, multiplier);
    }

    @Override
    public boolean onPlayerAttackedBonus(Player damager, int multiplier, EntityDamageByEntityEvent event) {
        if (this.isBlockedOutpostWorld(damager)) {
            return false;
        }
        if (event.getEntity() instanceof Player) {
            return this.modifyDamage(damager, event, 3.0, multiplier);
        }
        return false;
    }

    public boolean onEnchantImmuneToProced(Player player, Player damager, String enchant, int multiplier, CustomEnchantProcOnEntityEvent event) {
        if (Math.random() <= (double) multiplier * 0.1) {
            event.setCancelled(true);
            player.sendMessage(CC.YellowB + "* DRAGON SLAYER CRYSTAL [" + CC.Gray + (enchant.startsWith("Ice") ? "Ice Aspect" : enchant) + " blocked" + CC.YellowB + "] *");
        }
        return false;
    }

    public ItemStack createArmor(Material material, String itemName, String loreType) {
        String bold = this.color + CC.Bold + "* " + this.color;
        ItemStack armor = ItemUtils.buildItem(material, (short) 0, this.color + CC.Bold + itemName + CC.Reset, this.color + CC.Italic + "The mythical " + loreType + " of a Slayer of Dragons.", "", this.color + CC.Bold + "DRAGON SLAYER SET BONUS", bold + "+15% PvP Damage", bold + "-20% Incoming Damage", bold + "Immune to Freezes", bold + "Mastery Enchant Reflect X", bold + "Negate 75% of Enemy Silence", bold + "25% Mastery Enchant Negation", CC.Gray + "(" + CC.GrayI + "Requires all 4 dragon slayer items" + CC.Gray + ")");
        armor.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        armor.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
        return armor;
    }

    @Override
    public void onPlayerEvent(Event event) {
        if (event instanceof PlayerQuitEvent) {
            this.onUnequip(((PlayerQuitEvent) event).getPlayer());
        }
    }
}

