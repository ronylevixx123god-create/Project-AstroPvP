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
        event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.05D);
        if (Math.random() < 0.02D) {
            event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 2.0D);
            if (damaged instanceof Player)
                damager.sendMessage(CC.GoldB + "[" + CC.YellowB + "HEAVY HIT " + CC.Gray + "(2x damage to " + ((Player)damaged).getDisplayName() + ")" + CC.GoldB + " ]");
        }
    }

    public void onAttacked(Player damaged, Entity damager, EntityDamageByEntityEvent event) {
        event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.98D);
    }
}
