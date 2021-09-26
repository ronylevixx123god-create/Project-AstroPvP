// 
// Decompiled by Procyon v0.5.36
// 

package com.compilex.CosmicCrate.libs.cratedata;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import com.cosmicpvp.CosmicRankQuests.CosmicRankQuestsAPI;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.enchantments.util.TieredEnchantments;
import me.cosmicpvp.CosmicGlobalKits.libs.FallenHero;
import me.cosmicpvp.CosmicGlobalKits.libs.GlobalKits;
import org.arkhamnetwork.enchantments.EnchantmentOrb;
import org.arkhamnetwork.enchantments.libs.OrbType;
import java.util.concurrent.ThreadLocalRandom;
import org.arkhamnetwork.enchantments.RandomizationScroll;
import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import me.ifamasssxd.cosmicdisguises.managers.CosmicDisguiseAPI;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import org.bukkit.Material;
import com.cosmicpvp.CosmicSpaceChests.libs.EvolutionKitItems;
import me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKits;
import com.compilex.CosmicDungeons.DungeonsRewardsAPI;
import com.compilex.CosmicDungeons.DungeonsAPI;
import com.compilex.CosmicCrate.api.CCAPI;
import com.compilex.CosmicCrate.libs.Version;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import com.google.common.collect.Lists;
import com.cosmicpvp.cosmicutils.utils.CC;
import org.bukkit.ChatColor;
import java.util.List;
import com.compilex.CosmicCrate.libs.CosmicCrateData;

public class April2018 extends CosmicCrateData
{
    @Override
    public List<String> getDescriptionLore(final String username) {
        final String bulletpoint = ChatColor.BOLD.toString() + "* ";
        return (List<String>)Lists.newArrayList((Object[])new String[] { ChatColor.LIGHT_PURPLE + "Unlocked by " + ChatColor.BOLD + username + ChatColor.LIGHT_PURPLE + " at " + ChatColor.YELLOW + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Chocolate Bunny", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Limited Edition Titles", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Mushroom Cow " + CC.YellowB + CC.Underline + "OR" + CC.Yellow + " Pig Zombie Disguise", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "2-3x Legendary+ Randomization Scrolls", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1-2x 100% Armor/Weapon Enchantment Orb (" + ChatColor.GOLD + ChatColor.BOLD + "12 Slot" + ChatColor.GOLD + ")", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "2-3x /gkit " + ChatColor.GOLD + ChatColor.BOLD + ChatColor.UNDERLINE.toString() + "OR" + ChatColor.GOLD + " /vkit Fallen Hero", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1-2x Random Heroic Enchantment Book", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Mystery Cosmic Crate", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Rank Quest", "", ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", ChatColor.GREEN + bulletpoint + ChatColor.GREEN + "April 2016 " + CC.GreenB + CC.Underline + "OR" + CC.Green + " 2017 Cosmic Crate", ChatColor.GREEN + bulletpoint + ChatColor.GREEN + "Abandoned Spaceship Dungeon Lootbag", ChatColor.GREEN + bulletpoint + ChatColor.GREEN + "Judgement /vkit Gem" });
    }
    
    @Override
    public ItemStack getRandomMetaItem(final Player player) {
        final double chance = Math.random();
        if (chance <= 0.2) {
            return CCAPI.getCosmicCrate((this.random.nextInt(10) <= 3) ? Version.APRIL_2017 : Version.APRIL, "April 2018 Cosmic Crate");
        }
        if (chance > 0.2 && chance < 0.7) {
            return DungeonsRewardsAPI.getDungeonLootBag(player, DungeonsAPI.get().getDungeon(0));
        }
        return EvolutionKitItems.getKitItem(EvolutionKits.JUDGEMENT);
    }
    
    @Override
    public List<ItemStack> getCrateLoot(final Player player) {
        final List<ItemStack> items = (List<ItemStack>)Lists.newArrayList();
        items.add(ItemUtils.setAmount(ItemUtils.buildItem(Material.LEATHER, (short)0, ChatColor.translateAlternateColorCodes('&', CC.AquaB + "Chocolate Bunny"), new String[] { "", ChatColor.GRAY + "An ancient bunny encapsulated", ChatColor.GRAY + "in a chocolatey tomb, yum!" }), 1));
        items.add(CosmicDisguiseAPI.createRedeemableDisguise(this.random.nextBoolean() ? "MUSHROOM_COW" : "PIG_ZOMBIE"));
        items.add(ItemUtils.setAmount(RandomizationScroll.getRandomizationScroll(ItemRarity.LEGENDARY), 2 + ThreadLocalRandom.current().nextInt(2)));
        final int x = this.random.nextInt(4);
        if (x == 0) {
            items.add(ItemUtils.getTitleVoucher("#EasterBunny"));
        }
        if (x == 1) {
            items.add(ItemUtils.getTitleVoucher("#AprilShowers"));
        }
        if (x == 2) {
            items.add(ItemUtils.getTitleVoucher("#Spring2018"));
        }
        if (x == 3) {
            if (Math.random() < 0.1) {
                items.add(ItemUtils.getTitleVoucher("#Easter2018"));
            }
            else {
                items.add(ItemUtils.getTitleVoucher("#EasterMayhem"));
            }
        }
        items.add(ItemUtils.setAmount(EnchantmentOrb.getEnchantmentOrb(100, 12, (this.random.nextInt(10) <= 6) ? OrbType.ARMOR : OrbType.WEAPON), (this.random.nextInt(10) <= 6) ? 1 : 2));
        items.add(ItemUtils.setAmount(ThreadLocalRandom.current().nextBoolean() ? FallenHero.getFallenHeroItem(GlobalKits.values()[this.random.nextInt(GlobalKits.values().length)]) : FallenHero.getFallenHeroItem(EvolutionKits.values()[this.random.nextInt(EvolutionKits.values().length)].getKit()), 2 + this.random.nextInt(2)));
        items.add(ItemID.removeItemID(ItemUtils.setAmount(TieredEnchantments.getMysteryEnchantmentBook(7), (this.random.nextInt(10) <= 7) ? 1 : 2)));
        int rankId = 1;
        if (Math.random() < 0.6) {
            rankId = 2;
            if (Math.random() < 0.25) {
                rankId = 3;
            }
            if (Math.random() < 0.1) {
                rankId = 4;
            }
        }
        items.add(CosmicRankQuestsAPI.getRankQuestItem(rankId));
        items.add(ItemID.removeItemID(ItemUtils.setAmount(CCAPI.getMysteryCrate(), 1)));
        return items;
    }
    
    @Override
    public short getNonColorPane() {
        return DyeColor.PURPLE.getWoolData();
    }
    
    @Override
    public List<Color> getConclusionFireworkColors() {
        return (List<Color>)Lists.newArrayList((Object[])new Color[] { Color.PURPLE, Color.YELLOW });
    }
    
    @Override
    public short getColorPane() {
        return DyeColor.YELLOW.getWoolData();
    }
}
