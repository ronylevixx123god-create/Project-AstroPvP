/*
 * Decompiled with CFR 0.145.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.gui.CosmicGUI
 *  com.cosmicpvp.cosmicutils.gui.InventoryItem
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.utils.CC
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.utils.ItemBuilder
 *  org.bukkit.DyeColor
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.me.astropvp.cratekeys.inventory.ItemStack
 */
package com.massivecraft.factions.struct;

import com.cosmicpvp.cosmicutils.gui.CosmicGUI;
import com.cosmicpvp.cosmicutils.gui.InventoryItem;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FUnclaimAllConfirm
        extends CosmicGUI {
    private int claims;

    public FUnclaimAllConfirm(Player player, int claims) {
        super(player, "Confirm /f unclaimall", 9);
        this.claims = claims;
    }

    public void redraw() {
        int i;
        InventoryItem item = new InventoryItem(new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) DyeColor.LIME.getWoolData())).name(CC.GreenB + "Confirm").lore(new String[]{"", CC.Gray + "Confirm unclaiming of " + CC.GreenB + this.claims + CC.Gray + " claim(s)!"}).build()).click(() -> {
            this.player.closeInventory();
            this.player.performCommand("f unclaimall confirm");
        });
        for (i = 0; i < 4; ++i) {
            this.setItem(i, item);
        }
        this.setItem(4, new ItemBuilder(Material.STAINED_GLASS_PANE).data((short) DyeColor.BLACK.getWoolData()).name(" ").build(), null);
        for (i = 5; i < this.size; ++i) {
            this.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, CC.RedB + "Cancel", "", CC.Gray + "Click to cancel unclaiming of all land.").data((short) DyeColor.RED.getWoolData()).build(), () -> {
                this.player.closeInventory();
                this.player.sendMessage(CC.RedB + "(!) " + CC.Red + "Unclaiming cancelled.");
            });
        }
    }
}

