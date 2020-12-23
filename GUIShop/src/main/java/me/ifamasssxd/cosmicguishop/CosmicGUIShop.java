/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.milkbowl.vault.economy.Economy
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 */
package me.ifamasssxd.cosmicguishop;

import lombok.SneakyThrows;
import me.ifamasssxd.cosmicguishop.commands.CommandShop;
import me.ifamasssxd.cosmicguishop.listeners.MenuListener;
import me.ifamasssxd.cosmicguishop.listeners.PlayerListener;
import me.ifamasssxd.cosmicguishop.struct.ShopManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class CosmicGUIShop
extends JavaPlugin {
    private static CosmicGUIShop instance;
    private ShopManager shopManager;
    private static Economy economy;

    @SneakyThrows
    public void onEnable() {
        instance = this;
        economy = (Economy)this.getServer().getServicesManager().getRegistration(Economy.class).getProvider();
        this.shopManager = new ShopManager();
        this.shopManager.loadAllShopItems();
        this.getServer().getPluginManager().registerEvents((Listener)new PlayerListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new MenuListener(), (Plugin)this);
        this.getCommand("cosmicshop").setExecutor((CommandExecutor)new CommandShop());
    }

    public static CosmicGUIShop get() {
        return instance;
    }

    public ShopManager getShopManager() {
        return this.shopManager;
    }

    public static Economy getEconomy() {
        return economy;
    }
}

