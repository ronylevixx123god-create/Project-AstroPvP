package com.conaxgames.customskulls.data;

import com.conaxgames.customskulls.CoreSkulls;
import com.conaxgames.customskulls.ability.AbilityData;
import com.conaxgames.customskulls.enums.AbilityType;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class MakeCustomBlocks {

    public static void newFileItem(Player player, AbilityType abilityType, Block block) {
        File file = new File(CoreSkulls.getInstance().getDataFolder().toString() + "/data/" + AbilityData.getUUID(player.getLocation()) + ".yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException exception) {
                CoreSkulls.getInstance().info("Unable to create yml file. Printing stack...");
                exception.printStackTrace();
            }
        }

        YamlConfiguration ymlFile = YamlConfiguration.loadConfiguration(file);

        ymlFile.set("owner", player.getUniqueId().toString());
        ymlFile.set("ability", abilityType.getName());
        ymlFile.set("location", player.getLocation());

        AbilityData data = AbilityData.getByUUID(AbilityData.getUUID(player.getLocation()));
        data.setUuid(AbilityData.getUUID(player.getLocation()));
        data.setLocation(player.getLocation());
        data.setAbilityType(abilityType);

        try {
            ymlFile.save(file);
        }
        catch (IOException exception) {
            CoreSkulls.getInstance().info("Unable to save yml file information. Printing stack...");
            exception.printStackTrace();
        }
        player.sendMessage("Successfully created a new custom block file!");
    }
}
