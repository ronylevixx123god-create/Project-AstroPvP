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

public class DeepSpaceBackpackSkin extends Backpack { //NEED TO CODE IMMUNE TO SOUL TRAP (ARROWFUNCTION)
    @Override
    public BackpackType getType() {
        return BackpackType.DEEP_SPACE_BACKPACK;
    }

    @Override
    public List<String> getLore() {
        return Lists.newArrayList(CC.Red + "+3% Outgoing Damage", CC.Red + "Immune to Soul Trap");
    }

    @Override
    public void onAttack(Player damager, Entity damaged, EntityDamageByEntityEvent e) {
        e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.03);
    }
}
