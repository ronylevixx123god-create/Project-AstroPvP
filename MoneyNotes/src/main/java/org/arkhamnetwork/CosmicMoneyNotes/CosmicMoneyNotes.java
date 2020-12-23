/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.milkbowl.vault.economy.Economy
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.RegisteredServiceProvider
 *  org.bukkit.plugin.java.JavaPlugin
 */
package org.arkhamnetwork.CosmicMoneyNotes;

import net.milkbowl.vault.economy.Economy;
import org.arkhamnetwork.CosmicMoneyNotes.commands.CommandBankNote;
import org.arkhamnetwork.CosmicMoneyNotes.listeners.PlayerListener;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class CosmicMoneyNotes
extends JavaPlugin {
    private static Economy economy = null;

    public void onEnable() {
        this.setupEconomy();
        this.registerCommands();
        this.registerListeners();
    }

    public void onDisable() {
    }

    public static Economy getEconomy() {
        return economy;
    }

    private void registerListeners() {
        this.getServer().getPluginManager().registerEvents((Listener)new PlayerListener(), (Plugin)this);
    }

    private void registerCommands() {
        this.getCommand("banknote").setExecutor((CommandExecutor)new CommandBankNote());
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider economyProvider = this.getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null) {
            economy = (Economy)economyProvider.getProvider();
        }
        return economy != null;
    }
}

