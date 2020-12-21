/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.event.player;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerTeleportEvent
extends PlayerMoveEvent {
    private static final HandlerList handlers = new HandlerList();
    private TeleportCause cause = TeleportCause.UNKNOWN;

    public PlayerTeleportEvent(Player player, Location from, Location to) {
        super(player, from, to);
    }

    public PlayerTeleportEvent(Player player, Location from, Location to, TeleportCause cause) {
        this(player, from, to);
        this.cause = cause;
    }

    public TeleportCause getCause() {
        return this.cause;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public static enum TeleportCause {
        ENDER_PEARL,
        COMMAND,
        PLUGIN,
        NETHER_PORTAL,
        END_PORTAL,
        UNKNOWN;

    }
}

