package me.astropvp.astropvpskins.objects.skins.belts;

import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.astropvpskins.objects.ItemSkin;
import net.astropvp.damagehandler.AstroDamageHandler;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class SexyBeltSkin extends ItemSkin {

    public void onAttacked(Player damaged, Entity damager, EntityDamageByEntityEvent event) {
        if(damager instanceof Projectile)
            AstroDamageHandler.getInstance().getDamageHandler().reduceDamage(7.5, event, "Sexy Belt");
    }
}

