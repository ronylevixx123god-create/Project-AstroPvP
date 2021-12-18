package com.compilex.CosmicArmorSets.listeners;

import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class SantaListener implements Listener {
    @EventHandler
    public void damageReductionWithWeapon(EntityDamageByEntityEvent e ) {
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            Player damaged = ((Player) e.getEntity()).getPlayer();
            ItemStack hand = damaged.getItemInHand();
            if (hand == null || hand.getType() == Material.AIR)
                return;
                NBTWrapper wrapper = new NBTWrapper(hand);
                if(!wrapper.hasKey("cosmicType"))
                    return;
                if(wrapper.getString("cosmicType").equals("SANTA_WEAPON"))
                    e.setDamage(e.getDamage() * 0.85);
        }
    }
}
