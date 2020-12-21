/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_7_R4.Entity
 *  org.bukkit.Bukkit
 *  org.bukkit.command.CommandSender
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.CreatureSpawnEvent
 */
package org.arkhamnetwork.Arkkit.patches;

import net.minecraft.server.v1_7_R4.Entity;
import org.arkhamnetwork.Arkkit.commands.CommandArkReboot;
import org.arkhamnetwork.Arkkit.patches.AntiCrash;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

class AntiCrashEntityListener
implements Listener {
    AntiCrashEntityListener() {
    }

    @EventHandler(ignoreCancelled=true)
    public void onEntitySpawn(CreatureSpawnEvent event) {
        int id = Entity.entityCount.get();
        if (id >= 2097483647) {
            if (AntiCrash.reboot_task_id != -1 || CommandArkReboot.restart_task_exists) {
                return;
            }
            Bukkit.getLogger().info("Rebooting due running out of entity ids: current id: " + id);
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)"arkreboot");
        }
    }
}

