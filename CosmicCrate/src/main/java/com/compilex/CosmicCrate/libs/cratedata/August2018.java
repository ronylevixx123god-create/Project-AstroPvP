// 
// Decompiled by Procyon v0.5.36
// 

package com.compilex.CosmicCrate.libs.cratedata;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import com.cosmicpvp.cosmicutils.utils.CustomItemUtils;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.arkhamnetwork.Arkkit.patches.EquipmentLootboxes;
import com.compilex.CosmicCrate.api.CCAPI;
import com.compilex.CosmicCrate.libs.Version;
import com.cosmicpvp.CosmicRankQuests.CosmicRankQuestsAPI;
import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.HeroicArmorType;
import org.arkhamnetwork.Arkkit.patches.LoreTag;
import org.arkhamnetwork.Arkkit.patches.EssentialsAddHomes;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import org.bukkit.Material;
import com.cosmicpvp.CosmicRankQuests.HeroicRankCrystalAPI;
import me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKits;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.compilex.CosmicDungeons.DungeonsAPI;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import com.google.common.collect.Lists;
import com.cosmicpvp.cosmicutils.utils.CC;
import org.bukkit.ChatColor;
import java.util.List;
import com.compilex.CosmicCrate.libs.CosmicCrateData;

public class August2018 extends CosmicCrateData
{
    @Override
    public List<String> getDescriptionLore(final String username) {
        final String bulletpoint = ChatColor.BOLD.toString() + "* ";
        return (List<String>)Lists.newArrayList((Object[])new String[] { ChatColor.RED + "Unlocked by " + ChatColor.BOLD + username + ChatColor.RED + " at " + ChatColor.GOLD + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Ice Cold Lemonade", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Limited Edition Titles", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "2-3x Lore Crystals " + CC.Underline + "OR" + ChatColor.YELLOW + " Item Nametags", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "4-5x Max Home Increase", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", CC.Gold + bulletpoint + CC.Gold + "Random Heroic Armor Piece", CC.Gold + bulletpoint + CC.Gold + "2x Equipment Lootbox", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "Top Tier Rank Quest", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1x August 2016 " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " 2017 CC", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1-2x /vkit Evolution Gems", "", ChatColor.RED.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", ChatColor.RED + bulletpoint + ChatColor.RED + "Dimensional Traveler Set Piece", ChatColor.RED + bulletpoint + ChatColor.RED + "Destroyed Outpost Dungeon Portal", ChatColor.RED + bulletpoint + ChatColor.RED + "Mimic /vkit Crystal", ChatColor.RED + bulletpoint + ChatColor.RED + "Heroic Rank Crystal" });
    }
    
    @Override
    public ItemStack getRandomMetaItem(final Player player) {
        final double chance = Math.random();
        if (chance <= 0.6) {
            return DungeonsAPI.get().getDungeon(1).getDungeonPortal();
        }
        if (chance <= 0.9) {
            return ArmorSet.DIMENSIONAL_TRAVELER.getRandomPeice(true);
        }
        if (chance <= 0.95) {
            return EvolutionKits.MIMIC.getClaimableItem();
        }
        return HeroicRankCrystalAPI.getHeroicRankCrystalItem();
    }
    
    @Override
    public List<ItemStack> getCrateLoot(final Player player) {
        final List<ItemStack> items = (List<ItemStack>)Lists.newArrayList();
        items.add(ItemUtils.buildItem(Material.SPECKLED_MELON, (short)0, ChatColor.translateAlternateColorCodes('&', "&e&lIce &b&lCold &e&lLemonade"), new String[] { ChatColor.GRAY + "An icey beverage perfect", ChatColor.GRAY + "for a hot August 2018 day!" }));
        items.add(ItemUtils.setAmount(EssentialsAddHomes.getAddHomeItem(), 4 + this.random.nextInt(2)));
        items.add(ItemUtils.setAmount((Math.random() <= 0.5) ? ItemUtils.getItemNametag() : LoreTag.getLoreTagItem(), 1 + this.random.nextInt(2)));
        final int x = this.random.nextInt(4);
        if (x == 0) {
            items.add(ItemUtils.getTitleVoucher("SummerBoi"));
        }
        if (x == 1) {
            items.add(ItemUtils.getTitleVoucher("NoSchoolNoRules"));
        }
        if (x == 2) {
            items.add(ItemUtils.getTitleVoucher("Trendy"));
        }
        if (x == 3) {
            if (Math.random() < 0.1) {
                items.add(ItemUtils.getTitleVoucher("#Hashtag"));
            }
            else {
                items.add(ItemUtils.getTitleVoucher("bored"));
            }
        }
        items.add(CosmicArmorSetsAPI.createHeroicPeice(HeroicArmorType.values()[this.random.nextInt(HeroicArmorType.values().length)]));
        items.add(CosmicRankQuestsAPI.getRankQuestItem(5));
        items.add(CCAPI.getCosmicCrate((Math.random() <= 0.65) ? Version.AUGUST : Version.AUGUST_2017, player.getName()));
        items.add(ItemUtils.setAmount(((EquipmentLootboxes)ArkPlugins.EQUIPMENT_LOOTBOX.getArkPlugin()).createLootboxItem(false), 2));
        items.add(ItemUtils.setAmount(CustomItemUtils.createRandomEvolutionKitGem(), this.getRandomOne()));
        return items;
    }
    
    private int getRandomOne() {
        return (Math.random() <= 0.3) ? 2 : 1;
    }
    
    @Override
    public short getNonColorPane() {
        return DyeColor.RED.getWoolData();
    }
    
    @Override
    public List<Color> getConclusionFireworkColors() {
        return (List<Color>)Lists.newArrayList((Object[])new Color[] { Color.ORANGE, Color.RED });
    }
    
    @Override
    public short getColorPane() {
        return DyeColor.ORANGE.getWoolData();
    }
}
