// 
// Decompiled by Procyon v0.5.36
// 

package com.compilex.CosmicCrate.libs.cratedata;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import org.arkhamnetwork.enchantments.EnchantmentOrb;
import org.arkhamnetwork.enchantments.libs.OrbType;
import me.ifamasssxd.cosmicconquest.CosmicConquestAPI;
import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI;
import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import org.arkhamnetwork.Arkkit.patches.EssentialsAddHomes;
import org.arkhamnetwork.Arkkit.patches.ShowcaseExpander;
import org.arkhamnetwork.Arkkit.patches.LoreTag;
import com.cosmicpvp.cosmicutils.utils.CC;
import org.bukkit.Material;
import com.compilex.CosmicCrate.api.CCAPI;
import com.compilex.CosmicCrate.libs.Version;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import me.ifamasssxd.cosmicbosses.struct.enums.BossType;
import com.cosmicpvp.CosmicRankQuests.HeroicRankCrystalAPI;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import com.google.common.collect.Lists;
import org.bukkit.ChatColor;
import java.util.List;
import com.compilex.CosmicCrate.libs.CosmicCrateData;

public class March2018 extends CosmicCrateData
{
    @Override
    public List<String> getDescriptionLore(final String username) {
        final String bulletpoint = ChatColor.BOLD.toString() + "* ";
        return (List<String>)Lists.newArrayList((Object[])new String[] { ChatColor.DARK_GREEN + "Unlocked by " + ChatColor.BOLD + username + ChatColor.DARK_GREEN + " at " + ChatColor.GREEN + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Four-Leaf Clover", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Limited Edition Titles", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "2-3x Lore Crystals " + ChatColor.UNDERLINE.toString() + "OR" + ChatColor.YELLOW + " Item Nametags", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "1-3x Showcase Expansion " + ChatColor.UNDERLINE.toString() + "OR" + ChatColor.YELLOW + " 5x Max Home Increase", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1-3x Godly Space Flare", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "10-30x Faction Crystals", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Heroic Mystery Cosmic Crate", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "2-4x 80-95% Weapon/Armor Orb (" + ChatColor.GOLD + ChatColor.BOLD + "11-12 Slot" + ChatColor.GOLD + ")", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Random Phantom " + ChatColor.UNDERLINE + "OR" + ChatColor.GOLD + " Mother of Yijki Set Piece", "", ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", ChatColor.GREEN + bulletpoint + ChatColor.GREEN + "March 2017 Cosmic Crate", ChatColor.GREEN + bulletpoint + ChatColor.GREEN + "Heroic Rank Crystal", ChatColor.GREEN + bulletpoint + ChatColor.GREEN + "2-4x Legendary Boss Eggs" });
    }
    
    @Override
    public ItemStack getRandomMetaItem(final Player player) {
        if (Math.random() < 0.05) {
            return HeroicRankCrystalAPI.getHeroicRankCrystalItem();
        }
        if (Math.random() < 0.75) {
            final double randomVal = Math.random();
            ItemStack egg;
            if (randomVal <= 0.06) {
                egg = ItemUtils.getNewBossEgg(BossType.DEMONIC_WITHER);
            }
            else if (randomVal <= 0.1) {
                egg = ItemUtils.getNewBossEgg(BossType.PLAGUE_BLOATER);
            }
            else if (randomVal < 0.2) {
                egg = ItemUtils.getNewBossEgg(BossType.SKELETON_HORSE);
            }
            else if (randomVal < 0.55) {
                egg = ItemUtils.getNewBossEgg(BossType.KING_SLIME);
            }
            else {
                egg = ItemUtils.getNewBossEgg(BossType.BROOD_MOTHER);
            }
            return ItemID.removeItemID(ItemUtils.setAmount(egg, 2 + this.random.nextInt(3)));
        }
        return CCAPI.getCosmicCrate(Version.MARCH_2017, "March 2018 Cosmic Crate");
    }
    
    @Override
    public List<ItemStack> getCrateLoot(final Player player) {
        final List<ItemStack> items = (List<ItemStack>)Lists.newArrayList();
        items.add(ItemUtils.setAmount(ItemUtils.buildItem(Material.LONG_GRASS, (short)2, ChatColor.translateAlternateColorCodes('&', CC.GreenB + "Four-Leaf " + CC.DarkGreen + "Clover"), new String[] { "", ChatColor.GRAY + "A clover passed down for", ChatColor.GRAY + "generations, its said to bestow", ChatColor.GRAY + "great fortune to the beholder." }), 1));
        items.add(ItemUtils.setAmount((Math.random() <= 0.55) ? ItemUtils.getItemNametag() : LoreTag.getLoreTagItem(), 1 + this.random.nextInt(3)));
        if (Math.random() <= 0.5) {
            items.add(ItemUtils.setAmount(ShowcaseExpander.getShowcaseExpanderItem(), 1 + this.random.nextInt(3)));
        }
        else {
            items.add(ItemUtils.setAmount(EssentialsAddHomes.getAddHomeItem(), 5));
        }
        final int x = this.random.nextInt(4);
        if (x == 0) {
            items.add(ItemUtils.getTitleVoucher("FeelingLucky"));
        }
        if (x == 1) {
            items.add(ItemUtils.getTitleVoucher("#LuckOfTheIrish"));
        }
        if (x == 2) {
            items.add(ItemUtils.getTitleVoucher("Shamrock"));
        }
        if (x == 3) {
            if (Math.random() < 0.1) {
                items.add(ItemUtils.getTitleVoucher("#Martigras"));
            }
            else {
                items.add(ItemUtils.getTitleVoucher("Leprechaun"));
            }
        }
        items.add(ItemID.removeItemID(ItemUtils.setAmount(CosmicSpaceChestsAPI.getSpaceFlare(ItemRarity.GOD), 1 + this.random.nextInt(3))));
        items.add(CosmicConquestAPI.createFactionToken(10 + this.random.nextInt(21)));
        items.add(ItemID.removeItemID(ItemUtils.setAmount(CCAPI.getHeroicMysteryCrate(), 1)));
        items.add(ItemUtils.setAmount(EnchantmentOrb.getEnchantmentOrb(80 + this.random.nextInt(16), this.random.nextInt(2) + 11, (Math.random() < 0.35) ? OrbType.WEAPON : OrbType.ARMOR), 2 + this.random.nextInt(3)));
        items.add(ItemID.removeItemID(CosmicArmorSetsAPI.getArmorSet((Math.random() < 0.1) ? ArmorSet.MOTHER_OF_YIJKI : ArmorSet.PHANTOM)[this.random.nextInt(4)]));
        return items;
    }
    
    @Override
    public short getNonColorPane() {
        return DyeColor.LIME.getWoolData();
    }
    
    @Override
    public List<Color> getConclusionFireworkColors() {
        return (List<Color>)Lists.newArrayList((Object[])new Color[] { Color.LIME, Color.GREEN });
    }
    
    @Override
    public short getColorPane() {
        return DyeColor.GREEN.getWoolData();
    }
}
