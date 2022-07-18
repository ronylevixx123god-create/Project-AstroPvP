package net.astropvp.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang.StringUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public abstract class SaveLoadManager<T extends KeyToSave> {

    private final File dataDirectory;
    private final Gson gson;
    private Class<T> clazz;
    private final Map<String, T> cache;

    protected SaveLoadManager(JavaPlugin plugin, Gson gson, String dataDirectory, Class<T> clazz) {
        this.gson = gson;
        this.clazz = clazz;
        this.dataDirectory = new File(plugin.getDataFolder(), dataDirectory);
        this.cache = new HashMap<>();
    }


    public void loadAll() {
        System.out.println("[Collect] Started loading...");
        if (!dataDirectory.exists()) {
            dataDirectory.mkdirs();
        }

        File[] validKitFiles = dataDirectory.listFiles((dir,name) -> name.endsWith(".json"));
        System.out.println("[Collect] " + validKitFiles.length);

        if (validKitFiles == null) {
            return;
        }

        for (File file : validKitFiles) {
            this.load(file);
        }
        System.out.println("[Collect] Finished loading...");

    }

    public void saveAll() {
        if (!dataDirectory.exists()) {
            dataDirectory.mkdirs();
        }

        for (Map.Entry<String, T> entry : cache.entrySet()) {
            this.save(entry.getKey(), entry.getValue());
        }

    }

    private void load(File file) {
        String fileName = file.getName();
        int fileNameLength = fileName.length();
        String key = fileName.substring(0,fileNameLength-5);

/*        if (!StringUtils.isAllLowerCase(key)) {
            System.out.println("[Collect] Returning because lowercase");
            return;
        }*/

        TypeToken<T> typeToke = new TypeToken<T>() {};
        Type type = typeToke.getType();

        try (FileReader reader = new FileReader(file)) {
            T t = this.gson.fromJson(reader, clazz);
            if (t == null) {
                return;
            }
            t.setKey(key);
            cache.put(key, t);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void save(String key, T t) {
        File file = new File(dataDirectory,key + ".json");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String jsonString = this.gson.toJson(t);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(jsonString);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addToCache(T t) {
        this.cache.put(t.getKey(), t);
    }

    public void removeFromCache(T t) {
        this.cache.remove(t.getKey());
    }

    public void removeFromCache(String key) {
        this.cache.remove(key);
    }

    public T getFromCache(String key) {
        return this.cache.get(key);
    }
}
