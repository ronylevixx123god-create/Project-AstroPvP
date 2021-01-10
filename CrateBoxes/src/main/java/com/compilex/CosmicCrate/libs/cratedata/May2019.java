/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicArmorSets.CosmicArmorSetsAPI
 *  com.compilex.CosmicArmorSets.enums.AbilityArmorSet
 *  com.compilex.CosmicArmorSets.enums.ArmorSet
 *  com.compilex.CosmicDungeons.DungeonsRewardsAPI
 *  com.compilex.CosmicDungeons.dungeons.libs.Dungeon
 *  com.compilex.CosmicDungeons.dungeons.libs.DungeonType
 *  com.cosmicpvp.CosmicRankQuests.CosmicRankQuestsAPI
 *  com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity
 *  com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils
 *  com.cosmicpvp.cosmicutils.itembuilder.LootboxItemBuilder
 *  com.cosmicpvp.cosmicutils.itembuilder.MaskBuilder
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.google.common.collect.Lists
 *  com.rit.sucy.CustomEnchantment
 *  com.rit.sucy.EnchantmentAPI
 *  me.cosmicpvp.CosmicGlobalKits.CosmicGlobalKitsAPI
 *  me.cosmicpvp.CosmicGlobalKits.libs.MasteryKits
 *  me.ifamasssxd.cosmicskycustoms.struct.CustomItemBuilder
 *  org.arkhamnetwork.enchantments.EnchantmentBook
 *  org.arkhamnetwork.enchantments.RandomizationScroll
 *  org.bukkit.ChatColor
 *  org.bukkit.Color
 *  org.bukkit.DyeColor
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package com.compilex.CosmicCrate.libs.cratedata;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.AbilityArmorSet;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.compilex.CosmicCrate.api.CCAPI;
import com.compilex.CosmicCrate.libs.CosmicCrateData;
import com.compilex.CosmicCrate.libs.Version;
import com.compilex.CosmicDungeons.DungeonsRewardsAPI;
import com.compilex.CosmicDungeons.dungeons.libs.Dungeon;
import com.compilex.CosmicDungeons.dungeons.libs.DungeonType;
import com.cosmicpvp.CosmicRankQuests.CosmicRankQuestsAPI;
import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.itembuilder.LootboxItemBuilder;
import com.cosmicpvp.cosmicutils.itembuilder.MaskBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import me.cosmicpvp.CosmicGlobalKits.CosmicGlobalKitsAPI;
import me.cosmicpvp.CosmicGlobalKits.libs.MasteryKits;
import me.ifamasssxd.cosmicskycustoms.struct.CustomItemBuilder;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import org.arkhamnetwork.enchantments.RandomizationScroll;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class May2019
extends CosmicCrateData {
    private ChatColor mainColor = ChatColor.BLUE;
    private ChatColor secondColor = ChatColor.GREEN;

    @Override
    public List<String> getDescriptionLore(String username) {
        String bulletpoint = ChatColor.BOLD.toString() + "* ";
        return Lists.newArrayList((Object[])new String[]{(Object)this.mainColor + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)this.mainColor + " at " + (Object)this.secondColor + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Doctor's Note", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", CC.YellowB + bulletpoint + CC.Yellow + "2-4x Godly Randomization Scrolls", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", CC.Gold + bulletpoint + CC.Gold + "1-2x 35-50% Crystal Extraction Item", CC.Gold + bulletpoint + CC.Gold + "1x Bunny " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " Zeus Mask", CC.Gold + bulletpoint + CC.Gold + "1x Lootbox: April Showers " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " Easter", CC.Gold + bulletpoint + CC.Gold + "1x May 2017, " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " 2018 /cc", CC.Gold + bulletpoint + CC.Gold + "1x Tier 4 " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " Tier 5 Rank Quest", CC.Gold + bulletpoint + CC.Gold + "1-4x Random Heroic Upgrade", CC.Gold + bulletpoint + CC.Gold + "1-2x Random Dungeon Lootbags", "", (Object)this.secondColor + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)this.secondColor + bulletpoint + (Object)this.secondColor + "1x Random /mkit Gem", (Object)this.secondColor + bulletpoint + (Object)this.secondColor + "1x Polymorphic Metaphysical IV", (Object)this.secondColor + bulletpoint + (Object)this.secondColor + "1x 60% Armor Crystal (Dimensional Traveler)", (Object)this.secondColor + bulletpoint + (Object)this.secondColor + "1x 90% Anti M-Kit Crystal (Necromancer)"});
    }

    @Override
    public ItemStack getRandomMetaItem(Player player) {
        double chance = Math.random();
        if (chance <= 0.35) {
            return EnchantmentBook.getEnchantmentBook((CustomEnchantment)EnchantmentAPI.getEnchantment((String)"Polymorphic Metaphysical"), (int)4, (int)(60 + new Random().nextInt(20)), (int)(25 + new Random().nextInt(25)));
        }
        if (chance <= 0.6) {
            return CosmicGlobalKitsAPI.createMasteryKitItem((MasteryKits)MasteryKits.values()[ThreadLocalRandom.current().nextInt(MasteryKits.values().length)], (boolean)true);
        }
        if (chance <= 0.85) {
            return new CustomItemBuilder("ANTI_MKIT", new Object[]{AbilityArmorSet.NECROMANCER, 90}).build();
        }
        return new CustomItemBuilder("bonusArmorCrystal", new Object[]{ArmorSet.DIMENSIONAL_TRAVELER, 60}).build();
    }

    @Override
    public List<ItemStack> getCrateLoot(Player player) {
        ArrayList items = Lists.newArrayList();
        items.add(ItemUtils.buildItem((Material)Material.WRITTEN_BOOK, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&f&lD&c&lo&f&lc&c&lt&f&lo&c&lr&f&l's &nNote"), (String[])new String[]{CC.Gray + "The equivalent of an IRL,", CC.Gray + "'get-out-of-jail FREE card -", CC.Gray + "use it wisely..."}));
        items.add(ItemUtils.setAmount((ItemStack)RandomizationScroll.getRandomizationScroll((ItemRarity)ItemRarity.GOD), (int)(2 + this.random.nextInt(3))));
        items.add(ItemUtils.setAmount((ItemStack)new CustomItemBuilder("extractCrystal", new Object[]{35 + new Random().nextInt(16)}).build(), (int)this.getRandomOne()));
        ItemStack mask = new MaskBuilder(this.random.nextBoolean() ? "BUNNY" : "ZEUS").build();
        if (mask != null) {
            items.add(mask);
        }
        items.add(this.setAmount(CosmicArmorSetsAPI.createRandomArmorSetUpgrade(), 1 + this.random.nextInt(4)));
        items.add(this.setAmount(DungeonsRewardsAPI.getDungeonLootBag((Player)player, (Dungeon)(this.random.nextDouble() <= 0.8 ? DungeonType.ABANDONED_SHIP.getDungeonInstance() : DungeonType.DESTROYED_OUTPOST.getDungeonInstance())), this.getRandomOne()));
        items.add(CosmicRankQuestsAPI.getRankQuestItem((int)(Math.random() < 0.22 ? 5 : 4)));
        ItemStack lootbox = new LootboxItemBuilder(Math.random() < 0.4 ? "lootbox_easter2019" : "lootbox_aprilshowers").build();
        if (lootbox != null) {
            items.add(lootbox);
        }
        items.add(CCAPI.getCosmicCrate(this.random.nextDouble() <= 0.3 ? Version.MAY_2018 : Version.MAY_2017, player.getName()));
        return items;
    }

    private int getRandomOne() {
        return Math.random() <= 0.25 ? 2 : 1;
    }

    @Override
    public short getNonColorPane() {
        return DyeColor.BLUE.getWoolData();
    }

    @Override
    public List<Color> getConclusionFireworkColors() {
        return Lists.newArrayList((Object[])new Color[]{Color.BLUE, Color.LIME});
    }

    @Override
    public short getColorPane() {
        return DyeColor.LIME.getWoolData();
    }
}

