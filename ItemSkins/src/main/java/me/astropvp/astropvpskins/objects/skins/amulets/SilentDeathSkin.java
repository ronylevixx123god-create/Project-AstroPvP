package me.astropvp.astropvpskins.objects.skins.amulets;

import me.astropvp.astropvpskins.objects.ItemSkin;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class SilentDeathSkin extends ItemSkin {
    public void onAttack(Player damager, Entity damaged, EntityDamageByEntityEvent event) {
        event.setDamage(event.getDamage() * 1.03);
    }

    public void onAttacked(Player damaged, Entity damager, EntityDamageByEntityEvent event) {
        if(Math.random() >= 0.05) {
            //silent death ability
        }
    }


}
