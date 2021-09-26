// 
// Decompiled by Procyon v0.5.36
// 

package com.compilex.CosmicCrate.libs.cratedata;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import me.cosmicpvp.CosmicGlobalKits.commands.CommandFallenHero;
import com.cosmicpvp.cosmicutils.itembuilder.LootboxItemBuilder;
import com.compilex.CosmicCrate.api.CCAPI;
import com.compilex.CosmicCrate.libs.Version;
import com.cosmicpvp.cosmicutils.itembuilder.CustomItemBuilder;
import com.compilex.CosmicDungeons.DungeonsRewardsAPI;
import com.compilex.CosmicDungeons.dungeons.libs.DungeonType;
import me.ifamasssxd.killtracker.KillTrackerAPI;
import me.ifamasssxd.killtracker.listeners.Tracker;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import org.bukkit.Material;
import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import me.cosmicpvp.CosmicGlobalKits.CosmicGlobalKitsAPI;
import me.cosmicpvp.CosmicGlobalKits.libs.MasteryKits;
import com.cosmicpvp.cosmicutils.itembuilder.SpeedTrinketBuilder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import com.google.common.collect.Lists;
import com.cosmicpvp.cosmicutils.utils.CC;
import java.util.List;
import org.bukkit.ChatColor;
import com.compilex.CosmicCrate.libs.CosmicCrateData;

public class Febuary2019 extends CosmicCrateData
{
    private ChatColor mainColor;
    private ChatColor secondColor;
    
    public Febuary2019() {
        this.mainColor = ChatColor.AQUA;
        this.secondColor = ChatColor.RED;
    }
    
    @Override
    public List<String> getDescriptionLore(final String username) {
        final String bulletpoint = ChatColor.BOLD.toString() + "* ";
        return (List<String>)Lists.newArrayList((Object[])new String[] { this.mainColor + "Unlocked by " + ChatColor.BOLD + username + this.mainColor + " at " + this.secondColor + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Cozy Vibes", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Limited Edition Titles", CC.YellowB + bulletpoint + ChatColor.YELLOW + "Godly Soul Tracker", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", CC.Gold + bulletpoint + CC.Gold + "1-2x Random Dungeon Lootbag", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "2-4 Random Heroic Upgrade", CC.Gold + bulletpoint + CC.Gold + "1-5x Random Heroic G-Kit " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " M-Kit Generator", CC.Gold + bulletpoint + CC.Gold + "1x Lootbox: Genesis " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " Snow Day", CC.Gold + bulletpoint + CC.Gold + "1x Random Mastery Fallen Hero Bundle", CC.Gold + bulletpoint + CC.Gold + "1x February 2018 " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " 2016 /cc", "", this.secondColor + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", this.secondColor + bulletpoint + this.secondColor + "Dragon Slayer Upgrade Gem", this.secondColor + bulletpoint + this.secondColor + "Random /mkit Gem", this.secondColor + bulletpoint + this.secondColor + "Speed Trinket" });
    }
    
    @Override
    public ItemStack getRandomMetaItem(final Player player) {
        final double chance = Math.random();
        if (chance <= 0.3) {
            return new SpeedTrinketBuilder().build();
        }
        if (chance <= 0.75) {
            return CosmicGlobalKitsAPI.createMasteryKitItem((this.random.nextDouble() <= 0.7) ? ((this.random.nextDouble() <= 0.6) ? MasteryKits.GHOST : MasteryKits.NECROMANCER) : MasteryKits.DEATH_KNIGHT, true);
        }
        return CosmicArmorSetsAPI.createArmorSetUpgrade(ArmorSet.DRAGON_SLAYER, (double)(75 + this.random.nextInt(26)));
    }
    
    @Override
    public List<ItemStack> getCrateLoot(final Player player) {
        final List<ItemStack> items = (List<ItemStack>)Lists.newArrayList();
        items.add(ItemUtils.buildItem(Material.BOOK_AND_QUILL, (short)0, ChatColor.translateAlternateColorCodes('&', "&f&lCozy Vibes"), new String[] { CC.Gray + "Submit your dankest memes to the", CC.Gray + "intergalactic board of toxicity." }));
        final int x = this.random.nextInt(4);
        if (x == 0) {
            items.add(ItemUtils.getTitleVoucher("#Numb"));
        }
        if (x == 1) {
            items.add(ItemUtils.getTitleVoucher("FrostBitten"));
        }
        if (x == 2) {
            items.add(ItemUtils.getTitleVoucher("HappilyAlone"));
        }
        if (x == 3) {
            if (Math.random() <= 0.3) {
                items.add(ItemUtils.getTitleVoucher("Depression"));
            }
            else {
                items.add(ItemUtils.getTitleVoucher("LoveIsALie"));
            }
        }
        items.add(KillTrackerAPI.buildTracker(Tracker.Godly));
        items.add(this.setAmount(DungeonsRewardsAPI.getDungeonLootBag(player, (this.random.nextDouble() <= 0.8) ? DungeonType.ABANDONED_SHIP.getDungeonInstance() : DungeonType.DESTROYED_OUTPOST.getDungeonInstance()), (this.random.nextDouble() >= 0.7) ? this.getRandomOne() : 1));
        final ItemStack generator = new CustomItemBuilder((this.random.nextDouble() <= 0.15) ? CustomItemBuilder.CustomItemType.MKIT_GENERATOR : CustomItemBuilder.CustomItemType.GKIT_GENERATOR).build();
        if (generator != null) {
            items.add(this.setAmount(generator, this.getRandomFive()));
        }
        items.add(this.setAmount(CosmicArmorSetsAPI.createRandomArmorSetUpgrade(), 2 + ((this.random.nextDouble() <= 0.4) ? this.getRandomOne() : 0)));
        items.add(CCAPI.getCosmicCrate((this.random.nextDouble() <= 0.4) ? Version.FEBUARY_2018 : Version.FEBRUARY, player.getName()));
        final ItemStack lootbox = new LootboxItemBuilder(this.random.nextBoolean() ? "lootbox_genesis" : "lootbox_snowday").build();
        if (lootbox != null) {
            items.add(lootbox);
        }
        final int chances = this.random.nextInt(5);
        items.add((chances <= 2) ? CommandFallenHero.getGhostMasteryKitBundle() : ((chances == 3) ? CommandFallenHero.getNecromancerKitBundle() : CommandFallenHero.getDeathKnightBundle()));
        return items;
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
    
    private int getRandomOne() {
        return (Math.random() <= 0.3) ? 2 : 1;
    }
    
    private int getRandomThree() {
        return (Math.random() <= 0.1) ? 3 : ((Math.random() <= 0.3) ? 2 : 1);
    }
    
    @Override
    public short getNonColorPane() {
        return DyeColor.CYAN.getWoolData();
    }
    
    @Override
    public List<Color> getConclusionFireworkColors() {
        return (List<Color>)Lists.newArrayList((Object[])new Color[] { Color.AQUA, Color.YELLOW });
    }
    
    @Override
    public short getColorPane() {
        return DyeColor.RED.getWoolData();
    }
}
