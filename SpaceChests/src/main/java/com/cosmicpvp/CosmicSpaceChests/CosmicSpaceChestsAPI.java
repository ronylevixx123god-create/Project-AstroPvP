/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.struct.factions.points.FactionPointUtils
 *  com.cosmicpvp.cosmicutils.utils.WorldGuardUtils
 *  com.rit.sucy.CustomEnchantment
 *  org.arkhamnetwork.Arkkit.patches.ItemID
 *  org.arkhamnetwork.Arkkit.plugins.ArkPlugins
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.entity.FallingBlock
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package com.cosmicpvp.CosmicSpaceChests;

import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsPlugin;
import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import com.cosmicpvp.CosmicSpaceChests.libs.LootGenerator;
import com.cosmicpvp.CosmicSpaceChests.utils.CustomEnchants;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import com.cosmicpvp.CosmicSpaceChests.utils.LocationUtils;
import com.cosmicpvp.cosmicutils.struct.factions.points.FactionPointUtils;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import com.rit.sucy.CustomEnchantment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class CosmicSpaceChestsAPI {
    private static short WHITE_STAINED_GLASS = 0;
    private static short GREEN_STAINED_GLASS = (short)5;
    private static short BLUE_STAINED_GLASS = (short)3;
    private static short YELLOW_STAINED_GLASS = (short)4;
    private static short GOLD_STAINED_GLASS = 1;
    private static short RED_STAINED_GLASS = (short)14;

    public static boolean giveDropPackageChest(Player ply, ItemRarity ir) {
        if (ply.getInventory().firstEmpty() == -1) {
            return false;
        }
        ChatColor cc = CosmicSpaceChestsAPI.getColor(ir);
        ItemStack is = CosmicSpaceChestsAPI.getDropPackageChest(ir);
        ply.getInventory().addItem(new ItemStack[]{is});
        ply.sendMessage((Object)ChatColor.GRAY + "You got a " + (Object)cc + (Object)ChatColor.UNDERLINE + ir.name + " Cosmic Chest!");
        ply.updateInventory();
        return true;
    }

    public static ItemStack getDropPackageChest(ItemRarity ir) {
        ChatColor cc = CosmicSpaceChestsAPI.getColor(ir);
        ItemStack is = new ItemStack(Material.CHEST, 1);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName((Object)cc + ChatColor.BOLD.toString() + ir.name + " Cosmic Chest" + (Object)ChatColor.GRAY + " (Right Click)");
        ArrayList<String> lore = new ArrayList<String>(Arrays.asList((Object)ChatColor.GRAY + "A cache of equipment packaged by", (Object)ChatColor.GRAY + "the Intergalactic Cosmonaut Station.", "", (Object)ChatColor.GRAY + "Contains " + (Object)cc + (Object)ChatColor.UNDERLINE + "5 " + ir.name + " rarity" + (Object)ChatColor.GRAY + " items..."));
        if (ir == ItemRarity.SIMPLE) {
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Vanilla Equipment");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Vanilla Potions");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Sheep Spawners");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Food");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Buckets");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Exploding Cake");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "$10,000 Money Notes");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "1,000XP Bottles");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Simple Player Titles");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Simple Secret Dust");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Simple Enchantment Book");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Simple Randomization Scroll");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Tier #1 Rank Quest");
        }
        if (ir == ItemRarity.UNIQUE) {
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Unique Equipment");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Unique Custom TNT");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Pig/Chicken Spawners");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Hopper");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Cosmic Crackers");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "10 Level Skill Vouchers");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "5 Level mcMMO Voucher");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "$50,000 Money Notes");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "2,000-5,000XP Bottles");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Unique Player Titles");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Unique Secret Dust");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Unique Enchantment Book");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Simple/Unique Kill Tracker");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Unique Randomzation Scroll");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Tier #2 Rank Quest");
            lore.add((Object)cc + "... and more!");
        }
        if (ir == ItemRarity.ELITE) {
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Elite Equipment");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Elite Custom TNT");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Creeper Eggs");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Hoppers");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Zombie, Skeleton Spawners");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Spider, Wolf Spawners");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "30 Level (Secondary) Skill Vouchers");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "15 Level mcMMO Voucher");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "$100,000 Money Notes");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "5,000-20,000XP Bottles");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Elite Player Titles");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Elite Secret Dust");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Elite Enchantment Book");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Elite Randomzation Scroll");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Elite /gkit Fallen Heroes");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Tier #3 Rank Quest");
            lore.add((Object)cc + "... and more!");
        }
        if (ir == ItemRarity.ULTIMATE) {
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Ultimate Equipment");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Ultimate /gkit Items");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Ultimate Boss Eggs");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "30 Level (Primary) Skill Vouchers");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "25 Level mcMMO Voucher");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "$250,000 Money Notes");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "25,000-75,000XP Bottles");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Item Nametags");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Transmog Scrolls");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "White & 50% Black Scrolls");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Blaze/IG Spawners");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Mystery Soul Book");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Mystery Mob Spawners");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "XP Boosters");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Ultimate /gkit Fallen Heroes");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Custom Creeper Eggs");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Tier #4 Rank Quest");
            lore.add((Object)cc + "... and more!");
        }
        if (ir == ItemRarity.LEGENDARY) {
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Legendary Equipment");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Legendary /gkit Items");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Legendary Boss Eggs");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "75 Level (Primary) Skill Vouchers");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "50 Level mcMMO Voucher");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "$1,000,000 Money Notes");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "50,000-100,000XP Bottles");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "White & 75% Black Scrolls");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Cosmic Envoy Summon");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Nether Mob Spawners");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Lucky Creepers/TNT");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Legendary Player Titles");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Mystery Mob Spawners");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "XP Boosters");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "10 Slot Enchantment Orbs");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "/gkit + /vkit Fallen Heroes");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Godly Space Flare");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Showcase Expander");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Home Increaser");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Tier #5 Rank Quest");
            lore.add((Object)cc + "... and more!");
        }
        if (ir == ItemRarity.GOD) {
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Godly Equipment");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Godly /gkit Items");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Yijki / Plague Bloater Boss Eggs");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "200 Level mcMMO Voucher");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "$4,000,000 Money Notes");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "100,000-200,000XP Bottles");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Legendary Enchantment Books");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Cosmic Envoy Summon");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Nether Mob Spawners");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Lucky Creepers/TNT");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Mystery Mob Spawners");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Godly Randomization Scrolls");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "11 Slot Enchantment Orbs");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "/gkit + /vkit Fallen Heroes");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Soul Enchantment Books");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Faction Crystals");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Depth Strider Books");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Mystery Cosmic Crate");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Tier #5 Rank Quest");
            lore.add((Object)cc + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Heroic Rank Crystal");
            lore.add((Object)cc + "... and much more!");
        }
        im.setLore(lore);
        is.setItemMeta(im);
        if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning()) {
            ItemID.setItemID((ItemStack)is, (long)ItemID.generateNewItemID());
        }
        return is;
    }

    public static boolean isDropPackageChest(ItemStack is) {
        if (!(is != null && is.getType() == Material.CHEST && is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().hasLore())) {
            return false;
        }
        return ((String)is.getItemMeta().getLore().get(0)).equals((Object)ChatColor.GRAY + "A cache of equipment packaged by") || ((String)is.getItemMeta().getLore().get(0)).equals((Object)ChatColor.GRAY + "A supply drop full of loot!");
    }

    public static ItemStack getSpaceFlare(ItemRarity ir) {
        ChatColor cc = CosmicSpaceChestsAPI.getColor(ir);
        ItemStack is = new ItemStack(Material.TORCH, 1);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName((Object)cc + ChatColor.BOLD.toString() + ir.name + " Space Flare" + (Object)ChatColor.GRAY + " (Right Click)");
        im.setLore(new ArrayList<String>(Arrays.asList((Object)ChatColor.GRAY + "Drops a cache of equipment packaged by", (Object)ChatColor.GRAY + "the " + (Object)ChatColor.UNDERLINE + "Intergalactic Cosmonaut Station.", (Object)ChatColor.GRAY + "near your character's current position.")));
        is.setItemMeta(im);
        return is;
    }

    public static boolean isSpaceFlare(ItemStack is) {
        if (!(is != null && is.getType() == Material.TORCH && is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().hasLore())) {
            return false;
        }
        return ((String)is.getItemMeta().getLore().get(0)).equals((Object)ChatColor.GRAY + "Drops a cache of equipment packaged by");
    }

    public static ItemRarity getItemRarityByColor(ItemStack i) {
        if (i == null || !i.hasItemMeta() || !i.getItemMeta().hasDisplayName()) {
            return ItemRarity.UNKNOWN;
        }
        String itemName = i.getItemMeta().getDisplayName();
        if (itemName.startsWith(ChatColor.WHITE.toString()) || itemName.startsWith(ChatColor.GRAY.toString())) {
            return ItemRarity.SIMPLE;
        }
        if (itemName.startsWith(ChatColor.GREEN.toString())) {
            return ItemRarity.UNIQUE;
        }
        if (itemName.startsWith(ChatColor.AQUA.toString())) {
            return ItemRarity.ELITE;
        }
        if (itemName.startsWith(ChatColor.YELLOW.toString())) {
            return ItemRarity.ULTIMATE;
        }
        if (itemName.startsWith(ChatColor.GOLD.toString())) {
            return ItemRarity.LEGENDARY;
        }
        if (itemName.startsWith(ChatColor.RED.toString())) {
            return ItemRarity.GOD;
        }
        return ItemRarity.UNKNOWN;
    }

    public static int getEmptyInventorySlots(Player pl) {
        int empty_slots = 0;
        for (ItemStack is : pl.getInventory()) {
            if (is != null && is.getType() != Material.AIR) continue;
            ++empty_slots;
        }
        return empty_slots;
    }

    public static ItemRarity getItemRarity(int i) {
        if (i == 0) {
            return ItemRarity.UNKNOWN;
        }
        if (i == 1) {
            return ItemRarity.SIMPLE;
        }
        if (i == 2) {
            return ItemRarity.UNIQUE;
        }
        if (i == 3) {
            return ItemRarity.ELITE;
        }
        if (i == 4) {
            return ItemRarity.ULTIMATE;
        }
        if (i == 5) {
            return ItemRarity.LEGENDARY;
        }
        if (i == 6) {
            return ItemRarity.GOD;
        }
        return null;
    }

    public static int getItemRarity(ItemRarity ir) {
        if (ir == ItemRarity.UNKNOWN) {
            return 0;
        }
        if (ir == ItemRarity.SIMPLE) {
            return 1;
        }
        if (ir == ItemRarity.UNIQUE) {
            return 2;
        }
        if (ir == ItemRarity.ELITE) {
            return 3;
        }
        if (ir == ItemRarity.ULTIMATE) {
            return 4;
        }
        if (ir == ItemRarity.LEGENDARY) {
            return 5;
        }
        if (ir == ItemRarity.GOD) {
            return 6;
        }
        return 1;
    }

    public static ChatColor getColor(ItemRarity ir) {
        if (ir == ItemRarity.SIMPLE) {
            return ChatColor.WHITE;
        }
        if (ir == ItemRarity.UNIQUE) {
            return ChatColor.GREEN;
        }
        if (ir == ItemRarity.ELITE) {
            return ChatColor.AQUA;
        }
        if (ir == ItemRarity.ULTIMATE) {
            return ChatColor.YELLOW;
        }
        if (ir == ItemRarity.LEGENDARY) {
            return ChatColor.GOLD;
        }
        if (ir == ItemRarity.GOD || ir == ItemRarity.SOUL) {
            return ChatColor.RED;
        }
        return ChatColor.RESET;
    }

    public static boolean isEmpty(Inventory inv) {
        int i = 0;
        for (ItemStack is : inv.getContents()) {
            if (is == null || is.getType() == Material.AIR) continue;
            ++i;
        }
        return i <= 0;
    }

    public static int getEmptySlots(Inventory inv) {
        int i = 0;
        for (ItemStack is : inv.getContents()) {
            if (is != null && is.getType() != Material.AIR) continue;
            ++i;
        }
        return i;
    }

    public static ItemRarity getRandomItemRarity() {
        int x = new Random().nextInt(1000);
        if (x <= 500) {
            return ItemRarity.SIMPLE;
        }
        if (x <= 750) {
            return ItemRarity.UNIQUE;
        }
        if (x <= 950) {
            return ItemRarity.ELITE;
        }
        if (x <= 998) {
            return ItemRarity.ULTIMATE;
        }
        return ItemRarity.LEGENDARY;
    }

    public static ItemRarity getRandomSlotRarity() {
        int x = new Random().nextInt(1000);
        if (x <= 500) {
            return ItemRarity.SIMPLE;
        }
        if (x <= 750) {
            return ItemRarity.UNIQUE;
        }
        if (x <= 960) {
            return ItemRarity.ELITE;
        }
        if (x <= 990) {
            return ItemRarity.ULTIMATE;
        }
        return ItemRarity.LEGENDARY;
    }

    public static ItemStack getRandomStainedGlassItem(ItemRarity chestRarity) {
        ItemRarity ir = CosmicSpaceChestsAPI.getRandomSlotRarity();
        ItemStack i = ItemUtils.buildItem(Material.STAINED_GLASS_PANE, (short)0, CosmicSpaceChestsAPI.getColor(ir).toString() + ChatColor.BOLD.toString() + ir.name + " Mystery Item", (Object)ChatColor.GRAY + "Click here to reveal a", CosmicSpaceChestsAPI.getColor(ir).toString() + ir.name + (Object)ChatColor.GRAY + " item from the " + CosmicSpaceChestsAPI.getColor(chestRarity).toString() + chestRarity.name, (Object)ChatColor.GRAY + "loot table.", "", CosmicSpaceChestsAPI.getColor(ir).toString() + ChatColor.BOLD.toString() + "+" + (int)(100.0 * (1.0 - LootGenerator.getSlotRarityModifier(ir))) + "%" + CosmicSpaceChestsAPI.getColor(ir).toString() + " Rare Loot Chance");
        if (ir == ItemRarity.SIMPLE) {
            i.setDurability(WHITE_STAINED_GLASS);
        }
        if (ir == ItemRarity.UNIQUE) {
            i.setDurability(GREEN_STAINED_GLASS);
        }
        if (ir == ItemRarity.ELITE) {
            i.setDurability(BLUE_STAINED_GLASS);
        }
        if (ir == ItemRarity.ULTIMATE) {
            i.setDurability(YELLOW_STAINED_GLASS);
        }
        if (ir == ItemRarity.LEGENDARY) {
            i.setDurability(GOLD_STAINED_GLASS);
        }
        if (ir == ItemRarity.GOD) {
            i.setDurability(RED_STAINED_GLASS);
        }
        return i;
    }

    public static ItemRarity getStainedGlassRarity(ItemStack i) {
        if (i.getDurability() == WHITE_STAINED_GLASS) {
            return ItemRarity.SIMPLE;
        }
        if (i.getDurability() == GREEN_STAINED_GLASS) {
            return ItemRarity.UNIQUE;
        }
        if (i.getDurability() == BLUE_STAINED_GLASS) {
            return ItemRarity.ELITE;
        }
        if (i.getDurability() == YELLOW_STAINED_GLASS) {
            return ItemRarity.ULTIMATE;
        }
        if (i.getDurability() == GOLD_STAINED_GLASS) {
            return ItemRarity.LEGENDARY;
        }
        if (i.getDurability() == RED_STAINED_GLASS) {
            return ItemRarity.GOD;
        }
        return ItemRarity.SIMPLE;
    }

    public static List<ItemStack> getEnvoyItems(ItemRarity ir, int amount) {
        ItemStack factionPoints;
        ArrayList<ItemStack> li = new ArrayList<ItemStack>();
        while (li.size() < amount) {
            ItemStack is;
            block9: {
                is = null;
                ItemRarity slotRarity = CosmicSpaceChestsAPI.getRandomItemRarity();
                try {
                    if (ir == ItemRarity.SIMPLE) {
                        is = LootGenerator.getRandomCommonLoot(slotRarity);
                    }
                    if (ir == ItemRarity.UNIQUE) {
                        is = LootGenerator.getRandomUncommonLoot(slotRarity);
                    }
                    if (ir == ItemRarity.ELITE) {
                        is = LootGenerator.getRandomRareLoot(slotRarity);
                    }
                    if (ir == ItemRarity.ULTIMATE) {
                        is = LootGenerator.getRandomUltraRareLoot(slotRarity);
                    }
                    if (ir == ItemRarity.LEGENDARY) {
                        is = LootGenerator.getRandomLegendaryLoot(slotRarity);
                    }
                    if (ir != ItemRarity.GOD) break block9;
                    is = LootGenerator.getRandomGodLoot(slotRarity);
                }
                catch (Exception err) {
                    err.printStackTrace();
                    continue;
                }
            }
            if (is == null) continue;
            li.add(is);
        }
        if ((ir == ItemRarity.LEGENDARY || ir == ItemRarity.GOD) && Math.random() <= 0.05 && (factionPoints = FactionPointUtils.getFactionPointsItem(null, null, (String)"space_chest", (Object)((Object)ir))) != null) {
            li.add(factionPoints);
        }
        return li;
    }

    public static List<ItemStack> getItemList(ItemRarity ir, int amount) {
        ArrayList<ItemStack> li = new ArrayList<ItemStack>();
        while (li.size() < amount) {
            ItemStack is;
            block8: {
                is = null;
                ItemRarity slotRarity = CosmicSpaceChestsAPI.getRandomItemRarity();
                try {
                    if (ir == ItemRarity.SIMPLE) {
                        is = LootGenerator.getRandomCommonLoot(slotRarity);
                    }
                    if (ir == ItemRarity.UNIQUE) {
                        is = LootGenerator.getRandomUncommonLoot(slotRarity);
                    }
                    if (ir == ItemRarity.ELITE) {
                        is = LootGenerator.getRandomRareLoot(slotRarity);
                    }
                    if (ir == ItemRarity.ULTIMATE) {
                        is = LootGenerator.getRandomUltraRareLoot(slotRarity);
                    }
                    if (ir == ItemRarity.LEGENDARY) {
                        is = LootGenerator.getRandomLegendaryLoot(slotRarity);
                    }
                    if (ir != ItemRarity.GOD) break block8;
                    is = LootGenerator.getRandomGodLoot(slotRarity);
                }
                catch (Exception err) {
                    err.printStackTrace();
                    continue;
                }
            }
            if (is == null) continue;
            li.add(is);
        }
        return li;
    }

    public static ItemStack[] getItems(ItemRarity ir, int amount) {
        List<ItemStack> retr = CosmicSpaceChestsAPI.getItemList(ir, amount);
        return retr.toArray((T[])new ItemStack[retr.size()]);
    }

    public static ItemStack[] getItems(ItemRarity chestRarity, ItemRarity slotRarity, int amount) {
        ItemRarity ir = chestRarity;
        ArrayList<ItemStack> li = new ArrayList<ItemStack>();
        while (li.size() < amount) {
            ItemStack is;
            block8: {
                is = null;
                try {
                    if (chestRarity == ItemRarity.SIMPLE) {
                        is = LootGenerator.getRandomCommonLoot(slotRarity);
                    }
                    if (chestRarity == ItemRarity.UNIQUE) {
                        is = LootGenerator.getRandomUncommonLoot(slotRarity);
                    }
                    if (chestRarity == ItemRarity.ELITE) {
                        is = LootGenerator.getRandomRareLoot(slotRarity);
                    }
                    if (chestRarity == ItemRarity.ULTIMATE) {
                        is = LootGenerator.getRandomUltraRareLoot(slotRarity);
                    }
                    if (chestRarity == ItemRarity.LEGENDARY) {
                        is = LootGenerator.getRandomLegendaryLoot(slotRarity);
                    }
                    if (chestRarity != ItemRarity.GOD) break block8;
                    is = LootGenerator.getRandomGodLoot(slotRarity);
                }
                catch (Exception err) {
                    err.printStackTrace();
                    continue;
                }
            }
            if (is == null) continue;
            li.add(is);
        }
        return li.toArray((T[])new ItemStack[li.size()]);
    }

    public static void spawnCustomEnvoy(Location center, int radius, int amountToSpawn, ItemRarity chestRarity) {
        HashSet<Location> validDropLocations = new HashSet<Location>();
        for (int attempts = 0; validDropLocations.size() < amountToSpawn && attempts < 8000; ++attempts) {
            String regionName;
            Location randomNearby = LocationUtils.getNearbyLocation(center, radius, Math.max(radius / 3, 1), 10);
            if (randomNearby == null || (regionName = WorldGuardUtils.getRegionName((Location)randomNearby)).startsWith("spawn_")) continue;
            validDropLocations.add(randomNearby);
        }
        validDropLocations.forEach(loc -> CosmicSpaceChestsAPI.dropChest(loc, chestRarity == null ? CosmicSpaceChestsAPI.getRandomItemRarity() : chestRarity));
        Bukkit.getLogger().info("Dropping: " + validDropLocations.size() + " envoy chests around: " + center.getX() + ", " + center.getY() + ", " + center.getZ() + " desired: " + amountToSpawn + " rarity: " + (Object)((Object)chestRarity));
    }

    public static CustomEnchantment getRandomCustomEnchant(Material m) {
        if (m.name().toLowerCase().contains("sword")) {
            return CustomEnchants.SwordCustomEnchants.values()[new Random().nextInt((int)CustomEnchants.SwordCustomEnchants.values().length)].enchant;
        }
        if (m.name().toLowerCase().contains("axe")) {
            return CustomEnchants.AxeCustomEnchants.values()[new Random().nextInt((int)CustomEnchants.AxeCustomEnchants.values().length)].enchant;
        }
        if (m.name().toLowerCase().contains("bow")) {
            return CustomEnchants.BowCustomEnchants.values()[new Random().nextInt((int)CustomEnchants.BowCustomEnchants.values().length)].enchant;
        }
        if (m.name().toLowerCase().contains("boots") || m.name().toLowerCase().contains("chestplate") || m.name().toLowerCase().contains("leggings") || m.name().toLowerCase().contains("helmet")) {
            if (m.name().toLowerCase().contains("boots") && new Random().nextBoolean()) {
                return CustomEnchants.BootCustomEnchants.values()[new Random().nextInt((int)CustomEnchants.BootCustomEnchants.values().length)].enchant;
            }
            if (m.name().toLowerCase().contains("helmet") && new Random().nextBoolean()) {
                return CustomEnchants.HelmetCustomEnchants.values()[new Random().nextInt((int)CustomEnchants.HelmetCustomEnchants.values().length)].enchant;
            }
            return CustomEnchants.ArmorCustomEnchants.values()[new Random().nextInt((int)CustomEnchants.ArmorCustomEnchants.values().length)].enchant;
        }
        if (m.name().toLowerCase().contains("hoe") || m.name().toLowerCase().contains("spade") || m.name().toLowerCase().contains("pickaxe")) {
            return CustomEnchants.ToolCustomEnchants.values()[new Random().nextInt((int)CustomEnchants.ToolCustomEnchants.values().length)].enchant;
        }
        return null;
    }

    public static void convertDropPackages(Player p) {
        for (int i = 0; i < 36; ++i) {
            ItemStack is = p.getInventory().getItem(i);
            if (is == null || is.getType() != Material.CHEST || !is.hasItemMeta() || !is.getItemMeta().hasDisplayName() || !is.getItemMeta().hasLore() || !((String)is.getItemMeta().getLore().get(0)).equals((Object)ChatColor.GRAY + "A supply drop full of loot!")) continue;
            Bukkit.getLogger().info(p.getName() + " => + " + is.getItemMeta().getDisplayName() + " in slot " + i);
            String chestName = is.getItemMeta().getDisplayName();
            ItemRarity ir = null;
            if (chestName.startsWith(ChatColor.WHITE.toString())) {
                ir = ItemRarity.SIMPLE;
            }
            if (chestName.startsWith(ChatColor.GREEN.toString())) {
                ir = ItemRarity.UNIQUE;
            }
            if (chestName.startsWith(ChatColor.AQUA.toString())) {
                ir = ItemRarity.ELITE;
            }
            if (chestName.startsWith(ChatColor.YELLOW.toString())) {
                ir = ItemRarity.ULTIMATE;
            }
            if (chestName.startsWith(ChatColor.GOLD.toString())) {
                ir = ItemRarity.LEGENDARY;
            }
            if (chestName.startsWith(ChatColor.RED.toString())) {
                ir = ItemRarity.GOD;
            }
            if (ir == null) continue;
            p.getInventory().setItem(i, ItemUtils.setAmount(CosmicSpaceChestsAPI.getDropPackageChest(ir), is.getAmount()));
        }
    }

    public static void convertDropPackages(Inventory inv) {
        for (int i = 0; i < inv.getSize(); ++i) {
            ItemStack is = inv.getItem(i);
            if (is == null || is.getType() != Material.CHEST || !is.hasItemMeta() || !is.getItemMeta().hasDisplayName() || !is.getItemMeta().hasLore() || !((String)is.getItemMeta().getLore().get(0)).equals((Object)ChatColor.GRAY + "A supply drop full of loot!")) continue;
            String chestName = is.getItemMeta().getDisplayName();
            ItemRarity ir = null;
            if (chestName.startsWith(ChatColor.WHITE.toString())) {
                ir = ItemRarity.SIMPLE;
            }
            if (chestName.startsWith(ChatColor.GREEN.toString())) {
                ir = ItemRarity.UNIQUE;
            }
            if (chestName.startsWith(ChatColor.AQUA.toString())) {
                ir = ItemRarity.ELITE;
            }
            if (chestName.startsWith(ChatColor.YELLOW.toString())) {
                ir = ItemRarity.ULTIMATE;
            }
            if (chestName.startsWith(ChatColor.GOLD.toString())) {
                ir = ItemRarity.LEGENDARY;
            }
            if (chestName.startsWith(ChatColor.RED.toString())) {
                ir = ItemRarity.GOD;
            }
            if (ir == null) continue;
            inv.setItem(i, ItemUtils.setAmount(CosmicSpaceChestsAPI.getDropPackageChest(ir), is.getAmount()));
        }
    }

    public static void dropChest(Location l) {
        CosmicSpaceChestsPlugin.log.debug("(DE) Falling chest spawning near " + l.toString(), CosmicSpaceChestsPlugin.class);
        FallingBlock e = l.getWorld().spawnFallingBlock(l, Material.CHEST.getId(), (byte)0);
        e.setMetadata("do_not_clear", (MetadataValue)new FixedMetadataValue((Plugin)CosmicSpaceChestsPlugin.plugin, (Object)true));
    }

    public static void dropChest(Location l, ItemRarity chestRarity) {
        CosmicSpaceChestsPlugin.log.debug("(DE) Falling chest spawning near " + l.toString(), CosmicSpaceChestsPlugin.class);
        FallingBlock e = l.getWorld().spawnFallingBlock(l, Material.CHEST.getId(), (byte)0);
        e.setMetadata("do_not_clear", (MetadataValue)new FixedMetadataValue((Plugin)CosmicSpaceChestsPlugin.plugin, (Object)true));
        e.setMetadata("chestRarity", (MetadataValue)new FixedMetadataValue((Plugin)CosmicSpaceChestsPlugin.plugin, (Object)chestRarity.name()));
    }

    public static boolean dropChestSpaceFlare(Location l, ItemRarity ir, int chests) {
        Block b = l.getWorld().getHighestBlockAt(l).getLocation().subtract(0.0, 0.5, 0.0).getBlock();
        final Material type = b.getType();
        if (type == Material.WATER || type == Material.STATIONARY_WATER || type == Material.LAVA || type == Material.STATIONARY_LAVA) {
            return false;
        }
        CosmicSpaceChestsPlugin.log.debug("(DE) Falling chest spawning near " + l.toString() + ", highest block: " + b.toString(), CosmicSpaceChestsPlugin.class);
        if (type == Material.YELLOW_FLOWER || type == Material.RED_ROSE || type == Material.DEAD_BUSH || type == Material.DOUBLE_PLANT || type == Material.STEP || type == Material.RED_MUSHROOM || type == Material.BROWN_MUSHROOM) {
            final Location bL = b.getLocation().clone();
            Bukkit.getScheduler().runTaskLater((Plugin)CosmicSpaceChestsPlugin.plugin, new Runnable(){

                @Override
                public void run() {
                    bL.getBlock().setType(type);
                }
            }, 6000L);
            b.setType(Material.AIR);
        }
        FallingBlock e = l.getWorld().spawnFallingBlock(l, Material.CHEST.getId(), (byte)0);
        e.setMetadata("chestRarity", (MetadataValue)new FixedMetadataValue((Plugin)CosmicSpaceChestsPlugin.plugin, (Object)ir.name()));
        e.setMetadata("spaceFlare", (MetadataValue)new FixedMetadataValue((Plugin)CosmicSpaceChestsPlugin.plugin, (Object)true));
        e.setMetadata("spaceFlareChests", (MetadataValue)new FixedMetadataValue((Plugin)CosmicSpaceChestsPlugin.plugin, (Object)chests));
        e.setMetadata("do_not_clear", (MetadataValue)new FixedMetadataValue((Plugin)CosmicSpaceChestsPlugin.plugin, (Object)true));
        return true;
    }
}

