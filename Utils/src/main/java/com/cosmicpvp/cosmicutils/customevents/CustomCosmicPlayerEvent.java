/*
 * Decompiled with CFR 0.150.
 */
package com.cosmicpvp.cosmicutils.customevents;

import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CustomCosmicPlayerEvent
extends Event
implements Cancellable {
    public boolean cancelled = false;
    private Player player;
    private UUID uuid;
    private String eventKey;
    private Object[] eventData;
    public static HandlerList list = new HandlerList();

    public CustomCosmicPlayerEvent(Player player, String eventKey, Object ... data) {
        this.player = player;
        this.eventKey = eventKey;
        this.uuid = player.getUniqueId();
        this.eventData = data;
    }

    public CustomCosmicPlayerEvent(UUID uuid, String eventKey, Object ... data) {
        this.uuid = uuid;
        this.eventKey = eventKey;
        this.eventData = data;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

    @Override
    public HandlerList getHandlers() {
        return list;
    }

    public static HandlerList getHandlerList() {
        return list;
    }

    public Player getPlayer() {
        return this.player;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public String getEventKey() {
        return this.eventKey;
    }

    public Object[] getEventData() {
        return this.eventData;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public void setEventData(Object[] eventData) {
        this.eventData = eventData;
    }
}

