/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicArmorSets.CosmicArmorSetsAPI
 *  com.compilex.CosmicArmorSets.enums.ArmorSet
 *  com.compilex.CosmicCustomCreepers.enums.CreeperTypes
 *  com.compilex.CosmicDungeons.DungeonsAPI
 *  com.cosmicpvp.CosmicRankQuests.CosmicRankQuestsAPI
 *  com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI
 *  com.cosmicpvp.CosmicSpaceChests.libs.EvolutionKitItems
 *  com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity
 *  com.cosmicpvp.CosmicSpaceChests.libs.KitItems
 *  com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils
 *  com.rit.sucy.CustomEnchantment
 *  me.cosmicpvp.CosmicGlobalKits.libs.FallenHero
 *  me.cosmicpvp.CosmicGlobalKits.libs.GlobalKits
 *  me.ifamasssxd.cosmicbosses.struct.enums.BossType
 *  me.ifamasssxd.cosmicconquest.CosmicConquestAPI
 *  me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKit
 *  me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKits
 *  me.ifamasssxd.cosmicfactionboosters.CosmicFactionBoosters
 *  me.ifamasssxd.cosmicfactionboosters.enums.BoosterType
 *  me.ifamasssxd.killtracker.KillTrackerAPI
 *  org.arkhamnetwork.Arkkit.patches.Drinks
 *  org.arkhamnetwork.Arkkit.patches.Easter
 *  org.arkhamnetwork.Arkkit.patches.EssentialsAddHomes
 *  org.arkhamnetwork.Arkkit.patches.ItemID
 *  org.arkhamnetwork.Arkkit.patches.ItemNametag
 *  org.arkhamnetwork.Arkkit.patches.LoreTag
 *  org.arkhamnetwork.Arkkit.patches.MysteryMobSpawners
 *  org.arkhamnetwork.Arkkit.patches.NewYears
 *  org.arkhamnetwork.Arkkit.patches.ShowcaseExpander
 *  org.arkhamnetwork.Arkkit.plugins.ArkPlugins
 *  org.arkhamnetwork.CosmicCustomTNT.enums.TNTTypes
 *  org.arkhamnetwork.enchantments.EnchantmentBook
 *  org.arkhamnetwork.enchantments.EnchantmentOrb
 *  org.arkhamnetwork.enchantments.RandomizationScroll
 *  org.arkhamnetwork.enchantments.libs.OrbType
 *  org.arkhamnetwork.enchantments.util.TieredEnchantments
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Color
 *  org.bukkit.FireworkEffect
 *  org.bukkit.FireworkEffect$Type
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Firework
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.FireworkMeta
 */
package com.compilex.CosmicCrate.api;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.compilex.CosmicCrate.CosmicCrate;
import com.compilex.CosmicCrate.libs.RandomFireworks;
import com.compilex.CosmicCrate.libs.Version;
import com.compilex.CosmicCrate.utils.LocalItemUtils;
import com.compilex.CosmicCustomCreepers.enums.CreeperTypes;
import com.compilex.CosmicDungeons.DungeonsAPI;
import com.cosmicpvp.CosmicRankQuests.CosmicRankQuestsAPI;
import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI;
import com.cosmicpvp.CosmicSpaceChests.libs.EvolutionKitItems;
import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import com.cosmicpvp.CosmicSpaceChests.libs.KitItems;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import com.rit.sucy.CustomEnchantment;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import me.cosmicpvp.CosmicGlobalKits.libs.FallenHero;
import me.cosmicpvp.CosmicGlobalKits.libs.GlobalKits;
import me.ifamasssxd.cosmicbosses.struct.enums.BossType;
import me.ifamasssxd.cosmicconquest.CosmicConquestAPI;
import me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKit;
import me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKits;
import me.ifamasssxd.cosmicfactionboosters.CosmicFactionBoosters;
import me.ifamasssxd.cosmicfactionboosters.enums.BoosterType;
import me.ifamasssxd.killtracker.KillTrackerAPI;
import org.arkhamnetwork.Arkkit.patches.Drinks;
import org.arkhamnetwork.Arkkit.patches.Easter;
import org.arkhamnetwork.Arkkit.patches.EssentialsAddHomes;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.Arkkit.patches.ItemNametag;
import org.arkhamnetwork.Arkkit.patches.LoreTag;
import org.arkhamnetwork.Arkkit.patches.MysteryMobSpawners;
import org.arkhamnetwork.Arkkit.patches.NewYears;
import org.arkhamnetwork.Arkkit.patches.ShowcaseExpander;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.arkhamnetwork.CosmicCustomTNT.enums.TNTTypes;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import org.arkhamnetwork.enchantments.EnchantmentOrb;
import org.arkhamnetwork.enchantments.RandomizationScroll;
import org.arkhamnetwork.enchantments.libs.OrbType;
import org.arkhamnetwork.enchantments.util.TieredEnchantments;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

public class CCAPI {
    public static ItemStack getMysteryCrate() {
        ItemStack i = ItemUtils.buildItem((Material)Material.ENDER_CHEST, (short)0, (String)((Object)ChatColor.YELLOW + "+" + (Object)ChatColor.GOLD + (Object)ChatColor.BOLD + "?" + (Object)ChatColor.YELLOW + "-" + (Object)ChatColor.RESET + (Object)ChatColor.GOLD + " " + (Object)ChatColor.BOLD + "Mystery Cosmic Crate " + (Object)ChatColor.YELLOW + "-" + (Object)ChatColor.GOLD + (Object)ChatColor.BOLD + "?" + (Object)ChatColor.YELLOW + "+"), (String[])new String[]{"", (Object)ChatColor.GRAY + "" + (Object)ChatColor.UNDERLINE + "Right-click" + (Object)ChatColor.GRAY + " (in your hand) to recieve", (Object)ChatColor.GRAY + "a random /cc from this list!", "", Version.JANURARY.getRawName(), Version.FEBRUARY.getRawName(), Version.MARCH.getRawName(), Version.APRIL.getRawName(), Version.JULY.getRawName(), Version.SCHOOL.getRawName(), Version.OCTOBER.getRawName(), Version.HALLOWEEN.getRawName(), Version.NOVEMBER.getRawName(), Version.BLACK_FRIDAY.getRawName(), Version.DECEMBER_2016.getRawName(), Version.HOLIDAY.getRawName(), "", (Object)ChatColor.GRAY + "" + (Object)ChatColor.ITALIC + "A mysterious chest that needs", (Object)ChatColor.GRAY + "" + (Object)ChatColor.ITALIC + "to be inspected to determine", (Object)ChatColor.GRAY + "" + (Object)ChatColor.ITALIC + "its exact contents."});
        if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning()) {
            ItemID.setItemID((ItemStack)i, (long)ItemID.generateNewItemID());
        }
        return i;
    }

    public static ItemStack getHeroicMysteryCrate() {
        ItemStack i = ItemUtils.buildItem((Material)Material.ENDER_CHEST, (short)0, (String)((Object)ChatColor.RED + "+" + (Object)ChatColor.DARK_RED + (Object)ChatColor.BOLD + "?" + (Object)ChatColor.RED + "-" + (Object)ChatColor.RESET + (Object)ChatColor.DARK_RED + " " + (Object)ChatColor.BOLD + "Heroic Mystery Cosmic Crate " + (Object)ChatColor.RED + "-" + (Object)ChatColor.DARK_RED + (Object)ChatColor.BOLD + "?" + (Object)ChatColor.RED + "+"), (String[])new String[]{"", (Object)ChatColor.GRAY + "" + (Object)ChatColor.UNDERLINE + "Right-click" + (Object)ChatColor.GRAY + " (in your hand) to recieve", (Object)ChatColor.GRAY + "a random 2017 /cc from this list!", "", Version.JANUARY_2017.getRawName(), Version.VALENTINE.getRawName(), Version.MARCH_2017.getRawName(), Version.APRIL_2017.getRawName(), Version.JULY_2017.getRawName(), Version.SCHOOL_2017.getRawName(), Version.OCTOBER_2017.getRawName(), Version.HALLOWEEN_2017.getRawName(), Version.NOVEMBER_2017.getRawName(), Version.DECEMBER_2017.getRawName(), Version.HOLIDAY_2017.getRawName(), "", (Object)ChatColor.GRAY + "" + (Object)ChatColor.ITALIC + "A mysterious chest that needs", (Object)ChatColor.GRAY + "" + (Object)ChatColor.ITALIC + "to be inspected to determine", (Object)ChatColor.GRAY + "" + (Object)ChatColor.ITALIC + "its exact contents."});
        if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning()) {
            ItemID.setItemID((ItemStack)i, (long)ItemID.generateNewItemID());
        }
        return i;
    }

    public static ItemStack getGodlyCosmicCrate() {
        ItemStack i = ItemUtils.buildItem((Material)Material.ENDER_CHEST, (short)0, (String)((Object)ChatColor.RED + "+" + (Object)ChatColor.DARK_PURPLE + (Object)ChatColor.BOLD + "?" + (Object)ChatColor.RED + "-" + (Object)ChatColor.RESET + (Object)ChatColor.DARK_PURPLE + " " + (Object)ChatColor.BOLD + "Superior Mystery Crate " + (Object)ChatColor.RED + "-" + (Object)ChatColor.DARK_PURPLE + (Object)ChatColor.BOLD + "?" + (Object)ChatColor.RED + "+"), (String[])new String[]{"", (Object)ChatColor.GRAY + "" + (Object)ChatColor.UNDERLINE + "Right-click" + (Object)ChatColor.GRAY + " (in your hand) to recieve", (Object)ChatColor.GRAY + "a random 2018 /cc from this list!", "", Version.JANUARY_2018.getRawName(), Version.FEBUARY_2018.getRawName(), Version.VALENTINE_2018.getRawName(), Version.MARCH_2018.getRawName(), Version.APRIL_2018.getRawName(), Version.MAY_2018.getRawName(), Version.JUNE_2018.getRawName(), Version.JULY_2018.getRawName(), Version.AUGUST_2018.getRawName(), Version.SEPTEMBER_2018.getRawName(), Version.OCTOBER_2018.getRawName(), Version.HALLOWEEN_2018.getRawName(), Version.NOVEMBER_2018.getRawName(), Version.THANKSGIVING_2018.getRawName(), Version.DECEMBER_2018.getRawName(), Version.HOLIDAY_2018.getRawName(), "", (Object)ChatColor.GRAY + "" + (Object)ChatColor.ITALIC + "An extremely mysterious crate that", (Object)ChatColor.GRAY + "" + (Object)ChatColor.ITALIC + "has a powerful aura emanating from it."});
        if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning()) {
            ItemID.setItemID((ItemStack)i, (long)ItemID.generateNewItemID());
        }
        return i;
    }

    public static ItemStack getCosmicCrate(Version v, String username) {
        String bulletpoint;
        ItemStack cc = null;
        if (v == Version.DECEMBER) {
            bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem((Material)Material.ENDER_CHEST, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)v.crateName), (String[])new String[]{(Object)ChatColor.RED + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)ChatColor.RED + " at " + (Object)ChatColor.GREEN + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Santa's Little Helper", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "New Years Clappers", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Candycane Cake", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Custom Item Nametag", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Exclusive Titles", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Jolly Transmog Scroll", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ENCHANTMENT ITEMS", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Godly Enchantment Book (100% Success)", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Demonic Black Scroll (95% Book)", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "10X High-Tier Secret Dust", "", ChatColor.RED.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)ChatColor.RED + bulletpoint + (Object)ChatColor.RED + "Explosive Snowballs", (Object)ChatColor.RED + bulletpoint + (Object)ChatColor.RED + "Limited Edition Kits"});
        }
        if (v == Version.JANURARY) {
            bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem((Material)Material.ENDER_CHEST, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)v.crateName), (String[])new String[]{(Object)ChatColor.YELLOW + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)ChatColor.YELLOW + " at " + (Object)ChatColor.GOLD + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "New Years Party Mask", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Executive Storage Chest", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Explosive Cakes", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "New Years Fireworks", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Limited Edition Titles", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Player Souls (10-100x)", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Lucky TNT (8-16x)", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Holy White Scroll (100%)", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Premium Monster Spawners (2x)", "", ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)ChatColor.GREEN + bulletpoint + (Object)ChatColor.GREEN + "Random Soul Tracker", (Object)ChatColor.GREEN + bulletpoint + (Object)ChatColor.GREEN + "Soul Enchantment Book", (Object)ChatColor.GREEN + bulletpoint + (Object)ChatColor.GREEN + "Limited Edition Kits"});
        }
        if (v == Version.FEBRUARY) {
            bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem((Material)Material.ENDER_CHEST, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)v.crateName), (String[])new String[]{(Object)ChatColor.RED + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)ChatColor.RED + " at " + (Object)ChatColor.DARK_RED + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Valentine's Day Card", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Box of Chocolates", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Space Drinks", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Lovely Item Nametag", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Limited Edition Titles", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Mystery Mob Spawner", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "EXP Bottles", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "mcMMO Voucher", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Bank Note", "", ChatColor.DARK_RED.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)ChatColor.DARK_RED + bulletpoint + (Object)ChatColor.DARK_RED + "Nether Spawner", (Object)ChatColor.DARK_RED + bulletpoint + (Object)ChatColor.DARK_RED + "Explosive Snowballs", (Object)ChatColor.DARK_RED + bulletpoint + (Object)ChatColor.DARK_RED + "Limited Edition Kits"});
        }
        if (v == Version.MARCH) {
            bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem((Material)Material.ENDER_CHEST, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)v.crateName), (String[])new String[]{(Object)ChatColor.GREEN + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)ChatColor.GREEN + " at " + (Object)ChatColor.DARK_GREEN + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Pot of Gold", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Flower Bouquet", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Spring Item Nametag", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Seasonal Transmog Scroll", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Legendary Player Titles", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Mystery Mob Spawner", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Tactical Creeper (1-3x)", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Gigantic Creeper (2-5x)", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Demonic Black Scroll (100%)", "", ChatColor.DARK_GREEN.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)ChatColor.DARK_GREEN + bulletpoint + (Object)ChatColor.DARK_GREEN + "Nether Spawner", (Object)ChatColor.DARK_GREEN + bulletpoint + (Object)ChatColor.DARK_GREEN + "Lucky Creepers", (Object)ChatColor.DARK_GREEN + bulletpoint + (Object)ChatColor.DARK_GREEN + "Limited Edition Kits"});
        }
        if (v == Version.APRIL) {
            bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem((Material)Material.ENDER_CHEST, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)v.crateName), (String[])new String[]{(Object)ChatColor.YELLOW + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)ChatColor.YELLOW + " at " + (Object)ChatColor.LIGHT_PURPLE + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Troll Letter", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Colorful Umbrella", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "April Fools Nametag", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Timeless Player Titles", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Explosive Cakes", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Limited Edition Kit", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Legendary Space Chest", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Mystery Mob Spawner", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Perfect White Scroll", "", ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)ChatColor.LIGHT_PURPLE + bulletpoint + (Object)ChatColor.LIGHT_PURPLE + "Player Rank Vouchers", (Object)ChatColor.LIGHT_PURPLE + bulletpoint + (Object)ChatColor.LIGHT_PURPLE + "Cosmic Envoy Summon", (Object)ChatColor.LIGHT_PURPLE + bulletpoint + (Object)ChatColor.LIGHT_PURPLE + "Cosmic Boss Eggs"});
        }
        if (v == Version.JULY) {
            bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem((Material)Material.ENDER_CHEST, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)v.crateName), (String[])new String[]{(Object)ChatColor.WHITE + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)ChatColor.WHITE + " at " + (Object)ChatColor.BLUE + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Independence Flag", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Declaration of Freedom", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Transmog Scroll", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Exclusive Freedom Titles", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Space Fireworks", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Fallen Hero #1", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Fallen Hero #2", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Heroic Fallen Hero", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Legendary Space Flare", "", ChatColor.BLUE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)ChatColor.BLUE + bulletpoint + (Object)ChatColor.BLUE + "Legendary Boss Egg", (Object)ChatColor.BLUE + bulletpoint + (Object)ChatColor.BLUE + "Rank Quest", (Object)ChatColor.BLUE + bulletpoint + (Object)ChatColor.BLUE + "Cosmic Envoy Summon"});
        }
        if (v == Version.AUGUST) {
            bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem((Material)Material.ENDER_CHEST, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)v.crateName), (String[])new String[]{(Object)ChatColor.AQUA + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)ChatColor.AQUA + " at " + (Object)ChatColor.DARK_AQUA + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Mojang #EULA", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Freshly Squeezed Lemonade", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "EULA Violating Titles", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Item Nametag #1", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Item Nametag #2", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Rank Quest", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Heroic Fallen Hero", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Legendary Space Flare", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "100% Black Scroll (2x)", "", ChatColor.DARK_AQUA.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)ChatColor.DARK_AQUA + bulletpoint + (Object)ChatColor.DARK_AQUA + "Godly Space Flare", (Object)ChatColor.DARK_AQUA + bulletpoint + (Object)ChatColor.DARK_AQUA + "Legendary Boss Egg", (Object)ChatColor.DARK_AQUA + bulletpoint + (Object)ChatColor.DARK_AQUA + "Soul Enchantment Book"});
        }
        if (v == Version.SCHOOL) {
            bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem((Material)Material.ENDER_CHEST, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)v.crateName), (String[])new String[]{(Object)ChatColor.GOLD + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)ChatColor.GOLD + " at " + (Object)ChatColor.WHITE + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Cheat Sheet", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Meme Backpack", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Limited Edition Titles", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Back To School Nametag", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Heroic Fallen Hero #1", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Heroic Fallen Hero #2", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "15X High-Tier Secret Dust", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "90% Black Scroll (3x)", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Perfect White Scroll (2x)", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Nether Spawner", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Godly Space Flare", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Soul Enchantment Book"});
        }
        if (v == Version.OCTOBER) {
            bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem((Material)Material.ENDER_CHEST, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)v.crateName), (String[])new String[]{(Object)ChatColor.RED + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)ChatColor.RED + " at " + (Object)ChatColor.DARK_GRAY + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Lucky Leaf", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Space Pumpkin", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Limited Edition Titles", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Spooky Transmog Scroll (2x)", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "2.0x10m Faction XP/mcMMO Booster", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Mystery Mob Spawner", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Fallen Hero", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Legendary Space Flare", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "100% Black Scroll (2x)", "", ChatColor.RED.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)ChatColor.RED + bulletpoint + (Object)ChatColor.RED + "4.0x30m Faction XP Booster", (Object)ChatColor.RED + bulletpoint + (Object)ChatColor.RED + "Nether Spawner", (Object)ChatColor.RED + bulletpoint + (Object)ChatColor.RED + "Godly Space Flare", (Object)ChatColor.RED + bulletpoint + (Object)ChatColor.RED + "Legendary Boss Egg"});
        }
        if (v == Version.HALLOWEEN) {
            bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem((Material)Material.ENDER_CHEST, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)v.crateName), (String[])new String[]{(Object)ChatColor.DARK_RED + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)ChatColor.DARK_RED + " at " + (Object)ChatColor.GOLD + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Bag of Candy", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Clown Mask", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Limited Edition Titles", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "2x Item Nametag", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "6.66x6m Faction XP/mcMMO Booster", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "4x Mystery Soul Dust", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "2x Mystery Mob Spawner", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1x Fallen Hero", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "66% Black Scroll (6x)", "", ChatColor.DARK_RED.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)ChatColor.DARK_RED + bulletpoint + (Object)ChatColor.DARK_RED + "6.66x66m Faction XP Booster", (Object)ChatColor.DARK_RED + bulletpoint + (Object)ChatColor.DARK_RED + "2x Soul Enchantment Book", (Object)ChatColor.DARK_RED + bulletpoint + (Object)ChatColor.DARK_RED + "Legendary Boss Egg", (Object)ChatColor.DARK_RED + bulletpoint + (Object)ChatColor.DARK_RED + "Rank Quest"});
        }
        if (v == Version.NOVEMBER) {
            bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem((Material)Material.ENDER_CHEST, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)v.crateName), (String[])new String[]{(Object)ChatColor.DARK_PURPLE + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)ChatColor.DARK_PURPLE + " at " + (Object)ChatColor.LIGHT_PURPLE + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Thanksgiving Horn", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Pilgram Hat", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Thanksgiving Titles", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "2-3x Transmog Scrolls", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "4.0x11m Faction XP/mcMMO Booster", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "2x Heroic Fallen Hero", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Legendary Space Flare", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "99% Black Scroll (3x)", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "15X High-Tier Secret Dust", "", ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)ChatColor.DARK_PURPLE + bulletpoint + (Object)ChatColor.DARK_PURPLE + "8.0x30m Faction XP Booster", (Object)ChatColor.DARK_PURPLE + bulletpoint + (Object)ChatColor.DARK_PURPLE + "Godly Space Flare", (Object)ChatColor.DARK_PURPLE + bulletpoint + (Object)ChatColor.DARK_PURPLE + "Legendary Boss Egg", (Object)ChatColor.DARK_PURPLE + bulletpoint + (Object)ChatColor.DARK_PURPLE + "Rank Quest"});
        }
        if (v == Version.BLACK_FRIDAY) {
            bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem((Material)Material.ENDER_CHEST, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)v.crateName), (String[])new String[]{(Object)ChatColor.DARK_GRAY + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)ChatColor.WHITE + " at " + (Object)ChatColor.DARK_GRAY + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "A Good Deal", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Fake 100%-Off Coupon", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Capitalism Titles", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "3x Item Nametags", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "70-95% Enchantment Orb", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "9.99x5m Faction XP/mcMMO Booster", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1-2x White Scroll", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "10X High-Tier Secret Dust", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Low Tier Rank Quest", "", ChatColor.DARK_GRAY.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)ChatColor.DARK_GRAY + bulletpoint + (Object)ChatColor.DARK_GRAY + "3x 100% Enchantment Orbs", (Object)ChatColor.DARK_GRAY + bulletpoint + (Object)ChatColor.DARK_GRAY + "Legendary Boss Egg", (Object)ChatColor.DARK_GRAY + bulletpoint + (Object)ChatColor.DARK_GRAY + "Godly Space Flare"});
        }
        if (v == Version.DECEMBER_2016) {
            bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem((Material)Material.ENDER_CHEST, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)v.crateName), (String[])new String[]{(Object)ChatColor.BLUE + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)ChatColor.BLUE + " at " + (Object)ChatColor.BLUE + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Winter Wonderland", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Cozy Space Mittens", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Icy Titles", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "2-3x Chilly Transmog Scrolls", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "2x 50-75% Armor Enchantment Orbs", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1x 30-60% Weapon Enchantment Orb", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "2x Fallen Hero", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1-2x White Scroll", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Legendary Space Flare", "", ChatColor.BLUE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)ChatColor.BLUE + bulletpoint + (Object)ChatColor.BLUE + "2x Soul Enchantment Books", (Object)ChatColor.BLUE + bulletpoint + (Object)ChatColor.BLUE + "High-Tier Rank Quest", (Object)ChatColor.BLUE + bulletpoint + (Object)ChatColor.BLUE + "Godly Space Flare"});
        }
        if (v == Version.HOLIDAY) {
            bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem((Material)Material.ENDER_CHEST, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)v.crateName), (String[])new String[]{(Object)ChatColor.GREEN + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)ChatColor.GREEN + " at " + (Object)ChatColor.DARK_RED + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Holiday Spirit", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Explosive Snowballs", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Jolly Titles", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "3x Item Nametags", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "2-3x 75-100% Armor Enchantment Orbs", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1-2x 75-100% Weapon Enchantment Orb", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "3x Heroic Fallen Hero", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Phoenix Evolution Kit", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Rank Quest", "", ChatColor.DARK_RED.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)ChatColor.DARK_RED + bulletpoint + (Object)ChatColor.DARK_RED + "Heroic Rank Crystal", (Object)ChatColor.DARK_RED + bulletpoint + (Object)ChatColor.DARK_RED + "High-Tier Rank Quest", (Object)ChatColor.DARK_RED + bulletpoint + (Object)ChatColor.DARK_RED + "Godly Space Flare"});
        }
        if (v == Version.JANUARY_2017) {
            bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem((Material)Material.ENDER_CHEST, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)v.crateName), (String[])new String[]{(Object)ChatColor.DARK_AQUA + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)ChatColor.DARK_AQUA + " at " + (Object)ChatColor.WHITE + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "New Years Resolution", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "2017 Trophy", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Limited Edition Titles", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "2-3x Transmog Scrolls", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Godly Space Flare", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Random /gkit Gem", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "5.0x10m Faction XP/mcMMO Booster", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "2-3x White Scroll", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "3-5x Black Scroll (90%)", "", ChatColor.DARK_AQUA.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)ChatColor.DARK_AQUA + bulletpoint + (Object)ChatColor.DARK_AQUA + "Legendary Boss Eggs (2x)", (Object)ChatColor.DARK_AQUA + bulletpoint + (Object)ChatColor.DARK_AQUA + "Phoenix Evolution Kit", (Object)ChatColor.DARK_AQUA + bulletpoint + (Object)ChatColor.DARK_AQUA + "Heroic Rank Crystal"});
        }
        if (v == Version.VALENTINE) {
            bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem((Material)Material.ENDER_CHEST, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)v.crateName), (String[])new String[]{(Object)ChatColor.DARK_RED + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)ChatColor.DARK_RED + " at " + (Object)ChatColor.RED + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Forever Alone Card", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Box of Memes", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Limited Edition Titles", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "1-3x Item Nametags", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Troll Evolution Kit", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "2x Fallen Hero", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1-4x White Scroll", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "2-4x Mystery Soul Dust", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1-2x 90-100% Weapon/Armor Enchantment Orbs", "", ChatColor.DARK_RED.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)ChatColor.DARK_RED + bulletpoint + (Object)ChatColor.DARK_RED + "Godly Space Flare", (Object)ChatColor.DARK_RED + bulletpoint + (Object)ChatColor.DARK_RED + "Rank Quest", (Object)ChatColor.DARK_RED + bulletpoint + (Object)ChatColor.DARK_RED + "Heroic Rank Crystal"});
        }
        if (v == Version.MARCH_2017) {
            bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem((Material)Material.ENDER_CHEST, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)v.crateName), (String[])new String[]{(Object)ChatColor.GREEN + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)ChatColor.GREEN + " at " + (Object)ChatColor.AQUA + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Spring Totem", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Luck of the Irish", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Limited Edition Titles", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "3x Transmog Scrolls", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1-3x Fallen Hero", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1-2x Heroic Fallen Hero", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Random Evolution Kit", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Rank Quest", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "3-4x 75-90% Weapon/Armor Enchantment Orbs", "", ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)ChatColor.GREEN + bulletpoint + (Object)ChatColor.GREEN + "4-5x Legendary Boss Eggs", (Object)ChatColor.GREEN + bulletpoint + (Object)ChatColor.GREEN + "High-Tier Rank Quest", (Object)ChatColor.GREEN + bulletpoint + (Object)ChatColor.GREEN + "Heroic Rank Crystal"});
        }
        if (v == Version.APRIL_2017) {
            bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem((Material)Material.ENDER_CHEST, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)v.crateName), (String[])new String[]{(Object)ChatColor.GOLD + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)ChatColor.GOLD + " at " + (Object)ChatColor.DARK_PURPLE + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Fool of April", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Easter Basket", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Limited Edition Titles", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "2-3x Item Nametags", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "10-15x Easter Eggs", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "5x10m Faction XP/mcMMO Booster", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1-2x /vkit or /gkit Fallen Hero", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Soul Reaper Boss Egg", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Godly Randomization Scroll", "", ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)ChatColor.DARK_PURPLE + bulletpoint + (Object)ChatColor.DARK_PURPLE + "Godly Space Flare", (Object)ChatColor.DARK_PURPLE + bulletpoint + (Object)ChatColor.DARK_PURPLE + "Alchemist /vkit Crystal", (Object)ChatColor.DARK_PURPLE + bulletpoint + (Object)ChatColor.DARK_PURPLE + "April 2016 Cosmic Crate"});
        }
        if (v == Version.MAY_2017) {
            bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem((Material)Material.ENDER_CHEST, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)v.crateName), (String[])new String[]{(Object)ChatColor.GREEN + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)ChatColor.GREEN + " at " + (Object)ChatColor.DARK_GREEN + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Spring... is coming.", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Vitamin D", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Limited Edition Titles", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "2-4x Transmog Scrolls", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "2x Random Weapon/Armor Orb", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1-2x /vkit Fallen Hero", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1-2x /gkit Fallen Hero", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "2-3x Randomization Scrolls", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "5-15x Faction Crystals", "", ChatColor.DARK_GREEN.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)ChatColor.DARK_GREEN + bulletpoint + (Object)ChatColor.DARK_GREEN + "Godly Space Flare", (Object)ChatColor.DARK_GREEN + bulletpoint + (Object)ChatColor.DARK_GREEN + "Random /gkit Crystal", (Object)ChatColor.DARK_GREEN + bulletpoint + (Object)ChatColor.DARK_GREEN + "50-100x Faction Crystals"});
        }
        if (v == Version.JUNE_2017) {
            bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem((Material)Material.ENDER_CHEST, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)v.crateName), (String[])new String[]{(Object)ChatColor.RED + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)ChatColor.RED + " at " + (Object)ChatColor.LIGHT_PURPLE + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Detention Slip", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Limited Edition Titles", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "2-3x Item Nametags", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1-5x 80-95% Random Weapon/Armor Orb", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1-3x Heroic /gkit Fallen Hero", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "3-8x White Scrolls", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "5-10x Black Scrolls (95%)", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Random /gkit Crystal", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Rank Quest", "", ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)ChatColor.LIGHT_PURPLE + bulletpoint + (Object)ChatColor.LIGHT_PURPLE + "Heroic Rank Crystal", (Object)ChatColor.LIGHT_PURPLE + bulletpoint + (Object)ChatColor.LIGHT_PURPLE + "1-4x Legendary Boss Eggs", (Object)ChatColor.LIGHT_PURPLE + bulletpoint + (Object)ChatColor.LIGHT_PURPLE + "50-100x Faction Crystals"});
        }
        if (v == Version.JULY_2017) {
            bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem((Material)Material.ENDER_CHEST, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)v.crateName), (String[])new String[]{(Object)ChatColor.WHITE + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)ChatColor.WHITE + " at " + (Object)ChatColor.AQUA + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Illegal Fireworks", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Limited Edition Titles", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "3x Transmog Scrolls " + ChatColor.UNDERLINE.toString() + "OR" + (Object)ChatColor.YELLOW + " Item Nametags", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Rank Quest", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1-2x Godly Space Flare", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1-3x /vkit Fallen Hero", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "3-4x Godly Randomization Scrolls", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "7.4x10m Faction XP/mcMMO Booster", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1-2x 25% Random Weapon/Armor Orb (11 slot)", "", ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)ChatColor.AQUA + bulletpoint + (Object)ChatColor.AQUA + "Ogre /vkit Crystal", (Object)ChatColor.AQUA + bulletpoint + (Object)ChatColor.AQUA + "Heroic Rank Crystal", (Object)ChatColor.AQUA + bulletpoint + (Object)ChatColor.AQUA + "July 2016 Cosmic Crate"});
        }
        if (v == Version.AUGUST_2017) {
            bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem((Material)Material.ENDER_CHEST, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)v.crateName), (String[])new String[]{(Object)ChatColor.WHITE + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)ChatColor.WHITE + " at " + (Object)ChatColor.BLUE + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Summer Vibes", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Limited Edition Titles", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "1-2x Max Home Increase", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "1-3x Showcase Expansion", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1-3x Heroic /gkit Fallen Hero", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "2-4 Legendary Space Flare", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "10-25x Faction Crystals", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "4-5x White Scrolls", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1x 75-90% Weapon/Armor Orb (11 slot)", "", ChatColor.BLUE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)ChatColor.BLUE + bulletpoint + (Object)ChatColor.BLUE + "Heroic Rank Crystal", (Object)ChatColor.BLUE + bulletpoint + (Object)ChatColor.BLUE + "Mystery Cosmic Crate", (Object)ChatColor.BLUE + bulletpoint + (Object)ChatColor.BLUE + "August 2016 Cosmic Crate"});
        }
        if (v == Version.SCHOOL_2017) {
            bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem((Material)Material.ENDER_CHEST, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)v.crateName), (String[])new String[]{(Object)ChatColor.GOLD + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)ChatColor.GOLD + " at " + (Object)ChatColor.GRAY + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Your Mixtape", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Limited Edition Titles", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "3-4x Item Nametags", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "1-3x Max Home Increase", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "2-3x /gkit Fallen Hero", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1-3x Legendary Space Chests", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Random /vkit Kit Gem", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Rank Quest", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Mystery Cosmic Crate", "", ChatColor.GRAY.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)ChatColor.GRAY + bulletpoint + (Object)ChatColor.GRAY + "2-3x Godly Space Chests", (Object)ChatColor.GRAY + bulletpoint + (Object)ChatColor.GRAY + "Heroic Rank Crystal", (Object)ChatColor.GRAY + bulletpoint + (Object)ChatColor.GRAY + "Back To School 2016 Cosmic Crate"});
        }
        if (v == Version.SEPTEMBER_2017) {
            bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem((Material)Material.ENDER_CHEST, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)v.crateName), (String[])new String[]{(Object)ChatColor.BLUE + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)ChatColor.BLUE + " at " + (Object)ChatColor.DARK_AQUA + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Intergalactic Fidget Spinner", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Limited Edition Titles", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "4x Transmog Scrolls", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "1-2x Max Home Increase " + ChatColor.UNDERLINE.toString() + "OR" + (Object)ChatColor.YELLOW + " Showcase Expanders", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "2x Legendary " + ChatColor.UNDERLINE.toString() + "OR" + (Object)ChatColor.GOLD + " Godly Space Chests", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "2-4x White " + ChatColor.UNDERLINE.toString() + "OR" + (Object)ChatColor.GOLD + " Black Scrolls (100%)", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "5x15m Faction XP Booster", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "10-25x Faction Crystals", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1x 50-75% Weapon/Armor Orb (" + (Object)ChatColor.BOLD + "12 slot" + (Object)ChatColor.GOLD + ")", "", ChatColor.BLUE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)ChatColor.BLUE + bulletpoint + (Object)ChatColor.BLUE + "Mystery Cosmic Crate", (Object)ChatColor.BLUE + bulletpoint + (Object)ChatColor.BLUE + "1-2x Heroic Boss Eggs", (Object)ChatColor.BLUE + bulletpoint + (Object)ChatColor.BLUE + "Heroic Rank Crystal"});
        }
        if (v == Version.OCTOBER_2017) {
            bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem((Material)Material.ENDER_CHEST, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)v.crateName), (String[])new String[]{(Object)ChatColor.GOLD + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)ChatColor.GOLD + " at " + (Object)ChatColor.DARK_GRAY + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Creepy Eye", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Limited Edition Titles", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "1-5x Item Nametags", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "1-2x Max Home Increase", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1-3x Legendary Space Chests", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "3-5x Black Scrolls (90-100%)", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1-2x Slayer /vkit Fallen Hero", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "50-100 Level mcMMO Voucher", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Rank Quest", "", ChatColor.DARK_GRAY.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)ChatColor.DARK_GRAY + bulletpoint + (Object)ChatColor.DARK_GRAY + "Soul Reaper Boss Egg", (Object)ChatColor.DARK_GRAY + bulletpoint + (Object)ChatColor.DARK_GRAY + "Heroic Rank Crystal", (Object)ChatColor.DARK_GRAY + bulletpoint + (Object)ChatColor.DARK_GRAY + "October 2016 Cosmic Crate"});
        }
        if (v == Version.HALLOWEEN_2017) {
            bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem((Material)Material.ENDER_CHEST, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)v.crateName), (String[])new String[]{(Object)ChatColor.DARK_RED + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)ChatColor.RED + " at " + (Object)ChatColor.DARK_RED + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Bottle O' Spooky", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Spooky Player Titles", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "4-5x Transmog Scrolls", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "2-3x Showcase Expanders", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "2x Legendary " + ChatColor.UNDERLINE.toString() + "OR" + (Object)ChatColor.GOLD + " Godly Space Chests", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "2-3x Heroic /gkit Fallen Hero", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1x 66% Random Weapon/Armor Orb (12 slot)", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Phantom Armor Set Piece", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Mystery Cosmic Crate", "", ChatColor.DARK_RED.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)ChatColor.DARK_RED + bulletpoint + (Object)ChatColor.DARK_RED + "1-2x Heroic Boss Eggs", (Object)ChatColor.DARK_RED + bulletpoint + (Object)ChatColor.DARK_RED + "Heroic Rank Crystal", (Object)ChatColor.DARK_RED + bulletpoint + (Object)ChatColor.DARK_RED + "Halloween 2016 Cosmic Crate"});
        }
        if (v == Version.NOVEMBER_2017) {
            bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem((Material)Material.ENDER_CHEST, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)v.crateName), (String[])new String[]{(Object)ChatColor.DARK_PURPLE + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)ChatColor.WHITE + " at " + (Object)ChatColor.DARK_PURPLE + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Seasonal Discombobulator", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Limited Player Titles", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "2-3x Max Home Increase", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "1-2x Lore Crystals", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1x 75-80% Weapon/Armor Orb (" + (Object)ChatColor.BOLD + "12 slot" + (Object)ChatColor.GOLD + ")", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1-2x Mystery Cosmic Crate", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1-2x Godly Space Flares", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "15-25x Faction Crystals", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Rank Quest", "", ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)ChatColor.DARK_PURPLE + bulletpoint + (Object)ChatColor.DARK_PURPLE + "November 2016 Cosmic Crate", (Object)ChatColor.DARK_PURPLE + bulletpoint + (Object)ChatColor.DARK_PURPLE + "2-3x Godly Space Chests", (Object)ChatColor.DARK_PURPLE + bulletpoint + (Object)ChatColor.DARK_PURPLE + "Heroic Rank Crystal"});
        }
        if (v == Version.THANKSGIVING_2017) {
            bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem((Material)Material.ENDER_CHEST, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)v.crateName), (String[])new String[]{(Object)ChatColor.GOLD + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)ChatColor.YELLOW + " at " + (Object)ChatColor.GOLD + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Turkey of Diplomacy", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Limited Edition Titles", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "5x Item Nametags", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "5x Lore Crystals", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "3-4x Legendary " + ChatColor.UNDERLINE.toString() + "OR" + (Object)ChatColor.GOLD + " Godly Space Flares", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1-2x 80-90% Weapon/Armor Orb (" + (Object)ChatColor.BOLD + "12 slot" + (Object)ChatColor.GOLD + ")", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1-2x Legendary Boss Eggs", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Random /gkit " + ChatColor.UNDERLINE.toString() + "OR" + (Object)ChatColor.GOLD + " /vkit Crystal", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Heroic Mystery Cosmic Crate", "", ChatColor.DARK_RED.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)ChatColor.DARK_RED + bulletpoint + (Object)ChatColor.DARK_RED + "High-tier Rank Quest", (Object)ChatColor.DARK_RED + bulletpoint + (Object)ChatColor.DARK_RED + "Godly Overload Upgrade Book", (Object)ChatColor.DARK_RED + bulletpoint + (Object)ChatColor.DARK_RED + "Heroic Rank Crystal"});
        }
        if (v == Version.DECEMBER_2017) {
            bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem((Material)Material.ENDER_CHEST, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)v.crateName), (String[])new String[]{(Object)ChatColor.BLUE + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)ChatColor.BLUE + " at " + (Object)ChatColor.DARK_BLUE + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Space Snowman", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Limited Edition Titles", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "2-3x Randomization Scrolls", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "3-4x Showcase Expanders", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1x Mystery " + ChatColor.UNDERLINE.toString() + "OR" + (Object)ChatColor.GOLD + " Heroic Cosmic Crate", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1-3x White " + ChatColor.UNDERLINE.toString() + "OR" + (Object)ChatColor.GOLD + " Black Scrolls (100%)", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Random /dungeon Portal Ingredient", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "$2-20m Cosmic Money Note", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "December 2015 Cosmic Crate", "", ChatColor.BLUE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)ChatColor.BLUE + bulletpoint + (Object)ChatColor.BLUE + "December 2016 Cosmic Crate", (Object)ChatColor.BLUE + bulletpoint + (Object)ChatColor.BLUE + "Abandoned Ship Dungeon Portal", (Object)ChatColor.BLUE + bulletpoint + (Object)ChatColor.BLUE + "Heroic Rank Crystal"});
        }
        if (v == Version.HOLIDAY_2017) {
            bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem((Material)Material.ENDER_CHEST, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)v.crateName), (String[])new String[]{(Object)ChatColor.GREEN + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)ChatColor.GREEN + " at " + (Object)ChatColor.DARK_RED + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "2018 Patch Notes", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Limited Edition Titles", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "5x Max Home Increase", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "1-4x Godly Randomization Scrolls", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Yeti Armor Set Piece", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "High-tier Rank Quest", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1-2x Heroic Cosmic Crates", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1-2x Heroic Enchantment Books", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1-5x Godly Space Flares", "", ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)ChatColor.GREEN + bulletpoint + (Object)ChatColor.GREEN + "Abandoned Spaceship Dungeon Lootbag", (Object)ChatColor.GREEN + bulletpoint + (Object)ChatColor.GREEN + "Holiday 2016 Cosmic Crate", (Object)ChatColor.GREEN + bulletpoint + (Object)ChatColor.GREEN + "Mother of Yijki Set Piece"});
        }
        if (v == Version.JANUARY_2018) {
            bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem((Material)Material.ENDER_CHEST, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)v.crateName), (String[])new String[]{(Object)ChatColor.AQUA + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)ChatColor.AQUA + " at " + (Object)ChatColor.DARK_AQUA + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Old Christmas Cookies", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Limited Edition Titles", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "2-5x Transmog Scrolls", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "2-5x Lore Crystals", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Random Heroic Enchantment Book", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Random Phantom " + ChatColor.UNDERLINE.toString() + "OR" + (Object)ChatColor.GOLD + " Yeti Armor Set Piece", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1-2x 75-100% Weapon/Armor Orb (" + (Object)ChatColor.BOLD + "12 slot" + (Object)ChatColor.GOLD + ")", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "2-3x Legendary " + ChatColor.UNDERLINE.toString() + "OR" + (Object)ChatColor.GOLD + " Godly Space Chests", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "January 2016 " + ChatColor.UNDERLINE.toString() + "OR" + (Object)ChatColor.GOLD + " 2017 Cosmic Crate", "", ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)ChatColor.AQUA + bulletpoint + (Object)ChatColor.AQUA + "Holiday 2017 Cosmic Crate", (Object)ChatColor.AQUA + bulletpoint + (Object)ChatColor.AQUA + "Heroic Fallen Hero Bundle", (Object)ChatColor.AQUA + bulletpoint + (Object)ChatColor.AQUA + "Abandoned Spaceship Dungeon Lootbag"});
        }
        if (v == Version.FEBUARY_2018) {
            bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem((Material)Material.ENDER_CHEST, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)v.crateName), (String[])new String[]{(Object)ChatColor.LIGHT_PURPLE + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)ChatColor.LIGHT_PURPLE + " at " + (Object)ChatColor.DARK_PURPLE + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Icey Flame", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Limited Edition Titles", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "1-3x Item Nametags " + ChatColor.UNDERLINE.toString() + "OR" + (Object)ChatColor.YELLOW + " Transmog Scrolls", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "3-5x Showcase Expansion " + ChatColor.UNDERLINE.toString() + "OR" + (Object)ChatColor.YELLOW + " Max Home Increase", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Mid-tier Rank Quest", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Random /gkit Crystal " + ChatColor.UNDERLINE.toString() + "OR" + (Object)ChatColor.GOLD + " /vkit Crystal", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1x Phantom " + ChatColor.UNDERLINE.toString() + "OR" + (Object)ChatColor.GOLD + " Mother of Yijki Item", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1-5x Mystery Mob Spawners", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Febuary 2016 /cc " + ChatColor.UNDERLINE.toString() + "OR" + (Object)ChatColor.GOLD + " Heroic Mystery Crate", "", ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)ChatColor.LIGHT_PURPLE + bulletpoint + (Object)ChatColor.LIGHT_PURPLE + "Max Rank Quest", (Object)ChatColor.LIGHT_PURPLE + bulletpoint + (Object)ChatColor.LIGHT_PURPLE + "Heroic Rank Crystal", (Object)ChatColor.LIGHT_PURPLE + bulletpoint + (Object)ChatColor.LIGHT_PURPLE + "Abandoned Spaceship Dungeon Lootbag"});
        }
        if (v.getCrateData() != null) {
            List<String> lore = v.getCrateData().getDescriptionLore(username);
            cc = ItemUtils.buildItem((Material)Material.ENDER_CHEST, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)v.crateName), (String[])lore.toArray(new String[lore.size()]));
        }
        if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning()) {
            ItemID.setItemID(cc, (long)ItemID.generateNewItemID());
        }
        return cc;
    }

    public static Version getCosmicCrateVersion(ItemStack i) {
        if (!i.hasItemMeta() || !i.getItemMeta().hasDisplayName() || i.getMaxStackSize() == 1) {
            return null;
        }
        for (Version v : Version.values()) {
            if (!v.crateName.equals(i.getItemMeta().getDisplayName())) continue;
            return v;
        }
        return null;
    }

    public static String getCosmicCrateUnlocker(ItemStack i) {
        Version v = CCAPI.getCosmicCrateVersion(i);
        if (v != null) {
            return ChatColor.stripColor((String)((String)i.getItemMeta().getLore().get(0)).split(" ")[2]);
        }
        return null;
    }

    public static Inventory getCosmicCrateClaimMenu(Player p) {
        Inventory inv = Bukkit.createInventory(null, (int)54, (String)"Cosmic Crate Storage");
        for (Version v : Version.values()) {
            ItemStack item = p.hasPermission(v.getPermissionNode()) ? (CCAPI.hasClaimedCosmicCrate(p, v) ? LocalItemUtils.changeType(CCAPI.getCosmicCrate(v, p.getName()), Material.STAINED_GLASS_PANE, (short)5) : CCAPI.getCosmicCrate(v, p.getName())) : LocalItemUtils.getLockedPlaceholder(p, v);
            if (v.guiSlot != -1) {
                inv.setItem(v.guiSlot, item);
                continue;
            }
            inv.setItem(inv.firstEmpty(), item);
        }
        while (inv.firstEmpty() != -1) {
            inv.setItem(inv.firstEmpty(), LocalItemUtils.getMysteryPlaceholder());
        }
        return inv;
    }

    public static void claimCosmicCrate(Player p, Version v) {
        String uuid = p.getUniqueId().toString();
        List<Object> claimedCrates = CosmicCrate.claimedCosmicCrates.containsKey(uuid) ? CosmicCrate.claimedCosmicCrates.get(uuid) : new ArrayList();
        claimedCrates.add((Object)v);
        CosmicCrate.claimedCosmicCrates.put(uuid, claimedCrates);
        CosmicCrate.sql_query.add("INSERT IGNORE INTO ccrates(uuid, username, version) VALUES('" + uuid + "', '" + p.getName() + "', '" + v.name() + "')");
        p.getInventory().addItem(new ItemStack[]{CCAPI.getCosmicCrate(v, p.getName())});
        p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        RandomFireworks.getManager().launchRandomFirework(p.getLocation());
    }

    public static boolean hasClaimedCosmicCrate(Player p, Version v) {
        return CosmicCrate.claimedCosmicCrates.containsKey(p.getUniqueId().toString()) && CosmicCrate.claimedCosmicCrates.get(p.getUniqueId().toString()).contains((Object)v);
    }

    public static void launchFirework(Location l, Color c) {
        Firework fw = (Firework)l.getWorld().spawnEntity(l, EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();
        FireworkEffect effect = FireworkEffect.builder().flicker(true).withColor(c).withFade(c).with(FireworkEffect.Type.BALL_LARGE).trail(true).build();
        fwm.addEffect(effect);
        fwm.setPower(0);
        fw.setFireworkMeta(fwm);
    }

    public static ItemStack getExplosiveSnowball() {
        return ItemUtils.buildItem((Material)Material.SNOW_BALL, (short)0, (String)(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "Explosive Snowball"), (String[])new String[]{(Object)ChatColor.GRAY + "Yes, this is a snowball with TNT inside.", (Object)ChatColor.GRAY + "Seriously, Happy Holidays!"});
    }

    public static boolean isExplosiveSnowball(ItemStack i) {
        return i != null && i.getType() == Material.SNOW_BALL && i.hasItemMeta() && i.getItemMeta().hasDisplayName() && i.getItemMeta().getDisplayName().equals(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "Explosive Snowball");
    }

    public static List<ItemStack> getCosmicCrateLoot(Player p, Version v) {
        ArrayList<GlobalKits> heroicKits;
        int x;
        List<ItemStack> items = new ArrayList<ItemStack>();
        if (v == Version.DECEMBER) {
            items.add(ItemUtils.buildItem((Material)Material.COOKIE, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&c&lS&a&la&c&ln&c&lt&a&la&c&l's &7&oLittle &d&l[HELPER]!"), (String[])new String[]{(Object)ChatColor.GRAY + "It is dangerous to go alone, so", (Object)ChatColor.GRAY + "take this freshly baked cookie."}));
            items.add(ItemUtils.buildItem((Material)Material.IRON_SPADE, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&7*CLAP* &e&l&nNEW YEARS CLAPPERS &7*CLAP*"), (String[])new String[]{(Object)ChatColor.GRAY + "Ring in the New Years with the", (Object)ChatColor.GRAY + "most annoying sound in the galaxy!"}));
            items.add(ItemUtils.buildItem((Material)Material.CAKE, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&f&lC&c&lA&f&lN&c&lD&f&lY&c&lC&f&lA&c&lN&f&lE &f&l&nCake"), (String[])new String[]{(Object)ChatColor.GRAY + "The perfect mix of sugary goodness", (Object)ChatColor.GRAY + "and minty freshness."}));
            items.add(ItemUtils.getItemNametag());
            items.add(ItemUtils.getTransmogScroll());
            x = new Random().nextInt(3);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher((String)"#naughty"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher((String)"#nice"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher((String)"#2k15"));
            }
            CustomEnchantment ce = TieredEnchantments.getRandomEnchantment((int)(new Random().nextInt(2) + 4));
            items.add(ItemUtils.getEnchantmentBook((CustomEnchantment)ce, (int)(ce.getMaxLevel() - new Random().nextInt(2)), (int)100, (int)0));
            items.add(ItemUtils.getBlackscroll((int)95));
            items.add(ItemUtils.setAmount((ItemStack)EnchantmentBook.getSecretDust((ItemRarity)(new Random().nextBoolean() ? ItemRarity.ULTIMATE : ItemRarity.LEGENDARY), (int)(70 + new Random().nextInt(40))), (int)10));
        }
        if (v == Version.JANURARY) {
            items.add(ItemUtils.buildItem((Material)Material.PUMPKIN, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&e&lNew &6&lYears &f&l&nParty Mask"), (String[])new String[]{(Object)ChatColor.GRAY + "The best way to party?", (Object)ChatColor.GRAY + "Anonymously. Go wild."}));
            items.add(ItemUtils.buildItem((Material)Material.CHEST, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&c&lExecutive Storage Chest"), (String[])new String[]{(Object)ChatColor.GRAY + "A chest with the inventory name", (Object)ChatColor.GRAY + "of 'Executive Storage Chest', so", (Object)ChatColor.GRAY + "people know you mean business."}));
            items.add(ItemUtils.setAmount((ItemStack)NewYears.getExplosiveCake(), (int)(32 + new Random().nextInt(16))));
            items.add(ItemUtils.setAmount((ItemStack)NewYears.getCosmicCracker(), (int)(16 + new Random().nextInt(16))));
            x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher((String)"#2k16"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher((String)"Frosty"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher((String)"Coldasice"));
            }
            if (x == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher((String)"#Target"));
                } else {
                    items.add(ItemUtils.getTitleVoucher((String)"Frosty"));
                }
            }
            items.add(ItemUtils.getWhitescroll());
            items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getCustomTNT((TNTTypes)TNTTypes.Lucky), (int)(8 + new Random().nextInt(9))));
            items.add(KillTrackerAPI.createSoulShard((int)(10 + new Random().nextInt(91))));
            int i = new Random().nextInt(3);
            if (i == 0) {
                items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getMobSpawner((EntityType)EntityType.IRON_GOLEM), (int)2));
            }
            if (i == 1) {
                items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getMobSpawner((EntityType)EntityType.BLAZE), (int)2));
            }
            if (i == 2) {
                items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getMobSpawner((EntityType)EntityType.CREEPER), (int)2));
            }
        }
        if (v == Version.FEBRUARY) {
            items.add(ItemUtils.buildItem((Material)Material.MAP, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&4&lValentine's Day&c &nCard"), (String[])new String[]{(Object)ChatColor.GRAY + "Show that special someone", (Object)ChatColor.GRAY + "that you really do care."}));
            items.add(ItemUtils.buildItem((Material)Material.getMaterial((int)336), (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&e&lBox of &6&l&nChocolates"), (String[])new String[]{(Object)ChatColor.GRAY + "They save love is no different", (Object)ChatColor.GRAY + "than the feeling you get while", (Object)ChatColor.GRAY + "consuming large amounts of chocolate."}));
            items.add(ItemUtils.setAmount((ItemStack)Drinks.getDrink(), (int)(32 + new Random().nextInt(16))));
            items.add(ItemUtils.getItemNametag());
            x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher((String)"#Lovely"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher((String)"BeMine"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher((String)"Cupid"));
            }
            if (x == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher((String)"#ForeverAlone"));
                } else {
                    items.add(ItemUtils.getTitleVoucher((String)"#4EverAlone"));
                }
            }
            items.add(MysteryMobSpawners.getMysteryMobSpawner());
            items.add(ItemUtils.getXpBottle((int)CCAPI.rand(25000, 50000), (String)"Cosmic Crate"));
            items.add(ItemUtils.getMCMMOVoucher((int)CCAPI.rand(25, 100)));
            items.add(ItemUtils.getMoneyNote((String)"Cosmic Crate", (int)CCAPI.rand(100000, 500000)));
        }
        if (v == Version.MARCH) {
            items.add(ItemUtils.buildItem((Material)Material.GLASS_BOTTLE, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&e&lPot of &6&l&nGold"), (String[])new String[]{(Object)ChatColor.GRAY + "Quick, run! They're after your", (Object)ChatColor.GRAY + "lucky charms... and gold!"}));
            items.add(ItemUtils.buildItem((Material)Material.getMaterial((int)38), (short)3, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&a&lF&d&ll&a&lo&d&lw&a&le&d&lr &a&lBouquet"), (String[])new String[]{(Object)ChatColor.GRAY + "The most beautiful bouquet of", (Object)ChatColor.GRAY + "seasonal spring flowers in all", (Object)ChatColor.GRAY + "of the Cosmoverse. So pretty..."}));
            items.add(ItemUtils.getItemNametag());
            items.add(ItemUtils.getTransmogScroll());
            x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher((String)"#Spring"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher((String)"GlobalWarming"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher((String)"#Leprachaun"));
            }
            if (x == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher((String)"#Lucky"));
                } else {
                    items.add(ItemUtils.getTitleVoucher((String)"#Hot"));
                }
            }
            items.add(MysteryMobSpawners.getMysteryMobSpawner());
            items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getCustomCreeperEgg((CreeperTypes)CreeperTypes.Huge), (int)(2 + ThreadLocalRandom.current().nextInt(4))));
            items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getCustomCreeperEgg((CreeperTypes)CreeperTypes.Tactical), (int)(1 + ThreadLocalRandom.current().nextInt(3))));
            items.add(ItemUtils.getBlackscroll((int)100));
        }
        if (v == Version.APRIL) {
            items.add(ItemUtils.buildItem((Material)Material.BOOK_AND_QUILL, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&f&lT&f&nr&f&oo&f&ml&f&ll &f&lL&f&ke&f&ltt&f&me&f&lr"), (String[])new String[]{(Object)ChatColor.GRAY + "You've always been a bit of a", (Object)ChatColor.GRAY + "prankster, " + (Object)ChatColor.GRAY + "who is this letter for?"}));
            items.add(ItemUtils.buildItem((Material)Material.HOPPER, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&c&lC&6&lo&e&ll&a&lo&b&lr&9&lf&d&lu&5&ll &e&l&nUmbrella"), (String[])new String[]{(Object)ChatColor.GRAY + "Spring is notorious for its", (Object)ChatColor.GRAY + "sporadic rain and storms, be", (Object)ChatColor.GRAY + "stylishly prepared for any climate!"}));
            items.add(ItemUtils.getItemNametag());
            items.add(ItemUtils.setAmount((ItemStack)NewYears.getExplosiveCake(), (int)(32 + new Random().nextInt(33))));
            x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher((String)"#Timeless"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher((String)"#April"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher((String)"Prankster"));
            }
            if (x == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher((String)"RanD"));
                } else {
                    items.add(ItemUtils.getTitleVoucher((String)"Troll"));
                }
            }
            items.add(KitItems.getKitItem((GlobalKits)GlobalKits.values()[new Random().nextInt(GlobalKits.values().length)]));
            items.add(CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.LEGENDARY));
            items.add(MysteryMobSpawners.getMysteryMobSpawner());
            items.add(ItemUtils.getWhitescroll());
        }
        if (v == Version.JULY) {
            items.add(ItemUtils.buildItem((Material)Material.WATER_LILY, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&f&lI&c&ln&9&ld&f&le&c&lp&9&le&f&ln&c&ld&9&le&f&ln&c&lc&9&le &f&l&nFlag"), (String[])new String[]{(Object)ChatColor.GRAY + "Wave this flag loud and proud,", (Object)ChatColor.GRAY + "for you are FREE."}));
            items.add(ItemUtils.buildItem((Material)Material.ITEM_FRAME, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&c&lDeclaration &9&lof &f&l&nFREEDOM"), (String[])new String[]{(Object)ChatColor.GRAY + "An ancient document rumored to", (Object)ChatColor.GRAY + "predate the Cosmonauts themselves,", (Object)ChatColor.GRAY + "it outlines the basic rights of", (Object)ChatColor.GRAY + "every humanoid creature in the galaxy."}));
            items.add(ItemUtils.getTransmogScroll());
            items.add(ItemUtils.setAmount((ItemStack)NewYears.getCosmicCracker(), (int)(16 + new Random().nextInt(32))));
            x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher((String)"#FREE"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher((String)"NotASlave"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher((String)"Independent"));
            }
            if (x == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher((String)"#NotForSale"));
                } else {
                    items.add(ItemUtils.getTitleVoucher((String)"#Freedom"));
                }
            }
            items.add(CosmicSpaceChestsAPI.getSpaceFlare((ItemRarity)ItemRarity.LEGENDARY));
            items.add(FallenHero.getFallenHeroItem((GlobalKits)GlobalKits.values()[new Random().nextInt(GlobalKits.values().length)]));
            items.add(FallenHero.getFallenHeroItem((GlobalKits)GlobalKits.values()[new Random().nextInt(GlobalKits.values().length)]));
            heroicKits = new ArrayList();
            for (GlobalKits gkit : GlobalKits.values()) {
                if (!gkit.getItemName().contains("Heroic")) continue;
                heroicKits.add(gkit);
            }
            items.add(FallenHero.getFallenHeroItem((GlobalKits)((GlobalKits)heroicKits.get(new Random().nextInt(heroicKits.size())))));
        }
        if (v == Version.AUGUST) {
            items.add(ItemUtils.buildItem((Material)Material.SULPHUR, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&c&lMojang &4&l&n#EULA"), (String[])new String[]{(Object)ChatColor.GRAY + "Ah yes the #EULA, commonly mistaken as", (Object)ChatColor.GRAY + "the \"End of User Love Agreement\"."}));
            items.add(ItemUtils.buildItem((Material)Material.LAVA_BUCKET, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&a&lFreshly &c&l&nSqueezed&r &e&lLEMONADE!"), (String[])new String[]{(Object)ChatColor.GRAY + "Wait, what do you mean this is not", (Object)ChatColor.GRAY + "lemonade? It says so right in", (Object)ChatColor.GRAY + "the item name!"}));
            items.add(ItemUtils.getItemNametag());
            items.add(ItemUtils.getItemNametag());
            x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher((String)"#Pay2Play"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher((String)"$9001"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher((String)"#WePayWeSay"));
            }
            if (x == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher((String)"#Pay2Win"));
                } else {
                    items.add(ItemUtils.getTitleVoucher((String)"$CASH$"));
                }
            }
            items.add(CosmicSpaceChestsAPI.getSpaceFlare((ItemRarity)ItemRarity.LEGENDARY));
            heroicKits = new ArrayList();
            for (GlobalKits gkit : GlobalKits.values()) {
                if (!gkit.getItemName().contains("Heroic")) continue;
                heroicKits.add(gkit);
            }
            items.add(FallenHero.getFallenHeroItem((GlobalKits)((GlobalKits)heroicKits.get(new Random().nextInt(heroicKits.size())))));
            items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getBlackscroll((int)100), (int)2));
            int donorTier = new Random().nextInt(5) + 1;
            if (donorTier >= 5) {
                donorTier -= new Random().nextInt(2);
            }
            if (donorTier >= 4) {
                donorTier -= new Random().nextInt(2);
            }
            items.add(CosmicRankQuestsAPI.getRankQuestItem((int)donorTier));
        }
        if (v == Version.SCHOOL) {
            items.add(ItemUtils.buildItem((Material)Material.PAPER, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&6&l&mCheat&f&l Sheet"), (String[])new String[]{(Object)ChatColor.GRAY + "They can't punish you if they", (Object)ChatColor.GRAY + "can't catch you."}));
            items.add(ItemUtils.buildItem((Material)Material.CHEST, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&f&l&nMeme&7 BACKPACK"), (String[])new String[]{(Object)ChatColor.GRAY + "Your mom disagreed, but you just", (Object)ChatColor.GRAY + "know this backpack is going to make", (Object)ChatColor.GRAY + "you the coolest kid in school this year!"}));
            items.add(ItemUtils.getItemNametag());
            x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher((String)"#Back2School"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher((String)"CyaInDetention"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher((String)"OnTheGrind"));
            }
            if (x == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher((String)"DROP_OUT"));
                } else {
                    items.add(ItemUtils.getTitleVoucher((String)"#Harambe"));
                }
            }
            heroicKits = new ArrayList<GlobalKits>();
            for (GlobalKits gkit : GlobalKits.values()) {
                if (!gkit.getItemName().contains("Heroic")) continue;
                heroicKits.add(gkit);
            }
            items.add(FallenHero.getFallenHeroItem((GlobalKits)((GlobalKits)heroicKits.get(new Random().nextInt(heroicKits.size())))));
            items.add(FallenHero.getFallenHeroItem((GlobalKits)((GlobalKits)heroicKits.get(new Random().nextInt(heroicKits.size())))));
            items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getBlackscroll((int)90), (int)3));
            items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getWhitescroll(), (int)2));
            items.add(ItemUtils.setAmount((ItemStack)EnchantmentBook.getSecretDust((ItemRarity)(new Random().nextBoolean() ? ItemRarity.ULTIMATE : ItemRarity.LEGENDARY), (int)(70 + new Random().nextInt(40))), (int)15));
        }
        if (v == Version.OCTOBER) {
            items.add(ItemUtils.buildItem((Material)Material.LEAVES, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&6&lLucky &a&nLeaf"), (String[])new String[]{(Object)ChatColor.GRAY + "For whatever reason, you feel unusually", (Object)ChatColor.GRAY + "lucky while holding this leaf."}));
            items.add(ItemUtils.buildItem((Material)Material.PUMPKIN, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&8&l&nSpace&r &6&lP&e&lumpki&6&ln"), (String[])new String[]{(Object)ChatColor.GRAY + "A pumpkin discovered by the", (Object)ChatColor.GRAY + "Cosmonauts while exploring space!", (Object)ChatColor.GRAY + "What secrest recipes might it hold?!"}));
            items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getTransmogScroll(), (int)2));
            x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher((String)"Sp00ky"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher((String)"CandyCrazy"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher((String)"#WinterIsComing"));
            }
            if (x == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher((String)"Ghost"));
                } else {
                    items.add(ItemUtils.getTitleVoucher((String)"#TrickOrGank"));
                }
            }
            items.add(FallenHero.getFallenHeroItem((GlobalKits)GlobalKits.values()[new Random().nextInt(GlobalKits.values().length)]));
            items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getBlackscroll((int)100), (int)2));
            items.add(CosmicSpaceChestsAPI.getSpaceFlare((ItemRarity)ItemRarity.LEGENDARY));
            items.add(MysteryMobSpawners.getMysteryMobSpawner());
            items.add(CosmicFactionBoosters.get().getBoosterManager().createBooster(Math.random() < 0.35 ? BoosterType.EXP : BoosterType.MCMMO, 600, 2.0));
        }
        if (v == Version.HALLOWEEN) {
            items.add(ItemUtils.buildItem((Material)Material.INK_SACK, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&e&lBag of... &d&lC&a&la&d&ln&a&ld&d&ly"), (String[])new String[]{(Object)ChatColor.GRAY + "A giant halloween bag stuffed full of", (Object)ChatColor.GRAY + "yummy, sugar-coated, delicious pieces", (Object)ChatColor.GRAY + "of 2016 CANDY!!"}));
            items.add(ItemUtils.buildItem((Material)Material.CHAINMAIL_HELMET, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&4&lClown &f&lMask"), (String[])new String[]{(Object)ChatColor.GRAY + "An eerie and chilling mask", (Object)ChatColor.GRAY + "used to hide one's true identity", (Object)ChatColor.GRAY + "while committing unspeakable acts."}));
            items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getItemNametag(), (int)2));
            x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher((String)"#TrickOrDerp"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher((String)"#2spooky4me"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher((String)"iLoveSugar"));
            }
            if (x == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher((String)"ImJustClowningAround"));
                } else {
                    items.add(ItemUtils.getTitleVoucher((String)"#ClownLivesMatter"));
                }
            }
            items.add(FallenHero.getFallenHeroItem((GlobalKits)GlobalKits.values()[new Random().nextInt(GlobalKits.values().length)]));
            items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getBlackscroll((int)66), (int)6));
            items.add(ItemUtils.setAmount((ItemStack)EnchantmentBook.getSecretDust((ItemRarity)ItemRarity.SOUL, (int)new Random().nextInt(71)), (int)5));
            items.add(ItemUtils.setAmount((ItemStack)MysteryMobSpawners.getMysteryMobSpawner(), (int)2));
            items.add(CosmicFactionBoosters.get().getBoosterManager().createBooster(Math.random() < 0.35 ? BoosterType.EXP : BoosterType.MCMMO, 366, 6.66));
        }
        if (v == Version.NOVEMBER) {
            items.add(ItemUtils.buildItem((Material)Material.INK_SACK, (short)11, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&eThanks&f&l-&6&l&nGIVING&r &cHorn"), (String[])new String[]{(Object)ChatColor.GRAY + "An plentiful amount of freshly harvested", (Object)ChatColor.GRAY + "fruits, space vegetables, and meats", (Object)ChatColor.GRAY + "wrapped in an enchanted weaved basket."}));
            items.add(ItemUtils.buildItem((Material)Material.CHAINMAIL_HELMET, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&5&l&nPilgram&r &d&l&nHat"), (String[])new String[]{(Object)ChatColor.GRAY + "A timeless classic in the world", (Object)ChatColor.GRAY + "of high fashion -- worn by", (Object)ChatColor.GRAY + "farmers and presidents alike!"}));
            items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getTransmogScroll(), (int)(new Random().nextInt(2) + 2)));
            x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher((String)"MakeLoveNotMinecraft"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher((String)"#Thankful"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher((String)"IAmNotATurkey"));
            }
            if (x == 3) {
                if (Math.random() < 0.25) {
                    items.add(ItemUtils.getTitleVoucher((String)"#NoShaveNovember"));
                } else {
                    items.add(ItemUtils.getTitleVoucher((String)"#TurkeyLivesMatter"));
                }
            }
            items.add(CosmicFactionBoosters.get().getBoosterManager().createBooster(Math.random() < 0.35 ? BoosterType.EXP : BoosterType.MCMMO, 660, 4.0));
            heroicKits = new ArrayList();
            for (GlobalKits gkit : GlobalKits.values()) {
                if (!gkit.getItemName().contains("Heroic")) continue;
                heroicKits.add(gkit);
            }
            items.add(ItemUtils.setAmount((ItemStack)FallenHero.getFallenHeroItem((GlobalKits)((GlobalKits)heroicKits.get(new Random().nextInt(heroicKits.size())))), (int)2));
            items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getBlackscroll((int)99), (int)3));
            items.add(CosmicSpaceChestsAPI.getSpaceFlare((ItemRarity)ItemRarity.LEGENDARY));
            items.add(ItemUtils.setAmount((ItemStack)EnchantmentBook.getSecretDust((ItemRarity)(new Random().nextBoolean() ? ItemRarity.ULTIMATE : ItemRarity.LEGENDARY), (int)(70 + new Random().nextInt(40))), (int)15));
        }
        if (v == Version.BLACK_FRIDAY) {
            items.add(ItemUtils.buildItem((Material)Material.BOOK, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&f&lA &a&l&nGood&f&l Deal"), (String[])new String[]{(Object)ChatColor.GRAY + "This book supposedly contains a", (Object)ChatColor.GRAY + "very good business deal. Perhaps", (Object)ChatColor.GRAY + "President Trump would be interested?"}));
            items.add(ItemUtils.buildItem((Material)Material.PAPER, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&4&l&nFake&f&l &n100%&f&l-Off Coupon"), (String[])new String[]{(Object)ChatColor.GRAY + "We had to put 'Fake' in the", (Object)ChatColor.GRAY + "name of this item because we", (Object)ChatColor.GRAY + "knew players would riot about not", (Object)ChatColor.GRAY + "actually getting 100% off everything", (Object)ChatColor.GRAY + "in the server store. So yes, it is fake."}));
            items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getItemNametag(), (int)3));
            x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher((String)"WALMART"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher((String)"EverythingMustGo"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher((String)"#BlackFriday"));
            }
            if (x == 3) {
                if (Math.random() < 0.25) {
                    items.add(ItemUtils.getTitleVoucher((String)"#PresidentTrump"));
                } else {
                    items.add(ItemUtils.getTitleVoucher((String)"ThankfulForBeingRich"));
                }
            }
            items.add(CosmicFactionBoosters.get().getBoosterManager().createBooster(Math.random() < 0.35 ? BoosterType.EXP : BoosterType.MCMMO, 300, 9.99));
            items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getWhitescroll(), (int)(new Random().nextInt(2) + 1)));
            items.add(EnchantmentOrb.getEnchantmentOrb((int)(new Random().nextInt(25) + 70), (int)10, (OrbType)OrbType.ARMOR));
            items.add(ItemUtils.setAmount((ItemStack)EnchantmentBook.getSecretDust((ItemRarity)(new Random().nextBoolean() ? ItemRarity.ULTIMATE : ItemRarity.LEGENDARY), (int)(70 + new Random().nextInt(40))), (int)10));
            items.add(CosmicRankQuestsAPI.getRankQuestItem((int)1));
        }
        if (v == Version.DECEMBER_2016) {
            items.add(ItemUtils.buildItem((Material)Material.MILK_BUCKET, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&f&l&nWinter&r &9&lW&b&lo&9&ln&b&ld&9&le&b&lr&9&ll&b&la&9&ln&b&ld"), (String[])new String[]{(Object)ChatColor.GRAY + "A mystical, icy cold bucket of...", (Object)ChatColor.GRAY + "Winter Wonder? Whatever it is,", (Object)ChatColor.GRAY + "it has a chilling aura around it."}));
            items.add(ItemUtils.buildItem((Material)Material.LEATHER_BOOTS, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&c&lCozy &6&lSpace &c&lMittens"), (String[])new String[]{(Object)ChatColor.GRAY + "Guranteed to keep your ligiments warm", (Object)ChatColor.GRAY + "at temperatures as low as -270.45C!"}));
            items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getTransmogScroll(), (int)(2 + new Random().nextInt(2))));
            x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher((String)"#ICY"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher((String)"WinterIsComing"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher((String)"KeepCalmAndStayToasty"));
            }
            if (x == 3) {
                if (Math.random() < 0.25) {
                    items.add(ItemUtils.getTitleVoucher((String)"0C"));
                } else {
                    items.add(ItemUtils.getTitleVoucher((String)"EveryDayImShovelin"));
                }
            }
            items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getWhitescroll(), (int)(new Random().nextInt(2) + 1)));
            items.add(ItemUtils.setAmount((ItemStack)EnchantmentOrb.getEnchantmentOrb((int)(new Random().nextInt(26) + 50), (int)10, (OrbType)OrbType.ARMOR), (int)2));
            items.add(ItemUtils.setAmount((ItemStack)EnchantmentOrb.getEnchantmentOrb((int)(new Random().nextInt(31) + 30), (int)10, (OrbType)OrbType.WEAPON), (int)1));
            items.add(CosmicSpaceChestsAPI.getSpaceFlare((ItemRarity)ItemRarity.LEGENDARY));
            items.add(ItemUtils.setAmount((ItemStack)FallenHero.getFallenHeroItem((GlobalKits)GlobalKits.values()[new Random().nextInt(GlobalKits.values().length)]), (int)2));
        }
        if (v == Version.HOLIDAY) {
            items.add(ItemUtils.buildItem((Material)Material.DIAMOND, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&a&lH&4&lo&a&ll&4&li&a&ld&4&la&4&ly &f&l&nSpirit"), (String[])new String[]{(Object)ChatColor.GRAY + "All the holiday spirit of a planet,", (Object)ChatColor.GRAY + "captured and trapped within a beautiful,", (Object)ChatColor.GRAY + "soul crystal. Oh, how very jolly indeed."}));
            items.add(ItemUtils.setAmount((ItemStack)CCAPI.getExplosiveSnowball(), (int)10));
            items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getItemNametag(), (int)3));
            x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher((String)"#XMAS"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher((String)"#2k17"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher((String)"Santa"));
            }
            if (x == 3) {
                if (Math.random() < 0.25) {
                    items.add(ItemUtils.getTitleVoucher((String)"NeitherNaughtyNorNice"));
                } else {
                    items.add(ItemUtils.getTitleVoucher((String)"IFindYourLackOfCheerDisturbing"));
                }
            }
            items.add(ItemUtils.setAmount((ItemStack)EnchantmentOrb.getEnchantmentOrb((int)(new Random().nextInt(26) + 75), (int)10, (OrbType)OrbType.ARMOR), (int)(2 + new Random().nextInt(2))));
            items.add(ItemUtils.setAmount((ItemStack)EnchantmentOrb.getEnchantmentOrb((int)(new Random().nextInt(26) + 75), (int)10, (OrbType)OrbType.WEAPON), (int)(1 + new Random().nextInt(2))));
            heroicKits = new ArrayList();
            for (GlobalKits gkit : GlobalKits.values()) {
                if (!gkit.getItemName().contains("Heroic")) continue;
                heroicKits.add(gkit);
            }
            items.add(ItemUtils.setAmount((ItemStack)FallenHero.getFallenHeroItem((GlobalKits)((GlobalKits)heroicKits.get(new Random().nextInt(heroicKits.size())))), (int)3));
            int rankId = 1;
            if (Math.random() < 0.25) {
                rankId = 2;
                if (Math.random() < 0.05) {
                    rankId = 3;
                }
            }
            items.add(CosmicRankQuestsAPI.getRankQuestItem((int)rankId));
            items.add(EvolutionKitItems.getKitItem((EvolutionKits)EvolutionKits.PHOENIX));
        }
        if (v == Version.JANUARY_2017) {
            items.add(ItemUtils.buildItem((Material)Material.PAPER, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&3&l&nNew Years&f&l Resolution"), (String[])new String[]{(Object)ChatColor.GRAY + "This thin piece of paper represents", (Object)ChatColor.GRAY + "your determination to change absolutely,", (Object)ChatColor.GRAY + "nothing about yourself from last year...", (Object)ChatColor.GRAY + "or something like that."}));
            items.add(ItemUtils.buildItem((Material)Material.GOLD_HELMET, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&a&l2&b&l0&e&l1&d&l7 &f&l&nTrophy"), (String[])new String[]{(Object)ChatColor.GRAY + "The year has only just started,", (Object)ChatColor.GRAY + "but you know you have already won."}));
            items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getTransmogScroll(), (int)(2 + new Random().nextInt(2))));
            x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher((String)"#NotMyPlanet"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher((String)"2017_OG"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher((String)"Renewal"));
            }
            if (x == 3) {
                if (Math.random() < 0.25) {
                    items.add(ItemUtils.getTitleVoucher((String)"#NewYearNewMemes"));
                } else {
                    items.add(ItemUtils.getTitleVoucher((String)"BuildThatWall"));
                }
            }
            items.add(CosmicFactionBoosters.get().getBoosterManager().createBooster(Math.random() < 0.35 ? BoosterType.EXP : BoosterType.MCMMO, 600, 5.0));
            items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getWhitescroll(), (int)(new Random().nextInt(2) + 2)));
            items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getBlackscroll((int)90), (int)(new Random().nextInt(3) + 3)));
            items.add(CosmicSpaceChestsAPI.getSpaceFlare((ItemRarity)ItemRarity.GOD));
            items.add(KitItems.getKitItem((GlobalKits)GlobalKits.values()[new Random().nextInt(GlobalKits.values().length)]));
        }
        if (v == Version.VALENTINE) {
            items.add(ItemUtils.buildItem((Material)Material.PAPER, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&f&lForever &4&l&nAlone&f&l Card"), (String[])new String[]{(Object)ChatColor.GRAY + "This card is your official membership to", (Object)ChatColor.GRAY + "the exclusive \"Forever Alone\" club...", (Object)ChatColor.GRAY + "Mom would be so proud of you."}));
            items.add(ItemUtils.buildItem((Material)Material.TRAPPED_CHEST, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&f&lBox of &0&l&nMemes"), (String[])new String[]{(Object)ChatColor.GRAY + "The only box ever constructed with more,", (Object)ChatColor.GRAY + "(dank) power than Pandora's Box."}));
            items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getItemNametag(), (int)(1 + new Random().nextInt(3))));
            x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher((String)"#CandyNCuddle"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher((String)"AnyGirls?"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher((String)"#CosmicNChill"));
            }
            if (x == 3) {
                if (Math.random() < 0.01) {
                    items.add(ItemUtils.getTitleVoucher((String)"(/\u00ef\u00bf\u00bd^\u00ef\u00bf\u00bd)_/L"));
                } else {
                    items.add(ItemUtils.getTitleVoucher((String)"#IHaveNoLifeAndIDoNotCare"));
                }
            }
            items.add(EvolutionKitItems.getKitItem((EvolutionKits)EvolutionKits.TROLL));
            items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getWhitescroll(), (int)(new Random().nextInt(4) + 1)));
            items.add(ItemUtils.setAmount((ItemStack)FallenHero.getFallenHeroItem((GlobalKits)GlobalKits.values()[new Random().nextInt(GlobalKits.values().length)]), (int)2));
            items.add(ItemUtils.setAmount((ItemStack)EnchantmentBook.getSecretDust((ItemRarity)ItemRarity.SOUL, (int)(new Random().nextInt(60) + 20)), (int)(new Random().nextInt(3) + 2)));
            if (Math.random() < 0.33) {
                items.add(ItemUtils.setAmount((ItemStack)EnchantmentOrb.getEnchantmentOrb((int)(new Random().nextInt(11) + 90), (int)10, (OrbType)OrbType.WEAPON), (int)1));
            } else {
                items.add(ItemUtils.setAmount((ItemStack)EnchantmentOrb.getEnchantmentOrb((int)(new Random().nextInt(11) + 90), (int)10, (OrbType)OrbType.ARMOR), (int)1));
            }
        }
        if (v == Version.MARCH_2017) {
            items.add(ItemUtils.buildItem((Material)Material.STICK, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&a&l&nSpring&r &b&lTotem"), (String[])new String[]{(Object)ChatColor.GRAY + "An enchanted wooden totem that", (Object)ChatColor.GRAY + "echoes the spirits of the ancients...", (Object)ChatColor.GRAY + "how bout that?"}));
            items.add(ItemUtils.buildItem((Material)Material.EMERALD, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&e&nLuck of the...&r &a&lIrish"), (String[])new String[]{(Object)ChatColor.GRAY + "A magical gem imbued with countless", (Object)ChatColor.GRAY + "generations of some of the luckiest", (Object)ChatColor.GRAY + "blockmen and women to ever live."}));
            items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getTransmogScroll(), (int)3));
            x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher((String)"Leprechaun"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher((String)"#IsItStillWinter?"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher((String)"#GotLucky"));
            }
            if (x == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher((String)"qt3.14"));
                } else {
                    items.add(ItemUtils.getTitleVoucher((String)"#CatchMeOutside"));
                }
            }
            items.add(FallenHero.getFallenHeroItem((EvolutionKit)(new Random().nextBoolean() ? EvolutionKits.TROLL.getKit() : EvolutionKits.PHOENIX.getKit())));
            items.add(ItemUtils.setAmount((ItemStack)FallenHero.getFallenHeroItem((GlobalKits)GlobalKits.values()[new Random().nextInt(GlobalKits.values().length)]), (int)(1 + new Random().nextInt(3))));
            heroicKits = new ArrayList();
            for (GlobalKits gkit : GlobalKits.values()) {
                if (!gkit.getItemName().contains("Heroic")) continue;
                heroicKits.add(gkit);
            }
            items.add(ItemUtils.setAmount((ItemStack)FallenHero.getFallenHeroItem((GlobalKits)((GlobalKits)heroicKits.get(new Random().nextInt(heroicKits.size())))), (int)2));
            int rankId = 1;
            if (Math.random() < 0.5) {
                rankId = 2;
                if (Math.random() < 0.25) {
                    rankId = 3;
                }
            }
            items.add(CosmicRankQuestsAPI.getRankQuestItem((int)rankId));
            if (Math.random() < 0.33) {
                items.add(ItemUtils.setAmount((ItemStack)EnchantmentOrb.getEnchantmentOrb((int)(new Random().nextInt(26) + 75), (int)10, (OrbType)OrbType.WEAPON), (int)(3 + new Random().nextInt(2))));
            } else {
                items.add(ItemUtils.setAmount((ItemStack)EnchantmentOrb.getEnchantmentOrb((int)(new Random().nextInt(26) + 75), (int)10, (OrbType)OrbType.ARMOR), (int)(3 + new Random().nextInt(2))));
            }
        }
        if (v == Version.APRIL_2017) {
            items.add(ItemUtils.buildItem((Material)Material.RAW_FISH, (short)3, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&e&lFool of &d&l&nApril"), (String[])new String[]{(Object)ChatColor.GRAY + "Quite possibly the most derpy item in Minecraft.", (Object)ChatColor.GRAY + "... :/"}));
            items.add(ItemUtils.buildItem((Material)Material.CHEST, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&a&l&nEaster&r &d&l&nBasket"), (String[])new String[]{(Object)ChatColor.GRAY + "A magically woven cosmic basket", (Object)ChatColor.GRAY + "designed exclusively to collect", (Object)ChatColor.GRAY + "and store Easter Eggs in!"}));
            items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getItemNametag(), (int)(2 + new Random().nextInt(2))));
            x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher((String)"#AprilFools"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher((String)"EasterBunny"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher((String)"Edgy"));
            }
            if (x == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher((String)"Sociopath"));
                } else {
                    items.add(ItemUtils.getTitleVoucher((String)"Joker"));
                }
            }
            items.add(ItemUtils.setAmount((ItemStack)Easter.getEasterEgg(), (int)(10 + new Random().nextInt(6))));
            if (Math.random() < 0.4) {
                items.add(ItemUtils.setAmount((ItemStack)FallenHero.getFallenHeroItem((EvolutionKit)(Math.random() < 0.3 ? EvolutionKits.ALCHEMIST.getKit() : (Math.random() < 0.5 ? EvolutionKits.PHOENIX.getKit() : EvolutionKits.TROLL.getKit()))), (int)(1 + new Random().nextInt(2))));
            } else {
                items.add(ItemUtils.setAmount((ItemStack)FallenHero.getFallenHeroItem((GlobalKits)GlobalKits.values()[new Random().nextInt(GlobalKits.values().length)]), (int)(1 + new Random().nextInt(2))));
            }
            items.add(ItemUtils.getNewBossEgg((BossType)BossType.SOUL_REAPER));
            items.add(RandomizationScroll.getRandomizationScroll((ItemRarity)ItemRarity.GOD));
            items.add(CosmicFactionBoosters.get().getBoosterManager().createBooster(Math.random() < 0.45 ? BoosterType.EXP : BoosterType.MCMMO, 600, 5.0));
        }
        if (v == Version.MAY_2017) {
            items.add(ItemUtils.buildItem((Material)Material.RED_ROSE, (short)8, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&a&lSpring...&2&l is coming."), (String[])new String[]{(Object)ChatColor.GRAY + "You knew winter was coming, but no one", (Object)ChatColor.GRAY + "prepared you for... SPRING 2017!"}));
            items.add(ItemUtils.buildItem((Material)Material.INK_SACK, (short)9, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&f&lVitamin &e&l&nD"), (String[])new String[]{(Object)ChatColor.GRAY + "Spending all your days indoors", (Object)ChatColor.GRAY + "slaying monsters is tough work,", (Object)ChatColor.GRAY + "replenish yourself with some Vitamin D!"}));
            items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getTransmogScroll(), (int)(2 + new Random().nextInt(3))));
            x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher((String)"#May2017"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher((String)"Sunny"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher((String)"SpringIsComing"));
            }
            if (x == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher((String)"TheSunlightHurtsMyEyes"));
                } else {
                    items.add(ItemUtils.getTitleVoucher((String)"#WaitingForWinter"));
                }
            }
            if (Math.random() < 0.33) {
                items.add(ItemUtils.setAmount((ItemStack)EnchantmentOrb.getEnchantmentOrb((int)(new Random().nextInt(26) + 75), (int)10, (OrbType)OrbType.WEAPON), (int)2));
            } else {
                items.add(ItemUtils.setAmount((ItemStack)EnchantmentOrb.getEnchantmentOrb((int)(new Random().nextInt(26) + 75), (int)10, (OrbType)OrbType.ARMOR), (int)2));
            }
            items.add(ItemUtils.setAmount((ItemStack)FallenHero.getFallenHeroItem((EvolutionKit)(Math.random() < 0.3 ? EvolutionKits.ALCHEMIST.getKit() : (Math.random() < 0.5 ? EvolutionKits.PHOENIX.getKit() : EvolutionKits.TROLL.getKit()))), (int)(1 + new Random().nextInt(2))));
            items.add(ItemUtils.setAmount((ItemStack)FallenHero.getFallenHeroItem((GlobalKits)GlobalKits.values()[new Random().nextInt(GlobalKits.values().length)]), (int)(1 + new Random().nextInt(2))));
            items.add(ItemUtils.setAmount((ItemStack)RandomizationScroll.getRandomizationScroll((ItemRarity)ItemRarity.GOD), (int)(2 + new Random().nextInt(2))));
            items.add(CosmicConquestAPI.createFactionToken((int)(5 + new Random().nextInt(11))));
        }
        if (v == Version.JUNE_2017) {
            items.add(ItemUtils.buildItem((Material)Material.PAPER, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&c&l&nDetention&r &f&lSlip"), (String[])new String[]{(Object)ChatColor.GRAY + "You tried to explain, but they", (Object)ChatColor.GRAY + "did not understand that it was", (Object)ChatColor.GRAY + "" + (Object)ChatColor.UNDERLINE + "just a prank, bro."}));
            items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getItemNametag(), (int)(2 + new Random().nextInt(2))));
            x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher((String)"#June2017"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher((String)"SchoolIsCruel"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher((String)"#IGotSmarts"));
            }
            if (x == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher((String)"BasicallyNaked"));
                } else {
                    items.add(ItemUtils.getTitleVoucher((String)"#NoSeasons"));
                }
            }
            if (Math.random() < 0.3) {
                items.add(ItemUtils.setAmount((ItemStack)EnchantmentOrb.getEnchantmentOrb((int)(new Random().nextInt(16) + 80), (int)10, (OrbType)OrbType.WEAPON), (int)(1 + new Random().nextInt(5))));
            } else {
                items.add(ItemUtils.setAmount((ItemStack)EnchantmentOrb.getEnchantmentOrb((int)(new Random().nextInt(16) + 80), (int)10, (OrbType)OrbType.ARMOR), (int)(1 + new Random().nextInt(5))));
            }
            heroicKits = new ArrayList();
            for (GlobalKits gkit : GlobalKits.values()) {
                if (!gkit.getItemName().contains("Heroic")) continue;
                heroicKits.add(gkit);
            }
            items.add(ItemUtils.setAmount((ItemStack)FallenHero.getFallenHeroItem((GlobalKits)((GlobalKits)heroicKits.get(new Random().nextInt(heroicKits.size())))), (int)(1 + new Random().nextInt(3))));
            items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getWhitescroll(), (int)(3 + new Random().nextInt(6))));
            items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getBlackscroll((int)95), (int)(5 + new Random().nextInt(6))));
            items.add(KitItems.getKitItem((GlobalKits)GlobalKits.values()[new Random().nextInt(GlobalKits.values().length)]));
            int rankId = 1;
            if (Math.random() < 0.5) {
                rankId = 2;
                if (Math.random() < 0.25) {
                    rankId = 3;
                }
            }
            items.add(CosmicRankQuestsAPI.getRankQuestItem((int)rankId));
        }
        if (v == Version.JULY_2017) {
            items.add(ItemUtils.buildItem((Material)Material.FIREWORK, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&4&l&mIllegal&r &c&lFire&cworks"), (String[])new String[]{(Object)ChatColor.GRAY + "Smuggled from the furthest reaches", (Object)ChatColor.GRAY + "of the Cosmoverse, these are some", (Object)ChatColor.GRAY + "" + (Object)ChatColor.UNDERLINE + "BIG." + (Object)ChatColor.GRAY + " ASS. FIREWORKS."}));
            if (Math.random() < 0.6) {
                items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getItemNametag(), (int)3));
            } else {
                items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getTransmogScroll(), (int)3));
            }
            x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher((String)"#IPlayForMyself"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher((String)"FR33_K"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher((String)"BaseSlave"));
            }
            if (x == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher((String)"Donald_Trump"));
                } else {
                    items.add(ItemUtils.getTitleVoucher((String)"Pyromaniac"));
                }
            }
            items.add(ItemUtils.setAmount((ItemStack)CosmicSpaceChestsAPI.getSpaceFlare((ItemRarity)ItemRarity.GOD), (int)(1 + new Random().nextInt(2))));
            if (Math.random() < 0.35) {
                items.add(ItemUtils.setAmount((ItemStack)EnchantmentOrb.getEnchantmentOrb((int)25, (int)11, (OrbType)OrbType.WEAPON), (int)(1 + new Random().nextInt(2))));
            } else {
                items.add(ItemUtils.setAmount((ItemStack)EnchantmentOrb.getEnchantmentOrb((int)25, (int)11, (OrbType)OrbType.ARMOR), (int)(1 + new Random().nextInt(2))));
            }
            items.add(ItemUtils.setAmount((ItemStack)RandomizationScroll.getRandomizationScroll((ItemRarity)ItemRarity.GOD), (int)(3 + new Random().nextInt(2))));
            items.add(CosmicFactionBoosters.get().getBoosterManager().createBooster(Math.random() < 0.4 ? BoosterType.EXP : BoosterType.MCMMO, 600, 7.4));
            items.add(ItemUtils.setAmount((ItemStack)FallenHero.getFallenHeroItem((EvolutionKit)(Math.random() < 0.25 ? EvolutionKits.ALCHEMIST.getKit() : (Math.random() < 0.5 ? EvolutionKits.PHOENIX.getKit() : (Math.random() < 0.75 ? EvolutionKits.TROLL.getKit() : EvolutionKits.OGRE.getKit())))), (int)(1 + new Random().nextInt(3))));
            int rankId = 1;
            if (Math.random() < 0.6) {
                rankId = 2;
                if (Math.random() < 0.25) {
                    rankId = 3;
                }
                if (Math.random() < 0.1) {
                    rankId = 4;
                }
            }
            items.add(CosmicRankQuestsAPI.getRankQuestItem((int)rankId));
        }
        if (v == Version.AUGUST_2017) {
            items.add(ItemUtils.buildItem((Material)Material.YELLOW_FLOWER, (short)8, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&f&lSummer &a&n&oVibes"), (String[])new String[]{(Object)ChatColor.GRAY + "You cannot help it, school is", (Object)ChatColor.GRAY + "out. It's summer everyday, bro."}));
            items.add(ItemUtils.setAmount((ItemStack)ShowcaseExpander.getShowcaseExpanderItem(), (int)(1 + new Random().nextInt(3))));
            items.add(ItemUtils.setAmount((ItemStack)EssentialsAddHomes.getAddHomeItem(), (int)(1 + new Random().nextInt(2))));
            x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher((String)"#ScummerVibes"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher((String)"CheckMyTanLines"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher((String)"#NoSchoolNoProblems"));
            }
            if (x == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher((String)"#JakePauler"));
                } else {
                    items.add(ItemUtils.getTitleVoucher((String)"IAmSoBored"));
                }
            }
            items.add(ItemUtils.setAmount((ItemStack)CosmicSpaceChestsAPI.getSpaceFlare((ItemRarity)ItemRarity.LEGENDARY), (int)(2 + new Random().nextInt(3))));
            if (Math.random() < 0.35) {
                items.add(ItemUtils.setAmount((ItemStack)EnchantmentOrb.getEnchantmentOrb((int)(75 + new Random().nextInt(21)), (int)11, (OrbType)OrbType.WEAPON), (int)(1 + new Random().nextInt(2))));
            } else {
                items.add(ItemUtils.setAmount((ItemStack)EnchantmentOrb.getEnchantmentOrb((int)(75 + new Random().nextInt(21)), (int)11, (OrbType)OrbType.ARMOR), (int)(1 + new Random().nextInt(2))));
            }
            ArrayList<GlobalKits> heroicKits2 = new ArrayList<GlobalKits>();
            for (GlobalKits gkit : GlobalKits.values()) {
                if (!gkit.getItemName().contains("Heroic")) continue;
                heroicKits2.add(gkit);
            }
            items.add(ItemUtils.setAmount((ItemStack)FallenHero.getFallenHeroItem((GlobalKits)((GlobalKits)heroicKits2.get(new Random().nextInt(heroicKits2.size())))), (int)(1 + new Random().nextInt(3))));
            items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getWhitescroll(), (int)(4 + new Random().nextInt(2))));
            items.add(CosmicConquestAPI.createFactionToken((int)(10 + new Random().nextInt(16))));
        }
        if (v == Version.SCHOOL_2017) {
            items.add(ItemUtils.buildItem((Material)Material.FIRE, (short)8, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)("&7" + (p != null ? p.getName() + "'s" : "Your") + "... &c&l&nMIXTAPE")), (String[])new String[]{(Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "CAUTION: " + (Object)ChatColor.GRAY + "Material is \"fire af\"."}));
            items.add(ItemUtils.setAmount((ItemStack)EssentialsAddHomes.getAddHomeItem(), (int)(1 + new Random().nextInt(3))));
            items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getItemNametag(), (int)(3 + new Random().nextInt(2))));
            x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher((String)"#BackToJail"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher((String)"DoMyHomework"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher((String)"IsItSummerYet?"));
            }
            if (x == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher((String)"IDC"));
                } else {
                    items.add(ItemUtils.getTitleVoucher((String)"MyFriendsPlayMinecraft"));
                }
            }
            items.add(ItemID.removeItemID((ItemStack)ItemUtils.setAmount((ItemStack)CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.LEGENDARY), (int)(2 + new Random().nextInt(2)))));
            items.add(ItemUtils.setAmount((ItemStack)FallenHero.getFallenHeroItem((GlobalKits)GlobalKits.values()[new Random().nextInt(GlobalKits.values().length)]), (int)(2 + new Random().nextInt(2))));
            items.add(EvolutionKitItems.getKitItem((EvolutionKits)(Math.random() < 0.3 ? EvolutionKits.ALCHEMIST : (Math.random() < 0.5 ? EvolutionKits.PHOENIX : EvolutionKits.TROLL))));
            items.add(CCAPI.getMysteryCrate());
            items.add(CosmicRankQuestsAPI.getRankQuestItem((int)(1 + new Random().nextInt(2))));
        }
        if (v == Version.SEPTEMBER_2017) {
            items.add(ItemUtils.buildItem((Material)Material.RECORD_11, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&8&lInter&f&ngalactic&r &9&lFidget Sp&ki&9&lnner"), (String[])new String[]{(Object)ChatColor.GRAY + "Confusingly addictive... " + (Object)ChatColor.ITALIC + "*SPIN* *SPIN* *SPIN*"}));
            items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getTransmogScroll(), (int)4));
            if (new Random().nextBoolean()) {
                items.add(ItemUtils.setAmount((ItemStack)EssentialsAddHomes.getAddHomeItem(), (int)(1 + new Random().nextInt(2))));
            } else {
                items.add(ItemUtils.setAmount((ItemStack)ShowcaseExpander.getShowcaseExpanderItem(), (int)(1 + new Random().nextInt(2))));
            }
            x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher((String)"#OverIt"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher((String)"SchoolIsEz"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher((String)"#Suspended"));
            }
            if (x == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher((String)"IOnlyCheatIRL"));
                } else {
                    items.add(ItemUtils.getTitleVoucher((String)"#Expelled"));
                }
            }
            items.add(ItemID.removeItemID((ItemStack)ItemUtils.setAmount((ItemStack)CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)(Math.random() < 0.2 ? ItemRarity.GOD : ItemRarity.LEGENDARY)), (int)2)));
            if (new Random().nextBoolean()) {
                items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getWhitescroll(), (int)(2 + new Random().nextInt(3))));
            } else {
                items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getBlackscroll((int)100), (int)(2 + new Random().nextInt(3))));
            }
            items.add(CosmicFactionBoosters.get().getBoosterManager().createBooster(BoosterType.EXP, 900, 5.0));
            items.add(CosmicConquestAPI.createFactionToken((int)(10 + new Random().nextInt(16))));
            if (Math.random() < 0.35) {
                items.add(ItemUtils.setAmount((ItemStack)EnchantmentOrb.getEnchantmentOrb((int)(50 + new Random().nextInt(26)), (int)12, (OrbType)OrbType.WEAPON), (int)1));
            } else {
                items.add(ItemUtils.setAmount((ItemStack)EnchantmentOrb.getEnchantmentOrb((int)(50 + new Random().nextInt(26)), (int)12, (OrbType)OrbType.ARMOR), (int)1));
            }
        }
        if (v == Version.OCTOBER_2017) {
            items.add(ItemUtils.buildItem((Material)Material.EYE_OF_ENDER, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&d&l&oCreepy &c&lEye"), (String[])new String[]{(Object)ChatColor.GRAY + "You can't shake this feeling... ", (Object)ChatColor.GRAY + "that something is " + (Object)ChatColor.UNDERLINE + "watching" + (Object)ChatColor.GRAY + "you."}));
            items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getItemNametag(), (int)(1 + new Random().nextInt(5))));
            items.add(ItemUtils.setAmount((ItemStack)EssentialsAddHomes.getAddHomeItem(), (int)(1 + new Random().nextInt(2))));
            x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher((String)"#Spoopy"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher((String)"Clowns"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher((String)"PumpkinManiac"));
            }
            if (x == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher((String)"Depression"));
                } else {
                    items.add(ItemUtils.getTitleVoucher((String)"2Spooky4Me"));
                }
            }
            items.add(ItemID.removeItemID((ItemStack)ItemUtils.setAmount((ItemStack)CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.LEGENDARY), (int)(1 + new Random().nextInt(3)))));
            items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getBlackscroll((int)(90 + new Random().nextInt(11))), (int)(3 + new Random().nextInt(3))));
            items.add(ItemUtils.setAmount((ItemStack)FallenHero.getFallenHeroItem((EvolutionKit)EvolutionKits.SLAYER.getKit()), (int)(1 + new Random().nextInt(2))));
            int rankId = 1;
            if (Math.random() < 0.6) {
                rankId = 2;
                if (Math.random() < 0.25) {
                    rankId = 3;
                }
                if (Math.random() < 0.1) {
                    rankId = 4;
                }
            }
            items.add(CosmicRankQuestsAPI.getRankQuestItem((int)rankId));
            items.add(ItemUtils.getMCMMOVoucher((int)CCAPI.rand(50, 100)));
        }
        if (v == Version.HALLOWEEN_2017) {
            items.add(ItemUtils.buildItem((Material)Material.GLASS_BOTTLE, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&f&lBottle O' &c&lS&4&lp&c&lo&4&lo&c&lk&4&ly"), (String[])new String[]{(Object)ChatColor.GRAY + "Highly dense and compounded spooky", (Object)ChatColor.GRAY + "sealed within a glass bottle."}));
            items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getTransmogScroll(), (int)(4 + new Random().nextInt(2))));
            items.add(ItemUtils.setAmount((ItemStack)ShowcaseExpander.getShowcaseExpanderItem(), (int)(2 + new Random().nextInt(2))));
            x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher((String)"#TrickAndTreat"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher((String)"Monster"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher((String)"CandyAddict"));
            }
            if (x == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher((String)"SerialKiller"));
                } else {
                    items.add(ItemUtils.getTitleVoucher((String)"ILikeSpookyMemes"));
                }
            }
            items.add(ItemID.removeItemID((ItemStack)ItemUtils.setAmount((ItemStack)CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)(Math.random() < 0.2 ? ItemRarity.GOD : ItemRarity.LEGENDARY)), (int)2)));
            if (Math.random() < 0.35) {
                items.add(ItemUtils.setAmount((ItemStack)EnchantmentOrb.getEnchantmentOrb((int)66, (int)12, (OrbType)OrbType.WEAPON), (int)1));
            } else {
                items.add(ItemUtils.setAmount((ItemStack)EnchantmentOrb.getEnchantmentOrb((int)66, (int)12, (OrbType)OrbType.ARMOR), (int)1));
            }
            ArrayList<GlobalKits> heroicKits3 = new ArrayList<GlobalKits>();
            for (GlobalKits gkit : GlobalKits.values()) {
                if (!gkit.getItemName().contains("Heroic")) continue;
                heroicKits3.add(gkit);
            }
            items.add(ItemUtils.setAmount((ItemStack)FallenHero.getFallenHeroItem((GlobalKits)((GlobalKits)heroicKits3.get(new Random().nextInt(heroicKits3.size())))), (int)(2 + new Random().nextInt(2))));
            items.add(CCAPI.getMysteryCrate());
            items.add(ItemID.removeItemID((ItemStack)CosmicArmorSetsAPI.getArmorSet((ArmorSet)ArmorSet.PHANTOM)[new Random().nextInt(4)]));
        }
        if (v == Version.NOVEMBER_2017) {
            items.add(ItemUtils.buildItem((Material)Material.QUARTZ_ORE, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&f&lSeasonal &8&lD&7&li&8&ls&7&lc&8&lo&7&lm&8&lb&7&lo&8&lb&7&lu&8&ll&7&la&8&lt&7&lo&8&lr "), (String[])new String[]{(Object)ChatColor.GRAY + "A cosmonaut piece of tinkering genius", (Object)ChatColor.GRAY + "used to control planetary seasons."}));
            items.add(ItemUtils.setAmount((ItemStack)EssentialsAddHomes.getAddHomeItem(), (int)(2 + new Random().nextInt(2))));
            items.add(ItemUtils.setAmount((ItemStack)LoreTag.getLoreTagItem(), (int)(1 + new Random().nextInt(2))));
            x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher((String)"#WinterIsHere"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher((String)"Chilly"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher((String)"#Numb"));
            }
            if (x == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher((String)"TheTimesTheyAreAChanging"));
                } else {
                    items.add(ItemUtils.getTitleVoucher((String)"HotChocolateNChill"));
                }
            }
            items.add(ItemID.removeItemID((ItemStack)ItemUtils.setAmount((ItemStack)CosmicSpaceChestsAPI.getSpaceFlare((ItemRarity)ItemRarity.GOD), (int)(1 + new Random().nextInt(2)))));
            if (Math.random() < 0.35) {
                items.add(ItemUtils.setAmount((ItemStack)EnchantmentOrb.getEnchantmentOrb((int)(75 + new Random().nextInt(6)), (int)12, (OrbType)OrbType.WEAPON), (int)1));
            } else {
                items.add(ItemUtils.setAmount((ItemStack)EnchantmentOrb.getEnchantmentOrb((int)(75 + new Random().nextInt(6)), (int)12, (OrbType)OrbType.ARMOR), (int)1));
            }
            int rankId = 1;
            if (Math.random() < 0.6) {
                rankId = 2;
                if (Math.random() < 0.25) {
                    rankId = 3;
                }
                if (Math.random() < 0.1) {
                    rankId = 4;
                }
            }
            items.add(CosmicRankQuestsAPI.getRankQuestItem((int)rankId));
            items.add(ItemID.removeItemID((ItemStack)ItemUtils.setAmount((ItemStack)CCAPI.getMysteryCrate(), (int)(1 + (Math.random() < 0.25 ? 1 : 0)))));
            items.add(CosmicConquestAPI.createFactionToken((int)(15 + new Random().nextInt(6))));
        }
        if (v == Version.THANKSGIVING_2017) {
            items.add(ItemUtils.buildItem((Material)Material.COOKED_CHICKEN, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&f&lTurkey of &a&l&nDiplomacy"), (String[])new String[]{(Object)ChatColor.GRAY + "Who knew a dead bird could do so", (Object)ChatColor.GRAY + "much for intergalactic relations?"}));
            items.add(ItemUtils.setAmount((ItemStack)ItemNametag.getNametagItem(), (int)5));
            items.add(ItemUtils.setAmount((ItemStack)LoreTag.getLoreTagItem(), (int)5));
            x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher((String)"BlackFriday2017"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher((String)"#Capitalism"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher((String)"NotAPuppet"));
            }
            if (x == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher((String)"VIP"));
                } else {
                    items.add(ItemUtils.getTitleVoucher((String)"#IPayToLose"));
                }
            }
            items.add(ItemID.removeItemID((ItemStack)ItemUtils.setAmount((ItemStack)CosmicSpaceChestsAPI.getSpaceFlare((ItemRarity)(Math.random() < 0.33 ? ItemRarity.GOD : ItemRarity.LEGENDARY)), (int)(3 + new Random().nextInt(2)))));
            if (Math.random() < 0.35) {
                items.add(ItemUtils.setAmount((ItemStack)EnchantmentOrb.getEnchantmentOrb((int)(80 + new Random().nextInt(11)), (int)12, (OrbType)OrbType.WEAPON), (int)1));
            } else {
                items.add(ItemUtils.setAmount((ItemStack)EnchantmentOrb.getEnchantmentOrb((int)(80 + new Random().nextInt(11)), (int)12, (OrbType)OrbType.ARMOR), (int)1));
            }
            ItemStack bossEgg = null;
            double i = Math.random();
            bossEgg = i <= 0.02 ? ItemUtils.getNewBossEgg((BossType)BossType.DEMONIC_WITHER) : (i <= 0.08 ? ItemUtils.getNewBossEgg((BossType)BossType.PLAGUE_BLOATER) : (i < 0.4 ? ItemUtils.getNewBossEgg((BossType)BossType.KING_SLIME) : ItemUtils.getNewBossEgg((BossType)BossType.BROOD_MOTHER)));
            bossEgg = ItemID.removeItemID((ItemStack)ItemUtils.setAmount((ItemStack)bossEgg, (int)(1 + new Random().nextInt(2))));
            items.add(bossEgg);
            if (Math.random() < 0.75) {
                items.add(KitItems.getKitItem((GlobalKits)GlobalKits.values()[new Random().nextInt(GlobalKits.values().length)]));
            } else {
                items.add(EvolutionKitItems.getKitItem((EvolutionKits)(Math.random() < 0.3 ? EvolutionKits.ALCHEMIST : (Math.random() < 0.5 ? EvolutionKits.PHOENIX : (Math.random() < 0.5 ? EvolutionKits.TROLL : EvolutionKits.SLAYER)))));
            }
            items.add(ItemID.removeItemID((ItemStack)ItemUtils.setAmount((ItemStack)CCAPI.getHeroicMysteryCrate(), (int)1)));
        }
        if (v == Version.DECEMBER_2017) {
            items.add(ItemUtils.buildItem((Material)Material.SNOW_BLOCK, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&f&lSpace   &b&l&nSnowman"), (String[])new String[]{(Object)ChatColor.GRAY + "Just your usual snowman made", (Object)ChatColor.GRAY + "out of... SPACE SNOW! Happy Holidays!"}));
            items.add(ItemUtils.setAmount((ItemStack)ShowcaseExpander.getShowcaseExpanderItem(), (int)(3 + new Random().nextInt(2))));
            ItemRarity ir = ItemRarity.ULTIMATE;
            if (Math.random() < 0.3) {
                ir = Math.random() < 0.15 ? ItemRarity.GOD : ItemRarity.LEGENDARY;
            }
            items.add(ItemUtils.setAmount((ItemStack)RandomizationScroll.getRandomizationScroll((ItemRarity)ir), (int)(2 + new Random().nextInt(2))));
            int x2 = new Random().nextInt(4);
            if (x2 == 0) {
                items.add(ItemUtils.getTitleVoucher((String)"BabyItsColdOffline"));
            }
            if (x2 == 1) {
                items.add(ItemUtils.getTitleVoucher((String)"#ChristmasIsComing"));
            }
            if (x2 == 2) {
                items.add(ItemUtils.getTitleVoucher((String)"iHateHolidays"));
            }
            if (x2 == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher((String)"Grinch"));
                } else {
                    items.add(ItemUtils.getTitleVoucher((String)"DeckTheWalls"));
                }
            }
            if (Math.random() < 0.3) {
                items.add(ItemID.removeItemID((ItemStack)ItemUtils.setAmount((ItemStack)CCAPI.getHeroicMysteryCrate(), (int)1)));
            } else {
                items.add(ItemID.removeItemID((ItemStack)ItemUtils.setAmount((ItemStack)CCAPI.getMysteryCrate(), (int)1)));
            }
            if (Math.random() < 0.1) {
                if (Math.random() < 0.05) {
                    items.add(DungeonsAPI.get().getDimensionWeb());
                } else {
                    items.add(DungeonsAPI.get().getCosmonautFuel());
                }
            } else {
                items.add(DungeonsAPI.get().getEnchantedObsidian());
            }
            if (Math.random() < 0.3) {
                items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getWhitescroll(), (int)(1 + new Random().nextInt(3))));
            } else {
                items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getBlackscroll((int)100), (int)(1 + new Random().nextInt(3))));
            }
            items.add(ItemUtils.getMoneyNote((String)"December 2017 Cosmic Crate", (int)(2000000 + new Random().nextInt(18000001))));
            items.add(CCAPI.getCosmicCrate(Version.DECEMBER, "December 2017 Cosmic Crate"));
        }
        if (v == Version.HOLIDAY_2017) {
            items.add(ItemUtils.buildItem((Material)Material.BOOK, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&c&l&n2018&r &f&lPatch Notes"), (String[])new String[]{(Object)ChatColor.GRAY + "A list of all the updates and", (Object)ChatColor.GRAY + "changes planned for the anticipated", (Object)ChatColor.GRAY + "2018 IRL gameplay update."}));
            items.add(ItemUtils.setAmount((ItemStack)EssentialsAddHomes.getAddHomeItem(), (int)5));
            items.add(ItemUtils.setAmount((ItemStack)RandomizationScroll.getRandomizationScroll((ItemRarity)ItemRarity.GOD), (int)(1 + new Random().nextInt(4))));
            int x3 = new Random().nextInt(4);
            if (x3 == 0) {
                items.add(ItemUtils.getTitleVoucher((String)"OP"));
            }
            if (x3 == 1) {
                items.add(ItemUtils.getTitleVoucher((String)"iHack"));
            }
            if (x3 == 2) {
                items.add(ItemUtils.getTitleVoucher((String)"Blacklisted"));
            }
            if (x3 == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher((String)"Glitched"));
                } else {
                    items.add(ItemUtils.getTitleVoucher((String)"RIP2k17"));
                }
            }
            items.add(ItemID.removeItemID((ItemStack)CosmicArmorSetsAPI.getArmorSet((ArmorSet)ArmorSet.YETI)[new Random().nextInt(4)]));
            items.add(ItemID.removeItemID((ItemStack)ItemUtils.setAmount((ItemStack)CCAPI.getHeroicMysteryCrate(), (int)(1 + new Random().nextInt(2)))));
            items.add(ItemID.removeItemID((ItemStack)ItemUtils.setAmount((ItemStack)CosmicSpaceChestsAPI.getSpaceFlare((ItemRarity)ItemRarity.GOD), (int)(1 + new Random().nextInt(5)))));
            items.add(ItemID.removeItemID((ItemStack)ItemUtils.setAmount((ItemStack)TieredEnchantments.getMysteryEnchantmentBook((int)7), (int)(1 + new Random().nextInt(2)))));
            int rankId = 2;
            if (Math.random() < 0.6) {
                rankId = 3;
                if (Math.random() < 0.25) {
                    rankId = 4;
                }
                if (Math.random() < 0.1) {
                    rankId = 5;
                }
            }
            items.add(CosmicRankQuestsAPI.getRankQuestItem((int)rankId));
        }
        if (v == Version.JANUARY_2018) {
            items.add(ItemUtils.setAmount((ItemStack)ItemUtils.buildItem((Material)Material.COOKIE, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&8&lOld &a&lC&c&lh&a&lr&c&li&a&ls&c&lt&a&lm&c&la&a&ls &6&l&nCookies"), (String[])new String[]{(Object)ChatColor.GRAY + "An ancient relic of Christmas-past,", (Object)ChatColor.GRAY + "do you dare consume such a treasure?"}), (int)2));
            items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getTransmogScroll(), (int)(2 + new Random().nextInt(4))));
            items.add(ItemUtils.setAmount((ItemStack)LoreTag.getLoreTagItem(), (int)(2 + new Random().nextInt(4))));
            int x4 = new Random().nextInt(4);
            if (x4 == 0) {
                items.add(ItemUtils.getTitleVoucher((String)"2k18"));
            }
            if (x4 == 1) {
                items.add(ItemUtils.getTitleVoucher((String)"NewYearNewMe"));
            }
            if (x4 == 2) {
                items.add(ItemUtils.getTitleVoucher((String)"Still#Glitched"));
            }
            if (x4 == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher((String)"IHaveATimeMachine"));
                } else {
                    items.add(ItemUtils.getTitleVoucher((String)"ImStillIn2017"));
                }
            }
            items.add(ItemID.removeItemID((ItemStack)CosmicArmorSetsAPI.getArmorSet((ArmorSet)(Math.random() < 0.3 ? ArmorSet.YETI : ArmorSet.PHANTOM))[new Random().nextInt(4)]));
            items.add(ItemID.removeItemID((ItemStack)ItemUtils.setAmount((ItemStack)TieredEnchantments.getMysteryEnchantmentBook((int)7), (int)1)));
            if (Math.random() < 0.35) {
                items.add(ItemUtils.setAmount((ItemStack)EnchantmentOrb.getEnchantmentOrb((int)(75 + new Random().nextInt(26)), (int)12, (OrbType)OrbType.WEAPON), (int)(1 + new Random().nextInt(2))));
            } else {
                items.add(ItemUtils.setAmount((ItemStack)EnchantmentOrb.getEnchantmentOrb((int)(75 + new Random().nextInt(26)), (int)12, (OrbType)OrbType.ARMOR), (int)(1 + new Random().nextInt(2))));
            }
            items.add(ItemID.removeItemID((ItemStack)ItemUtils.setAmount((ItemStack)CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)(Math.random() < 0.2 ? ItemRarity.GOD : ItemRarity.LEGENDARY)), (int)(2 + new Random().nextInt(2)))));
            items.add(CCAPI.getCosmicCrate(Math.random() < 0.25 ? Version.JANUARY_2017 : Version.JANURARY, "January 2018 Cosmic Crate"));
        }
        if (v == Version.FEBUARY_2018) {
            items.add(ItemUtils.setAmount((ItemStack)ItemUtils.buildItem((Material)Material.FIRE, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&b&lIcey &c&lFlame"), (String[])new String[]{(Object)ChatColor.GRAY + "Is it hot? Is it cold?", (Object)ChatColor.GRAY + "Touch it and find out!"}), (int)1));
            if (Math.random() < 0.4) {
                items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getItemNametag(), (int)(1 + new Random().nextInt(2))));
            } else {
                items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getTransmogScroll(), (int)(1 + new Random().nextInt(2))));
            }
            if (Math.random() < 0.4) {
                items.add(ItemUtils.setAmount((ItemStack)EssentialsAddHomes.getAddHomeItem(), (int)(3 + new Random().nextInt(2))));
            } else {
                items.add(ItemUtils.setAmount((ItemStack)ShowcaseExpander.getShowcaseExpanderItem(), (int)(3 + new Random().nextInt(2))));
            }
            int x5 = new Random().nextInt(4);
            if (x5 == 0) {
                items.add(ItemUtils.getTitleVoucher((String)"LoneWolf"));
            }
            if (x5 == 1) {
                items.add(ItemUtils.getTitleVoucher((String)"PartyOf1"));
            }
            if (x5 == 2) {
                items.add(ItemUtils.getTitleVoucher((String)"#69"));
            }
            if (x5 == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher((String)"AbuseMe"));
                } else {
                    items.add(ItemUtils.getTitleVoucher((String)"DupeMeDaddy"));
                }
            }
            int rankId = 3;
            if (Math.random() < 0.6) {
                rankId = 4;
                if (Math.random() < 0.1) {
                    rankId = 5;
                }
            }
            items.add(CosmicRankQuestsAPI.getRankQuestItem((int)rankId));
            items.add(ItemID.removeItemID((ItemStack)CosmicArmorSetsAPI.getArmorSet((ArmorSet)(Math.random() < 0.1 ? ArmorSet.MOTHER_OF_YIJKI : ArmorSet.PHANTOM))[new Random().nextInt(4)]));
            if (Math.random() < 0.6) {
                items.add(KitItems.getKitItem((GlobalKits)GlobalKits.values()[new Random().nextInt(GlobalKits.values().length)]));
            } else {
                items.add(EvolutionKitItems.getKitItem((EvolutionKits)(Math.random() < 0.3 ? EvolutionKits.ALCHEMIST : (Math.random() < 0.5 ? EvolutionKits.PHOENIX : (Math.random() < 0.5 ? EvolutionKits.TROLL : EvolutionKits.SLAYER)))));
            }
            items.add(ItemUtils.setAmount((ItemStack)MysteryMobSpawners.getMysteryMobSpawner(), (int)(1 + new Random().nextInt(5))));
            if (Math.random() < 0.2) {
                items.add(CCAPI.getHeroicMysteryCrate());
            } else {
                items.add(CCAPI.getCosmicCrate(Version.FEBRUARY, "Febuary 2018 Cosmic Crate"));
            }
        }
        if (v.getCrateData() != null) {
            items = v.getCrateData().getCrateLoot(p);
        }
        return items;
    }

    private static int rand(int low, int high) {
        return new Random().nextInt(high - low) + low;
    }
}

