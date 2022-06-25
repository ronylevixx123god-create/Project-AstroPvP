package me.kingsavaqe.astroenvoy.utils;


import java.text.DecimalFormat;

public class NumberUtil {
    private static final DecimalFormat decimalFormat = new DecimalFormat("##.#");

    public static String formatNumberByCommas(long number) {
        return String.format("%,d", number);
    }

    public static String formatNumberByDecimal(float number) {
        return decimalFormat.format(number);
    }
}
