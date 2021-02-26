package com.conaxgames.customskulls.data;

import com.conaxgames.customskulls.CoreSkulls;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Objects;

public class LoadCustomBlocks {

    public static void load() {

        // Looping through all the files in a directory.
        File directory = new File(CoreSkulls.getInstance().getDataFolder() + "/data");
        File[] files = Objects.requireNonNull(directory.listFiles());
        for (File file : files) {
            if (file == null) { // Null check because those pesky files!
                return;
            }
            YamlConfiguration ymlFile = YamlConfiguration.loadConfiguration(file); // Load the file into YAML because bukkit!

            String ability = ymlFile.getString("ability");
            Location location = (Location) ymlFile.get("location");

            // If we store abilities as enums, we can change the material to a value below.
            if (CoreSkulls.getInstance().getServer().getWorld(String.valueOf(location.getWorld())).getBlockAt(location).getType() != Material.SKULL) {
                // this skull doesn't exist? Oh noes, we better delete it!
                // delete the file.
                // delete memory, although there shouldn't be
                // send a nice pretty message.
                CoreSkulls.getInstance().info("[CustomSkulls] Found a location which is not a skull!");
            }

        }
    }

}