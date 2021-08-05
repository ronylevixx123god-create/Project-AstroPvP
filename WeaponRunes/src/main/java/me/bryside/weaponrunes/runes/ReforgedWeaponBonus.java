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

public class ReforgedWeaponBonus extends WeaponBonus {
    public List<String> getLore() {
        return Lists.newArrayList(new String[] { CC.AquaB + " ➥ " + CC.Gray + "50% Chance to negate Soul Harvester", CC.AquaB + " ➥ " + CC.Gray + "1.5x Silence duration multiplier", CC.AquaB + " ➥ " + CC.Gray + "1.25x Divine Damage multiplier"});
    }

    public WeaponBonusType getType() {
        return WeaponBonusType.REFORGED;
    }

    public void onAttack(Player damager, Entity damaged, EntityDamageByEntityEvent event) {
        event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.05D);
        if (Math.random() < 0.02D) {
            event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 2.0D);
            if (damaged instanceof Player)
                damager.sendMessage(CC.DarkAquaB + "[" + CC.AquaB + "HEAVY HIT " + CC.Gray + "(2x damage to " + ((Player)damaged).getDisplayName() + ")" + CC.DarkAquaB + " ]");
        }
    }

    public void onAttacked(Player damaged, Entity damager, EntityDamageByEntityEvent event) {
        event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.98D);
    }
}
