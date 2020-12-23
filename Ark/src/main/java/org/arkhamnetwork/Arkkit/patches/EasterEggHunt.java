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
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

final class EasterEggHunt
        extends BukkitRunnable {
    private final Easter instance;

    public EasterEggHunt(Easter easter) {
        this.instance = easter;
    }

    public void run() {
        if (!this.instance.isEaster()) {
            return;
        }
        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (!Board.getFactionAt(new FLocation(pl.getLocation())).isWarZone()) continue;
            Location eggHunt = this.getNearbyLocation(pl.getLocation(), 32, 0);
            Bosses.CHICKEN.b.spawnBoss(eggHunt);
            for (int i = 0; i < 6; ++i) {
                CosmicSpaceChestsAPI.dropChest(this.getNearbyLocation(eggHunt, 8, 40));
            }
            Bukkit.getServer().broadcastMessage("");
            Bukkit.getServer().broadcastMessage(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD.toString() + "(!) EVENT | " + ChatColor.DARK_AQUA + "An " + ChatColor.GOLD + "Easter Egg Hunt" + ChatColor.DARK_AQUA + " is nearby, defeat the evil easter chickens at " + ChatColor.GOLD + eggHunt.getBlockX() + "x, " + eggHunt.getBlockZ() + "z");
            Bukkit.getServer().broadcastMessage("");
            break;
        }
    }

    private Location getNearbyLocation(Location l, int radius, int y_boost) {
        Random rand = new Random();
        Location rand_loc = l.clone();
        rand_loc.add((rand.nextBoolean() ? 1 : -1) * (rand.nextInt(radius) + 8), 0.0, (rand.nextBoolean() ? 1 : -1) * (rand.nextInt(radius) + 8));
        rand_loc.add(0.5, y_boost, 0.5);
        while ((rand_loc.getBlock().getType() != Material.AIR || rand_loc.getBlock().getLocation().subtract(0.0, 1.0, 0.0).getBlock().getType() != Material.AIR) && rand_loc.getY() < 255.0) {
            rand_loc = rand_loc.add(0.0, 1.0, 0.0);
        }
        return rand_loc;
    }
}

