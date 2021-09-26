// 
// Decompiled by Procyon v0.5.36
// 

package com.compilex.CosmicCrate.libs.cratedata;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import com.compilex.CosmicCrate.libs.Version;
import me.ifamasssxd.cosmicconquest.CosmicConquestAPI;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.enchantments.util.TieredEnchantments;
import com.cosmicpvp.CosmicSpaceChests.libs.KitItems;
import me.cosmicpvp.CosmicGlobalKits.libs.GlobalKits;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.arkhamnetwork.Arkkit.patches.EquipmentLootboxes;
import org.arkhamnetwork.enchantments.RandomizationScroll;
import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import org.arkhamnetwork.Arkkit.patches.LoreTag;
import org.bukkit.Material;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import org.arkhamnetwork.enchantments.EnchantmentOrb;
import org.arkhamnetwork.enchantments.libs.OrbType;
import com.compilex.CosmicCrate.api.CCAPI;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import com.google.common.collect.Lists;
import com.cosmicpvp.cosmicutils.utils.CC;
import org.bukkit.ChatColor;
import java.util.List;
import com.compilex.CosmicCrate.libs.CosmicCrateData;

public class June2018 extends CosmicCrateData
{
    @Override
    public List<String> getDescriptionLore(final String username) {
        final String bulletpoint = ChatColor.BOLD.toString() + "* ";
        return (List<String>)Lists.newArrayList((Object[])new String[] { ChatColor.DARK_RED + "Unlocked by " + ChatColor.BOLD + username + ChatColor.DARK_RED + " at " + ChatColor.RED + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Report Card", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Limited Edition Titles", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "2-3x Item Nametags " + CC.YellowB + CC.Underline + "OR" + CC.Yellow + " Lore Crystals", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "1-4x Godly Randomization Scroll", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", CC.Gold + bulletpoint + CC.Gold + "Equipment Lootbox", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1-3x Random Heroic Enchantment Book", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1-3x Heroic Faction Crystals", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1-2x Random /gkit Gem", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "June 2017 CC", "", ChatColor.DARK_RED.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", ChatColor.DARK_RED + bulletpoint + ChatColor.DARK_RED + "Superior Mystery Cosmic Crate", ChatColor.DARK_RED + bulletpoint + ChatColor.DARK_RED + "80-95% Armor/Weapon Enchantment Orb (" + ChatColor.DARK_RED + ChatColor.BOLD + "13 Slot" + ChatColor.DARK_RED + ")", ChatColor.DARK_RED + bulletpoint + ChatColor.DARK_RED + "Supreme Armor Set Piece" });
    }
    
    @Override
    public ItemStack getRandomMetaItem(final Player player) {
        final double chance = Math.random();
        if (chance <= 0.1) {
            return CCAPI.getGodlyCosmicCrate();
        }
        if (chance < 0.65) {
            return ItemUtils.setAmount(EnchantmentOrb.getEnchantmentOrb(this.random.nextInt(16) + 80, 13, (this.random.nextInt(10) <= 6) ? OrbType.ARMOR : OrbType.WEAPON), 1);
        }
        return ArmorSet.SUPREME.getRandomPeice(true);
    }
    
    @Override
    public List<ItemStack> getCrateLoot(final Player player) {
        final List<ItemStack> items = (List<ItemStack>)Lists.newArrayList();
        String grade = "an " + CC.Red + "F" + CC.Gray;
        final int rand = this.random.nextInt(10);
        if (rand <= 2) {
            grade = "a " + CC.YellowB + "C+";
        }
        else if (rand <= 6 && rand > 4) {
            grade = "a " + CC.AquaB + "B+";
        }
        else if (rand == 8) {
            grade = "an " + CC.GreenB + "A";
        }
        else if (rand == 9) {
            grade = "an " + CC.GreenB + "A+";
        }
        items.add(ItemUtils.setAmount(ItemUtils.buildItem(Material.PAPER, (short)0, ChatColor.translateAlternateColorCodes('&', CC.AquaB + "Report Card"), new String[] { "", ChatColor.GRAY + "Your grades are in!", ChatColor.GRAY + "It seems you got " + grade + CC.Gray + "!" }), 1));
        items.add(ItemUtils.setAmount(this.random.nextBoolean() ? ItemUtils.getItemNametag() : LoreTag.getLoreTagItem(), this.random.nextInt(2) + 2));
        items.add(ItemUtils.setAmount(RandomizationScroll.getRandomizationScroll(ItemRarity.GOD), 1 + this.random.nextInt(4)));
        final int x = this.random.nextInt(4);
        if (x == 0) {
            items.add(ItemUtils.getTitleVoucher("#SchoolsOut"));
        }
        if (x == 1) {
            items.add(ItemUtils.getTitleVoucher("#Summer2018"));
        }
        if (x == 2) {
            items.add(ItemUtils.getTitleVoucher("ImYourFather"));
        }
        if (x == 3) {
            if (Math.random() < 0.1) {
                items.add(ItemUtils.getTitleVoucher("#WhosYourDaddy"));
            }
            else {
                items.add(ItemUtils.getTitleVoucher("#Tanned"));
            }
        }
        items.add(((EquipmentLootboxes)ArkPlugins.EQUIPMENT_LOOTBOX.getArkPlugin()).createLootboxItem(false));
        items.add(ItemUtils.setAmount(KitItems.getKitItem(GlobalKits.values()[this.random.nextInt(GlobalKits.values().length)]), this.random.nextInt(2) + 1));
        items.add(ItemID.removeItemID(ItemUtils.setAmount(TieredEnchantments.getMysteryEnchantmentBook(7), (this.random.nextDouble() <= 0.1) ? 3 : (this.random.nextInt(2) + 1))));
        items.add(CosmicConquestAPI.createHeroicToken(this.random.nextInt(4) + 1));
        items.add(CCAPI.getCosmicCrate(Version.JUNE_2017, player.getName()));
        return items;
    }
    
    @Override
    public short getNonColorPane() {
        return DyeColor.RED.getWoolData();
    }
    
    @Override
    public List<Color> getConclusionFireworkColors() {
        return (List<Color>)Lists.newArrayList((Object[])new Color[] { Color.RED, Color.PURPLE });
    }
    
    @Override
    public short getColorPane() {
        return DyeColor.PINK.getWoolData();
    }
}
