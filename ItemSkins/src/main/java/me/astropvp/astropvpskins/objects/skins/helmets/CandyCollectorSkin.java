package me.astropvp.astropvpskins.objects.skins.helmets;

import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.astropvpskins.objects.ItemSkin;
import net.astropvp.damagehandler.AstroDamageHandler;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CandyCollectorSkin extends ItemSkin {

    public void onAttacked(Player damaged, Entity damager, EntityDamageByEntityEvent event) {
        if(damager instanceof Player) {
            Player player = (Player) damager;
            if(player.getItemInHand().getType().toString().endsWith("_SWORD"))
                AstroDamageHandler.getInstance().getDamageHandler().reduceDamage(10, event, "Candy Collector");
        }
    }
}
