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

import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import me.ifamasssxd.cosmicguishop.commands.CommandBankNote;
import me.ifamasssxd.cosmicguishop.commands.CommandShop;
import me.ifamasssxd.cosmicguishop.commands.CommandXpShop;
import me.ifamasssxd.cosmicguishop.listeners.MenuListener;
import me.ifamasssxd.cosmicguishop.listeners.PlayerListener;
import me.ifamasssxd.cosmicguishop.listeners.XPShopMenuListener;
import me.ifamasssxd.cosmicguishop.struct.ShopManager;
import me.ifamasssxd.cosmicguishop.utils.Base64Utils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;

public class CosmicGUIShop extends JavaPlugin {
    private static CosmicGUIShop instance;
    private static CosmicGUIShop plugin;
    private static Economy economy;
    private ShopManager shopManager;

    public static CosmicGUIShop get() {
        return instance;
    }
    public static Economy getEconomy() {
        return economy;
    }

    @SneakyThrows
    public void onEnable() {
        instance = this;
        plugin = this;
        FileConfiguration config = this.getConfig();

        economy = this.getServer().getServicesManager().getRegistration(Economy.class).getProvider();
        this.shopManager = new ShopManager();
        try {
            this.shopManager.loadAllShopItems();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        this.getServer().getPluginManager().registerEvents(new MenuListener(), this);
        this.getServer().getPluginManager().registerEvents(new XPShopMenuListener(), this);
        this.getCommand("cosmicshop").setExecutor(new CommandShop());
        this.getCommand("banknote").setExecutor(new CommandBankNote());
        this.getCommand("xpshop").setExecutor(new CommandXpShop());

        // XPShop File
        File file = new File(this.getDataFolder() + File.separator + "config.yml");
        if (file.exists()) {
            this.getLogger().info("XP Shop Config Loaded");
        } else {
            String l = Base64Utils.toBase64(new ItemStack[]{new ItemStack(Material.EMERALD_ORE)}, 9) + ":10";
            ArrayList<String> list = Lists.newArrayList(l);
            this.getConfig().addDefault("me.astropvp.cratekeys.items", list);
            this.getLogger().info("Created XP Shop Config");
        }
    }

    @Override
    public void onDisable() {
        plugin = null;
    }

    public static CosmicGUIShop getInstance() {
        return plugin;
    }

    public void loadConfig() {
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
    }

    public ShopManager getShopManager() {
        return this.shopManager;
    }
}

