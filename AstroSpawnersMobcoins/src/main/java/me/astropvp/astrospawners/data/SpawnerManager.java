package me.astropvp.astrospawners.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import me.astropvp.astrospawners.AstroSpawners;
import org.bukkit.*;
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

    public void save() {
        if (this.loading)
            return;
        try {
            PreparedStatement statement = AstroSpawners.getInstance().getSqlDatabase().getConnection().prepareStatement("DELETE FROM spawner_data");
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public void load() {
        this.loading = true;
        try {
            PreparedStatement statement = AstroSpawners.getInstance().getSqlDatabase().getConnection().prepareStatement("SELECT * FROM spawner_data");
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                Location location = LocationUtils.fromStringLocation(set.getString("location"));
                EntityType type = EntityType.valueOf(set.getString("type"));
                int amount = Integer.parseInt(set.getString("amount"));
                SpawnerData spawnerData = new SpawnerData(type, amount);
                AstroSpawners.getInstance().getSpawnerManager().getSpawnersMap().put(location, spawnerData);
            }
            this.loading = false;
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public SpawnerData findSpawnerData(Location location) {
        return this.spawnersMap.get(location);
    }
}

