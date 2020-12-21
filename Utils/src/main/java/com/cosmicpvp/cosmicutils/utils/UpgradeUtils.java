/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  me.ifamasssxd.factionupgrades.FactionUpgradeAPI
 *  me.ifamasssxd.factionupgrades.struct.FactionUpgrade
 */
package com.cosmicpvp.cosmicutils.utils;

import me.ifamasssxd.factionupgrades.FactionUpgradeAPI;
import me.ifamasssxd.factionupgrades.struct.FactionUpgrade;
import org.bukkit.entity.Player;

public class UpgradeUtils {
    public static boolean factionUpgradesEnabled = false;

    public static int getUpgradeLevel(Player player, String upgradeName) {
        if (!factionUpgradesEnabled) {
            return 0;
        }
        try {
            return FactionUpgradeAPI.getPerkLevelIfAllowed((Player)player, (FactionUpgrade)FactionUpgrade.valueOf((String)upgradeName));
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int getUpgradeLevel(String factionId, String upgradeName) {
        if (!factionUpgradesEnabled) {
            return 0;
        }
        try {
            return FactionUpgradeAPI.getPerkLevelIfAllowed((String)factionId, (FactionUpgrade)FactionUpgrade.valueOf((String)upgradeName));
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}

