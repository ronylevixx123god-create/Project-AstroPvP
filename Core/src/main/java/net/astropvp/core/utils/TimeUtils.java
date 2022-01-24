package net.astropvp.core.utils;

public class TimeUtils {

    public static String format(int seconds) {
        StringBuilder builder = new StringBuilder();
        int minutes = seconds / 60;
        seconds %= 60;
        int hours = minutes / 60;
        minutes %= 60;
        int days = hours / 24;
        hours %= 24;
        if (days > 0) {
            builder.append(days + "d, ");
        }
        if (hours > 0) {
            builder.append(hours + "h, ");
        }
        if (minutes > 0) {
            builder.append(minutes + "m, ");
        }
        if (seconds > 0 || builder.toString().length() == 0) {
            builder.append(seconds + "s, ");
        }
        return builder.toString().substring(0, builder.toString().lastIndexOf(","));
    }

    public static String getTime(int seconds) {
        if (seconds < 60)
            return seconds + "s";
        int minutes = seconds / 60;
        int s = 60 * minutes;
        int secondsLeft = seconds - s;
        if (minutes < 60)
            return (secondsLeft > 0) ? String.valueOf(minutes + "m " + secondsLeft + "s") : String.valueOf(minutes + "m");
        if (minutes < 1440) {
            int i = minutes / 60;
            String str = i + "h";
            int j = 60 * i;
            int k = minutes - j;
            if (k >= 1)
                str = str + " " + k + "m";
            if (secondsLeft > 0)
                str = str + " " + secondsLeft + "s";
            return str;
        }
        int days = minutes / 1440;
        String time = days + "d";
        int inMins = 1440 * days;
        int leftOver = minutes - inMins;
        if (leftOver >= 1)
            if (leftOver < 60) {
                time = time + " " + leftOver + "m";
            } else {
                int hours = leftOver / 60;
                time = time + " " + hours + "h";
                int hoursInMins = 60 * hours;
                int minsLeft = leftOver - hoursInMins;
                time = time + " " + minsLeft + "m";
            }
        if (secondsLeft > 0)
            time = time + " " + secondsLeft + "s";
        return time;
    }
}
