/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicArmorSets.enums.AbilityArmorSet
 *  com.compilex.CosmicArmorSets.enums.ArmorSet
 *  com.compilex.CosmicArmorSets.items.CrystalExtractionItem
 *  com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils
 *  com.cosmicpvp.cosmicutils.itembuilder.MaskBuilder
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.google.common.collect.Lists
 *  com.rit.sucy.CustomEnchantment
 *  com.rit.sucy.EnchantmentAPI
 *  me.ifamasssxd.cosmicskycustoms.struct.CustomItemBuilder
 *  org.arkhamnetwork.Arkkit.patches.Drinks
 *  org.arkhamnetwork.enchantments.EnchantmentBook
 *  org.bukkit.ChatColor
 *  org.bukkit.Color
 *  org.bukkit.DyeColor
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package com.compilex.CosmicCrate.libs.cratedata;

import com.compilex.CosmicArmorSets.enums.AbilityArmorSet;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.compilex.CosmicArmorSets.items.CrystalExtractionItem;
import com.compilex.CosmicCrate.api.CCAPI;
import com.compilex.CosmicCrate.libs.CosmicCrateData;
import com.compilex.CosmicCrate.libs.Version;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.itembuilder.MaskBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import me.ifamasssxd.cosmicskycustoms.struct.CustomItemBuilder;
import org.arkhamnetwork.Arkkit.patches.Drinks;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class June2019
extends CosmicCrateData {
    private ChatColor mainColor = ChatColor.GOLD;
    private ChatColor secondColor = ChatColor.RED;

    @Override
    public List<String> getDescriptionLore(String username) {
        String bulletpoint = ChatColor.BOLD.toString() + "* ";
        return Lists.newArrayList((Object[])new String[]{(Object)this.mainColor + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)this.mainColor + " at " + (Object)this.secondColor + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Mineman Year Book", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", CC.YellowB + bulletpoint + CC.Yellow + "16-32x Splash Space Drinks", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", CC.Gold + bulletpoint + CC.Gold + "1x Superior /cc", CC.Gold + bulletpoint + CC.Gold + "1-2x Mystery Lootbox", CC.Gold + bulletpoint + CC.Gold + "2-4x Mystery Fallen Heroes", CC.Gold + bulletpoint + CC.Gold + "2-3x 25-50% Crystal Extraction Item", CC.Gold + bulletpoint + CC.Gold + "1x June 2017, " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " 2018 /cc", CC.Gold + bulletpoint + CC.Gold + "1-2x Omni /gkit " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " Omni /vkit", CC.Gold + bulletpoint + CC.Gold + "1x 70-90% Mighty Cactus " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " Explosives Expert", "", (Object)this.secondColor + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)this.secondColor + bulletpoint + (Object)this.secondColor + "1x Joker " + (Object)this.secondColor + CC.Bold + CC.Underline + "OR" + (Object)this.secondColor + " Bunny " + (Object)this.secondColor + CC.Bold + CC.Underline + "OR" + (Object)this.secondColor + " Lover Mask", (Object)this.secondColor + bulletpoint + (Object)this.secondColor + "1x 75% Armor Crystal (Yijki x Dragon)", (Object)this.secondColor + bulletpoint + (Object)this.secondColor + "1x 75% Anti M-Kit Crystal (Ghost x Necromancer)"});
    }

    @Override
    public ItemStack getRandomMetaItem(Player player) {
        double chance = Math.random();
        if (chance <= 0.5) {
            ArrayList masks = Lists.newArrayList((Object[])new String[]{"BUNNY", "JOKER", "LOVER"});
            ItemStack mask = new MaskBuilder((String)masks.get(ThreadLocalRandom.current().nextInt(masks.size()))).build();
            return mask;
        }
        if (chance <= 0.85) {
            return new CustomItemBuilder("ANTI_MKIT", new Object[]{75, AbilityArmorSet.NECROMANCER, AbilityArmorSet.GHOST}).build();
        }
        return new CustomItemBuilder("bonusArmorCrystal", new Object[]{75, ArmorSet.MOTHER_OF_YIJKI, ArmorSet.DRAGON_SLAYER}).build();
    }

    @Override
    public List<ItemStack> getCrateLoot(Player player) {
        ArrayList items = Lists.newArrayList();
        items.add(ItemUtils.buildItem((Material)Material.WRITTEN_BOOK, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&f&lMineman &b&lY&d&le&b&La&d&lr &f&l&nBook"), (String[])new String[]{CC.Gray + "Another year, another year-book!,", CC.Gray + "Don't forget to have all the other", CC.Gray + "Cosmonauts write you some nice messages!"}));
        items.add(ItemUtils.setAmount((ItemStack)Drinks.getSplashDrink(), (int)(16 + new Random().nextInt(16))));
        items.add(ItemUtils.setAmount((ItemStack)new CrystalExtractionItem().createItem(player, new Object[]{25 + new Random().nextInt(26)}), (int)this.getRandomTwo()));
        items.add(new CustomItemBuilder("mystery_lootbox", new Object[0]).amount(1 + ThreadLocalRandom.current().nextInt(2)).build());
        items.add(new CustomItemBuilder("mystery_fallen_hero", new Object[0]).amount(2 + ThreadLocalRandom.current().nextInt(3)).build());
        items.add(new CustomItemBuilder(Math.random() < 0.7 ? "wildcard_gkit" : "wildcard_vkit", new Object[0]).amount(1 + ThreadLocalRandom.current().nextInt(2)).build());
        if (Math.random() < 0.65) {
            items.add(EnchantmentBook.getEnchantmentBook((CustomEnchantment)EnchantmentAPI.getEnchantment((String)"Mighty Cactus"), (int)2, (int)(70 + new Random().nextInt(20)), (int)(25 + new Random().nextInt(25))));
        } else {
            items.add(EnchantmentBook.getEnchantmentBook((CustomEnchantment)EnchantmentAPI.getEnchantment((String)"Explosives Expert"), (int)1, (int)(70 + new Random().nextInt(20)), (int)(25 + new Random().nextInt(25))));
        }
        items.add(CCAPI.getGodlyCosmicCrate());
        items.add(CCAPI.getCosmicCrate(this.random.nextDouble() <= 0.3 ? Version.JUNE_2018 : Version.JUNE_2017, player.getName()));
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
        return DyeColor.YELLOW.getWoolData();
    }

    @Override
    public List<Color> getConclusionFireworkColors() {
        return Lists.newArrayList((Object[])new Color[]{Color.YELLOW, Color.RED});
    }

    @Override
    public short getColorPane() {
        return DyeColor.RED.getWoolData();
    }
}

