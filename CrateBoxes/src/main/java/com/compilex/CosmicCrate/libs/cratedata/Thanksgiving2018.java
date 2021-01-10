/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicArmorSets.CosmicArmorSetsAPI
 *  com.compilex.CosmicDungeons.DungeonsRewardsAPI
 *  com.compilex.CosmicDungeons.dungeons.libs.Dungeon
 *  com.compilex.CosmicDungeons.dungeons.libs.DungeonType
 *  com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils
 *  com.cosmicpvp.cosmicutils.itembuilder.MaskBuilder
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.google.common.collect.Lists
 *  me.cosmicpvp.CosmicGlobalKits.CosmicGlobalKitsAPI
 *  me.cosmicpvp.CosmicGlobalKits.commands.CommandFallenHero
 *  me.cosmicpvp.CosmicGlobalKits.libs.MasteryKits
 *  me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKits
 *  org.arkhamnetwork.Arkkit.patches.EquipmentLootboxes
 *  org.arkhamnetwork.Arkkit.plugins.ArkPlugins
 *  org.arkhamnetwork.enchantments.EnchantmentOrb
 *  org.arkhamnetwork.enchantments.libs.OrbType
 *  org.arkhamnetwork.enchantments.util.TieredEnchantments
 *  org.bukkit.ChatColor
 *  org.bukkit.Color
 *  org.bukkit.DyeColor
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package com.compilex.CosmicCrate.libs.cratedata;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicCrate.api.CCAPI;
import com.compilex.CosmicCrate.libs.CosmicCrateData;
import com.compilex.CosmicCrate.libs.Version;
import com.compilex.CosmicDungeons.DungeonsRewardsAPI;
import com.compilex.CosmicDungeons.dungeons.libs.Dungeon;
import com.compilex.CosmicDungeons.dungeons.libs.DungeonType;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.itembuilder.MaskBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import me.cosmicpvp.CosmicGlobalKits.CosmicGlobalKitsAPI;
import me.cosmicpvp.CosmicGlobalKits.commands.CommandFallenHero;
import me.cosmicpvp.CosmicGlobalKits.libs.MasteryKits;
import me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKits;
import org.arkhamnetwork.Arkkit.patches.EquipmentLootboxes;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.arkhamnetwork.enchantments.EnchantmentOrb;
import org.arkhamnetwork.enchantments.libs.OrbType;
import org.arkhamnetwork.enchantments.util.TieredEnchantments;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Thanksgiving2018
extends CosmicCrateData {
    private ChatColor mainColor = ChatColor.GOLD;
    private ChatColor secondColor = ChatColor.DARK_GRAY;

    @Override
    public List<String> getDescriptionLore(String username) {
        String bulletpoint = ChatColor.BOLD.toString() + "* ";
        return Lists.newArrayList((Object[])new String[]{(Object)this.mainColor + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)this.mainColor + " at " + (Object)this.secondColor + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Cosmic Credit Card", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Limited Edition Titles", CC.YellowB + bulletpoint + (Object)ChatColor.YELLOW + "Turkey " + CC.YellowB + CC.Underline + "OR" + CC.Yellow + " Pilgrim Mask", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", CC.Gold + bulletpoint + CC.Gold + "1-2x Random Dungeon Lootbag", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1-5x Equipment Lootbox", CC.Gold + bulletpoint + CC.Gold + "1-5x Random Heroic Upgrade", CC.Gold + bulletpoint + CC.Gold + "1-5x Random Heroic Enchantment", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Thanksgiving 2017 /cc", CC.Gold + bulletpoint + CC.Gold + "1-2x 75% Armor/Weapon Enchant Orb (14 Slot)", "", (Object)this.secondColor + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)this.secondColor + bulletpoint + (Object)this.secondColor + "Lucky /vkit", (Object)this.secondColor + bulletpoint + (Object)this.secondColor + "Necromancer Mastery Kit", (Object)this.secondColor + bulletpoint + (Object)this.secondColor + "Necromancer Fallen Hero Bundle", (Object)this.secondColor + bulletpoint + (Object)this.secondColor + "1-2x Superior Mystery Cosmic Crate"});
    }

    @Override
    public ItemStack getRandomMetaItem(Player player) {
        double chance = Math.random();
        if (chance <= 0.325) {
            return EvolutionKits.LUCKY.getClaimableItem();
        }
        if (chance <= 0.65) {
            return this.setAmount(CCAPI.getGodlyCosmicCrate(), this.getRandomOne());
        }
        if (chance <= 0.9) {
            return CommandFallenHero.getNecromancerKitBundle();
        }
        return CosmicGlobalKitsAPI.createMasteryKitItem((MasteryKits)MasteryKits.NECROMANCER, (boolean)true);
    }

    @Override
    public List<ItemStack> getCrateLoot(Player player) {
        ItemStack mask;
        ArrayList items = Lists.newArrayList();
        items.add(ItemUtils.buildItem((Material)Material.STONE_BUTTON, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&6&lCosmic Credit Card"), (String[])new String[]{CC.Gray + "The fabled credit card of the Cosmonauts,", CC.Gray + "rumored to give 5% cash back on all p2w!"}));
        int x = this.random.nextInt(4);
        if (x == 0) {
            items.add(ItemUtils.getTitleVoucher((String)"BuyMeDaddy"));
        }
        if (x == 1) {
            items.add(ItemUtils.getTitleVoucher((String)"IStoleYourCreditCard"));
        }
        if (x == 2) {
            items.add(ItemUtils.getTitleVoucher((String)"Fatboy"));
        }
        if (x == 3) {
            if (Math.random() <= 0.3) {
                items.add(ItemUtils.getTitleVoucher((String)"#p2wOnABudget"));
            } else {
                items.add(ItemUtils.getTitleVoucher((String)"100%Off"));
            }
        }
        if ((mask = new MaskBuilder(this.random.nextBoolean() ? "TURKEY" : "PILGRIM").build()) != null) {
            items.add(mask);
        }
        items.add(this.setAmount(DungeonsRewardsAPI.getDungeonLootBag((Player)player, (Dungeon)(this.random.nextDouble() <= 0.8 ? DungeonType.ABANDONED_SHIP.getDungeonInstance() : DungeonType.DESTROYED_OUTPOST.getDungeonInstance())), this.random.nextDouble() >= 0.7 ? this.getRandomOne() : 1));
        items.add(this.setAmount(((EquipmentLootboxes)ArkPlugins.EQUIPMENT_LOOTBOX.getArkPlugin()).createLootboxItem(false), this.getRandomFive()));
        items.add(this.setAmount(CosmicArmorSetsAPI.createRandomArmorSetUpgrade(), this.getRandomFive()));
        items.add(this.setAmount(TieredEnchantments.getMysteryEnchantmentBook((int)7), this.getRandomFive()));
        items.add(CCAPI.getCosmicCrate(Version.THANKSGIVING_2017, player.getName()));
        items.add(ItemUtils.setAmount((ItemStack)EnchantmentOrb.getEnchantmentOrb((int)75, (int)14, (OrbType)(this.random.nextInt(10) <= 4 ? OrbType.ARMOR : OrbType.WEAPON)), (int)this.getRandomOne()));
        return items;
    }

    private int getRandomOne() {
        return Math.random() <= 0.3 ? 2 : 1;
    }

    private int getRandomThree() {
        return Math.random() <= 0.1 ? 3 : (Math.random() <= 0.3 ? 2 : 1);
    }

    private int getRandomFive() {
        int highestPossible = 3;
        if (Math.random() <= 0.1) {
            highestPossible = 5;
        } else if (Math.random() <= 0.3) {
            highestPossible = 4;
        }
        return this.random.nextInt(highestPossible) + 1;
    }

    @Override
    public short getNonColorPane() {
        return DyeColor.ORANGE.getWoolData();
    }

    @Override
    public List<Color> getConclusionFireworkColors() {
        return Lists.newArrayList((Object[])new Color[]{Color.YELLOW, Color.ORANGE});
    }

    @Override
    public short getColorPane() {
        return DyeColor.YELLOW.getWoolData();
    }
}

