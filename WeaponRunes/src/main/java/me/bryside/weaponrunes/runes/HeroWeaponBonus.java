package me.bryside.weaponrunes.runes;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import java.util.List;

import me.bryside.weaponrunes.enums.WeaponBonusType;
import me.bryside.weaponrunes.utils.WeaponBonus;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class HeroWeaponBonus extends WeaponBonus {
    public List<String> getLore() {
        return Lists.newArrayList(new String[] { CC.YellowB + " ➥ " + CC.Gray + "50% Chance to negate Ancient Ability", CC.YellowB + " ➥ " + CC.Gray + "-5% Incoming DMG", CC.YellowB + " ➥ " + CC.Gray + "Critical Strike Effect"});
    }

    public WeaponBonusType getType() {
        return WeaponBonusType.HERO;
    }

    public void onAttack(Player damager, Entity damaged, EntityDamageByEntityEvent event) {
        if (!(damager instanceof Player)) return;
        if (!(damaged instanceof Player)) return;
        if (Math.random() > 0.1) return;

        event.setDamage(event.getDamage() *1.25);
        damager.sendMessage(CC.translate("&e** HERO CRITICAL STRIKE **"));
    }

    public void onAttacked(Player damaged, Entity damager, EntityDamageByEntityEvent event) {
        event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.95D);
    }
}
