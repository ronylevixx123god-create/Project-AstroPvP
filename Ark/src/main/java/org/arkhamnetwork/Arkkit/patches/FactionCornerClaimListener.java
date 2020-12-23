/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.massivecraft.factions.FLocation
 *  com.massivecraft.factions.event.LandClaimEvent
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 */
package org.arkhamnetwork.Arkkit.patches;

import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.event.LandClaimEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.HashSet;

final class FactionCornerClaimListener
        implements Listener {
    private final HashSet<String> announcedCorners = new HashSet();

    FactionCornerClaimListener() {
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onLandClaim(LandClaimEvent e) {
        FLocation fL = e.getLocation();
        long x = Math.abs(fL.getX());
        long z = Math.abs(fL.getZ());
        if (x == 937L && z == 937L) {
            String faction = e.getFaction().getTag();
            String cornerLocation = this.getCornerLocation(fL);
            if (this.announcedCorners.contains(cornerLocation)) {
                return;
            }
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "(!) " + ChatColor.DARK_GREEN + "The faction \"" + ChatColor.UNDERLINE + faction + ChatColor.DARK_GREEN + "\" has claimed the " + ChatColor.GREEN + ChatColor.BOLD + cornerLocation + ChatColor.DARK_GREEN + " corner chunk! Congratulations!");
            Bukkit.broadcastMessage("");
            this.announcedCorners.add(cornerLocation);
        }
    }

    private String getCornerLocation(FLocation fl) {
        long x = fl.getX();
        long z = fl.getZ();
        if (x == 937L && z == 937L) {
            return "North-East";
        }
        if (x == 937L && z == -937L) {
            return "South-East";
        }
        if (x == -937L && z == 937L) {
            return "North-West";
        }
        if (x == -937L && z == -937L) {
            return "South-West";
        }
        return "Unknown";
    }
}

