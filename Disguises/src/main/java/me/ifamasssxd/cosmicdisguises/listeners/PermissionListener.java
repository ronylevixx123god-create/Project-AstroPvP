/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CompilexPermissions.event.PermissionChangeEvent
 *  me.libraryaddict.disguise.disguisetypes.DisguiseType
 *  org.bukkit.Bukkit
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 */
package me.ifamasssxd.cosmicdisguises.listeners;

import com.compilex.CompilexPermissions.event.PermissionChangeEvent;
import java.util.Set;
import me.ifamasssxd.cosmicdisguises.CosmicDisguises;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class PermissionListener
implements Listener {
    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onPermissionUpdate(PermissionChangeEvent event) {
        if (event.getUser() == null || event.getUser().getPermissionNodes() == null) {
            return;
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)CosmicDisguises.get(), () -> {
            Set<DisguiseType> set = CosmicDisguises.get().getDisguiseManager().getAvailableDisguises(event.getUser().getPlayerUUID(), event.getUser().getPermissionNodes(), true);
        }, 1L);
    }
}

