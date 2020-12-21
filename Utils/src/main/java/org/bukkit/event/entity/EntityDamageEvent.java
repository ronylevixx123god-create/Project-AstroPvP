/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.util.NumberConversions;

public class EntityDamageEvent
extends EntityEvent
implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private double damage;
    private boolean cancelled;
    private final DamageCause cause;

    @Deprecated
    public EntityDamageEvent(Entity damagee, DamageCause cause, int damage) {
        this(damagee, cause, (double)damage);
    }

    public EntityDamageEvent(Entity damagee, DamageCause cause, double damage) {
        super(damagee);
        this.cause = cause;
        this.damage = damage;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    public double getDamage() {
        return this.damage;
    }

    @Deprecated
    public int _INVALID_getDamage() {
        return NumberConversions.ceil(this.getDamage());
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    @Deprecated
    public void _INVALID_setDamage(int damage) {
        this.setDamage(damage);
    }

    public DamageCause getCause() {
        return this.cause;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public static enum DamageCause {
        CONTACT,
        ENTITY_ATTACK,
        PROJECTILE,
        SUFFOCATION,
        FALL,
        FIRE,
        FIRE_TICK,
        MELTING,
        LAVA,
        DROWNING,
        BLOCK_EXPLOSION,
        ENTITY_EXPLOSION,
        VOID,
        LIGHTNING,
        SUICIDE,
        STARVATION,
        POISON,
        MAGIC,
        WITHER,
        FALLING_BLOCK,
        THORNS,
        CUSTOM;

    }
}

