/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.compilex.CosmicKOTH.utils.KothItemUtils
 *  com.cosmicpvp.cosmicutils.gui.CosmicGUI
 *  com.cosmicpvp.cosmicutils.gui.InventoryItem
 *  com.cosmicpvp.cosmicutils.itembuilder.MaskBuilder
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.ItemBuilder
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package com.compilex.CosmicArmorSets.menu;

import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.compilex.CosmicArmorSets.enums.WeaponedArmorSet;
import com.compilex.CosmicArmorSets.enums.armorsets.DragonSlayerArmorSet;
import com.compilex.CosmicArmorSets.enums.armorsets.KothArmorSet;
import com.compilex.CosmicKOTH.utils.KothItemUtils;
import com.cosmicpvp.cosmicutils.gui.CosmicGUI;
import com.cosmicpvp.cosmicutils.gui.InventoryItem;
import com.cosmicpvp.cosmicutils.itembuilder.MaskBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ArmorSetHelpMenu
        extends CosmicGUI {
    public ArmorSetHelpMenu(Player player) {
        super(player, "Armor Set Help", 54);
    }

    public void redraw() {
        int slotIndex = 10;
        for (ArmorSet armorSet : ArmorSet.values()) {
            int index = 0;
            if (armorSet.getArmorSetInfo() instanceof WeaponedArmorSet) {
                this.setItem(slotIndex + index++ * 9, new InventoryItem(((WeaponedArmorSet) armorSet.getArmorSetInfo()).getWeapon(true)));
            } else if (armorSet.getArmorSetInfo() instanceof KothArmorSet) {
                this.setItem(8 + index * 9, new InventoryItem(KothItemUtils.getKothAxe(this.player.getName())));
                this.setItem(slotIndex + index++ * 9, new InventoryItem(KothItemUtils.getKothSwordPvP(this.player.getName())));
            } else if (armorSet.getArmorSetInfo() instanceof DragonSlayerArmorSet) {
                ItemStack item = new MaskBuilder("DRAGON_MASK").build();
                if (item != null) {
                    this.setItem(slotIndex + index++ * 9, new InventoryItem(item));
                }
            } else {
                ++index;
            }
            for (ItemStack armorSetItem : armorSet.armorSetItems) {
                this.setItem(slotIndex + index++ * 9, new InventoryItem(armorSetItem));
            }
            ++slotIndex;
        }
        this.setItem(0, new InventoryItem(new ItemBuilder(Material.BOOK, CC.LightPurpleB + "Armor Sets", "", CC.Gray + "Armor Sets are custom sets of armor", CC.Gray + "that have special 'Set Bonuses'.", CC.Gray + "These bonuses are only active if", CC.Gray + "you have ALL pieces of a specific", CC.Gray + "set equipped.", "", CC.Gray + "Sample versions of all available", CC.Gray + "armor sets are currently displayed.").build()));
    }
}

