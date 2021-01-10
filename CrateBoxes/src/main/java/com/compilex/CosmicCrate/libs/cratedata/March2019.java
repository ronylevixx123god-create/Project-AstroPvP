/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicDungeons.DungeonsRewardsAPI
 *  com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils
 *  com.cosmicpvp.cosmicutils.itembuilder.CommandItemBuilder
 *  com.cosmicpvp.cosmicutils.itembuilder.CustomItemBuilder
 *  com.cosmicpvp.cosmicutils.itembuilder.CustomItemBuilder$CustomItemType
 *  com.cosmicpvp.cosmicutils.itembuilder.LootboxItemBuilder
 *  com.cosmicpvp.cosmicutils.itembuilder.PhoenixFeatherBuilder
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.google.common.collect.Lists
 *  me.cosmicpvp.CosmicGlobalKits.commands.CommandFallenHero
 *  me.ifamasssxd.cosmicdisguises.managers.CosmicDisguiseAPI
 *  me.ifamasssxd.killtracker.KillTrackerAPI
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
import com.compilex.CosmicDungeons.DungeonsRewardsAPI;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.itembuilder.CommandItemBuilder;
import com.cosmicpvp.cosmicutils.itembuilder.CustomItemBuilder;
import com.cosmicpvp.cosmicutils.itembuilder.LootboxItemBuilder;
import com.cosmicpvp.cosmicutils.itembuilder.PhoenixFeatherBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import me.cosmicpvp.CosmicGlobalKits.commands.CommandFallenHero;
import me.ifamasssxd.cosmicdisguises.managers.CosmicDisguiseAPI;
import me.ifamasssxd.killtracker.KillTrackerAPI;
import org.arkhamnetwork.enchantments.EnchantmentOrb;
import org.arkhamnetwork.enchantments.libs.OrbType;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class March2019
extends CosmicCrateData {
    private ChatColor mainColor = ChatColor.DARK_AQUA;
    private ChatColor secondColor = ChatColor.BLUE;

    @Override
    public List<String> getDescriptionLore(String username) {
        String bulletpoint = ChatColor.BOLD.toString() + "* ";
        return Lists.newArrayList((Object[])new String[]{(Object)this.mainColor + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)this.mainColor + " at " + (Object)this.secondColor + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Half Thawed", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", CC.YellowB + bulletpoint + CC.Yellow + "Limited Edition Titles", CC.YellowB + bulletpoint + CC.Yellow + "Pig Zombie Disguise", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", CC.Gold + bulletpoint + CC.Gold + "1-2x Holy White Scroll", CC.Gold + bulletpoint + CC.Gold + "2-3x Random Mask Generator", CC.Gold + bulletpoint + CC.Gold + "1-2x 50% Armor/Weapon Enchantment Orb (14 slot)", CC.Gold + bulletpoint + CC.Gold + "1x Phoenix Feather " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " 5,000-15,000 Player Souls", CC.Gold + bulletpoint + CC.Gold + "1x Lootbox: Survival Kit " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " Icy Adventures", CC.Gold + bulletpoint + CC.Gold + "1x March 2016, 2017, " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " 2018 /cc", "", (Object)this.secondColor + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)this.secondColor + bulletpoint + (Object)this.secondColor + "1-2x Superior /cc", (Object)this.secondColor + bulletpoint + (Object)this.secondColor + "Necromancer Fallen Hero Bundle", (Object)this.secondColor + bulletpoint + (Object)this.secondColor + "CosmicPvP: Legacy Top Rank"});
    }

    @Override
    public ItemStack getRandomMetaItem(Player player) {
        double chance = Math.random();
        if (chance <= 0.05) {
            CommandItemBuilder cmdItem = new CommandItemBuilder();
            String rankName = "Cosmonaut";
            int rankTier = 5;
            cmdItem.setCommand("queue legacyplanet ip:0.0.0.0 package:Kit Legacy id:kitlegacy_{playerName} username:{playerName} uuid:{playerUUID} rankquest giveitems {playerName} " + rankTier);
            cmdItem.setMessage("&e&l(!) &eYou have successfully redeemed a &b&lCosmicPvP: &c&lLegacy &erank! (&7" + rankName + "&e), login to legacy when it launches and run /confirm!");
            cmdItem.setItemName("&6&lLEGACY RANK \"&f" + rankName + "&6&l\"");
            cmdItem.setItemLore(new String[]{"&7Right-click this item to unlock", "&7the " + rankName + " Rank (Tier " + rankTier + ") on the", "&7unreleased Legacy Alien Planet.", "", "&b&lCosmicPvP: &c&lLegacy &7launches on", "&7March 8th, 3:00pm CST"});
            return cmdItem.build();
        }
        if (chance <= 0.7) {
            return this.setAmount(CCAPI.getGodlyCosmicCrate(), this.getRandomOne());
        }
        return CommandFallenHero.getNecromancerKitBundle();
    }

    @Override
    public List<ItemStack> getCrateLoot(Player player) {
        ArrayList items = Lists.newArrayList();
        items.add(ItemUtils.buildItem((Material)Material.LAPIS_ORE, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&f&lHalf Thawed"), (String[])new String[]{CC.Gray + "The winter has felt infinite,", CC.Gray + "but the salvation of spring", CC.Gray + "is finally on the horizon."}));
        int x = this.random.nextInt(4);
        if (x == 0) {
            items.add(ItemUtils.getTitleVoucher((String)"Lucky"));
        }
        if (x == 1) {
            items.add(ItemUtils.getTitleVoucher((String)"#GetLucky"));
        }
        if (x == 2) {
            items.add(ItemUtils.getTitleVoucher((String)"LeprachaunsOP"));
        }
        if (x == 3) {
            if (Math.random() <= 0.3) {
                items.add(ItemUtils.getTitleVoucher((String)"ImDrunk"));
            } else {
                items.add(ItemUtils.getTitleVoucher((String)"#GotLucky"));
            }
        }
        items.add(CosmicDisguiseAPI.createRedeemableDisguise((String)"PIG_ZOMBIE"));
        ItemStack whitescroll = DungeonsRewardsAPI.getHolyWhiteScroll();
        whitescroll.setAmount(this.getRandomOne());
        items.add(whitescroll);
        ItemStack mask = new CustomItemBuilder(CustomItemBuilder.CustomItemType.RANDOM_MASK).build();
        mask.setAmount(2 + new Random().nextInt(2));
        if (mask != null) {
            items.add(mask);
        }
        items.add(CCAPI.getCosmicCrate(this.random.nextDouble() <= 0.2 ? Version.MARCH_2018 : (this.random.nextDouble() < 0.4 ? Version.MARCH_2017 : Version.MARCH), player.getName()));
        ItemStack lootbox = new LootboxItemBuilder(this.random.nextBoolean() ? "lootbox_survival" : "lootbox_icy").build();
        if (lootbox != null) {
            items.add(lootbox);
        }
        ItemStack orbs = EnchantmentOrb.getEnchantmentOrb((int)50, (int)14, (OrbType)(this.random.nextInt(10) <= 7 ? OrbType.ARMOR : OrbType.WEAPON));
        orbs.setAmount(this.getRandomOne());
        items.add(orbs);
        if (Math.random() < 0.35) {
            items.add(new PhoenixFeatherBuilder().build());
        } else {
            items.add(KillTrackerAPI.createSoulShard((int)(5000 + new Random().nextInt(10001))));
        }
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

