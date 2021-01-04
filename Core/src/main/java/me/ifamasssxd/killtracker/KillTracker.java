/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 */
package me.ifamasssxd.killtracker;

import me.ifamasssxd.killtracker.commands.CommandGiveSoulGem;
import me.ifamasssxd.killtracker.commands.CommandGiveTracker;
import me.ifamasssxd.killtracker.commands.CommandWithdrawSouls;
import me.ifamasssxd.killtracker.listeners.ItemListener;
import me.ifamasssxd.killtracker.listeners.PlayerListener;
import me.ifamasssxd.killtracker.listeners.SoulGemListener;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class KillTracker
extends JavaPlugin {
    public static String LORE_SUFFIX = ":  ";
    private static KillTracker instance;

    public void onEnable() {
        instance = this;
        this.registerCommands();
        this.registerListener();
    }

    public void registerListener() {
        this.getServer().getPluginManager().registerEvents((Listener)new PlayerListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new ItemListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new SoulGemListener(), (Plugin)this);
    }

    public void registerCommands() {
        this.getCommand("withdrawsouls").setExecutor((CommandExecutor)new CommandWithdrawSouls());
        this.getCommand("givetracker").setExecutor((CommandExecutor)new CommandGiveTracker());
        this.getCommand("givesoulgem").setExecutor((CommandExecutor)new CommandGiveSoulGem());
    }

    public static KillTracker get() {
        return instance;
    }
}

