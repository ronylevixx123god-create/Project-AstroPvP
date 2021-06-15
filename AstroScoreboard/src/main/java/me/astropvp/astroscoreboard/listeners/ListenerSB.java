package me.astropvp.astroscoreboard.listeners;

import me.astropvp.astroscoreboard.AstroScoreboardPlugin;
import me.astropvp.astroscoreboard.board.Board;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ListenerSB implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (AstroScoreboardPlugin.getInstance().getBoardManager() != null) {
            AstroScoreboardPlugin.getInstance().getBoardManager().getPlayerBoards().put(player.getUniqueId(), new Board(player, AstroScoreboardPlugin.getInstance().getBoardManager().getAdapter()));
        }
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
}
