/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils
 *  com.google.common.collect.Lists
 *  com.rit.sucy.EnchantmentAPI
 *  me.ifamasssxd.cosmicconquest.CosmicConquestAPI
 *  org.arkhamnetwork.enchantments.EnchantmentBook
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.inventory.meta.PotionMeta
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package me.ifamasssxd.cosmicevolutionkits.structure.ekits;

import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import com.google.common.collect.Lists;
import com.rit.sucy.EnchantmentAPI;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Slayer
extends EvolutionKit {
    @Override
    public List<ItemStack> getItems(Player player, int kitLevel) {
        ArrayList items = Lists.newArrayList();
        EnchantedItemBuilder slayer_sword = new EnchantedItemBuilder(ItemBuilder.buildItem(Material.DIAMOND_SWORD, (Object)ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Glitched Sword (" + (Object)ChatColor.RED + "LVL " + kitLevel + (Object)ChatColor.DARK_RED + (Object)ChatColor.BOLD + ")", (Object)ChatColor.DARK_RED + "A corrupted version of the most", (Object)ChatColor.DARK_RED + "powerful holy weapon in the galaxy.", "", (Object)ChatColor.DARK_RED + "" + (Object)ChatColor.BOLD + "Kit Level: " + (Object)ChatColor.RED + kitLevel + " / 10"));
        double d = (double)kitLevel * 0.025;
        slayer_sword.addCustomEnchant("Assassin", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Assassin").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Assassin").getMaxLevel())));
        slayer_sword.addCustomEnchant("Lifesteal", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Lifesteal").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Lifesteal").getMaxLevel())));
        slayer_sword.addCustomEnchant("Disarmor", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Disarmor").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Disarmor").getMaxLevel())));
        slayer_sword.addCustomEnchant("Paralyze", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Paralyze").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Paralyze").getMaxLevel())));
        slayer_sword.addCustomEnchant("Double Strike", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Double Strike").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Double Strike").getMaxLevel())));
        slayer_sword.addCustomEnchant("Block", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Block").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Block").getMaxLevel())));
        slayer_sword.addCustomEnchant("Rage", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Rage").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Rage").getMaxLevel())));
        slayer_sword.addCustomEnchant("Enrage", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Enrage").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Enrage").getMaxLevel())));
        slayer_sword.addCustomEnchant("Silence", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Silence").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Silence").getMaxLevel())));
        if (kitLevel > 2) {
            slayer_sword.addCustomEnchant("Solitude", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Solitude").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Solitude").getMaxLevel())));
        }
        if (kitLevel > 3) {
            slayer_sword.addCustomEnchant("Inversion", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Inversion").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Inversion").getMaxLevel())));
        }
        if (kitLevel > 6) {
            slayer_sword.addCustomEnchant("Disintegrate", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Disintegrate").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Disintegrate").getMaxLevel())));
        }
        if (kitLevel >= 8 && Math.random() < 0.05 * (double)(kitLevel - 7)) {
            slayer_sword.addCustomEnchant("Divine Immolation", Math.max(1, Math.min(2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Divine Immolation").getMaxLevel()), EnchantmentAPI.getEnchantment((String)"Divine Immolation").getMaxLevel())));
        }
        items.add(EnchantmentBook.applyTransmogScroll((ItemStack)slayer_sword.addEnchant(Enchantment.DAMAGE_ALL, 5).addEnchant(Enchantment.DAMAGE_UNDEAD, 5).addEnchant(Enchantment.DURABILITY, 4).build()));
        EnchantedItemBuilder slayer_chestplate = new EnchantedItemBuilder(ItemBuilder.buildItem(Material.DIAMOND_CHESTPLATE, ChatColor.DARK_RED.toString() + (Object)ChatColor.BOLD + "Shroud of War (" + (Object)ChatColor.RED + "LVL " + kitLevel + (Object)ChatColor.DARK_RED + (Object)ChatColor.BOLD + ")", (Object)ChatColor.DARK_RED + "A firey shroud that echoes absolute", (Object)ChatColor.DARK_RED + "power and domination. No mere human", (Object)ChatColor.DARK_RED + "would dare stand against its owner.", "", (Object)ChatColor.DARK_RED + "" + (Object)ChatColor.BOLD + "Kit Level: " + (Object)ChatColor.RED + kitLevel + " / 10"));
        slayer_chestplate.addCustomEnchant("Aegis", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Aegis").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Aegis").getMaxLevel())));
        slayer_chestplate.addCustomEnchant("Repair Guard", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Repair Guard").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Repair Guard").getMaxLevel())));
        slayer_chestplate.addCustomEnchant("Enlighted", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Enlighted").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Enlighted").getMaxLevel())));
        slayer_chestplate.addCustomEnchant("Deathbringer", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Deathbringer").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Deathbringer").getMaxLevel())));
        slayer_chestplate.addCustomEnchant("Overload", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Overload").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Overload").getMaxLevel())));
        slayer_chestplate.addCustomEnchant("Protection", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Protection").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Protection").getMaxLevel())));
        slayer_chestplate.addCustomEnchant("Tank", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Tank").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Tank").getMaxLevel())));
        slayer_chestplate.addCustomEnchant("Dodge", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Dodge").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Dodge").getMaxLevel())));
        slayer_chestplate.addCustomEnchant("Heavy", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Heavy").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Heavy").getMaxLevel())));
        slayer_chestplate.addCustomEnchant("Armored", Math.max(1, Math.min(kitLevel - 1 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Armored").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Armored").getMaxLevel())));
        slayer_chestplate.addCustomEnchant("Ghost", Math.max(1, Math.min(kitLevel - 1 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Ghost").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Ghost").getMaxLevel())));
        if (kitLevel > 3) {
            slayer_chestplate.addCustomEnchant("Resilience", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Resilience").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Resilience").getMaxLevel())));
        }
        if (kitLevel > 5) {
            slayer_chestplate.addCustomEnchant("Arrow Deflect", Math.max(1, Math.min(kitLevel - 1 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Arrow Deflect").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Arrow Deflect").getMaxLevel())));
        }
        if (kitLevel > 8) {
            slayer_chestplate.addCustomEnchant("Guardians", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Guardians").getMaxLevel() - 5), EnchantmentAPI.getEnchantment((String)"Guardians").getMaxLevel())));
        }
        if (kitLevel >= 9 && Math.random() < 0.05 * (double)(kitLevel - 8)) {
            slayer_chestplate.addCustomEnchant("Immortal", Math.max(1, Math.min(2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Immortal").getMaxLevel()), EnchantmentAPI.getEnchantment((String)"Immortal").getMaxLevel())));
        }
        if (kitLevel == 10 && Math.random() < 0.05) {
            items.add(ItemUtils.getTitleVoucher((String)"Slayer"));
        }
        items.add(EnchantmentBook.applyTransmogScroll((ItemStack)slayer_chestplate.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchant(Enchantment.PROTECTION_FALL, 2).addEnchant(Enchantment.PROTECTION_PROJECTILE, 2).addEnchant(Enchantment.DURABILITY, 3).build()));
        EnchantedItemBuilder slayer_pickaxe = new EnchantedItemBuilder(ItemBuilder.buildItem(Material.DIAMOND_PICKAXE, ChatColor.DARK_RED.toString() + (Object)ChatColor.BOLD + "The Terraformer (" + (Object)ChatColor.RED + "LVL " + kitLevel + (Object)ChatColor.DARK_RED + (Object)ChatColor.BOLD + ")", (Object)ChatColor.DARK_RED + "A highly advanced space-age fusion tool", (Object)ChatColor.DARK_RED + "used to quickly harvest rare materials.", "", (Object)ChatColor.DARK_RED + "" + (Object)ChatColor.BOLD + "Kit Level: " + (Object)ChatColor.RED + kitLevel + " / 10"));
        slayer_pickaxe.addCustomEnchant("Fuse", 1);
        slayer_pickaxe.addCustomEnchant("Auto Smelt", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Auto Smelt").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Auto Smelt").getMaxLevel())));
        slayer_pickaxe.addCustomEnchant("Haste", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Haste").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Haste").getMaxLevel())));
        slayer_pickaxe.addCustomEnchant("Oxygenate", 1);
        slayer_pickaxe.addCustomEnchant("Telepathy", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Telepathy").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Telepathy").getMaxLevel())));
        if (kitLevel > 3) {
            slayer_pickaxe.addCustomEnchant("Skilling", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Skilling").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Skilling").getMaxLevel())));
        }
        if (kitLevel > 7) {
            slayer_pickaxe.addCustomEnchant("Obsidian Destroyer", Math.max(1, Math.min(kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Obsidian Destroyer").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Obsidian Destroyer").getMaxLevel())));
        }
        if (kitLevel == 10 && Math.random() < 0.05) {
            items.add(ItemUtils.getTitleVoucher((String)"Slayer"));
        }
        slayer_pickaxe.addEnchant(Enchantment.DIG_SPEED, 5).addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 2).addEnchant(Enchantment.DURABILITY, 3);
        if (kitLevel > 7 && Math.random() < 0.05 * (double)(kitLevel - 7)) {
            slayer_pickaxe.addEnchant(Enchantment.SILK_TOUCH, 1);
        }
        if (kitLevel > 7 && Math.random() <= 0.1 * (double)(kitLevel - 7)) {
            slayer_pickaxe.addCustomEnchant("Detonate", EnchantmentAPI.getEnchantment((String)"Detonate").getMaxLevel());
            double rand = Math.random();
            int level = rand <= 0.7 ? 1 : (rand <= 0.95 ? 2 : (rand <= 0.98 ? 3 : (rand >= 0.99 ? 4 : 2)));
            slayer_pickaxe.addCustomEnchant("Atomic Detonate", level);
        } else {
            slayer_pickaxe.addCustomEnchant("Detonate", Math.max(1, Math.min(6 + kitLevel / 2 + new Random().nextInt(EnchantmentAPI.getEnchantment((String)"Detonate").getMaxLevel() - 2), EnchantmentAPI.getEnchantment((String)"Detonate").getMaxLevel())));
        }
        items.add(EnchantmentBook.applyTransmogScroll((ItemStack)slayer_pickaxe.build()));
        ItemStack healPotion = new ItemStack(Material.POTION, 1, 16385);
        PotionMeta pm = (PotionMeta)healPotion.getItemMeta();
        pm.addCustomEffect(new PotionEffect(PotionEffectType.HEAL, 0, 2 + (kitLevel > 8 ? 1 : new Random().nextInt(2))), true);
        healPotion.setItemMeta((ItemMeta)pm);
        items.add(healPotion);
        if (kitLevel > 4) {
            items.add(healPotion);
        }
        if (kitLevel > 7) {
            items.add(healPotion);
        }
        if (kitLevel >= 7) {
            items.add(CosmicConquestAPI.createFactionToken((int)(1 + new Random().nextInt(kitLevel - 5))));
        }
        items.add(new ItemStack(Material.ENDER_PEARL, Math.min(64, 8 * kitLevel)));
        return items;
    }

    @Override
    public String getItemName() {
        return (Object)ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Slayer";
    }

    @Override
    public List<String> getLore() {
        return Lists.newArrayList((Object[])new String[]{"", (Object)ChatColor.DARK_RED + ChatColor.BOLD.toString() + "* " + (Object)ChatColor.DARK_RED + (Object)ChatColor.BOLD + "Glitched Sword", (Object)ChatColor.DARK_RED + ChatColor.BOLD.toString() + "* " + (Object)ChatColor.DARK_RED + (Object)ChatColor.BOLD + "Shroud of War", (Object)ChatColor.DARK_RED + ChatColor.BOLD.toString() + "* " + (Object)ChatColor.DARK_RED + (Object)ChatColor.BOLD + "The Terraformer", (Object)ChatColor.DARK_RED + ChatColor.BOLD.toString() + "* " + (Object)ChatColor.RED + (Object)ChatColor.BOLD + "A chance of...", (Object)ChatColor.DARK_RED + ChatColor.BOLD.toString() + "   - " + (Object)ChatColor.RED + "Custom Potions", (Object)ChatColor.DARK_RED + ChatColor.BOLD.toString() + "   - " + (Object)ChatColor.RED + "Enderpearls", (Object)ChatColor.DARK_RED + ChatColor.BOLD.toString() + "   - " + (Object)ChatColor.RED + "Faction Crystals", "", (Object)ChatColor.GRAY + "Quality over quantity: Anti-Gank.", (Object)ChatColor.GOLD + "* Back to School 2017"});
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

