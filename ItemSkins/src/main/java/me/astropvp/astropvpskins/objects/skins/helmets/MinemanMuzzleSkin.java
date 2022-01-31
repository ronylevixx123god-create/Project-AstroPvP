package me.astropvp.astropvpskins.objects.skins.helmets;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.astropvpskins.objects.ItemSkin;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.List;

public class MinemanMuzzleSkin extends ItemSkin {
    //TODO: 3.5% chance to negate cosmic cape for 10s
    //TODO: Phoenix ability
    //TODO: Banshee RKO

    @Override
    public void onAttacked(Player damaged, Entity damager, EntityDamageByEntityEvent event) {
        if(Math.random() < 0.1) {
            List<Entity> nearby = damaged.getNearbyEntities(3, 3, 3);
            nearby.forEach(entity -> {
                if(!(entity instanceof Player))
                    nearby.remove(entity);
            });
            if(nearby.isEmpty())
                return;
            double dmg = event.getDamage();
            double dmgToEach = Math.ceil(dmg/nearby.size());
            nearby.forEach(player -> {
                ((Player)player).damage(dmgToEach);
                player.sendMessage(CC.translate("&8&l** " + damaged.getName() + "'s Mineman Muzzle &7[" + dmgToEach + "DMG] &8&l**"));
            });
        }
    }
}
