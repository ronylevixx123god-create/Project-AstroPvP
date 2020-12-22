/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.rit.sucy.EnchantmentAPI
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.inventory.ItemStack
 */
package com.cosmicpvp.CosmicSpaceChests.generators;

import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import com.rit.sucy.EnchantmentAPI;
import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public enum LegendaryItems {
    DRACULA_BLADE("dracula_blade", ItemUtils.buildItem(Material.DIAMOND_SWORD, (short)0, ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "Dracula's Vampiric Blade", ChatColor.GOLD.toString() + ChatColor.ITALIC.toString() + "Sucks your victims dry of life energy.")),
    CTHULU_SWORD("cthulu_sword", ItemUtils.buildItem(Material.DIAMOND_SWORD, (short)0, ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "Cthulu's Ancient Sword", ChatColor.GOLD.toString() + ChatColor.ITALIC.toString() + "An old sword with a destructive history.")),
    HERMES_BOOTS("hermes_boots", ItemUtils.buildItem(Material.DIAMOND_BOOTS, (short)0, ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "Hereme's Winged Boots", ChatColor.GOLD.toString() + ChatColor.ITALIC.toString() + "A pair of boots with wings attached to them.")),
    ARES_CHESTPLATE("ares_chestplate", ItemUtils.buildItem(Material.DIAMOND_CHESTPLATE, (short)0, ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "Ares' War Chestplate", ChatColor.GOLD.toString() + ChatColor.ITALIC.toString() + "A dense, heavy chestplate from the god of war.")),
    APOLLO_BOW("apollo_bow", ItemUtils.buildItem(Material.BOW, (short)0, ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "Apollo's Bow", ChatColor.GOLD.toString() + ChatColor.ITALIC.toString() + "A hand-crafted, gold embroidered bow of light.")),
    MJOLNIR("thor_axe", ItemUtils.buildItem(Material.DIAMOND_AXE, (short)0, ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "Mjolnir", ChatColor.GOLD.toString() + ChatColor.ITALIC.toString() + "The fabled divine hammer of the Norse god, Thor.")),
    HAMMER_OF_HEPHAESTUS("hephaestus_axe", ItemUtils.buildItem(Material.DIAMOND_AXE, (short)0, ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "Hammer of Hephaestus", ChatColor.GOLD.toString() + ChatColor.ITALIC.toString() + "The hammer of the Greek smith-god Hephaestus.")),
    LONGBOW_OF_THOR("thor_longbow", ItemUtils.buildItem(Material.BOW, (short)0, ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "Longbow of Thor", ChatColor.GOLD.toString() + ChatColor.ITALIC.toString() + "A thundering bow of explosive power.")),
    PANS_BOW_OF_COWIFICATION("pans_bow", ItemUtils.buildItem(Material.BOW, (short)0, ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "Pans Bow of Cowification", ChatColor.GOLD.toString() + ChatColor.ITALIC.toString() + "A mystical bow of ... Cows?!")),
    ARES_PLATEMAIL("ares_platemail", ItemUtils.buildItem(Material.DIAMOND_CHESTPLATE, (short)0, ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "Platemail of Aegis the Protector", ChatColor.GOLD.toString() + ChatColor.ITALIC.toString() + "Protect your allies until your last breath.")),
    COSMIC_MILITARY_BLADE("military_blade", ItemUtils.buildItem(Material.DIAMOND_SWORD, (short)0, ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "Cosmic Military Energy Blade", ChatColor.GOLD.toString() + ChatColor.ITALIC.toString() + "Electrifyingly dangerous.")),
    COSMIC_PARTY_HAT("party_hat", ItemUtils.buildItem(Material.DIAMOND_HELMET, (short)0, ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "Cosmic Year #1 Party Hat", ChatColor.GOLD.toString() + ChatColor.ITALIC.toString() + "An extremely rare party hat, only", ChatColor.GOLD.toString() + ChatColor.ITALIC.toString() + "obtainable on the #1 anniversary weekend.", ChatColor.GOLD.toString() + ChatColor.ITALIC.toString() + "Now, party like it is 1999!")),
    SEPTRE_OF_DIO("party_septre", ItemUtils.buildItem(Material.DIAMOND_SWORD, (short)0, ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "The Septre of Dionysus", ChatColor.GOLD.toString() + ChatColor.ITALIC.toString() + "The legendary, fabled gem tipped", ChatColor.GOLD.toString() + ChatColor.ITALIC.toString() + "septre of Dionysus, god of partying.")),
    SHADOW_KILLER("shadow_killer", ItemUtils.buildItem(Material.DIAMOND_SWORD, (short)0, ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "Shadow Killer", ChatColor.GOLD.toString() + ChatColor.ITALIC.toString() + "An onyx dagger with a dark legacy,", ChatColor.GOLD.toString() + ChatColor.ITALIC.toString() + "rumor has it this blade only", ChatColor.GOLD.toString() + ChatColor.ITALIC.toString() + "exists for a few fleeting moments", ChatColor.GOLD.toString() + ChatColor.ITALIC.toString() + "before and after an assassination.")),
    VOID_SANDALS("void_sandals", ItemUtils.buildItem(Material.DIAMOND_BOOTS, (short)0, ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "Erebus' Sandals of the Void", ChatColor.GOLD.toString() + ChatColor.ITALIC.toString() + "You walk on the thin line between", ChatColor.GOLD.toString() + ChatColor.ITALIC.toString() + "existence and extinction, do your", ChatColor.GOLD.toString() + ChatColor.ITALIC.toString() + "enemies dare to follow?")),
    CHAOS_EXECUTIONER("chaos_executioner", ItemUtils.buildItem(Material.DIAMOND_SWORD, (short)0, ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "Chaos Executioner", ChatColor.GOLD.toString() + ChatColor.ITALIC.toString() + "A daemon forged greatsword,", ChatColor.GOLD.toString() + ChatColor.ITALIC.toString() + "wieldable only to those the blade", ChatColor.GOLD.toString() + ChatColor.ITALIC.toString() + "itself deems worthy, and to kill", ChatColor.GOLD.toString() + ChatColor.ITALIC.toString() + "those it deems unworthy.")),
    HEPH_PICKAXE("heph_pickaxe", ItemUtils.buildItem(Material.DIAMOND_PICKAXE, (short)0, ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "Pickaxe of Hephaestus", ChatColor.GOLD.toString() + ChatColor.ITALIC.toString() + "A legendary tool forged by", ChatColor.GOLD.toString() + ChatColor.ITALIC.toString() + "the cosmic god of builders.")),
    SHIVA_SPADE("shiva_spade", ItemUtils.buildItem(Material.DIAMOND_SPADE, (short)0, ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "Shiva's Excavator", ChatColor.GOLD.toString() + ChatColor.ITALIC.toString() + "An ancient spade use by", ChatColor.GOLD.toString() + ChatColor.ITALIC.toString() + "the cosmic god of excavation.")),
    FREEDOM_HAT("freedom_hat", ItemUtils.buildItem(Material.DIAMOND_HELMET, (short)0, ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "Freedom Hat", ChatColor.GOLD.toString() + ChatColor.ITALIC.toString() + "Free from the chains of the ancient", ChatColor.GOLD.toString() + ChatColor.ITALIC.toString() + "masters, you are the captain of your", ChatColor.GOLD.toString() + ChatColor.ITALIC.toString() + "own destiny.")),
    RELIC_OF_JUSTICE("relic_of_justice", ItemUtils.buildItem(Material.DIAMOND_SWORD, (short)0, ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "Relic of Astraea", ChatColor.GOLD.toString() + ChatColor.ITALIC.toString() + "A mystical runeblade imbued with,", ChatColor.GOLD.toString() + ChatColor.ITALIC.toString() + "the virtue of Astraea, godess of justice."));

    private ItemStack is;

    private LegendaryItems(String sys_name, ItemStack base_stack) {
        if (sys_name.equals("relic_of_justice")) {
            base_stack.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 5);
            base_stack.addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 2);
            base_stack.addUnsafeEnchantment(Enchantment.DURABILITY, 4);
            EnchantmentAPI.getEnchantment((String)"Rage").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Rage").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Lifesteal").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Lifesteal").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Featherweight").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Featherweight").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Blind").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Blind").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Execute").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Execute").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Silence").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Silence").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Trap").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Trap").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Inquisitive").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Inquisitive").getMaxLevel()) + 1);
        }
        if (sys_name.equals("freedom_hat")) {
            base_stack.addEnchantment(Enchantment.DURABILITY, 3);
            base_stack.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            EnchantmentAPI.getEnchantment((String)"Leadership").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Leadership").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Lucky").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Lucky").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Smoke Bomb").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Smoke Bomb").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Overload").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Overload").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Enlighted").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Enlighted").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Commander").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Commander").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Angelic").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Angelic").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Glowing").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Glowing").getMaxLevel()) + 1);
        }
        if (sys_name.equals("shiva_spade")) {
            base_stack.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
            base_stack.addEnchantment(Enchantment.DIG_SPEED, 5);
            base_stack.addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, new Random().nextInt(3) + 1);
            EnchantmentAPI.getEnchantment((String)"Detonate").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Detonate").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Telepathy").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Telepathy").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Oxygenate").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Oxygenate").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Haste").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Haste").getMaxLevel()) + 1);
        }
        if (sys_name.equals("heph_pickaxe")) {
            base_stack.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
            base_stack.addEnchantment(Enchantment.DIG_SPEED, 4 + new Random().nextInt(2));
            base_stack.addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, new Random().nextInt(3) + 1);
            EnchantmentAPI.getEnchantment((String)"Auto Smelt").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Auto Smelt").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Detonate").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Detonate").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Experience").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Experience").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Haste").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Haste").getMaxLevel()) + 1);
        }
        if (sys_name.equals("chaos_executioner")) {
            base_stack.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 5);
            base_stack.addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 2);
            base_stack.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
            EnchantmentAPI.getEnchantment((String)"Demonforged").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Demonforged").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Disarmor").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Disarmor").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Execute").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Execute").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Headless").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Headless").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Trap").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Trap").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Silence").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Silence").getMaxLevel()) + 1);
        }
        if (sys_name.equals("void_sandals")) {
            base_stack.addEnchantment(Enchantment.DURABILITY, 3);
            base_stack.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            EnchantmentAPI.getEnchantment((String)"Ender Shift").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Ender Shift").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Ender Walker").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Ender Walker").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Stormcaller").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Stormcaller").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Deathbringer").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Deathbringer").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Anti Gravity").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Anti Gravity").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Voodoo").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Voodoo").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Gears").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Gears").getMaxLevel()) + 1);
        }
        if (sys_name.equals("shadow_killer")) {
            base_stack.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 5);
            base_stack.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 2);
            base_stack.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
            EnchantmentAPI.getEnchantment((String)"Silence").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Silence").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Skill Swipe").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Skill Swipe").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Block").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Block").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Double Strike").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Double Strike").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Featherweight").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Featherweight").getMaxLevel()) + 1);
        }
        if (sys_name.equals("party_septre")) {
            base_stack.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 5);
            base_stack.addUnsafeEnchantment(Enchantment.DAMAGE_ARTHROPODS, 4);
            base_stack.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 2);
            base_stack.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
            EnchantmentAPI.getEnchantment((String)"Insomnia").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Insomnia").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Epicness").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Epicness").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Trap").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Trap").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Lifesteal").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Lifesteal").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Inquisitive").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Inquisitive").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Featherweight").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Featherweight").getMaxLevel()) + 1);
        }
        if (sys_name.equals("party_hat")) {
            base_stack.addEnchantment(Enchantment.DURABILITY, 3);
            base_stack.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            EnchantmentAPI.getEnchantment((String)"Drunk").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Drunk").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Aquatic").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Aquatic").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Glowing").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Glowing").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Implants").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Implants").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Enlighted").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Enlighted").getMaxLevel()) + 1);
        }
        if (sys_name.equals("military_blade")) {
            base_stack.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 5);
            base_stack.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 2);
            base_stack.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
            EnchantmentAPI.getEnchantment((String)"Obliterate").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Obliterate").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Inquisitive").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Inquisitive").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Paralyze").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Paralyze").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Double Strike").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Double Strike").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Featherweight").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Featherweight").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Headless").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Headless").getMaxLevel()) + 1);
        }
        if (sys_name.equals("ares_platemail")) {
            base_stack.addEnchantment(Enchantment.DURABILITY, 3);
            base_stack.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            EnchantmentAPI.getEnchantment((String)"Spirit Link").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Spirit Link").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Self Destruct").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Self Destruct").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Armored").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Armored").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Overload").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Overload").getMaxLevel()) + 1);
            EnchantmentAPI.getEnchantment((String)"Enlighted").addToItem(base_stack, new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Enlighted").getMaxLevel()) + 1);
        }
        if (sys_name.equals("dracula_blade")) {
            base_stack.addEnchantment(Enchantment.DURABILITY, new Random().nextInt(3) + 1);
            base_stack.addEnchantment(Enchantment.DAMAGE_ALL, 5);
            base_stack.addEnchantment(Enchantment.FIRE_ASPECT, 2);
            EnchantmentAPI.getEnchantment((String)"Blind").addToItem(base_stack, new Random().nextInt(3) + 1);
            EnchantmentAPI.getEnchantment((String)"Lifesteal").addToItem(base_stack, 5);
            EnchantmentAPI.getEnchantment((String)"Vampire").addToItem(base_stack, new Random().nextInt(3) + 1);
        }
        if (sys_name.equals("cthulu_sword")) {
            base_stack.addEnchantment(Enchantment.DURABILITY, new Random().nextInt(3) + 1);
            base_stack.addEnchantment(Enchantment.DAMAGE_ALL, 5);
            base_stack.addEnchantment(Enchantment.FIRE_ASPECT, 2);
            base_stack.addEnchantment(Enchantment.KNOCKBACK, new Random().nextInt(2) + 1);
            EnchantmentAPI.getEnchantment((String)"Thundering Blow").addToItem(base_stack, new Random().nextInt(3) + 1);
            EnchantmentAPI.getEnchantment((String)"Deep Wounds").addToItem(base_stack, new Random().nextInt(3) + 1);
        }
        if (sys_name.equals("hermes_boots")) {
            base_stack.addEnchantment(Enchantment.DURABILITY, new Random().nextInt(3) + 1);
            base_stack.addEnchantment(Enchantment.PROTECTION_FALL, new Random().nextInt(2) + 3);
            base_stack.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            base_stack.addEnchantment(Enchantment.THORNS, new Random().nextInt(2) + 2);
            EnchantmentAPI.getEnchantment((String)"Gears").addToItem(base_stack, new Random().nextInt(2) + 2);
            EnchantmentAPI.getEnchantment((String)"Springs").addToItem(base_stack, new Random().nextInt(2) + 2);
        }
        if (sys_name.equals("ares_chestplate")) {
            base_stack.addEnchantment(Enchantment.DURABILITY, new Random().nextInt(2) + 2);
            base_stack.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            base_stack.addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, new Random().nextInt(2) + 3);
            base_stack.addEnchantment(Enchantment.PROTECTION_FIRE, new Random().nextInt(2) + 3);
            base_stack.addEnchantment(Enchantment.PROTECTION_PROJECTILE, new Random().nextInt(2) + 3);
            base_stack.addEnchantment(Enchantment.THORNS, 3);
            EnchantmentAPI.getEnchantment((String)"Implants").addToItem(base_stack, new Random().nextInt(2) + 2);
            EnchantmentAPI.getEnchantment((String)"Hardened").addToItem(base_stack, new Random().nextInt(2) + 2);
            EnchantmentAPI.getEnchantment((String)"Overload").addToItem(base_stack, new Random().nextInt(3) + 1);
        }
        if (sys_name.equals("apollo_bow")) {
            base_stack.addEnchantment(Enchantment.DURABILITY, new Random().nextInt(3) + 1);
            base_stack.addEnchantment(Enchantment.ARROW_DAMAGE, 5);
            base_stack.addEnchantment(Enchantment.ARROW_FIRE, 1);
            base_stack.addEnchantment(Enchantment.ARROW_INFINITE, 1);
            base_stack.addEnchantment(Enchantment.ARROW_KNOCKBACK, new Random().nextInt(2) + 1);
            EnchantmentAPI.getEnchantment((String)"Thundering Blow").addToItem(base_stack, 3);
            EnchantmentAPI.getEnchantment((String)"Piercing").addToItem(base_stack, 3);
        }
        if (sys_name.equals("thor_axe")) {
            base_stack.addEnchantment(Enchantment.DURABILITY, new Random().nextInt(3) + 1);
            base_stack.addEnchantment(Enchantment.DAMAGE_ALL, 5);
            base_stack.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 2);
            base_stack.addUnsafeEnchantment(Enchantment.KNOCKBACK, new Random().nextInt(2) + 1);
            EnchantmentAPI.getEnchantment((String)"Berserk").addToItem(base_stack, 4);
            EnchantmentAPI.getEnchantment((String)"Blessed").addToItem(base_stack, 3);
            EnchantmentAPI.getEnchantment((String)"Bleed").addToItem(base_stack, 5);
        }
        if (sys_name.equals("hephaestus_axe")) {
            base_stack.addEnchantment(Enchantment.DURABILITY, new Random().nextInt(3) + 1);
            base_stack.addEnchantment(Enchantment.DAMAGE_ALL, 5);
            base_stack.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 2);
            base_stack.addUnsafeEnchantment(Enchantment.KNOCKBACK, new Random().nextInt(2) + 1);
            EnchantmentAPI.getEnchantment((String)"Decapitation").addToItem(base_stack, 3);
            EnchantmentAPI.getEnchantment((String)"Blessed").addToItem(base_stack, 3);
            EnchantmentAPI.getEnchantment((String)"Reforged").addToItem(base_stack, 8);
            EnchantmentAPI.getEnchantment((String)"Ravenous").addToItem(base_stack, 4);
        }
        this.is = base_stack.clone();
    }

    public ItemStack getItem() {
        return this.is.clone();
    }

    public static boolean isLegendaryItem(ItemStack is) {
        return is != null && is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith(ChatColor.GOLD.toString());
    }
}

