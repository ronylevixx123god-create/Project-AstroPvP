/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageByEntityEvent
extends EntityDamageEvent {
    private final Entity damager;

    @Deprecated
    public EntityDamageByEntityEvent(Entity damager, Entity damagee, EntityDamageEvent.DamageCause cause, int damage) {
        this(damager, damagee, cause, (double)damage);
    }

    public EntityDamageByEntityEvent(Entity damager, Entity damagee, EntityDamageEvent.DamageCause cause, double damage) {
        super(damagee, cause, damage);
        this.damager = damager;
    }

    public Entity getDamager() {
        return this.damager;
    }
}

