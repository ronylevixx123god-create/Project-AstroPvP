/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 */
package com.compilex.CosmicDungeons.listeners;

import com.compilex.CosmicDungeons.DungeonsAPI;
import com.compilex.CosmicDungeons.dungeons.libs.DungeonPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class BossSlayerTrinketListener
implements Listener {
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) {
            return;
        }
        if (event.getEntity() instanceof Player) {
            return;
        }
        Player player = (Player)event.getDamager();
        DungeonPlayer dp = DungeonsAPI.get().getPlayerDungeonData(player);
        if (dp.getCurrentDungeon() != null && dp.getCurrentDungeon().getBossSlayerTrinket()) {
            event.setDamage(event.getDamage() * 1.5);
        }
    }
}

