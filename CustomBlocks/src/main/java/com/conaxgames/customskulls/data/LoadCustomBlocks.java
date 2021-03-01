package com.conaxgames.customskulls.data;

import com.conaxgames.customskulls.CoreSkulls;
import com.conaxgames.customskulls.ability.AbilityData;
import com.conaxgames.customskulls.enums.AbilityType;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class LoadCustomBlocks {

    public static void load() {

        // Looping through all the files in a directory.
        File directory = new File(CoreSkulls.getInstance().getDataFolder() + File.separator + "data");
        File[] files = directory.listFiles();
        if (files == null || files.length == 0) {
            return;
        }
        for (File file : files) {
            if (file == null) { // Null check because those pesky files!
                return;
            }
            YamlConfiguration ymlFile = YamlConfiguration.loadConfiguration(file); // Load the file into YAML because bukkit!

            String ability = ymlFile.getString("ability");
            Location location = (Location) ymlFile.get("location");

            // If we store abilities as enums, we can change the material to a value below.
            //if (CoreSkulls.getInstance().getServer().getWorld(String.valueOf(location.getWorld())).getBlockAt(location).getType() != Material.SKULL) {
                // this skull doesn't exist? Oh noes, we better delete it!
                // delete the file.
                // delete memory, although there shouldn't be
                // send a nice pretty message.
                //CoreSkulls.getInstance().info("[CustomSkulls] Found a location which is not a skull!");
            //}

            AbilityData data = AbilityData.getByUUID(AbilityData.getUUID(location));
            data.setUuid(AbilityData.getUUID(location));
            data.setLocation(location);
            data.setAbilityType(AbilityType.valueOf(ability));
            CoreSkulls.getInstance().info("Put " + data.getAbilityType() + " into memory.");
        }
    }

}