/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.CosmicBossEggs.boss.type.Bosses
 *  com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI
 *  com.massivecraft.factions.Board
 *  com.massivecraft.factions.FLocation
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.CosmicBossEggs.boss.type.Bosses;
import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import java.util.Random;
import org.arkhamnetwork.Arkkit.patches.Easter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

final class EasterEggHunt
extends BukkitRunnable {
    private Easter instance;

    public EasterEggHunt(Easter easter) {
        this.instance = easter;
    }

    public void run() {
        if (!this.instance.isEaster()) {
            return;
        }
        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (!Board.getFactionAt((FLocation)new FLocation(pl.getLocation())).isWarZone()) continue;
            Location eggHunt = this.getNearbyLocation(pl.getLocation(), 32, 0);
            Bosses.CHICKEN.b.spawnBoss(eggHunt);
            for (int i = 0; i < 6; ++i) {
                CosmicSpaceChestsAPI.dropChest((Location)this.getNearbyLocation(eggHunt, 8, 40));
            }
            Bukkit.getServer().broadcastMessage("");
            Bukkit.getServer().broadcastMessage(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD.toString() + "(!) EVENT | " + (Object)ChatColor.DARK_AQUA + "An " + (Object)ChatColor.GOLD + "Easter Egg Hunt" + (Object)ChatColor.DARK_AQUA + " is nearby, defeat the evil easter chickens at " + (Object)ChatColor.GOLD + eggHunt.getBlockX() + "x, " + eggHunt.getBlockZ() + "z");
            Bukkit.getServer().broadcastMessage("");
            break;
        }
    }

    private Location getNearbyLocation(Location l, int radius, int y_boost) {
        Random rand = new Random();
        Location rand_loc = l.clone();
        rand_loc.add((double)((rand.nextBoolean() ? 1 : -1) * (rand.nextInt(radius) + 8)), 0.0, (double)((rand.nextBoolean() ? 1 : -1) * (rand.nextInt(radius) + 8)));
        rand_loc.add(0.5, (double)y_boost, 0.5);
        while ((rand_loc.getBlock().getType() != Material.AIR || rand_loc.getBlock().getLocation().subtract(0.0, 1.0, 0.0).getBlock().getType() != Material.AIR) && rand_loc.getY() < 255.0) {
            rand_loc = rand_loc.add(0.0, 1.0, 0.0);
        }
        return rand_loc;
    }
}

