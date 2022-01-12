package me.astropvp.astrospawners.data;

import com.conaxgames.customskulls.ability.AbilityData;
import me.astropvp.astrospawners.AstroSpawners;
import me.astropvp.astrospawners.configs.MakeSpawner;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.ProjectileHitEvent;

import me.astropvp.astrospawners.utils.LocationUtils;


public class SpawnerData {

    public int spawnerAmount;
    private EntityType type;

    public SpawnerData(EntityType type, int spawnerAmount) {
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


    public static String toString(Location location) {
        return location.getWorld().getName() + "_" + location.getBlockX()+ "_" + location.getBlockY()+ "_" + location.getBlockZ();
    }


    public static Location fromString(String location) {
        String[] locationSplit = location.split("_");
        World world = Bukkit.getWorld(locationSplit[0]);
        double x = Double.parseDouble(locationSplit[1]);
        double y = Double.parseDouble(locationSplit[2]);
        double z = Double.parseDouble(locationSplit[3]);

        return new Location(world, x, y, z);
    }
}
