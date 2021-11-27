package com.astropvp.cc.listener;

import com.astropvp.cc.packet.type.out.PacketOutHitSplat;
import com.astropvp.cc.player.CosmicPlayer;
import com.astropvp.cc.player.CosmicPlayers;
import com.astropvp.cc.util.Chat;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.text.DecimalFormat;
import java.util.concurrent.ThreadLocalRandom;

public class PlayerListener implements Listener {
    private static final DecimalFormat FORMAT = new DecimalFormat("##.#");

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player))return;
        if (event.isCancelled())return;

        double finalDamage = event.getFinalDamage();

        Location place = updateDamageSplatLocation(event.getEntity().getLocation());

        if((int) finalDamage <= 0)return;
        if (finalDamage > 44) {
            finalDamage = 44;
        }

        PacketOutHitSplat hitSplat = new PacketOutHitSplat(new PacketOutHitSplat.HitSplatData(event.getEntity().getEntityId(),
                colorDamage(finalDamage),
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
