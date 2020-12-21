/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.event.entity;

import org.bukkit.Location;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityEvent;

public class CreatureSpawnEvent
extends EntityEvent
implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean canceled;
    private final SpawnReason spawnReason;

    public CreatureSpawnEvent(LivingEntity spawnee, SpawnReason spawnReason) {
        super(spawnee);
        this.spawnReason = spawnReason;
    }

    @Deprecated
    public CreatureSpawnEvent(Entity spawnee, CreatureType type, Location loc, SpawnReason reason) {
        super(spawnee);
        this.spawnReason = reason;
    }

    @Override
    public boolean isCancelled() {
        return this.canceled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.canceled = cancel;
    }

    @Override
    public LivingEntity getEntity() {
        return (LivingEntity)this.entity;
    }

    public Location getLocation() {
        return this.getEntity().getLocation();
    }

    @Deprecated
    public CreatureType getCreatureType() {
        return CreatureType.fromEntityType(this.getEntityType());
    }

    public SpawnReason getSpawnReason() {
        return this.spawnReason;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public static enum SpawnReason {
        NATURAL,
        JOCKEY,
        CHUNK_GEN,
        SPAWNER,
        EGG,
        SPAWNER_EGG,
        LIGHTNING,
        BED,
        BUILD_SNOWMAN,
        BUILD_IRONGOLEM,
        BUILD_WITHER,
        VILLAGE_DEFENSE,
        VILLAGE_INVASION,
        BREEDING,
        SLIME_SPLIT,
        REINFORCEMENTS,
        NETHER_PORTAL,
        DISPENSE_EGG,
        CUSTOM,
        DEFAULT;

    }
}

