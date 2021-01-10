/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicArmorSets.enums.AbilityArmorSet
 *  com.compilex.CosmicArmorSets.enums.ArmorSet
 *  com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils
 *  com.cosmicpvp.cosmicutils.itembuilder.CustomItemBuilder
 *  com.cosmicpvp.cosmicutils.itembuilder.CustomItemBuilder$CustomItemType
 *  com.cosmicpvp.cosmicutils.itembuilder.MaskBuilder
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.google.common.collect.Lists
 *  me.ifamasssxd.cosmicskycustoms.struct.CustomItemBuilder
 *  org.arkhamnetwork.Arkkit.patches.ItemID
 *  org.arkhamnetwork.Arkkit.patches.NewYears
 *  org.arkhamnetwork.enchantments.util.TieredEnchantments
 *  org.bukkit.ChatColor
 *  org.bukkit.Color
 *  org.bukkit.DyeColor
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package com.compilex.CosmicCrate.libs.cratedata;

import com.compilex.CosmicArmorSets.enums.AbilityArmorSet;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.compilex.CosmicCrate.api.CCAPI;
import com.compilex.CosmicCrate.libs.CosmicCrateData;
import com.compilex.CosmicCrate.libs.Version;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.itembuilder.CustomItemBuilder;
import com.cosmicpvp.cosmicutils.itembuilder.MaskBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.Arkkit.patches.NewYears;
import org.arkhamnetwork.enchantments.util.TieredEnchantments;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class July2019
extends CosmicCrateData {
    private ChatColor mainColor = ChatColor.RED;
    private ChatColor secondColor = ChatColor.BLUE;

    @Override
    public List<String> getDescriptionLore(String username) {
        String bulletpoint = ChatColor.BOLD.toString() + "* ";
        return Lists.newArrayList((Object[])new String[]{(Object)this.mainColor + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)this.mainColor + " at " + (Object)this.secondColor + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Title: EST.1776", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", CC.YellowB + bulletpoint + CC.Yellow + "32-64x Space Fireworks", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", CC.Gold + bulletpoint + CC.Gold + "1-2x Heroic " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " Superior /cc", CC.Gold + bulletpoint + CC.Gold + "1x Outpost " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " Dungeon Mask", CC.Gold + bulletpoint + CC.Gold + "1x Gaia " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " Enchanter Pet", CC.Gold + bulletpoint + CC.Gold + "1x July 2018 " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " July 2017 /cc", CC.Gold + bulletpoint + CC.Gold + "2-3x Mystery Pet Box " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " Mask Generator", CC.Gold + bulletpoint + CC.Gold + "1-4x Random Heroic Enchantments", CC.Gold + bulletpoint + CC.Gold + "1-2x Mystery Lootbox", "", (Object)this.secondColor + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)this.secondColor + bulletpoint + (Object)this.secondColor + "1x Multi-Mask " + (Object)this.mainColor + "(Dragon X Monopoly)", (Object)this.secondColor + bulletpoint + (Object)this.secondColor + "1x 80% Armor Crystal " + (Object)this.mainColor + "(Yeti x Traveler)", (Object)this.secondColor + bulletpoint + (Object)this.secondColor + "1x 80% Anti M-Kit Crystal " + (Object)this.mainColor + "(Death Knight x Necromancer)"});
    }

    @Override
    public ItemStack getRandomMetaItem(Player player) {
        double chance = Math.random();
        if (chance <= 0.05) {
            ItemStack mask = new me.ifamasssxd.cosmicskycustoms.struct.CustomItemBuilder("multi_mask", new Object[]{"DRAGON_MASK", "MONOPOLY"}).build();
            return mask;
        }
        if (chance <= 0.75) {
            return new me.ifamasssxd.cosmicskycustoms.struct.CustomItemBuilder("ANTI_MKIT", new Object[]{80, AbilityArmorSet.DEATH_KNIGHT, AbilityArmorSet.NECROMANCER}).build();
        }
        return new me.ifamasssxd.cosmicskycustoms.struct.CustomItemBuilder("bonusArmorCrystal", new Object[]{80, ArmorSet.YETI, ArmorSet.DIMENSIONAL_TRAVELER}).build();
    }

    @Override
    public List<ItemStack> getCrateLoot(Player player) {
        ArrayList items = Lists.newArrayList();
        items.add(ItemUtils.getTitleVoucher((String)"EST.1776"));
        items.add(NewYears.getCosmicCracker((int)(32 + ThreadLocalRandom.current().nextInt(33))));
        items.add(ItemUtils.setAmount((ItemStack)(Math.random() < 0.7 ? CCAPI.getHeroicMysteryCrate() : CCAPI.getGodlyCosmicCrate()), (int)this.getRandomOne()));
        items.add(new me.ifamasssxd.cosmicskycustoms.struct.CustomItemBuilder("mystery_lootbox", new Object[0]).amount(1 + ThreadLocalRandom.current().nextInt(2)).build());
        items.add(new MaskBuilder(Math.random() < 0.9 ? "DUNGEON" : "OUTPOST").build());
        items.add(new me.ifamasssxd.cosmicskycustoms.struct.CustomItemBuilder("inventory_pet", new Object[]{Math.random() < 0.85 ? "ENCHANTER" : "GAIA"}).build());
        if (Math.random() < 0.35) {
            items.add(new me.ifamasssxd.cosmicskycustoms.struct.CustomItemBuilder("mystery_pet_box", new Object[0]).amount(this.getRandomTwo()).build());
        } else {
            ItemStack mask = new CustomItemBuilder(CustomItemBuilder.CustomItemType.RANDOM_MASK).setAmount(this.getRandomTwo()).build();
            items.add(mask);
        }
        items.add(ItemID.removeItemID((ItemStack)ItemUtils.setAmount((ItemStack)TieredEnchantments.getMysteryEnchantmentBook((int)7), (int)(this.getRandomOne() + this.getRandomOne()))));
        items.add(CCAPI.getCosmicCrate(this.random.nextDouble() <= 0.3 ? Version.JULY_2018 : Version.JULY_2017, player.getName()));
        return items;
    }

    private int getRandomOne() {
        return Math.random() <= 0.25 ? 2 : 1;
    }

    private int getRandomTwo() {
        return Math.random() <= 0.25 ? 3 : 2;
    }

    @Override
    public short getNonColorPane() {
        return DyeColor.RED.getWoolData();
    }

    @Override
    public List<Color> getConclusionFireworkColors() {
        return Lists.newArrayList((Object[])new Color[]{Color.RED, Color.BLUE, Color.WHITE});
    }

    @Override
    public short getColorPane() {
        return DyeColor.BLUE.getWoolData();
    }
}

