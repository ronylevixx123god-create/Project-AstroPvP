// 
// Decompiled by Procyon v0.5.36
// 

package com.compilex.CosmicCrate.libs.cratedata;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import com.compilex.CosmicCrate.api.CCAPI;
import com.compilex.CosmicCrate.libs.Version;
import com.cosmicpvp.cosmicutils.itembuilder.LootboxItemBuilder;
import com.cosmicpvp.CosmicRankQuests.CosmicRankQuestsAPI;
import com.compilex.CosmicDungeons.DungeonsRewardsAPI;
import com.compilex.CosmicDungeons.dungeons.libs.DungeonType;
import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.cosmicpvp.cosmicutils.itembuilder.MaskBuilder;
import org.arkhamnetwork.enchantments.RandomizationScroll;
import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import org.bukkit.Material;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import me.ifamasssxd.cosmicskycustoms.struct.CustomItemBuilder;
import com.compilex.CosmicArmorSets.enums.AbilityArmorSet;
import me.cosmicpvp.CosmicGlobalKits.CosmicGlobalKitsAPI;
import java.util.concurrent.ThreadLocalRandom;
import me.cosmicpvp.CosmicGlobalKits.libs.MasteryKits;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import java.util.Random;
import com.rit.sucy.EnchantmentAPI;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import com.google.common.collect.Lists;
import com.cosmicpvp.cosmicutils.utils.CC;
import java.util.List;
import org.bukkit.ChatColor;
import com.compilex.CosmicCrate.libs.CosmicCrateData;

public class May2019 extends CosmicCrateData
{
    private ChatColor mainColor;
    private ChatColor secondColor;
    
    public May2019() {
        this.mainColor = ChatColor.BLUE;
        this.secondColor = ChatColor.GREEN;
    }
    
    @Override
    public List<String> getDescriptionLore(final String username) {
        final String bulletpoint = ChatColor.BOLD.toString() + "* ";
        return (List<String>)Lists.newArrayList((Object[])new String[] { this.mainColor + "Unlocked by " + ChatColor.BOLD + username + this.mainColor + " at " + this.secondColor + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Doctor's Note", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", CC.YellowB + bulletpoint + CC.Yellow + "2-4x Godly Randomization Scrolls", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", CC.Gold + bulletpoint + CC.Gold + "1-2x 35-50% Crystal Extraction Item", CC.Gold + bulletpoint + CC.Gold + "1x Bunny " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " Zeus Mask", CC.Gold + bulletpoint + CC.Gold + "1x Lootbox: April Showers " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " Easter", CC.Gold + bulletpoint + CC.Gold + "1x May 2017, " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " 2018 /cc", CC.Gold + bulletpoint + CC.Gold + "1x Tier 4 " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " Tier 5 Rank Quest", CC.Gold + bulletpoint + CC.Gold + "1-4x Random Heroic Upgrade", CC.Gold + bulletpoint + CC.Gold + "1-2x Random Dungeon Lootbags", "", this.secondColor + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", this.secondColor + bulletpoint + this.secondColor + "1x Random /mkit Gem", this.secondColor + bulletpoint + this.secondColor + "1x Polymorphic Metaphysical IV", this.secondColor + bulletpoint + this.secondColor + "1x 60% Armor Crystal (Dimensional Traveler)", this.secondColor + bulletpoint + this.secondColor + "1x 90% Anti M-Kit Crystal (Necromancer)" });
    }
    
    @Override
    public ItemStack getRandomMetaItem(final Player player) {
        final double chance = Math.random();
        if (chance <= 0.35) {
            return EnchantmentBook.getEnchantmentBook(EnchantmentAPI.getEnchantment("Polymorphic Metaphysical"), 4, 60 + new Random().nextInt(20), 25 + new Random().nextInt(25));
        }
        if (chance <= 0.6) {
            return CosmicGlobalKitsAPI.createMasteryKitItem(MasteryKits.values()[ThreadLocalRandom.current().nextInt(MasteryKits.values().length)], true);
        }
        if (chance <= 0.85) {
            return new CustomItemBuilder("ANTI_MKIT", new Object[] { AbilityArmorSet.NECROMANCER, 90 }).build();
        }
        return new CustomItemBuilder("bonusArmorCrystal", new Object[] { ArmorSet.DIMENSIONAL_TRAVELER, 60 }).build();
    }
    
    @Override
    public List<ItemStack> getCrateLoot(final Player player) {
        final List<ItemStack> items = (List<ItemStack>)Lists.newArrayList();
        items.add(ItemUtils.buildItem(Material.WRITTEN_BOOK, (short)0, ChatColor.translateAlternateColorCodes('&', "&f&lD&c&lo&f&lc&c&lt&f&lo&c&lr&f&l's &nNote"), new String[] { CC.Gray + "The equivalent of an IRL,", CC.Gray + "'get-out-of-jail FREE card -", CC.Gray + "use it wisely..." }));
        items.add(ItemUtils.setAmount(RandomizationScroll.getRandomizationScroll(ItemRarity.GOD), 2 + this.random.nextInt(3)));
        items.add(ItemUtils.setAmount(new CustomItemBuilder("extractCrystal", new Object[] { 35 + new Random().nextInt(16) }).build(), this.getRandomOne()));
        final ItemStack mask = new MaskBuilder(this.random.nextBoolean() ? "BUNNY" : "ZEUS").build();
        if (mask != null) {
            items.add(mask);
        }
        items.add(this.setAmount(CosmicArmorSetsAPI.createRandomArmorSetUpgrade(), 1 + this.random.nextInt(4)));
        items.add(this.setAmount(DungeonsRewardsAPI.getDungeonLootBag(player, (this.random.nextDouble() <= 0.8) ? DungeonType.ABANDONED_SHIP.getDungeonInstance() : DungeonType.DESTROYED_OUTPOST.getDungeonInstance()), this.getRandomOne()));
        items.add(CosmicRankQuestsAPI.getRankQuestItem((Math.random() < 0.22) ? 5 : 4));
        final ItemStack lootbox = new LootboxItemBuilder((Math.random() < 0.4) ? "lootbox_easter2019" : "lootbox_aprilshowers").build();
        if (lootbox != null) {
            items.add(lootbox);
        }
        items.add(CCAPI.getCosmicCrate((this.random.nextDouble() <= 0.3) ? Version.MAY_2018 : Version.MAY_2017, player.getName()));
        return items;
    }
    
    private int getRandomOne() {
        return (Math.random() <= 0.25) ? 2 : 1;
    }
    
    @Override
    public short getNonColorPane() {
        return DyeColor.BLUE.getWoolData();
    }
    
    @Override
    public List<Color> getConclusionFireworkColors() {
        return (List<Color>)Lists.newArrayList((Object[])new Color[] { Color.BLUE, Color.LIME });
    }
    
    @Override
    public short getColorPane() {
        return DyeColor.LIME.getWoolData();
    }
}
