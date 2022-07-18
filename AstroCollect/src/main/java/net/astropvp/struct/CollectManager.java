package net.astropvp.struct;


import com.google.gson.Gson;
import net.astropvp.utils.SaveLoadManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class CollectManager extends SaveLoadManager<PlayerCollect> {


    public CollectManager(JavaPlugin plugin, Gson gson) {
        super(plugin, gson, "data/", PlayerCollect.class);
    }

    public void removeCategoryFromCache(String key, CollectCategory category) {
        getFromCache(key).getCategories().remove(category);
    }

    public PlayerCollect getOrCreate(String key) {
        if (getFromCache(key) == null) {
            addToCache(new PlayerCollect(UUID.fromString(key)));
        }
        return getFromCache(key);
    }
}
