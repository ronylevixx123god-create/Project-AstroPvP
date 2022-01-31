package com.conaxgames.customskulls.data;

import com.conaxgames.customskulls.CoreSkulls;
import com.conaxgames.customskulls.ability.AbilityData;
import com.conaxgames.customskulls.enums.AbilityType;
import com.conaxgames.customskulls.struct.MobGrinderCache;
import me.astropvp.cratekeys.enums.CrateType;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ConcurrentModificationException;
import java.util.Map;

public class SaveAllGrinderCustomBlocks {
    public static void saveAllGrinderCustomBlocks() {
        CoreSkulls.setSavingGrinderBlocks(true);
        CoreSkulls.instance.info("Started Saving customblocks");
        try {
            final Map<String, AbilityData> grinderBlocks = AbilityData.getAbilityDataMap();
            for (Map.Entry<String, AbilityData> entry : grinderBlocks.entrySet()) {
                AbilityData data = entry.getValue();
                if (data.getAbilityType() == AbilityType.BLAZE_STONE_TOTEM) {

                    File file = new File(CoreSkulls.instance.getDataFolder().toString() + "/data/" + AbilityData.getUUID(data.getLocation()) + ".yml");

                    if (!file.exists()) {
                        return;
                    }

                    YamlConfiguration ymlFile = YamlConfiguration.loadConfiguration(file);

                    MobGrinderCache mobGrinderCache = CoreSkulls.instance.getGrinderBlocks().get(data.getUuid());

                    ymlFile.set("lastActivated", data.getLastActivated());

                    data.setLastActivated(data.getLastActivated());

                    if (mobGrinderCache != null) {
                        ymlFile.set("astro", mobGrinderCache.getCrateKeys().get(CrateType.ASTRO));
                        ymlFile.set("legendary", mobGrinderCache.getCrateKeys().get(CrateType.LEGENDARY));
                        ymlFile.set("godly", mobGrinderCache.getCrateKeys().get(CrateType.GODLY));
                        ymlFile.set("xp", mobGrinderCache.getXp());

                        data.setLastActivated(data.getLastActivated());
                        mobGrinderCache.getCrateKeys().put(CrateType.ASTRO, mobGrinderCache.getCrateKeys().get(CrateType.ASTRO));
                        mobGrinderCache.getCrateKeys().put(CrateType.LEGENDARY, mobGrinderCache.getCrateKeys().get(CrateType.LEGENDARY));
                        mobGrinderCache.getCrateKeys().put(CrateType.GODLY, mobGrinderCache.getCrateKeys().get(CrateType.GODLY));
                        mobGrinderCache.setXp(mobGrinderCache.getXp());
                    }


                    try {
                        ymlFile.save(file);
                    } catch (Exception exception) {
                        CoreSkulls.instance.info("Unable to save yml file information. Printing stack...");
                        exception.printStackTrace();
                    }
                }
            }
            CoreSkulls.setSavingGrinderBlocks(false);
            CoreSkulls.instance.info("Finished saving customblocks");
        } catch (ConcurrentModificationException e) {
            CoreSkulls.setSavingGrinderBlocks(false);
            e.printStackTrace();
        }

    }

}
