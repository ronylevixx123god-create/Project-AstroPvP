package me.astropvp.factionlogs.utils;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class TimeDateUtils {

    public static String splitToComponentTimes(long seconds) {
        int hours = (int) seconds / 3600;
        int remainder = (int) seconds - hours * 3600;
        int mins = remainder / 60;
        remainder = remainder - mins * 60;
        int secs = remainder;
        return hours + "h " + mins + "m " + secs + "s ";
    }
}
