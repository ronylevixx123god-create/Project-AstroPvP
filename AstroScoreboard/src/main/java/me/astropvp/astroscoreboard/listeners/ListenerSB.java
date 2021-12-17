package me.astropvp.astroscoreboard.listeners;

import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.event.*;
import me.astropvp.astroscoreboard.AstroScoreboardAPI;
import me.astropvp.astroscoreboard.AstroScoreboardPlugin;
import me.astropvp.astroscoreboard.board.Board;
import me.astropvp.astroscoreboard.manager.PlayerDisplayManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ListenerSB implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (AstroScoreboardPlugin.getInstance().getBoardManager() != null) {
            AstroScoreboardPlugin.getInstance().getBoardManager().getPlayerBoards().put(player.getUniqueId(), new Board(player, AstroScoreboardPlugin.getInstance().getBoardManager().getAdapter()));
        }

        Bukkit.getScheduler().runTaskLaterAsynchronously(AstroScoreboardPlugin.getInstance(), this::refreshTagsAsyncForServer, 30L);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (AstroScoreboardPlugin.getInstance().getBoardManager() != null) {
            AstroScoreboardPlugin.getInstance().getBoardManager().getPlayerBoards().remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        Player player = event.getPlayer();
        if (AstroScoreboardPlugin.getInstance().getBoardManager() != null) {
            AstroScoreboardPlugin.getInstance().getBoardManager().getPlayerBoards().remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onFactionJoin(FPlayerJoinEvent event) {
        refreshTagsAsyncForServer();
    }

    @EventHandler
    public void onFactionLeave(FPlayerLeaveEvent event) {
        refreshTagsAsyncForServer();
    }

    @EventHandler
    public void onFactionCreate(FactionCreateEvent event) {
        refreshTagsAsyncForServer();
    }

    @EventHandler
    public void onFactionDisband(FactionDisbandEvent event) {
        refreshTagsAsyncForServer();
    }

    @EventHandler
    public void onFactionRelationChange(FactionRelationEvent event) {
        refreshTagsAsyncForFaction(event.getFaction(), event.getTargetFaction());
        refreshTagsAsyncForFaction(event.getTargetFaction(), event.getFaction());
    }

    @EventHandler
    public void onFactionRename(FactionRenameEvent event) {
        refreshTagsAsyncForServer();
    }

    @EventHandler
    public void onFactionFocus(FactionFocusEvent event) {
        refreshTagsAsyncForFaction(event.getFsender(), FactionUtils.getFaction(event.getFtarget()));
        refreshTagsAsyncForFaction(event.getFsender(), event.getFsender());

    }

    @EventHandler
    public void onFactionUnFocus(FactionUnFocusEvent event) {
        refreshTagsAsyncForFaction(event.getFsender(), FactionUtils.getFaction(event.getFtarget()));
        refreshTagsAsyncForFaction(event.getFsender(), event.getFsender());

    }

    public void refreshTagsAsyncForFaction(Faction faction1, Faction faction2) {
        Bukkit.getScheduler().runTaskAsynchronously(AstroScoreboardPlugin.getInstance(), () -> {
            for (Player player : faction1.getOnlinePlayers()) {
                for (Player other : faction2.getOnlinePlayers()) {
                    try {
                        PlayerDisplayManager.setUserPrefix(player, other, AstroScoreboardAPI.getDefaultPrefix(player, other) + " ");
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
        });
    }

    public void refreshTagsAsyncForServer() {
        Bukkit.getScheduler().runTaskAsynchronously(AstroScoreboardPlugin.getInstance(), () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                for (Player other : Bukkit.getOnlinePlayers()) {
                    try {
                        PlayerDisplayManager.setUserPrefix(player, other, AstroScoreboardAPI.getDefaultPrefix(player, other) + " ");
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
        });
    }
}
