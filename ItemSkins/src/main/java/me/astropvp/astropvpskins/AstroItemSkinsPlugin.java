/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  org.bukkit.Bukkit
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 */
package me.astropvp.astropvpskins;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.astropvpskins.commands.CommandGiveSkin;
import me.astropvp.astropvpskins.commands.CommandSkins;
import me.astropvp.astropvpskins.listeners.InventorySkinClickListener;
import me.astropvp.astropvpskins.listeners.ItemSkinApplyListener;
import me.astropvp.astropvpskins.listeners.ItemSkinCacherListener;
import me.astropvp.astropvpskins.listeners.ItemSkinProccessEventsListener;
import me.astropvp.astropvpskins.listeners.ItemSkinRemoveListener;
import me.astropvp.astropvpskins.objects.skins.ItemSkinCacher;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class AstroItemSkinsPlugin
extends JavaPlugin {
    public static AstroItemSkinsPlugin AstroItemSkinsPlugin;

    public void onEnable() {
        AstroItemSkinsPlugin = this;
        Bukkit.getLogger().info(String.valueOf(CC.Green) + "Loaded Astro Item Skins");
        this.registerListeners();
        this.registerCommands();
        Bukkit.getScheduler().runTaskTimer((Plugin)this, () -> {
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                if (p.isDead() || !p.isOnline() || p.getInventory() == null || p.getInventory().getArmorContents() == null) continue;
                new ItemSkinCacher(p);
            }
        }, 1L, 100L);
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
        this.registerListener(new ItemSkinApplyListener());
        this.registerListener(new ItemSkinRemoveListener());
        this.registerListener(new InventorySkinClickListener());
        this.registerListener(new ItemSkinProccessEventsListener());
        this.registerListener(new ItemSkinCacherListener());
    }

    public void registerCommands() {
        this.getCommand("giveskin").setExecutor((CommandExecutor)new CommandGiveSkin());
        this.getCommand("skins").setExecutor((CommandExecutor)new CommandSkins());
    }

    public void registerListener(Listener listener) {
        Bukkit.getServer().getPluginManager().registerEvents(listener, (Plugin)this);
    }
}

