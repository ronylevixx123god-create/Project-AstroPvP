/*
 * Decompiled with CFR 0.150.
 */
package me.ifamasssxd.cosmicbosses.utils;

import java.text.DecimalFormat;

public class NumberUtils {
    static DecimalFormat format = new DecimalFormat("#,##0");

    public static String addCommas(int number) {
        return format.format(number);
    }
}

