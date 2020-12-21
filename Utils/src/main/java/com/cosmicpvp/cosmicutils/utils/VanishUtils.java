/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.kitteh.vanish.VanishPlugin
 */
package com.cosmicpvp.cosmicutils.utils;

import com.cosmicpvp.cosmicutils.CosmicUtils;
import com.cosmicpvp.cosmicutils.utils.EssentialsUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.kitteh.vanish.VanishPlugin;

public class VanishUtils {
    private static JavaPlugin vanishNoPacket;

    public static boolean toggleVanishNoPacketVanished(Player player, boolean vanish) {
        if (vanishNoPacket == null && CosmicUtils.get().isVanishNoPacketEnabled()) {
            vanishNoPacket = (JavaPlugin)Bukkit.getPluginManager().getPlugin("VanishNoPacket");
        }
        if (vanishNoPacket instanceof VanishPlugin) {
            if (vanish) {
                ((VanishPlugin)vanishNoPacket).getManager().vanish(player, true, false);
            } else {
                ((VanishPlugin)vanishNoPacket).getManager().reveal(player, true, false);
            }
        }
        return VanishUtils.isVanishNoPacketVanished(player);
    }

    public static boolean isVanishNoPacketVanished(Player player) {
        if (vanishNoPacket == null && CosmicUtils.get().isVanishNoPacketEnabled()) {
            vanishNoPacket = (JavaPlugin)Bukkit.getPluginManager().getPlugin("VanishNoPacket");
        }
        if (vanishNoPacket instanceof VanishPlugin) {
            return ((VanishPlugin)vanishNoPacket).getManager().isVanished(player);
        }
        return false;
    }

    public static boolean toggleEssentialsVanished(Player player, boolean vanished) {
        return EssentialsUtils.setVanished(player, vanished);
    }

    public static boolean isEssentialsVanished(Player player) {
        return EssentialsUtils.isVanished(player);
    }

    public static boolean isVanished(Player player) {
        return VanishUtils.isVanishNoPacketVanished(player) || VanishUtils.isEssentialsVanished(player);
    }
}

