package net.astropvp;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.astropvp.commands.CollectCommand;
import net.astropvp.listeners.PlayerListener;
import net.astropvp.struct.CollectManager;
import net.astropvp.struct.PlayerCollect;
import net.astropvp.struct.PlayerCollectAdapter;
import net.astropvp.utils.ItemStackDeserializer;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class CollectPlugin extends JavaPlugin {
    private static CollectPlugin instance;
    private CollectManager collectManager;

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .registerTypeAdapter(PlayerCollect.class, new PlayerCollectAdapter())
            .registerTypeAdapter(ItemStack.class, new ItemStackDeserializer())
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .create();

    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        registerCommands();
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);

        this.collectManager = new CollectManager(this, gson);
        this.collectManager.loadAll();
    }

    public void onDisable() {

        this.collectManager.saveAll();
        instance = null;
    }

    public static CollectPlugin getInstance() {
        return instance;
    }


    public CollectManager getCollectManager()  { return this.collectManager; }

    private void registerCommands() {
        getCommand("collect").setExecutor(new CollectCommand());
    }
}
