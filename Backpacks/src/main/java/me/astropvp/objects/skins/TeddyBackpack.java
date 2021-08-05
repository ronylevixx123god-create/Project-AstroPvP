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

public class TeddyBackpack extends Backpack {
    List<Player> equippedPlayers = Lists.newArrayList();

    @Override
    public BackpackType getType() {
        return BackpackType.VALENTINES_2021_BACKPACK;
    }


    @Override
    public List<String> getLore() {
        return Lists.newArrayList(CC.Red + "Immune to (Lethal) Sniper",
                CC.Red + "Immune to Hero Killer",
                CC.Red + "+2% Outgoing Damage");
    }

    @Override
    public void onAttack(Player damager, Entity damaged, EntityDamageByEntityEvent e) {
        e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.02);
    }
}
