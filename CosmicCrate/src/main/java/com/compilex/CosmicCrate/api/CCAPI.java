// 
// Decompiled by Procyon v0.5.36
// 

package com.compilex.CosmicCrate.api;

import com.rit.sucy.CustomEnchantment;
import com.compilex.CosmicDungeons.DungeonsAPI;
import org.arkhamnetwork.Arkkit.patches.ItemNametag;
import org.arkhamnetwork.Arkkit.patches.LoreTag;
import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import org.arkhamnetwork.Arkkit.patches.EssentialsAddHomes;
import org.arkhamnetwork.Arkkit.patches.ShowcaseExpander;
import me.ifamasssxd.cosmicconquest.CosmicConquestAPI;
import org.arkhamnetwork.enchantments.RandomizationScroll;
import me.ifamasssxd.cosmicbosses.struct.enums.BossType;
import org.arkhamnetwork.Arkkit.patches.Easter;
import com.cosmicpvp.CosmicSpaceChests.libs.EvolutionKitItems;
import me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKits;
import org.arkhamnetwork.enchantments.EnchantmentOrb;
import org.arkhamnetwork.enchantments.libs.OrbType;
import me.ifamasssxd.cosmicfactionboosters.enums.BoosterType;
import me.ifamasssxd.cosmicfactionboosters.CosmicFactionBoosters;
import com.cosmicpvp.CosmicRankQuests.CosmicRankQuestsAPI;
import me.cosmicpvp.CosmicGlobalKits.libs.FallenHero;
import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI;
import com.cosmicpvp.CosmicSpaceChests.libs.KitItems;
import me.cosmicpvp.CosmicGlobalKits.libs.GlobalKits;
import java.util.concurrent.ThreadLocalRandom;
import com.compilex.CosmicCustomCreepers.enums.CreeperTypes;
import org.arkhamnetwork.Arkkit.patches.MysteryMobSpawners;
import org.arkhamnetwork.Arkkit.patches.Drinks;
import me.ifamasssxd.killtracker.KillTrackerAPI;
import org.arkhamnetwork.CosmicCustomTNT.enums.TNTTypes;
import org.arkhamnetwork.Arkkit.patches.NewYears;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import org.arkhamnetwork.enchantments.util.TieredEnchantments;
import java.util.Random;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.Color;
import org.bukkit.Location;
import com.compilex.CosmicCrate.libs.RandomFireworks;
import org.bukkit.Sound;
import java.util.ArrayList;
import com.compilex.CosmicCrate.CosmicCrate;
import com.compilex.CosmicCrate.utils.LocalItemUtils;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.entity.Player;
import java.util.List;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import com.compilex.CosmicCrate.libs.Version;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CCAPI
{
    public static ItemStack getMysteryCrate() {
        final ItemStack i = ItemUtils.buildItem(Material.ENDER_CHEST, (short)0, ChatColor.YELLOW + "+" + ChatColor.GOLD + ChatColor.BOLD + "?" + ChatColor.YELLOW + "-" + ChatColor.RESET + ChatColor.GOLD + " " + ChatColor.BOLD + "Mystery Cosmic Crate " + ChatColor.YELLOW + "-" + ChatColor.GOLD + ChatColor.BOLD + "?" + ChatColor.YELLOW + "+", new String[] { "", ChatColor.GRAY + "" + ChatColor.UNDERLINE + "Right-click" + ChatColor.GRAY + " (in your hand) to recieve", ChatColor.GRAY + "a random /cc from this list!", "", Version.JANURARY.getRawName(), Version.FEBRUARY.getRawName(), Version.MARCH.getRawName(), Version.APRIL.getRawName(), Version.JULY.getRawName(), Version.SCHOOL.getRawName(), Version.OCTOBER.getRawName(), Version.HALLOWEEN.getRawName(), Version.NOVEMBER.getRawName(), Version.BLACK_FRIDAY.getRawName(), Version.DECEMBER_2016.getRawName(), Version.HOLIDAY.getRawName(), "", ChatColor.GRAY + "" + ChatColor.ITALIC + "A mysterious chest that needs", ChatColor.GRAY + "" + ChatColor.ITALIC + "to be inspected to determine", ChatColor.GRAY + "" + ChatColor.ITALIC + "its exact contents." });
        if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning()) {
            ItemID.setItemID(i, ItemID.generateNewItemID());
        }
        return i;
    }
    
    public static ItemStack getHeroicMysteryCrate() {
        final ItemStack i = ItemUtils.buildItem(Material.ENDER_CHEST, (short)0, ChatColor.RED + "+" + ChatColor.DARK_RED + ChatColor.BOLD + "?" + ChatColor.RED + "-" + ChatColor.RESET + ChatColor.DARK_RED + " " + ChatColor.BOLD + "Heroic Mystery Cosmic Crate " + ChatColor.RED + "-" + ChatColor.DARK_RED + ChatColor.BOLD + "?" + ChatColor.RED + "+", new String[] { "", ChatColor.GRAY + "" + ChatColor.UNDERLINE + "Right-click" + ChatColor.GRAY + " (in your hand) to recieve", ChatColor.GRAY + "a random 2017 /cc from this list!", "", Version.JANUARY_2017.getRawName(), Version.VALENTINE.getRawName(), Version.MARCH_2017.getRawName(), Version.APRIL_2017.getRawName(), Version.JULY_2017.getRawName(), Version.SCHOOL_2017.getRawName(), Version.OCTOBER_2017.getRawName(), Version.HALLOWEEN_2017.getRawName(), Version.NOVEMBER_2017.getRawName(), Version.DECEMBER_2017.getRawName(), Version.HOLIDAY_2017.getRawName(), "", ChatColor.GRAY + "" + ChatColor.ITALIC + "A mysterious chest that needs", ChatColor.GRAY + "" + ChatColor.ITALIC + "to be inspected to determine", ChatColor.GRAY + "" + ChatColor.ITALIC + "its exact contents." });
        if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning()) {
            ItemID.setItemID(i, ItemID.generateNewItemID());
        }
        return i;
    }
    
    public static ItemStack getGodlyCosmicCrate() {
        final ItemStack i = ItemUtils.buildItem(Material.ENDER_CHEST, (short)0, ChatColor.RED + "+" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "?" + ChatColor.RED + "-" + ChatColor.RESET + ChatColor.DARK_PURPLE + " " + ChatColor.BOLD + "Superior Mystery Crate " + ChatColor.RED + "-" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "?" + ChatColor.RED + "+", new String[] { "", ChatColor.GRAY + "" + ChatColor.UNDERLINE + "Right-click" + ChatColor.GRAY + " (in your hand) to recieve", ChatColor.GRAY + "a random 2018 /cc from this list!", "", Version.JANUARY_2018.getRawName(), Version.FEBUARY_2018.getRawName(), Version.VALENTINE_2018.getRawName(), Version.MARCH_2018.getRawName(), Version.APRIL_2018.getRawName(), Version.MAY_2018.getRawName(), Version.JUNE_2018.getRawName(), Version.JULY_2018.getRawName(), Version.AUGUST_2018.getRawName(), Version.SEPTEMBER_2018.getRawName(), Version.OCTOBER_2018.getRawName(), Version.HALLOWEEN_2018.getRawName(), Version.NOVEMBER_2018.getRawName(), Version.THANKSGIVING_2018.getRawName(), Version.DECEMBER_2018.getRawName(), Version.HOLIDAY_2018.getRawName(), "", ChatColor.GRAY + "" + ChatColor.ITALIC + "An extremely mysterious crate that", ChatColor.GRAY + "" + ChatColor.ITALIC + "has a powerful aura emanating from it." });
        if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning()) {
            ItemID.setItemID(i, ItemID.generateNewItemID());
        }
        return i;
    }
    
    public static ItemStack getCosmicCrate(final Version v, final String username) {
        ItemStack cc = null;
        if (v == Version.DECEMBER) {
            final String bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem(Material.ENDER_CHEST, (short)0, ChatColor.translateAlternateColorCodes('&', v.crateName), new String[] { ChatColor.RED + "Unlocked by " + ChatColor.BOLD + username + ChatColor.RED + " at " + ChatColor.GREEN + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Santa's Little Helper", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "New Years Clappers", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Candycane Cake", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Custom Item Nametag", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Exclusive Titles", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Jolly Transmog Scroll", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ENCHANTMENT ITEMS", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Godly Enchantment Book (100% Success)", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Demonic Black Scroll (95% Book)", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "10X High-Tier Secret Dust", "", ChatColor.RED.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", ChatColor.RED + bulletpoint + ChatColor.RED + "Explosive Snowballs", ChatColor.RED + bulletpoint + ChatColor.RED + "Limited Edition Kits" });
        }
        if (v == Version.JANURARY) {
            final String bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem(Material.ENDER_CHEST, (short)0, ChatColor.translateAlternateColorCodes('&', v.crateName), new String[] { ChatColor.YELLOW + "Unlocked by " + ChatColor.BOLD + username + ChatColor.YELLOW + " at " + ChatColor.GOLD + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "New Years Party Mask", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Executive Storage Chest", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Explosive Cakes", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "New Years Fireworks", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Limited Edition Titles", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Player Souls (10-100x)", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Lucky TNT (8-16x)", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Holy White Scroll (100%)", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Premium Monster Spawners (2x)", "", ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", ChatColor.GREEN + bulletpoint + ChatColor.GREEN + "Random Soul Tracker", ChatColor.GREEN + bulletpoint + ChatColor.GREEN + "Soul Enchantment Book", ChatColor.GREEN + bulletpoint + ChatColor.GREEN + "Limited Edition Kits" });
        }
        if (v == Version.FEBRUARY) {
            final String bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem(Material.ENDER_CHEST, (short)0, ChatColor.translateAlternateColorCodes('&', v.crateName), new String[] { ChatColor.RED + "Unlocked by " + ChatColor.BOLD + username + ChatColor.RED + " at " + ChatColor.DARK_RED + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Valentine's Day Card", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Box of Chocolates", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Space Drinks", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Lovely Item Nametag", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Limited Edition Titles", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Mystery Mob Spawner", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "EXP Bottles", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "mcMMO Voucher", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Bank Note", "", ChatColor.DARK_RED.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", ChatColor.DARK_RED + bulletpoint + ChatColor.DARK_RED + "Nether Spawner", ChatColor.DARK_RED + bulletpoint + ChatColor.DARK_RED + "Explosive Snowballs", ChatColor.DARK_RED + bulletpoint + ChatColor.DARK_RED + "Limited Edition Kits" });
        }
        if (v == Version.MARCH) {
            final String bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem(Material.ENDER_CHEST, (short)0, ChatColor.translateAlternateColorCodes('&', v.crateName), new String[] { ChatColor.GREEN + "Unlocked by " + ChatColor.BOLD + username + ChatColor.GREEN + " at " + ChatColor.DARK_GREEN + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Pot of Gold", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Flower Bouquet", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Spring Item Nametag", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Seasonal Transmog Scroll", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Legendary Player Titles", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Mystery Mob Spawner", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Tactical Creeper (1-3x)", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Gigantic Creeper (2-5x)", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Demonic Black Scroll (100%)", "", ChatColor.DARK_GREEN.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", ChatColor.DARK_GREEN + bulletpoint + ChatColor.DARK_GREEN + "Nether Spawner", ChatColor.DARK_GREEN + bulletpoint + ChatColor.DARK_GREEN + "Lucky Creepers", ChatColor.DARK_GREEN + bulletpoint + ChatColor.DARK_GREEN + "Limited Edition Kits" });
        }
        if (v == Version.APRIL) {
            final String bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem(Material.ENDER_CHEST, (short)0, ChatColor.translateAlternateColorCodes('&', v.crateName), new String[] { ChatColor.YELLOW + "Unlocked by " + ChatColor.BOLD + username + ChatColor.YELLOW + " at " + ChatColor.LIGHT_PURPLE + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Troll Letter", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Colorful Umbrella", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "April Fools Nametag", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Timeless Player Titles", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Explosive Cakes", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Limited Edition Kit", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Legendary Space Chest", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Mystery Mob Spawner", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Perfect White Scroll", "", ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", ChatColor.LIGHT_PURPLE + bulletpoint + ChatColor.LIGHT_PURPLE + "Player Rank Vouchers", ChatColor.LIGHT_PURPLE + bulletpoint + ChatColor.LIGHT_PURPLE + "Cosmic Envoy Summon", ChatColor.LIGHT_PURPLE + bulletpoint + ChatColor.LIGHT_PURPLE + "Cosmic Boss Eggs" });
        }
        if (v == Version.JULY) {
            final String bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem(Material.ENDER_CHEST, (short)0, ChatColor.translateAlternateColorCodes('&', v.crateName), new String[] { ChatColor.WHITE + "Unlocked by " + ChatColor.BOLD + username + ChatColor.WHITE + " at " + ChatColor.BLUE + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Independence Flag", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Declaration of Freedom", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Transmog Scroll", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Exclusive Freedom Titles", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Space Fireworks", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Fallen Hero #1", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Fallen Hero #2", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Heroic Fallen Hero", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Legendary Space Flare", "", ChatColor.BLUE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", ChatColor.BLUE + bulletpoint + ChatColor.BLUE + "Legendary Boss Egg", ChatColor.BLUE + bulletpoint + ChatColor.BLUE + "Rank Quest", ChatColor.BLUE + bulletpoint + ChatColor.BLUE + "Cosmic Envoy Summon" });
        }
        if (v == Version.AUGUST) {
            final String bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem(Material.ENDER_CHEST, (short)0, ChatColor.translateAlternateColorCodes('&', v.crateName), new String[] { ChatColor.AQUA + "Unlocked by " + ChatColor.BOLD + username + ChatColor.AQUA + " at " + ChatColor.DARK_AQUA + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Mojang #EULA", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Freshly Squeezed Lemonade", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "EULA Violating Titles", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Item Nametag #1", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Item Nametag #2", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Rank Quest", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Heroic Fallen Hero", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Legendary Space Flare", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "100% Black Scroll (2x)", "", ChatColor.DARK_AQUA.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", ChatColor.DARK_AQUA + bulletpoint + ChatColor.DARK_AQUA + "Godly Space Flare", ChatColor.DARK_AQUA + bulletpoint + ChatColor.DARK_AQUA + "Legendary Boss Egg", ChatColor.DARK_AQUA + bulletpoint + ChatColor.DARK_AQUA + "Soul Enchantment Book" });
        }
        if (v == Version.SCHOOL) {
            final String bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem(Material.ENDER_CHEST, (short)0, ChatColor.translateAlternateColorCodes('&', v.crateName), new String[] { ChatColor.GOLD + "Unlocked by " + ChatColor.BOLD + username + ChatColor.GOLD + " at " + ChatColor.WHITE + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Cheat Sheet", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Meme Backpack", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Limited Edition Titles", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Back To School Nametag", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Heroic Fallen Hero #1", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Heroic Fallen Hero #2", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "15X High-Tier Secret Dust", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "90% Black Scroll (3x)", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Perfect White Scroll (2x)", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Nether Spawner", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Godly Space Flare", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Soul Enchantment Book" });
        }
        if (v == Version.OCTOBER) {
            final String bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem(Material.ENDER_CHEST, (short)0, ChatColor.translateAlternateColorCodes('&', v.crateName), new String[] { ChatColor.RED + "Unlocked by " + ChatColor.BOLD + username + ChatColor.RED + " at " + ChatColor.DARK_GRAY + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Lucky Leaf", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Space Pumpkin", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Limited Edition Titles", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Spooky Transmog Scroll (2x)", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "2.0x10m Faction XP/mcMMO Booster", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Mystery Mob Spawner", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Fallen Hero", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Legendary Space Flare", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "100% Black Scroll (2x)", "", ChatColor.RED.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", ChatColor.RED + bulletpoint + ChatColor.RED + "4.0x30m Faction XP Booster", ChatColor.RED + bulletpoint + ChatColor.RED + "Nether Spawner", ChatColor.RED + bulletpoint + ChatColor.RED + "Godly Space Flare", ChatColor.RED + bulletpoint + ChatColor.RED + "Legendary Boss Egg" });
        }
        if (v == Version.HALLOWEEN) {
            final String bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem(Material.ENDER_CHEST, (short)0, ChatColor.translateAlternateColorCodes('&', v.crateName), new String[] { ChatColor.DARK_RED + "Unlocked by " + ChatColor.BOLD + username + ChatColor.DARK_RED + " at " + ChatColor.GOLD + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Bag of Candy", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Clown Mask", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Limited Edition Titles", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "2x Item Nametag", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "6.66x6m Faction XP/mcMMO Booster", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "4x Mystery Soul Dust", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "2x Mystery Mob Spawner", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1x Fallen Hero", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "66% Black Scroll (6x)", "", ChatColor.DARK_RED.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", ChatColor.DARK_RED + bulletpoint + ChatColor.DARK_RED + "6.66x66m Faction XP Booster", ChatColor.DARK_RED + bulletpoint + ChatColor.DARK_RED + "2x Soul Enchantment Book", ChatColor.DARK_RED + bulletpoint + ChatColor.DARK_RED + "Legendary Boss Egg", ChatColor.DARK_RED + bulletpoint + ChatColor.DARK_RED + "Rank Quest" });
        }
        if (v == Version.NOVEMBER) {
            final String bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem(Material.ENDER_CHEST, (short)0, ChatColor.translateAlternateColorCodes('&', v.crateName), new String[] { ChatColor.DARK_PURPLE + "Unlocked by " + ChatColor.BOLD + username + ChatColor.DARK_PURPLE + " at " + ChatColor.LIGHT_PURPLE + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Thanksgiving Horn", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Pilgram Hat", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Thanksgiving Titles", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "2-3x Transmog Scrolls", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "4.0x11m Faction XP/mcMMO Booster", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "2x Heroic Fallen Hero", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Legendary Space Flare", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "99% Black Scroll (3x)", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "15X High-Tier Secret Dust", "", ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", ChatColor.DARK_PURPLE + bulletpoint + ChatColor.DARK_PURPLE + "8.0x30m Faction XP Booster", ChatColor.DARK_PURPLE + bulletpoint + ChatColor.DARK_PURPLE + "Godly Space Flare", ChatColor.DARK_PURPLE + bulletpoint + ChatColor.DARK_PURPLE + "Legendary Boss Egg", ChatColor.DARK_PURPLE + bulletpoint + ChatColor.DARK_PURPLE + "Rank Quest" });
        }
        if (v == Version.BLACK_FRIDAY) {
            final String bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem(Material.ENDER_CHEST, (short)0, ChatColor.translateAlternateColorCodes('&', v.crateName), new String[] { ChatColor.DARK_GRAY + "Unlocked by " + ChatColor.BOLD + username + ChatColor.WHITE + " at " + ChatColor.DARK_GRAY + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "A Good Deal", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Fake 100%-Off Coupon", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Capitalism Titles", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "3x Item Nametags", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "70-95% Enchantment Orb", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "9.99x5m Faction XP/mcMMO Booster", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1-2x White Scroll", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "10X High-Tier Secret Dust", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Low Tier Rank Quest", "", ChatColor.DARK_GRAY.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", ChatColor.DARK_GRAY + bulletpoint + ChatColor.DARK_GRAY + "3x 100% Enchantment Orbs", ChatColor.DARK_GRAY + bulletpoint + ChatColor.DARK_GRAY + "Legendary Boss Egg", ChatColor.DARK_GRAY + bulletpoint + ChatColor.DARK_GRAY + "Godly Space Flare" });
        }
        if (v == Version.DECEMBER_2016) {
            final String bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem(Material.ENDER_CHEST, (short)0, ChatColor.translateAlternateColorCodes('&', v.crateName), new String[] { ChatColor.BLUE + "Unlocked by " + ChatColor.BOLD + username + ChatColor.BLUE + " at " + ChatColor.BLUE + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Winter Wonderland", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Cozy Space Mittens", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Icy Titles", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "2-3x Chilly Transmog Scrolls", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "2x 50-75% Armor Enchantment Orbs", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1x 30-60% Weapon Enchantment Orb", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "2x Fallen Hero", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1-2x White Scroll", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Legendary Space Flare", "", ChatColor.BLUE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", ChatColor.BLUE + bulletpoint + ChatColor.BLUE + "2x Soul Enchantment Books", ChatColor.BLUE + bulletpoint + ChatColor.BLUE + "High-Tier Rank Quest", ChatColor.BLUE + bulletpoint + ChatColor.BLUE + "Godly Space Flare" });
        }
        if (v == Version.HOLIDAY) {
            final String bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem(Material.ENDER_CHEST, (short)0, ChatColor.translateAlternateColorCodes('&', v.crateName), new String[] { ChatColor.GREEN + "Unlocked by " + ChatColor.BOLD + username + ChatColor.GREEN + " at " + ChatColor.DARK_RED + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Holiday Spirit", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Explosive Snowballs", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Jolly Titles", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "3x Item Nametags", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "2-3x 75-100% Armor Enchantment Orbs", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1-2x 75-100% Weapon Enchantment Orb", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "3x Heroic Fallen Hero", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Phoenix Evolution Kit", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Rank Quest", "", ChatColor.DARK_RED.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", ChatColor.DARK_RED + bulletpoint + ChatColor.DARK_RED + "Heroic Rank Crystal", ChatColor.DARK_RED + bulletpoint + ChatColor.DARK_RED + "High-Tier Rank Quest", ChatColor.DARK_RED + bulletpoint + ChatColor.DARK_RED + "Godly Space Flare" });
        }
        if (v == Version.JANUARY_2017) {
            final String bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem(Material.ENDER_CHEST, (short)0, ChatColor.translateAlternateColorCodes('&', v.crateName), new String[] { ChatColor.DARK_AQUA + "Unlocked by " + ChatColor.BOLD + username + ChatColor.DARK_AQUA + " at " + ChatColor.WHITE + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "New Years Resolution", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "2017 Trophy", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Limited Edition Titles", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "2-3x Transmog Scrolls", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Godly Space Flare", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Random /gkit Gem", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "5.0x10m Faction XP/mcMMO Booster", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "2-3x White Scroll", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "3-5x Black Scroll (90%)", "", ChatColor.DARK_AQUA.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", ChatColor.DARK_AQUA + bulletpoint + ChatColor.DARK_AQUA + "Legendary Boss Eggs (2x)", ChatColor.DARK_AQUA + bulletpoint + ChatColor.DARK_AQUA + "Phoenix Evolution Kit", ChatColor.DARK_AQUA + bulletpoint + ChatColor.DARK_AQUA + "Heroic Rank Crystal" });
        }
        if (v == Version.VALENTINE) {
            final String bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem(Material.ENDER_CHEST, (short)0, ChatColor.translateAlternateColorCodes('&', v.crateName), new String[] { ChatColor.DARK_RED + "Unlocked by " + ChatColor.BOLD + username + ChatColor.DARK_RED + " at " + ChatColor.RED + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Forever Alone Card", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Box of Memes", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Limited Edition Titles", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "1-3x Item Nametags", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Troll Evolution Kit", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "2x Fallen Hero", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1-4x White Scroll", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "2-4x Mystery Soul Dust", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1-2x 90-100% Weapon/Armor Enchantment Orbs", "", ChatColor.DARK_RED.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", ChatColor.DARK_RED + bulletpoint + ChatColor.DARK_RED + "Godly Space Flare", ChatColor.DARK_RED + bulletpoint + ChatColor.DARK_RED + "Rank Quest", ChatColor.DARK_RED + bulletpoint + ChatColor.DARK_RED + "Heroic Rank Crystal" });
        }
        if (v == Version.MARCH_2017) {
            final String bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem(Material.ENDER_CHEST, (short)0, ChatColor.translateAlternateColorCodes('&', v.crateName), new String[] { ChatColor.GREEN + "Unlocked by " + ChatColor.BOLD + username + ChatColor.GREEN + " at " + ChatColor.AQUA + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Spring Totem", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Luck of the Irish", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Limited Edition Titles", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "3x Transmog Scrolls", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1-3x Fallen Hero", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1-2x Heroic Fallen Hero", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Random Evolution Kit", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Rank Quest", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "3-4x 75-90% Weapon/Armor Enchantment Orbs", "", ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", ChatColor.GREEN + bulletpoint + ChatColor.GREEN + "4-5x Legendary Boss Eggs", ChatColor.GREEN + bulletpoint + ChatColor.GREEN + "High-Tier Rank Quest", ChatColor.GREEN + bulletpoint + ChatColor.GREEN + "Heroic Rank Crystal" });
        }
        if (v == Version.APRIL_2017) {
            final String bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem(Material.ENDER_CHEST, (short)0, ChatColor.translateAlternateColorCodes('&', v.crateName), new String[] { ChatColor.GOLD + "Unlocked by " + ChatColor.BOLD + username + ChatColor.GOLD + " at " + ChatColor.DARK_PURPLE + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Fool of April", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Easter Basket", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Limited Edition Titles", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "2-3x Item Nametags", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "10-15x Easter Eggs", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "5x10m Faction XP/mcMMO Booster", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1-2x /vkit or /gkit Fallen Hero", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Soul Reaper Boss Egg", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Godly Randomization Scroll", "", ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", ChatColor.DARK_PURPLE + bulletpoint + ChatColor.DARK_PURPLE + "Godly Space Flare", ChatColor.DARK_PURPLE + bulletpoint + ChatColor.DARK_PURPLE + "Alchemist /vkit Crystal", ChatColor.DARK_PURPLE + bulletpoint + ChatColor.DARK_PURPLE + "April 2016 Cosmic Crate" });
        }
        if (v == Version.MAY_2017) {
            final String bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem(Material.ENDER_CHEST, (short)0, ChatColor.translateAlternateColorCodes('&', v.crateName), new String[] { ChatColor.GREEN + "Unlocked by " + ChatColor.BOLD + username + ChatColor.GREEN + " at " + ChatColor.DARK_GREEN + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Spring... is coming.", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Vitamin D", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Limited Edition Titles", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "2-4x Transmog Scrolls", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "2x Random Weapon/Armor Orb", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1-2x /vkit Fallen Hero", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1-2x /gkit Fallen Hero", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "2-3x Randomization Scrolls", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "5-15x Faction Crystals", "", ChatColor.DARK_GREEN.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", ChatColor.DARK_GREEN + bulletpoint + ChatColor.DARK_GREEN + "Godly Space Flare", ChatColor.DARK_GREEN + bulletpoint + ChatColor.DARK_GREEN + "Random /gkit Crystal", ChatColor.DARK_GREEN + bulletpoint + ChatColor.DARK_GREEN + "50-100x Faction Crystals" });
        }
        if (v == Version.JUNE_2017) {
            final String bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem(Material.ENDER_CHEST, (short)0, ChatColor.translateAlternateColorCodes('&', v.crateName), new String[] { ChatColor.RED + "Unlocked by " + ChatColor.BOLD + username + ChatColor.RED + " at " + ChatColor.LIGHT_PURPLE + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Detention Slip", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Limited Edition Titles", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "2-3x Item Nametags", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1-5x 80-95% Random Weapon/Armor Orb", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1-3x Heroic /gkit Fallen Hero", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "3-8x White Scrolls", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "5-10x Black Scrolls (95%)", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Random /gkit Crystal", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Rank Quest", "", ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", ChatColor.LIGHT_PURPLE + bulletpoint + ChatColor.LIGHT_PURPLE + "Heroic Rank Crystal", ChatColor.LIGHT_PURPLE + bulletpoint + ChatColor.LIGHT_PURPLE + "1-4x Legendary Boss Eggs", ChatColor.LIGHT_PURPLE + bulletpoint + ChatColor.LIGHT_PURPLE + "50-100x Faction Crystals" });
        }
        if (v == Version.JULY_2017) {
            final String bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem(Material.ENDER_CHEST, (short)0, ChatColor.translateAlternateColorCodes('&', v.crateName), new String[] { ChatColor.WHITE + "Unlocked by " + ChatColor.BOLD + username + ChatColor.WHITE + " at " + ChatColor.AQUA + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Illegal Fireworks", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Limited Edition Titles", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "3x Transmog Scrolls " + ChatColor.UNDERLINE.toString() + "OR" + ChatColor.YELLOW + " Item Nametags", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Rank Quest", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1-2x Godly Space Flare", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1-3x /vkit Fallen Hero", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "3-4x Godly Randomization Scrolls", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "7.4x10m Faction XP/mcMMO Booster", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1-2x 25% Random Weapon/Armor Orb (11 slot)", "", ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", ChatColor.AQUA + bulletpoint + ChatColor.AQUA + "Ogre /vkit Crystal", ChatColor.AQUA + bulletpoint + ChatColor.AQUA + "Heroic Rank Crystal", ChatColor.AQUA + bulletpoint + ChatColor.AQUA + "July 2016 Cosmic Crate" });
        }
        if (v == Version.AUGUST_2017) {
            final String bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem(Material.ENDER_CHEST, (short)0, ChatColor.translateAlternateColorCodes('&', v.crateName), new String[] { ChatColor.WHITE + "Unlocked by " + ChatColor.BOLD + username + ChatColor.WHITE + " at " + ChatColor.BLUE + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Summer Vibes", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Limited Edition Titles", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "1-2x Max Home Increase", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "1-3x Showcase Expansion", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1-3x Heroic /gkit Fallen Hero", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "2-4 Legendary Space Flare", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "10-25x Faction Crystals", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "4-5x White Scrolls", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1x 75-90% Weapon/Armor Orb (11 slot)", "", ChatColor.BLUE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", ChatColor.BLUE + bulletpoint + ChatColor.BLUE + "Heroic Rank Crystal", ChatColor.BLUE + bulletpoint + ChatColor.BLUE + "Mystery Cosmic Crate", ChatColor.BLUE + bulletpoint + ChatColor.BLUE + "August 2016 Cosmic Crate" });
        }
        if (v == Version.SCHOOL_2017) {
            final String bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem(Material.ENDER_CHEST, (short)0, ChatColor.translateAlternateColorCodes('&', v.crateName), new String[] { ChatColor.GOLD + "Unlocked by " + ChatColor.BOLD + username + ChatColor.GOLD + " at " + ChatColor.GRAY + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Your Mixtape", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Limited Edition Titles", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "3-4x Item Nametags", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "1-3x Max Home Increase", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "2-3x /gkit Fallen Hero", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1-3x Legendary Space Chests", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Random /vkit Kit Gem", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Rank Quest", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Mystery Cosmic Crate", "", ChatColor.GRAY.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", ChatColor.GRAY + bulletpoint + ChatColor.GRAY + "2-3x Godly Space Chests", ChatColor.GRAY + bulletpoint + ChatColor.GRAY + "Heroic Rank Crystal", ChatColor.GRAY + bulletpoint + ChatColor.GRAY + "Back To School 2016 Cosmic Crate" });
        }
        if (v == Version.SEPTEMBER_2017) {
            final String bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem(Material.ENDER_CHEST, (short)0, ChatColor.translateAlternateColorCodes('&', v.crateName), new String[] { ChatColor.BLUE + "Unlocked by " + ChatColor.BOLD + username + ChatColor.BLUE + " at " + ChatColor.DARK_AQUA + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Intergalactic Fidget Spinner", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Limited Edition Titles", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "4x Transmog Scrolls", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "1-2x Max Home Increase " + ChatColor.UNDERLINE.toString() + "OR" + ChatColor.YELLOW + " Showcase Expanders", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "2x Legendary " + ChatColor.UNDERLINE.toString() + "OR" + ChatColor.GOLD + " Godly Space Chests", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "2-4x White " + ChatColor.UNDERLINE.toString() + "OR" + ChatColor.GOLD + " Black Scrolls (100%)", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "5x15m Faction XP Booster", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "10-25x Faction Crystals", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1x 50-75% Weapon/Armor Orb (" + ChatColor.BOLD + "12 slot" + ChatColor.GOLD + ")", "", ChatColor.BLUE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", ChatColor.BLUE + bulletpoint + ChatColor.BLUE + "Mystery Cosmic Crate", ChatColor.BLUE + bulletpoint + ChatColor.BLUE + "1-2x Heroic Boss Eggs", ChatColor.BLUE + bulletpoint + ChatColor.BLUE + "Heroic Rank Crystal" });
        }
        if (v == Version.OCTOBER_2017) {
            final String bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem(Material.ENDER_CHEST, (short)0, ChatColor.translateAlternateColorCodes('&', v.crateName), new String[] { ChatColor.GOLD + "Unlocked by " + ChatColor.BOLD + username + ChatColor.GOLD + " at " + ChatColor.DARK_GRAY + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Creepy Eye", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Limited Edition Titles", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "1-5x Item Nametags", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "1-2x Max Home Increase", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1-3x Legendary Space Chests", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "3-5x Black Scrolls (90-100%)", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1-2x Slayer /vkit Fallen Hero", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "50-100 Level mcMMO Voucher", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Rank Quest", "", ChatColor.DARK_GRAY.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", ChatColor.DARK_GRAY + bulletpoint + ChatColor.DARK_GRAY + "Soul Reaper Boss Egg", ChatColor.DARK_GRAY + bulletpoint + ChatColor.DARK_GRAY + "Heroic Rank Crystal", ChatColor.DARK_GRAY + bulletpoint + ChatColor.DARK_GRAY + "October 2016 Cosmic Crate" });
        }
        if (v == Version.HALLOWEEN_2017) {
            final String bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem(Material.ENDER_CHEST, (short)0, ChatColor.translateAlternateColorCodes('&', v.crateName), new String[] { ChatColor.DARK_RED + "Unlocked by " + ChatColor.BOLD + username + ChatColor.RED + " at " + ChatColor.DARK_RED + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Bottle O' Spooky", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Spooky Player Titles", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "4-5x Transmog Scrolls", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "2-3x Showcase Expanders", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "2x Legendary " + ChatColor.UNDERLINE.toString() + "OR" + ChatColor.GOLD + " Godly Space Chests", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "2-3x Heroic /gkit Fallen Hero", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1x 66% Random Weapon/Armor Orb (12 slot)", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Phantom Armor Set Piece", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Mystery Cosmic Crate", "", ChatColor.DARK_RED.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", ChatColor.DARK_RED + bulletpoint + ChatColor.DARK_RED + "1-2x Heroic Boss Eggs", ChatColor.DARK_RED + bulletpoint + ChatColor.DARK_RED + "Heroic Rank Crystal", ChatColor.DARK_RED + bulletpoint + ChatColor.DARK_RED + "Halloween 2016 Cosmic Crate" });
        }
        if (v == Version.NOVEMBER_2017) {
            final String bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem(Material.ENDER_CHEST, (short)0, ChatColor.translateAlternateColorCodes('&', v.crateName), new String[] { ChatColor.DARK_PURPLE + "Unlocked by " + ChatColor.BOLD + username + ChatColor.WHITE + " at " + ChatColor.DARK_PURPLE + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Seasonal Discombobulator", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Limited Player Titles", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "2-3x Max Home Increase", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "1-2x Lore Crystals", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1x 75-80% Weapon/Armor Orb (" + ChatColor.BOLD + "12 slot" + ChatColor.GOLD + ")", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1-2x Mystery Cosmic Crate", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1-2x Godly Space Flares", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "15-25x Faction Crystals", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Rank Quest", "", ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", ChatColor.DARK_PURPLE + bulletpoint + ChatColor.DARK_PURPLE + "November 2016 Cosmic Crate", ChatColor.DARK_PURPLE + bulletpoint + ChatColor.DARK_PURPLE + "2-3x Godly Space Chests", ChatColor.DARK_PURPLE + bulletpoint + ChatColor.DARK_PURPLE + "Heroic Rank Crystal" });
        }
        if (v == Version.THANKSGIVING_2017) {
            final String bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem(Material.ENDER_CHEST, (short)0, ChatColor.translateAlternateColorCodes('&', v.crateName), new String[] { ChatColor.GOLD + "Unlocked by " + ChatColor.BOLD + username + ChatColor.YELLOW + " at " + ChatColor.GOLD + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Turkey of Diplomacy", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Limited Edition Titles", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "5x Item Nametags", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "5x Lore Crystals", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "3-4x Legendary " + ChatColor.UNDERLINE.toString() + "OR" + ChatColor.GOLD + " Godly Space Flares", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1-2x 80-90% Weapon/Armor Orb (" + ChatColor.BOLD + "12 slot" + ChatColor.GOLD + ")", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1-2x Legendary Boss Eggs", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Random /gkit " + ChatColor.UNDERLINE.toString() + "OR" + ChatColor.GOLD + " /vkit Crystal", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Heroic Mystery Cosmic Crate", "", ChatColor.DARK_RED.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", ChatColor.DARK_RED + bulletpoint + ChatColor.DARK_RED + "High-tier Rank Quest", ChatColor.DARK_RED + bulletpoint + ChatColor.DARK_RED + "Godly Overload Upgrade Book", ChatColor.DARK_RED + bulletpoint + ChatColor.DARK_RED + "Heroic Rank Crystal" });
        }
        if (v == Version.DECEMBER_2017) {
            final String bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem(Material.ENDER_CHEST, (short)0, ChatColor.translateAlternateColorCodes('&', v.crateName), new String[] { ChatColor.BLUE + "Unlocked by " + ChatColor.BOLD + username + ChatColor.BLUE + " at " + ChatColor.DARK_BLUE + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Space Snowman", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Limited Edition Titles", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "2-3x Randomization Scrolls", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "3-4x Showcase Expanders", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1x Mystery " + ChatColor.UNDERLINE.toString() + "OR" + ChatColor.GOLD + " Heroic Cosmic Crate", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1-3x White " + ChatColor.UNDERLINE.toString() + "OR" + ChatColor.GOLD + " Black Scrolls (100%)", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Random /dungeon Portal Ingredient", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "$2-20m Cosmic Money Note", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "December 2015 Cosmic Crate", "", ChatColor.BLUE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", ChatColor.BLUE + bulletpoint + ChatColor.BLUE + "December 2016 Cosmic Crate", ChatColor.BLUE + bulletpoint + ChatColor.BLUE + "Abandoned Ship Dungeon Portal", ChatColor.BLUE + bulletpoint + ChatColor.BLUE + "Heroic Rank Crystal" });
        }
        if (v == Version.HOLIDAY_2017) {
            final String bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem(Material.ENDER_CHEST, (short)0, ChatColor.translateAlternateColorCodes('&', v.crateName), new String[] { ChatColor.GREEN + "Unlocked by " + ChatColor.BOLD + username + ChatColor.GREEN + " at " + ChatColor.DARK_RED + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "2018 Patch Notes", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Limited Edition Titles", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "5x Max Home Increase", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "1-4x Godly Randomization Scrolls", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Yeti Armor Set Piece", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "High-tier Rank Quest", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1-2x Heroic Cosmic Crates", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1-2x Heroic Enchantment Books", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1-5x Godly Space Flares", "", ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", ChatColor.GREEN + bulletpoint + ChatColor.GREEN + "Abandoned Spaceship Dungeon Lootbag", ChatColor.GREEN + bulletpoint + ChatColor.GREEN + "Holiday 2016 Cosmic Crate", ChatColor.GREEN + bulletpoint + ChatColor.GREEN + "Mother of Yijki Set Piece" });
        }
        if (v == Version.JANUARY_2018) {
            final String bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem(Material.ENDER_CHEST, (short)0, ChatColor.translateAlternateColorCodes('&', v.crateName), new String[] { ChatColor.AQUA + "Unlocked by " + ChatColor.BOLD + username + ChatColor.AQUA + " at " + ChatColor.DARK_AQUA + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Old Christmas Cookies", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Limited Edition Titles", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "2-5x Transmog Scrolls", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "2-5x Lore Crystals", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Random Heroic Enchantment Book", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Random Phantom " + ChatColor.UNDERLINE.toString() + "OR" + ChatColor.GOLD + " Yeti Armor Set Piece", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1-2x 75-100% Weapon/Armor Orb (" + ChatColor.BOLD + "12 slot" + ChatColor.GOLD + ")", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "2-3x Legendary " + ChatColor.UNDERLINE.toString() + "OR" + ChatColor.GOLD + " Godly Space Chests", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "January 2016 " + ChatColor.UNDERLINE.toString() + "OR" + ChatColor.GOLD + " 2017 Cosmic Crate", "", ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", ChatColor.AQUA + bulletpoint + ChatColor.AQUA + "Holiday 2017 Cosmic Crate", ChatColor.AQUA + bulletpoint + ChatColor.AQUA + "Heroic Fallen Hero Bundle", ChatColor.AQUA + bulletpoint + ChatColor.AQUA + "Abandoned Spaceship Dungeon Lootbag" });
        }
        if (v == Version.FEBUARY_2018) {
            final String bulletpoint = ChatColor.BOLD.toString() + "* ";
            cc = ItemUtils.buildItem(Material.ENDER_CHEST, (short)0, ChatColor.translateAlternateColorCodes('&', v.crateName), new String[] { ChatColor.LIGHT_PURPLE + "Unlocked by " + ChatColor.BOLD + username + ChatColor.LIGHT_PURPLE + " at " + ChatColor.DARK_PURPLE + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Icey Flame", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Limited Edition Titles", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "1-3x Item Nametags " + ChatColor.UNDERLINE.toString() + "OR" + ChatColor.YELLOW + " Transmog Scrolls", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "3-5x Showcase Expansion " + ChatColor.UNDERLINE.toString() + "OR" + ChatColor.YELLOW + " Max Home Increase", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Mid-tier Rank Quest", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Random /gkit Crystal " + ChatColor.UNDERLINE.toString() + "OR" + ChatColor.GOLD + " /vkit Crystal", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1x Phantom " + ChatColor.UNDERLINE.toString() + "OR" + ChatColor.GOLD + " Mother of Yijki Item", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1-5x Mystery Mob Spawners", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Febuary 2016 /cc " + ChatColor.UNDERLINE.toString() + "OR" + ChatColor.GOLD + " Heroic Mystery Crate", "", ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", ChatColor.LIGHT_PURPLE + bulletpoint + ChatColor.LIGHT_PURPLE + "Max Rank Quest", ChatColor.LIGHT_PURPLE + bulletpoint + ChatColor.LIGHT_PURPLE + "Heroic Rank Crystal", ChatColor.LIGHT_PURPLE + bulletpoint + ChatColor.LIGHT_PURPLE + "Abandoned Spaceship Dungeon Lootbag" });
        }
        if (v.getCrateData() != null) {
            final List<String> lore = v.getCrateData().getDescriptionLore(username);
            cc = ItemUtils.buildItem(Material.ENDER_CHEST, (short)0, ChatColor.translateAlternateColorCodes('&', v.crateName), (String[])lore.toArray(new String[lore.size()]));
        }
        if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning()) {
            ItemID.setItemID(cc, ItemID.generateNewItemID());
        }
        return cc;
    }
    
    public static Version getCosmicCrateVersion(final ItemStack i) {
        if (!i.hasItemMeta() || !i.getItemMeta().hasDisplayName() || i.getMaxStackSize() == 1) {
            return null;
        }
        for (final Version v : Version.values()) {
            if (v.crateName.equals(i.getItemMeta().getDisplayName())) {
                return v;
            }
        }
        return null;
    }
    
    public static String getCosmicCrateUnlocker(final ItemStack i) {
        final Version v = getCosmicCrateVersion(i);
        if (v != null) {
            return ChatColor.stripColor(i.getItemMeta().getLore().get(0).split(" ")[2]);
        }
        return null;
    }
    
    public static Inventory getCosmicCrateClaimMenu(final Player p) {
        final Inventory inv = Bukkit.createInventory((InventoryHolder)null, 54, "Cosmic Crate Storage");
        for (final Version v : Version.values()) {
            ItemStack item;
            if (p.hasPermission(v.getPermissionNode())) {
                if (hasClaimedCosmicCrate(p, v)) {
                    item = LocalItemUtils.changeType(getCosmicCrate(v, p.getName()), Material.STAINED_GLASS_PANE, (short)5);
                }
                else {
                    item = getCosmicCrate(v, p.getName());
                }
            }
            else {
                item = LocalItemUtils.getLockedPlaceholder(p, v);
            }
            if (v.guiSlot != -1) {
                inv.setItem(v.guiSlot, item);
            }
            else {
                inv.setItem(inv.firstEmpty(), item);
            }
        }
        while (inv.firstEmpty() != -1) {
            inv.setItem(inv.firstEmpty(), LocalItemUtils.getMysteryPlaceholder());
        }
        return inv;
    }
    
    public static void claimCosmicCrate(final Player p, final Version v) {
        final String uuid = p.getUniqueId().toString();
        final List<Version> claimedCrates = CosmicCrate.claimedCosmicCrates.containsKey(uuid) ? CosmicCrate.claimedCosmicCrates.get(uuid) : new ArrayList<Version>();
        claimedCrates.add(v);
        CosmicCrate.claimedCosmicCrates.put(uuid, claimedCrates);
        CosmicCrate.sql_query.add("INSERT IGNORE INTO ccrates(uuid, username, version) VALUES('" + uuid + "', '" + p.getName() + "', '" + v.name() + "')");
        p.getInventory().addItem(new ItemStack[] { getCosmicCrate(v, p.getName()) });
        p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        RandomFireworks.getManager().launchRandomFirework(p.getLocation());
    }
    
    public static boolean hasClaimedCosmicCrate(final Player p, final Version v) {
        return CosmicCrate.claimedCosmicCrates.containsKey(p.getUniqueId().toString()) && CosmicCrate.claimedCosmicCrates.get(p.getUniqueId().toString()).contains(v);
    }
    
    public static void launchFirework(final Location l, final Color c) {
        final Firework fw = (Firework)l.getWorld().spawnEntity(l, EntityType.FIREWORK);
        final FireworkMeta fwm = fw.getFireworkMeta();
        final FireworkEffect effect = FireworkEffect.builder().flicker(true).withColor(c).withFade(c).with(FireworkEffect.Type.BALL_LARGE).trail(true).build();
        fwm.addEffect(effect);
        fwm.setPower(0);
        fw.setFireworkMeta(fwm);
    }
    
    public static ItemStack getExplosiveSnowball() {
        return ItemUtils.buildItem(Material.SNOW_BALL, (short)0, ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "Explosive Snowball", new String[] { ChatColor.GRAY + "Yes, this is a snowball with TNT inside.", ChatColor.GRAY + "Seriously, Happy Holidays!" });
    }
    
    public static boolean isExplosiveSnowball(final ItemStack i) {
        return i != null && i.getType() == Material.SNOW_BALL && i.hasItemMeta() && i.getItemMeta().hasDisplayName() && i.getItemMeta().getDisplayName().equals(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "Explosive Snowball");
    }
    
    public static List<ItemStack> getCosmicCrateLoot(final Player p, final Version v) {
        List<ItemStack> items = new ArrayList<ItemStack>();
        if (v == Version.DECEMBER) {
            items.add(ItemUtils.buildItem(Material.COOKIE, (short)0, ChatColor.translateAlternateColorCodes('&', "&c&lS&a&la&c&ln&c&lt&a&la&c&l's &7&oLittle &d&l[HELPER]!"), new String[] { ChatColor.GRAY + "It is dangerous to go alone, so", ChatColor.GRAY + "take this freshly baked cookie." }));
            items.add(ItemUtils.buildItem(Material.IRON_SPADE, (short)0, ChatColor.translateAlternateColorCodes('&', "&7*CLAP* &e&l&nNEW YEARS CLAPPERS &7*CLAP*"), new String[] { ChatColor.GRAY + "Ring in the New Years with the", ChatColor.GRAY + "most annoying sound in the galaxy!" }));
            items.add(ItemUtils.buildItem(Material.CAKE, (short)0, ChatColor.translateAlternateColorCodes('&', "&f&lC&c&lA&f&lN&c&lD&f&lY&c&lC&f&lA&c&lN&f&lE &f&l&nCake"), new String[] { ChatColor.GRAY + "The perfect mix of sugary goodness", ChatColor.GRAY + "and minty freshness." }));
            items.add(ItemUtils.getItemNametag());
            items.add(ItemUtils.getTransmogScroll());
            final int x = new Random().nextInt(3);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher("#naughty"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher("#nice"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher("#2k15"));
            }
            final CustomEnchantment ce = TieredEnchantments.getRandomEnchantment(new Random().nextInt(2) + 4);
            items.add(ItemUtils.getEnchantmentBook(ce, ce.getMaxLevel() - new Random().nextInt(2), 100, 0));
            items.add(ItemUtils.getBlackscroll(95));
            items.add(ItemUtils.setAmount(EnchantmentBook.getSecretDust(new Random().nextBoolean() ? ItemRarity.ULTIMATE : ItemRarity.LEGENDARY, 70 + new Random().nextInt(40)), 10));
        }
        if (v == Version.JANURARY) {
            items.add(ItemUtils.buildItem(Material.PUMPKIN, (short)0, ChatColor.translateAlternateColorCodes('&', "&e&lNew &6&lYears &f&l&nParty Mask"), new String[] { ChatColor.GRAY + "The best way to party?", ChatColor.GRAY + "Anonymously. Go wild." }));
            items.add(ItemUtils.buildItem(Material.CHEST, (short)0, ChatColor.translateAlternateColorCodes('&', "&c&lExecutive Storage Chest"), new String[] { ChatColor.GRAY + "A chest with the inventory name", ChatColor.GRAY + "of 'Executive Storage Chest', so", ChatColor.GRAY + "people know you mean business." }));
            items.add(ItemUtils.setAmount(NewYears.getExplosiveCake(), 32 + new Random().nextInt(16)));
            items.add(ItemUtils.setAmount(NewYears.getCosmicCracker(), 16 + new Random().nextInt(16)));
            final int x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher("#2k16"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher("Frosty"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher("Coldasice"));
            }
            if (x == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher("#Target"));
                }
                else {
                    items.add(ItemUtils.getTitleVoucher("Frosty"));
                }
            }
            items.add(ItemUtils.getWhitescroll());
            items.add(ItemUtils.setAmount(ItemUtils.getCustomTNT(TNTTypes.Lucky), 8 + new Random().nextInt(9)));
            items.add(KillTrackerAPI.createSoulShard(10 + new Random().nextInt(91)));
            final int i = new Random().nextInt(3);
            if (i == 0) {
                items.add(ItemUtils.setAmount(ItemUtils.getMobSpawner(EntityType.IRON_GOLEM), 2));
            }
            if (i == 1) {
                items.add(ItemUtils.setAmount(ItemUtils.getMobSpawner(EntityType.BLAZE), 2));
            }
            if (i == 2) {
                items.add(ItemUtils.setAmount(ItemUtils.getMobSpawner(EntityType.CREEPER), 2));
            }
        }
        if (v == Version.FEBRUARY) {
            items.add(ItemUtils.buildItem(Material.MAP, (short)0, ChatColor.translateAlternateColorCodes('&', "&4&lValentine's Day&c &nCard"), new String[] { ChatColor.GRAY + "Show that special someone", ChatColor.GRAY + "that you really do care." }));
            items.add(ItemUtils.buildItem(Material.getMaterial(336), (short)0, ChatColor.translateAlternateColorCodes('&', "&e&lBox of &6&l&nChocolates"), new String[] { ChatColor.GRAY + "They save love is no different", ChatColor.GRAY + "than the feeling you get while", ChatColor.GRAY + "consuming large amounts of chocolate." }));
            items.add(ItemUtils.setAmount(Drinks.getDrink(), 32 + new Random().nextInt(16)));
            items.add(ItemUtils.getItemNametag());
            final int x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher("#Lovely"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher("BeMine"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher("Cupid"));
            }
            if (x == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher("#ForeverAlone"));
                }
                else {
                    items.add(ItemUtils.getTitleVoucher("#4EverAlone"));
                }
            }
            items.add(MysteryMobSpawners.getMysteryMobSpawner());
            items.add(ItemUtils.getXpBottle(rand(25000, 50000), "Cosmic Crate"));
            items.add(ItemUtils.getMCMMOVoucher(rand(25, 100)));
            items.add(ItemUtils.getMoneyNote("Cosmic Crate", rand(100000, 500000)));
        }
        if (v == Version.MARCH) {
            items.add(ItemUtils.buildItem(Material.GLASS_BOTTLE, (short)0, ChatColor.translateAlternateColorCodes('&', "&e&lPot of &6&l&nGold"), new String[] { ChatColor.GRAY + "Quick, run! They're after your", ChatColor.GRAY + "lucky charms... and gold!" }));
            items.add(ItemUtils.buildItem(Material.getMaterial(38), (short)3, ChatColor.translateAlternateColorCodes('&', "&a&lF&d&ll&a&lo&d&lw&a&le&d&lr &a&lBouquet"), new String[] { ChatColor.GRAY + "The most beautiful bouquet of", ChatColor.GRAY + "seasonal spring flowers in all", ChatColor.GRAY + "of the Cosmoverse. So pretty..." }));
            items.add(ItemUtils.getItemNametag());
            items.add(ItemUtils.getTransmogScroll());
            final int x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher("#Spring"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher("GlobalWarming"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher("#Leprachaun"));
            }
            if (x == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher("#Lucky"));
                }
                else {
                    items.add(ItemUtils.getTitleVoucher("#Hot"));
                }
            }
            items.add(MysteryMobSpawners.getMysteryMobSpawner());
            items.add(ItemUtils.setAmount(ItemUtils.getCustomCreeperEgg(CreeperTypes.Huge), 2 + ThreadLocalRandom.current().nextInt(4)));
            items.add(ItemUtils.setAmount(ItemUtils.getCustomCreeperEgg(CreeperTypes.Tactical), 1 + ThreadLocalRandom.current().nextInt(3)));
            items.add(ItemUtils.getBlackscroll(100));
        }
        if (v == Version.APRIL) {
            items.add(ItemUtils.buildItem(Material.BOOK_AND_QUILL, (short)0, ChatColor.translateAlternateColorCodes('&', "&f&lT&f&nr&f&oo&f&ml&f&ll &f&lL&f&ke&f&ltt&f&me&f&lr"), new String[] { ChatColor.GRAY + "You've always been a bit of a", ChatColor.GRAY + "prankster, " + ChatColor.GRAY + "who is this letter for?" }));
            items.add(ItemUtils.buildItem(Material.HOPPER, (short)0, ChatColor.translateAlternateColorCodes('&', "&c&lC&6&lo&e&ll&a&lo&b&lr&9&lf&d&lu&5&ll &e&l&nUmbrella"), new String[] { ChatColor.GRAY + "Spring is notorious for its", ChatColor.GRAY + "sporadic rain and storms, be", ChatColor.GRAY + "stylishly prepared for any climate!" }));
            items.add(ItemUtils.getItemNametag());
            items.add(ItemUtils.setAmount(NewYears.getExplosiveCake(), 32 + new Random().nextInt(33)));
            final int x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher("#Timeless"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher("#April"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher("Prankster"));
            }
            if (x == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher("RanD"));
                }
                else {
                    items.add(ItemUtils.getTitleVoucher("Troll"));
                }
            }
            items.add(KitItems.getKitItem(GlobalKits.values()[new Random().nextInt(GlobalKits.values().length)]));
            items.add(CosmicSpaceChestsAPI.getDropPackageChest(ItemRarity.LEGENDARY));
            items.add(MysteryMobSpawners.getMysteryMobSpawner());
            items.add(ItemUtils.getWhitescroll());
        }
        if (v == Version.JULY) {
            items.add(ItemUtils.buildItem(Material.WATER_LILY, (short)0, ChatColor.translateAlternateColorCodes('&', "&f&lI&c&ln&9&ld&f&le&c&lp&9&le&f&ln&c&ld&9&le&f&ln&c&lc&9&le &f&l&nFlag"), new String[] { ChatColor.GRAY + "Wave this flag loud and proud,", ChatColor.GRAY + "for you are FREE." }));
            items.add(ItemUtils.buildItem(Material.ITEM_FRAME, (short)0, ChatColor.translateAlternateColorCodes('&', "&c&lDeclaration &9&lof &f&l&nFREEDOM"), new String[] { ChatColor.GRAY + "An ancient document rumored to", ChatColor.GRAY + "predate the Cosmonauts themselves,", ChatColor.GRAY + "it outlines the basic rights of", ChatColor.GRAY + "every humanoid creature in the galaxy." }));
            items.add(ItemUtils.getTransmogScroll());
            items.add(ItemUtils.setAmount(NewYears.getCosmicCracker(), 16 + new Random().nextInt(32)));
            final int x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher("#FREE"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher("NotASlave"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher("Independent"));
            }
            if (x == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher("#NotForSale"));
                }
                else {
                    items.add(ItemUtils.getTitleVoucher("#Freedom"));
                }
            }
            items.add(CosmicSpaceChestsAPI.getSpaceFlare(ItemRarity.LEGENDARY));
            items.add(FallenHero.getFallenHeroItem(GlobalKits.values()[new Random().nextInt(GlobalKits.values().length)]));
            items.add(FallenHero.getFallenHeroItem(GlobalKits.values()[new Random().nextInt(GlobalKits.values().length)]));
            final List<GlobalKits> heroicKits = new ArrayList<GlobalKits>();
            for (final GlobalKits gkit : GlobalKits.values()) {
                if (gkit.getItemName().contains("Heroic")) {
                    heroicKits.add(gkit);
                }
            }
            items.add(FallenHero.getFallenHeroItem((GlobalKits)heroicKits.get(new Random().nextInt(heroicKits.size()))));
        }
        if (v == Version.AUGUST) {
            items.add(ItemUtils.buildItem(Material.SULPHUR, (short)0, ChatColor.translateAlternateColorCodes('&', "&c&lMojang &4&l&n#EULA"), new String[] { ChatColor.GRAY + "Ah yes the #EULA, commonly mistaken as", ChatColor.GRAY + "the \"End of User Love Agreement\"." }));
            items.add(ItemUtils.buildItem(Material.LAVA_BUCKET, (short)0, ChatColor.translateAlternateColorCodes('&', "&a&lFreshly &c&l&nSqueezed&r &e&lLEMONADE!"), new String[] { ChatColor.GRAY + "Wait, what do you mean this is not", ChatColor.GRAY + "lemonade? It says so right in", ChatColor.GRAY + "the item name!" }));
            items.add(ItemUtils.getItemNametag());
            items.add(ItemUtils.getItemNametag());
            final int x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher("#Pay2Play"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher("$9001"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher("#WePayWeSay"));
            }
            if (x == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher("#Pay2Win"));
                }
                else {
                    items.add(ItemUtils.getTitleVoucher("$CASH$"));
                }
            }
            items.add(CosmicSpaceChestsAPI.getSpaceFlare(ItemRarity.LEGENDARY));
            final List<GlobalKits> heroicKits = new ArrayList<GlobalKits>();
            for (final GlobalKits gkit : GlobalKits.values()) {
                if (gkit.getItemName().contains("Heroic")) {
                    heroicKits.add(gkit);
                }
            }
            items.add(FallenHero.getFallenHeroItem((GlobalKits)heroicKits.get(new Random().nextInt(heroicKits.size()))));
            items.add(ItemUtils.setAmount(ItemUtils.getBlackscroll(100), 2));
            int donorTier = new Random().nextInt(5) + 1;
            if (donorTier >= 5) {
                donorTier -= new Random().nextInt(2);
            }
            if (donorTier >= 4) {
                donorTier -= new Random().nextInt(2);
            }
            items.add(CosmicRankQuestsAPI.getRankQuestItem(donorTier));
        }
        if (v == Version.SCHOOL) {
            items.add(ItemUtils.buildItem(Material.PAPER, (short)0, ChatColor.translateAlternateColorCodes('&', "&6&l&mCheat&f&l Sheet"), new String[] { ChatColor.GRAY + "They can't punish you if they", ChatColor.GRAY + "can't catch you." }));
            items.add(ItemUtils.buildItem(Material.CHEST, (short)0, ChatColor.translateAlternateColorCodes('&', "&f&l&nMeme&7 BACKPACK"), new String[] { ChatColor.GRAY + "Your mom disagreed, but you just", ChatColor.GRAY + "know this backpack is going to make", ChatColor.GRAY + "you the coolest kid in school this year!" }));
            items.add(ItemUtils.getItemNametag());
            final int x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher("#Back2School"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher("CyaInDetention"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher("OnTheGrind"));
            }
            if (x == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher("DROP_OUT"));
                }
                else {
                    items.add(ItemUtils.getTitleVoucher("#Harambe"));
                }
            }
            final List<GlobalKits> heroicKits = new ArrayList<GlobalKits>();
            for (final GlobalKits gkit : GlobalKits.values()) {
                if (gkit.getItemName().contains("Heroic")) {
                    heroicKits.add(gkit);
                }
            }
            items.add(FallenHero.getFallenHeroItem((GlobalKits)heroicKits.get(new Random().nextInt(heroicKits.size()))));
            items.add(FallenHero.getFallenHeroItem((GlobalKits)heroicKits.get(new Random().nextInt(heroicKits.size()))));
            items.add(ItemUtils.setAmount(ItemUtils.getBlackscroll(90), 3));
            items.add(ItemUtils.setAmount(ItemUtils.getWhitescroll(), 2));
            items.add(ItemUtils.setAmount(EnchantmentBook.getSecretDust(new Random().nextBoolean() ? ItemRarity.ULTIMATE : ItemRarity.LEGENDARY, 70 + new Random().nextInt(40)), 15));
        }
        if (v == Version.OCTOBER) {
            items.add(ItemUtils.buildItem(Material.LEAVES, (short)0, ChatColor.translateAlternateColorCodes('&', "&6&lLucky &a&nLeaf"), new String[] { ChatColor.GRAY + "For whatever reason, you feel unusually", ChatColor.GRAY + "lucky while holding this leaf." }));
            items.add(ItemUtils.buildItem(Material.PUMPKIN, (short)0, ChatColor.translateAlternateColorCodes('&', "&8&l&nSpace&r &6&lP&e&lumpki&6&ln"), new String[] { ChatColor.GRAY + "A pumpkin discovered by the", ChatColor.GRAY + "Cosmonauts while exploring space!", ChatColor.GRAY + "What secrest recipes might it hold?!" }));
            items.add(ItemUtils.setAmount(ItemUtils.getTransmogScroll(), 2));
            final int x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher("Sp00ky"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher("CandyCrazy"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher("#WinterIsComing"));
            }
            if (x == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher("Ghost"));
                }
                else {
                    items.add(ItemUtils.getTitleVoucher("#TrickOrGank"));
                }
            }
            items.add(FallenHero.getFallenHeroItem(GlobalKits.values()[new Random().nextInt(GlobalKits.values().length)]));
            items.add(ItemUtils.setAmount(ItemUtils.getBlackscroll(100), 2));
            items.add(CosmicSpaceChestsAPI.getSpaceFlare(ItemRarity.LEGENDARY));
            items.add(MysteryMobSpawners.getMysteryMobSpawner());
            items.add(CosmicFactionBoosters.get().getBoosterManager().createBooster((Math.random() < 0.35) ? BoosterType.EXP : BoosterType.MCMMO, 600, 2.0));
        }
        if (v == Version.HALLOWEEN) {
            items.add(ItemUtils.buildItem(Material.INK_SACK, (short)0, ChatColor.translateAlternateColorCodes('&', "&e&lBag of... &d&lC&a&la&d&ln&a&ld&d&ly"), new String[] { ChatColor.GRAY + "A giant halloween bag stuffed full of", ChatColor.GRAY + "yummy, sugar-coated, delicious pieces", ChatColor.GRAY + "of 2016 CANDY!!" }));
            items.add(ItemUtils.buildItem(Material.CHAINMAIL_HELMET, (short)0, ChatColor.translateAlternateColorCodes('&', "&4&lClown &f&lMask"), new String[] { ChatColor.GRAY + "An eerie and chilling mask", ChatColor.GRAY + "used to hide one's true identity", ChatColor.GRAY + "while committing unspeakable acts." }));
            items.add(ItemUtils.setAmount(ItemUtils.getItemNametag(), 2));
            final int x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher("#TrickOrDerp"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher("#2spooky4me"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher("iLoveSugar"));
            }
            if (x == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher("ImJustClowningAround"));
                }
                else {
                    items.add(ItemUtils.getTitleVoucher("#ClownLivesMatter"));
                }
            }
            items.add(FallenHero.getFallenHeroItem(GlobalKits.values()[new Random().nextInt(GlobalKits.values().length)]));
            items.add(ItemUtils.setAmount(ItemUtils.getBlackscroll(66), 6));
            items.add(ItemUtils.setAmount(EnchantmentBook.getSecretDust(ItemRarity.SOUL, new Random().nextInt(71)), 5));
            items.add(ItemUtils.setAmount(MysteryMobSpawners.getMysteryMobSpawner(), 2));
            items.add(CosmicFactionBoosters.get().getBoosterManager().createBooster((Math.random() < 0.35) ? BoosterType.EXP : BoosterType.MCMMO, 366, 6.66));
        }
        if (v == Version.NOVEMBER) {
            items.add(ItemUtils.buildItem(Material.INK_SACK, (short)11, ChatColor.translateAlternateColorCodes('&', "&eThanks&f&l-&6&l&nGIVING&r &cHorn"), new String[] { ChatColor.GRAY + "An plentiful amount of freshly harvested", ChatColor.GRAY + "fruits, space vegetables, and meats", ChatColor.GRAY + "wrapped in an enchanted weaved basket." }));
            items.add(ItemUtils.buildItem(Material.CHAINMAIL_HELMET, (short)0, ChatColor.translateAlternateColorCodes('&', "&5&l&nPilgram&r &d&l&nHat"), new String[] { ChatColor.GRAY + "A timeless classic in the world", ChatColor.GRAY + "of high fashion -- worn by", ChatColor.GRAY + "farmers and presidents alike!" }));
            items.add(ItemUtils.setAmount(ItemUtils.getTransmogScroll(), new Random().nextInt(2) + 2));
            final int x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher("MakeLoveNotMinecraft"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher("#Thankful"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher("IAmNotATurkey"));
            }
            if (x == 3) {
                if (Math.random() < 0.25) {
                    items.add(ItemUtils.getTitleVoucher("#NoShaveNovember"));
                }
                else {
                    items.add(ItemUtils.getTitleVoucher("#TurkeyLivesMatter"));
                }
            }
            items.add(CosmicFactionBoosters.get().getBoosterManager().createBooster((Math.random() < 0.35) ? BoosterType.EXP : BoosterType.MCMMO, 660, 4.0));
            final List<GlobalKits> heroicKits = new ArrayList<GlobalKits>();
            for (final GlobalKits gkit : GlobalKits.values()) {
                if (gkit.getItemName().contains("Heroic")) {
                    heroicKits.add(gkit);
                }
            }
            items.add(ItemUtils.setAmount(FallenHero.getFallenHeroItem((GlobalKits)heroicKits.get(new Random().nextInt(heroicKits.size()))), 2));
            items.add(ItemUtils.setAmount(ItemUtils.getBlackscroll(99), 3));
            items.add(CosmicSpaceChestsAPI.getSpaceFlare(ItemRarity.LEGENDARY));
            items.add(ItemUtils.setAmount(EnchantmentBook.getSecretDust(new Random().nextBoolean() ? ItemRarity.ULTIMATE : ItemRarity.LEGENDARY, 70 + new Random().nextInt(40)), 15));
        }
        if (v == Version.BLACK_FRIDAY) {
            items.add(ItemUtils.buildItem(Material.BOOK, (short)0, ChatColor.translateAlternateColorCodes('&', "&f&lA &a&l&nGood&f&l Deal"), new String[] { ChatColor.GRAY + "This book supposedly contains a", ChatColor.GRAY + "very good business deal. Perhaps", ChatColor.GRAY + "President Trump would be interested?" }));
            items.add(ItemUtils.buildItem(Material.PAPER, (short)0, ChatColor.translateAlternateColorCodes('&', "&4&l&nFake&f&l &n100%&f&l-Off Coupon"), new String[] { ChatColor.GRAY + "We had to put 'Fake' in the", ChatColor.GRAY + "name of this item because we", ChatColor.GRAY + "knew players would riot about not", ChatColor.GRAY + "actually getting 100% off everything", ChatColor.GRAY + "in the server store. So yes, it is fake." }));
            items.add(ItemUtils.setAmount(ItemUtils.getItemNametag(), 3));
            final int x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher("WALMART"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher("EverythingMustGo"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher("#BlackFriday"));
            }
            if (x == 3) {
                if (Math.random() < 0.25) {
                    items.add(ItemUtils.getTitleVoucher("#PresidentTrump"));
                }
                else {
                    items.add(ItemUtils.getTitleVoucher("ThankfulForBeingRich"));
                }
            }
            items.add(CosmicFactionBoosters.get().getBoosterManager().createBooster((Math.random() < 0.35) ? BoosterType.EXP : BoosterType.MCMMO, 300, 9.99));
            items.add(ItemUtils.setAmount(ItemUtils.getWhitescroll(), new Random().nextInt(2) + 1));
            items.add(EnchantmentOrb.getEnchantmentOrb(new Random().nextInt(25) + 70, 10, OrbType.ARMOR));
            items.add(ItemUtils.setAmount(EnchantmentBook.getSecretDust(new Random().nextBoolean() ? ItemRarity.ULTIMATE : ItemRarity.LEGENDARY, 70 + new Random().nextInt(40)), 10));
            items.add(CosmicRankQuestsAPI.getRankQuestItem(1));
        }
        if (v == Version.DECEMBER_2016) {
            items.add(ItemUtils.buildItem(Material.MILK_BUCKET, (short)0, ChatColor.translateAlternateColorCodes('&', "&f&l&nWinter&r &9&lW&b&lo&9&ln&b&ld&9&le&b&lr&9&ll&b&la&9&ln&b&ld"), new String[] { ChatColor.GRAY + "A mystical, icy cold bucket of...", ChatColor.GRAY + "Winter Wonder? Whatever it is,", ChatColor.GRAY + "it has a chilling aura around it." }));
            items.add(ItemUtils.buildItem(Material.LEATHER_BOOTS, (short)0, ChatColor.translateAlternateColorCodes('&', "&c&lCozy &6&lSpace &c&lMittens"), new String[] { ChatColor.GRAY + "Guranteed to keep your ligiments warm", ChatColor.GRAY + "at temperatures as low as -270.45C!" }));
            items.add(ItemUtils.setAmount(ItemUtils.getTransmogScroll(), 2 + new Random().nextInt(2)));
            final int x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher("#ICY"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher("WinterIsComing"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher("KeepCalmAndStayToasty"));
            }
            if (x == 3) {
                if (Math.random() < 0.25) {
                    items.add(ItemUtils.getTitleVoucher("0C"));
                }
                else {
                    items.add(ItemUtils.getTitleVoucher("EveryDayImShovelin"));
                }
            }
            items.add(ItemUtils.setAmount(ItemUtils.getWhitescroll(), new Random().nextInt(2) + 1));
            items.add(ItemUtils.setAmount(EnchantmentOrb.getEnchantmentOrb(new Random().nextInt(26) + 50, 10, OrbType.ARMOR), 2));
            items.add(ItemUtils.setAmount(EnchantmentOrb.getEnchantmentOrb(new Random().nextInt(31) + 30, 10, OrbType.WEAPON), 1));
            items.add(CosmicSpaceChestsAPI.getSpaceFlare(ItemRarity.LEGENDARY));
            items.add(ItemUtils.setAmount(FallenHero.getFallenHeroItem(GlobalKits.values()[new Random().nextInt(GlobalKits.values().length)]), 2));
        }
        if (v == Version.HOLIDAY) {
            items.add(ItemUtils.buildItem(Material.DIAMOND, (short)0, ChatColor.translateAlternateColorCodes('&', "&a&lH&4&lo&a&ll&4&li&a&ld&4&la&4&ly &f&l&nSpirit"), new String[] { ChatColor.GRAY + "All the holiday spirit of a planet,", ChatColor.GRAY + "captured and trapped within a beautiful,", ChatColor.GRAY + "soul crystal. Oh, how very jolly indeed." }));
            items.add(ItemUtils.setAmount(getExplosiveSnowball(), 10));
            items.add(ItemUtils.setAmount(ItemUtils.getItemNametag(), 3));
            final int x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher("#XMAS"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher("#2k17"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher("Santa"));
            }
            if (x == 3) {
                if (Math.random() < 0.25) {
                    items.add(ItemUtils.getTitleVoucher("NeitherNaughtyNorNice"));
                }
                else {
                    items.add(ItemUtils.getTitleVoucher("IFindYourLackOfCheerDisturbing"));
                }
            }
            items.add(ItemUtils.setAmount(EnchantmentOrb.getEnchantmentOrb(new Random().nextInt(26) + 75, 10, OrbType.ARMOR), 2 + new Random().nextInt(2)));
            items.add(ItemUtils.setAmount(EnchantmentOrb.getEnchantmentOrb(new Random().nextInt(26) + 75, 10, OrbType.WEAPON), 1 + new Random().nextInt(2)));
            final List<GlobalKits> heroicKits = new ArrayList<GlobalKits>();
            for (final GlobalKits gkit : GlobalKits.values()) {
                if (gkit.getItemName().contains("Heroic")) {
                    heroicKits.add(gkit);
                }
            }
            items.add(ItemUtils.setAmount(FallenHero.getFallenHeroItem((GlobalKits)heroicKits.get(new Random().nextInt(heroicKits.size()))), 3));
            int rankId = 1;
            if (Math.random() < 0.25) {
                rankId = 2;
                if (Math.random() < 0.05) {
                    rankId = 3;
                }
            }
            items.add(CosmicRankQuestsAPI.getRankQuestItem(rankId));
            items.add(EvolutionKitItems.getKitItem(EvolutionKits.PHOENIX));
        }
        if (v == Version.JANUARY_2017) {
            items.add(ItemUtils.buildItem(Material.PAPER, (short)0, ChatColor.translateAlternateColorCodes('&', "&3&l&nNew Years&f&l Resolution"), new String[] { ChatColor.GRAY + "This thin piece of paper represents", ChatColor.GRAY + "your determination to change absolutely,", ChatColor.GRAY + "nothing about yourself from last year...", ChatColor.GRAY + "or something like that." }));
            items.add(ItemUtils.buildItem(Material.GOLD_HELMET, (short)0, ChatColor.translateAlternateColorCodes('&', "&a&l2&b&l0&e&l1&d&l7 &f&l&nTrophy"), new String[] { ChatColor.GRAY + "The year has only just started,", ChatColor.GRAY + "but you know you have already won." }));
            items.add(ItemUtils.setAmount(ItemUtils.getTransmogScroll(), 2 + new Random().nextInt(2)));
            final int x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher("#NotMyPlanet"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher("2017_OG"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher("Renewal"));
            }
            if (x == 3) {
                if (Math.random() < 0.25) {
                    items.add(ItemUtils.getTitleVoucher("#NewYearNewMemes"));
                }
                else {
                    items.add(ItemUtils.getTitleVoucher("BuildThatWall"));
                }
            }
            items.add(CosmicFactionBoosters.get().getBoosterManager().createBooster((Math.random() < 0.35) ? BoosterType.EXP : BoosterType.MCMMO, 600, 5.0));
            items.add(ItemUtils.setAmount(ItemUtils.getWhitescroll(), new Random().nextInt(2) + 2));
            items.add(ItemUtils.setAmount(ItemUtils.getBlackscroll(90), new Random().nextInt(3) + 3));
            items.add(CosmicSpaceChestsAPI.getSpaceFlare(ItemRarity.GOD));
            items.add(KitItems.getKitItem(GlobalKits.values()[new Random().nextInt(GlobalKits.values().length)]));
        }
        if (v == Version.VALENTINE) {
            items.add(ItemUtils.buildItem(Material.PAPER, (short)0, ChatColor.translateAlternateColorCodes('&', "&f&lForever &4&l&nAlone&f&l Card"), new String[] { ChatColor.GRAY + "This card is your official membership to", ChatColor.GRAY + "the exclusive \"Forever Alone\" club...", ChatColor.GRAY + "Mom would be so proud of you." }));
            items.add(ItemUtils.buildItem(Material.TRAPPED_CHEST, (short)0, ChatColor.translateAlternateColorCodes('&', "&f&lBox of &0&l&nMemes"), new String[] { ChatColor.GRAY + "The only box ever constructed with more,", ChatColor.GRAY + "(dank) power than Pandora's Box." }));
            items.add(ItemUtils.setAmount(ItemUtils.getItemNametag(), 1 + new Random().nextInt(3)));
            final int x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher("#CandyNCuddle"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher("AnyGirls?"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher("#CosmicNChill"));
            }
            if (x == 3) {
                if (Math.random() < 0.01) {
                    items.add(ItemUtils.getTitleVoucher("(/\u00ef¿½^\u00ef¿½)_/L"));
                }
                else {
                    items.add(ItemUtils.getTitleVoucher("#IHaveNoLifeAndIDoNotCare"));
                }
            }
            items.add(EvolutionKitItems.getKitItem(EvolutionKits.TROLL));
            items.add(ItemUtils.setAmount(ItemUtils.getWhitescroll(), new Random().nextInt(4) + 1));
            items.add(ItemUtils.setAmount(FallenHero.getFallenHeroItem(GlobalKits.values()[new Random().nextInt(GlobalKits.values().length)]), 2));
            items.add(ItemUtils.setAmount(EnchantmentBook.getSecretDust(ItemRarity.SOUL, new Random().nextInt(60) + 20), new Random().nextInt(3) + 2));
            if (Math.random() < 0.33) {
                items.add(ItemUtils.setAmount(EnchantmentOrb.getEnchantmentOrb(new Random().nextInt(11) + 90, 10, OrbType.WEAPON), 1));
            }
            else {
                items.add(ItemUtils.setAmount(EnchantmentOrb.getEnchantmentOrb(new Random().nextInt(11) + 90, 10, OrbType.ARMOR), 1));
            }
        }
        if (v == Version.MARCH_2017) {
            items.add(ItemUtils.buildItem(Material.STICK, (short)0, ChatColor.translateAlternateColorCodes('&', "&a&l&nSpring&r &b&lTotem"), new String[] { ChatColor.GRAY + "An enchanted wooden totem that", ChatColor.GRAY + "echoes the spirits of the ancients...", ChatColor.GRAY + "how bout that?" }));
            items.add(ItemUtils.buildItem(Material.EMERALD, (short)0, ChatColor.translateAlternateColorCodes('&', "&e&nLuck of the...&r &a&lIrish"), new String[] { ChatColor.GRAY + "A magical gem imbued with countless", ChatColor.GRAY + "generations of some of the luckiest", ChatColor.GRAY + "blockmen and women to ever live." }));
            items.add(ItemUtils.setAmount(ItemUtils.getTransmogScroll(), 3));
            final int x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher("Leprechaun"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher("#IsItStillWinter?"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher("#GotLucky"));
            }
            if (x == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher("qt3.14"));
                }
                else {
                    items.add(ItemUtils.getTitleVoucher("#CatchMeOutside"));
                }
            }
            items.add(FallenHero.getFallenHeroItem(new Random().nextBoolean() ? EvolutionKits.TROLL.getKit() : EvolutionKits.PHOENIX.getKit()));
            items.add(ItemUtils.setAmount(FallenHero.getFallenHeroItem(GlobalKits.values()[new Random().nextInt(GlobalKits.values().length)]), 1 + new Random().nextInt(3)));
            final List<GlobalKits> heroicKits = new ArrayList<GlobalKits>();
            for (final GlobalKits gkit : GlobalKits.values()) {
                if (gkit.getItemName().contains("Heroic")) {
                    heroicKits.add(gkit);
                }
            }
            items.add(ItemUtils.setAmount(FallenHero.getFallenHeroItem((GlobalKits)heroicKits.get(new Random().nextInt(heroicKits.size()))), 2));
            int rankId = 1;
            if (Math.random() < 0.5) {
                rankId = 2;
                if (Math.random() < 0.25) {
                    rankId = 3;
                }
            }
            items.add(CosmicRankQuestsAPI.getRankQuestItem(rankId));
            if (Math.random() < 0.33) {
                items.add(ItemUtils.setAmount(EnchantmentOrb.getEnchantmentOrb(new Random().nextInt(26) + 75, 10, OrbType.WEAPON), 3 + new Random().nextInt(2)));
            }
            else {
                items.add(ItemUtils.setAmount(EnchantmentOrb.getEnchantmentOrb(new Random().nextInt(26) + 75, 10, OrbType.ARMOR), 3 + new Random().nextInt(2)));
            }
        }
        if (v == Version.APRIL_2017) {
            items.add(ItemUtils.buildItem(Material.RAW_FISH, (short)3, ChatColor.translateAlternateColorCodes('&', "&e&lFool of &d&l&nApril"), new String[] { ChatColor.GRAY + "Quite possibly the most derpy item in Minecraft.", ChatColor.GRAY + "... :/" }));
            items.add(ItemUtils.buildItem(Material.CHEST, (short)0, ChatColor.translateAlternateColorCodes('&', "&a&l&nEaster&r &d&l&nBasket"), new String[] { ChatColor.GRAY + "A magically woven cosmic basket", ChatColor.GRAY + "designed exclusively to collect", ChatColor.GRAY + "and store Easter Eggs in!" }));
            items.add(ItemUtils.setAmount(ItemUtils.getItemNametag(), 2 + new Random().nextInt(2)));
            final int x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher("#AprilFools"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher("EasterBunny"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher("Edgy"));
            }
            if (x == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher("Sociopath"));
                }
                else {
                    items.add(ItemUtils.getTitleVoucher("Joker"));
                }
            }
            items.add(ItemUtils.setAmount(Easter.getEasterEgg(), 10 + new Random().nextInt(6)));
            if (Math.random() < 0.4) {
                items.add(ItemUtils.setAmount(FallenHero.getFallenHeroItem((Math.random() < 0.3) ? EvolutionKits.ALCHEMIST.getKit() : ((Math.random() < 0.5) ? EvolutionKits.PHOENIX.getKit() : EvolutionKits.TROLL.getKit())), 1 + new Random().nextInt(2)));
            }
            else {
                items.add(ItemUtils.setAmount(FallenHero.getFallenHeroItem(GlobalKits.values()[new Random().nextInt(GlobalKits.values().length)]), 1 + new Random().nextInt(2)));
            }
            items.add(ItemUtils.getNewBossEgg(BossType.SOUL_REAPER));
            items.add(RandomizationScroll.getRandomizationScroll(ItemRarity.GOD));
            items.add(CosmicFactionBoosters.get().getBoosterManager().createBooster((Math.random() < 0.45) ? BoosterType.EXP : BoosterType.MCMMO, 600, 5.0));
        }
        if (v == Version.MAY_2017) {
            items.add(ItemUtils.buildItem(Material.RED_ROSE, (short)8, ChatColor.translateAlternateColorCodes('&', "&a&lSpring...&2&l is coming."), new String[] { ChatColor.GRAY + "You knew winter was coming, but no one", ChatColor.GRAY + "prepared you for... SPRING 2017!" }));
            items.add(ItemUtils.buildItem(Material.INK_SACK, (short)9, ChatColor.translateAlternateColorCodes('&', "&f&lVitamin &e&l&nD"), new String[] { ChatColor.GRAY + "Spending all your days indoors", ChatColor.GRAY + "slaying monsters is tough work,", ChatColor.GRAY + "replenish yourself with some Vitamin D!" }));
            items.add(ItemUtils.setAmount(ItemUtils.getTransmogScroll(), 2 + new Random().nextInt(3)));
            final int x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher("#May2017"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher("Sunny"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher("SpringIsComing"));
            }
            if (x == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher("TheSunlightHurtsMyEyes"));
                }
                else {
                    items.add(ItemUtils.getTitleVoucher("#WaitingForWinter"));
                }
            }
            if (Math.random() < 0.33) {
                items.add(ItemUtils.setAmount(EnchantmentOrb.getEnchantmentOrb(new Random().nextInt(26) + 75, 10, OrbType.WEAPON), 2));
            }
            else {
                items.add(ItemUtils.setAmount(EnchantmentOrb.getEnchantmentOrb(new Random().nextInt(26) + 75, 10, OrbType.ARMOR), 2));
            }
            items.add(ItemUtils.setAmount(FallenHero.getFallenHeroItem((Math.random() < 0.3) ? EvolutionKits.ALCHEMIST.getKit() : ((Math.random() < 0.5) ? EvolutionKits.PHOENIX.getKit() : EvolutionKits.TROLL.getKit())), 1 + new Random().nextInt(2)));
            items.add(ItemUtils.setAmount(FallenHero.getFallenHeroItem(GlobalKits.values()[new Random().nextInt(GlobalKits.values().length)]), 1 + new Random().nextInt(2)));
            items.add(ItemUtils.setAmount(RandomizationScroll.getRandomizationScroll(ItemRarity.GOD), 2 + new Random().nextInt(2)));
            items.add(CosmicConquestAPI.createFactionToken(5 + new Random().nextInt(11)));
        }
        if (v == Version.JUNE_2017) {
            items.add(ItemUtils.buildItem(Material.PAPER, (short)0, ChatColor.translateAlternateColorCodes('&', "&c&l&nDetention&r &f&lSlip"), new String[] { ChatColor.GRAY + "You tried to explain, but they", ChatColor.GRAY + "did not understand that it was", ChatColor.GRAY + "" + ChatColor.UNDERLINE + "just a prank, bro." }));
            items.add(ItemUtils.setAmount(ItemUtils.getItemNametag(), 2 + new Random().nextInt(2)));
            final int x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher("#June2017"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher("SchoolIsCruel"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher("#IGotSmarts"));
            }
            if (x == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher("BasicallyNaked"));
                }
                else {
                    items.add(ItemUtils.getTitleVoucher("#NoSeasons"));
                }
            }
            if (Math.random() < 0.3) {
                items.add(ItemUtils.setAmount(EnchantmentOrb.getEnchantmentOrb(new Random().nextInt(16) + 80, 10, OrbType.WEAPON), 1 + new Random().nextInt(5)));
            }
            else {
                items.add(ItemUtils.setAmount(EnchantmentOrb.getEnchantmentOrb(new Random().nextInt(16) + 80, 10, OrbType.ARMOR), 1 + new Random().nextInt(5)));
            }
            final List<GlobalKits> heroicKits = new ArrayList<GlobalKits>();
            for (final GlobalKits gkit : GlobalKits.values()) {
                if (gkit.getItemName().contains("Heroic")) {
                    heroicKits.add(gkit);
                }
            }
            items.add(ItemUtils.setAmount(FallenHero.getFallenHeroItem((GlobalKits)heroicKits.get(new Random().nextInt(heroicKits.size()))), 1 + new Random().nextInt(3)));
            items.add(ItemUtils.setAmount(ItemUtils.getWhitescroll(), 3 + new Random().nextInt(6)));
            items.add(ItemUtils.setAmount(ItemUtils.getBlackscroll(95), 5 + new Random().nextInt(6)));
            items.add(KitItems.getKitItem(GlobalKits.values()[new Random().nextInt(GlobalKits.values().length)]));
            int rankId = 1;
            if (Math.random() < 0.5) {
                rankId = 2;
                if (Math.random() < 0.25) {
                    rankId = 3;
                }
            }
            items.add(CosmicRankQuestsAPI.getRankQuestItem(rankId));
        }
        if (v == Version.JULY_2017) {
            items.add(ItemUtils.buildItem(Material.FIREWORK, (short)0, ChatColor.translateAlternateColorCodes('&', "&4&l&mIllegal&r &c&lFire&cworks"), new String[] { ChatColor.GRAY + "Smuggled from the furthest reaches", ChatColor.GRAY + "of the Cosmoverse, these are some", ChatColor.GRAY + "" + ChatColor.UNDERLINE + "BIG." + ChatColor.GRAY + " ASS. FIREWORKS." }));
            if (Math.random() < 0.6) {
                items.add(ItemUtils.setAmount(ItemUtils.getItemNametag(), 3));
            }
            else {
                items.add(ItemUtils.setAmount(ItemUtils.getTransmogScroll(), 3));
            }
            final int x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher("#IPlayForMyself"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher("FR33_K"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher("BaseSlave"));
            }
            if (x == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher("Donald_Trump"));
                }
                else {
                    items.add(ItemUtils.getTitleVoucher("Pyromaniac"));
                }
            }
            items.add(ItemUtils.setAmount(CosmicSpaceChestsAPI.getSpaceFlare(ItemRarity.GOD), 1 + new Random().nextInt(2)));
            if (Math.random() < 0.35) {
                items.add(ItemUtils.setAmount(EnchantmentOrb.getEnchantmentOrb(25, 11, OrbType.WEAPON), 1 + new Random().nextInt(2)));
            }
            else {
                items.add(ItemUtils.setAmount(EnchantmentOrb.getEnchantmentOrb(25, 11, OrbType.ARMOR), 1 + new Random().nextInt(2)));
            }
            items.add(ItemUtils.setAmount(RandomizationScroll.getRandomizationScroll(ItemRarity.GOD), 3 + new Random().nextInt(2)));
            items.add(CosmicFactionBoosters.get().getBoosterManager().createBooster((Math.random() < 0.4) ? BoosterType.EXP : BoosterType.MCMMO, 600, 7.4));
            items.add(ItemUtils.setAmount(FallenHero.getFallenHeroItem((Math.random() < 0.25) ? EvolutionKits.ALCHEMIST.getKit() : ((Math.random() < 0.5) ? EvolutionKits.PHOENIX.getKit() : ((Math.random() < 0.75) ? EvolutionKits.TROLL.getKit() : EvolutionKits.OGRE.getKit()))), 1 + new Random().nextInt(3)));
            int rankId2 = 1;
            if (Math.random() < 0.6) {
                rankId2 = 2;
                if (Math.random() < 0.25) {
                    rankId2 = 3;
                }
                if (Math.random() < 0.1) {
                    rankId2 = 4;
                }
            }
            items.add(CosmicRankQuestsAPI.getRankQuestItem(rankId2));
        }
        if (v == Version.AUGUST_2017) {
            items.add(ItemUtils.buildItem(Material.YELLOW_FLOWER, (short)8, ChatColor.translateAlternateColorCodes('&', "&f&lSummer &a&n&oVibes"), new String[] { ChatColor.GRAY + "You cannot help it, school is", ChatColor.GRAY + "out. It's summer everyday, bro." }));
            items.add(ItemUtils.setAmount(ShowcaseExpander.getShowcaseExpanderItem(), 1 + new Random().nextInt(3)));
            items.add(ItemUtils.setAmount(EssentialsAddHomes.getAddHomeItem(), 1 + new Random().nextInt(2)));
            final int x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher("#ScummerVibes"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher("CheckMyTanLines"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher("#NoSchoolNoProblems"));
            }
            if (x == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher("#JakePauler"));
                }
                else {
                    items.add(ItemUtils.getTitleVoucher("IAmSoBored"));
                }
            }
            items.add(ItemUtils.setAmount(CosmicSpaceChestsAPI.getSpaceFlare(ItemRarity.LEGENDARY), 2 + new Random().nextInt(3)));
            if (Math.random() < 0.35) {
                items.add(ItemUtils.setAmount(EnchantmentOrb.getEnchantmentOrb(75 + new Random().nextInt(21), 11, OrbType.WEAPON), 1 + new Random().nextInt(2)));
            }
            else {
                items.add(ItemUtils.setAmount(EnchantmentOrb.getEnchantmentOrb(75 + new Random().nextInt(21), 11, OrbType.ARMOR), 1 + new Random().nextInt(2)));
            }
            final List<GlobalKits> heroicKits = new ArrayList<GlobalKits>();
            for (final GlobalKits gkit : GlobalKits.values()) {
                if (gkit.getItemName().contains("Heroic")) {
                    heroicKits.add(gkit);
                }
            }
            items.add(ItemUtils.setAmount(FallenHero.getFallenHeroItem((GlobalKits)heroicKits.get(new Random().nextInt(heroicKits.size()))), 1 + new Random().nextInt(3)));
            items.add(ItemUtils.setAmount(ItemUtils.getWhitescroll(), 4 + new Random().nextInt(2)));
            items.add(CosmicConquestAPI.createFactionToken(10 + new Random().nextInt(16)));
        }
        if (v == Version.SCHOOL_2017) {
            items.add(ItemUtils.buildItem(Material.FIRE, (short)8, ChatColor.translateAlternateColorCodes('&', "&7" + ((p != null) ? (p.getName() + "'s") : "Your") + "... &c&l&nMIXTAPE"), new String[] { ChatColor.GOLD + "" + ChatColor.BOLD + "CAUTION: " + ChatColor.GRAY + "Material is \"fire af\"." }));
            items.add(ItemUtils.setAmount(EssentialsAddHomes.getAddHomeItem(), 1 + new Random().nextInt(3)));
            items.add(ItemUtils.setAmount(ItemUtils.getItemNametag(), 3 + new Random().nextInt(2)));
            final int x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher("#BackToJail"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher("DoMyHomework"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher("IsItSummerYet?"));
            }
            if (x == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher("IDC"));
                }
                else {
                    items.add(ItemUtils.getTitleVoucher("MyFriendsPlayMinecraft"));
                }
            }
            items.add(ItemID.removeItemID(ItemUtils.setAmount(CosmicSpaceChestsAPI.getDropPackageChest(ItemRarity.LEGENDARY), 2 + new Random().nextInt(2))));
            items.add(ItemUtils.setAmount(FallenHero.getFallenHeroItem(GlobalKits.values()[new Random().nextInt(GlobalKits.values().length)]), 2 + new Random().nextInt(2)));
            items.add(EvolutionKitItems.getKitItem((Math.random() < 0.3) ? EvolutionKits.ALCHEMIST : ((Math.random() < 0.5) ? EvolutionKits.PHOENIX : EvolutionKits.TROLL)));
            items.add(getMysteryCrate());
            items.add(CosmicRankQuestsAPI.getRankQuestItem(1 + new Random().nextInt(2)));
        }
        if (v == Version.SEPTEMBER_2017) {
            items.add(ItemUtils.buildItem(Material.RECORD_11, (short)0, ChatColor.translateAlternateColorCodes('&', "&8&lInter&f&ngalactic&r &9&lFidget Sp&ki&9&lnner"), new String[] { ChatColor.GRAY + "Confusingly addictive... " + ChatColor.ITALIC + "*SPIN* *SPIN* *SPIN*" }));
            items.add(ItemUtils.setAmount(ItemUtils.getTransmogScroll(), 4));
            if (new Random().nextBoolean()) {
                items.add(ItemUtils.setAmount(EssentialsAddHomes.getAddHomeItem(), 1 + new Random().nextInt(2)));
            }
            else {
                items.add(ItemUtils.setAmount(ShowcaseExpander.getShowcaseExpanderItem(), 1 + new Random().nextInt(2)));
            }
            final int x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher("#OverIt"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher("SchoolIsEz"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher("#Suspended"));
            }
            if (x == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher("IOnlyCheatIRL"));
                }
                else {
                    items.add(ItemUtils.getTitleVoucher("#Expelled"));
                }
            }
            items.add(ItemID.removeItemID(ItemUtils.setAmount(CosmicSpaceChestsAPI.getDropPackageChest((Math.random() < 0.2) ? ItemRarity.GOD : ItemRarity.LEGENDARY), 2)));
            if (new Random().nextBoolean()) {
                items.add(ItemUtils.setAmount(ItemUtils.getWhitescroll(), 2 + new Random().nextInt(3)));
            }
            else {
                items.add(ItemUtils.setAmount(ItemUtils.getBlackscroll(100), 2 + new Random().nextInt(3)));
            }
            items.add(CosmicFactionBoosters.get().getBoosterManager().createBooster(BoosterType.EXP, 900, 5.0));
            items.add(CosmicConquestAPI.createFactionToken(10 + new Random().nextInt(16)));
            if (Math.random() < 0.35) {
                items.add(ItemUtils.setAmount(EnchantmentOrb.getEnchantmentOrb(50 + new Random().nextInt(26), 12, OrbType.WEAPON), 1));
            }
            else {
                items.add(ItemUtils.setAmount(EnchantmentOrb.getEnchantmentOrb(50 + new Random().nextInt(26), 12, OrbType.ARMOR), 1));
            }
        }
        if (v == Version.OCTOBER_2017) {
            items.add(ItemUtils.buildItem(Material.EYE_OF_ENDER, (short)0, ChatColor.translateAlternateColorCodes('&', "&d&l&oCreepy &c&lEye"), new String[] { ChatColor.GRAY + "You can't shake this feeling... ", ChatColor.GRAY + "that something is " + ChatColor.UNDERLINE + "watching" + ChatColor.GRAY + "you." }));
            items.add(ItemUtils.setAmount(ItemUtils.getItemNametag(), 1 + new Random().nextInt(5)));
            items.add(ItemUtils.setAmount(EssentialsAddHomes.getAddHomeItem(), 1 + new Random().nextInt(2)));
            final int x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher("#Spoopy"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher("Clowns"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher("PumpkinManiac"));
            }
            if (x == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher("Depression"));
                }
                else {
                    items.add(ItemUtils.getTitleVoucher("2Spooky4Me"));
                }
            }
            items.add(ItemID.removeItemID(ItemUtils.setAmount(CosmicSpaceChestsAPI.getDropPackageChest(ItemRarity.LEGENDARY), 1 + new Random().nextInt(3))));
            items.add(ItemUtils.setAmount(ItemUtils.getBlackscroll(90 + new Random().nextInt(11)), 3 + new Random().nextInt(3)));
            items.add(ItemUtils.setAmount(FallenHero.getFallenHeroItem(EvolutionKits.SLAYER.getKit()), 1 + new Random().nextInt(2)));
            int rankId2 = 1;
            if (Math.random() < 0.6) {
                rankId2 = 2;
                if (Math.random() < 0.25) {
                    rankId2 = 3;
                }
                if (Math.random() < 0.1) {
                    rankId2 = 4;
                }
            }
            items.add(CosmicRankQuestsAPI.getRankQuestItem(rankId2));
            items.add(ItemUtils.getMCMMOVoucher(rand(50, 100)));
        }
        if (v == Version.HALLOWEEN_2017) {
            items.add(ItemUtils.buildItem(Material.GLASS_BOTTLE, (short)0, ChatColor.translateAlternateColorCodes('&', "&f&lBottle O' &c&lS&4&lp&c&lo&4&lo&c&lk&4&ly"), new String[] { ChatColor.GRAY + "Highly dense and compounded spooky", ChatColor.GRAY + "sealed within a glass bottle." }));
            items.add(ItemUtils.setAmount(ItemUtils.getTransmogScroll(), 4 + new Random().nextInt(2)));
            items.add(ItemUtils.setAmount(ShowcaseExpander.getShowcaseExpanderItem(), 2 + new Random().nextInt(2)));
            final int x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher("#TrickAndTreat"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher("Monster"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher("CandyAddict"));
            }
            if (x == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher("SerialKiller"));
                }
                else {
                    items.add(ItemUtils.getTitleVoucher("ILikeSpookyMemes"));
                }
            }
            items.add(ItemID.removeItemID(ItemUtils.setAmount(CosmicSpaceChestsAPI.getDropPackageChest((Math.random() < 0.2) ? ItemRarity.GOD : ItemRarity.LEGENDARY), 2)));
            if (Math.random() < 0.35) {
                items.add(ItemUtils.setAmount(EnchantmentOrb.getEnchantmentOrb(66, 12, OrbType.WEAPON), 1));
            }
            else {
                items.add(ItemUtils.setAmount(EnchantmentOrb.getEnchantmentOrb(66, 12, OrbType.ARMOR), 1));
            }
            final List<GlobalKits> heroicKits = new ArrayList<GlobalKits>();
            for (final GlobalKits gkit : GlobalKits.values()) {
                if (gkit.getItemName().contains("Heroic")) {
                    heroicKits.add(gkit);
                }
            }
            items.add(ItemUtils.setAmount(FallenHero.getFallenHeroItem((GlobalKits)heroicKits.get(new Random().nextInt(heroicKits.size()))), 2 + new Random().nextInt(2)));
            items.add(getMysteryCrate());
            items.add(ItemID.removeItemID(CosmicArmorSetsAPI.getArmorSet(ArmorSet.PHANTOM)[new Random().nextInt(4)]));
        }
        if (v == Version.NOVEMBER_2017) {
            items.add(ItemUtils.buildItem(Material.QUARTZ_ORE, (short)0, ChatColor.translateAlternateColorCodes('&', "&f&lSeasonal &8&lD&7&li&8&ls&7&lc&8&lo&7&lm&8&lb&7&lo&8&lb&7&lu&8&ll&7&la&8&lt&7&lo&8&lr "), new String[] { ChatColor.GRAY + "A cosmonaut piece of tinkering genius", ChatColor.GRAY + "used to control planetary seasons." }));
            items.add(ItemUtils.setAmount(EssentialsAddHomes.getAddHomeItem(), 2 + new Random().nextInt(2)));
            items.add(ItemUtils.setAmount(LoreTag.getLoreTagItem(), 1 + new Random().nextInt(2)));
            final int x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher("#WinterIsHere"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher("Chilly"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher("#Numb"));
            }
            if (x == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher("TheTimesTheyAreAChanging"));
                }
                else {
                    items.add(ItemUtils.getTitleVoucher("HotChocolateNChill"));
                }
            }
            items.add(ItemID.removeItemID(ItemUtils.setAmount(CosmicSpaceChestsAPI.getSpaceFlare(ItemRarity.GOD), 1 + new Random().nextInt(2))));
            if (Math.random() < 0.35) {
                items.add(ItemUtils.setAmount(EnchantmentOrb.getEnchantmentOrb(75 + new Random().nextInt(6), 12, OrbType.WEAPON), 1));
            }
            else {
                items.add(ItemUtils.setAmount(EnchantmentOrb.getEnchantmentOrb(75 + new Random().nextInt(6), 12, OrbType.ARMOR), 1));
            }
            int rankId2 = 1;
            if (Math.random() < 0.6) {
                rankId2 = 2;
                if (Math.random() < 0.25) {
                    rankId2 = 3;
                }
                if (Math.random() < 0.1) {
                    rankId2 = 4;
                }
            }
            items.add(CosmicRankQuestsAPI.getRankQuestItem(rankId2));
            items.add(ItemID.removeItemID(ItemUtils.setAmount(getMysteryCrate(), 1 + ((Math.random() < 0.25) ? 1 : 0))));
            items.add(CosmicConquestAPI.createFactionToken(15 + new Random().nextInt(6)));
        }
        if (v == Version.THANKSGIVING_2017) {
            items.add(ItemUtils.buildItem(Material.COOKED_CHICKEN, (short)0, ChatColor.translateAlternateColorCodes('&', "&f&lTurkey of &a&l&nDiplomacy"), new String[] { ChatColor.GRAY + "Who knew a dead bird could do so", ChatColor.GRAY + "much for intergalactic relations?" }));
            items.add(ItemUtils.setAmount(ItemNametag.getNametagItem(), 5));
            items.add(ItemUtils.setAmount(LoreTag.getLoreTagItem(), 5));
            final int x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher("BlackFriday2017"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher("#Capitalism"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher("NotAPuppet"));
            }
            if (x == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher("VIP"));
                }
                else {
                    items.add(ItemUtils.getTitleVoucher("#IPayToLose"));
                }
            }
            items.add(ItemID.removeItemID(ItemUtils.setAmount(CosmicSpaceChestsAPI.getSpaceFlare((Math.random() < 0.33) ? ItemRarity.GOD : ItemRarity.LEGENDARY), 3 + new Random().nextInt(2))));
            if (Math.random() < 0.35) {
                items.add(ItemUtils.setAmount(EnchantmentOrb.getEnchantmentOrb(80 + new Random().nextInt(11), 12, OrbType.WEAPON), 1));
            }
            else {
                items.add(ItemUtils.setAmount(EnchantmentOrb.getEnchantmentOrb(80 + new Random().nextInt(11), 12, OrbType.ARMOR), 1));
            }
            ItemStack bossEgg = null;
            final double j = Math.random();
            if (j <= 0.02) {
                bossEgg = ItemUtils.getNewBossEgg(BossType.DEMONIC_WITHER);
            }
            else if (j <= 0.08) {
                bossEgg = ItemUtils.getNewBossEgg(BossType.PLAGUE_BLOATER);
            }
            else if (j < 0.4) {
                bossEgg = ItemUtils.getNewBossEgg(BossType.KING_SLIME);
            }
            else {
                bossEgg = ItemUtils.getNewBossEgg(BossType.BROOD_MOTHER);
            }
            bossEgg = ItemID.removeItemID(ItemUtils.setAmount(bossEgg, 1 + new Random().nextInt(2)));
            items.add(bossEgg);
            if (Math.random() < 0.75) {
                items.add(KitItems.getKitItem(GlobalKits.values()[new Random().nextInt(GlobalKits.values().length)]));
            }
            else {
                items.add(EvolutionKitItems.getKitItem((Math.random() < 0.3) ? EvolutionKits.ALCHEMIST : ((Math.random() < 0.5) ? EvolutionKits.PHOENIX : ((Math.random() < 0.5) ? EvolutionKits.TROLL : EvolutionKits.SLAYER))));
            }
            items.add(ItemID.removeItemID(ItemUtils.setAmount(getHeroicMysteryCrate(), 1)));
        }
        if (v == Version.DECEMBER_2017) {
            items.add(ItemUtils.buildItem(Material.SNOW_BLOCK, (short)0, ChatColor.translateAlternateColorCodes('&', "&f&lSpace   &b&l&nSnowman"), new String[] { ChatColor.GRAY + "Just your usual snowman made", ChatColor.GRAY + "out of... SPACE SNOW! Happy Holidays!" }));
            items.add(ItemUtils.setAmount(ShowcaseExpander.getShowcaseExpanderItem(), 3 + new Random().nextInt(2)));
            ItemRarity ir = ItemRarity.ULTIMATE;
            if (Math.random() < 0.3) {
                if (Math.random() < 0.15) {
                    ir = ItemRarity.GOD;
                }
                else {
                    ir = ItemRarity.LEGENDARY;
                }
            }
            items.add(ItemUtils.setAmount(RandomizationScroll.getRandomizationScroll(ir), 2 + new Random().nextInt(2)));
            final int x2 = new Random().nextInt(4);
            if (x2 == 0) {
                items.add(ItemUtils.getTitleVoucher("BabyItsColdOffline"));
            }
            if (x2 == 1) {
                items.add(ItemUtils.getTitleVoucher("#ChristmasIsComing"));
            }
            if (x2 == 2) {
                items.add(ItemUtils.getTitleVoucher("iHateHolidays"));
            }
            if (x2 == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher("Grinch"));
                }
                else {
                    items.add(ItemUtils.getTitleVoucher("DeckTheWalls"));
                }
            }
            if (Math.random() < 0.3) {
                items.add(ItemID.removeItemID(ItemUtils.setAmount(getHeroicMysteryCrate(), 1)));
            }
            else {
                items.add(ItemID.removeItemID(ItemUtils.setAmount(getMysteryCrate(), 1)));
            }
            if (Math.random() < 0.1) {
                if (Math.random() < 0.05) {
                    items.add(DungeonsAPI.get().getDimensionWeb());
                }
                else {
                    items.add(DungeonsAPI.get().getCosmonautFuel());
                }
            }
            else {
                items.add(DungeonsAPI.get().getEnchantedObsidian());
            }
            if (Math.random() < 0.3) {
                items.add(ItemUtils.setAmount(ItemUtils.getWhitescroll(), 1 + new Random().nextInt(3)));
            }
            else {
                items.add(ItemUtils.setAmount(ItemUtils.getBlackscroll(100), 1 + new Random().nextInt(3)));
            }
            items.add(ItemUtils.getMoneyNote("December 2017 Cosmic Crate", 2000000 + new Random().nextInt(18000001)));
            items.add(getCosmicCrate(Version.DECEMBER, "December 2017 Cosmic Crate"));
        }
        if (v == Version.HOLIDAY_2017) {
            items.add(ItemUtils.buildItem(Material.BOOK, (short)0, ChatColor.translateAlternateColorCodes('&', "&c&l&n2018&r &f&lPatch Notes"), new String[] { ChatColor.GRAY + "A list of all the updates and", ChatColor.GRAY + "changes planned for the anticipated", ChatColor.GRAY + "2018 IRL gameplay update." }));
            items.add(ItemUtils.setAmount(EssentialsAddHomes.getAddHomeItem(), 5));
            items.add(ItemUtils.setAmount(RandomizationScroll.getRandomizationScroll(ItemRarity.GOD), 1 + new Random().nextInt(4)));
            final int x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher("OP"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher("iHack"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher("Blacklisted"));
            }
            if (x == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher("Glitched"));
                }
                else {
                    items.add(ItemUtils.getTitleVoucher("RIP2k17"));
                }
            }
            items.add(ItemID.removeItemID(CosmicArmorSetsAPI.getArmorSet(ArmorSet.YETI)[new Random().nextInt(4)]));
            items.add(ItemID.removeItemID(ItemUtils.setAmount(getHeroicMysteryCrate(), 1 + new Random().nextInt(2))));
            items.add(ItemID.removeItemID(ItemUtils.setAmount(CosmicSpaceChestsAPI.getSpaceFlare(ItemRarity.GOD), 1 + new Random().nextInt(5))));
            items.add(ItemID.removeItemID(ItemUtils.setAmount(TieredEnchantments.getMysteryEnchantmentBook(7), 1 + new Random().nextInt(2))));
            int rankId2 = 2;
            if (Math.random() < 0.6) {
                rankId2 = 3;
                if (Math.random() < 0.25) {
                    rankId2 = 4;
                }
                if (Math.random() < 0.1) {
                    rankId2 = 5;
                }
            }
            items.add(CosmicRankQuestsAPI.getRankQuestItem(rankId2));
        }
        if (v == Version.JANUARY_2018) {
            items.add(ItemUtils.setAmount(ItemUtils.buildItem(Material.COOKIE, (short)0, ChatColor.translateAlternateColorCodes('&', "&8&lOld &a&lC&c&lh&a&lr&c&li&a&ls&c&lt&a&lm&c&la&a&ls &6&l&nCookies"), new String[] { ChatColor.GRAY + "An ancient relic of Christmas-past,", ChatColor.GRAY + "do you dare consume such a treasure?" }), 2));
            items.add(ItemUtils.setAmount(ItemUtils.getTransmogScroll(), 2 + new Random().nextInt(4)));
            items.add(ItemUtils.setAmount(LoreTag.getLoreTagItem(), 2 + new Random().nextInt(4)));
            final int x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher("2k18"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher("NewYearNewMe"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher("Still#Glitched"));
            }
            if (x == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher("IHaveATimeMachine"));
                }
                else {
                    items.add(ItemUtils.getTitleVoucher("ImStillIn2017"));
                }
            }
            items.add(ItemID.removeItemID(CosmicArmorSetsAPI.getArmorSet((Math.random() < 0.3) ? ArmorSet.YETI : ArmorSet.PHANTOM)[new Random().nextInt(4)]));
            items.add(ItemID.removeItemID(ItemUtils.setAmount(TieredEnchantments.getMysteryEnchantmentBook(7), 1)));
            if (Math.random() < 0.35) {
                items.add(ItemUtils.setAmount(EnchantmentOrb.getEnchantmentOrb(75 + new Random().nextInt(26), 12, OrbType.WEAPON), 1 + new Random().nextInt(2)));
            }
            else {
                items.add(ItemUtils.setAmount(EnchantmentOrb.getEnchantmentOrb(75 + new Random().nextInt(26), 12, OrbType.ARMOR), 1 + new Random().nextInt(2)));
            }
            items.add(ItemID.removeItemID(ItemUtils.setAmount(CosmicSpaceChestsAPI.getDropPackageChest((Math.random() < 0.2) ? ItemRarity.GOD : ItemRarity.LEGENDARY), 2 + new Random().nextInt(2))));
            items.add(getCosmicCrate((Math.random() < 0.25) ? Version.JANUARY_2017 : Version.JANURARY, "January 2018 Cosmic Crate"));
        }
        if (v == Version.FEBUARY_2018) {
            items.add(ItemUtils.setAmount(ItemUtils.buildItem(Material.FIRE, (short)0, ChatColor.translateAlternateColorCodes('&', "&b&lIcey &c&lFlame"), new String[] { ChatColor.GRAY + "Is it hot? Is it cold?", ChatColor.GRAY + "Touch it and find out!" }), 1));
            if (Math.random() < 0.4) {
                items.add(ItemUtils.setAmount(ItemUtils.getItemNametag(), 1 + new Random().nextInt(2)));
            }
            else {
                items.add(ItemUtils.setAmount(ItemUtils.getTransmogScroll(), 1 + new Random().nextInt(2)));
            }
            if (Math.random() < 0.4) {
                items.add(ItemUtils.setAmount(EssentialsAddHomes.getAddHomeItem(), 3 + new Random().nextInt(2)));
            }
            else {
                items.add(ItemUtils.setAmount(ShowcaseExpander.getShowcaseExpanderItem(), 3 + new Random().nextInt(2)));
            }
            final int x = new Random().nextInt(4);
            if (x == 0) {
                items.add(ItemUtils.getTitleVoucher("LoneWolf"));
            }
            if (x == 1) {
                items.add(ItemUtils.getTitleVoucher("PartyOf1"));
            }
            if (x == 2) {
                items.add(ItemUtils.getTitleVoucher("#69"));
            }
            if (x == 3) {
                if (Math.random() < 0.1) {
                    items.add(ItemUtils.getTitleVoucher("AbuseMe"));
                }
                else {
                    items.add(ItemUtils.getTitleVoucher("DupeMeDaddy"));
                }
            }
            int rankId2 = 3;
            if (Math.random() < 0.6) {
                rankId2 = 4;
                if (Math.random() < 0.1) {
                    rankId2 = 5;
                }
            }
            items.add(CosmicRankQuestsAPI.getRankQuestItem(rankId2));
            items.add(ItemID.removeItemID(CosmicArmorSetsAPI.getArmorSet((Math.random() < 0.1) ? ArmorSet.MOTHER_OF_YIJKI : ArmorSet.PHANTOM)[new Random().nextInt(4)]));
            if (Math.random() < 0.6) {
                items.add(KitItems.getKitItem(GlobalKits.values()[new Random().nextInt(GlobalKits.values().length)]));
            }
            else {
                items.add(EvolutionKitItems.getKitItem((Math.random() < 0.3) ? EvolutionKits.ALCHEMIST : ((Math.random() < 0.5) ? EvolutionKits.PHOENIX : ((Math.random() < 0.5) ? EvolutionKits.TROLL : EvolutionKits.SLAYER))));
            }
            items.add(ItemUtils.setAmount(MysteryMobSpawners.getMysteryMobSpawner(), 1 + new Random().nextInt(5)));
            if (Math.random() < 0.2) {
                items.add(getHeroicMysteryCrate());
            }
            else {
                items.add(getCosmicCrate(Version.FEBRUARY, "Febuary 2018 Cosmic Crate"));
            }
        }
        if (v.getCrateData() != null) {
            items = v.getCrateData().getCrateLoot(p);
        }
        return items;
    }
    
    private static int rand(final int low, final int high) {
        return new Random().nextInt(high - low) + low;
    }
}
