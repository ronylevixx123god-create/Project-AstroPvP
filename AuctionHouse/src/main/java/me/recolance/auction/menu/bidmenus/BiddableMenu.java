/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.gui.CosmicGUI
 *  org.bukkit.entity.Player
 */
package me.recolance.auction.menu.bidmenus;

import com.cosmicpvp.cosmicutils.gui.CosmicGUI;
import java.util.UUID;
import org.bukkit.entity.Player;

public abstract class BiddableMenu
extends CosmicGUI {
    public boolean forceUpdate = false;
    private UUID lastSlotClicked;
    public long lastLeftClick;
    public long lastRightClick;
    public long lastBid;

    public BiddableMenu(Player player, String title, int size) {
        super(player, title, size);
    }

    public Player getPlayer() {
        return this.player;
    }

    public String getTitle() {
        return this.title;
    }
}

