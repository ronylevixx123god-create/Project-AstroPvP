package me.astropvp.hotfixplugin;

import io.netty.util.internal.ThreadLocalRandom;
import me.astropvp.hotfixplugin.listeners.*;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.DecimalFormat;
import java.util.Objects;

public class HotFixPlugin extends JavaPlugin {
    private static HotFixPlugin instance;
    public static final DecimalFormat df = new DecimalFormat("#.#");


    @Override
    public void onEnable() {
        instance = this;
        System.out.println("[HotFix] HotFixPlugin has been Loaded!");
        Bukkit.getPluginManager().registerEvents(new DamageListener(), this);
        Bukkit.getPluginManager().registerEvents(new TeleportSafetyListener(), this);

        Bukkit.getPluginManager().registerEvents(new EnderPearlFixes(), this);
        Bukkit.getPluginManager().registerEvents(new CommandPingListener(), this);

        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, () -> {
            Bukkit.getServer().dispatchCommand( Bukkit.getConsoleSender(), "citizens reload");
        }, 400L, 200L);
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static HotFixPlugin getInstance() {
        return instance;
    }

    public static int getPing(Player player) {
        int ping = ((CraftPlayer) player).getHandle().ping;
        if (ping == 0) {
            ping = ThreadLocalRandom.current().nextInt(90, 150);
        } else if (ping > 80 && ping < 210) {
            ping = ThreadLocalRandom.current().nextInt(40, 80);

        } else if
        (ping > 210) {
            ping = ThreadLocalRandom.current().nextInt(100, 140);
        }
        return ping;
    }
}
