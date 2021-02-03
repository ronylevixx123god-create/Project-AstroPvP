package com.massivecraft.factions.util;

public class TextHelper
{
    public static String toTitleCase(final String input) {
        final StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;
        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            }
            else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }
            titleCase.append(c);
        }
        return titleCase.toString();
    }

    public static String shrink(String string, final int chars) {
        if (string.length() > chars) {
            string = string.substring(0, chars);
        }
        return string;
    }

    public static boolean isEmpty(final String string) {
        return string == null || string.length() == 0;
    }

    public static String merge(final String[] arguments, final int startIndex) {
        String ret = "";
        for (int i = startIndex; i < arguments.length; ++i) {
            ret = ret + arguments[i] + " ";
        }
        return ret.trim();
    }

    public static int getLevenshteinDistance(final String s, final String t) {
        if (s == null || t == null) {
            throw new IllegalArgumentException("getLevenshteinDistance(): El null pointer");
        }
        final int n = s.length();
        final int m = t.length();
        if (n == 0) {
            return m;
        }
        if (m == 0) {
            return n;
        }
        int[] p = new int[n + 1];
        int[] d = new int[n + 1];
        for (int i = 0; i <= n; ++i) {
            p[i] = i;
        }
        for (int j = 1; j <= m; ++j) {
            final char t_j = t.charAt(j - 1);
            d[0] = j;
            for (int i = 1; i <= n; ++i) {
                final int cost = (s.charAt(i - 1) != t_j) ? 1 : 0;
                d[i] = Math.min(Math.min(d[i - 1] + 1, p[i] + 1), p[i - 1] + cost);
            }
            final int[] _d = p;
            p = d;
            d = _d;
        }
        return p[n];
    }
}

