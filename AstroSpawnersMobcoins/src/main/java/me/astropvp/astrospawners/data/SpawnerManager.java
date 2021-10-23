package me.astropvp.astrospawners.data;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.astrospawners.AstroSpawners;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.ChunkSnapshot;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.TileEntityMobSpawner;
import org.apache.commons.lang.mutable.MutableInt;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.CraftChunk;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import com.google.common.collect.Lists;
import me.astropvp.astrospawners.utils.LocationUtils;
import org.bukkit.scheduler.BukkitRunnable;

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

    public void save(boolean async) {
        if (this.loading)
            return;
        try {
            for (Map.Entry<Location, SpawnerData> entry : spawnersMap.entrySet()) {
                Location location = entry.getKey();
                SpawnerData spawnerData = entry.getValue();
                PreparedStatement statement = AstroSpawners.getInstance().getSqlDatabase().getConnection().prepareStatement("INSERT IGNORE INTO spawner_data(location, type, amount) VALUES (?, ?, ?);");
                try {
                    statement.setString(1, LocationUtils.toStringLocation(location));
                    statement.setString(2, spawnerData.getEntityType().name());
                    statement.setString(3, Integer.toString(spawnerData.getSpawnerAmount()));
                    statement.executeUpdate();

                } finally {
                    /*if (Collections.singletonList(statement).get(0) != null)
                        statement.close();*/
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void load(boolean async) {
        this.loading = true;
        try {
            PreparedStatement statement = AstroSpawners.getInstance().getSqlDatabase().getConnection().prepareStatement("SELECT * FROM spawner_data");
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                Location location = LocationUtils.fromStringLocation(set.getString("location"));
                EntityType type = EntityType.valueOf(set.getString("type"));
                int amount = Integer.parseInt(set.getString("amount"));
                SpawnerData spawnerData = new SpawnerData(location, type, amount);
                AstroSpawners.getInstance().getCollectManager().getSpawnersMap().put(location, spawnerData);
            }
            this.loading = false;
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

