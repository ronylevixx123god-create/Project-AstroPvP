package net.astropvp;

import lombok.Getter;
import net.astropvp.commands.CollectCommand;
import net.astropvp.commands.CollectSaveCommand;
import net.astropvp.listeners.PlayerListener;
import net.astropvp.storage.StoragePlugin;
import net.astropvp.struct.CollectManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class CollectPlugin extends JavaPlugin {
    @Getter
    private static CollectPlugin instance;
    private CollectManager collectManager;

    public void onEnable() {
        instance = this;

        this.collectManager = new CollectManager(this, StoragePlugin.getInstance().getGson());
        this.collectManager.loadAll();

        this.registerCommands();
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, ()-> {
            System.out.println("[Collect] Started auto saving all collects");
            collectManager.saveAll();
            System.out.println("[Collect] Finished auto saving all collects");
        }, 60L, 5*20*60L);
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
