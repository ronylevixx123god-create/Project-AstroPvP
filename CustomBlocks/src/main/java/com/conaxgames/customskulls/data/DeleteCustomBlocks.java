package com.conaxgames.customskulls.data;

import com.conaxgames.customskulls.CoreSkulls;
import com.conaxgames.customskulls.ability.AbilityData;
import org.bukkit.Location;

import java.io.File;

public class DeleteCustomBlocks {
    public static void func(Location location) {
        File file = new File(CoreSkulls.getInstance().getDataFolder().toString() + "/me.astropvp.cratekeys.data/" + AbilityData.getUUID(location) + ".yml");

        // Delete the file as we don't need it anymore!
        if (file.exists()) {
            file.delete();
        }
        // Delete the me.astropvp.cratekeys.data from memory as we don't need it anymore!
        AbilityData.getAbilityDataMap().remove(AbilityData.getUUID(location));
    }
}
