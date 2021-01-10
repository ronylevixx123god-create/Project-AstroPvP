/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicDungeons.dungeons.libs.DungeonType
 *  com.cosmicpvp.CosmicRankQuests.HeroicRankCrystalAPI
 *  com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils
 *  com.cosmicpvp.cosmicutils.itembuilder.CustomItemBuilder
 *  com.cosmicpvp.cosmicutils.itembuilder.CustomItemBuilder$CustomItemType
 *  com.cosmicpvp.cosmicutils.itembuilder.MysteryEnchantBookBuilder
 *  com.cosmicpvp.cosmicutils.itembuilder.RandomizationScrollBuilder
 *  com.cosmicpvp.cosmicutils.struct.ItemTier
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.google.common.collect.Lists
 *  me.cosmicpvp.CosmicGlobalKits.commands.CommandFallenHero
 *  org.arkhamnetwork.enchantments.EnchantmentOrb
 *  org.arkhamnetwork.enchantments.libs.OrbType
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
import com.compilex.CosmicDungeons.dungeons.libs.DungeonType;
import com.cosmicpvp.CosmicRankQuests.HeroicRankCrystalAPI;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.itembuilder.CustomItemBuilder;
import com.cosmicpvp.cosmicutils.itembuilder.MysteryEnchantBookBuilder;
import com.cosmicpvp.cosmicutils.itembuilder.RandomizationScrollBuilder;
import com.cosmicpvp.cosmicutils.struct.ItemTier;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import me.cosmicpvp.CosmicGlobalKits.commands.CommandFallenHero;
import org.arkhamnetwork.enchantments.EnchantmentOrb;
import org.arkhamnetwork.enchantments.libs.OrbType;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class January2019
extends CosmicCrateData {
    private ChatColor mainColor = ChatColor.DARK_AQUA;
    private ChatColor secondColor = ChatColor.YELLOW;

    @Override
    public List<String> getDescriptionLore(String username) {
        String bulletpoint = ChatColor.BOLD.toString() + "* ";
        return Lists.newArrayList((Object[])new String[]{(Object)this.mainColor + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)this.mainColor + " at " + (Object)this.secondColor + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "2019 Meme Preview", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Limited Edition Titles", CC.YellowB + bulletpoint + (Object)ChatColor.YELLOW + "Random Mask Generator", CC.YellowB + bulletpoint + (Object)ChatColor.YELLOW + "2-4x Godly Randomization Scrolls", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", CC.Gold + bulletpoint + CC.Gold + "January 2017 " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " 2018 /cc", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1-2x Heroic " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " Normal Equipment Lootboxes", CC.Gold + bulletpoint + CC.Gold + "1-2x Heroic " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " Soul Enchantment Books", CC.Gold + bulletpoint + CC.Gold + "1-2x Heroic G-Kit " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " M-Kit Generator", CC.Gold + bulletpoint + CC.Gold + "1x 100% Armor/Weapon Enchant Orb (13-14 Slot)", "", (Object)this.secondColor + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)this.secondColor + bulletpoint + (Object)this.secondColor + "Heroic Rank Crystal", (Object)this.secondColor + bulletpoint + (Object)this.secondColor + "1-2x Random Dungeon Portal", (Object)this.secondColor + bulletpoint + (Object)this.secondColor + "Random Mastery Fallen Hero Bundle"});
    }

    @Override
    public ItemStack getRandomMetaItem(Player player) {
        double chance = Math.random();
        if (chance <= 0.6) {
            return this.setAmount((this.random.nextDouble() <= 0.6 ? DungeonType.ABANDONED_SHIP : DungeonType.DESTROYED_OUTPOST).getDungeonInstance().getDungeonPortal(), this.getRandomOne());
        }
        if (chance <= 0.9) {
            return HeroicRankCrystalAPI.getHeroicRankCrystalItem();
        }
        int chances = this.random.nextInt(5);
        return chances <= 2 ? CommandFallenHero.getGhostMasteryKitBundle() : (chances == 3 ? CommandFallenHero.getNecromancerKitBundle() : CommandFallenHero.getDeathKnightBundle());
    }

    @Override
    public List<ItemStack> getCrateLoot(Player player) {
        ItemStack item;
        ItemStack mask;
        ArrayList items = Lists.newArrayList();
        items.add(ItemUtils.buildItem((Material)Material.BOOK_AND_QUILL, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&f&l2019 Meme Preview"), (String[])new String[]{CC.Gray + "Submit your dankest memes to the", CC.Gray + "intergalactic board of toxicity."}));
        int x = this.random.nextInt(4);
        if (x == 0) {
            items.add(ItemUtils.getTitleVoucher((String)"#NewYearNewMe"));
        }
        if (x == 1) {
            items.add(ItemUtils.getTitleVoucher((String)"NewYears"));
        }
        if (x == 2) {
            items.add(ItemUtils.getTitleVoucher((String)"#2k19"));
        }
        if (x == 3) {
            if (Math.random() <= 0.3) {
                items.add(ItemUtils.getTitleVoucher((String)"#2019"));
            } else {
                items.add(ItemUtils.getTitleVoucher((String)"Cya2k18"));
            }
        }
        if ((mask = new CustomItemBuilder(CustomItemBuilder.CustomItemType.RANDOM_MASK).build()) != null) {
            items.add(mask);
        }
        items.add(new RandomizationScrollBuilder(ItemTier.GOD, 2, 4).build());
        items.add(CCAPI.getCosmicCrate(this.random.nextDouble() <= 0.4 ? Version.JANUARY_2018 : Version.JANUARY_2017, player.getName()));
        ItemStack generator = new CustomItemBuilder(this.random.nextDouble() <= 0.15 ? CustomItemBuilder.CustomItemType.MKIT_GENERATOR : CustomItemBuilder.CustomItemType.GKIT_GENERATOR).build();
        if (generator != null) {
            items.add(this.setAmount(generator, this.getRandomOne()));
        }
        if ((item = new CustomItemBuilder(this.random.nextDouble() < 0.6 ? CustomItemBuilder.CustomItemType.EQUIPMENT_LOOTBOX : CustomItemBuilder.CustomItemType.HEROIC_EQUIPMENT_LOOTBOX, this.getRandomThree()).build()) != null) {
            items.add(this.setAmount(item, this.getRandomOne()));
        }
        items.add(EnchantmentOrb.getEnchantmentOrb((int)100, (int)(this.random.nextInt(2) + 13), (OrbType)(this.random.nextInt(10) <= 6 ? OrbType.ARMOR : OrbType.WEAPON)));
        ItemStack book = new MysteryEnchantBookBuilder(this.random.nextDouble() <= 0.6 ? ItemTier.GOD : ItemTier.HEROIC, 1, 2).build();
        if (book != null) {
            items.add(this.setAmount(book, this.getRandomOne()));
        }
        return items;
    }

    private int getRandomOne() {
        return Math.random() <= 0.3 ? 2 : 1;
    }

    private int getRandomThree() {
        return Math.random() <= 0.1 ? 3 : (Math.random() <= 0.3 ? 2 : 1);
    }

    @Override
    public short getNonColorPane() {
        return DyeColor.YELLOW.getWoolData();
    }

    @Override
    public List<Color> getConclusionFireworkColors() {
        return Lists.newArrayList((Object[])new Color[]{Color.AQUA, Color.YELLOW});
    }

    @Override
    public short getColorPane() {
        return DyeColor.CYAN.getWoolData();
    }
}

