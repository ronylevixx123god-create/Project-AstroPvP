package me.astropvp.astrospawners.configs;

import com.conaxgames.customskulls.CoreSkulls;
import com.conaxgames.customskulls.ability.AbilityData;
import com.conaxgames.customskulls.blocks.CustomBlock;
import com.conaxgames.customskulls.enums.AbilityType;
import com.conaxgames.customskulls.util.CC;
import me.astropvp.astrospawners.AstroSpawners;
import me.astropvp.astrospawners.data.SpawnerData;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class MakeSpawner {

    public static void newFileItem(Location location, EntityType type, int amount) {
        File file = new File(AstroSpawners.getInstance().getDataFolder().toString() + "/data/" + SpawnerData.toString(location) + ".yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException exception) {
                System.out.println("Unable to create yml file. Printing stack...");
                exception.printStackTrace();
            }
        }

        YamlConfiguration ymlFile = YamlConfiguration.loadConfiguration(file);

        ymlFile.set("location", SpawnerData.toString(location));
        ymlFile.set("type", type.name());
        ymlFile.set("amount", amount);

        SpawnerData data = new SpawnerData(type, amount);
        AstroSpawners.getInstance().getSpawnersMap().put(location, data);


        try {
            ymlFile.save(file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
