/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 */
package com.compilex.CosmicOutposts.outpost.enums;

import org.bukkit.ChatColor;

public enum OutpostTier {
    NONE("None", ChatColor.WHITE),
    TRAINEE("Trainee", ChatColor.GRAY),
    HERO("Hero", ChatColor.AQUA),
    COSMONAUT("Cosmonaut", ChatColor.GOLD),
    VANILLA("Vanilla", ChatColor.RED);

    public String chatPrefix;
    public ChatColor chatColor;

    private OutpostTier(String s, ChatColor cc) {
        this.chatPrefix = s;
        this.chatColor = cc;
    }
}

