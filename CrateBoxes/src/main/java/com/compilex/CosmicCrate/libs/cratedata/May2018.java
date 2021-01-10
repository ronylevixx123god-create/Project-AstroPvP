/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicArmorSets.enums.ArmorSet
 *  com.cosmicpvp.CosmicRankQuests.HeroicRankCrystalAPI
 *  com.cosmicpvp.CosmicSpaceChests.libs.KitItems
 *  com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.google.common.collect.Lists
 *  me.cosmicpvp.CosmicGlobalKits.libs.FallenHero
 *  me.cosmicpvp.CosmicGlobalKits.libs.GlobalKits
 *  me.ifamasssxd.cosmicdisguises.managers.CosmicDisguiseAPI
 *  me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKit
 *  me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKits
 *  org.arkhamnetwork.Arkkit.patches.ItemID
 *  org.arkhamnetwork.Arkkit.patches.LoreTag
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

import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.compilex.CosmicCrate.api.CCAPI;
import com.compilex.CosmicCrate.libs.CosmicCrateData;
import com.compilex.CosmicCrate.libs.Version;
import com.cosmicpvp.CosmicRankQuests.HeroicRankCrystalAPI;
import com.cosmicpvp.CosmicSpaceChests.libs.KitItems;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import me.cosmicpvp.CosmicGlobalKits.libs.FallenHero;
import me.cosmicpvp.CosmicGlobalKits.libs.GlobalKits;
import me.ifamasssxd.cosmicdisguises.managers.CosmicDisguiseAPI;
import me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKit;
import me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKits;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.Arkkit.patches.LoreTag;
import org.arkhamnetwork.enchantments.EnchantmentOrb;
import org.arkhamnetwork.enchantments.libs.OrbType;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class May2018
extends CosmicCrateData {
    @Override
    public List<String> getDescriptionLore(String username) {
        String bulletpoint = ChatColor.BOLD.toString() + "* ";
        return Lists.newArrayList((Object[])new String[]{CC.Green + "Unlocked by " + CC.GreenB + username + CC.Green + " at " + CC.AquaU + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "May Flowers", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Limited Edition Titles", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Zombie " + CC.YellowB + CC.Underline + "OR" + CC.Yellow + " Blaze Disguise", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "2-3x Lore Crystals " + CC.YellowB + CC.Underline + "OR" + CC.Yellow + " Item Nametags", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Random /vkit Fallen Hero", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Grim Reaper /gkit Gem", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Heroic Mystery Cosmic Crate", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Random Ranger Armor Piece", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "May 2017 Cosmic Crate", "", ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)ChatColor.AQUA + bulletpoint + (Object)ChatColor.AQUA + "Heroic Rank Crystal", (Object)ChatColor.AQUA + bulletpoint + (Object)ChatColor.AQUA + "Ranger Bow", (Object)ChatColor.AQUA + bulletpoint + (Object)ChatColor.AQUA + "80-100% Armor/Weapon Enchantment Orb (" + (Object)ChatColor.AQUA + (Object)ChatColor.BOLD + "13 Slot" + (Object)ChatColor.AQUA + ")"});
    }

    @Override
    public ItemStack getRandomMetaItem(Player player) {
        double chance = Math.random();
        if (chance <= 0.22) {
            return ArmorSet.createRangerBow();
        }
        if (chance < 0.75) {
            return ItemUtils.setAmount((ItemStack)EnchantmentOrb.getEnchantmentOrb((int)(this.random.nextInt(21) + 80), (int)13, (OrbType)(this.random.nextInt(10) <= 6 ? OrbType.ARMOR : OrbType.WEAPON)), (int)1);
        }
        return HeroicRankCrystalAPI.getHeroicRankCrystalItem();
    }

    @Override
    public List<ItemStack> getCrateLoot(Player player) {
        ArrayList items = Lists.newArrayList();
        items.add(ItemUtils.setAmount((ItemStack)ItemUtils.buildItem((Material)Material.getMaterial((int)38), (short)8, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)(CC.AquaB + "May Flower")), (String[])new String[]{"", (Object)ChatColor.GRAY + "A tough flower that survived", (Object)ChatColor.GRAY + "the dreaded winter of 2017/2018!"}), (int)1));
        items.add(CosmicDisguiseAPI.createRedeemableDisguise((String)(this.random.nextBoolean() ? "ZOMBIE" : "BLAZE")));
        if (this.random.nextDouble() <= 0.5) {
            items.add(ItemUtils.setAmount((ItemStack)ItemUtils.getItemNametag(), (int)(this.random.nextInt(2) + 2)));
        } else {
            items.add(ItemUtils.setAmount((ItemStack)LoreTag.getLoreTagItem(), (int)(this.random.nextInt(2) + 2)));
        }
        int x = this.random.nextInt(4);
        if (x == 0) {
            items.add(ItemUtils.getTitleVoucher((String)"#SpringIsSprung"));
        }
        if (x == 1) {
            items.add(ItemUtils.getTitleVoucher((String)"#May2018"));
        }
        if (x == 2) {
            items.add(ItemUtils.getTitleVoucher((String)"#HelloSpring"));
        }
        if (x == 3) {
            if (Math.random() < 0.1) {
                items.add(ItemUtils.getTitleVoucher((String)"#MayFlower"));
            } else {
                items.add(ItemUtils.getTitleVoucher((String)"#LostKingdoms"));
            }
        }
        items.add(ArmorSet.RANGER.armorSetItems[ThreadLocalRandom.current().nextInt(ArmorSet.RANGER.armorSetItems.length)].clone());
        items.add(FallenHero.getFallenHeroItem((EvolutionKit)EvolutionKits.values()[this.random.nextInt(EvolutionKits.values().length)].getKit()));
        items.add(KitItems.getKitItem((GlobalKits)GlobalKits.GrimReaper));
        items.add(CCAPI.getHeroicMysteryCrate());
        items.add(ItemID.removeItemID((ItemStack)CCAPI.getCosmicCrate(Version.MAY_2017, "May 2018 Cosmic Crate")));
        return items;
    }

    @Override
    public short getNonColorPane() {
        return DyeColor.LIME.getWoolData();
    }

    @Override
    public List<Color> getConclusionFireworkColors() {
        return Lists.newArrayList((Object[])new Color[]{Color.LIME, Color.AQUA});
    }

    @Override
    public short getColorPane() {
        return DyeColor.LIGHT_BLUE.getWoolData();
    }
}

