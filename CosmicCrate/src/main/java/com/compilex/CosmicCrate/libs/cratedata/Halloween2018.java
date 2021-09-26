// 
// Decompiled by Procyon v0.5.36
// 

package com.compilex.CosmicCrate.libs.cratedata;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import com.compilex.CosmicCrate.api.CCAPI;
import com.compilex.CosmicCrate.libs.Version;
import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import org.arkhamnetwork.enchantments.util.TieredEnchantments;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import me.cosmicpvp.CosmicGlobalKits.commands.CommandFallenHero;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.arkhamnetwork.Arkkit.patches.EquipmentLootboxes;
import com.cosmicpvp.cosmicutils.itembuilder.MaskBuilder;
import org.bukkit.Material;
import org.arkhamnetwork.enchantments.EnchantmentOrb;
import org.arkhamnetwork.enchantments.libs.OrbType;
import me.cosmicpvp.CosmicGlobalKits.CosmicGlobalKitsAPI;
import me.cosmicpvp.CosmicGlobalKits.libs.MasteryKits;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import com.compilex.CosmicDungeons.DungeonsRewardsAPI;
import com.compilex.CosmicDungeons.dungeons.libs.DungeonType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import com.google.common.collect.Lists;
import com.cosmicpvp.cosmicutils.utils.CC;
import java.util.List;
import org.bukkit.ChatColor;
import com.compilex.CosmicCrate.libs.CosmicCrateData;

public class Halloween2018 extends CosmicCrateData
{
    private ChatColor mainColor;
    private ChatColor secondColor;
    
    public Halloween2018() {
        this.mainColor = ChatColor.GOLD;
        this.secondColor = ChatColor.DARK_RED;
    }
    
    @Override
    public List<String> getDescriptionLore(final String username) {
        final String bulletpoint = ChatColor.BOLD.toString() + "* ";
        return (List<String>)Lists.newArrayList((Object[])new String[] { this.mainColor + "Unlocked by " + ChatColor.BOLD + username + this.mainColor + " at " + this.secondColor + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Bag of Candy", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Spooky Edition Titles", CC.YellowB + bulletpoint + ChatColor.YELLOW + "Purge Mask", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", CC.Gold + bulletpoint + CC.Gold + "1x Heroic Fallen Hero Bundle", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "2-3x Equipment Lootbox", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1-3x Heroic Blackscroll " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " Holy Whitescroll", CC.Gold + bulletpoint + CC.Gold + "1-2x Random Heroic Upgrade", CC.Gold + bulletpoint + CC.Gold + "1-3x Random Heroic Enchantment", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Halloween 2016 " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " 2017 /cc", "", this.secondColor + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", this.secondColor + bulletpoint + this.secondColor + "1x 66% Armor/Weapon Enchant Orb (14 Slot)", this.secondColor + bulletpoint + this.secondColor + "Ghost Mastery Kit", this.secondColor + bulletpoint + this.secondColor + "1-2x Random Dungeon Lootbag" });
    }
    
    @Override
    public ItemStack getRandomMetaItem(final Player player) {
        final double chance = Math.random();
        if (chance <= 0.65) {
            return ItemUtils.setAmount(DungeonsRewardsAPI.getDungeonLootBag(player, (this.random.nextDouble() <= 0.8) ? DungeonType.ABANDONED_SHIP.getDungeonInstance() : DungeonType.DESTROYED_OUTPOST.getDungeonInstance()), (this.random.nextDouble() >= 0.7) ? this.getRandomOne() : 1);
        }
        if (chance <= 0.9) {
            return CosmicGlobalKitsAPI.createMasteryKitItem(MasteryKits.GHOST, true);
        }
        return ItemUtils.setAmount(EnchantmentOrb.getEnchantmentOrb(66, 14, (this.random.nextInt(10) <= 6) ? OrbType.ARMOR : OrbType.WEAPON), 1);
    }
    
    @Override
    public List<ItemStack> getCrateLoot(final Player player) {
        final List<ItemStack> items = (List<ItemStack>)Lists.newArrayList();
        items.add(ItemUtils.buildItem(Material.LAVA_BUCKET, (short)0, ChatColor.translateAlternateColorCodes('&', "&6&lBag Of Candy"), new String[] { ChatColor.GRAY + "A hard earned bag of the", CC.Gray + "best gum drops in the galaxy!" }));
        final int x = this.random.nextInt(4);
        if (x == 0) {
            items.add(ItemUtils.getTitleVoucher("TrickOrTrick"));
        }
        if (x == 1) {
            items.add(ItemUtils.getTitleVoucher("Nightmare"));
        }
        if (x == 2) {
            items.add(ItemUtils.getTitleVoucher("Psychopath"));
        }
        if (x == 3) {
            if (Math.random() <= 0.3) {
                items.add(ItemUtils.getTitleVoucher("NothingCanSaveYou"));
            }
            else {
                items.add(ItemUtils.getTitleVoucher("#Purge"));
            }
        }
        final ItemStack mask = new MaskBuilder("PURGE").build();
        if (mask != null) {
            items.add(mask);
        }
        items.add(this.setAmount(((EquipmentLootboxes)ArkPlugins.EQUIPMENT_LOOTBOX.getArkPlugin()).createLootboxItem(false), 1 + this.getRandomOne()));
        items.add(CommandFallenHero.getHeroicFallenHeroBundle());
        items.add(this.setAmount((this.random.nextDouble() <= 0.2) ? DungeonsRewardsAPI.getHolyWhiteScroll() : EnchantmentBook.getHeroicBlackscroll(true), this.getRandomThree()));
        items.add(this.setAmount(TieredEnchantments.getMysteryEnchantmentBook(7), this.getRandomThree()));
        items.add(this.setAmount(CosmicArmorSetsAPI.createRandomArmorSetUpgrade(), this.getRandomOne()));
        items.add(CCAPI.getCosmicCrate((Math.random() <= 0.65) ? Version.HALLOWEEN : Version.HALLOWEEN_2017, player.getName()));
        return items;
    }
    
    private int getRandomOne() {
        return (Math.random() <= 0.3) ? 2 : 1;
    }
    
    private int getRandomThree() {
        return (Math.random() <= 0.1) ? 3 : ((Math.random() <= 0.3) ? 2 : 1);
    }
    
    @Override
    public short getNonColorPane() {
        return DyeColor.RED.getWoolData();
    }
    
    @Override
    public List<Color> getConclusionFireworkColors() {
        return (List<Color>)Lists.newArrayList((Object[])new Color[] { Color.RED, Color.ORANGE });
    }
    
    @Override
    public short getColorPane() {
        return DyeColor.ORANGE.getWoolData();
    }
}
