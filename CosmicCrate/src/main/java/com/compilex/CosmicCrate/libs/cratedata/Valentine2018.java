// 
// Decompiled by Procyon v0.5.36
// 

package com.compilex.CosmicCrate.libs.cratedata;

import org.bukkit.Color;
import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI;
import org.arkhamnetwork.enchantments.EnchantmentOrb;
import org.arkhamnetwork.enchantments.libs.OrbType;
import com.cosmicpvp.CosmicRankQuests.CosmicRankQuestsAPI;
import com.compilex.CosmicCrate.api.CCAPI;
import com.compilex.CosmicCrate.libs.Version;
import com.cosmicpvp.CosmicSpaceChests.libs.EvolutionKitItems;
import me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKits;
import java.util.concurrent.ThreadLocalRandom;
import org.arkhamnetwork.enchantments.RandomizationScroll;
import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import org.arkhamnetwork.Arkkit.patches.LoreTag;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.utils.CC;
import org.bukkit.Material;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.compilex.CosmicDungeons.DungeonsRewardsAPI;
import com.compilex.CosmicDungeons.DungeonsAPI;
import com.cosmicpvp.CosmicRankQuests.HeroicRankCrystalAPI;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import com.google.common.collect.Lists;
import org.bukkit.ChatColor;
import java.util.List;
import com.compilex.CosmicCrate.libs.CosmicCrateData;

public class Valentine2018 extends CosmicCrateData
{
    @Override
    public List<String> getDescriptionLore(final String username) {
        final String bulletpoint = ChatColor.BOLD.toString() + "* ";
        return (List<String>)Lists.newArrayList((Object[])new String[] { ChatColor.DARK_RED + "Unlocked by " + ChatColor.BOLD + username + ChatColor.DARK_RED + " at " + ChatColor.RED + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Cupid's Broken Heart", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Limited Edition Titles", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "1-3x Lore Crystals " + ChatColor.UNDERLINE.toString() + "OR" + ChatColor.YELLOW + " Item Nametags", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "2-3x Ultimate+ Randomization Scrolls", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Random Evolution Kit Gem", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Valentine 2017 Cosmic Crate", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Rank Quest", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "2-4x 70-95% Weapon/Armor Orb (" + ChatColor.GOLD + ChatColor.BOLD + "12 Slot" + ChatColor.GOLD + ")", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1-3x Godly " + ChatColor.UNDERLINE.toString() + "OR" + ChatColor.GOLD + " Legendary Space Flares", "", ChatColor.RED.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", ChatColor.RED + bulletpoint + ChatColor.RED + "Random Phantom " + ChatColor.UNDERLINE + "OR" + ChatColor.RED + " Mother Of Yijki " + ChatColor.UNDERLINE + "OR" + ChatColor.RED + " Yeti Gear", ChatColor.RED + bulletpoint + ChatColor.RED + "Heroic Rank Crystal", ChatColor.RED + bulletpoint + ChatColor.RED + "Abandoned Spaceship Dungeon Lootbag" });
    }
    
    @Override
    public ItemStack getRandomMetaItem(final Player player) {
        if (Math.random() < 0.05) {
            return HeroicRankCrystalAPI.getHeroicRankCrystalItem();
        }
        if (Math.random() < 0.6) {
            return DungeonsRewardsAPI.getDungeonLootBag(player, DungeonsAPI.get().getDungeon(0));
        }
        return ItemID.removeItemID(CosmicArmorSetsAPI.getArmorSet((Math.random() < 0.18) ? ArmorSet.MOTHER_OF_YIJKI : ((Math.random() < 0.4) ? ArmorSet.YETI : ArmorSet.PHANTOM))[this.random.nextInt(4)]);
    }
    
    @Override
    public List<ItemStack> getCrateLoot(final Player player) {
        final List<ItemStack> items = (List<ItemStack>)Lists.newArrayList();
        items.add(ItemUtils.setAmount(ItemUtils.buildItem(Material.FERMENTED_SPIDER_EYE, (short)0, ChatColor.translateAlternateColorCodes('&', CC.DarkRed + "Cupid's " + CC.RedB + "Broken Heart"), new String[] { ChatColor.GRAY + "The God of Loves aching heart!", ChatColor.GRAY + "Will he ever find love?" }), 1));
        items.add(ItemUtils.setAmount((Math.random() <= 0.5) ? ItemUtils.getItemNametag() : LoreTag.getLoreTagItem(), 1 + this.random.nextInt(2)));
        items.add(ItemUtils.setAmount(RandomizationScroll.getRandomizationScroll((Math.random() <= 0.65) ? ItemRarity.ULTIMATE : ((Math.random() <= 0.4) ? ItemRarity.LEGENDARY : ItemRarity.GOD)), 2 + ThreadLocalRandom.current().nextInt(2)));
        final int x = this.random.nextInt(4);
        if (x == 0) {
            items.add(ItemUtils.getTitleVoucher("HeartEyes"));
        }
        if (x == 1) {
            items.add(ItemUtils.getTitleVoucher("SecretAdmirer"));
        }
        if (x == 2) {
            items.add(ItemUtils.getTitleVoucher("#WillYouBeMine"));
        }
        if (x == 3) {
            if (Math.random() < 0.1) {
                items.add(ItemUtils.getTitleVoucher("GodOfLove"));
            }
            else {
                items.add(ItemUtils.getTitleVoucher("#LoveNotWar"));
            }
        }
        int rankId = 2;
        if (Math.random() < 0.6) {
            rankId = 3;
            if (Math.random() < 0.4) {
                rankId = 4;
            }
        }
        items.add(EvolutionKitItems.getKitItem((Math.random() < 0.3) ? EvolutionKits.ALCHEMIST : ((Math.random() < 0.5) ? EvolutionKits.PHOENIX : ((Math.random() < 0.5) ? EvolutionKits.TROLL : EvolutionKits.SLAYER))));
        items.add(CCAPI.getCosmicCrate(Version.VALENTINE, "Valentine 2018 Cosmic Crate"));
        items.add(CosmicRankQuestsAPI.getRankQuestItem(rankId));
        if (Math.random() < 0.35) {
            items.add(ItemUtils.setAmount(EnchantmentOrb.getEnchantmentOrb(70 + this.random.nextInt(26), 12, OrbType.WEAPON), 2 + this.random.nextInt(3)));
        }
        else {
            items.add(ItemUtils.setAmount(EnchantmentOrb.getEnchantmentOrb(70 + this.random.nextInt(26), 12, OrbType.ARMOR), 2 + this.random.nextInt(3)));
        }
        items.add(ItemID.removeItemID(ItemUtils.setAmount(CosmicSpaceChestsAPI.getSpaceFlare((Math.random() < 0.33) ? ItemRarity.GOD : ItemRarity.LEGENDARY), 1 + this.random.nextInt(3))));
        return items;
    }
    
    @Override
    public short getNonColorPane() {
        return 2;
    }
    
    @Override
    public List<Color> getConclusionFireworkColors() {
        return (List<Color>)Lists.newArrayList((Object[])new Color[] { Color.PURPLE, Color.FUCHSIA });
    }
    
    @Override
    public short getColorPane() {
        return 10;
    }
}
