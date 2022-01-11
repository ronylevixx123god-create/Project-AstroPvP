package me.astropvp.cratekeys.filters;

import com.cosmicpvp.cosmicutils.itembuilder.JsonItemSerializer;
import com.cosmicpvp.cosmicutils.utils.JSONUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class FilterPlayer {
    private transient Gson gson = new GsonBuilder().setPrettyPrinting().enableComplexMapKeySerialization().registerTypeAdapter(ItemStack.class, new JsonItemSerializer()).create();

    private HashMap<String, Filter<?>> filters;

    private transient Plugin plugin;

    private UUID uuid;

    public FilterPlayer(Plugin plugin, UUID uuid) {
        this.plugin = plugin;
        this.uuid = uuid;
        this.filters = new HashMap<>();
    }

    public boolean hasFilter(String name) {
        return this.filters.containsKey(name);
    }

    public Filter<?> getFilter(String name) {
        if(!this.filters.containsKey(name))
            return null;
        return this.filters.get(name);
    }

    public Filter<?> registerFilter(Filter<?> filter) {
        this.filters.put(filter.getName(), filter);
        return filter;
    }

    public void loadData() {
        File file = new File(this.plugin.getDataFolder(), this.uuid + ".json");
        if(!file.exists()) {
            return;
        }
        try {
            this.filters = (HashMap<String, Filter<?>>) JSONUtils.fromJson(file, filters.getClass(), this.gson);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void saveData() {
        File file = null;
        try {
            file = JSONUtils.getOrCreateFile(this.plugin.getDataFolder(), this.uuid + ".json");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        if(file == null) {
            Bukkit.getLogger().info("FAILED TO SAVE FILTER DATA FOR UUID: " + this.uuid +" (SKIPPING)");
            return;
        }
        try {
            JSONUtils.saveJSONToFile(file, filters, filters.getClass(), this.gson);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
