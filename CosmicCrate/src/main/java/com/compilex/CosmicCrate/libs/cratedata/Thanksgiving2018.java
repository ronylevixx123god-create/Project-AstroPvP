// 
// Decompiled by Procyon v0.5.36
// 

package com.compilex.CosmicCrate.libs.cratedata;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.arkhamnetwork.enchantments.EnchantmentOrb;
import org.arkhamnetwork.enchantments.libs.OrbType;
import com.compilex.CosmicCrate.libs.Version;
import org.arkhamnetwork.enchantments.util.TieredEnchantments;
import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.arkhamnetwork.Arkkit.patches.EquipmentLootboxes;
import com.compilex.CosmicDungeons.DungeonsRewardsAPI;
import com.compilex.CosmicDungeons.dungeons.libs.DungeonType;
import com.cosmicpvp.cosmicutils.itembuilder.MaskBuilder;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import org.bukkit.Material;
import me.cosmicpvp.CosmicGlobalKits.CosmicGlobalKitsAPI;
import me.cosmicpvp.CosmicGlobalKits.libs.MasteryKits;
import me.cosmicpvp.CosmicGlobalKits.commands.CommandFallenHero;
import com.compilex.CosmicCrate.api.CCAPI;
import me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKits;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import com.google.common.collect.Lists;
import com.cosmicpvp.cosmicutils.utils.CC;
import java.util.List;
import org.bukkit.ChatColor;
import com.compilex.CosmicCrate.libs.CosmicCrateData;

public class Thanksgiving2018 extends CosmicCrateData
{
    private ChatColor mainColor;
    private ChatColor secondColor;
    
    public Thanksgiving2018() {
        this.mainColor = ChatColor.GOLD;
        this.secondColor = ChatColor.DARK_GRAY;
    }
    
    @Override
    public List<String> getDescriptionLore(final String username) {
        final String bulletpoint = ChatColor.BOLD.toString() + "* ";
        return (List<String>)Lists.newArrayList((Object[])new String[] { this.mainColor + "Unlocked by " + ChatColor.BOLD + username + this.mainColor + " at " + this.secondColor + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Cosmic Credit Card", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Limited Edition Titles", CC.YellowB + bulletpoint + ChatColor.YELLOW + "Turkey " + CC.YellowB + CC.Underline + "OR" + CC.Yellow + " Pilgrim Mask", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", CC.Gold + bulletpoint + CC.Gold + "1-2x Random Dungeon Lootbag", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1-5x Equipment Lootbox", CC.Gold + bulletpoint + CC.Gold + "1-5x Random Heroic Upgrade", CC.Gold + bulletpoint + CC.Gold + "1-5x Random Heroic Enchantment", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Thanksgiving 2017 /cc", CC.Gold + bulletpoint + CC.Gold + "1-2x 75% Armor/Weapon Enchant Orb (14 Slot)", "", this.secondColor + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", this.secondColor + bulletpoint + this.secondColor + "Lucky /vkit", this.secondColor + bulletpoint + this.secondColor + "Necromancer Mastery Kit", this.secondColor + bulletpoint + this.secondColor + "Necromancer Fallen Hero Bundle", this.secondColor + bulletpoint + this.secondColor + "1-2x Superior Mystery Cosmic Crate" });
    }
    
    @Override
    public ItemStack getRandomMetaItem(final Player player) {
        final double chance = Math.random();
        if (chance <= 0.325) {
            return EvolutionKits.LUCKY.getClaimableItem();
        }
        if (chance <= 0.65) {
            return this.setAmount(CCAPI.getGodlyCosmicCrate(), this.getRandomOne());
        }
        if (chance <= 0.9) {
            return CommandFallenHero.getNecromancerKitBundle();
        }
        return CosmicGlobalKitsAPI.createMasteryKitItem(MasteryKits.NECROMANCER, true);
    }
    
    @Override
    public List<ItemStack> getCrateLoot(final Player player) {
        final List<ItemStack> items = (List<ItemStack>)Lists.newArrayList();
        items.add(ItemUtils.buildItem(Material.STONE_BUTTON, (short)0, ChatColor.translateAlternateColorCodes('&', "&6&lCosmic Credit Card"), new String[] { CC.Gray + "The fabled credit card of the Cosmonauts,", CC.Gray + "rumored to give 5% cash back on all p2w!" }));
        final int x = this.random.nextInt(4);
        if (x == 0) {
            items.add(ItemUtils.getTitleVoucher("BuyMeDaddy"));
        }
        if (x == 1) {
            items.add(ItemUtils.getTitleVoucher("IStoleYourCreditCard"));
        }
        if (x == 2) {
            items.add(ItemUtils.getTitleVoucher("Fatboy"));
        }
        if (x == 3) {
            if (Math.random() <= 0.3) {
                items.add(ItemUtils.getTitleVoucher("#p2wOnABudget"));
            }
            else {
                items.add(ItemUtils.getTitleVoucher("100%Off"));
            }
        }
        final ItemStack mask = new MaskBuilder(this.random.nextBoolean() ? "TURKEY" : "PILGRIM").build();
        if (mask != null) {
            items.add(mask);
        }
        items.add(this.setAmount(DungeonsRewardsAPI.getDungeonLootBag(player, (this.random.nextDouble() <= 0.8) ? DungeonType.ABANDONED_SHIP.getDungeonInstance() : DungeonType.DESTROYED_OUTPOST.getDungeonInstance()), (this.random.nextDouble() >= 0.7) ? this.getRandomOne() : 1));
        items.add(this.setAmount(((EquipmentLootboxes)ArkPlugins.EQUIPMENT_LOOTBOX.getArkPlugin()).createLootboxItem(false), this.getRandomFive()));
        items.add(this.setAmount(CosmicArmorSetsAPI.createRandomArmorSetUpgrade(), this.getRandomFive()));
        items.add(this.setAmount(TieredEnchantments.getMysteryEnchantmentBook(7), this.getRandomFive()));
        items.add(CCAPI.getCosmicCrate(Version.THANKSGIVING_2017, player.getName()));
        items.add(ItemUtils.setAmount(EnchantmentOrb.getEnchantmentOrb(75, 14, (this.random.nextInt(10) <= 4) ? OrbType.ARMOR : OrbType.WEAPON), this.getRandomOne()));
        return items;
    }
    
    private int getRandomOne() {
        return (Math.random() <= 0.3) ? 2 : 1;
    }
    
    private int getRandomThree() {
        return (Math.random() <= 0.1) ? 3 : ((Math.random() <= 0.3) ? 2 : 1);
    }
    
    private int getRandomFive() {
        int highestPossible = 3;
        if (Math.random() <= 0.1) {
            highestPossible = 5;
        }
        else if (Math.random() <= 0.3) {
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
        return (List<Color>)Lists.newArrayList((Object[])new Color[] { Color.YELLOW, Color.ORANGE });
    }
    
    @Override
    public short getColorPane() {
        return DyeColor.YELLOW.getWoolData();
    }
}
