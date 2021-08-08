package me.astropvp.struct;

import org.bukkit.entity.Player;

public class TournamentMatch {
    private TournamentPlayer player1;
    private TournamentPlayer player2;

    public TournamentMatch(TournamentPlayer player1, TournamentPlayer player2) {
        this.player1 = player1;
        this.player2 = player2;
        start();
    }

    private void start() {

    }

    public TournamentPlayer getTournamentPlayer1() {
        return player1;
    }

    public TournamentPlayer getTournamentPlayer2() {
        return player2;
    }
}
