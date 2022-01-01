package com.compilex.CosmicArmorSets.menu;

import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.cosmicpvp.cosmicutils.gui.CosmicGUI;
import com.cosmicpvp.cosmicutils.gui.InventoryItem;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.rit.sucy.EnchantmentAPI;
import org.arkhamnetwork.enchantments.Enchantments;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ArmorSetHelpMenu extends CosmicGUI {
    public ArmorSetHelpMenu(Player player) {
        super(player, "Armor Set Help", 27);
    }

    public void redraw() {
        int slotIndex = 1;
        setItem(26,
                new InventoryItem((new ItemBuilder(Material.BOOK, String.valueOf(String.valueOf(CC.LightPurpleB)) + "Armor Sets",
                        "", String.valueOf(String.valueOf(CC.Gray)) + "Armor Sets are custom sets of armor",
                        String.valueOf(String.valueOf(CC.Gray)) + "that have special 'Set Bonuses'.",
                        String.valueOf(String.valueOf(CC.Gray)) + "These bonuses are only active if",
                        String.valueOf(String.valueOf(CC.Gray)) + "you have ALL pieces of a specific",
                        String.valueOf(String.valueOf(CC.Gray)) + "set equipped.", "",
                        String.valueOf(String.valueOf(CC.Gray)) + "Sample versions of all available",
                        String.valueOf(String.valueOf(CC.Gray)) + "armor sets are currently displayed.")).build()));

        setItem(0, new InventoryItem(EnchantmentAPI.removeEnchantments(ArmorSet.PHANTOM.getArmorSetInfo().getHelmet(true))));
        setItem(1, new InventoryItem(EnchantmentAPI.removeEnchantments(ArmorSet.YETI.getArmorSetInfo().getHelmet(true))));
        setItem(2, new InventoryItem(EnchantmentAPI.removeEnchantments(ArmorSet.MOTHER_OF_YIJKI.getArmorSetInfo().getHelmet(true))));
        setItem(3, new InventoryItem(EnchantmentAPI.removeEnchantments(ArmorSet.RANGER.getArmorSetInfo().getHelmet(true))));
        setItem(4, new InventoryItem(EnchantmentAPI.removeEnchantments(ArmorSet.KOTH.getArmorSetInfo().getHelmet(true))));
        setItem(5, new InventoryItem(EnchantmentAPI.removeEnchantments(ArmorSet.ENGINEER.getArmorSetInfo().getHelmet(true))));
        setItem(6, new InventoryItem(EnchantmentAPI.removeEnchantments(ArmorSet.SUPREME.getArmorSetInfo().getHelmet(true))));
        setItem(7, new InventoryItem(EnchantmentAPI.removeEnchantments(ArmorSet.DRAGON_SLAYER.getArmorSetInfo().getHelmet(true))));
        setItem(8, new InventoryItem(EnchantmentAPI.removeEnchantments(ArmorSet.DIMENSIONAL_TRAVELER.getArmorSetInfo().getHelmet(true))));
        setItem(9, new InventoryItem(EnchantmentAPI.removeEnchantments(ArmorSet.GRINDER.getArmorSetInfo().getHelmet(true))));
        setItem(10, new InventoryItem(EnchantmentAPI.removeEnchantments(ArmorSet.ANCIENT.getArmorSetInfo().getHelmet(true))));
        setItem(11, new InventoryItem(EnchantmentAPI.removeEnchantments(ArmorSet.ASTRO.getArmorSetInfo().getHelmet(true))));
        //setItem(15, new InventoryItem(EnchantmentAPI.removeEnchantments(ArmorSet.NEBULA.getArmorSetInfo().getHelmet(true))));
        //setItem(16, new InventoryItem(EnchantmentAPI.removeEnchantments(ArmorSet.GOBLIN.getArmorSetInfo().getHelmet(true))));




    }

}
