/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.AsyncPlayerPreLoginEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.plugin.Plugin
 */
package me.ifamasssxd.cosmiccontests.listeners;

import java.util.List;
import me.ifamasssxd.cosmiccontests.CosmicContests;
import me.ifamasssxd.cosmiccontests.struct.prizes.RankingPrize;
import me.ifamasssxd.cosmiccontests.struct.user.ContestUser;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class PlayerListener
implements Listener {
    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void asyncPlayerJoin(AsyncPlayerPreLoginEvent event) {
        ContestUser existing = ContestUser.getUser(event.getUniqueId());
        if (existing != null) {
            return;
        }
        existing = new ContestUser(event.getName(), event.getUniqueId());
        ContestUser.getUsers().put(event.getUniqueId(), existing);
        existing.loadData(false);
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player pl;
        ContestUser user;
        if (event.getPlayer().isOnline() && (user = ContestUser.getUser((pl = event.getPlayer()).getUniqueId())) != null) {
            List<RankingPrize> prizes;
            if (user.getName() != null && !user.getName().equals(pl.getName())) {
                user.updateName(pl.getName());
            }
            if ((prizes = CosmicContests.get().getPrizeManager().getCachedPrizes().get(pl.getUniqueId())) != null && !prizes.isEmpty()) {
                Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)CosmicContests.get(), () -> {
                    pl.playSound(pl.getLocation(), Sound.WITHER_SHOOT, 1.0f, 1.4f);
                    pl.sendMessage("");
                    pl.sendMessage((Object)ChatColor.GREEN + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.GREEN + "You have " + (Object)ChatColor.GREEN + (Object)ChatColor.BOLD + (Object)ChatColor.UNDERLINE + prizes.size() + (Object)ChatColor.GREEN + " pending Challenge prizes!");
                    pl.sendMessage((Object)ChatColor.GRAY + "Use " + (Object)ChatColor.GREEN + "/challenge claim" + (Object)ChatColor.GRAY + " to claim your prizes!");
                }, 50L);
            }
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onPlayerLeave(PlayerQuitEvent event) {
        ContestUser user = ContestUser.getUser(event.getPlayer().getUniqueId());
        if (user != null) {
            user.saveData(true);
        }
    }
}

