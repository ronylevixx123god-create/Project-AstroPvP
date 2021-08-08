package me.bryside.astrorevive.Utils;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;


public class KUtil {
    public static ItemStack createItem(String name, int data, Material material, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setDurability((short) data);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createItem(String name, int data, Material material, List<String> lore) {
        return createItem(name, data, material, lore.<String>toArray(new String[lore.size() - 1]));
    }

    public static ItemStack getMobHead(String display, EntityType type, String... lore) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

        if (type == EntityType.IRON_GOLEM) {
            skullMeta.setOwner("MHF_Golem");
        } else {
            skullMeta.setOwner("MHF_" + type.name());
        }

        skullMeta.setDisplayName(display);
        skullMeta.setLore(Arrays.asList(lore));
        skull.setItemMeta((ItemMeta) skullMeta);
        return skull;
    }


    public static String getProgressBar(int current, int max, int totalBars, String symbol, String completedColor, String notCompletedColor) {
        float percent = current / max;

        int progressBars = (int) (totalBars * percent);

        int leftOver = totalBars - progressBars;

        StringBuilder sb = new StringBuilder();
        sb.append(ChatColor.translateAlternateColorCodes('&', completedColor));
        int i;
        for (i = 0; i < progressBars; i++) {
            sb.append(symbol);
        }
        sb.append(ChatColor.translateAlternateColorCodes('&', notCompletedColor));
        for (i = 0; i < leftOver; i++) {
            sb.append(symbol);
        }
        return sb.toString();
    }

    public static void broadcastMessage(String... msg) {
        Arrays.<String>asList(msg).forEach(m -> Bukkit.getServer().broadcastMessage(m));
    }

    public static boolean hasItems(Player player) {
        return (player.getInventory().firstEmpty() != 0);
    }

    public static boolean hasArmorOn(Player player) {
        for (ItemStack item : player.getInventory().getArmorContents()) {
            if (item != null && item.getType() != Material.AIR)
                return true;
        }
        return false;
    }

    public static Integer tryParseInt(String string) {
        try {
            return Integer.valueOf(Integer.parseInt(string));
        } catch (Exception e) {
            return null;
        }
    }

    public static class Debugger {
        private long start;
        private int j = 0;
        private boolean closed;

        public Debugger() {
            this.start = System.currentTimeMillis();
        }

        public void close() {
            this.closed = true;
            Bukkit.getServer().broadcast("§9[Debug finished in (" + (System.currentTimeMillis() - this.start) + "ms)", "*");
        }

        public void print(String string) {
            if (!this.closed) {
                Bukkit.getServer().broadcast("§9[Debug #" + ++this.j + "] " + string + " (" + (
                        System.currentTimeMillis() - this.start) + "ms)", "*");
            }
        }
    }

    public static class TimeUtil {
        public static String formatTime(int secs) {
            int remainder = secs % 86400;

            int days = secs / 86400;
            int hours = remainder / 3600;
            int minutes = remainder / 60 - hours * 60;
            int seconds = remainder % 3600 - minutes * 60;

            String fDays = (days > 0) ? (" " + days + " day" + ((days > 1) ? "s" : "")) : "";
            String fHours = (hours > 0) ? (" " + hours + " hour" + ((hours > 1) ? "s" : "")) : "";
            String fMinutes = (minutes > 0) ? (" " + minutes + " minute" + ((minutes > 1) ? "s" : "")) : "";
            String fSeconds = (seconds > 0) ? (" " + seconds + " second" + ((seconds > 1) ? "s" : "")) : "";

            return fDays + fHours + fMinutes + fSeconds;
        }

        public static String formatTime(long secs) {
            secs /= 1000L;
            long remainder = secs % 86400L;

            long days = secs / 86400L;
            long hours = remainder / 3600L;
            long minutes = remainder / 60L - hours * 60L;
            long seconds = remainder % 3600L - minutes * 60L;

            String fDays = (days > 0L) ? (" " + days + " day" + ((days > 1L) ? "s" : "")) : "";
            String fHours = (hours > 0L) ? (" " + hours + " hour" + ((hours > 1L) ? "s" : "")) : "";
            String fMinutes = (minutes > 0L) ? (" " + minutes + " minute" + ((minutes > 1L) ? "s" : "")) : "";
            String fSeconds = (seconds > 0L) ? (" " + seconds + " second" + ((seconds > 1L) ? "s" : "")) : "";

            String result = fDays + fHours + fMinutes + fSeconds;

            if (result.equals("")) {
                return "0 seconds";
            }

            return result;
        }
    }
}
