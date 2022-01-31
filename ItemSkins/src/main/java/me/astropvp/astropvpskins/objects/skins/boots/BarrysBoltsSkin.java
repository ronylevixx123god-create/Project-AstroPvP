package me.astropvp.astropvpskins.objects.skins.boots;

import me.astropvp.astropvpskins.AstroItemSkinsPlugin;
import me.astropvp.astropvpskins.objects.ItemSkin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.ArrayList;
import java.util.List;

public class BarrysBoltsSkin extends ItemSkin {
    //TODO: add messages, make ability work in other plugins (gonna be longggggg :( )
    List<Player> currentFlashPlayers = new ArrayList<>();

    List<Player> currentSilencedPlayers = new ArrayList<>();

    @Override
    public void onAttacked(Player damaged, Entity damager, EntityDamageByEntityEvent event) {
        if(!(damaged instanceof Player))
            return;
        if(!this.currentFlashPlayers.contains(damaged) && Math.random() < 0.1) {
            this.currentFlashPlayers.add(damaged);
            Bukkit.getScheduler().runTaskLaterAsynchronously(AstroItemSkinsPlugin.getInstance(), () -> this.currentFlashPlayers.remove(damaged), 100L);
        }
    }

    @Override
    public void onAttack(Player damager, Entity damaged, EntityDamageByEntityEvent event) {
        if(!(damaged instanceof Player))
            return;
        if(this.currentFlashPlayers.contains(damaged) && !this.currentFlashPlayers.contains(damager)) {
            this.currentSilencedPlayers.add(damager);
            Bukkit.getScheduler().runTaskLaterAsynchronously(AstroItemSkinsPlugin.getInstance(), () -> this.currentSilencedPlayers.remove(damager), 200L);
        }
    }
}
