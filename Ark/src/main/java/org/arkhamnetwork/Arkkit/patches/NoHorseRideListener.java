/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Horse
 *  org.bukkit.entity.Pig
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.vehicle.VehicleEnterEvent
 */
package org.arkhamnetwork.Arkkit.patches;

import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleEnterEvent;

final class NoHorseRideListener
        implements Listener {
    NoHorseRideListener() {
    }

    @EventHandler
    public void onHorseRide(VehicleEnterEvent e) {
        if ((e.getVehicle() instanceof Horse || e.getVehicle() instanceof Pig) && e.getEntered() instanceof Player) {
            e.setCancelled(true);
            e.getEntered().sendMessage(ChatColor.RED + "You " + ChatColor.UNDERLINE + "cannot" + ChatColor.RED + " ride " + (e.getVehicle().getType() == EntityType.PIG ? "pigs" : "horses") + " here.");
        }
    }
}

