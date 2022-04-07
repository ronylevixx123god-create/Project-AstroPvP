package me.astropvp.astrospawners.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import me.astropvp.astrospawners.AstroSpawners;
import me.astropvp.astrospawners.configs.MakeSpawner;
import org.bukkit.*;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;

import me.astropvp.astrospawners.utils.LocationUtils;

public class SpawnerManager {
    private boolean loading = false;


    private Map<Location, SpawnerData> spawnersMap;

    public SpawnerManager() {
        this.spawnersMap = new HashMap<>();
    }

    public void add(Location location, SpawnerData spawnerData) {
        this.spawnersMap.put(location, spawnerData);
    }

    public Map<Location, SpawnerData> getSpawnersMap() {
        return this.spawnersMap;
    }

    public void remove(Location location) {
        this.spawnersMap.remove(location);
    }


    public static boolean canPlaceStackedSpawnerInThisChunk(Chunk chunk, EntityType spawnertype) {
        for (BlockState blockState : chunk.getTileEntities()) {
            if (blockState instanceof CreatureSpawner) {
                EntityType type = ((CreatureSpawner) blockState).getSpawnedType();
                if (type == spawnertype) {
                    return false;
                }
            }
        }
        return true;
    }


    public SpawnerData findSpawnerData(Location location) {
        return this.spawnersMap.get(location);
    }
}

