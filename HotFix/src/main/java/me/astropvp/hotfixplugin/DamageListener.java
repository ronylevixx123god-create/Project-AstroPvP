package me.astropvp.hotfixplugin;

import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.ifamasssxd.cosmicsky.inventorypets.CosmicSkyInventoryPets;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageListener implements Listener {
    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e) {
        if(e.getEntity() instanceof Player) {
            if(((Player) e.getDamager()).getItemInHand().getType().toString().endsWith("_AXE"))
                e.setDamage(e.getDamage() * 1.4);
        }
    }
}
