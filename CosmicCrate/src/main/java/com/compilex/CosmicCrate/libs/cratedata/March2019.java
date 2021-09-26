// 
// Decompiled by Procyon v0.5.36
// 

package com.compilex.CosmicCrate.libs.cratedata;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import me.ifamasssxd.killtracker.KillTrackerAPI;
import com.cosmicpvp.cosmicutils.itembuilder.PhoenixFeatherBuilder;
import org.arkhamnetwork.enchantments.EnchantmentOrb;
import org.arkhamnetwork.enchantments.libs.OrbType;
import com.cosmicpvp.cosmicutils.itembuilder.LootboxItemBuilder;
import com.compilex.CosmicCrate.libs.Version;
import java.util.Random;
import com.cosmicpvp.cosmicutils.itembuilder.CustomItemBuilder;
import com.compilex.CosmicDungeons.DungeonsRewardsAPI;
import me.ifamasssxd.cosmicdisguises.managers.CosmicDisguiseAPI;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import org.bukkit.Material;
import me.cosmicpvp.CosmicGlobalKits.commands.CommandFallenHero;
import com.compilex.CosmicCrate.api.CCAPI;
import com.cosmicpvp.cosmicutils.itembuilder.CommandItemBuilder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import com.google.common.collect.Lists;
import com.cosmicpvp.cosmicutils.utils.CC;
import java.util.List;
import org.bukkit.ChatColor;
import com.compilex.CosmicCrate.libs.CosmicCrateData;

public class March2019 extends CosmicCrateData
{
    private ChatColor mainColor;
    private ChatColor secondColor;
    
    public March2019() {
        this.mainColor = ChatColor.DARK_AQUA;
        this.secondColor = ChatColor.BLUE;
    }
    
    @Override
    public List<String> getDescriptionLore(final String username) {
        final String bulletpoint = ChatColor.BOLD.toString() + "* ";
        return (List<String>)Lists.newArrayList((Object[])new String[] { this.mainColor + "Unlocked by " + ChatColor.BOLD + username + this.mainColor + " at " + this.secondColor + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Half Thawed", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", CC.YellowB + bulletpoint + CC.Yellow + "Limited Edition Titles", CC.YellowB + bulletpoint + CC.Yellow + "Pig Zombie Disguise", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", CC.Gold + bulletpoint + CC.Gold + "1-2x Holy White Scroll", CC.Gold + bulletpoint + CC.Gold + "2-3x Random Mask Generator", CC.Gold + bulletpoint + CC.Gold + "1-2x 50% Armor/Weapon Enchantment Orb (14 slot)", CC.Gold + bulletpoint + CC.Gold + "1x Phoenix Feather " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " 5,000-15,000 Player Souls", CC.Gold + bulletpoint + CC.Gold + "1x Lootbox: Survival Kit " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " Icy Adventures", CC.Gold + bulletpoint + CC.Gold + "1x March 2016, 2017, " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " 2018 /cc", "", this.secondColor + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", this.secondColor + bulletpoint + this.secondColor + "1-2x Superior /cc", this.secondColor + bulletpoint + this.secondColor + "Necromancer Fallen Hero Bundle", this.secondColor + bulletpoint + this.secondColor + "CosmicPvP: Legacy Top Rank" });
    }
    
    @Override
    public ItemStack getRandomMetaItem(final Player player) {
        final double chance = Math.random();
        if (chance <= 0.05) {
            final CommandItemBuilder cmdItem = new CommandItemBuilder();
            final String rankName = "Cosmonaut";
            final int rankTier = 5;
            cmdItem.setCommand("queue legacyplanet ip:0.0.0.0 package:Kit Legacy id:kitlegacy_{playerName} username:{playerName} uuid:{playerUUID} rankquest giveitems {playerName} " + rankTier);
            cmdItem.setMessage("&e&l(!) &eYou have successfully redeemed a &b&lCosmicPvP: &c&lLegacy &erank! (&7" + rankName + "&e), login to legacy when it launches and run /confirm!");
            cmdItem.setItemName("&6&lLEGACY RANK \"&f" + rankName + "&6&l\"");
            cmdItem.setItemLore(new String[] { "&7Right-click this item to unlock", "&7the " + rankName + " Rank (Tier " + rankTier + ") on the", "&7unreleased Legacy Alien Planet.", "", "&b&lCosmicPvP: &c&lLegacy &7launches on", "&7March 8th, 3:00pm CST" });
            return cmdItem.build();
        }
        if (chance <= 0.7) {
            return this.setAmount(CCAPI.getGodlyCosmicCrate(), this.getRandomOne());
        }
        return CommandFallenHero.getNecromancerKitBundle();
    }
    
    @Override
    public List<ItemStack> getCrateLoot(final Player player) {
        final List<ItemStack> items = (List<ItemStack>)Lists.newArrayList();
        items.add(ItemUtils.buildItem(Material.LAPIS_ORE, (short)0, ChatColor.translateAlternateColorCodes('&', "&f&lHalf Thawed"), new String[] { CC.Gray + "The winter has felt infinite,", CC.Gray + "but the salvation of spring", CC.Gray + "is finally on the horizon." }));
        final int x = this.random.nextInt(4);
        if (x == 0) {
            items.add(ItemUtils.getTitleVoucher("Lucky"));
        }
        if (x == 1) {
            items.add(ItemUtils.getTitleVoucher("#GetLucky"));
        }
        if (x == 2) {
            items.add(ItemUtils.getTitleVoucher("LeprachaunsOP"));
        }
        if (x == 3) {
            if (Math.random() <= 0.3) {
                items.add(ItemUtils.getTitleVoucher("ImDrunk"));
            }
            else {
                items.add(ItemUtils.getTitleVoucher("#GotLucky"));
            }
        }
        items.add(CosmicDisguiseAPI.createRedeemableDisguise("PIG_ZOMBIE"));
        final ItemStack whitescroll = DungeonsRewardsAPI.getHolyWhiteScroll();
        whitescroll.setAmount(this.getRandomOne());
        items.add(whitescroll);
        final ItemStack mask = new CustomItemBuilder(CustomItemBuilder.CustomItemType.RANDOM_MASK).build();
        mask.setAmount(2 + new Random().nextInt(2));
        if (mask != null) {
            items.add(mask);
        }
        items.add(CCAPI.getCosmicCrate((this.random.nextDouble() <= 0.2) ? Version.MARCH_2018 : ((this.random.nextDouble() < 0.4) ? Version.MARCH_2017 : Version.MARCH), player.getName()));
        final ItemStack lootbox = new LootboxItemBuilder(this.random.nextBoolean() ? "lootbox_survival" : "lootbox_icy").build();
        if (lootbox != null) {
            items.add(lootbox);
        }
        final ItemStack orbs = EnchantmentOrb.getEnchantmentOrb(50, 14, (this.random.nextInt(10) <= 7) ? OrbType.ARMOR : OrbType.WEAPON);
        orbs.setAmount(this.getRandomOne());
        items.add(orbs);
        if (Math.random() < 0.35) {
            items.add(new PhoenixFeatherBuilder().build());
        }
        else {
            items.add(KillTrackerAPI.createSoulShard(5000 + new Random().nextInt(10001)));
        }
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
