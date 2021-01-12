/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.massivecraft.factions.event.FactionDisbandEvent
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 */
package me.ifamasssxd.factionupgrades.listeners;

import com.massivecraft.factions.P;
import com.massivecraft.factions.event.FactionDisbandEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FactionListener
implements Listener {
    @EventHandler
    public void onFactionDisband(FactionDisbandEvent e) {
        String factionId = e.getFaction().getId();
        P.getP().getUpgradeManager().getUpgradeInfo().remove(factionId);
    }
}

