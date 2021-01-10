/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicDungeons.DungeonsAPI
 *  com.compilex.CosmicDungeons.DungeonsRewardsAPI
 *  com.compilex.CosmicDungeons.dungeons.libs.Dungeon
 *  com.cosmicpvp.CosmicRankQuests.CosmicRankQuestsAPI
 *  com.cosmicpvp.CosmicSpaceChests.libs.EvolutionKitItems
 *  com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity
 *  com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.google.common.collect.Lists
 *  me.cosmicpvp.CosmicGlobalKits.libs.FallenHero
 *  me.cosmicpvp.CosmicGlobalKits.libs.GlobalKits
 *  me.ifamasssxd.cosmicdisguises.managers.CosmicDisguiseAPI
 *  me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKit
 *  me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKits
 *  org.arkhamnetwork.Arkkit.patches.ItemID
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

import com.compilex.CosmicCrate.api.CCAPI;
import com.compilex.CosmicCrate.libs.CosmicCrateData;
import com.compilex.CosmicCrate.libs.Version;
import com.compilex.CosmicDungeons.DungeonsAPI;
import com.compilex.CosmicDungeons.DungeonsRewardsAPI;
import com.compilex.CosmicDungeons.dungeons.libs.Dungeon;
import com.cosmicpvp.CosmicRankQuests.CosmicRankQuestsAPI;
import com.cosmicpvp.CosmicSpaceChests.libs.EvolutionKitItems;
import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import me.cosmicpvp.CosmicGlobalKits.libs.FallenHero;
import me.cosmicpvp.CosmicGlobalKits.libs.GlobalKits;
import me.ifamasssxd.cosmicdisguises.managers.CosmicDisguiseAPI;
import me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKit;
import me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKits;
import org.arkhamnetwork.Arkkit.patches.ItemID;
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

public class April2018
extends CosmicCrateData {
    @Override
    public List<String> getDescriptionLore(String username) {
        String bulletpoint = ChatColor.BOLD.toString() + "* ";
        return Lists.newArrayList((Object[])new String[]{(Object)ChatColor.LIGHT_PURPLE + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)ChatColor.LIGHT_PURPLE + " at " + (Object)ChatColor.YELLOW + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Chocolate Bunny", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Limited Edition Titles", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Mushroom Cow " + CC.YellowB + CC.Underline + "OR" + CC.Yellow + " Pig Zombie Disguise", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "2-3x Legendary+ Randomization Scrolls", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1-2x 100% Armor/Weapon Enchantment Orb (" + (Object)ChatColor.GOLD + (Object)ChatColor.BOLD + "12 Slot" + (Object)ChatColor.GOLD + ")", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "2-3x /gkit " + (Object)ChatColor.GOLD + (Object)ChatColor.BOLD + ChatColor.UNDERLINE.toString() + "OR" + (Object)ChatColor.GOLD + " /vkit Fallen Hero", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1-2x Random Heroic Enchantment Book", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Mystery Cosmic Crate", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Rank Quest", "", ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)ChatColor.GREEN + bulletpoint + (Object)ChatColor.GREEN + "April 2016 " + CC.GreenB + CC.Underline + "OR" + CC.Green + " 2017 Cosmic Crate", (Object)ChatColor.GREEN + bulletpoint + (Object)ChatColor.GREEN + "Abandoned Spaceship Dungeon Lootbag", (Object)ChatColor.GREEN + bulletpoint + (Object)ChatColor.GREEN + "Judgement /vkit Gem"});
    }

    @Override
    public ItemStack getRandomMetaItem(Player player) {
        double chance = Math.random();
        if (chance <= 0.2) {
            return CCAPI.getCosmicCrate(this.random.nextInt(10) <= 3 ? Version.APRIL_2017 : Version.APRIL, "April 2018 Cosmic Crate");
        }
        if (chance > 0.2 && chance < 0.7) {
            return DungeonsRewardsAPI.getDungeonLootBag((Player)player, (Dungeon)DungeonsAPI.get().getDungeon(0));
        }
        return EvolutionKitItems.getKitItem((EvolutionKits)EvolutionKits.JUDGEMENT);
    }

    @Override
    public List<ItemStack> getCrateLoot(Player player) {
        ArrayList items = Lists.newArrayList();
        items.add(ItemUtils.setAmount((ItemStack)ItemUtils.buildItem((Material)Material.LEATHER, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)(CC.AquaB + "Chocolate Bunny")), (String[])new String[]{"", (Object)ChatColor.GRAY + "An ancient bunny encapsulated", (Object)ChatColor.GRAY + "in a chocolatey tomb, yum!"}), (int)1));
        items.add(CosmicDisguiseAPI.createRedeemableDisguise((String)(this.random.nextBoolean() ? "MUSHROOM_COW" : "PIG_ZOMBIE")));
        items.add(ItemUtils.setAmount((ItemStack)RandomizationScroll.getRandomizationScroll((ItemRarity)ItemRarity.LEGENDARY), (int)(2 + ThreadLocalRandom.current().nextInt(2))));
        int x = this.random.nextInt(4);
        if (x == 0) {
            items.add(ItemUtils.getTitleVoucher((String)"#EasterBunny"));
        }
        if (x == 1) {
            items.add(ItemUtils.getTitleVoucher((String)"#AprilShowers"));
        }
        if (x == 2) {
            items.add(ItemUtils.getTitleVoucher((String)"#Spring2018"));
        }
        if (x == 3) {
            if (Math.random() < 0.1) {
                items.add(ItemUtils.getTitleVoucher((String)"#Easter2018"));
            } else {
                items.add(ItemUtils.getTitleVoucher((String)"#EasterMayhem"));
            }
        }
        items.add(ItemUtils.setAmount((ItemStack)EnchantmentOrb.getEnchantmentOrb((int)100, (int)12, (OrbType)(this.random.nextInt(10) <= 6 ? OrbType.ARMOR : OrbType.WEAPON)), (int)(this.random.nextInt(10) <= 6 ? 1 : 2)));
        items.add(ItemUtils.setAmount((ItemStack)(ThreadLocalRandom.current().nextBoolean() ? FallenHero.getFallenHeroItem((GlobalKits)GlobalKits.values()[this.random.nextInt(GlobalKits.values().length)]) : FallenHero.getFallenHeroItem((EvolutionKit)EvolutionKits.values()[this.random.nextInt(EvolutionKits.values().length)].getKit())), (int)(2 + this.random.nextInt(2))));
        items.add(ItemID.removeItemID((ItemStack)ItemUtils.setAmount((ItemStack)TieredEnchantments.getMysteryEnchantmentBook((int)7), (int)(this.random.nextInt(10) <= 7 ? 1 : 2))));
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
        items.add(ItemID.removeItemID((ItemStack)ItemUtils.setAmount((ItemStack)CCAPI.getMysteryCrate(), (int)1)));
        return items;
    }

    @Override
    public short getNonColorPane() {
        return DyeColor.PURPLE.getWoolData();
    }

    @Override
    public List<Color> getConclusionFireworkColors() {
        return Lists.newArrayList((Object[])new Color[]{Color.PURPLE, Color.YELLOW});
    }

    @Override
    public short getColorPane() {
        return DyeColor.YELLOW.getWoolData();
    }
}

