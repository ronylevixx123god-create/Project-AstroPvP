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
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.java.JavaPlugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.gui.CosmicGUI;
import com.cosmicpvp.cosmicutils.gui.InventoryItem;
import com.cosmicpvp.cosmicutils.struct.DecimalFormatType;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import java.util.UUID;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.CandyGram;
import org.arkhamnetwork.Arkkit.patches.CandyGramConfirmSendMenu;
import org.arkhamnetwork.Arkkit.patches.CandyGramSender;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

class CandyGramSenderSelectGUI
extends CosmicGUI {
    private CandyGram pending;
    private UUID toSend;
    private String user;
    private CosmicGUI previous;

    public CandyGramSenderSelectGUI(Player player, CandyGram pending, UUID toSend, String user, CosmicGUI previous) {
        super(player, "Select Sender Name", 9);
        this.pending = pending;
        this.toSend = toSend;
        this.user = user;
        this.previous = previous;
    }

    public void redraw() {
        int slot = 0;
        for (CandyGramSender sender : CandyGramSender.values()) {
            String name = sender == CandyGramSender.YOU ? "YOU (" + this.player.getName() + ")" : sender.getName();
            this.setItem(slot++, new InventoryItem(new ItemBuilder(Material.PAPER).name(CC.GreenB + name).lore(new String[]{"", "", CC.GoldB + "COST: " + (sender.getCost() <= 0 ? CC.Green + "FREE" : "$" + CC.Gold + NumberUtils.format((double)sender.getCost(), (DecimalFormatType)DecimalFormatType.MONEY)), "", CC.Gray + "Click to select this sender name!"}).build()).click(() -> {
                this.pending.setSender(sender == CandyGramSender.YOU ? this.player.getName() : name);
                new CandyGramConfirmSendMenu(this.player, this.toSend, this.user, this.pending, this).openGUI(Arkkit.get());
            }));
            this.setItem(this.size - 1, new InventoryItem(new ItemBuilder(Material.ARROW).name(CC.GreenB + "Previous Menu").lore(new String[]{CC.Gray + "Click to return to item page!"}).build()).click(() -> this.previous.openGUI((JavaPlugin)Arkkit.get())));
        }
    }
}

