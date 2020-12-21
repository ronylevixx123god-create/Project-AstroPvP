/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.event.entity;

import org.bukkit.entity.Projectile;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityEvent;

public class ProjectileHitEvent
extends EntityEvent {
    private static final HandlerList handlers = new HandlerList();

    public ProjectileHitEvent(Projectile projectile) {
        super(projectile);
    }

    @Override
    public Projectile getEntity() {
        return (Projectile)this.entity;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}

