package me.astropvp.utils;

import me.astropvp.struct.TournamentRound;

import java.util.HashMap;

public class BracketUtils {
    public static HashMap<TournamentRound, Integer> createTournamentRounds(double amount) {
        HashMap<TournamentRound, Integer> map = new HashMap<>();
        boolean b = hasRemainder(amount);
        if(amount%2 != 0) {
            b = true;
        }
        amount = Math.floor(amount/2);
        TournamentRound round1 = new TournamentRound(amount);
        map.put(round1, 1);
        if(b) {
            if(amount%2)
        }
        return map;
    }

    private static boolean hasRemainder(double d) {
        return d % 2 == 0;
    }

    private static TournamentRound createRound(double amount) {
        TournamentRound round = new TournamentRound(amount);
        return round;
    }

}
