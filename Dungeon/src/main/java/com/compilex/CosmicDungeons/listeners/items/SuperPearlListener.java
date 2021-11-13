/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  org.bukkit.Material
 *  org.bukkit.entity.EnderPearl
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.ProjectileLaunchEvent
 */
package com.compilex.CosmicDungeons.listeners.items;

import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import org.bukkit.Material;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class SuperPearlListener
implements Listener {
    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        Player player;
        if (event.getEntity().getShooter() instanceof Player && event.getEntity() instanceof EnderPearl && (player = (Player)event.getEntity().getShooter()).getItemInHand() != null && player.getItemInHand().getType() != Material.AIR && new NBTWrapper(player.getItemInHand()).getString("cosmicType").equals("super_pearl")) {
            event.getEntity().setVelocity(event.getEntity().getVelocity().multiply(1.5f));
        }
    }
}

