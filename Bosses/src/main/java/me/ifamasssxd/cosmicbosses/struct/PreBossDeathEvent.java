/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Entity
 *  org.bukkit.event.Event
 *  org.bukkit.event.HandlerList
 *  org.bukkit.event.entity.EntityDamageEvent
 */
package me.ifamasssxd.cosmicbosses.struct;

import me.ifamasssxd.cosmicbosses.struct.Boss;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;

public class PreBossDeathEvent
extends Event {
    private static HandlerList handlers = new HandlerList();
    private Entity entity;
    private EntityDamageEvent lastDamageEvent;
    private Boss boss;

    public PreBossDeathEvent(Entity entity, Boss boss, EntityDamageEvent event) {
        this.entity = entity;
        this.boss = boss;
        this.lastDamageEvent = event;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public EntityDamageEvent getLastDamageEvent() {
        return this.lastDamageEvent;
    }

    public Boss getBoss() {
        return this.boss;
    }
}

