// 
// Decompiled by Procyon v0.5.36
// 

package com.compilex.CosmicCrate.libs.cratedata;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import com.compilex.CosmicCrate.api.CCAPI;
import com.compilex.CosmicCrate.libs.Version;
import com.cosmicpvp.cosmicutils.itembuilder.CustomItemBuilder;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import com.compilex.CosmicDungeons.DungeonsRewardsAPI;
import org.arkhamnetwork.enchantments.util.TieredEnchantments;
import com.cosmicpvp.cosmicutils.utils.CustomItemUtils;
import org.arkhamnetwork.enchantments.RandomizationScroll;
import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import org.bukkit.Material;
import com.cosmicpvp.CosmicRankQuests.HeroicRankCrystalAPI;
import me.cosmicpvp.CosmicGlobalKits.CosmicGlobalKitsAPI;
import me.cosmicpvp.CosmicGlobalKits.libs.MasteryKits;
import me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKits;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import com.google.common.collect.Lists;
import com.cosmicpvp.cosmicutils.utils.CC;
import java.util.List;
import org.bukkit.ChatColor;
import com.compilex.CosmicCrate.libs.CosmicCrateData;

public class December2018 extends CosmicCrateData
{
    private ChatColor mainColor;
    private ChatColor secondColor;
    
    public December2018() {
        this.mainColor = ChatColor.BLUE;
        this.secondColor = ChatColor.AQUA;
    }
    
    @Override
    public List<String> getDescriptionLore(final String username) {
        final String bulletpoint = ChatColor.BOLD.toString() + "* ";
        return (List<String>)Lists.newArrayList((Object[])new String[] { this.mainColor + "Unlocked by " + ChatColor.BOLD + username + this.mainColor + " at " + this.secondColor + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Cozy Hoodie", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Limited Edition Titles", CC.YellowB + bulletpoint + ChatColor.YELLOW + "1-3x Godly Randomization Scrolls", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", CC.Gold + bulletpoint + CC.Gold + "1-2x /vkit Evolution Gems", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1-3x Heroic " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " Soul Enchantment Books", CC.Gold + bulletpoint + CC.Gold + "1-2x Heroic Blackscroll " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " Holy Whitescroll", CC.Gold + bulletpoint + CC.Gold + "1-5x Heroic G-Kit Generator", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Random Mask Generator", CC.Gold + bulletpoint + CC.Gold + "December 2016 " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " 2017 /cc", "", this.secondColor + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", this.secondColor + bulletpoint + this.secondColor + "Necromancer " + this.secondColor + CC.Bold + CC.Underline + "OR" + this.secondColor + " Ghost /mkit Gem", this.secondColor + bulletpoint + this.secondColor + "Lucky /vkit Gem", this.secondColor + bulletpoint + this.secondColor + "Heroic Rank Crystal" });
    }
    
    @Override
    public ItemStack getRandomMetaItem(final Player player) {
        final double chance = Math.random();
        if (chance <= 0.3) {
            return EvolutionKits.LUCKY.getClaimableItem();
        }
        if (chance <= 0.65) {
            return CosmicGlobalKitsAPI.createMasteryKitItem((this.random.nextDouble() <= 0.4) ? MasteryKits.GHOST : MasteryKits.NECROMANCER, true);
        }
        return HeroicRankCrystalAPI.getHeroicRankCrystalItem();
    }
    
    @Override
    public List<ItemStack> getCrateLoot(final Player player) {
        final List<ItemStack> items = (List<ItemStack>)Lists.newArrayList();
        items.add(ItemUtils.buildItem(Material.LEATHER_HELMET, (short)0, ChatColor.translateAlternateColorCodes('&', "&b&lCozy Hoodie"), new String[] { CC.Gray + "The comfiest, coziest pullover", CC.Gray + "cotton hoodie you have ever worn!" }));
        final int x = this.random.nextInt(4);
        if (x == 0) {
            items.add(ItemUtils.getTitleVoucher("#NutCracker"));
        }
        if (x == 1) {
            items.add(ItemUtils.getTitleVoucher("ButIWasOnCC"));
        }
        if (x == 2) {
            items.add(ItemUtils.getTitleVoucher("ItWasLag"));
        }
        if (x == 3) {
            if (Math.random() <= 0.3) {
                items.add(ItemUtils.getTitleVoucher("StockingStuffer"));
            }
            else {
                items.add(ItemUtils.getTitleVoucher("#NaughtyList"));
            }
        }
        items.add(this.setAmount(RandomizationScroll.getRandomizationScroll(ItemRarity.GOD), this.getRandomThree()));
        items.add(this.setAmount(CustomItemUtils.createRandomEvolutionKitGem(), this.getRandomOne()));
        items.add(this.setAmount(TieredEnchantments.getMysteryEnchantmentBook((this.random.nextInt(100) <= 40) ? 6 : 7), this.getRandomThree()));
        items.add(this.setAmount((this.random.nextDouble() <= 0.2) ? DungeonsRewardsAPI.getHolyWhiteScroll() : EnchantmentBook.getHeroicBlackscroll(true), this.getRandomOne()));
        items.add(new CustomItemBuilder(CustomItemBuilder.CustomItemType.GKIT_GENERATOR, this.getRandomFive()).build());
        items.add(new CustomItemBuilder(CustomItemBuilder.CustomItemType.RANDOM_MASK).build());
        items.add(CCAPI.getCosmicCrate((this.random.nextDouble() <= 0.4) ? Version.DECEMBER_2017 : Version.DECEMBER_2016, player.getName()));
        return items;
    }
    
    private int getRandomOne() {
        return (Math.random() <= 0.3) ? 2 : 1;
    }
    
    private int getRandomThree() {
        return (Math.random() <= 0.1) ? 3 : ((Math.random() <= 0.3) ? 2 : 1);
    }
    
    private int getRandomFive() {
        int highestPossible = 3;
        if (Math.random() <= 0.1) {
            highestPossible = 5;
        }
        else if (Math.random() <= 0.3) {
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
        return (List<Color>)Lists.newArrayList((Object[])new Color[] { Color.AQUA, Color.ORANGE });
    }
    
    @Override
    public short getColorPane() {
        return DyeColor.LIGHT_BLUE.getWoolData();
    }
}
