/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 */
package com.conaxgames.factions.boosters.enums;

import org.bukkit.ChatColor;

public enum BoosterType {
    EXP("EXP", "XP", (Object)ChatColor.GRAY + "Multiplies XP gained by all faction//" + (Object)ChatColor.GRAY + "members from killing mobs while active."),
    MCMMO("MCMMO EXP", "mcMMO", (Object)ChatColor.GRAY + "Multiplies mcMMO XP gained by all//faction members while active.");

    String name;
    String itemName;
    String itemDescription;

    public static BoosterType fromItemName(String name) {
        for (BoosterType type : BoosterType.values()) {
            if (!type.getItemName().equals(name)) continue;
            return type;
        }
        return null;
    }

    public static BoosterType fromName(String name) {
        for (BoosterType type : BoosterType.values()) {
            if (!type.getName().equals(name)) continue;
            return type;
        }
        return null;
    }

    private BoosterType(String name, String itemName, String itemDescription) {
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
}

