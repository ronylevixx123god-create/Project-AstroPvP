package me.astropvp.astrospawners.configs;

import com.conaxgames.customskulls.CoreSkulls;
import com.conaxgames.customskulls.ability.AbilityData;
import com.conaxgames.customskulls.enums.AbilityType;
import me.astropvp.astrospawners.AstroSpawners;
import me.astropvp.astrospawners.data.SpawnerData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;

import java.io.File;
import java.util.UUID;

public class LoadSpawner {

    public static void load() {

        // Looping through all the files in a directory.
        File directory = new File(AstroSpawners.getInstance().getDataFolder() + File.separator + "data");
        File[] files = directory.listFiles();
        if (files == null || files.length == 0) {
            return;
        }
        for (File file : files) {
            if (file == null) { // Null check because those pesky files!
                return;
            }
            try {
                // Load the file into YAML because we need to get some values from it!
                YamlConfiguration ymlFile = YamlConfiguration.loadConfiguration(file);

                Location location = SpawnerData.fromString(ymlFile.getString("location"));
                int amount = Integer.parseInt(ymlFile.getString("amount"));
                EntityType type = EntityType.valueOf(ymlFile.getString("type"));

                // This CustomBlock doesn't exist? Oh noes, we better delete it! It's always nice to keep things clean.
                if (AstroSpawners.getInstance().getServer().getWorld(location.getWorld().getName()).getBlockAt(location).getType() != Material.MOB_SPAWNER) {
                    System.out.println("Deleted block at " + location + " because it was not a mobspawner");
                    file.delete();
                    continue;
                }

                SpawnerData data = new SpawnerData(type, amount);
                AstroSpawners.getInstance().getSpawnersMap().put(location, data);


                // This is a debug message which can be deleted at a later date!
                System.out.println("[SpawnersData] Put" + location + " into memory with type :" + type);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
