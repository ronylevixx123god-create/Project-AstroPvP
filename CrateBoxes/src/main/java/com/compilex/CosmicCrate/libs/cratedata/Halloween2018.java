/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicArmorSets.CosmicArmorSetsAPI
 *  com.compilex.CosmicDungeons.DungeonsRewardsAPI
 *  com.compilex.CosmicDungeons.dungeons.libs.Dungeon
 *  com.compilex.CosmicDungeons.dungeons.libs.DungeonType
 *  com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils
 *  com.cosmicpvp.cosmicutils.itembuilder.MaskBuilder
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.google.common.collect.Lists
 *  me.cosmicpvp.CosmicGlobalKits.CosmicGlobalKitsAPI
 *  me.cosmicpvp.CosmicGlobalKits.commands.CommandFallenHero
 *  me.cosmicpvp.CosmicGlobalKits.libs.MasteryKits
 *  org.arkhamnetwork.Arkkit.patches.EquipmentLootboxes
 *  org.arkhamnetwork.Arkkit.plugins.ArkPlugins
 *  org.arkhamnetwork.enchantments.EnchantmentBook
 *  org.arkhamnetwork.enchantments.EnchantmentOrb
 *  org.arkhamnetwork.enchantments.libs.OrbType
 *  org.arkhamnetwork.enchantments.util.TieredEnchantments
 *  org.bukkit.ChatColor
 *  org.bukkit.Color
 *  org.bukkit.DyeColor
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package com.compilex.CosmicCrate.libs.cratedata;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicCrate.api.CCAPI;
import com.compilex.CosmicCrate.libs.CosmicCrateData;
import com.compilex.CosmicCrate.libs.Version;
import com.compilex.CosmicDungeons.DungeonsRewardsAPI;
import com.compilex.CosmicDungeons.dungeons.libs.Dungeon;
import com.compilex.CosmicDungeons.dungeons.libs.DungeonType;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.itembuilder.MaskBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import me.cosmicpvp.CosmicGlobalKits.CosmicGlobalKitsAPI;
import me.cosmicpvp.CosmicGlobalKits.commands.CommandFallenHero;
import me.cosmicpvp.CosmicGlobalKits.libs.MasteryKits;
import org.arkhamnetwork.Arkkit.patches.EquipmentLootboxes;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import org.arkhamnetwork.enchantments.EnchantmentOrb;
import org.arkhamnetwork.enchantments.libs.OrbType;
import org.arkhamnetwork.enchantments.util.TieredEnchantments;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Halloween2018
extends CosmicCrateData {
    private ChatColor mainColor = ChatColor.GOLD;
    private ChatColor secondColor = ChatColor.DARK_RED;

    @Override
    public List<String> getDescriptionLore(String username) {
        String bulletpoint = ChatColor.BOLD.toString() + "* ";
        return Lists.newArrayList((Object[])new String[]{(Object)this.mainColor + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)this.mainColor + " at " + (Object)this.secondColor + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Bag of Candy", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Spooky Edition Titles", CC.YellowB + bulletpoint + (Object)ChatColor.YELLOW + "Purge Mask", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", CC.Gold + bulletpoint + CC.Gold + "1x Heroic Fallen Hero Bundle", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "2-3x Equipment Lootbox", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1-3x Heroic Blackscroll " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " Holy Whitescroll", CC.Gold + bulletpoint + CC.Gold + "1-2x Random Heroic Upgrade", CC.Gold + bulletpoint + CC.Gold + "1-3x Random Heroic Enchantment", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Halloween 2016 " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " 2017 /cc", "", (Object)this.secondColor + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)this.secondColor + bulletpoint + (Object)this.secondColor + "1x 66% Armor/Weapon Enchant Orb (14 Slot)", (Object)this.secondColor + bulletpoint + (Object)this.secondColor + "Ghost Mastery Kit", (Object)this.secondColor + bulletpoint + (Object)this.secondColor + "1-2x Random Dungeon Lootbag"});
    }

    @Override
    public ItemStack getRandomMetaItem(Player player) {
        double chance = Math.random();
        if (chance <= 0.65) {
            return ItemUtils.setAmount((ItemStack)DungeonsRewardsAPI.getDungeonLootBag((Player)player, (Dungeon)(this.random.nextDouble() <= 0.8 ? DungeonType.ABANDONED_SHIP.getDungeonInstance() : DungeonType.DESTROYED_OUTPOST.getDungeonInstance())), (int)(this.random.nextDouble() >= 0.7 ? this.getRandomOne() : 1));
        }
        if (chance <= 0.9) {
            return CosmicGlobalKitsAPI.createMasteryKitItem((MasteryKits)MasteryKits.GHOST, (boolean)true);
        }
        return ItemUtils.setAmount((ItemStack)EnchantmentOrb.getEnchantmentOrb((int)66, (int)14, (OrbType)(this.random.nextInt(10) <= 6 ? OrbType.ARMOR : OrbType.WEAPON)), (int)1);
    }

    @Override
    public List<ItemStack> getCrateLoot(Player player) {
        ItemStack mask;
        ArrayList items = Lists.newArrayList();
        items.add(ItemUtils.buildItem((Material)Material.LAVA_BUCKET, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&6&lBag Of Candy"), (String[])new String[]{(Object)ChatColor.GRAY + "A hard earned bag of the", CC.Gray + "best gum drops in the galaxy!"}));
        int x = this.random.nextInt(4);
        if (x == 0) {
            items.add(ItemUtils.getTitleVoucher((String)"TrickOrTrick"));
        }
        if (x == 1) {
            items.add(ItemUtils.getTitleVoucher((String)"Nightmare"));
        }
        if (x == 2) {
            items.add(ItemUtils.getTitleVoucher((String)"Psychopath"));
        }
        if (x == 3) {
            if (Math.random() <= 0.3) {
                items.add(ItemUtils.getTitleVoucher((String)"NothingCanSaveYou"));
            } else {
                items.add(ItemUtils.getTitleVoucher((String)"#Purge"));
            }
        }
        if ((mask = new MaskBuilder("PURGE").build()) != null) {
            items.add(mask);
        }
        items.add(this.setAmount(((EquipmentLootboxes)ArkPlugins.EQUIPMENT_LOOTBOX.getArkPlugin()).createLootboxItem(false), 1 + this.getRandomOne()));
        items.add(CommandFallenHero.getHeroicFallenHeroBundle());
        items.add(this.setAmount(this.random.nextDouble() <= 0.2 ? DungeonsRewardsAPI.getHolyWhiteScroll() : EnchantmentBook.getHeroicBlackscroll((boolean)true), this.getRandomThree()));
        items.add(this.setAmount(TieredEnchantments.getMysteryEnchantmentBook((int)7), this.getRandomThree()));
        items.add(this.setAmount(CosmicArmorSetsAPI.createRandomArmorSetUpgrade(), this.getRandomOne()));
        items.add(CCAPI.getCosmicCrate(Math.random() <= 0.65 ? Version.HALLOWEEN : Version.HALLOWEEN_2017, player.getName()));
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
        return DyeColor.RED.getWoolData();
    }

    @Override
    public List<Color> getConclusionFireworkColors() {
        return Lists.newArrayList((Object[])new Color[]{Color.RED, Color.ORANGE});
    }

    @Override
    public short getColorPane() {
        return DyeColor.ORANGE.getWoolData();
    }
}

