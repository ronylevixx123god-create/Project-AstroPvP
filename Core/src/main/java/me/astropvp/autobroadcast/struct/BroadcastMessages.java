/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.utils.CC
 */
package me.astropvp.autobroadcast.struct;

import org.bukkit.ChatColor;

public enum BroadcastMessages {
    DISCORD(ChatColor.GREEN + "&8[&a&lAstro&e&lPvP&8] &aMake sure to join our discord at &f/discord&a."),
    STORE(ChatColor.GREEN + "&8[&a&lAstro&e&lPvP&8] &aMake sure to take advantage of the &f&l50% SALE&a on the store &7(/buy)&a."),
    SYNC(ChatColor.GREEN + "&8[&a&lAstro&e&lPvP&8] &aWith the &fAstro Client&a you will get 1.5x EXP and Mobcoins!."),
    APOLLO(ChatColor.GREEN + "&8[&a&lAstro&e&lPvP&8] &aYou can buy the &b&lApollo Rank&a on &fbuy.astropvp.net&a."),
    MOBCOINS(ChatColor.GREEN + "&8[&a&lAstro&e&lPvP&8] &aYou can spend your mobcoins in &f/transfer&a.");

    String message;

    private BroadcastMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}

