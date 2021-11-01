/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.utils.ItemBuilder
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.utils.NBTTagCompoundBuilder
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.utils.NBTWrapper
 *  com.google.common.collect.Lists
 *  com.rit.sucy.CustomEnchantment
 *  com.rit.sucy.EnchantmentAPI
 *  me.ifamasssxd.cosmicmysterychest.CosmicMysteryChest
 *  me.ifamasssxd.cosmicmysterychest.struct.MysteryChest
 *  net.minecraft.server.v1_7_R4.NBTBase
 *  org.arkhamnetwork.Arkkit.patches.ItemID
 *  org.arkhamnetwork.Arkkit.plugins.ArkPlugins
 *  org.arkhamnetwork.enchantments.EnchantmentBook
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.event.me.astropvp.cratekeys.inventory.InventoryClickEvent
 *  org.bukkit.me.astropvp.cratekeys.inventory.ItemStack
 */
package com.compilex.CosmicDungeons;

import com.compilex.CosmicDungeons.dungeons.libs.Dungeon;
import net.astropvp.core.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.google.common.collect.Lists;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import me.ifamasssxd.cosmicmysterychest.CosmicMysteryChest;
import me.ifamasssxd.cosmicmysterychest.struct.MysteryChest;
import net.minecraft.server.v1_8_R3.NBTBase;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class DungeonsRewardsAPI {
    private static List<String> heroicEnchantments = new ArrayList<String>(Arrays.asList("Planetary Deathbringer", "Master Inquisitive", "Divine Enlighted", "Godly Overload"));

    public static ItemStack getSoulPearl() {
        return ItemUtils.addGlow(ItemUtils.buildItem(Material.ENDER_PEARL, (short)0, (Object)ChatColor.DARK_PURPLE + "" + (Object)ChatColor.BOLD + "Soul Pearl", (Object)ChatColor.LIGHT_PURPLE + "A rare /dungeon trinket that", (Object)ChatColor.LIGHT_PURPLE + "functions like a normal enderpearl,", (Object)ChatColor.LIGHT_PURPLE + "but costs souls instead of being,", (Object)ChatColor.LIGHT_PURPLE + "consumed on use.", "", (Object)ChatColor.DARK_PURPLE + "" + (Object)ChatColor.UNDERLINE + "Soul Cost:" + (Object)ChatColor.LIGHT_PURPLE + " 5/pearl"));
    }

    public static ItemStack getSoulAnvil() {
        return ItemUtils.addGlow(ItemUtils.buildItem(Material.ANVIL, (short)0, (Object)ChatColor.DARK_PURPLE + "" + (Object)ChatColor.BOLD + "Soul Anvil", (Object)ChatColor.LIGHT_PURPLE + "A powerful /dungeon trinket that", (Object)ChatColor.LIGHT_PURPLE + "heals durability of all equipped,", (Object)ChatColor.LIGHT_PURPLE + "armor by 10% in exchange for souls.", "", (Object)ChatColor.DARK_PURPLE + "" + (Object)ChatColor.UNDERLINE + "Soul Cost:" + (Object)ChatColor.LIGHT_PURPLE + " 100/use", (Object)ChatColor.DARK_PURPLE + "" + (Object)ChatColor.UNDERLINE + "Cooldown:" + (Object)ChatColor.LIGHT_PURPLE + " 30s"));
    }

    public static ItemStack getHolyWhiteScroll() {
        return CosmicDungeonsPlugin.get().getHolyWhitescrollItem().createItem(null, new Object[0]);
    }

    @Deprecated
    public static ItemStack getOldHolyWhiteScroll() {
        return ItemUtils.addGlow(ItemUtils.buildItem(Material.PAPER, (short)0, (Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "Holy White Scroll", (Object)ChatColor.YELLOW + "A legendary /dungeon reward that", (Object)ChatColor.YELLOW + "can be applied to armor/weapons,", (Object)ChatColor.YELLOW + "gives a 100% chance of not losing", (Object)ChatColor.YELLOW + "the blessed item on death.", "", (Object)ChatColor.GOLD + "" + (Object)ChatColor.UNDERLINE + "REQ:" + (Object)ChatColor.YELLOW + " White Scroll"));
    }

    public static ItemStack getRandomHeroicEnchantment() {
        CustomEnchantment ce = EnchantmentAPI.getEnchantment((String)heroicEnchantments.get(new Random().nextInt(heroicEnchantments.size())));
        return EnchantmentBook.getEnchantmentBook((CustomEnchantment)ce, (int)(1 + new Random().nextInt(ce.getMaxLevel())));
    }

    public static ItemStack getDungeonLootBag(Player p, Dungeon d) {
        ArrayList lore = Lists.newArrayList(new String[]{(Object)ChatColor.GRAY + "A bag of dungeon loot obtained", (Object)ChatColor.GRAY + "by " + (Object)ChatColor.GOLD + p.getName() + (Object)ChatColor.GRAY + " from defeating the", d.getDungeonName() + (Object)ChatColor.GRAY + " dungeon!", ""});
        lore.add((Object)ChatColor.GRAY + "Contains " + (Object)ChatColor.GOLD + "??? Dungeon" + (Object)ChatColor.GRAY + " me.astropvp.cratekeys.items...");
        if (d.isAbandondedSpaceship()) {
            lore.addAll(Lists.newArrayList(new String[]{(Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GOLD + "$$,$$$,$$$+ Money Notes", (Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GOLD + "100+ Level mcMMO Vouchers", (Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GOLD + "Soul Enderpearl", (Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GOLD + "Soul Anvil", (Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GOLD + "Soul Dust", (Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GOLD + "Holy White Scroll", (Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GOLD + "Mother of Yijki Armor", (Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GOLD + "Planetary Deathbringer", (Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GOLD + "Master Inquisitive", (Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GOLD + "Divine Enlighted"}));
            return ((MysteryChest)CosmicMysteryChest.get().getChestManager().getMysteryChestMap().get("FirstDungeonLootbag")).createClaimableItem(false);
        }
        lore.addAll(Lists.newArrayList(new String[]{(Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GOLD + "$,$$$,$$$+ Money Notes", (Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GOLD + "100-500 Level mcMMO Vouchers", (Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GOLD + "Soul Enderpearl", (Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GOLD + "Heroic Armor", (Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GOLD + "13 Slot Enchantment Orb", (Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GOLD + "Soul Dust", (Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GOLD + "Heroic Black Scrolls", (Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GOLD + "Dimensional Traveler Armor", (Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GOLD + "Heroic Faction Crystals", (Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GOLD + "Equipment Lootbox", (Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GOLD + "Superior Crate"}));
        lore.add((Object)ChatColor.GOLD + "... good luck.");
        ItemStack retr = new ItemBuilder(Material.CHEST).name((Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "Dungeon Lootbag: " + d.getDungeonName()).lore((List)lore).build();
        if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning()) {
            retr = ItemID.assignItemID((InventoryClickEvent)null, (ItemStack)retr);
        }
        return new NBTWrapper(retr).setString("cosmicType", "lootbag").setNBTBase("conaxData", (NBTBase)new NBTTagCompoundBuilder().setString("bagType", d.getDungeonType().name()).get()).build();
    }

    public static boolean isDungeonLootBag(ItemStack i) {
        return i != null && i.getType() == Material.CHEST && i.hasItemMeta() && i.getItemMeta().hasLore() && ((String)i.getItemMeta().getLore().get(0)).equals((Object)ChatColor.GRAY + "A bag of dungeon loot obtained");
    }
}

