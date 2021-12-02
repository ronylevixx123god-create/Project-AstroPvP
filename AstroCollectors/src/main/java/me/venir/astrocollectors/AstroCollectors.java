package me.venir.astrocollectors;

import com.earth2me.essentials.Essentials;
import me.astropvp.astrospawners.AstroSpawners;
import me.venir.astrocollectors.collector.CollectorManager;
import me.venir.astrocollectors.commands.CollectorGiveCommand;
import me.venir.astrocollectors.commands.RemoveCollectorCommand;
import me.venir.astrocollectors.listeners.InventoryClickListener;
import me.venir.astrocollectors.listeners.collectorlisteners.BlockBreakListener;
import me.venir.astrocollectors.listeners.collectorlisteners.BlockPlaceListener;
import me.venir.astrocollectors.listeners.collectorlisteners.PlayerInteractListener;
import me.venir.astrocollectors.listeners.collectorlisteners.StackedMobDeathListener;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class AstroCollectors extends JavaPlugin {

    private final File collectorsFile = new File(this.getDataFolder(), "collectors.json");
    private static Economy econ = null;
    private Essentials essentials;
    public static AstroCollectors INSTANCE;


    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEconomy() {
        return econ;
    }


    @Override
    public void onEnable() {
        INSTANCE = this;
        CollectorManager.DELEGATE.loadFromFile(collectorsFile);
        getCommand("c").setExecutor(new CollectorGiveCommand());
        getCommand("removecollector").setExecutor(new RemoveCollectorCommand());
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new BlockPlaceListener(), this);
        pm.registerEvents(new BlockBreakListener(this), this);
        pm.registerEvents(new InventoryClickListener(), this);
        pm.registerEvents(new PlayerInteractListener(this), this);


        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            if (AstroSpawners.getInstance().getMobStackerUtils().getMobMap().size() > 65000) {
                Bukkit.broadcastMessage(ChatColor.GREEN + "Cleared all stacked mobs on the server!");
                Bukkit.getWorld("world").getEntities().forEach(entity -> {
                    if (AstroSpawners.getInstance().getMobStackerUtils().isStackedMob(entity)) {
                        AstroSpawners.getInstance().getMobStackerUtils().removeStackedMob(entity);
                    }
                });
                AstroSpawners.getInstance().getMobStackerUtils().getMobMap().clear();
            }

        }, 500L, 6000L);
        pm.registerEvents(new StackedMobDeathListener(this), this);
        if (!setupEconomy()) {
            System.out.println("No economy plugin found. Disabling Vault");
            getServer().getPluginManager().disablePlugin(this);
        }
        if (!Bukkit.getPluginManager().isPluginEnabled("Essentials")) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[Astro] " + ChatColor.RED + "Essentials is not loaded.");
        } else {
            essentials = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");
        }
    }

    @Override
    public void onDisable() {
        CollectorManager.DELEGATE.saveToFile(collectorsFile);
    }

    public Essentials getEssentials() {
        return essentials;
    }
}
