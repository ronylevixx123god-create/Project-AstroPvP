/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.GameMode
 *  org.bukkit.entity.Player
 *  org.bukkit.event.player.PlayerTeleportEvent$TeleportCause
 */
package com.compilex.CosmicKOTH.tasks;

import com.compilex.CosmicKOTH.CosmicKOTHAPI;
import com.compilex.CosmicKOTH.CosmicKOTHPlugin;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

public class KothSanityTask
implements Runnable {
    @Override
    public void run() {
        if (CosmicKOTHPlugin.get_().getWorld() != null && !CosmicKOTHAPI.isKOTHRunning()) {
            for (Player p : CosmicKOTHPlugin.get_().getWorld().getPlayers()) {
                if (p.isOp() || p.getGameMode() == GameMode.CREATIVE) continue;
                p.teleport(Bukkit.getWorld((String)"world").getSpawnLocation(), PlayerTeleportEvent.TeleportCause.UNKNOWN);
            }
        }
    }
}

