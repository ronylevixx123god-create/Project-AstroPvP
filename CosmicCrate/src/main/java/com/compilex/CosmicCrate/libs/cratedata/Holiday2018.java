// 
// Decompiled by Procyon v0.5.36
// 

package com.compilex.CosmicCrate.libs.cratedata;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.arkhamnetwork.enchantments.EnchantmentOrb;
import org.arkhamnetwork.enchantments.libs.OrbType;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import com.cosmicpvp.cosmicutils.itembuilder.CustomItemBuilder;
import com.compilex.CosmicCrate.api.CCAPI;
import com.compilex.CosmicCrate.libs.Version;
import com.cosmicpvp.cosmicutils.itembuilder.MaskBuilder;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import org.bukkit.Material;
import me.cosmicpvp.CosmicGlobalKits.commands.CommandFallenHero;
import me.cosmicpvp.CosmicGlobalKits.CosmicGlobalKitsAPI;
import me.cosmicpvp.CosmicGlobalKits.libs.MasteryKits;
import com.compilex.CosmicDungeons.DungeonsRewardsAPI;
import com.compilex.CosmicDungeons.DungeonsAPI;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import com.google.common.collect.Lists;
import com.cosmicpvp.cosmicutils.utils.CC;
import java.util.List;
import org.bukkit.ChatColor;
import com.compilex.CosmicCrate.libs.CosmicCrateData;

public class Holiday2018 extends CosmicCrateData
{
    private ChatColor mainColor;
    private ChatColor secondColor;
    
    public Holiday2018() {
        this.mainColor = ChatColor.RED;
        this.secondColor = ChatColor.WHITE;
    }
    
    @Override
    public List<String> getDescriptionLore(final String username) {
        final String bulletpoint = ChatColor.BOLD.toString() + "* ";
        return (List<String>)Lists.newArrayList((Object[])new String[] { this.mainColor + "Unlocked by " + ChatColor.BOLD + username + this.mainColor + " at " + this.secondColor + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Cosmic Rewind", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Limited Edition Titles", CC.YellowB + bulletpoint + ChatColor.YELLOW + "Santa " + CC.YellowB + "OR" + CC.Yellow + " Reindeer Mask", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", CC.Gold + bulletpoint + CC.Gold + "Holiday 2016 " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " 2017 /cc", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1-2x Random M-Kit Generator", CC.Gold + bulletpoint + CC.Gold + "1-2x Superior Cosmic Crates", CC.Gold + bulletpoint + CC.Gold + "2-4x Heroic Equipment Lootboxes", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "2-4x Holy White Scrolls " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " Heroic Blackscrolls", CC.Gold + bulletpoint + CC.Gold + "1-2x 95-100% Armor/Weapon Enchant Orb (14 Slot)", "", this.secondColor + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", this.secondColor + bulletpoint + this.secondColor + "1-5x Random Dungeon Lootbags", this.secondColor + bulletpoint + this.secondColor + "Random /mkit Gem", this.secondColor + bulletpoint + this.secondColor + "Death Knight /mkit Fallen Hero Bundle" });
    }
    
    @Override
    public ItemStack getRandomMetaItem(final Player player) {
        final double chance = Math.random();
        if (chance <= 0.4) {
            return this.setAmount(DungeonsRewardsAPI.getDungeonLootBag(player, DungeonsAPI.get().getDungeon((int)((this.random.nextDouble() <= 0.2) ? 1 : 0))), this.getRandomFive());
        }
        if (chance <= 0.85) {
            return CosmicGlobalKitsAPI.createMasteryKitItem((this.random.nextDouble() <= 0.7) ? ((this.random.nextDouble() <= 0.6) ? MasteryKits.GHOST : MasteryKits.NECROMANCER) : MasteryKits.DEATH_KNIGHT, true);
        }
        return CommandFallenHero.getDeathKnightBundle();
    }
    
    @Override
    public List<ItemStack> getCrateLoot(final Player player) {
        final List<ItemStack> items = (List<ItemStack>)Lists.newArrayList();
        items.add(ItemUtils.buildItem(Material.RECORD_12, (short)0, ChatColor.translateAlternateColorCodes('&', "&b&lCosmic " + CC.LightPurpleB + "Rewind"), new String[] { CC.Gray + "An ancient recording of how", CC.Gray + "CosmicPvP has evolved over the", CC.Gray + "years, and how it used to be.", CC.Gray + "What could this be used for?" }));
        final int x = this.random.nextInt(4);
        if (x == 0) {
            items.add(ItemUtils.getTitleVoucher("#NutCracker"));
        }
        if (x == 1) {
            items.add(ItemUtils.getTitleVoucher("ButIWasOnCC"));
        }
        if (x == 2) {
            items.add(ItemUtils.getTitleVoucher("#NiceList"));
        }
        if (x == 3) {
            if (Math.random() <= 0.3) {
                items.add(ItemUtils.getTitleVoucher("StockingStuffer"));
            }
            else {
                items.add(ItemUtils.getTitleVoucher("#NaughtyList"));
            }
        }
        final ItemStack mask = new MaskBuilder(this.random.nextBoolean() ? "SANTA" : "REINDEER").build();
        if (mask != null) {
            items.add(mask);
        }
        items.add(CCAPI.getCosmicCrate((this.random.nextDouble() <= 0.4) ? Version.HOLIDAY_2017 : Version.HOLIDAY, player.getName()));
        final ItemStack mkitItem = new CustomItemBuilder(CustomItemBuilder.CustomItemType.MKIT_GENERATOR, this.getRandomOne()).build();
        if (mkitItem != null) {
            items.add(mkitItem);
        }
        items.add(this.setAmount(CCAPI.getGodlyCosmicCrate(), this.getRandomOne()));
        final ItemStack heroicBox = new CustomItemBuilder(CustomItemBuilder.CustomItemType.HEROIC_EQUIPMENT_LOOTBOX, 2 + this.random.nextInt(this.getRandomOne())).build();
        if (heroicBox != null) {
            items.add(heroicBox);
        }
        items.add(this.setAmount((this.random.nextDouble() <= 0.2) ? DungeonsRewardsAPI.getHolyWhiteScroll() : EnchantmentBook.getHeroicBlackscroll(true), 2 + this.random.nextInt(this.getRandomOne())));
        items.add(ItemUtils.setAmount(EnchantmentOrb.getEnchantmentOrb(95 + this.random.nextInt(6), 14, (this.random.nextInt(10) <= 6) ? OrbType.ARMOR : OrbType.WEAPON), this.getRandomOne()));
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
    
    private int getRandomFour() {
        int highestPossible = 2;
        if (Math.random() <= 0.1) {
            highestPossible = 4;
        }
        else if (Math.random() <= 0.3) {
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
        return (List<Color>)Lists.newArrayList((Object[])new Color[] { Color.RED, Color.LIME });
    }
    
    @Override
    public short getColorPane() {
        return DyeColor.LIME.getWoolData();
    }
}
