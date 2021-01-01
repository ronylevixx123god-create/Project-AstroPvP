package me.astropvp.objects.skins;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import me.astropvp.objects.Backpack;
import me.astropvp.objects.BackpackType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.List;

public class CursedWingsSkin extends Backpack {
    List<Player> equippedPlayers = Lists.newArrayList();

    @Override
    public BackpackType getType() {
        return BackpackType.CURSED_WINGS;
    }


    @Override
    public List<String> getLore() {
        return Lists.newArrayList(CC.Red + "-75% Silence Duration",
                CC.Red + "Blocks all enemy Rocket Escape",
                CC.Red + "-3% Incoming Damage");
    }

    @Override
    public void onDamaged(Player damaged, Entity damager, EntityDamageByEntityEvent e) {
        e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.97);
    }
}
