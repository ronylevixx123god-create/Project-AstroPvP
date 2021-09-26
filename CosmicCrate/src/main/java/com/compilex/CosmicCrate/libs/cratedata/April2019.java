// 
// Decompiled by Procyon v0.5.36
// 

package com.compilex.CosmicCrate.libs.cratedata;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import com.compilex.CosmicCrate.libs.Version;
import com.cosmicpvp.cosmicutils.itembuilder.LootboxItemBuilder;
import com.cosmicpvp.cosmicutils.itembuilder.PhoenixFeatherBuilder;
import com.compilex.CosmicDungeons.DungeonsRewardsAPI;
import com.compilex.CosmicDungeons.dungeons.libs.DungeonType;
import com.cosmicpvp.cosmicutils.itembuilder.CustomItemBuilder;
import org.arkhamnetwork.Arkkit.patches.LoreTag;
import org.arkhamnetwork.Arkkit.patches.Drinks;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import org.bukkit.Material;
import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.cosmicpvp.cosmicutils.itembuilder.MaskBuilder;
import com.compilex.CosmicCrate.api.CCAPI;
import org.arkhamnetwork.enchantments.EnchantmentOrb;
import org.arkhamnetwork.enchantments.libs.OrbType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import com.google.common.collect.Lists;
import com.cosmicpvp.cosmicutils.utils.CC;
import java.util.List;
import org.bukkit.ChatColor;
import com.compilex.CosmicCrate.libs.CosmicCrateData;

public class April2019 extends CosmicCrateData
{
    private ChatColor mainColor;
    private ChatColor secondColor;
    
    public April2019() {
        this.mainColor = ChatColor.DARK_PURPLE;
        this.secondColor = ChatColor.YELLOW;
    }
    
    @Override
    public List<String> getDescriptionLore(final String username) {
        final String bulletpoint = ChatColor.BOLD.toString() + "* ";
        return (List<String>)Lists.newArrayList((Object[])new String[] { this.mainColor + "Unlocked by " + ChatColor.BOLD + username + this.mainColor + " at " + this.secondColor + ChatColor.UNDERLINE + "buy.cosmicpvp.com", "", ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ADMIN ITEMS", ChatColor.WHITE + bulletpoint + ChatColor.WHITE + "Fishing Permit", "", ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "COSMETIC ITEMS", CC.YellowB + bulletpoint + CC.Yellow + "32-64x Splash Space Drinks", CC.YellowB + bulletpoint + CC.Yellow + "3-5x Item Nametags " + CC.YellowB + CC.Underline + "OR" + CC.Yellow + " Lore Crystals", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "TREASURE ITEMS", CC.Gold + bulletpoint + CC.Gold + "1-2x Heroic G-Kit Generator", CC.Gold + bulletpoint + CC.Gold + "2-4x Random Heroic Upgrade", CC.Gold + bulletpoint + CC.Gold + "1-2x Random Dungeon Lootbags", CC.Gold + bulletpoint + CC.Gold + "1x Holy White Scroll " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " Phoenix Feather", CC.Gold + bulletpoint + CC.Gold + "1x Lootbox: Lucky " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " Hangover", CC.Gold + bulletpoint + CC.Gold + "1x April 2016, 2017, " + CC.GoldB + CC.Underline + "OR" + CC.Gold + " 2018 /cc", "", this.secondColor + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "BONUS ITEMS", this.secondColor + bulletpoint + this.secondColor + "1x Supreme Fanny Pack", this.secondColor + bulletpoint + this.secondColor + "1-2x Superior /cc", this.secondColor + bulletpoint + this.secondColor + "1-2x 90% Armor/Weapon Enchant Orb (14 slot)", this.secondColor + bulletpoint + this.secondColor + "1x Glitch " + CC.YellowB + CC.Underline + "OR" + this.secondColor + " Lovers Masks" });
    }
    
    @Override
    public ItemStack getRandomMetaItem(final Player player) {
        final double chance = Math.random();
        if (chance <= 0.05) {
            final ItemStack orbs = EnchantmentOrb.getEnchantmentOrb(90, 14, (this.random.nextInt(10) <= 7) ? OrbType.ARMOR : OrbType.WEAPON);
            orbs.setAmount(this.getRandomOne());
            return orbs;
        }
        if (chance <= 0.7) {
            return this.setAmount(CCAPI.getGodlyCosmicCrate(), this.getRandomOne());
        }
        if (chance <= 0.95) {
            return (Math.random() < 0.4) ? new MaskBuilder("LOVER").build() : new MaskBuilder("GLITCH").build();
        }
        return CosmicArmorSetsAPI.getWeaponFromArmorSet(ArmorSet.SUPREME);
    }
    
    @Override
    public List<ItemStack> getCrateLoot(final Player player) {
        final List<ItemStack> items = (List<ItemStack>)Lists.newArrayList();
        items.add(ItemUtils.buildItem(Material.RAW_FISH, (short)0, ChatColor.translateAlternateColorCodes('&', "&f&lFishing Permit"), new String[] { CC.Gray + "In the future, raw fish,", CC.Gray + "are used to signify permission", CC.Gray + "to catch more raw fish.", CC.Gray + "... we're not exactly sure either." }));
        items.add(ItemUtils.setAmount(Drinks.getSplashDrink(), 32 + this.random.nextInt(33)));
        items.add(ItemUtils.setAmount((Math.random() <= 0.5) ? ItemUtils.getItemNametag() : LoreTag.getLoreTagItem(), 3 + this.random.nextInt(3)));
        final ItemStack generator = new CustomItemBuilder(CustomItemBuilder.CustomItemType.GKIT_GENERATOR).build();
        if (generator != null) {
            items.add(this.setAmount(generator, 1 + this.random.nextInt(2)));
        }
        items.add(this.setAmount(CosmicArmorSetsAPI.createRandomArmorSetUpgrade(), 2 + this.random.nextInt(3)));
        items.add(this.setAmount(DungeonsRewardsAPI.getDungeonLootBag(player, (this.random.nextDouble() <= 0.8) ? DungeonType.ABANDONED_SHIP.getDungeonInstance() : DungeonType.DESTROYED_OUTPOST.getDungeonInstance()), (this.random.nextDouble() >= 0.7) ? this.getRandomOne() : 1));
        items.add((Math.random() < 0.2) ? new PhoenixFeatherBuilder().build() : DungeonsRewardsAPI.getHolyWhiteScroll());
        final ItemStack lootbox = new LootboxItemBuilder((Math.random() < 0.4) ? "lootbox_lucky" : "lootbox_hangover").build();
        if (lootbox != null) {
            items.add(lootbox);
        }
        items.add(CCAPI.getCosmicCrate((this.random.nextDouble() <= 0.2) ? Version.APRIL_2018 : ((this.random.nextDouble() < 0.4) ? Version.APRIL_2017 : Version.APRIL), player.getName()));
        return items;
    }
    
    private int getRandomOne() {
        return (Math.random() <= 0.3) ? 2 : 1;
    }
    
    @Override
    public short getNonColorPane() {
        return DyeColor.PURPLE.getWoolData();
    }
    
    @Override
    public List<Color> getConclusionFireworkColors() {
        return (List<Color>)Lists.newArrayList((Object[])new Color[] { Color.AQUA, Color.YELLOW });
    }
    
    @Override
    public short getColorPane() {
        return DyeColor.YELLOW.getWoolData();
    }
}
