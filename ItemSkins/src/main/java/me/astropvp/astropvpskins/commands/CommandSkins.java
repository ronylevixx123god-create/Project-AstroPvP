/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 */
package me.astropvp.astropvpskins.commands;

import java.util.HashMap;
import java.util.Map;
import me.astropvp.astropvpskins.enums.ItemSkinType;
import me.astropvp.astropvpskins.utils.InventoryItems;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CommandSkins
implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
        if (sender instanceof Player) {
            ((Player)sender).openInventory(CommandSkins.getInventory());
        }
        return false;
    }

    public static Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(null, (int)45, (String)"\u00a78Astro Item Skins");
        HashMap<ItemSkinType, Integer> map = new HashMap<ItemSkinType, Integer>();
        map.put(ItemSkinType.HELMET, 4);
        map.put(ItemSkinType.CHESTPLATE, 13);
        map.put(ItemSkinType.AMULET, 14);
        map.put(ItemSkinType.LEGGINGS, 22);
        map.put(ItemSkinType.BELT, 23);
        map.put(ItemSkinType.BOOTS, 31);
        map.put(ItemSkinType.BOW, 40);
        map.put(ItemSkinType.SWORD, 39);
        map.put(ItemSkinType.AXE, 41);
        map.put(ItemSkinType.PICKAXE, 42);
        for (Map.Entry entry : map.entrySet()) {
            inventory.setItem(((Integer)entry.getValue()).intValue(), InventoryItems.createItemSkinTemplate((ItemSkinType)((Object)entry.getKey())));
        }
        for (int i = 0; i < inventory.getSize(); ++i) {
            if (inventory.getItem(i) != null) continue;
            inventory.setItem(i, InventoryItems.createPane(7));
        }
        return inventory;
    }
}

