package me.astropvp.astrospawners.configs;

import com.conaxgames.customskulls.CoreSkulls;
import com.conaxgames.customskulls.ability.AbilityData;
import com.conaxgames.customskulls.blocks.CustomBlock;
import com.conaxgames.customskulls.util.CC;
import me.astropvp.astrospawners.AstroSpawners;
import me.astropvp.astrospawners.data.SpawnerData;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;

public class DeleteSpawner {
    public static void delete(Location location) {
        File file = new File(AstroSpawners.getInstance().getDataFolder().toString() + "/data/" + SpawnerData.toString(location) + ".yml");

        // Delete the file as we don't need it anymore!
        if (file.exists()) {
            file.delete();
        }
        // Delete the data from memory as we don't need it anymore!
        AstroSpawners.getInstance().getSpawnersMap().remove(location);

    }
}
