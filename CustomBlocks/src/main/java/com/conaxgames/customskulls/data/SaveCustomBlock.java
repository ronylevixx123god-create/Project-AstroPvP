package com.conaxgames.customskulls.data;

import com.conaxgames.customskulls.CoreSkulls;
import com.conaxgames.customskulls.ability.AbilityData;
import com.conaxgames.customskulls.blocks.CustomBlock;
import com.conaxgames.customskulls.enums.AbilityType;
import com.conaxgames.customskulls.struct.MobGrinderCache;
import com.conaxgames.customskulls.util.CC;
import me.astropvp.cratekeys.enums.CrateType;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class SaveCustomBlock {
    public static void saveCustomBlock(Player player, AbilityType abilityType, Block block) {
        File file = new File(CoreSkulls.instance.getDataFolder().toString() + "/data/" + AbilityData.getUUID(block.getLocation()) + ".yml");

        if (!file.exists()) {
            return;
        }

        YamlConfiguration ymlFile = YamlConfiguration.loadConfiguration(file);

        ymlFile.set("owner", player.getUniqueId().toString());
        ymlFile.set("ability", abilityType.getName());
        ymlFile.set("location", block.getLocation());

        AbilityData data = AbilityData.getByUUID(AbilityData.getUUID(block.getLocation()));
        MobGrinderCache mobGrinderCache = CoreSkulls.instance.getGrinderBlocks().get(data.getUuid());
        ymlFile.set("lastActivated", data.getLastActivated());
        ymlFile.set("level", data.getLevel());

        data.setOwner(player.getUniqueId());
        data.setUuid(AbilityData.getUUID(block.getLocation()));
        data.setLocation(block.getLocation());
        data.setAbilityType(abilityType);
        data.setLastActivated(data.getLastActivated());
        data.setLevel(data.getLevel());

        if (abilityType == AbilityType.BLAZE_STONE_TOTEM) {
            ymlFile.set("astro", mobGrinderCache.getCrateKeys().get(CrateType.ASTRO));
            ymlFile.set("legendary", mobGrinderCache.getCrateKeys().get(CrateType.LEGENDARY));
            ymlFile.set("godly", mobGrinderCache.getCrateKeys().get(CrateType.GODLY));
            ymlFile.set("xp", mobGrinderCache.getXp());

            data.setLastActivated(data.getLastActivated());
            data.setLevel(data.getLevel());
            data.getCrateKeys().put(CrateType.ASTRO, mobGrinderCache.getCrateKeys().get(CrateType.ASTRO));
            data.getCrateKeys().put(CrateType.LEGENDARY, mobGrinderCache.getCrateKeys().get(CrateType.LEGENDARY));
            data.getCrateKeys().put(CrateType.GODLY, mobGrinderCache.getCrateKeys().get(CrateType.GODLY));
            data.setXp(mobGrinderCache.getXp());

        }

        try {
            ymlFile.save(file);
        }
        catch (IOException exception) {
            CoreSkulls.instance.info("Unable to save yml file information. Printing stack...");
            exception.printStackTrace();
        }
    }
}

