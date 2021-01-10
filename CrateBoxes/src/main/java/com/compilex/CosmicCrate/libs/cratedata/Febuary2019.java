/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicArmorSets.CosmicArmorSetsAPI
 *  com.compilex.CosmicArmorSets.enums.ArmorSet
 *  com.compilex.CosmicDungeons.DungeonsRewardsAPI
 *  com.compilex.CosmicDungeons.dungeons.libs.Dungeon
 *  com.compilex.CosmicDungeons.dungeons.libs.DungeonType
 *  com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils
 *  com.cosmicpvp.cosmicutils.itembuilder.CustomItemBuilder
 *  com.cosmicpvp.cosmicutils.itembuilder.CustomItemBuilder$CustomItemType
 *  com.cosmicpvp.cosmicutils.itembuilder.LootboxItemBuilder
 *  com.cosmicpvp.cosmicutils.itembuilder.SpeedTrinketBuilder
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.google.common.collect.Lists
 *  me.cosmicpvp.CosmicGlobalKits.CosmicGlobalKitsAPI
 *  me.cosmicpvp.CosmicGlobalKits.commands.CommandFallenHero
 *  me.cosmicpvp.CosmicGlobalKits.libs.MasteryKits
 *  me.ifamasssxd.killtracker.KillTrackerAPI
 *  me.ifamasssxd.killtracker.listeners.Tracker
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
import com.compilex.CosmicCrate.api.CCAPI;
import com.compilex.CosmicCrate.libs.CosmicCrateData;
import com.compilex.CosmicCrate.libs.Version;
import com.compilex.CosmicDungeons.DungeonsRewardsAPI;
import com.compilex.CosmicDungeons.dungeons.libs.Dungeon;
import com.compilex.CosmicDungeons.dungeons.libs.DungeonType;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.itembuilder.CustomItemBuilder;
import com.cosmicpvp.cosmicutils.itembuilder.LootboxItemBuilder;
import com.cosmicpvp.cosmicutils.itembuilder.SpeedTrinketBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import me.cosmicpvp.CosmicGlobalKits.CosmicGlobalKitsAPI;
import me.cosmicpvp.CosmicGlobalKits.commands.CommandFallenHero;
import me.cosmicpvp.CosmicGlobalKits.libs.MasteryKits;
import me.ifamasssxd.killtracker.KillTrackerAPI;
import me.ifamasssxd.killtracker.listeners.Tracker;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Febuary2019
extends CosmicCrateData {
    private ChatColor mainColor = ChatColor.AQUA;
    private ChatColor secondColor = ChatColor.RED;

    @Override
    public List<String> getDescriptionLore(String username) {
        String bulletpoint = ChatColor.BOLD.toString() + "* ";
        return Lists.newArrayList((Object[])new String[]{(Object)this.mainColor + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)this.mainColor + " at " + (Object)this.secondColor + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Cozy Vibes", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Limited Edition Titles", CC.YellowB + bulletpoint + (Object)ChatColor.YELLOW + "Godly Soul Tracker", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", CC.Gold + bulletpoint + CC.Gold + "1-2x Random Dungeon Lootbag", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "2-4 Random Heroic Upgrade", CC.Gold + bulletpoint + CC.Gold + "1-5x Random Heroic G-Kit " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " M-Kit Generator", CC.Gold + bulletpoint + CC.Gold + "1x Lootbox: Genesis " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " Snow Day", CC.Gold + bulletpoint + CC.Gold + "1x Random Mastery Fallen Hero Bundle", CC.Gold + bulletpoint + CC.Gold + "1x February 2018 " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " 2016 /cc", "", (Object)this.secondColor + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)this.secondColor + bulletpoint + (Object)this.secondColor + "Dragon Slayer Upgrade Gem", (Object)this.secondColor + bulletpoint + (Object)this.secondColor + "Random /mkit Gem", (Object)this.secondColor + bulletpoint + (Object)this.secondColor + "Speed Trinket"});
    }

    @Override
    public ItemStack getRandomMetaItem(Player player) {
        double chance = Math.random();
        if (chance <= 0.3) {
            return new SpeedTrinketBuilder().build();
        }
        if (chance <= 0.75) {
            return CosmicGlobalKitsAPI.createMasteryKitItem((MasteryKits)(this.random.nextDouble() <= 0.7 ? (this.random.nextDouble() <= 0.6 ? MasteryKits.GHOST : MasteryKits.NECROMANCER) : MasteryKits.DEATH_KNIGHT), (boolean)true);
        }
        return CosmicArmorSetsAPI.createArmorSetUpgrade((ArmorSet)ArmorSet.DRAGON_SLAYER, (double)(75 + this.random.nextInt(26)));
    }

    @Override
    public List<ItemStack> getCrateLoot(Player player) {
        int chances;
        ArrayList items = Lists.newArrayList();
        items.add(ItemUtils.buildItem((Material)Material.BOOK_AND_QUILL, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&f&lCozy Vibes"), (String[])new String[]{CC.Gray + "Submit your dankest memes to the", CC.Gray + "intergalactic board of toxicity."}));
        int x = this.random.nextInt(4);
        if (x == 0) {
            items.add(ItemUtils.getTitleVoucher((String)"#Numb"));
        }
        if (x == 1) {
            items.add(ItemUtils.getTitleVoucher((String)"FrostBitten"));
        }
        if (x == 2) {
            items.add(ItemUtils.getTitleVoucher((String)"HappilyAlone"));
        }
        if (x == 3) {
            if (Math.random() <= 0.3) {
                items.add(ItemUtils.getTitleVoucher((String)"Depression"));
            } else {
                items.add(ItemUtils.getTitleVoucher((String)"LoveIsALie"));
            }
        }
        items.add(KillTrackerAPI.buildTracker((Tracker)Tracker.Godly));
        items.add(this.setAmount(DungeonsRewardsAPI.getDungeonLootBag((Player)player, (Dungeon)(this.random.nextDouble() <= 0.8 ? DungeonType.ABANDONED_SHIP.getDungeonInstance() : DungeonType.DESTROYED_OUTPOST.getDungeonInstance())), this.random.nextDouble() >= 0.7 ? this.getRandomOne() : 1));
        ItemStack generator = new CustomItemBuilder(this.random.nextDouble() <= 0.15 ? CustomItemBuilder.CustomItemType.MKIT_GENERATOR : CustomItemBuilder.CustomItemType.GKIT_GENERATOR).build();
        if (generator != null) {
            items.add(this.setAmount(generator, this.getRandomFive()));
        }
        items.add(this.setAmount(CosmicArmorSetsAPI.createRandomArmorSetUpgrade(), 2 + (this.random.nextDouble() <= 0.4 ? this.getRandomOne() : 0)));
        items.add(CCAPI.getCosmicCrate(this.random.nextDouble() <= 0.4 ? Version.FEBUARY_2018 : Version.FEBRUARY, player.getName()));
        ItemStack lootbox = new LootboxItemBuilder(this.random.nextBoolean() ? "lootbox_genesis" : "lootbox_snowday").build();
        if (lootbox != null) {
            items.add(lootbox);
        }
        items.add((chances = this.random.nextInt(5)) <= 2 ? CommandFallenHero.getGhostMasteryKitBundle() : (chances == 3 ? CommandFallenHero.getNecromancerKitBundle() : CommandFallenHero.getDeathKnightBundle()));
        return items;
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

    private int getRandomOne() {
        return Math.random() <= 0.3 ? 2 : 1;
    }

    private int getRandomThree() {
        return Math.random() <= 0.1 ? 3 : (Math.random() <= 0.3 ? 2 : 1);
    }

    @Override
    public short getNonColorPane() {
        return DyeColor.CYAN.getWoolData();
    }

    @Override
    public List<Color> getConclusionFireworkColors() {
        return Lists.newArrayList((Object[])new Color[]{Color.AQUA, Color.YELLOW});
    }

    @Override
    public short getColorPane() {
        return DyeColor.RED.getWoolData();
    }
}

