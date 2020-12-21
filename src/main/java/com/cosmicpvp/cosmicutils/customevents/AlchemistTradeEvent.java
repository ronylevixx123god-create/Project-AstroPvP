/*
 * Decompiled with CFR 0.150.
 */
package com.cosmicpvp.cosmicutils.customevents;

import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class AlchemistTradeEvent
extends Event {
    public Player player;
    public List<ItemStack> tradedItems;
    public List<ItemStack> willReceive;
    public static HandlerList list = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return list;
    }

    public static HandlerList getHandlerList() {
        return list;
    }

    public AlchemistTradeEvent(Player player, List<ItemStack> tradedItems, List<ItemStack> willReceive) {
        this.player = player;
        this.tradedItems = tradedItems;
        this.willReceive = willReceive;
    }
}

