package me.venir.astrocollectors.utils;

import org.bukkit.ChatColor;

import java.text.NumberFormat;

public class Utils {
    public static String format(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    private static final NumberFormat nf = NumberFormat.getIntegerInstance();

    public static String formatNum(int number) {
        return nf.format(number);
    }

    public static String formatNum(long number) {
        return nf.format(number);
    }

    public static String formatNum(double number) {
        return nf.format(number);
    }

    public static String formatNum(float number) {
        return nf.format(number);
    }
}
