package me.astropvp.astropvpskins;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.astropvpskins.commands.CommandGiveSkin;
import me.astropvp.astropvpskins.commands.CommandSkins;

import me.astropvp.astropvpskins.listeners.*;
import me.astropvp.astropvpskins.objects.skins.ItemSkinCacher;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class AstroItemSkinsPlugin
        extends JavaPlugin {
    public static AstroItemSkinsPlugin AstroItemSkinsPlugin;

    public void onEnable() {
        AstroItemSkinsPlugin = this;
        Bukkit.getLogger().info(String.valueOf(CC.Green) + "Loaded Astro Item Skins");

        registerListeners();
        registerCommands();
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                if (!p.isDead() && p.isOnline() && p.getInventory() != null && p.getInventory().getArmorContents() != null) {
                    new ItemSkinCacher(p);
                }
            }
        }, 5L, 100L); // 1 second asynchronous timer to update item skins.

    }

    public void onDisable() {
        for (ItemSkinCacher cacher : ItemSkinCacher.getCachers()) {
           ItemSkinCacher.removePlayer(cacher.getPlayer());
        }
        AstroItemSkinsPlugin = null;
    }

    public static AstroItemSkinsPlugin getInstance() {
        return AstroItemSkinsPlugin;
    }

    public void registerListeners() {
        registerListener((Listener) new ItemSkinApplyListener());
        registerListener((Listener) new ItemSkinRemoveListener());
        registerListener((Listener) new InventorySkinClickListener());
        registerListener((Listener) new ItemSkinProccessEventsListener());
        registerListener((Listener) new ItemSkinCacherListener());
        registerListener(new MauiHookListener());
    }

    public void registerCommands() {
        getCommand("giveskin").setExecutor((CommandExecutor) new CommandGiveSkin());
        getCommand("skins").setExecutor((CommandExecutor) new CommandSkins());
    }

    public void registerListener(Listener listener) {
        Bukkit.getServer().getPluginManager().registerEvents(listener, (Plugin) this);
    }
}


