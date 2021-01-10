/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicArmorSets.CosmicArmorSetsAPI
 *  com.compilex.CosmicArmorSets.enums.ArmorSet
 *  com.compilex.CosmicArmorSets.enums.HeroicArmorType
 *  com.compilex.CosmicDungeons.DungeonsAPI
 *  com.cosmicpvp.CosmicRankQuests.CosmicRankQuestsAPI
 *  com.cosmicpvp.CosmicRankQuests.HeroicRankCrystalAPI
 *  com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.CustomItemUtils
 *  com.google.common.collect.Lists
 *  me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKits
 *  org.arkhamnetwork.Arkkit.patches.EquipmentLootboxes
 *  org.arkhamnetwork.Arkkit.patches.EssentialsAddHomes
 *  org.arkhamnetwork.Arkkit.patches.LoreTag
 *  org.arkhamnetwork.Arkkit.plugins.ArkPlugins
 *  org.bukkit.ChatColor
 *  org.bukkit.Color
 *  org.bukkit.DyeColor
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package com.compilex.CosmicCrate.libs.cratedata;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.compilex.CosmicArmorSets.enums.HeroicArmorType;
import com.compilex.CosmicCrate.api.CCAPI;
import com.compilex.CosmicCrate.libs.CosmicCrateData;
import com.compilex.CosmicCrate.libs.Version;
import com.compilex.CosmicDungeons.DungeonsAPI;
import com.cosmicpvp.CosmicRankQuests.CosmicRankQuestsAPI;
import com.cosmicpvp.CosmicRankQuests.HeroicRankCrystalAPI;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.CustomItemUtils;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKits;
import org.arkhamnetwork.Arkkit.patches.EquipmentLootboxes;
import org.arkhamnetwork.Arkkit.patches.EssentialsAddHomes;
import org.arkhamnetwork.Arkkit.patches.LoreTag;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class August2018
extends CosmicCrateData {
    @Override
    public List<String> getDescriptionLore(String username) {
        String bulletpoint = ChatColor.BOLD.toString() + "* ";
        return Lists.newArrayList((Object[])new String[]{(Object)ChatColor.RED + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)ChatColor.RED + " at " + (Object)ChatColor.GOLD + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Ice Cold Lemonade", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Limited Edition Titles", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "2-3x Lore Crystals " + CC.Underline + "OR" + (Object)ChatColor.YELLOW + " Item Nametags", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "4-5x Max Home Increase", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", CC.Gold + bulletpoint + CC.Gold + "Random Heroic Armor Piece", CC.Gold + bulletpoint + CC.Gold + "2x Equipment Lootbox", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "Top Tier Rank Quest", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1x August 2016 " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " 2017 CC", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1-2x /vkit Evolution Gems", "", ChatColor.RED.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)ChatColor.RED + bulletpoint + (Object)ChatColor.RED + "Dimensional Traveler Set Piece", (Object)ChatColor.RED + bulletpoint + (Object)ChatColor.RED + "Destroyed Outpost Dungeon Portal", (Object)ChatColor.RED + bulletpoint + (Object)ChatColor.RED + "Mimic /vkit Crystal", (Object)ChatColor.RED + bulletpoint + (Object)ChatColor.RED + "Heroic Rank Crystal"});
    }

    @Override
    public ItemStack getRandomMetaItem(Player player) {
        double chance = Math.random();
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
    public List<ItemStack> getCrateLoot(Player player) {
        ArrayList items = Lists.newArrayList();
        items.add(ItemUtils.buildItem((Material)Material.SPECKLED_MELON, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&e&lIce &b&lCold &e&lLemonade"), (String[])new String[]{(Object)ChatColor.GRAY + "An icey beverage perfect", (Object)ChatColor.GRAY + "for a hot August 2018 day!"}));
        items.add(ItemUtils.setAmount((ItemStack)EssentialsAddHomes.getAddHomeItem(), (int)(4 + this.random.nextInt(2))));
        items.add(ItemUtils.setAmount((ItemStack)(Math.random() <= 0.5 ? ItemUtils.getItemNametag() : LoreTag.getLoreTagItem()), (int)(1 + this.random.nextInt(2))));
        int x = this.random.nextInt(4);
        if (x == 0) {
            items.add(ItemUtils.getTitleVoucher((String)"SummerBoi"));
        }
        if (x == 1) {
            items.add(ItemUtils.getTitleVoucher((String)"NoSchoolNoRules"));
        }
        if (x == 2) {
            items.add(ItemUtils.getTitleVoucher((String)"Trendy"));
        }
        if (x == 3) {
            if (Math.random() < 0.1) {
                items.add(ItemUtils.getTitleVoucher((String)"#Hashtag"));
            } else {
                items.add(ItemUtils.getTitleVoucher((String)"bored"));
            }
        }
        items.add(CosmicArmorSetsAPI.createHeroicPeice((HeroicArmorType)HeroicArmorType.values()[this.random.nextInt(HeroicArmorType.values().length)]));
        items.add(CosmicRankQuestsAPI.getRankQuestItem((int)5));
        items.add(CCAPI.getCosmicCrate(Math.random() <= 0.65 ? Version.AUGUST : Version.AUGUST_2017, player.getName()));
        items.add(ItemUtils.setAmount((ItemStack)((EquipmentLootboxes)ArkPlugins.EQUIPMENT_LOOTBOX.getArkPlugin()).createLootboxItem(false), (int)2));
        items.add(ItemUtils.setAmount((ItemStack)CustomItemUtils.createRandomEvolutionKitGem(), (int)this.getRandomOne()));
        return items;
    }

    private int getRandomOne() {
        return Math.random() <= 0.3 ? 2 : 1;
    }

    @Override
    public short getNonColorPane() {
        return DyeColor.RED.getWoolData();
    }

    @Override
    public List<Color> getConclusionFireworkColors() {
        return Lists.newArrayList((Object[])new Color[]{Color.ORANGE, Color.RED});
    }

    @Override
    public short getColorPane() {
        return DyeColor.ORANGE.getWoolData();
    }
}

