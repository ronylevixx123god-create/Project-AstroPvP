package me.astropvp.listeners;

import me.astropvp.CosmicTournamentsPlugin;
import me.astropvp.struct.Tournament;
import me.astropvp.struct.TournamentPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class TournamentListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        if(CosmicTournamentsPlugin.getInstance().getActiveTournament() != null) {
            Tournament tournament = CosmicTournamentsPlugin.getInstance().getActiveTournament();
            if(tournament.getTournamentPlayer(e.getEntity()) == null)
                return;
            TournamentPlayer player = tournament.getTournamentPlayer(e.getEntity());
            tournament.endMatch(tournament.getMatch(player));
        }
    }
}
