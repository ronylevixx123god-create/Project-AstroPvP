package me.astropvp.astrospawners.data;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.ProjectileHitEvent;

import me.astropvp.astrospawners.utils.LocationUtils;


public class SpawnerData {

    private String location;
    public int spawnerAmount;
    private EntityType type;
    private boolean nullSpawner;

    public SpawnerData(Location location, EntityType type, int spawnerAmount) {
        this.location = LocationUtils.toStringLocation(location);
        this.spawnerAmount = spawnerAmount;
        this.type = type;
        this.nullSpawner = false;
    }

    public EntityType getEntityType() {
        return this.type;
    }

    public void setNullSpawner(boolean nullSpawner) {
        this.nullSpawner = nullSpawner;
    }

    public boolean isNullSpawner() {
        return this.nullSpawner;
    }

    public Location getLocation() {
        return LocationUtils.fromStringLocation(this.location);
    }

    public String getStringLocation() {
        return this.location;
    }

    public int getSpawnerAmount() {
        return this.spawnerAmount;
    }

    public void setSpawnerAmount(int spawnerAmount) {
        this.spawnerAmount = spawnerAmount;
    }
    ProjectileHitEvent event;
}
