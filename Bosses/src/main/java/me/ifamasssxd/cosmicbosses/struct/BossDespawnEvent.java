/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Entity
 *  org.bukkit.event.Event
 *  org.bukkit.event.HandlerList
 */
package me.ifamasssxd.cosmicbosses.struct;

import me.ifamasssxd.cosmicbosses.struct.Boss;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BossDespawnEvent
extends Event {
    private static HandlerList handlers = new HandlerList();
    private Entity entity;
    private Boss boss;

    public BossDespawnEvent(Entity entity, Boss boss) {
        this.entity = entity;
        this.boss = boss;
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

    public Boss getBoss() {
        return this.boss;
    }
}

