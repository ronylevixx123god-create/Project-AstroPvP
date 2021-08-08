package me.astropvp.tasks;

import me.astropvp.struct.Tournament;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class TournamentLobbyTask extends BukkitRunnable {
    private Tournament tournament;

    public TournamentLobbyTask(Tournament tournament) {
        this.tournament = tournament;
    }
    public void run() {
        tournament.setLobbyWaitingTime(tournament.getLobbyWaitingTime() - 1);
        Bukkit.broadcastMessage(String.valueOf(tournament.getLobbyWaitingTime()));
        if(tournament.getLobbyWaitingTime() < 0) {
            tournament.close();
        }
    }
}
