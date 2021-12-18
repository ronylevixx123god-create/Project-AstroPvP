package com.astropvp.cc.listener;

import com.astropvp.cc.CCVerify;
import com.astropvp.cc.packet.type.out.PacketOutHealthBar;
import com.astropvp.cc.packet.type.out.PacketOutHitSplat;
import com.astropvp.cc.player.CosmicPlayer;
import com.astropvp.cc.player.CosmicPlayers;
import com.astropvp.cc.util.Chat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
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
        Player player = (Player)((Object)event.getEntity());

        double oldHealth = player.getHealth();
        Bukkit.getScheduler().scheduleSyncDelayedTask(CCVerify.get(), () -> {
            if (player.isDead()) {
                return;
            }
            double healthDifference = Math.round(player.getHealth() - oldHealth);
            if (healthDifference >= 0.0) {
                return;
            }

            healthDifference = healthDifference*-1;

            if (healthDifference > 44) {
                healthDifference = 44;
            }

            CosmicPlayer cp = CosmicPlayers.get().getCosmicPlayer((Player) event.getDamager());
            cp.setCurrentHealthBar((Player) event.getEntity());
            PacketOutHealthBar healthBar = new PacketOutHealthBar(cp.getCurrentHealthBar());
            Bukkit.broadcastMessage("send health bar packet");
            cp.sendPacket(healthBar);

            Location place = updateDamageSplatLocation(event.getEntity().getLocation());


            PacketOutHitSplat hitSplat = new PacketOutHitSplat(new PacketOutHitSplat.HitSplatData(event.getEntity().getEntityId(),
                    colorDamage(healthDifference),
                    "HEALTH",
                    place.getX(),
                    place.getY(),
                    place.getZ()));

            for(CosmicPlayer cosmicPlayer : CosmicPlayers.get().getAll()){

                if(cosmicPlayer.getPlayer().getWorld() != event.getEntity().getWorld())continue;

                if(cosmicPlayer.getPlayer().getLocation().distance(event.getEntity().getLocation()) > 80)continue;

                if(cosmicPlayer.getPlayer() == event.getEntity())continue;

                cosmicPlayer.sendPacket(hitSplat);
            }
        }, 1L);



    }

    private Location updateDamageSplatLocation(Location location){
        location = location.add(ThreadLocalRandom.current().nextBoolean() ? -0.25 : 0.25, ThreadLocalRandom.current().nextBoolean() ? -0.25 : 0.25, ThreadLocalRandom.current().nextBoolean() ? -0.25 : 0.25);
        return location;
    }

    private String colorDamage(double finalDamage){
        if(finalDamage <= 10.0D){
            return Chat.c("&c&l-" + (int) (finalDamage) + " HP");
        }else{
            return Chat.c("&4&l-" + (int) (finalDamage) + " HP");
        }
    }
}
