/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 */
package com.conaxgames.factions.utils.flying;

import java.util.HashSet;

import com.conaxgames.factions.utils.libs.ParticleEffect;
import com.massivecraft.factions.P;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CosmicFlyingParticleTask
implements Runnable {
    @Override
    public void run() {
        HashSet<Location> to_play = new HashSet<Location>();
        for (Player pl : CosmicFlyingPlugin.get_().getAsyncPlayerMap().getPlayers().values()) {
            if (!pl.isFlying() || pl.isOp() || pl.hasPermission("essentials.fly.noparticles")) continue;
            Location ploc = pl.getLocation().clone();
            to_play.add(ploc);
        }
        final Location[] locs = to_play.toArray(new Location[0]);
        Bukkit.getScheduler().runTaskAsynchronously(P.getP(), new Runnable(){

            @Override
            public void run() {
                for (Location loc : locs) {
                    try {
                        if (loc.getWorld().getName().equals("cosmic-pvparenas") || loc.getWorld().getName().equals("world_duels") || loc.getWorld().getName().equals("world_duels2")) continue;
                        ParticleEffect.sendToLocation(ParticleEffect.CLOUD, loc.subtract(0.0, 0.5, 0.0), 0.0f, 0.0f, 0.0f, 0.005f, 5);
                    }
                    catch (Exception err) {
                        err.printStackTrace();
                    }
                }
            }
        });
    }
}

