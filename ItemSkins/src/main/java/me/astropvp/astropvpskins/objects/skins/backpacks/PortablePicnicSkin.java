package me.astropvp.astropvpskins.objects.skins.backpacks;

import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.astropvpskins.objects.ItemSkin;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PortablePicnicSkin extends ItemSkin {
    public void onAttacked(Player damaged, Entity damager, EntityDamageByEntityEvent event) {
        event.setDamage(event.getDamage() * 0.96);
    }
    //TODO: DO THE EFFECT FOR THIS MAYBE?
}