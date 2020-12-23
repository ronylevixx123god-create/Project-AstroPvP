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
 *  org.bukkit.ChatColor
 *  org.bukkit.DyeColor
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.entity.Player
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.java.JavaPlugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.gui.CosmicGUI;
import com.cosmicpvp.cosmicutils.gui.InventoryItem;
import com.cosmicpvp.cosmicutils.struct.DecimalFormatType;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

class CandyGramConfirmSendMenu
        extends CosmicGUI {
    private final CandyGram pending;
    private final UUID toSend;
    private final String user;
    private final CosmicGUI previous;
    private boolean finished = false;

    public CandyGramConfirmSendMenu(Player player, UUID toSend, String name, CandyGram gram, CosmicGUI previous) {
        super(player, "Confirm Candy Gram", 9);
        this.toSend = toSend;
        this.user = name;
        this.pending = gram;
        this.previous = previous;
    }

    public void onInventoryClose() {
        super.onInventoryClose();
        if (!this.finished) {
            this.finished = true;
            this.player.sendMessage(CC.Red + "Candy Gram sending cancelled!");
        }
    }

    public void redraw() {
        int i;
        InventoryItem confirm = new InventoryItem(new ItemBuilder(Material.STAINED_GLASS_PANE, CC.GreenB + "Confirm Candy Gram", "", CC.Gray + "Click to confirm that you want to", CC.Gray + "send " + CC.Green + this.user + CC.Gray + " a candy gram!", "", CC.GreenB + "COST: $" + CC.Green + NumberUtils.format(this.pending.getTotalCost(), DecimalFormatType.MONEY) + CC.Gray + "!").data(DyeColor.LIME.getWoolData()).build()).click(() -> {
            if (!Arkkit.economy.has(this.player, this.pending.getTotalCost())) {
                this.player.sendMessage(CC.Red + "You cannot afford to send that Candy Gram!");
                return;
            }
            if (this.player.hasMetadata("candyGramCooldown")) {
                long l = this.player.getMetadata("candyGramCooldown").get(0).asLong();
            }
            Arkkit.economy.withdrawPlayer(this.player, this.pending.getTotalCost());
            this.finished = true;
            this.player.closeInventory();
            this.player.sendMessage(CC.RedB + "- $" + CC.Red + NumberUtils.format(this.pending.getTotalCost(), DecimalFormatType.MONEY));
            ((ValentinesDay) ArkPlugins.VALENTINES_DAY.getArkPlugin()).giveCandyGram(this.toSend, this.pending);
            this.player.sendMessage("");
            this.player.sendMessage(CC.GreenB + "(!) " + CC.Green + "Candy Gram sent to " + this.user + "!");
            this.player.sendMessage(CC.Gray + "  Candy Gram Message: " + (this.pending.getMessage().getDisplayName() == null ? CC.Green + this.pending.getMessage().getMessages().get(0) : ""));
            if (this.pending.getMessage().getDisplayName() != null) {
                this.pending.getMessage().getMessages().forEach(msg -> this.player.sendMessage(CC.Gray + " " + ChatColor.translateAlternateColorCodes('&', msg)));
            }
        });
        for (i = 0; i < 5; ++i) {
            this.setItem(i, confirm);
        }
        this.setItem(4, new InventoryItem(this.pending.getItemStack()));
        for (i = 5; i < 9; ++i) {
            this.setItem(i, new InventoryItem(new ItemBuilder(Material.STAINED_GLASS_PANE, CC.RedB + "Cancel", "", CC.Gray + "Click to cancel and return", CC.Gray + "to your previous menu.").data(DyeColor.RED.getWoolData()).build()).click(() -> this.previous.openGUI(Arkkit.get())));
        }
    }
}

