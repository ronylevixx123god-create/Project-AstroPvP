/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 */
package org.arkhamnetwork.Arkkit;

import com.cosmicpvp.cosmicutils.utils.CC;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

final class ArkkitListener
implements Listener {
    private List<String> blockedCommandsWhileRebooting = Lists.newArrayList("/pv", "/trade", "/playervaults", "/chest", "/vault", "/enderchest", "/echest", "/betterenderchest", "/ec", "/bec");

    ArkkitListener() {
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onPlayerJoin(PlayerJoinEvent e) {
        Arkkit.get().getAsyncPlayerMap().put(e.getPlayer().getName(), e.getPlayer());
        Arkkit.get().getPlayerLocations().put(e.getPlayer().getName(), e.getPlayer().getLocation());
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent e) {
        Arkkit.get().getAsyncPlayerMap().remove(e.getPlayer().getName());
        Arkkit.get().getPlayerLocations().remove(e.getPlayer().getName());
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onPlayerCommandWhileRebooting(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (event.getMessage() == null || !Arkkit.get().isRebooting()) {
            return;
        }
        String command = (event.getMessage().contains(" ") ? event.getMessage().split(" ")[0] : event.getMessage()).toLowerCase();
        if (this.blockedCommandsWhileRebooting.contains(command)) {
            if (player.isOp()) {
                player.sendMessage(CC.Red + "This command is disabled during the reboot, but since you're OP you can run it.");
                return;
            }
            event.setCancelled(true);
            event.setMessage("/null");
            Bukkit.getLogger().info("(CommandRestriction) Disabling " + command + " for " + player.getName() + " due to reboot.");
            player.sendMessage(CC.RedB + "(!) " + CC.Red + "You cannot use this command while the server is rebooting!");
        }
    }
}

