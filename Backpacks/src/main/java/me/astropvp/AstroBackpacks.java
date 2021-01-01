package me.astropvp;

import me.astropvp.commands.CommandGiveBackpack;
import me.astropvp.events.ArmorListener;
import me.astropvp.listeners.BackpackListener;
import me.astropvp.listeners.PlayerDamageListener;
import me.astropvp.listeners.PortablePicnicListener;
import me.astropvp.objects.Backpack;
import me.astropvp.tasks.PortablePicnicTask;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class AstroBackpacks extends JavaPlugin {
    private static AstroBackpacks plugin;

    public void onEnable() {
        plugin = this;
        registerListeners();
        registerCommands();
        Bukkit.getScheduler().runTaskTimer((Plugin)this, new PortablePicnicTask(), 0L, 200L);
    }

    @Override
    public void onDisable() {
        plugin = null;
    }

    public static AstroBackpacks getInstance() {
        return plugin;
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new BackpackListener(), (Plugin) this);
        getServer().getPluginManager().registerEvents(new PlayerDamageListener(), (Plugin) this);
        getServer().getPluginManager().registerEvents(new PortablePicnicListener(), (Plugin) this);
        getServer().getPluginManager().registerEvents(new ArmorListener(getConfig().getStringList("blocked")), (Plugin)this);
    }

    private void registerCommands() {
        getCommand("givebackpack").setExecutor((CommandExecutor) new CommandGiveBackpack());
    }
}
