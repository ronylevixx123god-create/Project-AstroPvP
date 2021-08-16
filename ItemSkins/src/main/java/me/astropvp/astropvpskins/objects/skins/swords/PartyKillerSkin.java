package me.astropvp.astropvpskins.objects.skins.swords;

import me.astropvp.astropvpskins.objects.ItemSkin;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PartyKillerSkin extends ItemSkin {


    @Override
    public void onAttack(Player damager, Entity damaged, EntityDamageByEntityEvent event) {
        damager.setLastDamage(damager.getLastDamage()*1.05);
    }


    @Override
    public void onAttacked(Player damaged, Entity damager, EntityDamageByEntityEvent event) {
        damaged.setLastDamage(damaged.getLastDamage()*0.95);
    }
}
