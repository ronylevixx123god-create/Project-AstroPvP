/*
 * Decompiled with CFR 0.145.
 *
 * Could not load the following classes:
 *  me.ifamasssxd.cosmicauth.CosmicAuth
 *  me.ifamasssxd.cosmicauth.manager.AuthenticationManager
 *  org.bukkit.Bukkit
 *  org.bukkit.plugin.Plugin
 */
package com.massivecraft.factions.util;

import me.ifamasssxd.cosmicauth.CosmicAuth;
import org.bukkit.Bukkit;

import java.util.UUID;

public class AuthenticationUtils {
    private static CosmicAuth cosmicAuth;
    private static boolean isEnabled;

    static {
        isEnabled = false;
    }

    public static boolean loadAuthenication() {
        try {
            cosmicAuth = (CosmicAuth) Bukkit.getPluginManager().getPlugin("CosmicAuthentication");
            isEnabled = true;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isEnabled(UUID uuid) {
        return cosmicAuth.getAuthenticationManager().getAuthenticationMap().containsKey(uuid);
    }

    public static boolean isEnabled() {
        return isEnabled;
    }
}

