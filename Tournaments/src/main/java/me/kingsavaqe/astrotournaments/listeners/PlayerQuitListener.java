package me.kingsavaqe.astrotournaments.listeners;
import me.kingsavaqe.astrotournaments.AstroTournamentsPlugin;
import org.bukkit.event.*;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
public class PlayerQuitListener implements Listener {
    private final AstroTournamentsPlugin plugin;
    public PlayerQuitListener(AstroTournamentsPlugin plugin) { this.plugin = plugin; }
    @EventHandler
    public void playerQuitInTournament(PlayerQuitEvent event) {
        plugin.getTournamentManager().getTournamentPlayer(event.getPlayer().getUniqueId()).leaveTournament();
    }
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void playerKickInTournament(PlayerKickEvent event) {
        plugin.getTournamentManager().getTournamentPlayer(event.getPlayer().getUniqueId()).leaveTournament();
    }
}
