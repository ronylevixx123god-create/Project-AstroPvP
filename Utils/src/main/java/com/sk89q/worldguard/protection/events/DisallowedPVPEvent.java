/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.worldguard.protection.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DisallowedPVPEvent
extends Event
implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled = false;
    private final Player attacker;
    private final Player defender;
    private final EntityDamageByEntityEvent event;

    public DisallowedPVPEvent(Player attacker, Player defender, EntityDamageByEntityEvent event) {
        this.attacker = attacker;
        this.defender = defender;
        this.event = event;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public Player getAttacker() {
        return this.attacker;
    }

    public Player getDefender() {
        return this.defender;
    }

    public EntityDamageByEntityEvent getCause() {
        return this.event;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}

