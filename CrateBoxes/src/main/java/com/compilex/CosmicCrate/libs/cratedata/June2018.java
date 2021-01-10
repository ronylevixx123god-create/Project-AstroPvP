/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicArmorSets.enums.ArmorSet
 *  com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity
 *  com.cosmicpvp.CosmicSpaceChests.libs.KitItems
 *  com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.google.common.collect.Lists
 *  me.cosmicpvp.CosmicGlobalKits.libs.GlobalKits
 *  me.ifamasssxd.cosmicconquest.CosmicConquestAPI
 *  org.arkhamnetwork.Arkkit.patches.EquipmentLootboxes
 *  org.arkhamnetwork.Arkkit.patches.ItemID
 *  org.arkhamnetwork.Arkkit.patches.LoreTag
 *  org.arkhamnetwork.Arkkit.plugins.ArkPlugins
 *  org.arkhamnetwork.enchantments.EnchantmentOrb
 *  org.arkhamnetwork.enchantments.RandomizationScroll
 *  org.arkhamnetwork.enchantments.libs.OrbType
 *  org.arkhamnetwork.enchantments.util.TieredEnchantments
 *  org.bukkit.ChatColor
 *  org.bukkit.Color
 *  org.bukkit.DyeColor
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package com.compilex.CosmicCrate.libs.cratedata;

import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.compilex.CosmicCrate.api.CCAPI;
import com.compilex.CosmicCrate.libs.CosmicCrateData;
import com.compilex.CosmicCrate.libs.Version;
import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import com.cosmicpvp.CosmicSpaceChests.libs.KitItems;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import me.cosmicpvp.CosmicGlobalKits.libs.GlobalKits;
import me.ifamasssxd.cosmicconquest.CosmicConquestAPI;
import org.arkhamnetwork.Arkkit.patches.EquipmentLootboxes;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.Arkkit.patches.LoreTag;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.arkhamnetwork.enchantments.EnchantmentOrb;
import org.arkhamnetwork.enchantments.RandomizationScroll;
import org.arkhamnetwork.enchantments.libs.OrbType;
import org.arkhamnetwork.enchantments.util.TieredEnchantments;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class June2018
extends CosmicCrateData {
    @Override
    public List<String> getDescriptionLore(String username) {
        String bulletpoint = ChatColor.BOLD.toString() + "* ";
        return Lists.newArrayList((Object[])new String[]{(Object)ChatColor.DARK_RED + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)ChatColor.DARK_RED + " at " + (Object)ChatColor.RED + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Report Card", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Limited Edition Titles", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "2-3x Item Nametags " + CC.YellowB + CC.Underline + "OR" + CC.Yellow + " Lore Crystals", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "1-4x Godly Randomization Scroll", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", CC.Gold + bulletpoint + CC.Gold + "Equipment Lootbox", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1-3x Random Heroic Enchantment Book", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1-3x Heroic Faction Crystals", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1-2x Random /gkit Gem", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "June 2017 CC", "", ChatColor.DARK_RED.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)ChatColor.DARK_RED + bulletpoint + (Object)ChatColor.DARK_RED + "Superior Mystery Cosmic Crate", (Object)ChatColor.DARK_RED + bulletpoint + (Object)ChatColor.DARK_RED + "80-95% Armor/Weapon Enchantment Orb (" + (Object)ChatColor.DARK_RED + (Object)ChatColor.BOLD + "13 Slot" + (Object)ChatColor.DARK_RED + ")", (Object)ChatColor.DARK_RED + bulletpoint + (Object)ChatColor.DARK_RED + "Supreme Armor Set Piece"});
    }

    @Override
    public ItemStack getRandomMetaItem(Player player) {
        double chance = Math.random();
        if (chance <= 0.1) {
            return CCAPI.getGodlyCosmicCrate();
        }
        if (chance < 0.65) {
            return ItemUtils.setAmount((ItemStack)EnchantmentOrb.getEnchantmentOrb((int)(this.random.nextInt(16) + 80), (int)13, (OrbType)(this.random.nextInt(10) <= 6 ? OrbType.ARMOR : OrbType.WEAPON)), (int)1);
        }
        return ArmorSet.SUPREME.getRandomPeice(true);
    }

    @Override
    public List<ItemStack> getCrateLoot(Player player) {
        ArrayList items = Lists.newArrayList();
        String grade = "an " + CC.Red + "F" + CC.Gray;
        int rand = this.random.nextInt(10);
        if (rand <= 2) {
            grade = "a " + CC.YellowB + "C+";
        } else if (rand <= 6 && rand > 4) {
            grade = "a " + CC.AquaB + "B+";
        } else if (rand == 8) {
            grade = "an " + CC.GreenB + "A";
        } else if (rand == 9) {
            grade = "an " + CC.GreenB + "A+";
        }
        items.add(ItemUtils.setAmount((ItemStack)ItemUtils.buildItem((Material)Material.PAPER, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)(CC.AquaB + "Report Card")), (String[])new String[]{"", (Object)ChatColor.GRAY + "Your grades are in!", (Object)ChatColor.GRAY + "It seems you got " + grade + CC.Gray + "!"}), (int)1));
        items.add(ItemUtils.setAmount((ItemStack)(this.random.nextBoolean() ? ItemUtils.getItemNametag() : LoreTag.getLoreTagItem()), (int)(this.random.nextInt(2) + 2)));
        items.add(ItemUtils.setAmount((ItemStack)RandomizationScroll.getRandomizationScroll((ItemRarity)ItemRarity.GOD), (int)(1 + this.random.nextInt(4))));
        int x = this.random.nextInt(4);
        if (x == 0) {
            items.add(ItemUtils.getTitleVoucher((String)"#SchoolsOut"));
        }
        if (x == 1) {
            items.add(ItemUtils.getTitleVoucher((String)"#Summer2018"));
        }
        if (x == 2) {
            items.add(ItemUtils.getTitleVoucher((String)"ImYourFather"));
        }
        if (x == 3) {
            if (Math.random() < 0.1) {
                items.add(ItemUtils.getTitleVoucher((String)"#WhosYourDaddy"));
            } else {
                items.add(ItemUtils.getTitleVoucher((String)"#Tanned"));
            }
        }
        items.add(((EquipmentLootboxes)ArkPlugins.EQUIPMENT_LOOTBOX.getArkPlugin()).createLootboxItem(false));
        items.add(ItemUtils.setAmount((ItemStack)KitItems.getKitItem((GlobalKits)GlobalKits.values()[this.random.nextInt(GlobalKits.values().length)]), (int)(this.random.nextInt(2) + 1)));
        items.add(ItemID.removeItemID((ItemStack)ItemUtils.setAmount((ItemStack)TieredEnchantments.getMysteryEnchantmentBook((int)7), (int)(this.random.nextDouble() <= 0.1 ? 3 : this.random.nextInt(2) + 1))));
        items.add(CosmicConquestAPI.createHeroicToken((int)(this.random.nextInt(4) + 1)));
        items.add(CCAPI.getCosmicCrate(Version.JUNE_2017, player.getName()));
        return items;
    }

    @Override
    public short getNonColorPane() {
        return DyeColor.RED.getWoolData();
    }

    @Override
    public List<Color> getConclusionFireworkColors() {
        return Lists.newArrayList((Object[])new Color[]{Color.RED, Color.PURPLE});
    }

    @Override
    public short getColorPane() {
        return DyeColor.PINK.getWoolData();
    }
}

