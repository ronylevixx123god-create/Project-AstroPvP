package me.astropvp.utils;

import java.util.HashMap;

public class BracketUtils {
    public static HashMap<TournamentRound, Integer> createTournamentRounds(double amount) {
        HashMap<TournamentRound, Integer> map = new HashMap<>();
        if(!confirmBracket())
            amount =- 1;
        map.put(new TournamentRound(amount), 1);
        map.put(new TournamentRound(amount/2), 2);
        map.put(new To)
        return map;
    }

    private static boolean hasRemainder(double d) {
        return d % 2 == 0;
    }

    private static TournamentRound createRound(double amount) {
        return new TournamentRound(amount);
    }

    private static boolean confirmBracket(int i) {
        return i%2 == 0;
    }

}
