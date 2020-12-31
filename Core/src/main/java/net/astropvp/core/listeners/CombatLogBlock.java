package net.astropvp.core.listeners;

import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import org.arkhamnetwork.Arkkit.patches.combatlog.CombatLog;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CombatLogBlock implements Listener {
    @EventHandler
    public void fHome(final PlayerCommandPreprocessEvent event) {
        final Player player = event.getPlayer();
        final String msg = event.getMessage().toLowerCase();
        if (CombatLog.inCombat(player) && msg.startsWith("/f home")) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "You cannot use /f home while in combat!");
        }
    }

    @EventHandler
    public void trash(final PlayerCommandPreprocessEvent event) {
        final Player player = event.getPlayer();
        final String msg = event.getMessage().toLowerCase();
        if (CombatLog.inCombat(player) && msg.startsWith("/trash")) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "You cannot use /trash while in combat!");
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void stopPvP(PlayerCommandPreprocessEvent event) {
        if (FactionUtils.isWarZone(event.getPlayer().getLocation()) && event.getMessage().toLowerCase().startsWith("/pvp") || FactionUtils.isWarZone(event.getPlayer().getLocation()) && event.getMessage().toLowerCase().startsWith("/wildpvp")) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("§cYou cannot set a pvp here!");
        }
    }
}
