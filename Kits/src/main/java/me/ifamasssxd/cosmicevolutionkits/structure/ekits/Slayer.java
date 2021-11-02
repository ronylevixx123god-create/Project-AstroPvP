/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.CosmicSpaceChests.me.astropvp.cratekeys.utils.ItemUtils
 *  com.google.common.collect.Lists
 *  com.rit.sucy.EnchantmentAPI
 *  me.ifamasssxd.cosmicconquest.CosmicConquestAPI
 *  org.arkhamnetwork.enchantments.EnchantmentBook
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Player
 *  org.bukkit.me.astropvp.cratekeys.inventory.ItemStack
 *  org.bukkit.me.astropvp.cratekeys.inventory.meta.ItemMeta
 *  org.bukkit.me.astropvp.cratekeys.inventory.meta.PotionMeta
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package me.ifamasssxd.cosmicevolutionkits.structure.ekits;


import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import com.google.common.collect.Lists;
import com.rit.sucy.EnchantmentAPI;
import me.ifamasssxd.cosmicconquest.CosmicConquestAPI;
import me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKit;
import me.ifamasssxd.cosmicevolutionkits.utils.EnchantedItemBuilder;
import me.ifamasssxd.cosmicevolutionkits.utils.ItemBuilder;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Slayer extends EvolutionKit {
    @Override
    public List<ItemStack> getItems(Player player, int kitLevel) {
        ArrayList<ItemStack> items = Lists.newArrayList();
        EnchantedItemBuilder slayer_sword = new EnchantedItemBuilder(ItemBuilder.buildItem(Material.DIAMOND_SWORD, ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Glitched Sword (" + ChatColor.RED + "LVL " + kitLevel + ChatColor.DARK_RED + ChatColor.BOLD + ")", ChatColor.DARK_RED + "A corrupted version of the most", ChatColor.DARK_RED + "powerful holy weapon in the galaxy.", "", ChatColor.DARK_RED + "" + ChatColor.BOLD + "Kit Level: " + ChatColor.RED + kitLevel + " / 10"));
        double d = (double)kitLevel * 0.025;
        slayer_sword.addCustomEnchant("Assassin", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment("Assassin").getMaxLevel() - 2), EnchantmentAPI.getEnchantment("Assassin").getMaxLevel())));
        slayer_sword.addCustomEnchant("Lifesteal", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment("Lifesteal").getMaxLevel() - 2), EnchantmentAPI.getEnchantment("Lifesteal").getMaxLevel())));
        slayer_sword.addCustomEnchant("Disarmor", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment("Disarmor").getMaxLevel() - 2), EnchantmentAPI.getEnchantment("Disarmor").getMaxLevel())));
        slayer_sword.addCustomEnchant("Paralyze", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment("Paralyze").getMaxLevel() - 2), EnchantmentAPI.getEnchantment("Paralyze").getMaxLevel())));
        slayer_sword.addCustomEnchant("Double Strike", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment("Double Strike").getMaxLevel() - 2), EnchantmentAPI.getEnchantment("Double Strike").getMaxLevel())));
        slayer_sword.addCustomEnchant("Block", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment("Block").getMaxLevel() - 2), EnchantmentAPI.getEnchantment("Block").getMaxLevel())));
        slayer_sword.addCustomEnchant("Rage", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment("Rage").getMaxLevel() - 2), EnchantmentAPI.getEnchantment("Rage").getMaxLevel())));
        slayer_sword.addCustomEnchant("Enrage", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment("Enrage").getMaxLevel() - 2), EnchantmentAPI.getEnchantment("Enrage").getMaxLevel())));
        slayer_sword.addCustomEnchant("Silence", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment("Silence").getMaxLevel() - 2), EnchantmentAPI.getEnchantment("Silence").getMaxLevel())));
        if (kitLevel > 2) {
            slayer_sword.addCustomEnchant("Solitude", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment("Solitude").getMaxLevel() - 2), EnchantmentAPI.getEnchantment("Solitude").getMaxLevel())));
        }
        if (kitLevel > 3) {
            slayer_sword.addCustomEnchant("Inversion", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment("Inversion").getMaxLevel() - 2), EnchantmentAPI.getEnchantment("Inversion").getMaxLevel())));
        }
        if (kitLevel > 6) {
            slayer_sword.addCustomEnchant("Disintegrate", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment("Disintegrate").getMaxLevel() - 2), EnchantmentAPI.getEnchantment("Disintegrate").getMaxLevel())));
        }
        if (kitLevel >= 8 && Math.random() < 0.05 * (double)(kitLevel - 7)) {
            slayer_sword.addCustomEnchant("Divine Immolation", Math.max(1, Math.min(2 + new Random().nextInt(EnchantmentAPI.getEnchantment("Divine Immolation").getMaxLevel()), EnchantmentAPI.getEnchantment("Divine Immolation").getMaxLevel())));
        }
        items.add(EnchantmentBook.applyTransmogScroll(slayer_sword.addEnchant(Enchantment.DAMAGE_ALL, 5).addEnchant(Enchantment.DAMAGE_UNDEAD, 5).addEnchant(Enchantment.DURABILITY, 4).build()));
        EnchantedItemBuilder slayer_chestplate = new EnchantedItemBuilder(ItemBuilder.buildItem(Material.DIAMOND_CHESTPLATE, ChatColor.DARK_RED.toString() + ChatColor.BOLD + "Shroud of War (" + ChatColor.RED + "LVL " + kitLevel + ChatColor.DARK_RED + ChatColor.BOLD + ")", ChatColor.DARK_RED + "A firey shroud that echoes absolute", ChatColor.DARK_RED + "power and domination. No mere human", ChatColor.DARK_RED + "would dare stand against its owner.", "", ChatColor.DARK_RED + "" + ChatColor.BOLD + "Kit Level: " + ChatColor.RED + kitLevel + " / 10"));
        slayer_chestplate.addCustomEnchant("Aegis", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment("Aegis").getMaxLevel() - 2), EnchantmentAPI.getEnchantment("Aegis").getMaxLevel())));
        slayer_chestplate.addCustomEnchant("Repair Guard", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment("Repair Guard").getMaxLevel() - 2), EnchantmentAPI.getEnchantment("Repair Guard").getMaxLevel())));
        slayer_chestplate.addCustomEnchant("Enlighted", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment("Enlighted").getMaxLevel() - 2), EnchantmentAPI.getEnchantment("Enlighted").getMaxLevel())));
        slayer_chestplate.addCustomEnchant("Deathbringer", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment("Deathbringer").getMaxLevel() - 2), EnchantmentAPI.getEnchantment("Deathbringer").getMaxLevel())));
        slayer_chestplate.addCustomEnchant("Overload", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment("Overload").getMaxLevel() - 2), EnchantmentAPI.getEnchantment("Overload").getMaxLevel())));
        slayer_chestplate.addCustomEnchant("Protection", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment("Protection").getMaxLevel() - 2), EnchantmentAPI.getEnchantment("Protection").getMaxLevel())));
        slayer_chestplate.addCustomEnchant("Tank", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment("Tank").getMaxLevel() - 2), EnchantmentAPI.getEnchantment("Tank").getMaxLevel())));
        slayer_chestplate.addCustomEnchant("Dodge", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment("Dodge").getMaxLevel() - 2), EnchantmentAPI.getEnchantment("Dodge").getMaxLevel())));
        slayer_chestplate.addCustomEnchant("Heavy", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment("Heavy").getMaxLevel() - 2), EnchantmentAPI.getEnchantment("Heavy").getMaxLevel())));
        slayer_chestplate.addCustomEnchant("Armored", Math.max(1, Math.min(kitLevel - 1 + new Random().nextInt(EnchantmentAPI.getEnchantment("Armored").getMaxLevel() - 2), EnchantmentAPI.getEnchantment("Armored").getMaxLevel())));
        slayer_chestplate.addCustomEnchant("Ghost", Math.max(1, Math.min(kitLevel - 1 + new Random().nextInt(EnchantmentAPI.getEnchantment("Ghost").getMaxLevel() - 2), EnchantmentAPI.getEnchantment("Ghost").getMaxLevel())));
        if (kitLevel > 3) {
            slayer_chestplate.addCustomEnchant("Resilience", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment("Resilience").getMaxLevel() - 2), EnchantmentAPI.getEnchantment("Resilience").getMaxLevel())));
        }
        if (kitLevel > 5) {
            slayer_chestplate.addCustomEnchant("Arrow Deflect", Math.max(1, Math.min(kitLevel - 1 + new Random().nextInt(EnchantmentAPI.getEnchantment("Arrow Deflect").getMaxLevel() - 2), EnchantmentAPI.getEnchantment("Arrow Deflect").getMaxLevel())));
        }
        if (kitLevel > 8) {
            slayer_chestplate.addCustomEnchant("Guardians", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment("Guardians").getMaxLevel() - 5), EnchantmentAPI.getEnchantment("Guardians").getMaxLevel())));
        }
        if (kitLevel >= 9 && Math.random() < 0.05 * (double)(kitLevel - 8)) {
            slayer_chestplate.addCustomEnchant("Immortal", Math.max(1, Math.min(2 + new Random().nextInt(EnchantmentAPI.getEnchantment("Immortal").getMaxLevel()), EnchantmentAPI.getEnchantment("Immortal").getMaxLevel())));
        }
        if (kitLevel == 10 && Math.random() < 0.05) {
            items.add(ItemUtils.getTitleVoucher("Slayer"));
        }
        items.add(EnchantmentBook.applyTransmogScroll(slayer_chestplate.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchant(Enchantment.PROTECTION_FALL, 2).addEnchant(Enchantment.PROTECTION_PROJECTILE, 2).addEnchant(Enchantment.DURABILITY, 3).build()));
        EnchantedItemBuilder slayer_pickaxe = new EnchantedItemBuilder(ItemBuilder.buildItem(Material.DIAMOND_PICKAXE, ChatColor.DARK_RED.toString() + ChatColor.BOLD + "The Terraformer (" + ChatColor.RED + "LVL " + kitLevel + ChatColor.DARK_RED + ChatColor.BOLD + ")", ChatColor.DARK_RED + "A highly advanced space-age fusion tool", ChatColor.DARK_RED + "used to quickly harvest rare materials.", "", ChatColor.DARK_RED + "" + ChatColor.BOLD + "Kit Level: " + ChatColor.RED + kitLevel + " / 10"));
        slayer_pickaxe.addCustomEnchant("Fuse", 1);
        slayer_pickaxe.addCustomEnchant("Auto Smelt", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment("Auto Smelt").getMaxLevel() - 2), EnchantmentAPI.getEnchantment("Auto Smelt").getMaxLevel())));
        slayer_pickaxe.addCustomEnchant("Haste", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment("Haste").getMaxLevel() - 2), EnchantmentAPI.getEnchantment("Haste").getMaxLevel())));
        slayer_pickaxe.addCustomEnchant("Oxygenate", 1);
        slayer_pickaxe.addCustomEnchant("Telepathy", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment("Telepathy").getMaxLevel() - 2), EnchantmentAPI.getEnchantment("Telepathy").getMaxLevel())));
        if (kitLevel > 3) {
            slayer_pickaxe.addCustomEnchant("Skilling", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment("Skilling").getMaxLevel() - 2), EnchantmentAPI.getEnchantment("Skilling").getMaxLevel())));
        }
        if (kitLevel > 7) {
            slayer_pickaxe.addCustomEnchant("Obsidian Destroyer", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment("Obsidian Destroyer").getMaxLevel() - 2), EnchantmentAPI.getEnchantment("Obsidian Destroyer").getMaxLevel())));
        }
        if (kitLevel == 10 && Math.random() < 0.05) {
            items.add(ItemUtils.getTitleVoucher("Slayer"));
        }
        slayer_pickaxe.addEnchant(Enchantment.DIG_SPEED, 5).addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 2).addEnchant(Enchantment.DURABILITY, 3);
        if (kitLevel > 7 && Math.random() < 0.05 * (double)(kitLevel - 7)) {
            slayer_pickaxe.addEnchant(Enchantment.SILK_TOUCH, 1);
        }
        if (kitLevel > 7 && Math.random() <= 0.1 * (double)(kitLevel - 7)) {
            slayer_pickaxe.addCustomEnchant("Detonate", EnchantmentAPI.getEnchantment("Detonate").getMaxLevel());
            double rand = Math.random();
            int level = rand <= 0.7 ? 1 : (rand <= 0.95 ? 2 : (rand <= 0.98 ? 3 : (rand >= 0.99 ? 4 : 2)));
            slayer_pickaxe.addCustomEnchant("Atomic Detonate", level);
        } else {
            slayer_pickaxe.addCustomEnchant("Detonate", Math.max(1, Math.min(6 + kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment("Detonate").getMaxLevel() - 2), EnchantmentAPI.getEnchantment("Detonate").getMaxLevel())));
        }
        items.add(EnchantmentBook.applyTransmogScroll(slayer_pickaxe.build()));
        ItemStack healPotion = new ItemStack(Material.POTION, 1, (short) 16385);
        PotionMeta pm = (PotionMeta)healPotion.getItemMeta();
        pm.addCustomEffect(new PotionEffect(PotionEffectType.HEAL, 0, 2 + (kitLevel > 8 ? 1 : new Random().nextInt(2))), true);
        healPotion.setItemMeta(pm);
        items.add(healPotion);
        if (kitLevel > 4) {
            items.add(healPotion);
        }
        if (kitLevel > 7) {
            items.add(healPotion);
        }
        if (kitLevel >= 7) {
            items.add(CosmicConquestAPI.createFactionToken(1 + new Random().nextInt(kitLevel - 5)));
        }
        items.add(new ItemStack(Material.ENDER_PEARL, Math.min(64, 8 * kitLevel)));
        return items;
    }

    @Override
    public String getItemName() {
        return ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Slayer";
    }

    @Override
    public List<String> getLore() {
        return Lists.newArrayList("", ChatColor.DARK_RED + ChatColor.BOLD.toString() + "* " + ChatColor.DARK_RED + ChatColor.BOLD + "Glitched Sword", ChatColor.DARK_RED + ChatColor.BOLD.toString() + "* " + ChatColor.DARK_RED + ChatColor.BOLD + "Shroud of War", ChatColor.DARK_RED + ChatColor.BOLD.toString() + "* " + ChatColor.DARK_RED + ChatColor.BOLD + "The Terraformer", ChatColor.DARK_RED + ChatColor.BOLD.toString() + "* " + ChatColor.RED + ChatColor.BOLD + "A chance of...", ChatColor.DARK_RED + ChatColor.BOLD.toString() + "   - " + ChatColor.RED + "Custom Potions", ChatColor.DARK_RED + ChatColor.BOLD.toString() + "   - " + ChatColor.RED + "Enderpearls", ChatColor.DARK_RED + ChatColor.BOLD.toString() + "   - " + ChatColor.RED + "Faction Crystals", "", ChatColor.GRAY + "Quality over quantity: Anti-Gank.", ChatColor.GOLD + "* Back to School 2017");
    }

    @Override
    public Material getDisplayMaterial() {
        return Material.GOLD_SWORD;
    }

    @Override
    public short getDisplayData() {
        return 0;
    }

    @Override
    public int getInventorySlot() {
        return 4;
    }
}

