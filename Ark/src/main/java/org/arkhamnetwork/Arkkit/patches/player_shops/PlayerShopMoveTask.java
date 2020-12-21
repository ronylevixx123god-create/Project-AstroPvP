/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches.player_shops;

import java.util.Map;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.player_shops.PlayerShops;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerShopMoveTask
extends BukkitRunnable {
    public void run() {
        for (Map.Entry<Player, Location> data : PlayerShops.player_shop_locations.entrySet()) {
            final Player pl = data.getKey();
            final Location shop_loc = data.getValue();
            if (pl == null || !pl.isOnline() || !(pl.getLocation().distanceSquared(data.getValue()) >= 1.0)) continue;
            Bukkit.getServer().getScheduler().runTask((Plugin)Arkkit.get(), new Runnable(){

                @Override
                public void run() {
                    pl.teleport(shop_loc);
                    pl.sendMessage((Object)ChatColor.RED + "You " + (Object)ChatColor.UNDERLINE + "cannot" + (Object)ChatColor.RED + " move with an active /shop.");
                    pl.sendMessage((Object)ChatColor.GRAY + "Type " + (Object)ChatColor.RED + "/shop close" + (Object)ChatColor.GRAY + " to re-enable movement.");
                }
            });
        }
    }
}

