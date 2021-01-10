/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicArmorSets.CosmicArmorSetsAPI
 *  com.compilex.CosmicArmorSets.enums.ArmorSet
 *  com.compilex.CosmicDungeons.DungeonsAPI
 *  com.compilex.CosmicDungeons.DungeonsRewardsAPI
 *  com.compilex.CosmicDungeons.dungeons.libs.Dungeon
 *  com.cosmicpvp.CosmicRankQuests.CosmicRankQuestsAPI
 *  com.cosmicpvp.CosmicRankQuests.HeroicRankCrystalAPI
 *  com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI
 *  com.cosmicpvp.CosmicSpaceChests.libs.EvolutionKitItems
 *  com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity
 *  com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.google.common.collect.Lists
 *  me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKits
 *  org.arkhamnetwork.Arkkit.patches.ItemID
 *  org.arkhamnetwork.Arkkit.patches.LoreTag
 *  org.arkhamnetwork.enchantments.EnchantmentOrb
 *  org.arkhamnetwork.enchantments.RandomizationScroll
 *  org.arkhamnetwork.enchantments.libs.OrbType
 *  org.bukkit.ChatColor
 *  org.bukkit.Color
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
import com.compilex.CosmicDungeons.DungeonsAPI;
import com.compilex.CosmicDungeons.DungeonsRewardsAPI;
import com.compilex.CosmicDungeons.dungeons.libs.Dungeon;
import com.cosmicpvp.CosmicRankQuests.CosmicRankQuestsAPI;
import com.cosmicpvp.CosmicRankQuests.HeroicRankCrystalAPI;
import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI;
import com.cosmicpvp.CosmicSpaceChests.libs.EvolutionKitItems;
import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKits;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.Arkkit.patches.LoreTag;
import org.arkhamnetwork.enchantments.EnchantmentOrb;
import org.arkhamnetwork.enchantments.RandomizationScroll;
import org.arkhamnetwork.enchantments.libs.OrbType;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Valentine2018
extends CosmicCrateData {
    @Override
    public List<String> getDescriptionLore(String username) {
        String bulletpoint = ChatColor.BOLD.toString() + "* ";
        return Lists.newArrayList((Object[])new String[]{(Object)ChatColor.DARK_RED + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)ChatColor.DARK_RED + " at " + (Object)ChatColor.RED + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Cupid's Broken Heart", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Limited Edition Titles", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "1-3x Lore Crystals " + ChatColor.UNDERLINE.toString() + "OR" + (Object)ChatColor.YELLOW + " Item Nametags", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "2-3x Ultimate+ Randomization Scrolls", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Random Evolution Kit Gem", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Valentine 2017 Cosmic Crate", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Rank Quest", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "2-4x 70-95% Weapon/Armor Orb (" + (Object)ChatColor.GOLD + (Object)ChatColor.BOLD + "12 Slot" + (Object)ChatColor.GOLD + ")", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1-3x Godly " + ChatColor.UNDERLINE.toString() + "OR" + (Object)ChatColor.GOLD + " Legendary Space Flares", "", ChatColor.RED.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)ChatColor.RED + bulletpoint + (Object)ChatColor.RED + "Random Phantom " + (Object)ChatColor.UNDERLINE + "OR" + (Object)ChatColor.RED + " Mother Of Yijki " + (Object)ChatColor.UNDERLINE + "OR" + (Object)ChatColor.RED + " Yeti Gear", (Object)ChatColor.RED + bulletpoint + (Object)ChatColor.RED + "Heroic Rank Crystal", (Object)ChatColor.RED + bulletpoint + (Object)ChatColor.RED + "Abandoned Spaceship Dungeon Lootbag"});
    }

    @Override
    public ItemStack getRandomMetaItem(Player player) {
        if (Math.random() < 0.05) {
            return HeroicRankCrystalAPI.getHeroicRankCrystalItem();
        }
        if (Math.random() < 0.6) {
            return DungeonsRewardsAPI.getDungeonLootBag((Player)player, (Dungeon)DungeonsAPI.get().getDungeon(0));
        }
        return ItemID.removeItemID((ItemStack)CosmicArmorSetsAPI.getArmorSet((ArmorSet)(Math.random() < 0.18 ? ArmorSet.MOTHER_OF_YIJKI : (Math.random() < 0.4 ? ArmorSet.YETI : ArmorSet.PHANTOM)))[this.random.nextInt(4)]);
    }

    @Override
    public List<ItemStack> getCrateLoot(Player player) {
        ArrayList items = Lists.newArrayList();
        items.add(ItemUtils.setAmount((ItemStack)ItemUtils.buildItem((Material)Material.FERMENTED_SPIDER_EYE, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)(CC.DarkRed + "Cupid's " + CC.RedB + "Broken Heart")), (String[])new String[]{(Object)ChatColor.GRAY + "The God of Loves aching heart!", (Object)ChatColor.GRAY + "Will he ever find love?"}), (int)1));
        items.add(ItemUtils.setAmount((ItemStack)(Math.random() <= 0.5 ? ItemUtils.getItemNametag() : LoreTag.getLoreTagItem()), (int)(1 + this.random.nextInt(2))));
        items.add(ItemUtils.setAmount((ItemStack)RandomizationScroll.getRandomizationScroll((ItemRarity)(Math.random() <= 0.65 ? ItemRarity.ULTIMATE : (Math.random() <= 0.4 ? ItemRarity.LEGENDARY : ItemRarity.GOD))), (int)(2 + ThreadLocalRandom.current().nextInt(2))));
        int x = this.random.nextInt(4);
        if (x == 0) {
            items.add(ItemUtils.getTitleVoucher((String)"HeartEyes"));
        }
        if (x == 1) {
            items.add(ItemUtils.getTitleVoucher((String)"SecretAdmirer"));
        }
        if (x == 2) {
            items.add(ItemUtils.getTitleVoucher((String)"#WillYouBeMine"));
        }
        if (x == 3) {
            if (Math.random() < 0.1) {
                items.add(ItemUtils.getTitleVoucher((String)"GodOfLove"));
            } else {
                items.add(ItemUtils.getTitleVoucher((String)"#LoveNotWar"));
            }
        }
        int rankId = 2;
        if (Math.random() < 0.6) {
            rankId = 3;
            if (Math.random() < 0.4) {
                rankId = 4;
            }
        }
        items.add(EvolutionKitItems.getKitItem((EvolutionKits)(Math.random() < 0.3 ? EvolutionKits.ALCHEMIST : (Math.random() < 0.5 ? EvolutionKits.PHOENIX : (Math.random() < 0.5 ? EvolutionKits.TROLL : EvolutionKits.SLAYER)))));
        items.add(CCAPI.getCosmicCrate(Version.VALENTINE, "Valentine 2018 Cosmic Crate"));
        items.add(CosmicRankQuestsAPI.getRankQuestItem((int)rankId));
        if (Math.random() < 0.35) {
            items.add(ItemUtils.setAmount((ItemStack)EnchantmentOrb.getEnchantmentOrb((int)(70 + this.random.nextInt(26)), (int)12, (OrbType)OrbType.WEAPON), (int)(2 + this.random.nextInt(3))));
        } else {
            items.add(ItemUtils.setAmount((ItemStack)EnchantmentOrb.getEnchantmentOrb((int)(70 + this.random.nextInt(26)), (int)12, (OrbType)OrbType.ARMOR), (int)(2 + this.random.nextInt(3))));
        }
        items.add(ItemID.removeItemID((ItemStack)ItemUtils.setAmount((ItemStack)CosmicSpaceChestsAPI.getSpaceFlare((ItemRarity)(Math.random() < 0.33 ? ItemRarity.GOD : ItemRarity.LEGENDARY)), (int)(1 + this.random.nextInt(3)))));
        return items;
    }

    @Override
    public short getNonColorPane() {
        return 2;
    }

    @Override
    public List<Color> getConclusionFireworkColors() {
        return Lists.newArrayList((Object[])new Color[]{Color.PURPLE, Color.FUCHSIA});
    }

    @Override
    public short getColorPane() {
        return 10;
    }
}

