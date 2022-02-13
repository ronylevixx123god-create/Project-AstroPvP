package me.astropvp.astropvpskins.objects.skins.boots;

import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.astropvpskins.objects.ItemSkin;
import net.astropvp.damagehandler.AstroDamageHandler;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class TeddySlippersSkin extends ItemSkin {

    public void onAttack(Player damager, Entity damaged, EntityDamageByEntityEvent event) {
        AstroDamageHandler.getInstance().getDamageHandler().increaseDamage(1, event, "Teddy Slippers");
    }

    public void onAttacked(Player damaged, Entity damager, EntityDamageByEntityEvent event) {
        AstroDamageHandler.getInstance().getDamageHandler().reduceDamage(2, event, "Teddy Slippers");
    }
}

