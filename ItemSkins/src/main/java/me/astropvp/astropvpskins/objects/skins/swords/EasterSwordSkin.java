package me.astropvp.astropvpskins.objects.skins.swords;

import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.astropvpskins.objects.ItemSkin;
import org.arkhamnetwork.enchantments.enchantments.axes.Devour;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EasterSwordSkin extends ItemSkin {

    public void onAttack(Player damager, Entity damaged, EntityDamageByEntityEvent event) {
        (new Devour()).applyEffect(damager, (LivingEntity) damaged, 5, event);
        if(damaged instanceof Player)
            return;
        event.setDamage(event.getDamage() * 1.15);
    }
}


