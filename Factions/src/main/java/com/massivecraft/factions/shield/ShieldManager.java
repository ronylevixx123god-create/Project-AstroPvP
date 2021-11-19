package com.massivecraft.factions.shield;

import lombok.Getter;

import java.time.LocalTime;
import java.util.HashMap;

public class ShieldManager {
    @Getter private static HashMap<LocalTime, LocalTime> times;

    public static void loadTimes() {
        times = new HashMap<>();
        int duration = 8; //hours
        for(int i = 0; i < 24; i++) {
            times.put(LocalTime.MIDNIGHT.plusHours(i), LocalTime.MIDNIGHT.plusHours(i + duration));
        }
    }

}
