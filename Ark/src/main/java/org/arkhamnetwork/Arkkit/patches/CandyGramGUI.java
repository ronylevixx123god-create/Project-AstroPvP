/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.gui.CosmicGUI
 *  com.cosmicpvp.cosmicutils.gui.InventoryItem
 *  com.cosmicpvp.cosmicutils.struct.DecimalFormatType
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.ItemBuilder
 *  com.cosmicpvp.cosmicutils.utils.NumberUtils
 *  org.bukkit.DyeColor
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.gui.CosmicGUI;
import com.cosmicpvp.cosmicutils.gui.InventoryItem;
import com.cosmicpvp.cosmicutils.struct.DecimalFormatType;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.CandyGramItem;
import org.arkhamnetwork.Arkkit.patches.CandyGramPlayerSelectGUI;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

class CandyGramGUI
extends CosmicGUI {
    public CandyGramGUI(Player player) {
        super(player, "Send a Candy Gram", 45);
    }

    public void redraw() {
        for (CandyGramItem item : CandyGramItem.values()) {
            this.setItem(item.getSlot(), new InventoryItem(new ItemBuilder(item.getMaterial().getItemType()).data((short)item.getMaterial().getData()).name(item.getName()).lore(new String[]{"", CC.GoldB + "COST: $" + CC.Gold + NumberUtils.format((double)item.getCost(), (DecimalFormatType)DecimalFormatType.MONEY), "", CC.Gray + "Click to choose which player", CC.Gray + "should receive this Candy Gram!"}).build()).click(() -> new CandyGramPlayerSelectGUI(this.player, item, this).openGUI(Arkkit.get())));
        }
        for (int i = 0; i < this.size; ++i) {
            ItemStack item = this.inventory.getItem(i);
            if (item != null && item.getType() != Material.AIR) continue;
            this.setItem(i, new InventoryItem(new ItemBuilder(Material.STAINED_GLASS_PANE, " ", new String[0]).data((short)DyeColor.GRAY.getWoolData()).build()));
        }
    }
}

