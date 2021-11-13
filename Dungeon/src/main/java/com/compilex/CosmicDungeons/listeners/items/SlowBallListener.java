/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  me.ifamasssxd.cosmicskycustoms.CosmicSkyCustomItems
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Snowball
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.ProjectileLaunchEvent
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package com.compilex.CosmicDungeons.listeners.items;

import com.compilex.CosmicDungeons.CosmicDungeonsPlugin;
import com.cosmicpvp.cosmicutils.utils.CC;
import me.ifamasssxd.cosmicskycustoms.CosmicSkyCustomItems;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class SlowBallListener
implements Listener {
    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() instanceof Player && event.getEntity() instanceof Snowball) {
            Player player = (Player)event.getEntity().getShooter();
            if (CosmicSkyCustomItems.get().getItemManager().getCustomItem("slow_ball").createItem(null, new Object[0]).isSimilar(player.getItemInHand())) {
                event.getEntity().setMetadata("slow_ball", (MetadataValue)new FixedMetadataValue((Plugin)CosmicDungeonsPlugin.get(), (Object)true));
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Snowball && event.getDamager().hasMetadata("slow_ball") && event.getEntity() instanceof Player && !event.getEntity().hasMetadata("slow_balled")) {
            Player player = (Player)event.getEntity();
            player.setMetadata("slow_balled", (MetadataValue)new FixedMetadataValue((Plugin)CosmicDungeonsPlugin.get(), (Object)true));
            player.sendMessage(CC.translate((String)"&3You have been &f&nslowed&3 by a &3&lSlow&f&lBall&3 for &f3s."));
            player.setWalkSpeed(player.getWalkSpeed() / 3.0f);
            player.setFlySpeed(player.getFlySpeed() / 2.0f);
            Bukkit.getScheduler().runTaskLater((Plugin)CosmicDungeonsPlugin.get(), () -> {
                player.setWalkSpeed(0.2f);
                player.removeMetadata("slow_balled", (Plugin)CosmicDungeonsPlugin.get());
            }, 60L);
        }
    }
}

