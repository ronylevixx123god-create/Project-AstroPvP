package me.astropvp.astrospawners.data;

import java.io.File;
import java.io.IOException;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

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
import org.bukkit.metadata.FixedMetadataValue;

import com.google.common.collect.Lists;
import me.astropvp.astrospawners.utils.LocationUtils;
import org.bukkit.scheduler.BukkitRunnable;

public class SpawnerDataHelper {

    private static List<SpawnerData> data;

    public static void addSpawnersToSpawnerInChunk(EntityType spawnerTypeToPlace, Location locationPlaced, int amountToPlace) {
        File file = new File(AstroSpawners.getInstance().getDataFolder().toString() + "/spawner-data");
        if (file != null && file.exists()) {
            File[] files = file.listFiles();
            if (files == null || files.length == 0) {
                return;
            }
            for (File chunk : files) {
                if (chunk == null) {
                    Bukkit.broadcastMessage("DEBUG 2");
                    continue;
                }
                String name = chunk.getName();
                if (!name.startsWith("chunk_")) {
                    continue;
                }

                String fileChunk = name.replace("chunk_", "");


                if ((locationPlaced.getWorld().getName() + "_" + locationPlaced.getChunk().getX() + "_" + locationPlaced.getChunk().getZ() + ".yml").equals(fileChunk)) {

                    Bukkit.broadcastMessage(fileChunk);

                    YamlConfiguration config = YamlConfiguration.loadConfiguration(chunk);

                    List<String> spawners = config.getStringList("spawners");
                    Bukkit.broadcastMessage("DEBUG 3");
                    for (int i = spawners.size() - 1; i >= 0; i--) {
                        String spawner = spawners.get(i);
                        String[] split = spawner.split("%");
                        EntityType type = EntityType.valueOf(split[0]);


                        if (spawnerTypeToPlace == type) {
                            int amount = Integer.valueOf(split[2]);
                            spawners.remove(i);

                            int totalAmount = amount + amountToPlace;

                            Location location = LocationUtils.fromStringLocation(split[1]);
                            Bukkit.broadcastMessage(CC.DarkGray + location);
                            SpawnerData data = SpawnerDataHelper.findSpawnerData(location);


                            data.setSpawnerAmount(totalAmount);




                            /*spawner.replace("%"+amount, "%"+totalAmount);

                            Bukkit.broadcastMessage(ChatColor.DARK_AQUA + spawner);

                            spawners.add(spawner);

                            config.set("spawners", spawners);
                             */
                        }
                    }
                }
            }
        }
    }

    public static void init() {
        data = Lists.newArrayList();
        File file = new File(AstroSpawners.getInstance().getDataFolder().toString() + "/spawner-data");
        if (file != null && file.exists()) {
            File[] files = file.listFiles();
            if (files == null || files.length == 0) {
                return;
            }
            for (File chunk : files) {
                if (chunk == null) {
                    continue;
                }
                String name = chunk.getName();
                if (!name.startsWith("chunk_")) {
                    continue;
                }
                YamlConfiguration config = YamlConfiguration.loadConfiguration(chunk);

                List<String> spawners = config.getStringList("spawners");
                boolean save = false;
                for (int i = spawners.size() - 1; i >= 0; i--) {
                    String spawner = spawners.get(i);
                    String[] split = spawner.split("%");
                    EntityType type = EntityType.valueOf(split[0]);
                    Location location = LocationUtils.fromStringLocation(split[1]);
                    int amount = Integer.valueOf(split[2]);
                    Block block = location.getBlock();
                    if (block.getType() != Material.MOB_SPAWNER) {
                        spawners.remove(i);
                        save = true;
                        Bukkit.getLogger().info("[Spawners] Removing Invalid Spawner Location at " +
                                LocationUtils.toStringLocation(location));
                        continue;
                    }
                    SpawnerData data = new SpawnerData(location, type, amount);
                    new SpawnerDataHelper(data, false);
                }
                if (save) {
                    config.set("spawners", spawners);
                    try {
                        config.save(chunk);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void onDisable() {
        for (SpawnerData data : SpawnerDataHelper.data) {
            if (data.getSpawnerAmount() <= 0 || data.isNullSpawner()) {
                deletePath(data);
                continue;
            }
            createConfiguration(data);
        }
    }

    public SpawnerDataHelper(SpawnerData data, boolean createConfig) {
        SpawnerDataHelper.data.add(data);
        if (createConfig) {
            createConfiguration(data);
        }
        Block block = data.getLocation().getBlock();
        block.setMetadata("spawner_data", new FixedMetadataValue(AstroSpawners.getInstance(), data));
    }

    public static void deletePath(SpawnerData data) {
        Location location = data.getLocation();
        String stringLocation = LocationUtils.toStringLocation(location);
        int dataX = location.getChunk().getX();
        int dataZ = location.getChunk().getZ();
        File chunk = new File(AstroSpawners.getInstance().getDataFolder().toString() + "/spawner-data/chunk_" +
                location.getWorld().getName() + "_" + dataX + "_" + dataZ + ".yml");
        try {
            chunk.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        YamlConfiguration config = YamlConfiguration.loadConfiguration(chunk);
        List<String> spawners = config.getStringList("spawners");
        for (int i = spawners.size() - 1; i >= 0; i--) {
            if (spawners.get(i).split("%")[1].equals(stringLocation)) {
                spawners.remove(i);
                break;
            }
        }
        config.set("spawners", spawners);
        try {
            config.save(chunk);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createConfiguration(SpawnerData data) {
        Location location = data.getLocation();
        String stringLocation = LocationUtils.toStringLocation(location);
        int dataX = location.getChunk().getX();
        int dataZ = location.getChunk().getZ();
        File chunk = new File(AstroSpawners.getInstance().getDataFolder().toString() + "/spawner-data/chunk_" +
                location.getWorld().getName() + "_" + dataX + "_" + dataZ + ".yml");
        try {
            chunk.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        YamlConfiguration config = YamlConfiguration.loadConfiguration(chunk);
        List<String> spawners = config.getStringList("spawners");
        String value = data.getEntityType().name() + "%" + data.getStringLocation() + "%" + data.getSpawnerAmount();
        for (int i = spawners.size() - 1; i >= 0; i--) {
            if (spawners.get(i).split("%")[1].equals(stringLocation)) {
                spawners.remove(i);
                break;
            }
        }
        spawners.add(value);
        config.set("spawners", spawners);
        try {
            config.save(chunk);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<EntityType, MutableInt> getSpawners(final Chunk chunk) {
        final Map<EntityType, MutableInt> spawners = new EnumMap<>(EntityType.class);

        TileEntity entity;

        EntityType type;
        MutableInt amount;

        for (final Map.Entry<BlockPosition, TileEntity> entry : ((CraftChunk) chunk).getHandle().tileEntities.entrySet()) {
            if ((entity = entry.getValue()) instanceof TileEntityMobSpawner) {
                type = EntityType.fromName(((TileEntityMobSpawner) entity).getSpawner().getMobName());

                if ((amount = spawners.get(type)) == null) {
                    spawners.put(type, new MutableInt(1));
                } else {
                    amount.increment();
                }
            }
        }
        return spawners;
    }

    public static boolean canPlaceStackedSpawnerInThisChunk(Chunk chunk, EntityType entityType) {

        Map<EntityType, MutableInt> spawners = getSpawners(chunk);
        if (spawners.isEmpty()) {
            return true;
        }

        if (spawners.containsKey(entityType)) {
            return false;
        }

        return true;
    }


    public static SpawnerData findSpawnerData(SpawnerData data) {
        String location = data.getStringLocation();
        for (int i = 0; i < SpawnerDataHelper.data.size(); i++) {
            SpawnerData sd = SpawnerDataHelper.data.get(i);
            if (sd.getStringLocation().contentEquals(location)) {
                return sd;
            }
        }
        return null;
    }

    public static SpawnerData findSpawnerData(Location loc) {
        Block block = loc.getBlock();
        if (block.hasMetadata("spawner_data")) {
            return (SpawnerData) block.getMetadata("spawner_data").get(0).value();
        }
        String location = LocationUtils.toStringLocation(loc);
        for (int i = 0; i < SpawnerDataHelper.data.size(); i++) {
            SpawnerData sd = SpawnerDataHelper.data.get(i);
            if (sd.getStringLocation().equals(location)) {
                return sd;
            }
        }
        return null;
    }

    public static void removeSpawnerData(SpawnerData data) {
        Location location = data.getLocation();
        Block block = location.getBlock();
        if (block.hasMetadata("spawner_data")) {
            block.removeMetadata("spawner_data", AstroSpawners.getInstance());
        }
        data.setSpawnerAmount(0);
    }

    public static List<SpawnerData> getData() {
        return data;
    }
}
