package me.bryside.weaponrunes.runes;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import me.bryside.weaponrunes.enums.WeaponBonusType;
import me.bryside.weaponrunes.utils.WeaponBonus;

import java.util.List;

public class DemonWeaponBonus extends WeaponBonus {
    @Override
    public List<String> getLore() {
        return Lists.newArrayList(CC.RedB + " ➥ " + CC.Gray + "35% Chance to Block Astro Cage Ability",CC.RedB + " ➥ " + CC.Gray + "1.5x Divine Damage Multiplier", CC.RedB + " ➥ " + CC.Gray + "35% Chance to Block Banshee Pets", CC.RedB + " ➥ " + CC.Gray + "15% Chance to Block Engineer Crystal on 1 Piece");
    }

    @Override
    public WeaponBonusType getType() {
        return WeaponBonusType.DEMON;
    }
}
