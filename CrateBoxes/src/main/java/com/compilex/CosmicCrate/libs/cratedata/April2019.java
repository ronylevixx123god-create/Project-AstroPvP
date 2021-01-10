/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicArmorSets.CosmicArmorSetsAPI
 *  com.compilex.CosmicArmorSets.enums.ArmorSet
 *  com.compilex.CosmicDungeons.DungeonsRewardsAPI
 *  com.compilex.CosmicDungeons.dungeons.libs.Dungeon
 *  com.compilex.CosmicDungeons.dungeons.libs.DungeonType
 *  com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils
 *  com.cosmicpvp.cosmicutils.itembuilder.CustomItemBuilder
 *  com.cosmicpvp.cosmicutils.itembuilder.CustomItemBuilder$CustomItemType
 *  com.cosmicpvp.cosmicutils.itembuilder.LootboxItemBuilder
 *  com.cosmicpvp.cosmicutils.itembuilder.MaskBuilder
 *  com.cosmicpvp.cosmicutils.itembuilder.PhoenixFeatherBuilder
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.google.common.collect.Lists
 *  org.arkhamnetwork.Arkkit.patches.Drinks
 *  org.arkhamnetwork.Arkkit.patches.LoreTag
 *  org.arkhamnetwork.enchantments.EnchantmentOrb
 *  org.arkhamnetwork.enchantments.libs.OrbType
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
import com.compilex.CosmicCrate.api.CCAPI;
import com.compilex.CosmicCrate.libs.CosmicCrateData;
import com.compilex.CosmicCrate.libs.Version;
import com.compilex.CosmicDungeons.DungeonsRewardsAPI;
import com.compilex.CosmicDungeons.dungeons.libs.Dungeon;
import com.compilex.CosmicDungeons.dungeons.libs.DungeonType;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.itembuilder.CustomItemBuilder;
import com.cosmicpvp.cosmicutils.itembuilder.LootboxItemBuilder;
import com.cosmicpvp.cosmicutils.itembuilder.MaskBuilder;
import com.cosmicpvp.cosmicutils.itembuilder.PhoenixFeatherBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import org.arkhamnetwork.Arkkit.patches.Drinks;
import org.arkhamnetwork.Arkkit.patches.LoreTag;
import org.arkhamnetwork.enchantments.EnchantmentOrb;
import org.arkhamnetwork.enchantments.libs.OrbType;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class April2019
extends CosmicCrateData {
    private ChatColor mainColor = ChatColor.DARK_PURPLE;
    private ChatColor secondColor = ChatColor.YELLOW;

    @Override
    public List<String> getDescriptionLore(String username) {
        String bulletpoint = ChatColor.BOLD.toString() + "* ";
        return Lists.newArrayList((Object[])new String[]{(Object)this.mainColor + "Unlocked by " + (Object)ChatColor.BOLD + username + (Object)this.mainColor + " at " + (Object)this.secondColor + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", (Object)ChatColor.WHITE + bulletpoint + (Object)ChatColor.WHITE + "Fishing Permit", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", CC.YellowB + bulletpoint + CC.Yellow + "32-64x Splash Space Drinks", CC.YellowB + bulletpoint + CC.Yellow + "3-5x Item Nametags " + CC.YellowB + CC.Underline + "OR" + CC.Yellow + " Lore Crystals", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", CC.Gold + bulletpoint + CC.Gold + "1-2x Heroic G-Kit Generator", CC.Gold + bulletpoint + CC.Gold + "2-4x Random Heroic Upgrade", CC.Gold + bulletpoint + CC.Gold + "1-2x Random Dungeon Lootbags", CC.Gold + bulletpoint + CC.Gold + "1x Holy White Scroll " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " Phoenix Feather", CC.Gold + bulletpoint + CC.Gold + "1x Lootbox: Lucky " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " Hangover", CC.Gold + bulletpoint + CC.Gold + "1x April 2016, 2017, " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " 2018 /cc", "", (Object)this.secondColor + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", (Object)this.secondColor + bulletpoint + (Object)this.secondColor + "1x Supreme Fanny Pack", (Object)this.secondColor + bulletpoint + (Object)this.secondColor + "1-2x Superior /cc", (Object)this.secondColor + bulletpoint + (Object)this.secondColor + "1-2x 90% Armor/Weapon Enchant Orb (14 slot)", (Object)this.secondColor + bulletpoint + (Object)this.secondColor + "1x Glitch " + CC.YellowB + CC.Underline + "OR" + (Object)this.secondColor + " Lovers Masks"});
    }

    @Override
    public ItemStack getRandomMetaItem(Player player) {
        double chance = Math.random();
        if (chance <= 0.05) {
            ItemStack orbs = EnchantmentOrb.getEnchantmentOrb((int)90, (int)14, (OrbType)(this.random.nextInt(10) <= 7 ? OrbType.ARMOR : OrbType.WEAPON));
            orbs.setAmount(this.getRandomOne());
            return orbs;
        }
        if (chance <= 0.7) {
            return this.setAmount(CCAPI.getGodlyCosmicCrate(), this.getRandomOne());
        }
        if (chance <= 0.95) {
            return Math.random() < 0.4 ? new MaskBuilder("LOVER").build() : new MaskBuilder("GLITCH").build();
        }
        return CosmicArmorSetsAPI.getWeaponFromArmorSet((ArmorSet)ArmorSet.SUPREME);
    }

    @Override
    public List<ItemStack> getCrateLoot(Player player) {
        ArrayList items = Lists.newArrayList();
        items.add(ItemUtils.buildItem((Material)Material.RAW_FISH, (short)0, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&f&lFishing Permit"), (String[])new String[]{CC.Gray + "In the future, raw fish,", CC.Gray + "are used to signify permission", CC.Gray + "to catch more raw fish.", CC.Gray + "... we're not exactly sure either."}));
        items.add(ItemUtils.setAmount((ItemStack)Drinks.getSplashDrink(), (int)(32 + this.random.nextInt(33))));
        items.add(ItemUtils.setAmount((ItemStack)(Math.random() <= 0.5 ? ItemUtils.getItemNametag() : LoreTag.getLoreTagItem()), (int)(3 + this.random.nextInt(3))));
        ItemStack generator = new CustomItemBuilder(CustomItemBuilder.CustomItemType.GKIT_GENERATOR).build();
        if (generator != null) {
            items.add(this.setAmount(generator, 1 + this.random.nextInt(2)));
        }
        items.add(this.setAmount(CosmicArmorSetsAPI.createRandomArmorSetUpgrade(), 2 + this.random.nextInt(3)));
        items.add(this.setAmount(DungeonsRewardsAPI.getDungeonLootBag((Player)player, (Dungeon)(this.random.nextDouble() <= 0.8 ? DungeonType.ABANDONED_SHIP.getDungeonInstance() : DungeonType.DESTROYED_OUTPOST.getDungeonInstance())), this.random.nextDouble() >= 0.7 ? this.getRandomOne() : 1));
        items.add(Math.random() < 0.2 ? new PhoenixFeatherBuilder().build() : DungeonsRewardsAPI.getHolyWhiteScroll());
        ItemStack lootbox = new LootboxItemBuilder(Math.random() < 0.4 ? "lootbox_lucky" : "lootbox_hangover").build();
        if (lootbox != null) {
            items.add(lootbox);
        }
        items.add(CCAPI.getCosmicCrate(this.random.nextDouble() <= 0.2 ? Version.APRIL_2018 : (this.random.nextDouble() < 0.4 ? Version.APRIL_2017 : Version.APRIL), player.getName()));
        return items;
    }

    private int getRandomOne() {
        return Math.random() <= 0.3 ? 2 : 1;
    }

    @Override
    public short getNonColorPane() {
        return DyeColor.PURPLE.getWoolData();
    }

    @Override
    public List<Color> getConclusionFireworkColors() {
        return Lists.newArrayList((Object[])new Color[]{Color.AQUA, Color.YELLOW});
    }

    @Override
    public short getColorPane() {
        return DyeColor.YELLOW.getWoolData();
    }
}

