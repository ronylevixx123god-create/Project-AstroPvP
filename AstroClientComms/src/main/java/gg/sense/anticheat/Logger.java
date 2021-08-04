package gg.sense.anticheat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Logger {
    private static int getMaxLength(final String... strings) {
        int len = Integer.MIN_VALUE;
        for (final String str : strings) {
            len = Math.max(str.length(), len);
        }
        return len;
    }

    private static String padString(final String str, final int len) {
        return str + fill(' ', len - str.length());
    }

    private static String fill(final char ch, final int len) {
        final StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; ++i) {
            sb.append(ch);
        }
        return sb.toString();
    }

    public static void printBox(ChatColor color, final String... strings) {
        final int maxBoxWidth = getMaxLength(strings);
        final String line = "+" + fill('-', maxBoxWidth + 2) + "+";
        Bukkit.getConsoleSender().sendMessage(color.toString() + line);
        for (final String str : strings) {
            Bukkit.getConsoleSender().sendMessage(
                    color.toString() + String.format(
                            "| %s |",
                            padString(str, maxBoxWidth) + ChatColor.RESET + color.toString()
                    )
            );
        }
        Bukkit.getConsoleSender().sendMessage(color.toString() + line);
    }
}
