/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.wimbli.WorldBorder.BorderData
 *  com.wimbli.WorldBorder.WorldBorder
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerTeleportEvent
 *  org.bukkit.event.player.PlayerTeleportEvent$TeleportCause
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.wimbli.WorldBorder.BorderData;
import com.wimbli.WorldBorder.WorldBorder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

class WorldBorderEnderpearlListener
        implements Listener {
    WorldBorder plugin;

    WorldBorderEnderpearlListener() {
    }

    public WorldBorder get() {
        if (this.plugin == null) {
            this.plugin = (WorldBorder) Bukkit.getPluginManager().getPlugin("WorldBorder");
        }
        return this.plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onProjectileLand(PlayerTeleportEvent event) {
        BorderData data;
        Player player = event.getPlayer();
        if (event.getCause() != PlayerTeleportEvent.TeleportCause.ENDER_PEARL || player.isOp()) {
            return;
        }
        Location location = player.getLocation();
        WorldBorder border = this.get();
        if (border != null && (data = border.getWorldBorder(location.getWorld().getName())) != null && !data.insideBorder(location)) {
            event.setCancelled(true);
            player.sendMessage(CC.RedB + "(!) " + CC.Red + "You cannot teleport outside of the WorldBorder with an Ender Pearl!");
        }
    }
}

