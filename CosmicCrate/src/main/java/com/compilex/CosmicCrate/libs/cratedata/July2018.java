// 
// Decompiled by Procyon v0.5.36
// 

package com.compilex.CosmicCrate.libs.cratedata;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import com.compilex.CosmicCrate.libs.Version;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.enchantments.util.TieredEnchantments;
import com.compilex.CosmicCrate.api.CCAPI;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.arkhamnetwork.Arkkit.patches.EquipmentLootboxes;
import me.ifamasssxd.cosmicdisguises.managers.CosmicDisguiseAPI;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import org.bukkit.Material;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.HeroicArmorType;
import com.compilex.CosmicDungeons.DungeonsRewardsAPI;
import com.compilex.CosmicDungeons.DungeonsAPI;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import com.google.common.collect.Lists;
import com.cosmicpvp.cosmicutils.utils.CC;
import org.bukkit.ChatColor;
import java.util.List;
import com.compilex.CosmicCrate.libs.CosmicCrateData;

public class July2018 extends CosmicCrateData
{
    @Override
    public List<String> getDescriptionLore(final String username) {
        final String bulletpoint = ChatColor.BOLD.toString() + "* ";
        return (List<String>)Lists.newArrayList((Object[])new String[] { ChatColor.WHITE + "Unlocked by " + ChatColor.BOLD + username + ChatColor.WHITE + " at " + ChatColor.BLUE + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Summer Vibes", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Limited Edition Titles", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Blaze " + CC.YellowB + CC.Underline + "OR" + CC.Yellow + " Ghast Disguise", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", CC.Gold + bulletpoint + CC.Gold + "1-2x Equipment Lootbox", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1-2x Heroic " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " Superior Mystery Crate", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1-3x Random Heroic Enchantment", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1x Heroic Blackscroll " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " Holy Whitescroll", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1x July 2017 " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " 2016 CC", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1x Dungeon Portal Ingredient", "", ChatColor.BLUE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", ChatColor.BLUE + bulletpoint + ChatColor.BLUE + "Destroyed Outpost Dungeon Portal", ChatColor.BLUE + bulletpoint + ChatColor.BLUE + "Heroic Armor", ChatColor.BLUE + bulletpoint + ChatColor.BLUE + "Dimensional Traveler Set Piece", ChatColor.BLUE + bulletpoint + ChatColor.BLUE + "Abandoned Spaceship Lootbag" });
    }
    
    @Override
    public ItemStack getRandomMetaItem(final Player player) {
        final double chance = Math.random();
        if (chance <= 0.4) {
            return DungeonsRewardsAPI.getDungeonLootBag(player, DungeonsAPI.get().getDungeon(0));
        }
        if (chance <= 0.65) {
            return DungeonsAPI.get().getDungeon(1).getDungeonPortal();
        }
        if (chance <= 0.85) {
            return CosmicArmorSetsAPI.createHeroicPeice(HeroicArmorType.values()[this.random.nextInt(HeroicArmorType.values().length)]);
        }
        return ArmorSet.DIMENSIONAL_TRAVELER.getRandomPeice(true);
    }
    
    @Override
    public List<ItemStack> getCrateLoot(final Player player) {
        final List<ItemStack> items = (List<ItemStack>)Lists.newArrayList();
        items.add(ItemUtils.buildItem(Material.SPECKLED_MELON, (short)0, ChatColor.translateAlternateColorCodes('&', "&f&lSummer &a&n&oVibes"), new String[] { ChatColor.GRAY + "You cannot help it, school is", ChatColor.GRAY + "out. It's summer everyday, bro." }));
        items.add(CosmicDisguiseAPI.createRedeemableDisguise((this.random.nextDouble() <= 0.75) ? "BLAZE" : "GHAST"));
        final int x = this.random.nextInt(4);
        if (x == 0) {
            items.add(ItemUtils.getTitleVoucher("#4thOfJuly"));
        }
        if (x == 1) {
            items.add(ItemUtils.getTitleVoucher("#Fireworks"));
        }
        if (x == 2) {
            items.add(ItemUtils.getTitleVoucher("LightUpMyWorld"));
        }
        if (x == 3) {
            if (Math.random() < 0.1) {
                items.add(ItemUtils.getTitleVoucher("#CosmicNChill"));
            }
            else {
                items.add(ItemUtils.getTitleVoucher("#Summer2018"));
            }
        }
        items.add(ItemUtils.setAmount(((EquipmentLootboxes)ArkPlugins.EQUIPMENT_LOOTBOX.getArkPlugin()).createLootboxItem(false), this.getRandomOne()));
        items.add(ItemUtils.setAmount((this.random.nextDouble() <= 0.75) ? CCAPI.getHeroicMysteryCrate() : CCAPI.getGodlyCosmicCrate(), this.getRandomOne()));
        items.add(ItemID.removeItemID(ItemUtils.setAmount(TieredEnchantments.getMysteryEnchantmentBook(7), (this.random.nextDouble() <= 0.4) ? (this.random.nextInt(3) + 1) : (this.random.nextInt(2) + 1))));
        items.add(this.random.nextBoolean() ? DungeonsRewardsAPI.getHolyWhiteScroll() : EnchantmentBook.getHeroicBlackscroll(true));
        items.add(CCAPI.getCosmicCrate((Math.random() <= 0.65) ? Version.JULY : Version.JULY_2017, player.getName()));
        if (Math.random() < 0.25) {
            if (Math.random() <= 0.25) {
                if (Math.random() < 0.05) {
                    items.add(ItemUtils.setAmount(DungeonsAPI.get().getDimensionWeb().clone(), 1));
                }
                else {
                    items.add(ItemUtils.setAmount(DungeonsAPI.get().getCosmonautFuel().clone(), 1));
                }
            }
            else if (Math.random() <= 0.25) {
                items.add(ItemUtils.setAmount(DungeonsAPI.get().getInterdimensionalCore().clone(), 1));
            }
            else {
                items.add(ItemUtils.setAmount(DungeonsAPI.get().getTeleportationCrystal().clone(), 1));
            }
        }
        else {
            items.add(ItemUtils.setAmount(DungeonsAPI.get().getEnchantedObsidian().clone(), 1));
        }
        return items;
    }
    
    private int getRandomOne() {
        return (Math.random() <= 0.3) ? 2 : 1;
    }
    
    @Override
    public short getNonColorPane() {
        return DyeColor.WHITE.getWoolData();
    }
    
    @Override
    public List<Color> getConclusionFireworkColors() {
        return (List<Color>)Lists.newArrayList((Object[])new Color[] { Color.RED, Color.BLUE });
    }
    
    @Override
    public short getColorPane() {
        return DyeColor.RED.getWoolData();
    }
}
