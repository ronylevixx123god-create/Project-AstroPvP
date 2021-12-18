package com.compilex.CosmicArmorSets.listeners;

import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class SantaListener implements Listener {
    @EventHandler
    public void damageReductionWithWeapon(EntityDamageByEntityEvent e ) {
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            Player damaged = ((Player) e.getEntity()).getPlayer();
            if (damaged.getItemInHand() != null) {
                if (new NBTWrapper(damaged.getItemInHand()).getString("cosmicType").equals("SANTA_WEAPON")) {
                    e.setDamage(e.getDamage()*0.85);
                }
            }
        }
    }
}
