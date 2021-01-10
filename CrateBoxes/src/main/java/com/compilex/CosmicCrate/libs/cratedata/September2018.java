/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicArmorSets.enums.ArmorSet
 *  com.cosmicpvp.CosmicRankQuests.CosmicRankQuestsAPI
 *  com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity
 *  com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.google.common.collect.Lists
 *  com.rit.sucy.CustomEnchantment
 *  com.rit.sucy.EnchantmentAPI
 *  me.cosmicpvp.CosmicGlobalKits.commands.CommandFallenHero
 *  me.ifamasssxd.cosmicdisguises.managers.CosmicDisguiseAPI
 *  org.arkhamnetwork.Arkkit.patches.EquipmentLootboxes
 *  org.arkhamnetwork.Arkkit.plugins.ArkPlugins
 *  org.arkhamnetwork.enchantments.EnchantmentBook
 *  org.arkhamnetwork.enchantments.RandomizationScroll
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
import com.cosmicpvp.CosmicRankQuests.CosmicRankQuestsAPI;
import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import me.cosmicpvp.CosmicGlobalKits.commands.CommandFallenHero;
import me.ifamasssxd.cosmicdisguises.managers.CosmicDisguiseAPI;
import org.arkhamnetwork.Arkkit.patches.EquipmentLootboxes;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import org.arkhamnetwork.enchantments.RandomizationScroll;
import org.arkhamnetwork.enchantments.util.TieredEnchantments;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class September2018
extends CosmicCrateData {
    @Override
    public List<String> getDescriptionLore(String username) {
        String bulletpoint = ChatColor.BOLD.toString() + "* ";
        return Lists.newArrayList((Object[])new String[]{(Object)ChatColor.AQUA + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)ChatColor.AQUA + " at " + (Object)ChatColor.DARK_BLUE + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Happy Memories", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Limited Edition Titles", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "2-3x Godly Randomization Scrolls", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Chicken " + CC.YellowB + CC.Underline + "OR" + CC.Yellow + " Iron Golem Disguise", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", CC.Gold + bulletpoint + CC.Gold + "1-2x Heroic G-Kit Generator", CC.Gold + bulletpoint + CC.Gold + "1-3x Random Heroic Enchantment Books", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1x Evolution " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " Heroic Fallen Hero Bundle", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1x Heroic " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " Superior Mystery Cosmic Crate", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Back to School 2017 " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " 2016 CC", "", ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)ChatColor.AQUA + bulletpoint + (Object)ChatColor.AQUA + "Infinite Luck Enchantment Book", (Object)ChatColor.AQUA + bulletpoint + (Object)ChatColor.AQUA + "Top-tier Rank Quest", (Object)ChatColor.AQUA + bulletpoint + (Object)ChatColor.AQUA + "Dimensional Traveler Set Piece"});
    }

    @Override
    public ItemStack getRandomMetaItem(Player player) {
        double val;
        double chance = Math.random();
        if (chance <= 0.7) {
            return CosmicRankQuestsAPI.getRankQuestItem((int)5);
        }
        if (chance <= 0.9) {
            return ArmorSet.DIMENSIONAL_TRAVELER.getRandomPeice(true);
        }
        CustomEnchantment ench = EnchantmentAPI.getEnchantment((String)"Infinite Luck");
        if (ench == null) {
            ench = EnchantmentAPI.getEnchantment((String)"Lucky");
        }
        int level = (val = Math.random()) <= 0.75 ? 1 : (val <= 0.85 ? 2 : (val <= 0.9 ? 3 : (val <= 0.95 ? 4 : 5)));
        return EnchantmentBook.getEnchantmentBook((CustomEnchantment)ench, (int)level, (int)(20 + ThreadLocalRandom.current().nextInt(25)), (int)(35 + ThreadLocalRandom.current().nextInt(40)));
    }

    @Override
    public List<ItemStack> getCrateLoot(Player player) {
        ArrayList items = Lists.newArrayList();
        items.add(ItemUtils.buildItem((Material)Material.PAPER, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&e&lHappy Memories"), (String[])new String[]{(Object)ChatColor.GRAY + "All that remains of the Summer of 2018."}));
        items.add(this.setAmount(RandomizationScroll.getRandomizationScroll((ItemRarity)ItemRarity.GOD), 2 + this.random.nextInt(2)));
        items.add(CosmicDisguiseAPI.createRedeemableDisguise((String)(Math.random() <= 0.02 ? "CHICKEN" : "IRON_GOLEM")));
        int x = this.random.nextInt(4);
        if (x == 0) {
            items.add(ItemUtils.getTitleVoucher((String)"BackToSchool"));
        }
        if (x == 1) {
            items.add(ItemUtils.getTitleVoucher((String)"#MissSummer"));
        }
        if (x == 2) {
            items.add(ItemUtils.getTitleVoucher((String)"Trendy"));
        }
        if (x == 3) {
            if (Math.random() < 0.1) {
                items.add(ItemUtils.getTitleVoucher((String)"#SchoolsNotCool"));
            } else {
                items.add(ItemUtils.getTitleVoucher((String)"bored"));
            }
        }
        items.add(this.setAmount(((EquipmentLootboxes)ArkPlugins.EQUIPMENT_LOOTBOX.getArkPlugin()).createHeroicGKitGenerator(false), 1 + this.random.nextInt(2)));
        items.add(this.setAmount(TieredEnchantments.getMysteryEnchantmentBook((int)7), 1 + this.random.nextInt(3)));
        items.add(this.random.nextBoolean() ? CommandFallenHero.getHeroicFallenHeroBundle() : CommandFallenHero.getHeroicVKitHeroBundle());
        items.add(this.random.nextBoolean() ? CCAPI.getHeroicMysteryCrate() : CCAPI.getGodlyCosmicCrate());
        items.add(CCAPI.getCosmicCrate(Math.random() <= 0.65 ? Version.SCHOOL : Version.SCHOOL_2017, player.getName()));
        return items;
    }

    @Override
    public short getNonColorPane() {
        return DyeColor.LIGHT_BLUE.getWoolData();
    }

    @Override
    public List<Color> getConclusionFireworkColors() {
        return Lists.newArrayList((Object[])new Color[]{Color.AQUA, Color.BLUE});
    }

    @Override
    public short getColorPane() {
        return DyeColor.BLUE.getWoolData();
    }
}

