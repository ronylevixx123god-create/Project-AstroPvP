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
import java.util.HashSet;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

final class FactionCornerClaimListener
implements Listener {
    private HashSet<String> announcedCorners = new HashSet();

    FactionCornerClaimListener() {
    }

    @EventHandler(priority=EventPriority.MONITOR)
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
            Bukkit.broadcastMessage((String)"");
            Bukkit.broadcastMessage((String)((Object)ChatColor.DARK_GREEN + "" + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.DARK_GREEN + "The faction \"" + (Object)ChatColor.UNDERLINE + faction + (Object)ChatColor.DARK_GREEN + "\" has claimed the " + (Object)ChatColor.GREEN + (Object)ChatColor.BOLD + cornerLocation + (Object)ChatColor.DARK_GREEN + " corner chunk! Congratulations!"));
            Bukkit.broadcastMessage((String)"");
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

