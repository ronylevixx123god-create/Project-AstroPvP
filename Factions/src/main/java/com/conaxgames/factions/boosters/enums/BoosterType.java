/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 */
package com.conaxgames.factions.boosters.enums;

import org.bukkit.ChatColor;

public enum BoosterType {
    EXP("EXP", "XP", ChatColor.GRAY + "Multiplies XP gained by all faction//" + ChatColor.GRAY + "members from killing mobs while active."),
    MCMMO("MCMMO EXP", "mcMMO", ChatColor.GRAY + "Multiplies mcMMO XP gained by all//faction members while active.");

    String itemDescription;

    String itemName;

    String name;

    BoosterType(String name, String itemName, String itemDescription) {
        this.name = name;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
    }

    public String getName() {
        return this.name;
    }

    public String getItemName() {
        return this.itemName;
    }

    public String getItemDescription() {
        return this.itemDescription;
    }

    public static BoosterType fromItemName(String name) {
        for (BoosterType type : values()) {
            if (type.getItemName().equals(name))
                return type;
        }
        return null;
    }

    public static BoosterType fromName(String name) {
        for (BoosterType type : values()) {
            if (type.getName().equals(name))
                return type;
        }
        return null;
    }
}
