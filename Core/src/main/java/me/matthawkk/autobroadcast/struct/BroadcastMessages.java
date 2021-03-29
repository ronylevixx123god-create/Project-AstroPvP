/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 */
package me.matthawkk.autobroadcast.struct;

import org.bukkit.ChatColor;

public enum BroadcastMessages {
    DISCORD(ChatColor.GREEN + "&8[&a&lAstro&e&lPvP&8] &aMake sure to join our discord at &f/discord&a."),
    STORE(ChatColor.GREEN + "&8[&a&lAstro&e&lPvP&8] &aMake sure to take advantage of the &f&l50% SALE&a on the store &7(/buy)&a."),
    DI(ChatColor.GREEN + "&8[&a&lAstro&e&lPvP&8] &aYou can toggle damage indicators using &f/di&a."),
    APOLLO(ChatColor.GREEN + "&8[&a&lAstro&e&lPvP&8] &aYou can buy the &b&lApollo Rank&a on &fbuy.astropvp.net&a.");

    String message;

    private BroadcastMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}

