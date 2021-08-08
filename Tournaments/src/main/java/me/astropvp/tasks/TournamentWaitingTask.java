package me.astropvp.tasks;

import me.astropvp.struct.Tournament;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class TournamentWaitingTask extends BukkitRunnable {
    private Tournament tournament;

    public TournamentWaitingTask(Tournament tournament) {
        this.tournament = tournament;
    }
    public void run() {
        tournament.setStartingTime(tournament.getStartingTime() - 1);
        Bukkit.broadcastMessage(String.valueOf(tournament.getStartingTime()));
        if(tournament.getStartingTime() < 0) {
            tournament.start();
        }
    }
}
