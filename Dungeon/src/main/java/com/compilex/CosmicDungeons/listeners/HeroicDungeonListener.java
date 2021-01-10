/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageModifier
 *  org.bukkit.event.player.PlayerInteractEvent
 */
package com.compilex.CosmicDungeons.listeners;

import com.compilex.CosmicDungeons.DungeonsAPI;
import com.compilex.CosmicDungeons.dungeons.libs.DungeonPlayer;
import com.compilex.CosmicDungeons.listeners.DungeonPortalListener;
import com.cosmicpvp.cosmicutils.utils.CC;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class HeroicDungeonListener
implements Listener {
    DungeonPortalListener dungeonPortalListener;

    @EventHandler
    public void onBossHit(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            return;
        }
        if (e.getDamager() instanceof Player) {
            Player player = (Player)e.getDamager();
            DungeonPlayer dp = DungeonsAPI.get().getPlayerDungeonData(player);
            if (dp.getCurrentDungeon() != null && dp.getCurrentDungeon().getHeroicDungeon()) {
                if (DungeonPortalListener.getDebuffs().contains(7) && player.getItemInHand().equals((Object)Material.BOW)) {
                    e.setCancelled(true);
                    player.sendMessage(CC.DarkRed + "Due to being in a heroic dungeon, you cannot use bows!");
                }
                e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.25);
                if (DungeonPortalListener.getDebuffs().contains(8) && e.getDamage(EntityDamageEvent.DamageModifier.BASE) > 30.0) {
                    e.setDamage(30.0);
                }
            }
        } else if (!(e.getDamager() instanceof Player)) {
            Player player = (Player)e.getEntity();
            DungeonPlayer dp = DungeonsAPI.get().getPlayerDungeonData(player);
            if (dp.getCurrentDungeon() != null && dp.getCurrentDungeon().getHeroicDungeon()) {
                e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 2.5);
            }
        }
    }

    @EventHandler
    public void onEnderPearl(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        DungeonPlayer dp = DungeonsAPI.get().getPlayerDungeonData(player);
        if (dp.getCurrentDungeon() != null && dp.getCurrentDungeon().getHeroicDungeon()) {
            if (DungeonPortalListener.getDebuffs().contains(5) && e.getAction() == Action.RIGHT_CLICK_BLOCK && player.getItemInHand().equals((Object)Material.ENDER_PEARL)) {
                e.setCancelled(true);
                player.sendMessage(CC.DarkRed + "Due to being in a heroic dungeon, you cannot use enderpearls!");
            }
        }
    }

    @EventHandler
    public void onPotion(PlayerInteractEvent e) {
        DungeonPlayer dp;
        Player player = e.getPlayer();
        if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK && player.getItemInHand().equals((Object)Material.POTION)) && (dp = DungeonsAPI.get().getPlayerDungeonData(player)).getCurrentDungeon() != null && dp.getCurrentDungeon().getHeroicDungeon()) {
            if (DungeonPortalListener.getDebuffs().contains(6)) {
                e.setCancelled(true);
                player.sendMessage(CC.DarkRed + "Due to being in a heroic dungeon, you cannot use potions!");
            }
        }
    }
}

