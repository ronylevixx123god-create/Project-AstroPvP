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

import com.massivecraft.factions.P;
import de.inventivegames.particle.ParticleEffect;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;

public class CosmicFlyingParticleTask
implements Runnable {
    @SneakyThrows
    @Override
    public void run() {
        HashSet<Player> to_play_player = new HashSet<Player>();
        for (Player pl : CosmicFlyingPlugin.get_().getAsyncPlayerMap().getPlayers().values()) {
            if (!pl.isFlying() || pl.isOp() || pl.hasPermission("essentials.fly.noparticles")) continue;
            to_play_player.add(pl);
        }
        Bukkit.getScheduler().runTaskAsynchronously(P.getP(), new Runnable(){

            @Override
            public void run() {
                for (Player player : to_play_player) {
                    try {
                        if (player.getWorld().getName().equals("cosmic-pvparenas")
                                || player.getWorld().getName().equals("world_duels")
                                || player.getWorld().getName().equals("world_duels2")) continue;
                    }
                    catch (Exception err) {
                        err.printStackTrace();
                    }
                }
            }
        });
    }
}

