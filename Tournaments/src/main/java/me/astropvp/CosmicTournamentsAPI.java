package me.astropvp;

import me.astropvp.struct.ActiveTournamentPlayer;
import org.bukkit.entity.Player;

public class CosmicTournamentsAPI {
    public static boolean isPlayerActive(Player player) {
        for(ActiveTournamentPlayer p : CosmicTournamentsPlugin.getInstance().getActiveTournament().getPlayerList()) {
            if(p.getPlayer() == player) {
                return true;
            }
        }
        return false;
    }
}
