package net.astropvp;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import net.astropvp.commands.CollectCommand;
import net.astropvp.commands.CollectSaveCommand;
import net.astropvp.listeners.PlayerListener;
import net.astropvp.struct.CollectManager;
import net.astropvp.utils.ItemStackArrayDeserializer;
import net.astropvp.utils.ItemStackArraySerializer;
import net.astropvp.utils.ItemStackDeserializer;
import net.astropvp.utils.ItemStackSerializer;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class CollectPlugin extends JavaPlugin {
    @Getter
    private static CollectPlugin instance;
    private CollectManager collectManager;

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .registerTypeAdapter(ItemStack.class, new ItemStackSerializer()).setLenient()
            .registerTypeAdapter(ItemStack.class, new ItemStackDeserializer()).setLenient()
            .registerTypeAdapter(ItemStack[].class, new ItemStackArraySerializer()).setLenient()
            .registerTypeAdapter(ItemStack[].class, new ItemStackArrayDeserializer()).setLenient()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .create();

    public void onEnable() {
        instance = this;

        this.collectManager = new CollectManager(this, gson);
        this.collectManager.loadAll();

        this.registerCommands();
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
    }

    public void onDisable() {
        this.collectManager.saveAll();
        instance = null;
    }

    private void registerCommands() {
        getCommand("collect").setExecutor(new CollectCommand());
        getCommand("collectsave").setExecutor(new CollectSaveCommand(this));
        getCommand("collectload").setExecutor(new CollectSaveCommand(this));
    }
}
