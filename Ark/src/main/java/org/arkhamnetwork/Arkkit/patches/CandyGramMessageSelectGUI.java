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
 *  com.google.common.collect.Lists
 *  org.bukkit.ChatColor
 *  org.bukkit.DyeColor
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.java.JavaPlugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.gui.CosmicGUI;
import com.cosmicpvp.cosmicutils.gui.InventoryItem;
import com.cosmicpvp.cosmicutils.struct.DecimalFormatType;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

class CandyGramMessageSelectGUI
extends CosmicGUI {
    private UUID uuid;
    private String receiverName;
    private CandyGramItem itemToSend;
    private CosmicGUI previous;

    public CandyGramMessageSelectGUI(Player player, UUID chose, String receiver, CandyGramItem item, CosmicGUI previous) {
        super(player, "Candy Gram Messages", 54);
        this.uuid = chose;
        this.itemToSend = item;
        this.receiverName = receiver;
        this.previous = previous;
    }

    public void redraw() {
        for (CandyGramMessages message : CandyGramMessages.values()) {
            ArrayList lore = Lists.newArrayList();
            if (message.getDisplayName() != null) {
                lore.add("");
                lore.add(CC.LightPurpleB + "Message:");
                message.getMessages().forEach(msg -> lore.add(CC.Gray + " " + ChatColor.translateAlternateColorCodes((char)'&', (String)msg)));
            }
            lore.add("");
            lore.add(CC.GoldB + "COST: $" + CC.Gold + NumberUtils.format((double)message.getCost(), (DecimalFormatType)DecimalFormatType.MONEY));
            lore.add("");
            lore.add(CC.Gray + "Click to select this message");
            lore.add(CC.Gray + "for a Candy Gram to " + this.receiverName + "!");
            this.setItem(message.getSlot(), new InventoryItem(new ItemBuilder(Material.PAPER).name(CC.GreenB + (message.getDisplayName() == null ? message.getMessages().get(0) : message.getDisplayName())).lore((List)lore).build()).click(() -> {
                CandyGram gram = new CandyGram(this.itemToSend, message, this.player.getName());
                new CandyGramSenderSelectGUI(this.player, gram, this.uuid, this.receiverName, this).openGUI(Arkkit.get());
            }));
        }
        this.setItem(this.size - 1, new InventoryItem(new ItemBuilder(Material.ARROW).name(CC.GreenB + "Previous Menu").lore(new String[]{CC.Gray + "Click to return to item page!"}).build()).click(() -> this.previous.openGUI((JavaPlugin)Arkkit.get())));
        for (int i = 0; i < this.size; ++i) {
            ItemStack item = this.inventory.getItem(i);
            if (item != null && item.getType() != Material.AIR) continue;
            this.setItem(i, new InventoryItem(new ItemBuilder(Material.STAINED_GLASS_PANE, " ", new String[0]).data((short)DyeColor.GRAY.getWoolData()).build()));
        }
    }
}

