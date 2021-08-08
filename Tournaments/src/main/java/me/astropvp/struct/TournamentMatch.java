package me.astropvp.struct;

import org.bukkit.entity.Player;

public class TournamentMatch {
    private Player player1;
    private Player player2;

    public TournamentMatch(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        start();
    }

    private void start() {

    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }
}
