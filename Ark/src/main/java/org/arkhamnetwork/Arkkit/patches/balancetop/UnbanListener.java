/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  me.confuser.banmanager.events.PlayerBanEvent
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 */
package org.arkhamnetwork.Arkkit.patches.balancetop;

import me.confuser.banmanager.events.PlayerBanEvent;
import org.arkhamnetwork.Arkkit.patches.balancetop.BalanceManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class UnbanListener
implements Listener {
    @EventHandler
    public void onPlayerBannedEvent(PlayerBanEvent e) {
        if (BalanceManager.balanceMap.containsKey(e.getBan().getPlayer().getUUID())) {
            System.out.println("Removing " + e.getBan().getPlayer().getName() + " from /baltop due to them being banned.");
            BalanceManager.balanceMap.remove(e.getBan().getPlayer().getUUID());
        }
    }
}

