package com.conaxgames.customskulls.data;

import com.conaxgames.customskulls.CoreSkulls;
import com.conaxgames.customskulls.ability.AbilityData;
import com.conaxgames.customskulls.blocks.CustomBlock;
import com.conaxgames.customskulls.enums.AbilityType;
import com.conaxgames.customskulls.util.CC;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class SaveCustomBlocks {
    public static void saveCustomBlock(Player player, AbilityType abilityType, Block block) {
        File file = new File(CoreSkulls.getInstance().getDataFolder().toString() + "/data/" + AbilityData.getUUID(block.getLocation()) + ".yml");

        if (!file.exists()) {
            return;
        }

        YamlConfiguration ymlFile = YamlConfiguration.loadConfiguration(file);

        ymlFile.set("owner", player.getUniqueId().toString());
        ymlFile.set("ability", abilityType.getName());
        ymlFile.set("location", block.getLocation());

        AbilityData data = AbilityData.getByUUID(AbilityData.getUUID(block.getLocation()));
        ymlFile.set("lastActivated", data.getLastActivated());

        data.setOwner(player.getUniqueId());
        data.setUuid(AbilityData.getUUID(block.getLocation()));
        data.setLocation(block.getLocation());
        data.setAbilityType(abilityType);
        data.setLastActivated(data.getLastActivated());

        try {
            ymlFile.save(file);
        }
        catch (IOException exception) {
            CoreSkulls.getInstance().info("Unable to save yml file information. Printing stack...");
            exception.printStackTrace();
        }
    }
}

