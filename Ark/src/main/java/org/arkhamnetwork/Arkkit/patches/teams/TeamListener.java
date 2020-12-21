/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.arkhamnetwork.TeamAPI
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.player.AsyncPlayerChatEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches.teams;

import com.arkhamnetwork.TeamAPI;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class TeamListener
implements Listener {
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        Player p_damaged;
        if (e.getEntity() instanceof Player && org.arkhamnetwork.Arkkit.utils.TeamAPI.getTeam(p_damaged = (Player)e.getEntity()) != null) {
            Player p_damager = null;
            if (e.getDamager() instanceof Projectile) {
                if (((Projectile)e.getDamager()).getShooter() instanceof Player) {
                    p_damager = (Player)((Projectile)e.getDamager()).getShooter();
                }
            } else if (e.getDamager() instanceof Player) {
                p_damager = (Player)e.getDamager();
            }
            if (p_damager == null || org.arkhamnetwork.Arkkit.utils.TeamAPI.getTeam(p_damager) == null || !org.arkhamnetwork.Arkkit.utils.TeamAPI.onTeam(p_damaged, p_damager)) {
                return;
            }
            e.setCancelled(true);
            e.setDamage(0.0);
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        if (org.arkhamnetwork.Arkkit.utils.TeamAPI.getTeam(e.getPlayer()) != null) {
            e.setFormat((Object)org.arkhamnetwork.Arkkit.utils.TeamAPI.getTeamColor(org.arkhamnetwork.Arkkit.utils.TeamAPI.getTeam(e.getPlayer())) + ChatColor.BOLD.toString() + "T " + e.getFormat());
        }
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (org.arkhamnetwork.Arkkit.utils.TeamAPI.getTeam(e.getPlayer()) != null) {
            final Player pl = e.getPlayer();
            Bukkit.getScheduler().runTaskLater((Plugin)Arkkit.get(), new Runnable(){

                @Override
                public void run() {
                    TeamAPI.setGlobalPrefix((Player)pl, (String)((Object)org.arkhamnetwork.Arkkit.utils.TeamAPI.getTeamColor(org.arkhamnetwork.Arkkit.utils.TeamAPI.getTeam(pl)) + "&lT"));
                }
            }, 15L);
        }
    }
}

