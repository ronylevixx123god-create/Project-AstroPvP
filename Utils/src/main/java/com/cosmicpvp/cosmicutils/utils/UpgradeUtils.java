/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  me.ifamasssxd.factionupgrades.FactionUpgradeAPI
 *  me.ifamasssxd.factionupgrades.struct.FactionUpgrade
 */
package com.cosmicpvp.cosmicutils.utils;

import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.upgrade.FactionUpgrade;
import org.bukkit.entity.Player;

public class UpgradeUtils {
    public static boolean factionUpgradesEnabled = false;

    public static int getUpgradeLevel(Player player, String upgradeName) {
        if (!factionUpgradesEnabled) {
            return 0;
        }
        try {
            return FPlayers.getInstance().getByPlayer(player).getFaction().getUpgrade(FactionUpgrade.valueOf(upgradeName));
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int getUpgradeLevel(String factionId, String upgradeName) {
        if (!factionUpgradesEnabled) {
            return 0;
        }
        try {
            return Factions.getInstance().getFactionById(factionId).getUpgrade(FactionUpgrade.valueOf(upgradeName));
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}

