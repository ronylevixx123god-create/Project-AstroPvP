package org.arkhamnetwork.Arkkit.commands;

import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.commands.CommandArkReboot;
import org.arkhamnetwork.Arkkit.patches.AntiCrash;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

final class ArkRebootListener
implements Listener {
    ArkRebootListener() {
    }

    @EventHandler(priority=EventPriority.LOW)
    public void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent e) {
        if (Arkkit.get().shutting_down || AntiCrash.reboot_task_id != -1 || CommandArkReboot.restart_task_exists) {
            e.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
            e.setKickMessage(ChatColor.RED + "You cannot join the server, it is currently rebooting.");
        }
    }
}

