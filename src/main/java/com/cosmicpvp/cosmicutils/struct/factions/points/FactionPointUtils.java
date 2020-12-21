/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.factionpoints.CosmicFactionPoints
 *  com.cosmicpvp.factionpoints.FactionsPointsAPI
 *  com.cosmicpvp.factionpoints.menus.PointPurchaseMenu
 *  com.massivecraft.factions.Faction
 */
package com.cosmicpvp.cosmicutils.struct.factions.points;

import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.cosmicpvp.factionpoints.CosmicFactionPoints;
import com.cosmicpvp.factionpoints.FactionsPointsAPI;
import com.cosmicpvp.factionpoints.menus.PointPurchaseMenu;
import com.massivecraft.factions.Faction;
import java.io.File;
import java.util.concurrent.TimeUnit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FactionPointUtils {
    private static boolean factionPointsEnabled = new File("plugins/CosmicFactionPoints.jar").exists();

    public static int getCostPerPoint() {
        if (!factionPointsEnabled) {
            return 0;
        }
        return PointPurchaseMenu.COST_PER_POINT;
    }

    public static boolean isPointsEnabled() {
        return factionPointsEnabled;
    }

    public static long getStartOfTheMapEpoch() {
        if (!FactionPointUtils.isPointsEnabled()) {
            return -1L;
        }
        return CosmicFactionPoints.get().getStartOfMap();
    }

    public static long getTimeSinceStartOfMap() {
        if (!FactionPointUtils.isPointsEnabled()) {
            return -1L;
        }
        return System.currentTimeMillis() - FactionPointUtils.getStartOfTheMapEpoch();
    }

    public static long getTimeSinceStartOfMapWithOffset(long startOffset) {
        if (!FactionPointUtils.isPointsEnabled()) {
            return -1L;
        }
        long time = System.currentTimeMillis() - (FactionPointUtils.getStartOfTheMapEpoch() + startOffset);
        if (time <= 0L) {
            return 0L;
        }
        return time;
    }

    public static long getTimeSinceStartOfMapInSeconds() {
        if (!FactionPointUtils.isPointsEnabled()) {
            return -1L;
        }
        return TimeUnit.MILLISECONDS.toSeconds(FactionPointUtils.getTimeSinceStartOfMap());
    }

    public static double getTimeSinceStartOfMapInDays() {
        if (!FactionPointUtils.isPointsEnabled()) {
            return -1.0;
        }
        return TimeUnit.MILLISECONDS.toDays(FactionPointUtils.getTimeSinceStartOfMap());
    }

    public static double getTimeSinceStartOfMapInWeeks() {
        if (!FactionPointUtils.isPointsEnabled()) {
            return -1.0;
        }
        return FactionPointUtils.getTimeSinceStartOfMapInDays() / 7.0;
    }

    public static boolean isPointItem(ItemStack item) {
        if (!FactionPointUtils.isPointsEnabled()) {
            return false;
        }
        return FactionsPointsAPI.isPointItem((NBTWrapper)new NBTWrapper(item));
    }

    public static boolean isPointItem(NBTWrapper wrapper) {
        if (!FactionPointUtils.isPointsEnabled()) {
            return false;
        }
        return FactionsPointsAPI.isPointItem((NBTWrapper)wrapper);
    }

    public static Long getFactionPoints(Faction faction) {
        if (!FactionPointUtils.isPointsEnabled()) {
            return 0L;
        }
        return FactionsPointsAPI.getPoints((Faction)faction);
    }

    public static Long giveFactionPoints(Faction faction, Player player, String eventName, Object data) {
        if (!FactionPointUtils.isPointsEnabled()) {
            return 0L;
        }
        return FactionsPointsAPI.giveFactionPoints((Faction)faction, (Player)player, (String)eventName, (Object)data);
    }

    public static Long giveFactionPoints(Faction faction, Player player, String eventName, int points, Object data) {
        if (!FactionPointUtils.isPointsEnabled()) {
            return 0L;
        }
        return FactionsPointsAPI.giveFactionPoints((Faction)faction, (Player)player, (String)eventName, (int)points, (Object)data);
    }

    public static ItemStack getFactionPointsItem(Faction faction, Player player, String eventName, Object data) {
        if (!FactionPointUtils.isPointsEnabled()) {
            return null;
        }
        return FactionsPointsAPI.getFactionPointItem((Faction)faction, (Player)player, (String)eventName, (Object)data);
    }

    public static ItemStack getFactionPointsItem(Faction faction, Player player, String eventName, int points, Object data) {
        if (!FactionPointUtils.isPointsEnabled()) {
            return null;
        }
        return FactionsPointsAPI.getFactionPointItem((Faction)faction, (Player)player, (String)eventName, (int)points, (Object)data);
    }

    public static ItemStack getPointItem(double amount, boolean applyIID) {
        if (!FactionPointUtils.isPointsEnabled()) {
            return null;
        }
        return FactionsPointsAPI.createPointItem((double)amount, (boolean)applyIID);
    }
}

