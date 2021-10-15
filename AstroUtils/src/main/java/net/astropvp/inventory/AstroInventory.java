package net.astropvp.inventory;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.HashMap;

public class AstroInventory {
    protected String title;

    protected int size;

    protected Player player;

    protected Inventory inventory;

    private HashMap<Integer, InventoryItem> inventoryItems = new HashMap<>();

    public AstroInventory(Player player, String title, int size, InventoryType type) {
        this.inventory = Bukkit.createInventory(null, size, title);
        this.player = player;
        this.size = size;
        this.title = title;
    }

}
