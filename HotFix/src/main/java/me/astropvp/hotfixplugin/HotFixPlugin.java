package me.astropvp.hotfixplugin;

import me.astropvp.astropvpskins.commands.CommandMultiSkin;
import me.astropvp.hotfixplugin.commands.FixSlotsCommand;
import me.astropvp.hotfixplugin.listeners.DamageListener;
import me.astropvp.hotfixplugin.listeners.TeleportSafetyListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class HotFixPlugin extends JavaPlugin {
    private static HotFixPlugin instance;


    @Override
    public void onEnable() {
        instance = this;
        System.out.println("[HotFix] HotFixPlugin has been Loaded!");
        Bukkit.getPluginManager().registerEvents(new DamageListener(), this);
        Bukkit.getPluginManager().registerEvents(new TeleportSafetyListener(), this);
        getCommand("fixslot").setExecutor(new FixSlotsCommand());

    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static HotFixPlugin getInstance() {
        return instance;
    }
}
