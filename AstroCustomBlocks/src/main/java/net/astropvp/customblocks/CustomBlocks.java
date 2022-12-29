package net.astropvp.customblocks;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketListener;
import lombok.Getter;
import me.mattstudios.mf.base.CommandManager;
import net.astropvp.customblocks.commands.CustomBlockAdminCommand;
import net.astropvp.customblocks.data.CustomBlockManager;
import net.astropvp.customblocks.listeners.CustomBlockBreakListener;
import net.astropvp.customblocks.listeners.CustomBlockPacketListener;
import net.astropvp.customblocks.listeners.CustomBlockPlaceListener;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class CustomBlocks extends JavaPlugin {
    @Getter
    public static CustomBlocks instance;
    private CustomBlockManager customBlockManager;
    private CommandManager commandManager;
    public void onEnable() {
        instance = this;
        this.commandManager = new CommandManager(this, true);
        commandManager.register(new CustomBlockAdminCommand());
        this.customBlockManager = new CustomBlockManager(this);
        customBlockManager.loadAll();
        registerListeners();
        ProtocolLibrary.getProtocolManager().addPacketListener((PacketListener)new CustomBlockPacketListener());

    }

    public void onDisable() {
        customBlockManager.saveAll();
        instance = null;
    }

    public void registerListeners() {
        getServer().getPluginManager().registerEvents(new CustomBlockBreakListener(this), this);
        getServer().getPluginManager().registerEvents(new CustomBlockPlaceListener(this), this);
    }
}
