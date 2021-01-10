/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicArmorSets.CosmicArmorSetsAPI
 *  com.compilex.CosmicArmorSets.enums.ArmorSet
 *  com.cosmicpvp.CosmicRankQuests.HeroicRankCrystalAPI
 *  com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI
 *  com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity
 *  com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.google.common.collect.Lists
 *  me.ifamasssxd.cosmicbosses.struct.enums.BossType
 *  me.ifamasssxd.cosmicconquest.CosmicConquestAPI
 *  org.arkhamnetwork.Arkkit.patches.EssentialsAddHomes
 *  org.arkhamnetwork.Arkkit.patches.ItemID
 *  org.arkhamnetwork.Arkkit.patches.LoreTag
 *  org.arkhamnetwork.Arkkit.patches.ShowcaseExpander
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

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.compilex.CosmicCrate.api.CCAPI;
import com.compilex.CosmicCrate.libs.CosmicCrateData;
import com.compilex.CosmicCrate.libs.Version;
import com.cosmicpvp.CosmicRankQuests.HeroicRankCrystalAPI;
import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI;
import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import me.ifamasssxd.cosmicbosses.struct.enums.BossType;
import me.ifamasssxd.cosmicconquest.CosmicConquestAPI;
import org.arkhamnetwork.Arkkit.patches.EssentialsAddHomes;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.Arkkit.patches.LoreTag;
import org.arkhamnetwork.Arkkit.patches.ShowcaseExpander;
import org.arkhamnetwork.enchantments.EnchantmentOrb;
import org.arkhamnetwork.enchantments.libs.OrbType;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class March2018
extends CosmicCrateData {
    @Override
    public List<String> getDescriptionLore(String username) {
        String bulletpoint = ChatColor.BOLD.toString() + "* ";
        return Lists.newArrayList((Object[])new String[]{(Object)ChatColor.DARK_GREEN + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)ChatColor.DARK_GREEN + " at " + (Object)ChatColor.GREEN + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Four-Leaf Clover", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Limited Edition Titles", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "2-3x Lore Crystals " + ChatColor.UNDERLINE.toString() + "OR" + (Object)ChatColor.YELLOW + " Item Nametags", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "1-3x Showcase Expansion " + ChatColor.UNDERLINE.toString() + "OR" + (Object)ChatColor.YELLOW + " 5x Max Home Increase", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1-3x Godly Space Flare", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "10-30x Faction Crystals", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Heroic Mystery Cosmic Crate", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "2-4x 80-95% Weapon/Armor Orb (" + (Object)ChatColor.GOLD + (Object)ChatColor.BOLD + "11-12 Slot" + (Object)ChatColor.GOLD + ")", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Random Phantom " + (Object)ChatColor.UNDERLINE + "OR" + (Object)ChatColor.GOLD + " Mother of Yijki Set Piece", "", ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)ChatColor.GREEN + bulletpoint + (Object)ChatColor.GREEN + "March 2017 Cosmic Crate", (Object)ChatColor.GREEN + bulletpoint + (Object)ChatColor.GREEN + "Heroic Rank Crystal", (Object)ChatColor.GREEN + bulletpoint + (Object)ChatColor.GREEN + "2-4x Legendary Boss Eggs"});
    }

    @Override
    public ItemStack getRandomMetaItem(Player player) {
        if (Math.random() < 0.05) {
            return HeroicRankCrystalAPI.getHeroicRankCrystalItem();
        }
        if (Math.random() < 0.75) {
            double randomVal = Math.random();
            ItemStack egg = randomVal <= 0.06 ? ItemUtils.getNewBossEgg((BossType)BossType.DEMONIC_WITHER) : (randomVal <= 0.1 ? ItemUtils.getNewBossEgg((BossType)BossType.PLAGUE_BLOATER) : (randomVal < 0.2 ? ItemUtils.getNewBossEgg((BossType)BossType.SKELETON_HORSE) : (randomVal < 0.55 ? ItemUtils.getNewBossEgg((BossType)BossType.KING_SLIME) : ItemUtils.getNewBossEgg((BossType)BossType.BROOD_MOTHER))));
            return ItemID.removeItemID((ItemStack)ItemUtils.setAmount((ItemStack)egg, (int)(2 + this.random.nextInt(3))));
        }
        return CCAPI.getCosmicCrate(Version.MARCH_2017, "March 2018 Cosmic Crate");
    }

    @Override
    public List<ItemStack> getCrateLoot(Player player) {
        ArrayList items = Lists.newArrayList();
        items.add(ItemUtils.setAmount((ItemStack)ItemUtils.buildItem((Material)Material.LONG_GRASS, (short)2, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)(CC.GreenB + "Four-Leaf " + CC.DarkGreen + "Clover")), (String[])new String[]{"", (Object)ChatColor.GRAY + "A clover passed down for", (Object)ChatColor.GRAY + "generations, its said to bestow", (Object)ChatColor.GRAY + "great fortune to the beholder."}), (int)1));
        items.add(ItemUtils.setAmount((ItemStack)(Math.random() <= 0.55 ? ItemUtils.getItemNametag() : LoreTag.getLoreTagItem()), (int)(1 + this.random.nextInt(3))));
        if (Math.random() <= 0.5) {
            items.add(ItemUtils.setAmount((ItemStack)ShowcaseExpander.getShowcaseExpanderItem(), (int)(1 + this.random.nextInt(3))));
        } else {
            items.add(ItemUtils.setAmount((ItemStack)EssentialsAddHomes.getAddHomeItem(), (int)5));
        }
        int x = this.random.nextInt(4);
        if (x == 0) {
            items.add(ItemUtils.getTitleVoucher((String)"FeelingLucky"));
        }
        if (x == 1) {
            items.add(ItemUtils.getTitleVoucher((String)"#LuckOfTheIrish"));
        }
        if (x == 2) {
            items.add(ItemUtils.getTitleVoucher((String)"Shamrock"));
        }
        if (x == 3) {
            if (Math.random() < 0.1) {
                items.add(ItemUtils.getTitleVoucher((String)"#Martigras"));
            } else {
                items.add(ItemUtils.getTitleVoucher((String)"Leprechaun"));
            }
        }
        items.add(ItemID.removeItemID((ItemStack)ItemUtils.setAmount((ItemStack)CosmicSpaceChestsAPI.getSpaceFlare((ItemRarity)ItemRarity.GOD), (int)(1 + this.random.nextInt(3)))));
        items.add(CosmicConquestAPI.createFactionToken((int)(10 + this.random.nextInt(21))));
        items.add(ItemID.removeItemID((ItemStack)ItemUtils.setAmount((ItemStack)CCAPI.getHeroicMysteryCrate(), (int)1)));
        items.add(ItemUtils.setAmount((ItemStack)EnchantmentOrb.getEnchantmentOrb((int)(80 + this.random.nextInt(16)), (int)(this.random.nextInt(2) + 11), (OrbType)(Math.random() < 0.35 ? OrbType.WEAPON : OrbType.ARMOR)), (int)(2 + this.random.nextInt(3))));
        items.add(ItemID.removeItemID((ItemStack)CosmicArmorSetsAPI.getArmorSet((ArmorSet)(Math.random() < 0.1 ? ArmorSet.MOTHER_OF_YIJKI : ArmorSet.PHANTOM))[this.random.nextInt(4)]));
        return items;
    }

    @Override
    public short getNonColorPane() {
        return DyeColor.LIME.getWoolData();
    }

    @Override
    public List<Color> getConclusionFireworkColors() {
        return Lists.newArrayList((Object[])new Color[]{Color.LIME, Color.GREEN});
    }

    @Override
    public short getColorPane() {
        return DyeColor.GREEN.getWoolData();
    }
}

