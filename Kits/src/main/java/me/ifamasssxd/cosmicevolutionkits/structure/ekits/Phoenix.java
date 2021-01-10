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
import me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKit;
import me.ifamasssxd.cosmicevolutionkits.utils.EnchantedItemBuilder;
import me.ifamasssxd.cosmicevolutionkits.utils.ItemBuilder;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Phoenix
extends EvolutionKit {
    @Override
    public List<ItemStack> getItems(Player player, int kitLevel) {
        ArrayList items = Lists.newArrayList();
        EnchantedItemBuilder sandalsOfThePhoenix = new EnchantedItemBuilder(ItemBuilder.buildItem(Material.DIAMOND_BOOTS, (Object)ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Sandals of the Phoenix (" + (Object)ChatColor.LIGHT_PURPLE + "LVL " + kitLevel + (Object)ChatColor.DARK_PURPLE + (Object)ChatColor.BOLD + ")", (Object)ChatColor.DARK_PURPLE + "A fabled relic that grants the owner", (Object)ChatColor.DARK_PURPLE + "control over life and death itself.", "", (Object)ChatColor.DARK_PURPLE + "" + (Object)ChatColor.BOLD + "Kit Level: " + (Object)ChatColor.LIGHT_PURPLE + kitLevel + " / 10"));
        double d = (double)kitLevel * 0.025;
        sandalsOfThePhoenix.addCustomEnchant("Gears", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Gears").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Gears").getMaxLevel())));
        sandalsOfThePhoenix.addCustomEnchant("Tank", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Tank").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Tank").getMaxLevel())));
        sandalsOfThePhoenix.addCustomEnchant("Enlighted", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Enlighted").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Enlighted").getMaxLevel())));
        sandalsOfThePhoenix.addCustomEnchant("Deathbringer", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Deathbringer").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Deathbringer").getMaxLevel())));
        sandalsOfThePhoenix.addCustomEnchant("Spirit Link", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Spirit Link").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Spirit Link").getMaxLevel())));
        sandalsOfThePhoenix.addCustomEnchant("Springs", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Springs").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Springs").getMaxLevel())));
        if (kitLevel > 2) {
            sandalsOfThePhoenix.addCustomEnchant("Valor", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Valor").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Valor").getMaxLevel())));
        }
        if (kitLevel > 3) {
            sandalsOfThePhoenix.addCustomEnchant("Armored", Math.max(1, Math.min(kitLevel / 2 - 3 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Armored").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Armored").getMaxLevel())));
        }
        if (kitLevel > 6) {
            sandalsOfThePhoenix.addCustomEnchant("Angelic", Math.max(1, Math.min(kitLevel - 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Angelic").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Angelic").getMaxLevel())));
        }
        if (kitLevel > 8) {
            sandalsOfThePhoenix.addCustomEnchant("Arrow Deflect", Math.max(1, Math.min(kitLevel / 2 - 5 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Arrow Deflect").getMaxLevel()), EnchantmentAPI.getEnchantment((String)"Arrow Deflect").getMaxLevel())));
        }
        if (Math.random() > d) {
            sandalsOfThePhoenix.addCustomEnchant("Ender Shift", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Ender Shift").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Ender Shift").getMaxLevel())));
        } else {
            sandalsOfThePhoenix.addCustomEnchant("Phoenix", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Phoenix").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Phoenix").getMaxLevel())));
        }
        items.add(EnchantmentBook.applyTransmogScroll((ItemStack)sandalsOfThePhoenix.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchant(Enchantment.PROTECTION_FALL, 4).addEnchant(Enchantment.DURABILITY, 3).build()));
        EnchantedItemBuilder sikanda = new EnchantedItemBuilder(ItemBuilder.buildItem(Material.DIAMOND_SWORD, ChatColor.DARK_PURPLE.toString() + (Object)ChatColor.BOLD + "Sikanda (" + (Object)ChatColor.LIGHT_PURPLE + "LVL " + kitLevel + (Object)ChatColor.DARK_PURPLE + (Object)ChatColor.BOLD + ")", (Object)ChatColor.DARK_PURPLE + "The legendary flaming sword of Grograman,", (Object)ChatColor.DARK_PURPLE + "AKA \"The Many colored Death\". An ancient", (Object)ChatColor.DARK_PURPLE + "celtic demon that personifies destruction.", "", (Object)ChatColor.DARK_PURPLE + "" + (Object)ChatColor.BOLD + "Kit Level: " + (Object)ChatColor.LIGHT_PURPLE + kitLevel + " / 10"));
        sikanda.addCustomEnchant("Disintegrate", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Disintegrate").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Disintegrate").getMaxLevel())));
        sikanda.addCustomEnchant("Blind", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Blind").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Blind").getMaxLevel())));
        sikanda.addCustomEnchant("Disarmor", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Disarmor").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Disarmor").getMaxLevel())));
        sikanda.addCustomEnchant("Execute", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Execute").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Execute").getMaxLevel())));
        sikanda.addCustomEnchant("Lifesteal", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Lifesteal").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Lifesteal").getMaxLevel())));
        sikanda.addCustomEnchant("Rage", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Rage").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Rage").getMaxLevel())));
        sikanda.addCustomEnchant("Double Strike", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Double Strike").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Double Strike").getMaxLevel())));
        if (kitLevel > 4) {
            sikanda.addCustomEnchant("Silence", Math.max(1, Math.min(kitLevel - 1 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Silence").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Silence").getMaxLevel())));
        }
        if (kitLevel > 5) {
            sikanda.addCustomEnchant("Inversion", Math.max(1, Math.min(kitLevel - 1 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Inversion").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Inversion").getMaxLevel())));
        }
        if (kitLevel > 7) {
            sikanda.addCustomEnchant("Solitude", Math.max(1, Math.min(kitLevel - 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Solitude").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Solitude").getMaxLevel())));
        }
        if (kitLevel >= 9 && Math.random() < 0.05 * (double)(kitLevel - 8)) {
            sikanda.addCustomEnchant("Divine Immolation", Math.max(1, Math.min(2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Divine Immolation").getMaxLevel()), EnchantmentAPI.getEnchantment((String)"Divine Immolation").getMaxLevel())));
        }
        if (kitLevel == 10 && Math.random() < 0.05) {
            items.add(ItemUtils.getTitleVoucher((String)"Phoenix"));
        }
        items.add(EnchantmentBook.applyTransmogScroll((ItemStack)sikanda.addEnchant(Enchantment.DAMAGE_ALL, 5).addEnchant(Enchantment.FIRE_ASPECT, 2).addEnchant(Enchantment.DAMAGE_UNDEAD, 5).addEnchant(Enchantment.DURABILITY, 3).build()));
        return items;
    }

    @Override
    public String getItemName() {
        return (Object)ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Phoenix";
    }

    @Override
    public List<String> getLore() {
        return Lists.newArrayList("", (Object)ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "* " + (Object)ChatColor.DARK_PURPLE + (Object)ChatColor.BOLD + "Sandals of the Phoenix", (Object)ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "* " + (Object)ChatColor.DARK_PURPLE + (Object)ChatColor.BOLD + "Sikanda", "", (Object)ChatColor.GRAY + "Return from the ashes once more.", (Object)ChatColor.GOLD + "* Astro Renewal Patch");
    }

    @Override
    public Material getDisplayMaterial() {
        return Material.BLAZE_POWDER;
    }

    @Override
    public short getDisplayData() {
        return 0;
    }

    @Override
    public int getInventorySlot() {
        return 0;
    }
}

