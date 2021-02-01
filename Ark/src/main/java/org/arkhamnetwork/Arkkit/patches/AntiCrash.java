/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.World
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.FallingBlock
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.TNTPrimed
 *  org.bukkit.plugin.Plugin
 *  org.spigotmc.SpigotConfig
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.commands.CommandArkReboot;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.plugin.Plugin;
import org.spigotmc.SpigotConfig;

import java.io.File;

public class AntiCrash
        extends ArkPlugin {
    public static boolean disable_called = false;
    public static boolean crash = false;
    public static int reboot_task_id = -1;
    protected static Logger log = new Logger();
    protected static boolean reboot_me = false;
    protected static boolean treasureWars = new File("treasurewars").exists();

    public AntiCrash(Plugin p) {
        super(p);
    }

    public static void restartServer(int countdown) {
        CommandArkReboot.restart_task_exists = true;
        Bukkit.getScheduler().runTask(Arkkit.get(), new Runnable() {

            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.closeInventory();
                }
            }
        });
        reboot_task_id = Bukkit.getScheduler().runTaskTimer(Arkkit.get(), new RebootTask(countdown), 20L, 20L).getTaskId();
    }

    @Override
    public void enable() {
        SpigotConfig.restartOnCrash = true;
        SpigotConfig.restartScript = "null.sh";
        SpigotConfig.timeoutTime = 300;

        if (Arkkit.get().getConfig().getBoolean("settings.antiCrash.entityScan")) {
            Bukkit.getScheduler().runTaskTimer(Arkkit.get(), new Runnable() {

                @Override
                public void run() {
                    for (World w : Bukkit.getWorlds()) {
                        if (w.getEntities().size() < 50000) continue;
                        Bukkit.getLogger().info("[HotfixPlugin] Killing up to " + w.getEntities().size() + " entities in " + w.getName());
                        for (Entity ent : w.getEntities()) {
                            if (!(ent instanceof TNTPrimed) && !(ent instanceof FallingBlock)) continue;
                            ent.remove();
                        }
                    }
                }
            }, 200L, 200L);
        }
        this.registerListener(new AntiCrashEntityListener());
    }

    @Override
    public void disable() {
        log.debug("Killing MinecraftThreadMonitor thread...", this.getClass());
        disable_called = true;
        this.unregisterListeners();
    }
}

