package com.massivecraft.factions.shield;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.massivecraft.factions.Faction;
import org.arkhamnetwork.enchantments.util.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ShieldInventory {
    public static Inventory get(Faction faction) {
        Inventory inventory = Bukkit.createInventory(null, 54, "Faction Shield");
        AtomicInteger i = new AtomicInteger(9);
        ShieldManager.getTimes().forEach((key, value) ->  {
            if(faction.getShieldTime() == key.getHour()) {
                inventory.setItem(i.get(), ItemUtils.addGlow(getTimeItem(key, value)));
            } else {
                inventory.setItem(i.get(), getTimeItem(key, value));
            }
            i.getAndIncrement();

        });
        return inventory;
    }

    private static ItemStack getTimeItem(LocalTime start, LocalTime end) {
        ItemStack item = new ItemStack(Material.WATCH);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(CC.Green + start + ":00" + CC.Gray + " ----> " + CC.Green + end + ":00");
        List<String> lore = new ArrayList<>();
        lore.add(CC.Gray + "Click to select.");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
