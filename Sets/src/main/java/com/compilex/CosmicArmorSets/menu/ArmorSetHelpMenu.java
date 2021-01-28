package com.compilex.CosmicArmorSets.menu;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.compilex.CosmicArmorSets.enums.WeaponedArmorSet;
import com.compilex.CosmicKOTH.utils.KothItemUtils;
import com.cosmicpvp.cosmicutils.gui.CosmicGUI;
import com.cosmicpvp.cosmicutils.gui.InventoryItem;
import com.cosmicpvp.cosmicutils.itembuilder.MaskBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ArmorSetHelpMenu extends CosmicGUI {
    public ArmorSetHelpMenu(Player player) {
        super(player, "Armor Set Help", 54);
    }

    public void redraw() {
        int slotIndex = 1;
        setItem(0,
                new InventoryItem((new ItemBuilder(Material.BOOK, String.valueOf(String.valueOf(CC.LightPurpleB)) + "Armor Sets",
                        new String[] { "", String.valueOf(String.valueOf(CC.Gray)) + "Armor Sets are custom sets of armor",
                                String.valueOf(String.valueOf(CC.Gray)) + "that have special 'Set Bonuses'.",
                                String.valueOf(String.valueOf(CC.Gray)) + "These bonuses are only active if",
                                String.valueOf(String.valueOf(CC.Gray)) + "you have ALL pieces of a specific",
                                String.valueOf(String.valueOf(CC.Gray)) + "set equipped.", "",
                                String.valueOf(String.valueOf(CC.Gray)) + "Sample versions of all available",
                                String.valueOf(String.valueOf(CC.Gray)) + "armor sets are currently displayed." })).build()));
    }
}
