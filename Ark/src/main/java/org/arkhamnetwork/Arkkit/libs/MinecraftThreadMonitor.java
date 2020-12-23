/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  me.vaqxine.NetworkManager.NetworkManager
 *  net.minecraft.server.v1_7_R4.MinecraftServer
 *  org.bukkit.Bukkit
 *  org.spigotmc.WatchdogThread
 */
package org.arkhamnetwork.Arkkit.libs;

import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.commands.CommandArkReboot;
import org.arkhamnetwork.Arkkit.patches.AntiCrash;
import org.bukkit.Bukkit;
import org.spigotmc.WatchdogThread;

import java.io.File;

public class MinecraftThreadMonitor
        extends Thread {
    public static int non_response = 0;
    public long previous_tick = 0L;
    long maxMemoryMB = 0L;
    long last_GC = 0L;

    @Override
    public void run() {
        this.maxMemoryMB = Runtime.getRuntime().maxMemory() / 0x100000L;
        while (true) {
            long l;
            try {
                Thread.sleep(5000L);
            } catch (Exception exception) {
                System.out.println("This stack trace is only to inform that the monitor thread was interrupted. The thread has still been killed.");
                exception.printStackTrace();
                System.out.println("Exiting Monitor Thread...");
                return;
            }
            if (AntiCrash.reboot_task_id == -1) {
                l = Runtime.getRuntime().totalMemory() / 0x100000L;
                long l2 = Runtime.getRuntime().freeMemory() / 0x100000L;
                if ((double) l2 <= (double) this.maxMemoryMB * 1.0E-4) {
                    Bukkit.getLogger().info("maxMemoryMB Reee = " + this.maxMemoryMB);
                    Bukkit.getLogger().info("currentMemoryMB = " + l);
                    Bukkit.getLogger().info("memLeftMB = " + (this.maxMemoryMB - l));
                    Bukkit.getLogger().info("freeMemMB = " + l2);
                    if (System.currentTimeMillis() - this.last_GC <= 900000L) {
                        return;
                    }
                    return;
                }
                File file = new File("/minecraft");
                if ((double) file.getUsableSpace() <= 5.0E9) {
                    Bukkit.getLogger().info("REEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
                    Bukkit.getLogger().info("[Arkkit (AntiCrash)] Less than 5GB of space left on primary partition, whitelisting and rebooting server.");
                    Bukkit.setWhitelist(true);
                    return;
                }
            }
            l = MinecraftServer.currentTick;
            if (this.previous_tick != 0L && l == this.previous_tick || !MinecraftServer.getServer().isRunning()) {
                if (non_response == 24) {
                    Bukkit.getLogger().info("The main thread cannot be recovered, attempting restart...");
                    if (new File("plugins/ArkhamHub.jar").exists()) {
                        Bukkit.getLogger().info("Force killing JVM.");
                        Runtime.getRuntime().exit(-1);
                    } else {
                        AntiCrash.crash = true;
                    }
                    ++non_response;
                    continue;
                }
                if (AntiCrash.disable_called ? non_response >= 162 : non_response >= 90) {
                    Bukkit.getLogger().info("Force killing JVM.");
                    Runtime.getRuntime().exit(-1);
                    return;
                }
                Bukkit.getLogger().info("The server's main thread seems to be frozen!");
                Bukkit.getLogger().info("previous_tick=" + this.previous_tick + ", current_tick=" + l);
                Bukkit.getLogger().info("non_response = " + ++non_response);
                if (non_response >= 5 && non_response != 15 || Arkkit.get().shutting_down || AntiCrash.crash || CommandArkReboot.restart_task_exists || Bukkit.getPluginManager().getPlugin("NetworkManager") != null)
                    continue;
                WatchdogThread.dumpStack();
                continue;
            }
            this.previous_tick = l;
            if (non_response > 0) {
                Bukkit.getLogger().info("Server thread recovered! YAY!");
            }
            non_response = 0;
        }
    }
}

