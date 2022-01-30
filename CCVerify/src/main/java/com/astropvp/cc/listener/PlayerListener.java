package com.astropvp.cc.listener;

import com.astropvp.cc.CCVerify;
import com.astropvp.cc.packet.type.out.PacketOutCooldown;
import com.astropvp.cc.packet.type.out.PacketOutHealthBar;
import com.astropvp.cc.packet.type.out.PacketOutHitSplat;
import com.astropvp.cc.player.CosmicPlayer;
import com.astropvp.cc.player.CosmicPlayers;
import com.astropvp.cc.util.Chat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.plugin.Plugin;

import java.text.DecimalFormat;
import java.util.concurrent.ThreadLocalRandom;

public class PlayerListener implements Listener {
    private static final DecimalFormat FORMAT = new DecimalFormat("##.#");

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDamage(EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player))
            return;
        if (event.isCancelled())
            return;
        Player damager = (Player)((Object)event.getDamager());
        Player damaged = (Player)((Object)event.getEntity());

        final double oldHealth = damaged.getHealth();
        Bukkit.getScheduler().scheduleSyncDelayedTask(CCVerify.get(), () -> {
            if (damaged.isDead()) {
                return;
            }
            double healthDifference = Math.round(damaged.getHealth() - oldHealth);
            if (healthDifference >= 0.0) {
                return;
            }

            healthDifference = healthDifference*-1;

            if (healthDifference > 44) {
                healthDifference = 44;
            }

            CosmicPlayer cDamager = CosmicPlayers.get().getCosmicPlayer((Player) event.getDamager());
            cDamager.sendHealthBar(damaged);
            CosmicPlayer cDamaged = CosmicPlayers.get().getCosmicPlayer(damaged);
            cDamaged.sendPacket(new PacketOutCooldown("Combat Tag", "DEBUFF", false, -43691, "Combat Tag", 15000, 15000, null, 283));

            Location place = updateDamageSplatLocation(event.getEntity().getLocation());


            PacketOutHitSplat hitSplat = new PacketOutHitSplat(new PacketOutHitSplat.HitSplatData(event.getEntity().getEntityId(),
                    colorDamage(healthDifference),
                    "HEALTH",
                    place.getX(),
                    place.getY(),
                    place.getZ()));

            for(CosmicPlayer cosmicPlayer : CosmicPlayers.get().getAll()){

                if(cosmicPlayer.getPlayer().getWorld() != event.getEntity().getWorld())continue;

                if(cosmicPlayer.getPlayer().getLocation().distance(event.getEntity().getLocation()) > 15)continue;

                if(cosmicPlayer.getPlayer() == event.getEntity())continue;

                cosmicPlayer.sendPacket(hitSplat);
            }
        }, 3L);



    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onThrow(ProjectileLaunchEvent event) {
        if(event.getEntity() instanceof EnderPearl && event.getEntity().getShooter() instanceof Player) {
            PacketOutCooldown packet = new PacketOutCooldown("Enderpearl", "DEBUFF", false, -43691, "Enderpearl", 10000, 10000, null, 368);
            CosmicPlayer cPlayer = CosmicPlayers.get().getCosmicPlayer((Player) event.getEntity().getShooter());
            cPlayer.sendPacket(packet);
        }

    }

    private Location updateDamageSplatLocation(Location location){
        location = location.add(ThreadLocalRandom.current().nextBoolean() ? -0.25 : 0.25, ThreadLocalRandom.current().nextBoolean() ? -0.25 : 0.25, ThreadLocalRandom.current().nextBoolean() ? -0.25 : 0.25);
        return location;
    }

    private String colorDamage(double finalDamage){
        if(finalDamage < 10.0D){
            return Chat.c("&c&l-" + (int) (finalDamage) + " HP");
        }else{
            return Chat.c("&4&l-" + (int) (finalDamage) + " HP");
        }
    }
}
