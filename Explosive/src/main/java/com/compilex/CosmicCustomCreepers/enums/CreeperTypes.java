/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.utils.CC
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.me.astropvp.cratekeys.inventory.ItemStack
 */
package com.compilex.CosmicCustomCreepers.enums;

import com.compilex.CosmicCustomCreepers.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.utils.CC;
import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum CreeperTypes {
    Huge(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "Gigantic Creeper", ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + "Explodes in a much larger radius."),
    Lucky(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "Lucky Creeper", ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + "Increases chances of spawner drops."),
    Tactical(ChatColor.BLUE.toString() + ChatColor.BOLD.toString() + "Tactical Creeper", ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + "Explosion only affects mob spawners."),
    Stun(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Stun Creeper", ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + "Explosion freezes ALL players", ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + "within a 6x6 area for 4s."),
    Raid(ChatColor.DARK_RED.toString() + ChatColor.BOLD.toString() + "Raid Creeper", ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + "Lucky + Tactical, with a", ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + "100% mob spawner drop rate."),
    Vile(ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD.toString() + "Vile Creeper", CC.GrayI + "Corrupts the area it explodes", CC.GrayI + "in, applying " + CC.RedB + "Rot and Decay V", CC.GrayI + "for a 5 second duration.");

    String name;
    String[] lore;

    private CreeperTypes(String name, String ... lore) {
        this.name = name;
        this.lore = lore;
    }

    public String getItemName() {
        return this.name.replace(ChatColor.BOLD.toString(), "");
    }

    public String getMobName() {
        return this.name;
    }

    public static ItemStack getCCItem(CreeperTypes tt) {
        return ItemUtils.buildItem(Material.MONSTER_EGG, (short)50, tt.getItemName(), tt.lore);
    }

    public static ItemStack getRandomCustomCreeperEgg(int amount) {
        CreeperTypes tt = CreeperTypes.values()[new Random().nextInt(CreeperTypes.values().length)];
        if (tt == Raid) {
            tt = Huge;
        }
        ItemStack i = CreeperTypes.getCCItem(tt);
        i.setAmount(amount);
        return i;
    }
}

