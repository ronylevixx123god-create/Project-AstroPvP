// 
// Decompiled by Procyon v0.5.36
// 

package com.compilex.CosmicCrate.libs.cratedata;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import com.compilex.CosmicCrate.libs.Version;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.enchantments.util.TieredEnchantments;
import com.cosmicpvp.cosmicutils.itembuilder.MaskBuilder;
import com.compilex.CosmicCrate.api.CCAPI;
import org.arkhamnetwork.Arkkit.patches.NewYears;
import java.util.concurrent.ThreadLocalRandom;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.compilex.CosmicArmorSets.enums.AbilityArmorSet;
import me.ifamasssxd.cosmicskycustoms.struct.CustomItemBuilder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import com.google.common.collect.Lists;
import com.cosmicpvp.cosmicutils.utils.CC;
import java.util.List;
import org.bukkit.ChatColor;
import com.compilex.CosmicCrate.libs.CosmicCrateData;

public class July2019 extends CosmicCrateData
{
    private ChatColor mainColor;
    private ChatColor secondColor;
    
    public July2019() {
        this.mainColor = ChatColor.RED;
        this.secondColor = ChatColor.BLUE;
    }
    
    @Override
    public List<String> getDescriptionLore(final String username) {
        final String bulletpoint = ChatColor.BOLD.toString() + "* ";
        return (List<String>)Lists.newArrayList((Object[])new String[] { this.mainColor + "Unlocked by " + ChatColor.BOLD + username + this.mainColor + " at " + this.secondColor + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Title: EST.1776", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", CC.YellowB + bulletpoint + CC.Yellow + "32-64x Space Fireworks", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", CC.Gold + bulletpoint + CC.Gold + "1-2x Heroic " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " Superior /cc", CC.Gold + bulletpoint + CC.Gold + "1x Outpost " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " Dungeon Mask", CC.Gold + bulletpoint + CC.Gold + "1x Gaia " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " Enchanter Pet", CC.Gold + bulletpoint + CC.Gold + "1x July 2018 " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " July 2017 /cc", CC.Gold + bulletpoint + CC.Gold + "2-3x Mystery Pet Box " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " Mask Generator", CC.Gold + bulletpoint + CC.Gold + "1-4x Random Heroic Enchantments", CC.Gold + bulletpoint + CC.Gold + "1-2x Mystery Lootbox", "", this.secondColor + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", this.secondColor + bulletpoint + this.secondColor + "1x Multi-Mask " + this.mainColor + "(Dragon X Monopoly)", this.secondColor + bulletpoint + this.secondColor + "1x 80% Armor Crystal " + this.mainColor + "(Yeti x Traveler)", this.secondColor + bulletpoint + this.secondColor + "1x 80% Anti M-Kit Crystal " + this.mainColor + "(Death Knight x Necromancer)" });
    }
    
    @Override
    public ItemStack getRandomMetaItem(final Player player) {
        final double chance = Math.random();
        if (chance <= 0.05) {
            final ItemStack mask = new CustomItemBuilder("multi_mask", new Object[] { "DRAGON_MASK", "MONOPOLY" }).build();
            return mask;
        }
        if (chance <= 0.75) {
            return new CustomItemBuilder("ANTI_MKIT", new Object[] { 80, AbilityArmorSet.DEATH_KNIGHT, AbilityArmorSet.NECROMANCER }).build();
        }
        return new CustomItemBuilder("bonusArmorCrystal", new Object[] { 80, ArmorSet.YETI, ArmorSet.DIMENSIONAL_TRAVELER }).build();
    }
    
    @Override
    public List<ItemStack> getCrateLoot(final Player player) {
        final List<ItemStack> items = (List<ItemStack>)Lists.newArrayList();
        items.add(ItemUtils.getTitleVoucher("EST.1776"));
        items.add(NewYears.getCosmicCracker(32 + ThreadLocalRandom.current().nextInt(33)));
        items.add(ItemUtils.setAmount((Math.random() < 0.7) ? CCAPI.getHeroicMysteryCrate() : CCAPI.getGodlyCosmicCrate(), this.getRandomOne()));
        items.add(new CustomItemBuilder("mystery_lootbox", new Object[0]).amount(1 + ThreadLocalRandom.current().nextInt(2)).build());
        items.add(new MaskBuilder((Math.random() < 0.9) ? "DUNGEON" : "OUTPOST").build());
        items.add(new CustomItemBuilder("inventory_pet", new Object[] { (Math.random() < 0.85) ? "ENCHANTER" : "GAIA" }).build());
        if (Math.random() < 0.35) {
            items.add(new CustomItemBuilder("mystery_pet_box", new Object[0]).amount(this.getRandomTwo()).build());
        }
        else {
            final ItemStack mask = new com.cosmicpvp.cosmicutils.itembuilder.CustomItemBuilder(com.cosmicpvp.cosmicutils.itembuilder.CustomItemBuilder.CustomItemType.RANDOM_MASK).setAmount(this.getRandomTwo()).build();
            items.add(mask);
        }
        items.add(ItemID.removeItemID(ItemUtils.setAmount(TieredEnchantments.getMysteryEnchantmentBook(7), this.getRandomOne() + this.getRandomOne())));
        items.add(CCAPI.getCosmicCrate((this.random.nextDouble() <= 0.3) ? Version.JULY_2018 : Version.JULY_2017, player.getName()));
        return items;
    }
    
    private int getRandomOne() {
        return (Math.random() <= 0.25) ? 2 : 1;
    }
    
    private int getRandomTwo() {
        return (Math.random() <= 0.25) ? 3 : 2;
    }
    
    @Override
    public short getNonColorPane() {
        return DyeColor.RED.getWoolData();
    }
    
    @Override
    public List<Color> getConclusionFireworkColors() {
        return (List<Color>)Lists.newArrayList((Object[])new Color[] { Color.RED, Color.BLUE, Color.WHITE });
    }
    
    @Override
    public short getColorPane() {
        return DyeColor.BLUE.getWoolData();
    }
}
