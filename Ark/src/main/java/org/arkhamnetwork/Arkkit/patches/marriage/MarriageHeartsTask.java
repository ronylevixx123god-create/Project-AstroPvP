/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches.marriage;

import java.util.UUID;
import org.arkhamnetwork.Arkkit.patches.marriage.MarriageDataManager;
import org.arkhamnetwork.Arkkit.patches.marriage.MarriageParticleEffect;
import org.arkhamnetwork.Arkkit.patches.marriage.MarriagePlayerList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

public class MarriageHeartsTask
extends BukkitRunnable {
    public void debug(String s) {
        Bukkit.getLogger().info(s);
    }

    public void run() {
        for (String name : MarriagePlayerList.async_player_map.keySet()) {
            UUID p_uuid = MarriageDataManager.getPartnerUUID(MarriagePlayerList.async_player_map.get(name).getUniqueId());
            if (p_uuid == null || Bukkit.getPlayer((UUID)p_uuid) == null) continue;
            Location loc = MarriagePlayerList.async_player_map.get(name).getLocation();
            if (!Bukkit.getPlayer((UUID)p_uuid).getWorld().equals((Object)loc.getWorld()) || !(Bukkit.getPlayer((UUID)p_uuid).getLocation().distanceSquared(loc) < 10.0)) continue;
            try {
                MarriageParticleEffect.sendToLocation(MarriageParticleEffect.HEART, loc.add(Math.random(), 1.0 + Math.random(), Math.random()), 0.0f, 0.0f, 0.0f, 0.01f, 5);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

