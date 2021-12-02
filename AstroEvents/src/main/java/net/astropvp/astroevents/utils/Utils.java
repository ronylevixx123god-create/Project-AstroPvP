package net.astropvp.astroevents.utils;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.List;

public class Utils {

    public static String color(String string) {
        char[] b = string.toCharArray();

        for (int i = 0; i < b.length - 1; ++i) {
            if (b[i] == '&' && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[i + 1]) > -1) {
                b[i] = 167;
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }

        return new String(b);
    }

    public static List<String> color(List<String> stringList) {
        stringList.replaceAll(Utils::color);
        return stringList;
    }

    public static String[] color(String[] stringArray) {
        for (int i = 0; i < stringArray.length; i++) {
            stringArray[i] = color(stringArray[i]);
        }
        return stringArray;
    }

    public static <T> T defaultIfNull(T val, T def) {
        return val != null ? val : def;
    }

    public static String defaultIfEmpty(String val, String def) {
        return !val.isEmpty() ? val : def;
    }

    public static String getTimeFromSecsToMins(long seconds) {
        int mins = (int) seconds / 60;
        int remainder = (int) seconds - mins * 60;
        return mins + "m " + remainder + "s ";
    }

    public static void sendActionBarMessage(Player player, String text) {
        PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + text + "\"}"), (byte)2);
        (((CraftPlayer)player).getHandle()).playerConnection.sendPacket(packet);
    }

    public static String toNiceString(String string) {
        string = ChatColor.stripColor(string).replace('_', ' ').toLowerCase();
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < string.toCharArray().length; ++i) {
            char c = string.toCharArray()[i];
            if (i > 0) {
                char prev = string.toCharArray()[i - 1];
                if ((prev == ' ' || prev == '[' || prev == '(') && (i == string.toCharArray().length - 1 || c != 'x' || !Character.isDigit(string.toCharArray()[i + 1]))) {
                    c = Character.toUpperCase(c);
                }
            } else if (c != 'x' || !Character.isDigit(string.toCharArray()[i + 1])) {
                c = Character.toUpperCase(c);
            }

            sb.append(c);
        }

        return sb.toString();
    }
}
