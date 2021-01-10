/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils
 *  com.google.common.collect.Lists
 *  com.rit.sucy.EnchantmentAPI
 *  org.arkhamnetwork.enchantments.EnchantmentBook
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package me.ifamasssxd.cosmicevolutionkits.structure.ekits;

import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import com.google.common.collect.Lists;
import com.rit.sucy.EnchantmentAPI;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKit;
import me.ifamasssxd.cosmicevolutionkits.utils.EnchantedItemBuilder;
import me.ifamasssxd.cosmicevolutionkits.utils.ItemBuilder;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Ogre
extends EvolutionKit {
    @Override
    public List<ItemStack> getItems(Player player, int kitLevel) {
        ArrayList items = Lists.newArrayList();
        EnchantedItemBuilder ogre_helmet = new EnchantedItemBuilder(ItemBuilder.buildItem(Material.DIAMOND_HELMET, (Object)ChatColor.DARK_GREEN + ChatColor.BOLD.toString() + "Ogre Mask (" + (Object)ChatColor.GREEN + "LVL " + kitLevel + (Object)ChatColor.DARK_GREEN + (Object)ChatColor.BOLD + ")", (Object)ChatColor.DARK_GREEN + "Learn to tame your monster inside,", (Object)ChatColor.DARK_GREEN + "before it eats you alive...", "", (Object)ChatColor.DARK_GREEN + "" + (Object)ChatColor.BOLD + "Kit Level: " + (Object)ChatColor.GREEN + kitLevel + " / 10"));
        double d = (double)kitLevel * 0.025;
        ogre_helmet.addCustomEnchant("Resilience", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Resilience").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Resilience").getMaxLevel())));
        ogre_helmet.addCustomEnchant("Tank", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Tank").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Tank").getMaxLevel())));
        ogre_helmet.addCustomEnchant("Deathbringer", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Deathbringer").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Deathbringer").getMaxLevel())));
        ogre_helmet.addCustomEnchant("Destruction", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Destruction").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Destruction").getMaxLevel())));
        ogre_helmet.addCustomEnchant("Voodoo", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Voodoo").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Voodoo").getMaxLevel())));
        ogre_helmet.addCustomEnchant("Enlighted", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Enlighted").getMaxLevel()), EnchantmentAPI.getEnchantment((String)"Enlighted").getMaxLevel())));
        ogre_helmet.addCustomEnchant("Clarity", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Clarity").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Clarity").getMaxLevel())));
        if (kitLevel > 2) {
            ogre_helmet.addCustomEnchant("Armored", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Armored").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Armored").getMaxLevel())));
        }
        if (kitLevel > 3) {
            ogre_helmet.addCustomEnchant("Lucky", Math.max(1, Math.min(kitLevel / 2 - 3 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Lucky").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Lucky").getMaxLevel())));
        }
        if (kitLevel > 6) {
            ogre_helmet.addCustomEnchant("Angelic", Math.max(1, Math.min(kitLevel - 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Angelic").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Angelic").getMaxLevel())));
        }
        if (kitLevel > 8) {
            ogre_helmet.addCustomEnchant("Ghost", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Ghost").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Ghost").getMaxLevel())));
        }
        items.add(EnchantmentBook.applyTransmogScroll((ItemStack)ogre_helmet.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchant(Enchantment.DURABILITY, 3).build()));
        EnchantedItemBuilder ogre_chestplate = new EnchantedItemBuilder(ItemBuilder.buildItem(Material.DIAMOND_CHESTPLATE, ChatColor.DARK_GREEN.toString() + (Object)ChatColor.BOLD + "Fat Tummy (" + (Object)ChatColor.GREEN + "LVL " + kitLevel + (Object)ChatColor.DARK_GREEN + (Object)ChatColor.BOLD + ")", (Object)ChatColor.DARK_GREEN + "There's no getting around it, it's", (Object)ChatColor.DARK_GREEN + "a huge lump of thick, nasty ogre fat.", (Object)ChatColor.DARK_GREEN + "Unconventional, yet amazingly effective.", "", (Object)ChatColor.DARK_GREEN + "" + (Object)ChatColor.BOLD + "Kit Level: " + (Object)ChatColor.GREEN + kitLevel + " / 10"));
        ogre_chestplate.addCustomEnchant("Blood Link", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Blood Link").getMaxLevel() - 3), EnchantmentAPI.getEnchantment((String)"Blood Link").getMaxLevel())));
        ogre_chestplate.addCustomEnchant("Sticky", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Sticky").getMaxLevel() - 4), EnchantmentAPI.getEnchantment((String)"Sticky").getMaxLevel())));
        ogre_chestplate.addCustomEnchant("Guardians", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Guardians").getMaxLevel() - 5), EnchantmentAPI.getEnchantment((String)"Guardians").getMaxLevel())));
        ogre_chestplate.addCustomEnchant("Tank", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Tank").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Tank").getMaxLevel())));
        ogre_chestplate.addCustomEnchant("Enlighted", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Enlighted").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Enlighted").getMaxLevel())));
        ogre_chestplate.addCustomEnchant("Deathbringer", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Deathbringer").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Deathbringer").getMaxLevel())));
        ogre_chestplate.addCustomEnchant("Overload", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Overload").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Overload").getMaxLevel())));
        ogre_chestplate.addCustomEnchant("Protection", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Protection").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Protection").getMaxLevel())));
        ogre_chestplate.addCustomEnchant("Shockwave", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Shockwave").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Shockwave").getMaxLevel())));
        if (kitLevel > 4) {
            ogre_chestplate.addCustomEnchant("Armored", Math.max(1, Math.min(kitLevel - 1 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Armored").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Armored").getMaxLevel())));
        }
        if (kitLevel > 5) {
            ogre_chestplate.addCustomEnchant("Arrow Deflect", Math.max(1, Math.min(kitLevel - 1 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Arrow Deflect").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Arrow Deflect").getMaxLevel())));
        }
        if (kitLevel > 7) {
            ogre_chestplate.addCustomEnchant("Angelic", Math.max(1, Math.min(kitLevel - 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Angelic").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Angelic").getMaxLevel())));
        }
        if (kitLevel >= 9 && Math.random() < 0.05 * (double)(kitLevel - 8)) {
            ogre_chestplate.addCustomEnchant("Immortal", Math.max(1, Math.min(2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Immortal").getMaxLevel()), EnchantmentAPI.getEnchantment((String)"Immortal").getMaxLevel())));
        }
        if (kitLevel == 10 && Math.random() < 0.05) {
            items.add(ItemUtils.getTitleVoucher((String)"Ogre"));
        }
        items.add(EnchantmentBook.applyTransmogScroll((ItemStack)ogre_chestplate.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchant(Enchantment.PROTECTION_FALL, 2).addEnchant(Enchantment.PROTECTION_PROJECTILE, 2).addEnchant(Enchantment.DURABILITY, 4).build()));
        return items;
    }

    @Override
    public String getItemName() {
        return (Object)ChatColor.DARK_GREEN + ChatColor.BOLD.toString() + "Ogre";
    }

    @Override
    public List<String> getLore() {
        return Lists.newArrayList("", (Object)ChatColor.DARK_GREEN + ChatColor.BOLD.toString() + "* " + (Object)ChatColor.DARK_GREEN + (Object)ChatColor.BOLD + "Ogre Mask", (Object)ChatColor.DARK_GREEN + ChatColor.BOLD.toString() + "* " + (Object)ChatColor.DARK_GREEN + (Object)ChatColor.BOLD + "Fat Tummy", "", (Object)ChatColor.GRAY + "The best offense is... a fat defense?", (Object)ChatColor.GOLD + "* Summer Break 2017");
    }

    @Override
    public Material getDisplayMaterial() {
        return Material.SKULL_ITEM;
    }

    @Override
    public short getDisplayData() {
        return 2;
    }

    @Override
    public int getInventorySlot() {
        return 3;
    }
}

