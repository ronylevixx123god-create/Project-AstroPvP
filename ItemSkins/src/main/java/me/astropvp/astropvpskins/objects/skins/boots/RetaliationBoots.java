package me.astropvp.astropvpskins.objects.skins.boots;

import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.astropvpskins.objects.ItemSkin;
import net.astropvp.damagehandler.AstroDamageHandler;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class RetaliationBoots extends ItemSkin {

    public void onAttacked(Player damaged, Entity damager, EntityDamageByEntityEvent event) {
        AstroDamageHandler.getInstance().getDamageHandler().reduceDamage(10, event, "Retaliation Boots");
    }

    public void onAttack(Player damager, Entity damaged, EntityDamageByEntityEvent event) {
        if(!(damaged instanceof Player))
            AstroDamageHandler.getInstance().getDamageHandler().increaseDamage(5, event, "Retaliation Boots");
    }
}
