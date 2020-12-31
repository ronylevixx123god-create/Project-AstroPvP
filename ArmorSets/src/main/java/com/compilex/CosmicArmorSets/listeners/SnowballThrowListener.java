package com.compilex.CosmicArmorSets.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SnowballThrowListener implements Listener {


    @EventHandler
    public void onThrow(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getItem() != null && e.getItem().getType() != Material.AIR && e.getItem().getType() == Material.SNOW_BALL) {
                if (e.getItem().hasItemMeta() && e.getItem().getItemMeta().hasLore())
                    e.setCancelled(true);
            }
        }
    }
}
