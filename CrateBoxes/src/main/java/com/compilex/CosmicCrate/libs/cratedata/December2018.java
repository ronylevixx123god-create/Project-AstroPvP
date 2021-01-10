/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicDungeons.DungeonsRewardsAPI
 *  com.cosmicpvp.CosmicRankQuests.HeroicRankCrystalAPI
 *  com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity
 *  com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils
 *  com.cosmicpvp.cosmicutils.itembuilder.CustomItemBuilder
 *  com.cosmicpvp.cosmicutils.itembuilder.CustomItemBuilder$CustomItemType
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.CustomItemUtils
 *  com.google.common.collect.Lists
 *  me.cosmicpvp.CosmicGlobalKits.CosmicGlobalKitsAPI
 *  me.cosmicpvp.CosmicGlobalKits.libs.MasteryKits
 *  me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKits
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

import com.compilex.CosmicCrate.api.CCAPI;
import com.compilex.CosmicCrate.libs.CosmicCrateData;
import com.compilex.CosmicCrate.libs.Version;
import com.compilex.CosmicDungeons.DungeonsRewardsAPI;
import com.cosmicpvp.CosmicRankQuests.HeroicRankCrystalAPI;
import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.itembuilder.CustomItemBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.CustomItemUtils;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import me.cosmicpvp.CosmicGlobalKits.CosmicGlobalKitsAPI;
import me.cosmicpvp.CosmicGlobalKits.libs.MasteryKits;
import me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKits;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import org.arkhamnetwork.enchantments.RandomizationScroll;
import org.arkhamnetwork.enchantments.util.TieredEnchantments;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class December2018
extends CosmicCrateData {
    private ChatColor mainColor = ChatColor.BLUE;
    private ChatColor secondColor = ChatColor.AQUA;

    @Override
    public List<String> getDescriptionLore(String username) {
        String bulletpoint = ChatColor.BOLD.toString() + "* ";
        return Lists.newArrayList((Object[])new String[]{(Object)this.mainColor + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)this.mainColor + " at " + (Object)this.secondColor + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Cozy Hoodie", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Limited Edition Titles", CC.YellowB + bulletpoint + (Object)ChatColor.YELLOW + "1-3x Godly Randomization Scrolls", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", CC.Gold + bulletpoint + CC.Gold + "1-2x /vkit Evolution Gems", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1-3x Heroic " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " Soul Enchantment Books", CC.Gold + bulletpoint + CC.Gold + "1-2x Heroic Blackscroll " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " Holy Whitescroll", CC.Gold + bulletpoint + CC.Gold + "1-5x Heroic G-Kit Generator", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Random Mask Generator", CC.Gold + bulletpoint + CC.Gold + "December 2016 " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " 2017 /cc", "", (Object)this.secondColor + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)this.secondColor + bulletpoint + (Object)this.secondColor + "Necromancer " + (Object)this.secondColor + CC.Bold + CC.Underline + "OR" + (Object)this.secondColor + " Ghost /mkit Gem", (Object)this.secondColor + bulletpoint + (Object)this.secondColor + "Lucky /vkit Gem", (Object)this.secondColor + bulletpoint + (Object)this.secondColor + "Heroic Rank Crystal"});
    }

    @Override
    public ItemStack getRandomMetaItem(Player player) {
        double chance = Math.random();
        if (chance <= 0.3) {
            return EvolutionKits.LUCKY.getClaimableItem();
        }
        if (chance <= 0.65) {
            return CosmicGlobalKitsAPI.createMasteryKitItem((MasteryKits)(this.random.nextDouble() <= 0.4 ? MasteryKits.GHOST : MasteryKits.NECROMANCER), (boolean)true);
        }
        return HeroicRankCrystalAPI.getHeroicRankCrystalItem();
    }

    @Override
    public List<ItemStack> getCrateLoot(Player player) {
        ArrayList items = Lists.newArrayList();
        items.add(ItemUtils.buildItem((Material)Material.LEATHER_HELMET, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&b&lCozy Hoodie"), (String[])new String[]{CC.Gray + "The comfiest, coziest pullover", CC.Gray + "cotton hoodie you have ever worn!"}));
        int x = this.random.nextInt(4);
        if (x == 0) {
            items.add(ItemUtils.getTitleVoucher((String)"#NutCracker"));
        }
        if (x == 1) {
            items.add(ItemUtils.getTitleVoucher((String)"ButIWasOnCC"));
        }
        if (x == 2) {
            items.add(ItemUtils.getTitleVoucher((String)"ItWasLag"));
        }
        if (x == 3) {
            if (Math.random() <= 0.3) {
                items.add(ItemUtils.getTitleVoucher((String)"StockingStuffer"));
            } else {
                items.add(ItemUtils.getTitleVoucher((String)"#NaughtyList"));
            }
        }
        items.add(this.setAmount(RandomizationScroll.getRandomizationScroll((ItemRarity)ItemRarity.GOD), this.getRandomThree()));
        items.add(this.setAmount(CustomItemUtils.createRandomEvolutionKitGem(), this.getRandomOne()));
        items.add(this.setAmount(TieredEnchantments.getMysteryEnchantmentBook((int)(this.random.nextInt(100) <= 40 ? 6 : 7)), this.getRandomThree()));
        items.add(this.setAmount(this.random.nextDouble() <= 0.2 ? DungeonsRewardsAPI.getHolyWhiteScroll() : EnchantmentBook.getHeroicBlackscroll((boolean)true), this.getRandomOne()));
        items.add(new CustomItemBuilder(CustomItemBuilder.CustomItemType.GKIT_GENERATOR, this.getRandomFive()).build());
        items.add(new CustomItemBuilder(CustomItemBuilder.CustomItemType.RANDOM_MASK).build());
        items.add(CCAPI.getCosmicCrate(this.random.nextDouble() <= 0.4 ? Version.DECEMBER_2017 : Version.DECEMBER_2016, player.getName()));
        return items;
    }

    private int getRandomOne() {
        return Math.random() <= 0.3 ? 2 : 1;
    }

    private int getRandomThree() {
        return Math.random() <= 0.1 ? 3 : (Math.random() <= 0.3 ? 2 : 1);
    }

    private int getRandomFive() {
        int highestPossible = 3;
        if (Math.random() <= 0.1) {
            highestPossible = 5;
        } else if (Math.random() <= 0.3) {
            highestPossible = 4;
        }
        return this.random.nextInt(highestPossible) + 1;
    }

    @Override
    public short getNonColorPane() {
        return DyeColor.BLUE.getWoolData();
    }

    @Override
    public List<Color> getConclusionFireworkColors() {
        return Lists.newArrayList((Object[])new Color[]{Color.AQUA, Color.ORANGE});
    }

    @Override
    public short getColorPane() {
        return DyeColor.LIGHT_BLUE.getWoolData();
    }
}

