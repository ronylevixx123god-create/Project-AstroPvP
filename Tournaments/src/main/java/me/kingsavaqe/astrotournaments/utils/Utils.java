package me.kingsavaqe.astrotournaments.utils;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.stream.Stream;

public class Utils {
    public static String fixColor(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static String locationToString(Location location) {
        return location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + "," + location.getYaw() + "," + location.getPitch();
    }

    public static Location getLocationFromString(String string) {
        String[] arg = string.split(",");
        double[] parsed = new double[5];
        for (int a = 0; a < 5; a++) {
            parsed[a] = Double.parseDouble(arg[a + 1]);
        }

        return new Location(Bukkit.getWorld(arg[0]), parsed[0], parsed[1], parsed[2], (float) parsed[3], (float) parsed[4]);
    }

    public static String getProperCase(String text) {
        StringBuilder sb = new StringBuilder(text.length());
        char[] charArray = text.toLowerCase().toCharArray();
        charArray[0] = Character.toUpperCase(charArray[0]);
        sb.append(new String(charArray));
        return sb.toString().trim();
    }

    public static boolean isEmptyInventory(Player player) {
        return Stream.of(player.getInventory().getContents()).allMatch(itemStack -> itemStack == null || itemStack.getType() == Material.AIR) &&
                Stream.of(player.getInventory().getArmorContents()).allMatch(itemStack -> itemStack == null || itemStack.getType() == Material.AIR);
    }


    public static boolean hasAvaliableSlot(Player player, int amount) {
        int amountOpen = 0;
        Inventory inv = player.getInventory();
        for (ItemStack item : inv.getContents()) {
            if (item == null) {
                amountOpen += 1;
            }
        }
        return amountOpen >= amount;
    }

    public static void playSound(Player player, Sound sound) {
        player.playSound(player.getLocation(), sound, 2.0F, 2.0F);
    }

    public static void emptyInventory(Player player) {
        player.getInventory().clear();
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
    }

    public static double round(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }
}
