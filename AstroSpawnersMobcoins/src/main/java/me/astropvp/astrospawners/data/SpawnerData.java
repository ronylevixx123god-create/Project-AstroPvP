package me.astropvp.astrospawners.data;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.ProjectileHitEvent;

import me.astropvp.astrospawners.utils.LocationUtils;


public class SpawnerData {

    public int spawnerAmount;
    private EntityType type;

    public SpawnerData(Location location, EntityType type, int spawnerAmount) {
        this.spawnerAmount = spawnerAmount;
        this.type = type;
    }

    public EntityType getEntityType() {
        return this.type;
    }

    public int getSpawnerAmount() {
        return this.spawnerAmount;
    }

    public void setSpawnerAmount(int spawnerAmount) {
        this.spawnerAmount = spawnerAmount;
    }
}
