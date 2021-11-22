package com.compilex.CosmicCrate.managers;

import com.compilex.CosmicCrate.CosmicCrate;
import com.compilex.CosmicCrate.api.CCAPI;
import com.compilex.CosmicCrate.libs.CosmicCrateData;
import com.compilex.CosmicCrate.utils.ConfigUtils;
import com.compilex.CosmicCrate.utils.LocalItemUtils;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CosmicCrateManager {

    @Getter private HashMap<String, CosmicCrateData> cosmicCrates;

    public CosmicCrateManager() {
        cosmicCrates = new HashMap<>();
        File folder = CosmicCrate.get().getDataFolder();
        if(folder.isDirectory())
            for(File found : folder.listFiles()) {
                if(found != null && found.getName().endsWith(".json")) {
                    YamlConfiguration config = YamlConfiguration.loadConfiguration(found);
                    cosmicCrates.put(config.getName(), getCosmicCrate(config));
                }
            }
    }

    private CosmicCrateData getCosmicCrate(YamlConfiguration config) {
        ItemStack randomMeta = ConfigUtils.getItem(config.getString("meta"));
        List<String> description = config.getStringList("description");
        List<ItemStack> loot = new ArrayList<>();
        for(String s : config.getStringList("loot")) {
            loot.add(ConfigUtils.getItem(s));
        }
        Short nonColorPane = (short) config.getInt("pane");
        Short colorPane = (short) config.getInt("color-pane");
        String name = config.getString("name");
        return new CosmicCrateData(name, randomMeta, description, loot, nonColorPane, colorPane);
    }

    public CosmicCrateData getCosmicCrate(String string) {
        if(cosmicCrates.containsKey(string)) {
            return cosmicCrates.get(string);
        }
        return null;
    }

    public String getId(HashMap<String, CosmicCrateData> map, CosmicCrateData data) {
        for(Map.Entry<String, CosmicCrateData> entry : map.entrySet()) {
            if(entry.getValue().equals(data)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public String getPermissionNode(String s) {
        return "ccrate.claim." + s.toLowerCase();
    }

    public boolean hasPermission(Player player, String crate) {
        return player.hasPermission(getPermissionNode(crate));
    }

    public boolean deleteCosmicCrate(String cosmicCrate) {
        this.cosmicCrates.remove(cosmicCrate);
        YamlConfiguration config;
        File folder = CosmicCrate.get().getDataFolder();
        for(File found : folder.listFiles()) {
            if(found == null || !found.getName().equalsIgnoreCase(cosmicCrate))
                return false;
            if(!found.delete())
                return false;
            else
                break;
        }
        this.cosmicCrates.remove(cosmicCrate);
        return true;
    }

    public ItemStack getDisplayItem(Player player, String crate) {
        if (player.hasPermission(getPermissionNode(crate))) {
            if (CCAPI.hasClaimedCosmicCrate(player, getCosmicCrate(crate)))
                return LocalItemUtils.changeType(CCAPI.getCosmicCrate(crate), Material.STAINED_GLASS_PANE, (short)5);
            return CCAPI.getCosmicCrate(crate);
        }
        return LocalItemUtils.getLockedPlaceholder(player, crate);
    }


}
