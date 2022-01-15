/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.CosmicSpaceChests.me.astropvp.cratekeys.net.astropvp.armorsets.utils.ItemUtils
 *  com.google.common.collect.Lists
 *  com.rit.sucy.EnchantmentAPI
 *  org.arkhamnetwork.enchantments.EnchantmentBook
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Player
 *  org.bukkit.me.astropvp.cratekeys.inventory.ItemStack
 */
package me.ifamasssxd.cosmicevolutionkits.structure.ekits;

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

public class Troll
extends EvolutionKit {
    @Override
    public List<ItemStack> getItems(Player player, int kitLevel) {
        ArrayList items = Lists.newArrayList();
        EnchantedItemBuilder troll_pants = new EnchantedItemBuilder(ItemBuilder.buildItem(Material.DIAMOND_LEGGINGS, (Object)ChatColor.DARK_AQUA + ChatColor.BOLD.toString() + "Trollolololo\"L\" (" + (Object)ChatColor.AQUA + "LVL " + kitLevel + (Object)ChatColor.DARK_AQUA + (Object)ChatColor.BOLD + ")", (Object)ChatColor.DARK_AQUA + "Everywhere you go, you can hear the faint", (Object)ChatColor.DARK_AQUA + "humm of your leggings as they whisper... \"Trollolol\".", "", (Object)ChatColor.DARK_AQUA + "" + (Object)ChatColor.BOLD + "Kit Level: " + (Object)ChatColor.AQUA + kitLevel + " / 10"));
        double d = (double)kitLevel * 0.025;
        troll_pants.addCustomEnchant("Tank", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Tank").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Tank").getMaxLevel())));
        troll_pants.addCustomEnchant("Clarity", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Clarity").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Clarity").getMaxLevel())));
        troll_pants.addCustomEnchant("Enlighted", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Enlighted").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Enlighted").getMaxLevel())));
        troll_pants.addCustomEnchant("Deathbringer", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Deathbringer").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Deathbringer").getMaxLevel())));
        troll_pants.addCustomEnchant("Armored", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Armored").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Armored").getMaxLevel())));
        troll_pants.addCustomEnchant("Obsidianshield", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Obsidianshield").getMaxLevel()), EnchantmentAPI.getEnchantment((String)"Obsidianshield").getMaxLevel())));
        troll_pants.addCustomEnchant("Cactus", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Cactus").getMaxLevel() - 1), EnchantmentAPI.getEnchantment((String)"Cactus").getMaxLevel())));
        troll_pants.addCustomEnchant("Protection", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Protection").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Protection").getMaxLevel())));
        if (kitLevel > 2) {
            troll_pants.addCustomEnchant("Lucky", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Valor").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Valor").getMaxLevel())));
        }
        if (kitLevel > 3) {
            troll_pants.addCustomEnchant("Dodge", Math.max(1, Math.min(kitLevel / 2 - 3 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Armored").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Armored").getMaxLevel())));
        }
        if (kitLevel > 6) {
            troll_pants.addCustomEnchant("Angelic", Math.max(1, Math.min(kitLevel - 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Angelic").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Angelic").getMaxLevel())));
        }
        if (Math.random() > d) {
            troll_pants.addCustomEnchant("Guardians", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Guardians").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Guardians").getMaxLevel())));
        } else {
            troll_pants.addCustomEnchant("Nature Wrath", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Nature Wrath").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Nature Wrath").getMaxLevel())));
        }
        items.add(EnchantmentBook.applyTransmogScroll((ItemStack)troll_pants.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchant(Enchantment.DURABILITY, 3).build()));
        EnchantedItemBuilder troll_axe = new EnchantedItemBuilder(ItemBuilder.buildItem(Material.DIAMOND_AXE, ChatColor.DARK_AQUA.toString() + (Object)ChatColor.BOLD + "Axe of ( \u0361\u00b0 \u035c\u0296 \u0361\u00b0) (" + (Object)ChatColor.AQUA + "LVL " + kitLevel + (Object)ChatColor.DARK_AQUA + (Object)ChatColor.BOLD + ")", (Object)ChatColor.DARK_AQUA + "An axe believed to be unrivalved in cringe", (Object)ChatColor.DARK_AQUA + "in all of the Cosmoverse; lemme axe you a", (Object)ChatColor.DARK_AQUA + "question, does it hurt to hold it in your hands?", "", (Object)ChatColor.DARK_AQUA + "" + (Object)ChatColor.BOLD + "Kit Level: " + (Object)ChatColor.AQUA + kitLevel + " / 10"));
        troll_axe.addCustomEnchant("Rage", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Rage").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Rage").getMaxLevel())));
        troll_axe.addCustomEnchant("Barbarian", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Barbarian").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Barbarian").getMaxLevel())));
        troll_axe.addCustomEnchant("Devour", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Devour").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Devour").getMaxLevel())));
        troll_axe.addCustomEnchant("Cleave", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Cleave").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Cleave").getMaxLevel())));
        troll_axe.addCustomEnchant("Bleed", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Bleed").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Bleed").getMaxLevel())));
        troll_axe.addCustomEnchant("Blessed", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Blessed").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Blessed").getMaxLevel())));
        troll_axe.addCustomEnchant("Pummel", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Pummel").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Pummel").getMaxLevel())));
        troll_axe.addCustomEnchant("Confusion", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Confusion").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Confusion").getMaxLevel())));
        if (kitLevel > 4) {
            troll_axe.addCustomEnchant("Insanity", Math.max(1, Math.min(kitLevel - 1 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Insanity").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Insanity").getMaxLevel())));
        }
        if (kitLevel > 5) {
            troll_axe.addCustomEnchant("Lifesteal", Math.max(1, Math.min(kitLevel - 1 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Lifesteal").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Lifesteal").getMaxLevel())));
        }
        if (kitLevel > 7) {
            troll_axe.addCustomEnchant("Silence", Math.max(1, Math.min(kitLevel - 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Silence").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Silence").getMaxLevel())));
        }
        if (kitLevel >= 9 && Math.random() < 0.05 * (double)(kitLevel - 8)) {
            troll_axe.addCustomEnchant("Soul Trap", Math.max(1, Math.min(2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Soul Trap").getMaxLevel()), EnchantmentAPI.getEnchantment((String)"Soul Trap").getMaxLevel())));
        }
        items.add(EnchantmentBook.applyTransmogScroll((ItemStack)troll_axe.addEnchant(Enchantment.DAMAGE_ALL, 5).addEnchant(Enchantment.FIRE_ASPECT, 2).addEnchant(Enchantment.DURABILITY, 3).build()));
        return items;
    }

    @Override
    public String getItemName() {
        return (Object)ChatColor.DARK_AQUA + ChatColor.BOLD.toString() + "Troll";
    }

    @Override
    public List<String> getLore() {
        return Lists.newArrayList("", (Object)ChatColor.DARK_AQUA + ChatColor.BOLD.toString() + "* " + (Object)ChatColor.DARK_AQUA + (Object)ChatColor.BOLD + "Trollolololo\"L\"", (Object)ChatColor.DARK_AQUA + ChatColor.BOLD.toString() + "* " + (Object)ChatColor.DARK_AQUA + (Object)ChatColor.BOLD + "Axe of ( \u0361\u00b0 \u035c\u0296 \u0361\u00b0)", "", (Object)ChatColor.GRAY + "Who needs love when you can meme?", (Object)ChatColor.GOLD + "* Valentine 2017");
    }

    @Override
    public Material getDisplayMaterial() {
        return Material.BONE;
    }

    @Override
    public short getDisplayData() {
        return 0;
    }

    @Override
    public int getInventorySlot() {
        return 1;
    }
}

