/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicArmorSets.CosmicArmorSetsAPI
 *  com.compilex.CosmicArmorSets.enums.ArmorSet
 *  com.compilex.CosmicArmorSets.enums.HeroicArmorType
 *  com.compilex.CosmicDungeons.DungeonsAPI
 *  com.compilex.CosmicDungeons.DungeonsRewardsAPI
 *  com.compilex.CosmicDungeons.dungeons.libs.Dungeon
 *  com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.google.common.collect.Lists
 *  me.ifamasssxd.cosmicdisguises.managers.CosmicDisguiseAPI
 *  org.arkhamnetwork.Arkkit.patches.EquipmentLootboxes
 *  org.arkhamnetwork.Arkkit.patches.ItemID
 *  org.arkhamnetwork.Arkkit.plugins.ArkPlugins
 *  org.arkhamnetwork.enchantments.EnchantmentBook
 *  org.arkhamnetwork.enchantments.util.TieredEnchantments
 *  org.bukkit.ChatColor
 *  org.bukkit.Color
 *  org.bukkit.DyeColor
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package com.compilex.CosmicCrate.libs.cratedata;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.compilex.CosmicArmorSets.enums.HeroicArmorType;
import com.compilex.CosmicCrate.api.CCAPI;
import com.compilex.CosmicCrate.libs.CosmicCrateData;
import com.compilex.CosmicCrate.libs.Version;
import com.compilex.CosmicDungeons.DungeonsAPI;
import com.compilex.CosmicDungeons.DungeonsRewardsAPI;
import com.compilex.CosmicDungeons.dungeons.libs.Dungeon;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import me.ifamasssxd.cosmicdisguises.managers.CosmicDisguiseAPI;
import org.arkhamnetwork.Arkkit.patches.EquipmentLootboxes;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import org.arkhamnetwork.enchantments.util.TieredEnchantments;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class July2018
extends CosmicCrateData {
    @Override
    public List<String> getDescriptionLore(String username) {
        String bulletpoint = ChatColor.BOLD.toString() + "* ";
        return Lists.newArrayList((Object[])new String[]{(Object)ChatColor.WHITE + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)ChatColor.WHITE + " at " + (Object)ChatColor.BLUE + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Summer Vibes", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Limited Edition Titles", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Blaze " + CC.YellowB + CC.Underline + "OR" + CC.Yellow + " Ghast Disguise", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", CC.Gold + bulletpoint + CC.Gold + "1-2x Equipment Lootbox", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1-2x Heroic " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " Superior Mystery Crate", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1-3x Random Heroic Enchantment", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1x Heroic Blackscroll " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " Holy Whitescroll", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1x July 2017 " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " 2016 CC", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1x Dungeon Portal Ingredient", "", ChatColor.BLUE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)ChatColor.BLUE + bulletpoint + (Object)ChatColor.BLUE + "Destroyed Outpost Dungeon Portal", (Object)ChatColor.BLUE + bulletpoint + (Object)ChatColor.BLUE + "Heroic Armor", (Object)ChatColor.BLUE + bulletpoint + (Object)ChatColor.BLUE + "Dimensional Traveler Set Piece", (Object)ChatColor.BLUE + bulletpoint + (Object)ChatColor.BLUE + "Abandoned Spaceship Lootbag"});
    }

    @Override
    public ItemStack getRandomMetaItem(Player player) {
        double chance = Math.random();
        if (chance <= 0.4) {
            return DungeonsRewardsAPI.getDungeonLootBag((Player)player, (Dungeon)DungeonsAPI.get().getDungeon(0));
        }
        if (chance <= 0.65) {
            return DungeonsAPI.get().getDungeon(1).getDungeonPortal();
        }
        if (chance <= 0.85) {
            return CosmicArmorSetsAPI.createHeroicPeice((HeroicArmorType)HeroicArmorType.values()[this.random.nextInt(HeroicArmorType.values().length)]);
        }
        return ArmorSet.DIMENSIONAL_TRAVELER.getRandomPeice(true);
    }

    @Override
    public List<ItemStack> getCrateLoot(Player player) {
        ArrayList items = Lists.newArrayList();
        items.add(ItemUtils.buildItem((Material)Material.SPECKLED_MELON, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&f&lSummer &a&n&oVibes"), (String[])new String[]{(Object)ChatColor.GRAY + "You cannot help it, school is", (Object)ChatColor.GRAY + "out. It's summer everyday, bro."}));
        items.add(CosmicDisguiseAPI.createRedeemableDisguise((String)(this.random.nextDouble() <= 0.75 ? "BLAZE" : "GHAST")));
        int x = this.random.nextInt(4);
        if (x == 0) {
            items.add(ItemUtils.getTitleVoucher((String)"#4thOfJuly"));
        }
        if (x == 1) {
            items.add(ItemUtils.getTitleVoucher((String)"#Fireworks"));
        }
        if (x == 2) {
            items.add(ItemUtils.getTitleVoucher((String)"LightUpMyWorld"));
        }
        if (x == 3) {
            if (Math.random() < 0.1) {
                items.add(ItemUtils.getTitleVoucher((String)"#CosmicNChill"));
            } else {
                items.add(ItemUtils.getTitleVoucher((String)"#Summer2018"));
            }
        }
        items.add(ItemUtils.setAmount((ItemStack)((EquipmentLootboxes)ArkPlugins.EQUIPMENT_LOOTBOX.getArkPlugin()).createLootboxItem(false), (int)this.getRandomOne()));
        items.add(ItemUtils.setAmount((ItemStack)(this.random.nextDouble() <= 0.75 ? CCAPI.getHeroicMysteryCrate() : CCAPI.getGodlyCosmicCrate()), (int)this.getRandomOne()));
        items.add(ItemID.removeItemID((ItemStack)ItemUtils.setAmount((ItemStack)TieredEnchantments.getMysteryEnchantmentBook((int)7), (int)(this.random.nextDouble() <= 0.4 ? this.random.nextInt(3) + 1 : this.random.nextInt(2) + 1))));
        items.add(this.random.nextBoolean() ? DungeonsRewardsAPI.getHolyWhiteScroll() : EnchantmentBook.getHeroicBlackscroll((boolean)true));
        items.add(CCAPI.getCosmicCrate(Math.random() <= 0.65 ? Version.JULY : Version.JULY_2017, player.getName()));
        if (Math.random() < 0.25) {
            if (Math.random() <= 0.25) {
                if (Math.random() < 0.05) {
                    items.add(ItemUtils.setAmount((ItemStack)DungeonsAPI.get().getDimensionWeb().clone(), (int)1));
                } else {
                    items.add(ItemUtils.setAmount((ItemStack)DungeonsAPI.get().getCosmonautFuel().clone(), (int)1));
                }
            } else if (Math.random() <= 0.25) {
                items.add(ItemUtils.setAmount((ItemStack)DungeonsAPI.get().getInterdimensionalCore().clone(), (int)1));
            } else {
                items.add(ItemUtils.setAmount((ItemStack)DungeonsAPI.get().getTeleportationCrystal().clone(), (int)1));
            }
        } else {
            items.add(ItemUtils.setAmount((ItemStack)DungeonsAPI.get().getEnchantedObsidian().clone(), (int)1));
        }
        return items;
    }

    private int getRandomOne() {
        return Math.random() <= 0.3 ? 2 : 1;
    }

    @Override
    public short getNonColorPane() {
        return DyeColor.WHITE.getWoolData();
    }

    @Override
    public List<Color> getConclusionFireworkColors() {
        return Lists.newArrayList((Object[])new Color[]{Color.RED, Color.BLUE});
    }

    @Override
    public short getColorPane() {
        return DyeColor.RED.getWoolData();
    }
}

