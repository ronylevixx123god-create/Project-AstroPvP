/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.event.entity;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageByBlockEvent
extends EntityDamageEvent {
    private final Block damager;

    @Deprecated
    public EntityDamageByBlockEvent(Block damager, Entity damagee, EntityDamageEvent.DamageCause cause, int damage) {
        this(damager, damagee, cause, (double)damage);
    }

    public EntityDamageByBlockEvent(Block damager, Entity damagee, EntityDamageEvent.DamageCause cause, double damage) {
        super(damagee, cause, damage);
        this.damager = damager;
    }

    public Block getDamager() {
        return this.damager;
    }
}

