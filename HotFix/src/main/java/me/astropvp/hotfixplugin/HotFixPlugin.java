package me.astropvp.hotfixplugin;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.StringUtils;
import com.massivecraft.factions.util.Clickable;
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
        Bukkit.getPluginManager().registerEvents(new AntiInvis(), this);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            Bukkit.getServer().dispatchCommand( Bukkit.getConsoleSender(), "citizens reload");
        }, 400L, 400);


        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, () -> {
            Clickable spaceClickable = new Clickable("");
                spaceClickable.add(StringUtils.getCenteredMessage(CC.translate("&7You can purchase it by clicking &f&nhere&7.")), CC.translate("&7/buy"), "/buy");
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendMessage("");
                    if (Math.random() < 0.5) {
                        player.sendMessage(StringUtils.getCenteredMessage(CC.translate("&6We released the new &f&lLootbox: &f&k;&r &8&lFriday the &7&l13th &f&k;&r &6on the store.")));
                    } else {
                        player.sendMessage(StringUtils.getCenteredMessage(CC.translate("&6We released the new &7&lUnlucky &f&lBundle&r &6on the store.")));
                    }
                    spaceClickable.sendToPlayer(player);
                    player.sendMessage("");

                }
        }, 40L, 3*20*60);
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
