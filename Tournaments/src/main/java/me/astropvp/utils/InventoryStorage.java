package me.astropvp.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import java.io.IOException;

public class InventoryStorage {
    public static String[] storeInventory(Player player) {
        PlayerInventory inventory = player.getInventory();
        String[] encoded = Base64Utils.playerInventoryToBase64(inventory);
        return encoded;
    }

    public static void restoreInventory(Player player, String[] string) {
        player.getInventory().clear();
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
        try {
            player.getInventory().setContents(Base64Utils.itemStackArrayFromBase64(string[0]));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        try {
            player.getInventory().setArmorContents(Base64Utils.itemStackArrayFromBase64(string[1]));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
