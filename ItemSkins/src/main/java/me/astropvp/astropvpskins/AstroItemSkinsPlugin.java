package me.astropvp.astropvpskins;

import me.astropvp.astropvpskins.armorequip.ArmorListener;
import me.astropvp.astropvpskins.commands.CommandItemSkin;
import me.astropvp.astropvpskins.commands.CommandMultiSkin;
import me.astropvp.astropvpskins.listeners.ItemSkinInventoryClickListener;
import me.astropvp.astropvpskins.listeners.ItemSkinListener;
import me.astropvp.astropvpskins.listeners.MauiHookListener;
import me.astropvp.astropvpskins.listeners.SnowflakeListener;
import org.bukkit.plugin.java.JavaPlugin;

public class AstroItemSkinsPlugin extends JavaPlugin {
    private static AstroItemSkinsPlugin instance;

    public void onEnable() {
        instance = this;
        getCommand("itemskin").setExecutor(new CommandItemSkin());
        getCommand("multiskin").setExecutor(new CommandMultiSkin());

        getServer().getPluginManager().registerEvents(new ItemSkinListener(), this);
        getServer().getPluginManager().registerEvents(new ArmorListener(getConfig().getStringList("blocked")), this);
        getServer().getPluginManager().registerEvents(new ItemSkinInventoryClickListener(), this);
        getServer().getPluginManager().registerEvents(new MauiHookListener(), this);
        getServer().getPluginManager().registerEvents(new SnowflakeListener(), this);
    }

    public void onDisable() {
        instance = null;
    }

    public static AstroItemSkinsPlugin getInstance() { return instance; }
}
