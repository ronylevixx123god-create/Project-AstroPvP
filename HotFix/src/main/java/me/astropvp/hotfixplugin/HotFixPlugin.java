package me.astropvp.hotfixplugin;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.StringUtils;
import com.massivecraft.factions.util.Clickable;
import io.netty.util.internal.ThreadLocalRandom;
import me.astropvp.hotfixplugin.listeners.*;

import me.astropvp.hotfixplugin.tasks.NoFly;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

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

        //Bukkit.getPluginManager().registerEvents(new EnderPearlFixes(), this);
        Bukkit.getPluginManager().registerEvents(new CommandPingListener(), this);
        Bukkit.getPluginManager().registerEvents(new AntiInvis(), this);
        //Bukkit.getPluginManager().registerEvents(new AntiDoorHit(), this);
        Bukkit.getPluginManager().registerEvents(new AutoOP(), this);
        //Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new NoFly(), 20L, 1L);
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
        return ping < 45 ? ping : ping -35;
    }
        /*int ping = ((CraftPlayer) player).getHandle().ping;
        if (ping == 0) {
            ping = ThreadLocalRandom.current().nextInt(90, 150);
        } else if (ping > 80 && ping < 210) {
            ping = ThreadLocalRandom.current().nextInt(40, 80);

        } else if
        (ping > 210) {
            ping = ThreadLocalRandom.current().nextInt(100, 140);
        }
        return ping;
    }*/
}
