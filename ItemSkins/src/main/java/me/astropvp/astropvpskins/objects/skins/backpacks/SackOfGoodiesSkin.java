package me.astropvp.astropvpskins.objects.skins.backpacks;

import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import me.astropvp.astropvpskins.objects.ItemSkin;
import net.astropvp.damagehandler.AstroDamageHandler;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class SackOfGoodiesSkin extends ItemSkin {
    public void onAttack(Player damager, Entity damaged, EntityDamageByEntityEvent event) {
        if (!WorldGuardUtils.isPvPEnabled(damager.getLocation())) return;

        AstroDamageHandler.getInstance().getDamageHandler().increaseDamage(2, event, "Sack Goodies");
    }

    public void onAttacked(Player damaged, Entity damager, EntityDamageByEntityEvent event) {
        AstroDamageHandler.getInstance().getDamageHandler().reduceDamage(2, event, "Sack Goodies");
    }
}
