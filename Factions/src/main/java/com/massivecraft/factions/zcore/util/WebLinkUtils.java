/*
 * Decompiled with CFR 0.145.
 *
 * Could not load the following classes:
 *  me.ifamasssxd.cosmicweblink.CosmicWebLink
 *  me.ifamasssxd.cosmicweblink.managers.ExtraDataManager
 *  org.bukkit.Bukkit
 */
package com.massivecraft.factions.zcore.util;

import me.ifamasssxd.cosmicweblink.CosmicWebLink;
import org.bukkit.Bukkit;

import java.util.UUID;

public class WebLinkUtils {
    private static boolean webLinkEnabled = false;

    public static boolean isEnabled() {
        if (!webLinkEnabled) {
            webLinkEnabled = Bukkit.getPluginManager().isPluginEnabled("CosmicWebLink");
        }
        return webLinkEnabled;
    }

    public static int getString(UUID uuid, String str) {
        if (!WebLinkUtils.isEnabled()) {
            return 0;
        }
        try {
            return CosmicWebLink.get().getExtraDataManager().getInt(uuid, str);
        } catch (Exception e) {
            return 0;
        }
    }
}

