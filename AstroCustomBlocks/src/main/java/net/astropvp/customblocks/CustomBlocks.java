package net.astropvp.customblocks;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketListener;
import net.astropvp.customblocks.tasks.ChunkCustomBlocksTask;
import net.astropvp.customblocks.tasks.TickAbleBlocksRunnable;
import net.astropvp.customblocks.utils.config.ConfigManager;
import net.astropvp.storage.StoragePlugin;
import net.astropvp.storage.gson.Gson;import lombok.Getter;
import me.mattstudios.mf.base.CommandManager;
import net.astropvp.customblocks.commands.CustomBlockCommand;
import net.astropvp.customblocks.data.CustomBlockManager;
import net.astropvp.customblocks.listeners.CustomBlockBreakListener;
import net.astropvp.customblocks.listeners.CustomBlockListener;
import net.astropvp.customblocks.listeners.CustomBlockPacketListener;
import net.astropvp.customblocks.listeners.CustomBlockPlaceListener;
import net.astropvp.customblocks.struct.CustomBlock;
import net.astropvp.customblocks.struct.typeadapters.CustomBlockDeserializer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class CustomBlocks extends JavaPlugin {
    @Getter
    public static CustomBlocks instance;
    private CustomBlockManager customBlockManager;
    private CommandManager commandManager;
    private ConfigManager configManager;
    private Gson gson;

    public void onEnable() {
        instance = this;
        this.gson = StoragePlugin.getInstance()
                .getGson()
                .newBuilder()
                .registerTypeAdapter(CustomBlock.class, new CustomBlockDeserializer()).setLenient()
                .create();
        this.commandManager = new CommandManager(this, true);
        commandManager.register(new CustomBlockCommand());
        this.customBlockManager = new CustomBlockManager(this);
        customBlockManager.loadAll();
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, ()-> {
            customBlockManager.saveAll();
        }, 40L, 15*60*20L);

        this.configManager = new ConfigManager(this);
        configManager.setupFiles();
        registerListeners();
        registerTasks();
        ProtocolLibrary.getProtocolManager().addPacketListener((PacketListener)new CustomBlockPacketListener());

    }

    public void onDisable() {
        customBlockManager.saveAll();
        instance = null;
    }

    public void registerListeners() {
        getServer().getPluginManager().registerEvents(new CustomBlockListener(this), this);
        getServer().getPluginManager().registerEvents(new CustomBlockBreakListener(this), this);
        getServer().getPluginManager().registerEvents(new CustomBlockPlaceListener(this), this);
    }

    public void registerTasks() {
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, new ChunkCustomBlocksTask(), 40L, 20L * 10);
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, new TickAbleBlocksRunnable(), 40L, 20L);
    }
}
