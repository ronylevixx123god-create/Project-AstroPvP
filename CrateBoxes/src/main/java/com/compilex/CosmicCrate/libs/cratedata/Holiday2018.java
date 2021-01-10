/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicDungeons.DungeonsAPI
 *  com.compilex.CosmicDungeons.DungeonsRewardsAPI
 *  com.compilex.CosmicDungeons.dungeons.libs.Dungeon
 *  com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils
 *  com.cosmicpvp.cosmicutils.itembuilder.CustomItemBuilder
 *  com.cosmicpvp.cosmicutils.itembuilder.CustomItemBuilder$CustomItemType
 *  com.cosmicpvp.cosmicutils.itembuilder.MaskBuilder
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.google.common.collect.Lists
 *  me.cosmicpvp.CosmicGlobalKits.CosmicGlobalKitsAPI
 *  me.cosmicpvp.CosmicGlobalKits.commands.CommandFallenHero
 *  me.cosmicpvp.CosmicGlobalKits.libs.MasteryKits
 *  org.arkhamnetwork.enchantments.EnchantmentBook
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
import com.compilex.CosmicDungeons.DungeonsAPI;
import com.compilex.CosmicDungeons.DungeonsRewardsAPI;
import com.compilex.CosmicDungeons.dungeons.libs.Dungeon;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.itembuilder.CustomItemBuilder;
import com.cosmicpvp.cosmicutils.itembuilder.MaskBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import me.cosmicpvp.CosmicGlobalKits.CosmicGlobalKitsAPI;
import me.cosmicpvp.CosmicGlobalKits.commands.CommandFallenHero;
import me.cosmicpvp.CosmicGlobalKits.libs.MasteryKits;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import org.arkhamnetwork.enchantments.EnchantmentOrb;
import org.arkhamnetwork.enchantments.libs.OrbType;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Holiday2018
extends CosmicCrateData {
    private ChatColor mainColor = ChatColor.RED;
    private ChatColor secondColor = ChatColor.WHITE;

    @Override
    public List<String> getDescriptionLore(String username) {
        String bulletpoint = ChatColor.BOLD.toString() + "* ";
        return Lists.newArrayList((Object[])new String[]{(Object)this.mainColor + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)this.mainColor + " at " + (Object)this.secondColor + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Cosmic Rewind", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Limited Edition Titles", CC.YellowB + bulletpoint + (Object)ChatColor.YELLOW + "Santa " + CC.YellowB + "OR" + CC.Yellow + " Reindeer Mask", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", CC.Gold + bulletpoint + CC.Gold + "Holiday 2016 " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " 2017 /cc", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1-2x Random M-Kit Generator", CC.Gold + bulletpoint + CC.Gold + "1-2x Superior Cosmic Crates", CC.Gold + bulletpoint + CC.Gold + "2-4x Heroic Equipment Lootboxes", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "2-4x Holy White Scrolls " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " Heroic Blackscrolls", CC.Gold + bulletpoint + CC.Gold + "1-2x 95-100% Armor/Weapon Enchant Orb (14 Slot)", "", (Object)this.secondColor + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)this.secondColor + bulletpoint + (Object)this.secondColor + "1-5x Random Dungeon Lootbags", (Object)this.secondColor + bulletpoint + (Object)this.secondColor + "Random /mkit Gem", (Object)this.secondColor + bulletpoint + (Object)this.secondColor + "Death Knight /mkit Fallen Hero Bundle"});
    }

    @Override
    public ItemStack getRandomMetaItem(Player player) {
        double chance = Math.random();
        if (chance <= 0.4) {
            return this.setAmount(DungeonsRewardsAPI.getDungeonLootBag((Player)player, (Dungeon)DungeonsAPI.get().getDungeon(this.random.nextDouble() <= 0.2 ? 1 : 0)), this.getRandomFive());
        }
        if (chance <= 0.85) {
            return CosmicGlobalKitsAPI.createMasteryKitItem((MasteryKits)(this.random.nextDouble() <= 0.7 ? (this.random.nextDouble() <= 0.6 ? MasteryKits.GHOST : MasteryKits.NECROMANCER) : MasteryKits.DEATH_KNIGHT), (boolean)true);
        }
        return CommandFallenHero.getDeathKnightBundle();
    }

    @Override
    public List<ItemStack> getCrateLoot(Player player) {
        ItemStack mask;
        ArrayList items = Lists.newArrayList();
        items.add(ItemUtils.buildItem((Material)Material.RECORD_12, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)("&b&lCosmic " + CC.LightPurpleB + "Rewind")), (String[])new String[]{CC.Gray + "An ancient recording of how", CC.Gray + "CosmicPvP has evolved over the", CC.Gray + "years, and how it used to be.", CC.Gray + "What could this be used for?"}));
        int x = this.random.nextInt(4);
        if (x == 0) {
            items.add(ItemUtils.getTitleVoucher((String)"#NutCracker"));
        }
        if (x == 1) {
            items.add(ItemUtils.getTitleVoucher((String)"ButIWasOnCC"));
        }
        if (x == 2) {
            items.add(ItemUtils.getTitleVoucher((String)"#NiceList"));
        }
        if (x == 3) {
            if (Math.random() <= 0.3) {
                items.add(ItemUtils.getTitleVoucher((String)"StockingStuffer"));
            } else {
                items.add(ItemUtils.getTitleVoucher((String)"#NaughtyList"));
            }
        }
        if ((mask = new MaskBuilder(this.random.nextBoolean() ? "SANTA" : "REINDEER").build()) != null) {
            items.add(mask);
        }
        items.add(CCAPI.getCosmicCrate(this.random.nextDouble() <= 0.4 ? Version.HOLIDAY_2017 : Version.HOLIDAY, player.getName()));
        ItemStack mkitItem = new CustomItemBuilder(CustomItemBuilder.CustomItemType.MKIT_GENERATOR, this.getRandomOne()).build();
        if (mkitItem != null) {
            items.add(mkitItem);
        }
        items.add(this.setAmount(CCAPI.getGodlyCosmicCrate(), this.getRandomOne()));
        ItemStack heroicBox = new CustomItemBuilder(CustomItemBuilder.CustomItemType.HEROIC_EQUIPMENT_LOOTBOX, 2 + this.random.nextInt(this.getRandomOne())).build();
        if (heroicBox != null) {
            items.add(heroicBox);
        }
        items.add(this.setAmount(this.random.nextDouble() <= 0.2 ? DungeonsRewardsAPI.getHolyWhiteScroll() : EnchantmentBook.getHeroicBlackscroll((boolean)true), 2 + this.random.nextInt(this.getRandomOne())));
        items.add(ItemUtils.setAmount((ItemStack)EnchantmentOrb.getEnchantmentOrb((int)(95 + this.random.nextInt(6)), (int)14, (OrbType)(this.random.nextInt(10) <= 6 ? OrbType.ARMOR : OrbType.WEAPON)), (int)this.getRandomOne()));
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

    private int getRandomFour() {
        int highestPossible = 2;
        if (Math.random() <= 0.1) {
            highestPossible = 4;
        } else if (Math.random() <= 0.3) {
            highestPossible = 3;
        }
        return this.random.nextInt(highestPossible) + 1;
    }

    @Override
    public short getNonColorPane() {
        return DyeColor.RED.getWoolData();
    }

    @Override
    public List<Color> getConclusionFireworkColors() {
        return Lists.newArrayList((Object[])new Color[]{Color.RED, Color.LIME});
    }

    @Override
    public short getColorPane() {
        return DyeColor.LIME.getWoolData();
    }
}

