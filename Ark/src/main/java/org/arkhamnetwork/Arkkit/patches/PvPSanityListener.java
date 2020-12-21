/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.inventory.ItemStack
 */
package org.arkhamnetwork.Arkkit.patches;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

final class PvPSanityListener
implements Listener {
    PvPSanityListener() {
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onPlayerDamagedByPlayer(EntityDamageEvent e) {
        Player pDamaged;
        if (e.getEntity() instanceof Player && (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK || e.getCause() == EntityDamageEvent.DamageCause.CUSTOM) && ((pDamaged = (Player)e.getEntity()).getHealth() - e.getFinalDamage() <= 0.0 || e.getDamage() >= 125.0) && pDamaged.getHealth() / pDamaged.getMaxHealth() > 0.33 && pDamaged.getHealth() != e.getDamage() && !this.isNaked(pDamaged)) {
            Bukkit.getLogger().info("[Arkkit (PvPSanity)] Preventing " + pDamaged.getName() + " (HP: " + pDamaged.getHealth() + "/" + pDamaged.getMaxHealth() + ") from dieing due to " + e.getDamage() + " DMG (f: " + e.getFinalDamage() + "), new DMG: " + e.getDamage() * 0.33 + ", cause: " + (Object)e.getCause());
            e.setDamage(e.getDamage() * 0.33);
        }
    }

    private boolean isNaked(Player p) {
        for (ItemStack i : p.getEquipment().getArmorContents()) {
            if (i == null || i.getType() == Material.AIR) continue;
            return false;
        }
        return true;
    }
}

