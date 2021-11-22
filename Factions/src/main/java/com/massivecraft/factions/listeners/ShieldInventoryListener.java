package com.massivecraft.factions.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.shield.ShieldInventory;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ShieldInventoryListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if(e.getInventory().getTitle().equals("Faction Shield")) {
            e.setCancelled(true);
            if(e.getCurrentItem().getType() == Material.WATCH) {
                String[] strings = e.getCurrentItem().getItemMeta().getDisplayName().split(":");
                int start = Integer.parseInt(CC.strip(strings[0]));
                FPlayer fPlayer = FPlayers.i.get((OfflinePlayer) e.getWhoClicked());
                fPlayer.getFaction().setShieldTime(start);
                e.getWhoClicked().closeInventory();
                e.getWhoClicked().openInventory(ShieldInventory.get(fPlayer.getFaction()));
            }
        }
    }
}
