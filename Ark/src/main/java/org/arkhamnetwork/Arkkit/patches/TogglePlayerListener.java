/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.compilex.CompilexPermissions.CompilexPermissions
 *  com.compilex.CompilexPermissions.manager.CacheManager
 *  com.compilex.CompilexPermissions.structure.PermissionGroup
 *  com.earth2me.essentials.Essentials
 *  com.earth2me.essentials.User
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.plugin.Plugin
 *  org.kitteh.vanish.VanishPlugin
 *  org.zencode.shortninja.staffplus.StaffPlus
 */
package org.arkhamnetwork.Arkkit.patches;

import com.compilex.CompilexPermissions.CompilexPermissions;
import com.compilex.CompilexPermissions.manager.CacheManager;
import com.compilex.CompilexPermissions.structure.PermissionGroup;
import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.kitteh.vanish.VanishPlugin;

import java.io.File;
import java.util.HashSet;
import java.util.List;

final class TogglePlayerListener
        implements Listener {
    private Essentials ess3;
    private final boolean vanishNoPacket = new File("plugins/VanishNoPacket.jar").exists();

    TogglePlayerListener() {
        if (Bukkit.getPluginManager().isPluginEnabled("Essentials")) {
            this.ess3 = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerRunVanish(PlayerCommandPreprocessEvent event) {
        if (event.getMessage() == null) {
            return;
        }
        if (event.getMessage().equalsIgnoreCase("/vanish") && Youtubers.youtubers.contains(event.getPlayer().getName())) {
            event.setMessage("/evanish");
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent e) {
        for (Player p : CommandTogglePlayer.hidden_players) {
            p.hidePlayer(e.getPlayer());
            e.getPlayer().hidePlayer(p);
        }
        boolean exemptFromVanish = false;
        if (this.vanishNoPacket) {
            List groups = CacheManager.getCachedPlayerGroups().get(e.getPlayer().getUniqueId().toString());
            List permissions = CacheManager.getCachedPlayerPermissions().get(e.getPlayer().getUniqueId().toString());
            HashSet allPerms = new HashSet();
            if (groups != null) {
                groups.forEach(group -> {
                    PermissionGroup foundGroup = CompilexPermissions.get().getPermissionManager().getCachedLoadedGroups().get(group);
                    if (foundGroup == null) {
                        return;
                    }
                    allPerms.addAll(foundGroup.getPermissions());
                });
            }
            if (permissions != null) {
                allPerms.addAll(permissions);
            }
            boolean silent = false;
            if ((allPerms.contains("vanish.joinvanished") || (silent = allPerms.contains("vanish.silentjoin") || allPerms.contains("*.*") || allPerms.contains("*") || allPerms.contains("vanish.*") || allPerms.contains("vanish.*"))) && !allPerms.contains("-vanish.joinvanished")) {
                Bukkit.getLogger().info("Not attempting to /evanish " + e.getPlayer().getName() + " due to having valid vanish.joinvanished permissions.");
                User u = this.ess3.getUser(e.getPlayer());
                if (u != null && u.isVanished()) {
                    u.setVanished(false);
                }
                e.getPlayer().removeMetadata("trackedVanishState", Arkkit.get());
                exemptFromVanish = true;
                VanishPlugin plugin = (VanishPlugin) Bukkit.getPluginManager().getPlugin("VanishNoPacket");
                if (plugin != null && !plugin.getManager().isVanished(e.getPlayer())) {
                    plugin.getManager().vanish(e.getPlayer(), silent, false);
                    Bukkit.getLogger().info("Forcing vanish for " + e.getPlayer().getName() + " due to having permissions and not being vanished!");
                }
            }
        }
        if (e.getPlayer().hasPermission("arkkit.staff.hidden") || Youtubers.youtubers.contains(e.getPlayer().getName())) {
            if (this.ess3 != null) {
                User u = this.ess3.getUser(e.getPlayer());
                if (!(u.isVanished() || exemptFromVanish || e.getPlayer().getName().equals("ypperin") && new File("jungleplanet").exists())) {
                    u.setVanished(true);
                }
                if (u.isTeleportEnabled()) {
                    u.setTeleportEnabled(false);
                }
            }
            if (Youtubers.youtubers.contains(e.getPlayer().getName())) {
                e.getPlayer().performCommand("ncp notify off");
                e.getPlayer().performCommand("shiva notify off");
            } else {
                e.getPlayer().performCommand("shiva notify on");
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        CommandTogglePlayer.hidden_players.remove(e.getPlayer());
    }
}

