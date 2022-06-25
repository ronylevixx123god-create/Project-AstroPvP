package me.kingsavaqe.astroenvoy.utils;


import java.util.concurrent.TimeUnit;

public class TimeUtil {
    public static String getDuration(int seconds) {
        int year = (int)TimeUnit.SECONDS.toDays(seconds) / 365;
        int day = (int)TimeUnit.SECONDS.toDays(seconds);
        long hour = TimeUnit.SECONDS.toHours(seconds) - (day * 24);
        long minute = TimeUnit.SECONDS.toMinutes(seconds) - TimeUnit.SECONDS.toHours(seconds) * 60L;
        long second = TimeUnit.SECONDS.toSeconds(seconds) - TimeUnit.SECONDS.toMinutes(seconds) * 60L;
        if (year != 0)
            return String.valueOf(year) + "y " + day + "d " + hour + "h " + minute + "m " + second + "s";
        if (day != 0)
            return String.valueOf(day) + "d " + hour + "h " + minute + "m " + second + "s";
        if (hour != 0L)
            return String.valueOf(hour) + "h " + minute + "m " + second + "s";
        if (minute != 0L)
            return String.valueOf(minute) + "m " + second + "s";
        return String.valueOf(second) + "s";
    }
}
