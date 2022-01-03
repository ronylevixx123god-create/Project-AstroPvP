package com.compilex.CosmicKOTH.listeners;

import com.compilex.CosmicKOTH.CosmicKOTHPlugin;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import org.arkhamnetwork.Arkkit.patches.CombatLog;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class KothPortalListener implements Listener {



    @EventHandler
    public void onPortalEnter(EntityPortalEnterEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (player.getWorld().getName().equals("world") && WorldGuardUtils.getRegionsIn(player.getLocation()).contains("desertkoth2")) {
                Location location = new Location(Bukkit.getWorld("world"), 2422, 120, 4638);
                Bukkit.getScheduler().runTaskLater(CosmicKOTHPlugin.get_(), () -> player.teleport(location, PlayerTeleportEvent.TeleportCause.UNKNOWN), 3L);
            }
        }
    }
}
