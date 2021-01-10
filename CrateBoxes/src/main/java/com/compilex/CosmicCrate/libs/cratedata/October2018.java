/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicDungeons.DungeonsAPI
 *  com.cosmicpvp.CosmicRankQuests.HeroicRankCrystalAPI
 *  com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI
 *  com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity
 *  com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.CustomItemUtils
 *  com.google.common.collect.Lists
 *  org.arkhamnetwork.Arkkit.patches.EquipmentLootboxes
 *  org.arkhamnetwork.Arkkit.patches.ItemID
 *  org.arkhamnetwork.Arkkit.patches.ItemNametag
 *  org.arkhamnetwork.Arkkit.patches.LoreTag
 *  org.arkhamnetwork.Arkkit.plugins.ArkPlugins
 *  org.arkhamnetwork.enchantments.EnchantmentOrb
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

import com.compilex.CosmicCrate.api.CCAPI;
import com.compilex.CosmicCrate.libs.CosmicCrateData;
import com.compilex.CosmicCrate.libs.Version;
import com.compilex.CosmicDungeons.DungeonsAPI;
import com.cosmicpvp.CosmicRankQuests.HeroicRankCrystalAPI;
import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI;
import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.CustomItemUtils;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import org.arkhamnetwork.Arkkit.patches.EquipmentLootboxes;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.Arkkit.patches.ItemNametag;
import org.arkhamnetwork.Arkkit.patches.LoreTag;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.arkhamnetwork.enchantments.EnchantmentOrb;
import org.arkhamnetwork.enchantments.libs.OrbType;
import org.arkhamnetwork.enchantments.util.TieredEnchantments;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class October2018
extends CosmicCrateData {
    private ChatColor mainColor = ChatColor.AQUA;
    private ChatColor secondColor = ChatColor.GOLD;

    @Override
    public List<String> getDescriptionLore(String username) {
        String bulletpoint = ChatColor.BOLD.toString() + "* ";
        return Lists.newArrayList((Object[])new String[]{(Object)this.mainColor + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)this.mainColor + " at " + (Object)this.secondColor + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Pumpkin Cake", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Limited Edition Titles", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "2-4x Item Nametags " + CC.YellowB + CC.Underline + "OR" + CC.Yellow + " Lore Crystals", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", CC.Gold + bulletpoint + CC.Gold + "1-2x Heroic G-Kit Generator", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1-2x Equipment Lootbox", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "2-4x /vkit Evolution Gems", CC.Gold + bulletpoint + CC.Gold + "1-3x Random Heroic Enchantment Books", CC.Gold + bulletpoint + CC.Gold + "2-3x Godly Space Chests", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "October 2016 " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " 2017 /cc", "", (Object)this.mainColor + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)this.mainColor + bulletpoint + (Object)this.mainColor + "1-2x 90% Armor/Weapon Enchant Orb (13 Slot)", (Object)this.mainColor + bulletpoint + (Object)this.mainColor + "Heroic Rank Crystal", (Object)this.mainColor + bulletpoint + (Object)this.mainColor + "1-3x Random Dungeon Portal"});
    }

    @Override
    public ItemStack getRandomMetaItem(Player player) {
        double chance = Math.random();
        if (chance <= 0.6) {
            return ItemUtils.setAmount((ItemStack)EnchantmentOrb.getEnchantmentOrb((int)90, (int)13, (OrbType)(this.random.nextInt(10) <= 6 ? OrbType.ARMOR : OrbType.WEAPON)), (int)(1 + this.random.nextInt(2)));
        }
        if (chance <= 0.9) {
            return this.setAmount(DungeonsAPI.get().getDungeon(this.random.nextBoolean() ? 1 : 0).getDungeonPortal(), 1 + (this.random.nextBoolean() ? this.random.nextInt(3) : 0));
        }
        return HeroicRankCrystalAPI.getHeroicRankCrystalItem();
    }

    @Override
    public List<ItemStack> getCrateLoot(Player player) {
        ArrayList items = Lists.newArrayList();
        items.add(ItemUtils.buildItem((Material)Material.PUMPKIN_PIE, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&e&lPumpkin Cake"), (String[])new String[]{(Object)ChatColor.GRAY + "A delicious cake that smells", CC.Gray + "of nutmeg and other Fall spices."}));
        int x = this.random.nextInt(4);
        if (x == 0) {
            items.add(ItemUtils.getTitleVoucher((String)"chilly"));
        }
        if (x == 1) {
            items.add(ItemUtils.getTitleVoucher((String)"CarveMePls"));
        }
        if (x == 2) {
            items.add(ItemUtils.getTitleVoucher((String)"WinterAlreadyCame"));
        }
        if (x == 3) {
            items.add(ItemUtils.getTitleVoucher((String)"#PumpkinSpiceIsNice"));
        }
        items.add(this.setAmount(this.random.nextBoolean() ? ItemNametag.getNametagItem() : LoreTag.getLoreTagItem(), 2 + this.random.nextInt(3)));
        items.add(this.setAmount(((EquipmentLootboxes)ArkPlugins.EQUIPMENT_LOOTBOX.getArkPlugin()).createHeroicGKitGenerator(false), 1 + this.random.nextInt(2)));
        items.add(this.setAmount(((EquipmentLootboxes)ArkPlugins.EQUIPMENT_LOOTBOX.getArkPlugin()).createLootboxItem(false), this.getRandomOne()));
        items.add(this.setAmount(TieredEnchantments.getMysteryEnchantmentBook((int)7), 1 + this.random.nextInt(3)));
        items.add(ItemID.removeItemID((ItemStack)ItemUtils.setAmount((ItemStack)CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.GOD), (int)(1 + this.getRandomOne()))));
        items.add(CCAPI.getCosmicCrate(Math.random() <= 0.65 ? Version.OCTOBER : Version.OCTOBER_2017, player.getName()));
        items.add(this.setAmount(CustomItemUtils.createRandomEvolutionKitGem(), 2 + this.random.nextInt(3)));
        return items;
    }

    private int getRandomOne() {
        return Math.random() <= 0.3 ? 2 : 1;
    }

    @Override
    public short getNonColorPane() {
        return DyeColor.ORANGE.getWoolData();
    }

    @Override
    public List<Color> getConclusionFireworkColors() {
        return Lists.newArrayList((Object[])new Color[]{Color.AQUA, Color.YELLOW});
    }

    @Override
    public short getColorPane() {
        return DyeColor.LIGHT_BLUE.getWoolData();
    }
}

