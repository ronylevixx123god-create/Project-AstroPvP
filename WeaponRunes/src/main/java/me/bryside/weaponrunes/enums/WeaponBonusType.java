package me.bryside.weaponrunes.enums;

import com.cosmicpvp.cosmicutils.utils.CC;
import java.util.Arrays;

import me.bryside.weaponrunes.runes.ChampionWeaponBonus;
import me.bryside.weaponrunes.runes.DemonWeaponBonus;
import me.bryside.weaponrunes.runes.HeroWeaponBonus;
import me.bryside.weaponrunes.runes.ReforgedWeaponBonus;
import me.bryside.weaponrunes.utils.WeaponBonus;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;

public enum WeaponBonusType {
    CHAMPION("CHAMPION", ChatColor.GREEN, CC.GreenB + "Champion", (short)10, (WeaponBonus)new ChampionWeaponBonus()),
    HERO("HERO", ChatColor.YELLOW, CC.YellowB + "Hero", (short)14, (WeaponBonus)new HeroWeaponBonus()),
    REFORGED("REFORGED", ChatColor.AQUA, CC.AquaB + "Reforged", (short)6, (WeaponBonus)new ReforgedWeaponBonus()),
    DEMON("DEMON", ChatColor.DARK_RED, CC.DarkRedB + "Demon", DyeColor.RED.getDyeData(), new DemonWeaponBonus());

    String id;

    ChatColor color;

    String name;

    Short data;

    WeaponBonus instance;

    WeaponBonusType(String id, ChatColor color, String name, short data, WeaponBonus instance) {
        this.id = id;
        this.color = color;
        this.name = name;
        this.data = Short.valueOf(data);
        this.instance = instance;
    }

    public String getId() {
        return this.id;
    }

    public ChatColor getColor() {
        return this.color;
    }

    public String getName() {
        return this.name;
    }

    public Short getData() {
        return this.data;
    }

    public WeaponBonus getInstance() {
        return this.instance;
    }

    public static WeaponBonusType fromId(String id) {
        return Arrays.<WeaponBonusType>stream(values()).filter(f -> f.getId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }
}
