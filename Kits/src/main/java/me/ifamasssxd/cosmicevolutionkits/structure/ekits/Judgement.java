/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.arkhamnetwork.Arkkit.patches.ExpBottles
 *  org.arkhamnetwork.Arkkit.patches.McmmoCredits
 *  org.arkhamnetwork.enchantments.EnchantmentBook
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package me.ifamasssxd.cosmicevolutionkits.structure.ekits;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKit;
import me.ifamasssxd.cosmicevolutionkits.utils.EnchantedItemBuilder;
import me.ifamasssxd.cosmicevolutionkits.utils.ItemBuilder;
import org.arkhamnetwork.Arkkit.patches.ExpBottles;
import org.arkhamnetwork.Arkkit.patches.McmmoCredits;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Judgement
extends EvolutionKit {
    private ChatColor mainColor = ChatColor.DARK_GRAY;

    private int getRandomEnchantmentLevel(String name, int kitLevel) {
        int maxLevel = this.getMaxLevel(name);
        if (maxLevel == 1) {
            return 1;
        }
        return Math.max(1, Math.min(kitLevel / 2 + this.random.nextInt(maxLevel - 2), maxLevel));
    }

    @Override
    public List<ItemStack> getItems(Player player, int kitLevel) {
        ArrayList items = Lists.newArrayList();
        EnchantedItemBuilder banhammer_axe = new EnchantedItemBuilder(ItemBuilder.buildItem(Material.DIAMOND_AXE, (Object)this.mainColor + ChatColor.BOLD.toString() + "The Banhammer (" + (Object)ChatColor.RED + "LVL " + kitLevel + (Object)this.mainColor + (Object)ChatColor.BOLD + ")", (Object)this.mainColor + "For eons thought to be nothing more than Cosmonaut folklore,", (Object)this.mainColor + "the very weapon used by admins to banish criminals", (Object)this.mainColor + "to the furthest reaches of the galaxies.", "", (Object)this.mainColor + "" + (Object)ChatColor.BOLD + "Kit Level: " + (Object)ChatColor.RED + kitLevel + " / 10"));
        double d = (double)kitLevel * 0.025;
        banhammer_axe.addCustomEnchant("Rage", Math.max(1, Math.min(kitLevel / 2 + this.random.nextInt(this.getMaxLevel("Rage") - 2), this.getMaxLevel("Rage"))));
        banhammer_axe.addCustomEnchant("Devour", this.getRandomEnchantmentLevel("Devour", kitLevel));
        banhammer_axe.addCustomEnchant("Bleed", this.getRandomEnchantmentLevel("Bleed", kitLevel));
        banhammer_axe.addCustomEnchant("Cleave", this.getRandomEnchantmentLevel("Cleave", kitLevel));
        banhammer_axe.addCustomEnchant("Blessed", this.getRandomEnchantmentLevel("Blessed", kitLevel));
        if (kitLevel >= 5) {
            int maxLevel = kitLevel == 10 ? this.getMaxLevel("Insanity") - this.random.nextInt(3) : this.getMaxLevel("Insanity") - 2;
            banhammer_axe.addCustomEnchant("Insanity", Math.max(1, Math.min(kitLevel / 2 + this.random.nextInt(maxLevel) - 2, maxLevel)));
        }
        if (kitLevel >= 6 && this.random.nextDouble() <= 0.3 + (double)(kitLevel - 4) * 0.06) {
            banhammer_axe.addCustomEnchant("Soul Trap", kitLevel == 10 ? this.random.nextInt(2) + 1 : 1);
        }
        if (kitLevel >= 7 && this.random.nextDouble() <= 0.35 + (double)(kitLevel - 4) * 0.05) {
            banhammer_axe.addCustomEnchant("Anti Gank", this.getRandomEnchantmentLevel("Anti Gank", kitLevel));
        }
        if (kitLevel >= 7 && this.random.nextDouble() <= Math.min(0.7, 0.2 + (double)(kitLevel - 4) * 0.06)) {
            banhammer_axe.addCustomEnchant("Blacksmith", this.getMaxLevel("Blacksmith"));
            banhammer_axe.addCustomEnchant("Master Blacksmith", this.getRandomEnchantmentLevel("Master Blacksmith", kitLevel));
        } else {
            banhammer_axe.addCustomEnchant("Blacksmith", this.getRandomEnchantmentLevel("Blacksmith", kitLevel));
        }
        if (kitLevel >= 8 && this.random.nextDouble() <= 0.35 + (double)(kitLevel - 4) * 0.05) {
            banhammer_axe.addCustomEnchant("Silence", this.getRandomEnchantmentLevel("Silence", kitLevel));
        }
        if (kitLevel >= 8) {
            banhammer_axe.addCustomEnchant("Lifesteal", this.getRandomEnchantmentLevel("Lifesteal", kitLevel));
        }
        banhammer_axe.addCustomEnchant("Pummel", this.getRandomEnchantmentLevel("Pummel", kitLevel));
        banhammer_axe.addCustomEnchant("Arrow Break", this.getRandomEnchantmentLevel("Arrow Break", kitLevel));
        banhammer_axe.addCustomEnchant("Ravenous", this.getRandomEnchantmentLevel("Ravenous", kitLevel));
        items.add(EnchantmentBook.applyTransmogScroll((ItemStack)banhammer_axe.addEnchant(Enchantment.DAMAGE_ALL, 5).addEnchant(Enchantment.FIRE_ASPECT, 2).addEnchant(Enchantment.DURABILITY, 4).build()));
        EnchantedItemBuilder judgment_helmet = new EnchantedItemBuilder(ItemBuilder.buildItem(Material.DIAMOND_CHESTPLATE, (Object)this.mainColor + ChatColor.BOLD.toString() + "Divine Robes of Judgement (" + (Object)ChatColor.RED + "LVL " + kitLevel + (Object)this.mainColor + (Object)ChatColor.BOLD + ")", (Object)this.mainColor + "A firey shroud that echoes absolute", (Object)this.mainColor + "power and domination. No mere human", (Object)this.mainColor + "would dare stand against its owner.", "", (Object)this.mainColor + "" + (Object)ChatColor.BOLD + "Kit Level: " + (Object)ChatColor.RED + kitLevel + " / 10"));
        judgment_helmet.addCustomEnchant("Overload", this.getRandomEnchantmentLevel("Overload", kitLevel));
        judgment_helmet.addCustomEnchant("Enlighted", this.getRandomEnchantmentLevel("Enlighted", kitLevel));
        if (this.random.nextDouble() <= 0.03 + (double)kitLevel * 0.03 && kitLevel >= 6) {
            judgment_helmet.addCustomEnchant("Deathbringer", this.getMaxLevel("Deathbringer"));
            judgment_helmet.addCustomEnchant("Planetary Deathbringer", this.getRandomEnchantmentLevel("Planetary Deathbringer", kitLevel));
        } else {
            judgment_helmet.addCustomEnchant("Deathbringer", this.getRandomEnchantmentLevel("Deathbringer", kitLevel));
        }
        if (kitLevel >= 6) {
            judgment_helmet.addCustomEnchant("Armored", this.getRandomEnchantmentLevel("Armored", kitLevel));
        }
        if (kitLevel >= 8) {
            int maxLevel = kitLevel == 10 ? this.getMaxLevel("Leadership") - this.random.nextInt(3) : this.getMaxLevel("Leadership") - 2;
            judgment_helmet.addCustomEnchant("Leadership", Math.max(1, Math.min(kitLevel / 2 + this.random.nextInt(maxLevel) - 2, maxLevel)));
        }
        if (this.random.nextDouble() <= 0.06 + (double)(kitLevel - 3) * 0.076 && kitLevel >= 6) {
            judgment_helmet.addCustomEnchant("Diminish", this.getMaxLevel("Diminish"));
            judgment_helmet.addCustomEnchant("Vengeful Diminish", this.getRandomEnchantmentLevel("Vengeful Diminish", kitLevel));
        } else {
            judgment_helmet.addCustomEnchant("Diminish", this.getRandomEnchantmentLevel("Diminish", kitLevel));
        }
        judgment_helmet.addCustomEnchant("Marksman", this.getRandomEnchantmentLevel("Marksman", kitLevel));
        int angelicMaxLevel = kitLevel == 10 ? this.getMaxLevel("Angelic") - this.random.nextInt(3) : this.getMaxLevel("Angelic") - 2;
        judgment_helmet.addCustomEnchant("Angelic", Math.max(1, Math.min(kitLevel / 2 + this.random.nextInt(angelicMaxLevel) - 2, angelicMaxLevel)));
        int voodMaxLevel = kitLevel == 10 ? this.getMaxLevel("Voodoo") - this.random.nextInt(3) : this.getMaxLevel("Voodoo") - 2;
        judgment_helmet.addCustomEnchant("Voodoo", Math.max(1, Math.min(kitLevel / 2 + this.random.nextInt(voodMaxLevel) - 2, voodMaxLevel)));
        int luckyMaxLevel = kitLevel == 10 ? this.getMaxLevel("Lucky") - this.random.nextInt(4) : this.getMaxLevel("Lucky") - 3;
        judgment_helmet.addCustomEnchant("Lucky", Math.max(1, Math.min(kitLevel / 2 + this.random.nextInt(luckyMaxLevel) - 2, luckyMaxLevel)));
        judgment_helmet.addCustomEnchant("Obsidianshield", this.getRandomEnchantmentLevel("Obsidianshield", kitLevel));
        if (kitLevel >= 4) {
            judgment_helmet.addCustomEnchant("Heavy", this.getRandomEnchantmentLevel("Heavy", kitLevel));
        }
        judgment_helmet.addCustomEnchant("Creeper Armor", this.getRandomEnchantmentLevel("Creeper Armor", kitLevel));
        int destructionMaxLevel = kitLevel == 10 ? this.getMaxLevel("Destruction") - this.random.nextInt(3) : this.getMaxLevel("Destruction") - 2;
        judgment_helmet.addCustomEnchant("Destruction", Math.max(1, Math.min(kitLevel / 2 + this.random.nextInt(destructionMaxLevel) - 2, destructionMaxLevel)));
        items.add(EnchantmentBook.applyTransmogScroll((ItemStack)judgment_helmet.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchant(Enchantment.PROTECTION_PROJECTILE, 2).addEnchant(Enchantment.DURABILITY, 3).build()));
        EnchantedItemBuilder karma_boots = new EnchantedItemBuilder(ItemBuilder.buildItem(Material.DIAMOND_BOOTS, this.mainColor.toString() + (Object)ChatColor.BOLD + "Boots of Karma (" + (Object)ChatColor.RED + "LVL " + kitLevel + (Object)this.mainColor + (Object)ChatColor.BOLD + ")", (Object)this.mainColor + "Both acts of great good and great evil", (Object)this.mainColor + "have been committed in these boots.", (Object)this.mainColor + "What type of acts will you commit?", "", (Object)this.mainColor + "" + (Object)ChatColor.BOLD + "Kit Level: " + (Object)ChatColor.RED + kitLevel + " / 10"));
        karma_boots.addCustomEnchant("Gears", this.getRandomEnchantmentLevel("Gears", kitLevel));
        karma_boots.addCustomEnchant("Enlighted", this.getRandomEnchantmentLevel("Enlighted", kitLevel));
        karma_boots.addCustomEnchant("Deathbringer", this.getRandomEnchantmentLevel("Deathbringer", kitLevel));
        int springMaxLevel = kitLevel == 10 ? this.getMaxLevel("Springs") - this.random.nextInt(2) : this.getMaxLevel("Springs") - 2;
        karma_boots.addCustomEnchant("Springs", Math.max(1, Math.min(kitLevel / 2 + this.random.nextInt(springMaxLevel) - 2, springMaxLevel)));
        karma_boots.addCustomEnchant("Marksman", this.getRandomEnchantmentLevel("Marksman", kitLevel));
        karma_boots.addCustomEnchant("Stormcaller", this.getRandomEnchantmentLevel("Stormcaller", kitLevel));
        karma_boots.addCustomEnchant("Hardened", this.getRandomEnchantmentLevel("Hardened", kitLevel));
        if (kitLevel >= 4) {
            karma_boots.addCustomEnchant("Angelic", Math.max(1, Math.min(kitLevel / 2 + this.random.nextInt(angelicMaxLevel) - 2, angelicMaxLevel)));
            int heavyMaxLevel = kitLevel == 10 ? this.getMaxLevel("Heavy") - this.random.nextInt(2) : this.getMaxLevel("Heavy") - 2;
            karma_boots.addCustomEnchant("Heavy", Math.max(1, Math.min(kitLevel / 2 + this.random.nextInt(heavyMaxLevel) - 2, heavyMaxLevel)));
        }
        if (kitLevel >= 6) {
            int enderwalkerMax = kitLevel == 10 ? this.getMaxLevel("Ender Walker") - this.random.nextInt(2) : this.getMaxLevel("Ender Walker") - 2;
            karma_boots.addCustomEnchant("Ender Walker", Math.max(1, Math.min(kitLevel / 2 + (this.random.nextInt(enderwalkerMax) - 2), enderwalkerMax)));
        }
        if (kitLevel >= 9) {
            karma_boots.addCustomEnchant("Rocket Escape", this.getRandomEnchantmentLevel("Rocket Escape", kitLevel));
        }
        karma_boots.addCustomEnchant("Tank", this.getRandomEnchantmentLevel("Tank", kitLevel));
        if (this.random.nextDouble() <= 0.7 - (double)kitLevel * 0.045 || kitLevel < 4) {
            karma_boots.addCustomEnchant("Resilience", this.getRandomEnchantmentLevel("Resilience", kitLevel));
        } else {
            karma_boots.addCustomEnchant("Phoenix", this.getRandomEnchantmentLevel("Phoenix", kitLevel));
        }
        karma_boots.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchant(Enchantment.PROTECTION_FALL, 2).addEnchant(Enchantment.DURABILITY, 3);
        items.add(EnchantmentBook.applyTransmogScroll((ItemStack)karma_boots.build()));
        items.add(McmmoCredits.buildMcmmoCreditItem((int)(5 * kitLevel)));
        items.add(ExpBottles.getXpBottle((int)(kitLevel == 1 ? 25000 : (kitLevel == 2 ? 50000 : (kitLevel == 3 ? 100000 : (kitLevel == 4 ? 200000 : (kitLevel == 5 ? 350000 : (kitLevel == 6 ? 500000 : (kitLevel == 7 ? 750000 : (kitLevel == 8 ? 1000000 : (kitLevel == 9 ? 1500000 : (kitLevel == 10 ? 2000000 : 25000)))))))))), (String)"Judgment VKit"));
        return items;
    }

    @Override
    public String getItemName() {
        return (Object)ChatColor.DARK_GRAY + ChatColor.BOLD.toString() + "Judgement";
    }

    @Override
    public List<String> getLore() {
        return Lists.newArrayList("", (Object)this.mainColor + ChatColor.BOLD.toString() + "* " + (Object)this.mainColor + (Object)ChatColor.BOLD + "The Banhammer", (Object)this.mainColor + ChatColor.BOLD.toString() + "* " + (Object)this.mainColor + (Object)ChatColor.BOLD + "Divine Robes of Judgement", (Object)this.mainColor + ChatColor.BOLD.toString() + "* " + (Object)this.mainColor + (Object)ChatColor.BOLD + "Boots of Karma", (Object)this.mainColor + ChatColor.BOLD.toString() + "* " + (Object)this.mainColor + (Object)ChatColor.BOLD + "mcMMO Credits", (Object)this.mainColor + ChatColor.BOLD.toString() + "* " + (Object)this.mainColor + (Object)ChatColor.BOLD + "EXP", "", (Object)ChatColor.GRAY + "Will you use this kit for good, or evil? ", (Object)ChatColor.GOLD + "* Spring 2018");
    }

    @Override
    public Material getDisplayMaterial() {
        return Material.DIAMOND_AXE;
    }

    @Override
    public short getDisplayData() {
        return 0;
    }

    @Override
    public int getInventorySlot() {
        return 5;
    }
}

