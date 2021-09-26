// 
// Decompiled by Procyon v0.5.36
// 

package com.compilex.CosmicCrate.libs.cratedata;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import com.compilex.CosmicCrate.libs.Version;
import com.compilex.CosmicCrate.api.CCAPI;
import com.cosmicpvp.CosmicSpaceChests.libs.KitItems;
import me.cosmicpvp.CosmicGlobalKits.libs.GlobalKits;
import me.cosmicpvp.CosmicGlobalKits.libs.FallenHero;
import me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKits;
import java.util.concurrent.ThreadLocalRandom;
import org.arkhamnetwork.Arkkit.patches.LoreTag;
import me.ifamasssxd.cosmicdisguises.managers.CosmicDisguiseAPI;
import org.bukkit.Material;
import com.cosmicpvp.CosmicRankQuests.HeroicRankCrystalAPI;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import org.arkhamnetwork.enchantments.EnchantmentOrb;
import org.arkhamnetwork.enchantments.libs.OrbType;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import com.google.common.collect.Lists;
import com.cosmicpvp.cosmicutils.utils.CC;
import org.bukkit.ChatColor;
import java.util.List;
import com.compilex.CosmicCrate.libs.CosmicCrateData;

public class May2018 extends CosmicCrateData
{
    @Override
    public List<String> getDescriptionLore(final String username) {
        final String bulletpoint = ChatColor.BOLD.toString() + "* ";
        return (List<String>)Lists.newArrayList((Object[])new String[] { CC.Green + "Unlocked by " + CC.GreenB + username + CC.Green + " at " + CC.AquaU + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "May Flowers", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Limited Edition Titles", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Zombie " + CC.YellowB + CC.Underline + "OR" + CC.Yellow + " Blaze Disguise", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "2-3x Lore Crystals " + CC.YellowB + CC.Underline + "OR" + CC.Yellow + " Item Nametags", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Random /vkit Fallen Hero", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Grim Reaper /gkit Gem", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Heroic Mystery Cosmic Crate", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Random Ranger Armor Piece", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "May 2017 Cosmic Crate", "", ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", ChatColor.AQUA + bulletpoint + ChatColor.AQUA + "Heroic Rank Crystal", ChatColor.AQUA + bulletpoint + ChatColor.AQUA + "Ranger Bow", ChatColor.AQUA + bulletpoint + ChatColor.AQUA + "80-100% Armor/Weapon Enchantment Orb (" + ChatColor.AQUA + ChatColor.BOLD + "13 Slot" + ChatColor.AQUA + ")" });
    }
    
    @Override
    public ItemStack getRandomMetaItem(final Player player) {
        final double chance = Math.random();
        if (chance <= 0.22) {
            return ArmorSet.createRangerBow();
        }
        if (chance < 0.75) {
            return ItemUtils.setAmount(EnchantmentOrb.getEnchantmentOrb(this.random.nextInt(21) + 80, 13, (this.random.nextInt(10) <= 6) ? OrbType.ARMOR : OrbType.WEAPON), 1);
        }
        return HeroicRankCrystalAPI.getHeroicRankCrystalItem();
    }
    
    @Override
    public List<ItemStack> getCrateLoot(final Player player) {
        final List<ItemStack> items = (List<ItemStack>)Lists.newArrayList();
        items.add(ItemUtils.setAmount(ItemUtils.buildItem(Material.getMaterial(38), (short)8, ChatColor.translateAlternateColorCodes('&', CC.AquaB + "May Flower"), new String[] { "", ChatColor.GRAY + "A tough flower that survived", ChatColor.GRAY + "the dreaded winter of 2017/2018!" }), 1));
        items.add(CosmicDisguiseAPI.createRedeemableDisguise(this.random.nextBoolean() ? "ZOMBIE" : "BLAZE"));
        if (this.random.nextDouble() <= 0.5) {
            items.add(ItemUtils.setAmount(ItemUtils.getItemNametag(), this.random.nextInt(2) + 2));
        }
        else {
            items.add(ItemUtils.setAmount(LoreTag.getLoreTagItem(), this.random.nextInt(2) + 2));
        }
        final int x = this.random.nextInt(4);
        if (x == 0) {
            items.add(ItemUtils.getTitleVoucher("#SpringIsSprung"));
        }
        if (x == 1) {
            items.add(ItemUtils.getTitleVoucher("#May2018"));
        }
        if (x == 2) {
            items.add(ItemUtils.getTitleVoucher("#HelloSpring"));
        }
        if (x == 3) {
            if (Math.random() < 0.1) {
                items.add(ItemUtils.getTitleVoucher("#MayFlower"));
            }
            else {
                items.add(ItemUtils.getTitleVoucher("#LostKingdoms"));
            }
        }
        items.add(ArmorSet.RANGER.armorSetItems[ThreadLocalRandom.current().nextInt(ArmorSet.RANGER.armorSetItems.length)].clone());
        items.add(FallenHero.getFallenHeroItem(EvolutionKits.values()[this.random.nextInt(EvolutionKits.values().length)].getKit()));
        items.add(KitItems.getKitItem(GlobalKits.GrimReaper));
        items.add(CCAPI.getHeroicMysteryCrate());
        items.add(ItemID.removeItemID(CCAPI.getCosmicCrate(Version.MAY_2017, "May 2018 Cosmic Crate")));
        return items;
    }
    
    @Override
    public short getNonColorPane() {
        return DyeColor.LIME.getWoolData();
    }
    
    @Override
    public List<Color> getConclusionFireworkColors() {
        return (List<Color>)Lists.newArrayList((Object[])new Color[] { Color.LIME, Color.AQUA });
    }
    
    @Override
    public short getColorPane() {
        return DyeColor.LIGHT_BLUE.getWoolData();
    }
}
