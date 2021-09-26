// 
// Decompiled by Procyon v0.5.36
// 

package com.compilex.CosmicCrate.libs.cratedata;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import com.compilex.CosmicDungeons.DungeonsAPI;
import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.arkhamnetwork.Arkkit.patches.EquipmentLootboxes;
import com.cosmicpvp.cosmicutils.itembuilder.CustomItemBuilder;
import com.cosmicpvp.cosmicutils.itembuilder.MaskBuilder;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import org.bukkit.Material;
import com.compilex.CosmicCrate.api.CCAPI;
import com.compilex.CosmicCrate.libs.Version;
import com.compilex.CosmicDungeons.DungeonsRewardsAPI;
import com.compilex.CosmicDungeons.dungeons.libs.DungeonType;
import me.cosmicpvp.CosmicGlobalKits.commands.CommandFallenHero;
import me.cosmicpvp.CosmicGlobalKits.CosmicGlobalKitsAPI;
import me.cosmicpvp.CosmicGlobalKits.libs.MasteryKits;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import com.google.common.collect.Lists;
import com.cosmicpvp.cosmicutils.utils.CC;
import java.util.List;
import org.bukkit.ChatColor;
import com.compilex.CosmicCrate.libs.CosmicCrateData;

public class November2018 extends CosmicCrateData
{
    private ChatColor mainColor;
    private ChatColor secondColor;
    
    public November2018() {
        this.mainColor = ChatColor.LIGHT_PURPLE;
        this.secondColor = ChatColor.AQUA;
    }
    
    @Override
    public List<String> getDescriptionLore(final String username) {
        final String bulletpoint = ChatColor.BOLD.toString() + "* ";
        return (List<String>)Lists.newArrayList((Object[])new String[] { this.mainColor + "Unlocked by " + ChatColor.BOLD + username + this.mainColor + " at " + this.secondColor + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Spooky Season", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Limited Edition Titles", CC.YellowB + bulletpoint + ChatColor.YELLOW + "Scarecrow Mask", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", CC.Gold + bulletpoint + CC.Gold + "2-5x Heroic G-Kit Generator", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1-3x Equipment Lootbox", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1x Heroic " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " Superior Cosmic Crate", CC.Gold + bulletpoint + CC.Gold + "1x Random Heroic Upgrade", CC.Gold + bulletpoint + CC.Gold + "1x Random Dungeon Portal", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "November 2016 " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " 2017 /cc", "", this.secondColor + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", this.secondColor + bulletpoint + this.secondColor + "Ghost Fallen Hero Bundle", this.secondColor + bulletpoint + this.secondColor + "Ghost Mastery Kit", this.secondColor + bulletpoint + this.secondColor + "Destroyed Dungeon Outpost Lootbag", this.secondColor + bulletpoint + this.secondColor + "Halloween 2018 /cc" });
    }
    
    @Override
    public ItemStack getRandomMetaItem(final Player player) {
        final double chance = Math.random();
        if (chance <= 0.35) {
            return CosmicGlobalKitsAPI.createMasteryKitItem(MasteryKits.GHOST, true);
        }
        if (chance <= 0.7) {
            return CommandFallenHero.getGhostMasteryKitBundle();
        }
        if (chance <= 0.85) {
            return DungeonsRewardsAPI.getDungeonLootBag(player, DungeonType.DESTROYED_OUTPOST.getDungeonInstance());
        }
        return CCAPI.getCosmicCrate(Version.HALLOWEEN_2018, "November 2018 CC");
    }
    
    @Override
    public List<ItemStack> getCrateLoot(final Player player) {
        final List<ItemStack> items = (List<ItemStack>)Lists.newArrayList();
        items.add(ItemUtils.buildItem(Material.PUMPKIN, (short)0, ChatColor.translateAlternateColorCodes('&', "&6&lSpooky Season"), new String[] { CC.Gray + "The spookiest season of all spooky seasons.", CC.Gray + "Super, duper, spooky." }));
        final int x = this.random.nextInt(4);
        if (x == 0) {
            items.add(ItemUtils.getTitleVoucher("IHaveNoSoul"));
        }
        if (x == 1) {
            items.add(ItemUtils.getTitleVoucher("Scarecrow"));
        }
        if (x == 2) {
            items.add(ItemUtils.getTitleVoucher("ItsGettingCold"));
        }
        if (x == 3) {
            if (Math.random() <= 0.3) {
                items.add(ItemUtils.getTitleVoucher("#Schooled"));
            }
            else {
                items.add(ItemUtils.getTitleVoucher("PumpkinSpiceNotNice"));
            }
        }
        final ItemStack mask = new MaskBuilder("SCARECROW").build();
        if (mask != null) {
            items.add(mask);
        }
        items.add(new CustomItemBuilder(CustomItemBuilder.CustomItemType.GKIT_GENERATOR, 2 + (this.random.nextBoolean() ? this.getRandomThree() : 0)).build());
        items.add(this.setAmount(((EquipmentLootboxes)ArkPlugins.EQUIPMENT_LOOTBOX.getArkPlugin()).createLootboxItem(false), this.getRandomThree()));
        items.add((this.random.nextDouble() <= 0.75) ? CCAPI.getHeroicMysteryCrate() : CCAPI.getGodlyCosmicCrate());
        items.add(CosmicArmorSetsAPI.createRandomArmorSetUpgrade());
        items.add(DungeonsAPI.get().getDungeon((int)(this.random.nextBoolean() ? 1 : 0)).getDungeonPortal());
        items.add(CCAPI.getCosmicCrate((Math.random() <= 0.65) ? Version.NOVEMBER : Version.NOVEMBER_2017, player.getName()));
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
        return DyeColor.PURPLE.getWoolData();
    }
    
    @Override
    public List<Color> getConclusionFireworkColors() {
        return (List<Color>)Lists.newArrayList((Object[])new Color[] { Color.AQUA, Color.PURPLE });
    }
    
    @Override
    public short getColorPane() {
        return DyeColor.LIGHT_BLUE.getWoolData();
    }
}
