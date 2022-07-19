package net.astropvp.storage;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import net.astropvp.storage.utils.ItemStackArrayDeserializer;
import net.astropvp.storage.utils.ItemStackArraySerializer;
import net.astropvp.storage.utils.ItemStackDeserializer;
import net.astropvp.storage.utils.ItemStackSerializer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class StoragePlugin extends JavaPlugin {

    @Getter
    private static StoragePlugin instance;

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .registerTypeAdapter(ItemStack.class, new ItemStackSerializer()).setLenient()
            .registerTypeAdapter(ItemStack.class, new ItemStackDeserializer()).setLenient()
            .registerTypeAdapter(ItemStack[].class, new ItemStackArraySerializer()).setLenient()
            .registerTypeAdapter(ItemStack[].class, new ItemStackArrayDeserializer()).setLenient()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .create();

    @Override
    public void onEnable() {
        instance = this;
    }

    @Override
    public void onDisable() {
        instance = null;
    }
}
