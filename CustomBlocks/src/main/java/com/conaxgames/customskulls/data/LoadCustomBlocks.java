package com.conaxgames.customskulls.data;

import com.conaxgames.customskulls.CoreSkulls;
import com.conaxgames.customskulls.ability.AbilityData;
import com.conaxgames.customskulls.enums.AbilityType;
import me.astropvp.cratekeys.enums.CrateType;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.UUID;

public class LoadCustomBlocks {

    public static void load() {

        // Looping through all the files in a directory.
        File directory = new File(CoreSkulls.instance.getDataFolder() + File.separator + "data");
        File[] files = directory.listFiles();
        if (files == null || files.length == 0) {
            return;
        }
        for (File file : files) {
            if (file == null) { // Null check because those pesky files!
                return;
            }
            // Load the file into YAML because we need to get some values from it!
            YamlConfiguration ymlFile = YamlConfiguration.loadConfiguration(file);

            String owner = ymlFile.getString("owner");
            String ability = ymlFile.getString("ability");
            Location location = (Location) ymlFile.get("location");
            AbilityType abilityType = AbilityType.valueOf(ability);
            long lastActivated = ymlFile.getLong("lastActivated");

            // This CustomBlock doesn't exist? Oh noes, we better delete it! It's always nice to keep things clean.
            if (CoreSkulls.instance.getServer().getWorld(location.getWorld().getName()).getBlockAt(location).getType() != abilityType.getMaterial()) {
                CoreSkulls.instance.info("[CustomSkulls] The block at a location was not the material. We can safely say it's not a CustomBlock.");
                CoreSkulls.instance.info(CoreSkulls.instance.getServer().getWorld(location.getWorld().getName()).getBlockAt(location).getType().toString());
                CoreSkulls.instance.info(abilityType.getMaterial().toString());
                file.delete();
                continue;
            }

            AbilityData data = AbilityData.getByUUID(AbilityData.getUUID(location));
            data.setOwner(UUID.fromString(owner));
            data.setUuid(AbilityData.getUUID(location));
            data.setLocation(location);
            data.setAbilityType(AbilityType.valueOf(ability));
            data.setLastActivated(lastActivated);

            if (abilityType == AbilityType.BLAZE_STONE_TOTEM) {
                int astroKeys = ymlFile.getInt("astro");
                int legKeys = ymlFile.getInt("legendary");
                int godKeys = ymlFile.getInt("godly");
                double xp =  ymlFile.getInt("xp");
                data.getCrateKeys().put(CrateType.ASTRO, astroKeys);
                data.getCrateKeys().put(CrateType.LEGENDARY, legKeys);
                data.getCrateKeys().put(CrateType.GODLY, godKeys);
                data.setXp(xp);
            }


            // This is a debug message which can be deleted at a later date!
        }
    }

}
