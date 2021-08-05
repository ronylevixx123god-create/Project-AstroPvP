package me.bryside.weaponrunes.runes;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import java.util.List;

import me.bryside.weaponrunes.enums.WeaponBonusType;
import me.bryside.weaponrunes.utils.WeaponBonus;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class ChampionWeaponBonus extends WeaponBonus {
    public List<String> getLore() {
        return Lists.newArrayList(new String[] { CC.GreenB + " ➥ " + CC.Gray + "50% Chance to block enemy Self Destruct", CC.GreenB + " ➥ " + CC.Gray + "-25% Incoming DMG from Axes", CC.GreenB + " ➥ " + CC.Gray + "Zeus Strike Effect"});
    }

    public WeaponBonusType getType() {
        return WeaponBonusType.CHAMPION;
    }

    public void onAttack(Player damager, Entity damaged, EntityDamageByEntityEvent event) {

    }

    public void onAttacked(Player damaged, Entity damager, EntityDamageByEntityEvent event) {
        if (!(damager instanceof Player)) return;
        if (!(damaged instanceof Player)) return;
        if (((Player) damager).getItemInHand().getType() == Material.DIAMOND_AXE || ((Player) damager).getItemInHand().getType() == Material.GOLD_AXE) {
            event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.75D);
        }
    }
}
