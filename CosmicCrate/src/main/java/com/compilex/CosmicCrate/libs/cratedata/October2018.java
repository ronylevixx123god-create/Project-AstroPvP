// 
// Decompiled by Procyon v0.5.36
// 

package com.compilex.CosmicCrate.libs.cratedata;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import com.cosmicpvp.cosmicutils.utils.CustomItemUtils;
import com.compilex.CosmicCrate.api.CCAPI;
import com.compilex.CosmicCrate.libs.Version;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI;
import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import org.arkhamnetwork.enchantments.util.TieredEnchantments;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.arkhamnetwork.Arkkit.patches.EquipmentLootboxes;
import org.arkhamnetwork.Arkkit.patches.LoreTag;
import org.arkhamnetwork.Arkkit.patches.ItemNametag;
import org.bukkit.Material;
import com.cosmicpvp.CosmicRankQuests.HeroicRankCrystalAPI;
import com.compilex.CosmicDungeons.DungeonsAPI;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import org.arkhamnetwork.enchantments.EnchantmentOrb;
import org.arkhamnetwork.enchantments.libs.OrbType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import com.google.common.collect.Lists;
import com.cosmicpvp.cosmicutils.utils.CC;
import java.util.List;
import org.bukkit.ChatColor;
import com.compilex.CosmicCrate.libs.CosmicCrateData;

public class October2018 extends CosmicCrateData
{
    private ChatColor mainColor;
    private ChatColor secondColor;
    
    public October2018() {
        this.mainColor = ChatColor.AQUA;
        this.secondColor = ChatColor.GOLD;
    }
    
    @Override
    public List<String> getDescriptionLore(final String username) {
        final String bulletpoint = ChatColor.BOLD.toString() + "* ";
        return (List<String>)Lists.newArrayList((Object[])new String[] { this.mainColor + "Unlocked by " + ChatColor.BOLD + username + this.mainColor + " at " + this.secondColor + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Pumpkin Cake", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Limited Edition Titles", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "2-4x Item Nametags " + CC.YellowB + CC.Underline + "OR" + CC.Yellow + " Lore Crystals", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", CC.Gold + bulletpoint + CC.Gold + "1-2x Heroic G-Kit Generator", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1-2x Equipment Lootbox", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "2-4x /vkit Evolution Gems", CC.Gold + bulletpoint + CC.Gold + "1-3x Random Heroic Enchantment Books", CC.Gold + bulletpoint + CC.Gold + "2-3x Godly Space Chests", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "October 2016 " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " 2017 /cc", "", this.mainColor + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", this.mainColor + bulletpoint + this.mainColor + "1-2x 90% Armor/Weapon Enchant Orb (13 Slot)", this.mainColor + bulletpoint + this.mainColor + "Heroic Rank Crystal", this.mainColor + bulletpoint + this.mainColor + "1-3x Random Dungeon Portal" });
    }
    
    @Override
    public ItemStack getRandomMetaItem(final Player player) {
        final double chance = Math.random();
        if (chance <= 0.6) {
            return ItemUtils.setAmount(EnchantmentOrb.getEnchantmentOrb(90, 13, (this.random.nextInt(10) <= 6) ? OrbType.ARMOR : OrbType.WEAPON), 1 + this.random.nextInt(2));
        }
        if (chance <= 0.9) {
            return this.setAmount(DungeonsAPI.get().getDungeon((int)(this.random.nextBoolean() ? 1 : 0)).getDungeonPortal(), 1 + (this.random.nextBoolean() ? this.random.nextInt(3) : 0));
        }
        return HeroicRankCrystalAPI.getHeroicRankCrystalItem();
    }
    
    @Override
    public List<ItemStack> getCrateLoot(final Player player) {
        final List<ItemStack> items = (List<ItemStack>)Lists.newArrayList();
        items.add(ItemUtils.buildItem(Material.PUMPKIN_PIE, (short)0, ChatColor.translateAlternateColorCodes('&', "&e&lPumpkin Cake"), new String[] { ChatColor.GRAY + "A delicious cake that smells", CC.Gray + "of nutmeg and other Fall spices." }));
        final int x = this.random.nextInt(4);
        if (x == 0) {
            items.add(ItemUtils.getTitleVoucher("chilly"));
        }
        if (x == 1) {
            items.add(ItemUtils.getTitleVoucher("CarveMePls"));
        }
        if (x == 2) {
            items.add(ItemUtils.getTitleVoucher("WinterAlreadyCame"));
        }
        if (x == 3) {
            items.add(ItemUtils.getTitleVoucher("#PumpkinSpiceIsNice"));
        }
        items.add(this.setAmount(this.random.nextBoolean() ? ItemNametag.getNametagItem() : LoreTag.getLoreTagItem(), 2 + this.random.nextInt(3)));
        items.add(this.setAmount(((EquipmentLootboxes)ArkPlugins.EQUIPMENT_LOOTBOX.getArkPlugin()).createHeroicGKitGenerator(false), 1 + this.random.nextInt(2)));
        items.add(this.setAmount(((EquipmentLootboxes)ArkPlugins.EQUIPMENT_LOOTBOX.getArkPlugin()).createLootboxItem(false), this.getRandomOne()));
        items.add(this.setAmount(TieredEnchantments.getMysteryEnchantmentBook(7), 1 + this.random.nextInt(3)));
        items.add(ItemID.removeItemID(ItemUtils.setAmount(CosmicSpaceChestsAPI.getDropPackageChest(ItemRarity.GOD), 1 + this.getRandomOne())));
        items.add(CCAPI.getCosmicCrate((Math.random() <= 0.65) ? Version.OCTOBER : Version.OCTOBER_2017, player.getName()));
        items.add(this.setAmount(CustomItemUtils.createRandomEvolutionKitGem(), 2 + this.random.nextInt(3)));
        return items;
    }
    
    private int getRandomOne() {
        return (Math.random() <= 0.3) ? 2 : 1;
    }
    
    @Override
    public short getNonColorPane() {
        return DyeColor.ORANGE.getWoolData();
    }
    
    @Override
    public List<Color> getConclusionFireworkColors() {
        return (List<Color>)Lists.newArrayList((Object[])new Color[] { Color.AQUA, Color.YELLOW });
    }
    
    @Override
    public short getColorPane() {
        return DyeColor.LIGHT_BLUE.getWoolData();
    }
}
