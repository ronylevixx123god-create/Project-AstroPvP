package com.massivecraft.factions.listeners;

import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class FactionShieldListener implements Listener {
    @EventHandler
    public void onTntExplode(EntityExplodeEvent e) {
        if(FactionUtils.getFactionAt(e.getLocation()).isShieldActive())
            e.setCancelled(true);
    }
}
