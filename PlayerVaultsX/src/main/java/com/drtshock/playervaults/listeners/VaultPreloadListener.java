package com.drtshock.playervaults.listeners;

import com.drtshock.playervaults.vaultmanagement.*;
import org.bukkit.scheduler.*;
import java.util.*;
import com.drtshock.playervaults.*;
import org.bukkit.plugin.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

public class VaultPreloadListener implements Listener
{
    final VaultManager vm;
    
    public VaultPreloadListener() {
        this.vm = VaultManager.getInstance();
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(final PlayerJoinEvent playerJoinEvent) {
        new BukkitRunnable() {
            final /* synthetic */ UUID val$uuid = playerJoinEvent.getPlayer().getUniqueId();
            
            public void run() {
                VaultPreloadListener.this.vm.cachePlayerVaultFile(this.val$uuid);
            }
        }.runTaskAsynchronously((Plugin)PlayerVaults.getInstance());
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(final PlayerQuitEvent playerQuitEvent) {
        this.vm.removeCachedPlayerVaultFile(playerQuitEvent.getPlayer().getUniqueId());
    }
}
