// 
// Decompiled by Procyon v0.5.36
// 

package net.astropvp;

import java.io.File;

import net.astropvp.commands.CommandSandbot;
import net.astropvp.config.FilesManager;
import net.astropvp.enums.GuiType;
import net.astropvp.inventories.MenuManager;
import net.astropvp.listeners.AntiDupeListener;
import net.astropvp.managers.SandBotManager;
import net.astropvp.utils.Utils;
import net.astropvp.utils.inventory.InventoryManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import net.astropvp.tasks.BlockSpawnTask;
import net.astropvp.listeners.PlayerListeners;
import org.bukkit.plugin.Plugin;
import net.astropvp.tasks.AutoSaveTask;
import org.bukkit.Bukkit;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Set;
import org.bukkit.plugin.java.JavaPlugin;

public final class SandBots extends JavaPlugin
{
    public static Set<SandBotManager> sandBots = ConcurrentHashMap.newKeySet();
    private static InventoryManager invManager;
    public static ConcurrentHashMap<SandBotManager, ConcurrentHashMap<GuiType, MenuManager>> botGuis = new ConcurrentHashMap<>();
    private FilesManager manager;
    public static BukkitTask spawnSandBlocks;

    public SandBots() {
        this.manager = new FilesManager(this);
    }

    public void onEnable() {
        invManager = new InventoryManager(this);
        invManager.init();
        new AntiDupeListener(this);
        Utils.setupConfigFiles(this.manager);
        Utils.loadDataOfSandbots(this.manager);
        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously((Plugin)this, (Runnable)new AutoSaveTask(this), 0L, 6000L);
        new CommandSandbot(this);
        new PlayerListeners(this);
        SandBots.spawnSandBlocks = Bukkit.getScheduler().runTaskTimerAsynchronously((Plugin)this, () -> Bukkit.getScheduler().runTask((Plugin)this, (Runnable)new BlockSpawnTask(this)), 0L, (long)this.getConfig().getInt("sand-spawn-speed"));
    }
    
    public void onDisable() {
        Utils.saveDataOfSandbots(this.manager);
    }
    
    public FilesManager getManager() {
        return this.manager;
    }

    public File getPluginFile() {
        return this.getFile();
    }

    public static InventoryManager getInvManager() {
        return invManager;
    }

}
