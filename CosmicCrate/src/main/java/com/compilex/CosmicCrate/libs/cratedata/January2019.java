// 
// Decompiled by Procyon v0.5.36
// 

package com.compilex.CosmicCrate.libs.cratedata;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import com.cosmicpvp.cosmicutils.itembuilder.MysteryEnchantBookBuilder;
import org.arkhamnetwork.enchantments.EnchantmentOrb;
import org.arkhamnetwork.enchantments.libs.OrbType;
import com.compilex.CosmicCrate.api.CCAPI;
import com.compilex.CosmicCrate.libs.Version;
import com.cosmicpvp.cosmicutils.itembuilder.RandomizationScrollBuilder;
import com.cosmicpvp.cosmicutils.struct.ItemTier;
import com.cosmicpvp.cosmicutils.itembuilder.CustomItemBuilder;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import org.bukkit.Material;
import me.cosmicpvp.CosmicGlobalKits.commands.CommandFallenHero;
import com.cosmicpvp.CosmicRankQuests.HeroicRankCrystalAPI;
import com.compilex.CosmicDungeons.dungeons.libs.DungeonType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import com.google.common.collect.Lists;
import com.cosmicpvp.cosmicutils.utils.CC;
import java.util.List;
import org.bukkit.ChatColor;
import com.compilex.CosmicCrate.libs.CosmicCrateData;

public class January2019 extends CosmicCrateData
{
    private ChatColor mainColor;
    private ChatColor secondColor;
    
    public January2019() {
        this.mainColor = ChatColor.DARK_AQUA;
        this.secondColor = ChatColor.YELLOW;
    }
    
    @Override
    public List<String> getDescriptionLore(final String username) {
        final String bulletpoint = ChatColor.BOLD.toString() + "* ";
        return (List<String>)Lists.newArrayList((Object[])new String[] { this.mainColor + "Unlocked by " + ChatColor.BOLD + username + this.mainColor + " at " + this.secondColor + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "2019 Meme Preview", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", ChatColor.YELLOW + bulletpoint + ChatColor.YELLOW + "Limited Edition Titles", CC.YellowB + bulletpoint + ChatColor.YELLOW + "Random Mask Generator", CC.YellowB + bulletpoint + ChatColor.YELLOW + "2-4x Godly Randomization Scrolls", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", CC.Gold + bulletpoint + CC.Gold + "January 2017 " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " 2018 /cc", ChatColor.GOLD + bulletpoint + ChatColor.GOLD + "1-2x Heroic " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " Normal Equipment Lootboxes", CC.Gold + bulletpoint + CC.Gold + "1-2x Heroic " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " Soul Enchantment Books", CC.Gold + bulletpoint + CC.Gold + "1-2x Heroic G-Kit " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " M-Kit Generator", CC.Gold + bulletpoint + CC.Gold + "1x 100% Armor/Weapon Enchant Orb (13-14 Slot)", "", this.secondColor + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", this.secondColor + bulletpoint + this.secondColor + "Heroic Rank Crystal", this.secondColor + bulletpoint + this.secondColor + "1-2x Random Dungeon Portal", this.secondColor + bulletpoint + this.secondColor + "Random Mastery Fallen Hero Bundle" });
    }
    
    @Override
    public ItemStack getRandomMetaItem(final Player player) {
        final double chance = Math.random();
        if (chance <= 0.6) {
            return this.setAmount(((this.random.nextDouble() <= 0.6) ? DungeonType.ABANDONED_SHIP : DungeonType.DESTROYED_OUTPOST).getDungeonInstance().getDungeonPortal(), this.getRandomOne());
        }
        if (chance <= 0.9) {
            return HeroicRankCrystalAPI.getHeroicRankCrystalItem();
        }
        final int chances = this.random.nextInt(5);
        return (chances <= 2) ? CommandFallenHero.getGhostMasteryKitBundle() : ((chances == 3) ? CommandFallenHero.getNecromancerKitBundle() : CommandFallenHero.getDeathKnightBundle());
    }
    
    @Override
    public List<ItemStack> getCrateLoot(final Player player) {
        final List<ItemStack> items = (List<ItemStack>)Lists.newArrayList();
        items.add(ItemUtils.buildItem(Material.BOOK_AND_QUILL, (short)0, ChatColor.translateAlternateColorCodes('&', "&f&l2019 Meme Preview"), new String[] { CC.Gray + "Submit your dankest memes to the", CC.Gray + "intergalactic board of toxicity." }));
        final int x = this.random.nextInt(4);
        if (x == 0) {
            items.add(ItemUtils.getTitleVoucher("#NewYearNewMe"));
        }
        if (x == 1) {
            items.add(ItemUtils.getTitleVoucher("NewYears"));
        }
        if (x == 2) {
            items.add(ItemUtils.getTitleVoucher("#2k19"));
        }
        if (x == 3) {
            if (Math.random() <= 0.3) {
                items.add(ItemUtils.getTitleVoucher("#2019"));
            }
            else {
                items.add(ItemUtils.getTitleVoucher("Cya2k18"));
            }
        }
        final ItemStack mask = new CustomItemBuilder(CustomItemBuilder.CustomItemType.RANDOM_MASK).build();
        if (mask != null) {
            items.add(mask);
        }
        items.add(new RandomizationScrollBuilder(ItemTier.GOD, 2, 4).build());
        items.add(CCAPI.getCosmicCrate((this.random.nextDouble() <= 0.4) ? Version.JANUARY_2018 : Version.JANUARY_2017, player.getName()));
        final ItemStack generator = new CustomItemBuilder((this.random.nextDouble() <= 0.15) ? CustomItemBuilder.CustomItemType.MKIT_GENERATOR : CustomItemBuilder.CustomItemType.GKIT_GENERATOR).build();
        if (generator != null) {
            items.add(this.setAmount(generator, this.getRandomOne()));
        }
        final ItemStack item = new CustomItemBuilder((this.random.nextDouble() < 0.6) ? CustomItemBuilder.CustomItemType.EQUIPMENT_LOOTBOX : CustomItemBuilder.CustomItemType.HEROIC_EQUIPMENT_LOOTBOX, this.getRandomThree()).build();
        if (item != null) {
            items.add(this.setAmount(item, this.getRandomOne()));
        }
        items.add(EnchantmentOrb.getEnchantmentOrb(100, this.random.nextInt(2) + 13, (this.random.nextInt(10) <= 6) ? OrbType.ARMOR : OrbType.WEAPON));
        final ItemStack book = new MysteryEnchantBookBuilder((this.random.nextDouble() <= 0.6) ? ItemTier.GOD : ItemTier.HEROIC, 1, 2).build();
        if (book != null) {
            items.add(this.setAmount(book, this.getRandomOne()));
        }
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
        return DyeColor.YELLOW.getWoolData();
    }
    
    @Override
    public List<Color> getConclusionFireworkColors() {
        return (List<Color>)Lists.newArrayList((Object[])new Color[] { Color.AQUA, Color.YELLOW });
    }
    
    @Override
    public short getColorPane() {
        return DyeColor.CYAN.getWoolData();
    }
}
