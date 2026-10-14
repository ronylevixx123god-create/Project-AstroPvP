package me.kingsavaqe.astrotournaments.listeners;
import me.kingsavaqe.astrotournaments.AstroTournamentsPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
public class TournamentDeathListener implements Listener {
    private final AstroTournamentsPlugin plugin;
    public TournamentDeathListener(AstroTournamentsPlugin plugin) { this.plugin = plugin; }
    @EventHandler
    public void tournamentDeathListener(PlayerDeathEvent event) {
        me.kingsavaqe.astrotournaments.objects.TournamentPlayer player = plugin.getTournamentManager().getTournamentPlayer(event.getEntity().getUniqueId());
        if (!player.isInTournament()) return;
        event.getDrops().clear();
        plugin.getTournamentManager().eliminate(player, true);
    }
}
