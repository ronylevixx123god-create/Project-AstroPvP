/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicArmorSets.CosmicArmorSetsAPI
 *  com.compilex.CosmicDungeons.DungeonsAPI
 *  com.compilex.CosmicDungeons.DungeonsRewardsAPI
 *  com.compilex.CosmicDungeons.dungeons.libs.Dungeon
 *  com.compilex.CosmicDungeons.dungeons.libs.DungeonType
 *  com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils
 *  com.cosmicpvp.cosmicutils.itembuilder.CustomItemBuilder
 *  com.cosmicpvp.cosmicutils.itembuilder.CustomItemBuilder$CustomItemType
 *  com.cosmicpvp.cosmicutils.itembuilder.MaskBuilder
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.google.common.collect.Lists
 *  me.cosmicpvp.CosmicGlobalKits.CosmicGlobalKitsAPI
 *  me.cosmicpvp.CosmicGlobalKits.commands.CommandFallenHero
 *  me.cosmicpvp.CosmicGlobalKits.libs.MasteryKits
 *  org.arkhamnetwork.Arkkit.patches.EquipmentLootboxes
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
import com.compilex.CosmicCrate.api.CCAPI;
import com.compilex.CosmicCrate.libs.CosmicCrateData;
import com.compilex.CosmicCrate.libs.Version;
import com.compilex.CosmicDungeons.DungeonsAPI;
import com.compilex.CosmicDungeons.DungeonsRewardsAPI;
import com.compilex.CosmicDungeons.dungeons.libs.Dungeon;
import com.compilex.CosmicDungeons.dungeons.libs.DungeonType;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.itembuilder.CustomItemBuilder;
import com.cosmicpvp.cosmicutils.itembuilder.MaskBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import me.cosmicpvp.CosmicGlobalKits.CosmicGlobalKitsAPI;
import me.cosmicpvp.CosmicGlobalKits.commands.CommandFallenHero;
import me.cosmicpvp.CosmicGlobalKits.libs.MasteryKits;
import org.arkhamnetwork.Arkkit.patches.EquipmentLootboxes;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class November2018
extends CosmicCrateData {
    private ChatColor mainColor = ChatColor.LIGHT_PURPLE;
    private ChatColor secondColor = ChatColor.AQUA;

    @Override
    public List<String> getDescriptionLore(String username) {
        String bulletpoint = ChatColor.BOLD.toString() + "* ";
        return Lists.newArrayList((Object[])new String[]{(Object)this.mainColor + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)this.mainColor + " at " + (Object)this.secondColor + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Spooky Season", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", (Object)ChatColor.YELLOW + bulletpoint + (Object)ChatColor.YELLOW + "Limited Edition Titles", CC.YellowB + bulletpoint + (Object)ChatColor.YELLOW + "Scarecrow Mask", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", CC.Gold + bulletpoint + CC.Gold + "2-5x Heroic G-Kit Generator", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1-3x Equipment Lootbox", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "1x Heroic " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " Superior Cosmic Crate", CC.Gold + bulletpoint + CC.Gold + "1x Random Heroic Upgrade", CC.Gold + bulletpoint + CC.Gold + "1x Random Dungeon Portal", (Object)ChatColor.GOLD + bulletpoint + (Object)ChatColor.GOLD + "November 2016 " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " 2017 /cc", "", (Object)this.secondColor + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)this.secondColor + bulletpoint + (Object)this.secondColor + "Ghost Fallen Hero Bundle", (Object)this.secondColor + bulletpoint + (Object)this.secondColor + "Ghost Mastery Kit", (Object)this.secondColor + bulletpoint + (Object)this.secondColor + "Destroyed Dungeon Outpost Lootbag", (Object)this.secondColor + bulletpoint + (Object)this.secondColor + "Halloween 2018 /cc"});
    }

    @Override
    public ItemStack getRandomMetaItem(Player player) {
        double chance = Math.random();
        if (chance <= 0.35) {
            return CosmicGlobalKitsAPI.createMasteryKitItem((MasteryKits)MasteryKits.GHOST, (boolean)true);
        }
        if (chance <= 0.7) {
            return CommandFallenHero.getGhostMasteryKitBundle();
        }
        if (chance <= 0.85) {
            return DungeonsRewardsAPI.getDungeonLootBag((Player)player, (Dungeon)DungeonType.DESTROYED_OUTPOST.getDungeonInstance());
        }
        return CCAPI.getCosmicCrate(Version.HALLOWEEN_2018, "November 2018 CC");
    }

    @Override
    public List<ItemStack> getCrateLoot(Player player) {
        ItemStack mask;
        ArrayList items = Lists.newArrayList();
        items.add(ItemUtils.buildItem((Material)Material.PUMPKIN, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&6&lSpooky Season"), (String[])new String[]{CC.Gray + "The spookiest season of all spooky seasons.", CC.Gray + "Super, duper, spooky."}));
        int x = this.random.nextInt(4);
        if (x == 0) {
            items.add(ItemUtils.getTitleVoucher((String)"IHaveNoSoul"));
        }
        if (x == 1) {
            items.add(ItemUtils.getTitleVoucher((String)"Scarecrow"));
        }
        if (x == 2) {
            items.add(ItemUtils.getTitleVoucher((String)"ItsGettingCold"));
        }
        if (x == 3) {
            if (Math.random() <= 0.3) {
                items.add(ItemUtils.getTitleVoucher((String)"#Schooled"));
            } else {
                items.add(ItemUtils.getTitleVoucher((String)"PumpkinSpiceNotNice"));
            }
        }
        if ((mask = new MaskBuilder("SCARECROW").build()) != null) {
            items.add(mask);
        }
        items.add(new CustomItemBuilder(CustomItemBuilder.CustomItemType.GKIT_GENERATOR, 2 + (this.random.nextBoolean() ? this.getRandomThree() : 0)).build());
        items.add(this.setAmount(((EquipmentLootboxes)ArkPlugins.EQUIPMENT_LOOTBOX.getArkPlugin()).createLootboxItem(false), this.getRandomThree()));
        items.add(this.random.nextDouble() <= 0.75 ? CCAPI.getHeroicMysteryCrate() : CCAPI.getGodlyCosmicCrate());
        items.add(CosmicArmorSetsAPI.createRandomArmorSetUpgrade());
        items.add(DungeonsAPI.get().getDungeon(this.random.nextBoolean() ? 1 : 0).getDungeonPortal());
        items.add(CCAPI.getCosmicCrate(Math.random() <= 0.65 ? Version.NOVEMBER : Version.NOVEMBER_2017, player.getName()));
        return items;
    }

    private int getRandomOne() {
        return Math.random() <= 0.3 ? 2 : 1;
    }

    private int getRandomThree() {
        return Math.random() <= 0.1 ? 3 : (Math.random() <= 0.3 ? 2 : 1);
    }

    @Override
    public short getNonColorPane() {
        return DyeColor.PURPLE.getWoolData();
    }

    @Override
    public List<Color> getConclusionFireworkColors() {
        return Lists.newArrayList((Object[])new Color[]{Color.AQUA, Color.PURPLE});
    }

    @Override
    public short getColorPane() {
        return DyeColor.LIGHT_BLUE.getWoolData();
    }
}

