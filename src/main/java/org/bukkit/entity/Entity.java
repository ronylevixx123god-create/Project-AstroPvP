/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.entity;

import java.util.List;
import java.util.UUID;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.metadata.Metadatable;
import org.bukkit.util.Vector;

public interface Entity
extends Metadatable {
    public Location getLocation();

    public Location getLocation(Location var1);

    public void setVelocity(Vector var1);

    public Vector getVelocity();

    public boolean isOnGround();

    public World getWorld();

    public boolean teleport(Location var1);

    public boolean teleport(Location var1, PlayerTeleportEvent.TeleportCause var2);

    public boolean teleport(Entity var1);

    public boolean teleport(Entity var1, PlayerTeleportEvent.TeleportCause var2);

    public List<Entity> getNearbyEntities(double var1, double var3, double var5);

    public int getEntityId();

    public int getFireTicks();

    public int getMaxFireTicks();

    public void setFireTicks(int var1);

    public void remove();

    public boolean isDead();

    public boolean isValid();

    public Server getServer();

    public Entity getPassenger();

    public boolean setPassenger(Entity var1);

    public boolean isEmpty();

    public boolean eject();

    public float getFallDistance();

    public void setFallDistance(float var1);

    public void setLastDamageCause(EntityDamageEvent var1);

    public EntityDamageEvent getLastDamageCause();

    public UUID getUniqueId();

    public int getTicksLived();

    public void setTicksLived(int var1);

    public void playEffect(EntityEffect var1);

    public EntityType getType();

    public boolean isInsideVehicle();

    public boolean leaveVehicle();

    public Entity getVehicle();
}

