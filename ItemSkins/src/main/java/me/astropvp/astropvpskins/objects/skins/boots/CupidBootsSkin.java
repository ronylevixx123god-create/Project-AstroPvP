package me.astropvp.astropvpskins.objects.skins.boots;

import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.astropvpskins.objects.ItemSkin;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

public class CupidBootsSkin extends ItemSkin {

    public void onAttack(Player damager, Entity damaged, EntityDamageByEntityEvent event) {
        if(!WorldGuardUtils.isPvPEnabled(damager.getLocation()))
            return;
        if (!WorldGuardUtils.isPvPEnabled(damaged.getLocation()))
            return;
        damaged.setVelocity(new Vector(Math.random(), Math.random(), Math.random()));
    }
}
