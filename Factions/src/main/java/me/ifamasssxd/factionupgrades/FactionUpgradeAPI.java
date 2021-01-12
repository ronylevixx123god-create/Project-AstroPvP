/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.FPlayers
 *  com.massivecraft.factions.Faction
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.entity.Player
 */
package me.ifamasssxd.factionupgrades;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.P;
import me.ifamasssxd.factionupgrades.struct.FactionUpgrade;
import me.ifamasssxd.factionupgrades.struct.FactionUpgradeInfo;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class FactionUpgradeAPI {
    public static int getPerkLevelIfAllowed(Faction faction, FactionUpgrade upgrade) {
        return FactionUpgradeAPI.getPerkLevel(faction.getId(), upgrade);
    }

    public static int getPerkLevelIfAllowed(String factionID, FactionUpgrade upgrade) {
        FactionUpgradeInfo info = P.getP().getUpgradeManager().getUpgradeInfo().get(factionID);
        if (info == null) {
            return 0;
        }
        int retr = info.getUpgradeTier(upgrade);
        if (retr <= 0 || !upgrade.isHeroic()) {
            return retr;
        }
        if (P.getP().getUpgradeManager().canAccessHeroic(factionID, upgrade)) {
            return retr;
        }
        return 0;
    }

    public static int getPerkLevelIfAllowed(Player player, FactionUpgrade upgrade) {
        FPlayer fplayer = (FPlayer)FPlayers.i.get((OfflinePlayer)player);
        if (fplayer == null || !fplayer.hasFaction() || !fplayer.getFaction().isNormal()) {
            return 0;
        }
        return FactionUpgradeAPI.getPerkLevelIfAllowed(fplayer.getFaction().getId(), upgrade);
    }

    public static int getPerkLevel(Faction faction, FactionUpgrade upgrade) {
        return FactionUpgradeAPI.getPerkLevel(faction.getId(), upgrade);
    }

    public static int getPerkLevel(String factionID, FactionUpgrade upgrade) {
        FactionUpgradeInfo info = P.getP().getUpgradeManager().getUpgradeInfo().get(factionID);
        if (info == null) {
            return 0;
        }
        return info.getUpgradeTier(upgrade);
    }

    public static int getPerkLevel(Player player, FactionUpgrade upgrade) {
        FPlayer fplayer = (FPlayer)FPlayers.i.get((OfflinePlayer)player);
        if (fplayer == null || !fplayer.hasFaction() || !fplayer.getFaction().isNormal()) {
            return 0;
        }
        return FactionUpgradeAPI.getPerkLevel(fplayer.getFaction().getId(), upgrade);
    }

    public static void processPerkPurchase(Faction f, FactionUpgrade upgrade, int newTier) {
        if (upgrade == FactionUpgrade.FACTION_POWER_BOOST) {
            f.setPowerBoost(f.getPowerBoost() + 20.0);
        }
    }
}

