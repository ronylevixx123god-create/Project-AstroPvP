// 
// Decompiled by Procyon v0.5.36
// 

package com.compilex.CosmicCrate.libs.cratedata;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import com.compilex.CosmicCrate.libs.Version;
import com.compilex.CosmicCrate.api.CCAPI;
import me.cosmicpvp.CosmicGlobalKits.commands.CommandFallenHero;
import org.arkhamnetwork.enchantments.util.TieredEnchantments;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.arkhamnetwork.Arkkit.patches.EquipmentLootboxes;
import me.ifamasssxd.cosmicdisguises.managers.CosmicDisguiseAPI;
import org.arkhamnetwork.enchantments.RandomizationScroll;
import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import org.bukkit.Material;
import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import java.util.concurrent.ThreadLocalRandom;
import com.rit.sucy.EnchantmentAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.cosmicpvp.CosmicRankQuests.CosmicRankQuestsAPI;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import com.google.common.collect.Lists;
import com.cosmicpvp.cosmicutils.utils.CC;
import org.bukkit.ChatColor;
import java.util.List;
import com.compilex.CosmicCrate.libs.CosmicCrateData;

public class September2018 extends CosmicCrateData
{
    @Override
    public List<String> getDescriptionLore(final String username) {
        final String bulletpoint = ChatColor.BOLD.toString() + "* ";
        return (List<String>)Lists.newArrayList((Object[])new String[] { ChatColor.AQUA + "Unlocked by " + ChatColor.BOLD + username + ChatColor.AQUA + " at " + ChatColor.DARK_BLUE + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Happy Memories", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Limited Edition Titles", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "2-3x Godly Randomization Scrolls", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Chicken " + CC.YellowB + CC.Underline + "OR" + CC.Yellow + " Iron Golem Disguise", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", CC.Gold + bulletpoint + CC.Gold + "1-2x Heroic G-Kit Generator", CC.Gold + bulletpoint + CC.Gold + "1-3x Random Heroic Enchantment Books", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1x Evolution " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " Heroic Fallen Hero Bundle", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1x Heroic " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " Superior Mystery Cosmic Crate", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Back to School 2017 " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " 2016 CC", "", ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", ChatColor.AQUA + bulletpoint + ChatColor.AQUA + "Infinite Luck Enchantment Book", ChatColor.AQUA + bulletpoint + ChatColor.AQUA + "Top-tier Rank Quest", ChatColor.AQUA + bulletpoint + ChatColor.AQUA + "Dimensional Traveler Set Piece" });
    }
    
    @Override
    public ItemStack getRandomMetaItem(final Player player) {
        final double chance = Math.random();
        if (chance <= 0.7) {
            return CosmicRankQuestsAPI.getRankQuestItem(5);
        }
        if (chance <= 0.9) {
            return ArmorSet.DIMENSIONAL_TRAVELER.getRandomPeice(true);
        }
        CustomEnchantment ench = EnchantmentAPI.getEnchantment("Infinite Luck");
        if (ench == null) {
            ench = EnchantmentAPI.getEnchantment("Lucky");
        }
        final double val = Math.random();
        final int level = (val <= 0.75) ? 1 : ((val <= 0.85) ? 2 : ((val <= 0.9) ? 3 : ((val <= 0.95) ? 4 : 5)));
        return EnchantmentBook.getEnchantmentBook(ench, level, 20 + ThreadLocalRandom.current().nextInt(25), 35 + ThreadLocalRandom.current().nextInt(40));
    }
    
    @Override
    public List<ItemStack> getCrateLoot(final Player player) {
        final List<ItemStack> items = (List<ItemStack>)Lists.newArrayList();
        items.add(ItemUtils.buildItem(Material.PAPER, (short)0, ChatColor.translateAlternateColorCodes('&', "&e&lHappy Memories"), new String[] { ChatColor.GRAY + "All that remains of the Summer of 2018." }));
        items.add(this.setAmount(RandomizationScroll.getRandomizationScroll(ItemRarity.GOD), 2 + this.random.nextInt(2)));
        items.add(CosmicDisguiseAPI.createRedeemableDisguise((Math.random() <= 0.02) ? "CHICKEN" : "IRON_GOLEM"));
        final int x = this.random.nextInt(4);
        if (x == 0) {
            items.add(ItemUtils.getTitleVoucher("BackToSchool"));
        }
        if (x == 1) {
            items.add(ItemUtils.getTitleVoucher("#MissSummer"));
        }
        if (x == 2) {
            items.add(ItemUtils.getTitleVoucher("Trendy"));
        }
        if (x == 3) {
            if (Math.random() < 0.1) {
                items.add(ItemUtils.getTitleVoucher("#SchoolsNotCool"));
            }
            else {
                items.add(ItemUtils.getTitleVoucher("bored"));
            }
        }
        items.add(this.setAmount(((EquipmentLootboxes)ArkPlugins.EQUIPMENT_LOOTBOX.getArkPlugin()).createHeroicGKitGenerator(false), 1 + this.random.nextInt(2)));
        items.add(this.setAmount(TieredEnchantments.getMysteryEnchantmentBook(7), 1 + this.random.nextInt(3)));
        items.add(this.random.nextBoolean() ? CommandFallenHero.getHeroicFallenHeroBundle() : CommandFallenHero.getHeroicVKitHeroBundle());
        items.add(this.random.nextBoolean() ? CCAPI.getHeroicMysteryCrate() : CCAPI.getGodlyCosmicCrate());
        items.add(CCAPI.getCosmicCrate((Math.random() <= 0.65) ? Version.SCHOOL : Version.SCHOOL_2017, player.getName()));
        return items;
    }
    
    @Override
    public short getNonColorPane() {
        return DyeColor.LIGHT_BLUE.getWoolData();
    }
    
    @Override
    public List<Color> getConclusionFireworkColors() {
        return (List<Color>)Lists.newArrayList((Object[])new Color[] { Color.AQUA, Color.BLUE });
    }
    
    @Override
    public short getColorPane() {
        return DyeColor.BLUE.getWoolData();
    }
}
