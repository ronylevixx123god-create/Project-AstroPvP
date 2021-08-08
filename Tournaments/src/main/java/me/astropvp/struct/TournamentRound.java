package me.astropvp.struct;

import java.util.ArrayList;
import java.util.List;

public class TournamentRound {
    private List<TournamentMatch> matches;
    private List<TournamentPlayer> winners;



    public TournamentRound() {
        this.matches = new ArrayList<TournamentMatch>();
        this.winners = new ArrayList<TournamentPlayer>();
    }

    public List<TournamentMatch> getMatches() {
        return matches;
    }

    public List<TournamentPlayer> getWinners() {
        return winners;
    }

    public void addWinner(TournamentPlayer tournamentPlayer) {
        if (tournamentPlayer.getPlayer().isOnline()) {
            winners.add(tournamentPlayer);
        }
    }

}
