package me.astropvp.astropvpskins.objects.skins.swords;

import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.astropvpskins.objects.ItemSkin;
import net.astropvp.damagehandler.AstroDamageHandler;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class JollyCandySwordSkin extends ItemSkin {

    public void onAttack(Player damager, Entity damaged, EntityDamageByEntityEvent event) {
        event.setDamage(EntityDamageByEntityEvent.DamageModifier.BASE,event.getDamage(EntityDamageByEntityEvent.DamageModifier.BASE)*1.075);
    }
}


