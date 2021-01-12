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

public class FactionPointsUtils {
    private static boolean factionPointsEnabled = (new File("plugins/CosmicFactionPoints.jar")).exists();

    public static int getCostPerPoint() {
        if (!factionPointsEnabled)
            return 0;
        return PointPurchaseMenu.COST_PER_POINT;
    }

    public static boolean isPointsEnabled() {
        return factionPointsEnabled;
    }

    public static long getStartOfTheMapEpoch() {
        if (!isPointsEnabled())
            return -1L;
        return CosmicFactionPoints.get().getStartOfMap();
    }

    public static long getTimeSinceStartOfMap() {
        if (!isPointsEnabled())
            return -1L;
        return System.currentTimeMillis() - getStartOfTheMapEpoch();
    }

    public static long getTimeSinceStartOfMapWithOffset(long startOffset) {
        if (!isPointsEnabled())
            return -1L;
        long time = System.currentTimeMillis() - getStartOfTheMapEpoch() + startOffset;
        if (time <= 0L)
            return 0L;
        return time;
    }

    public static long getTimeSinceStartOfMapInSeconds() {
        if (!isPointsEnabled())
            return -1L;
        return TimeUnit.MILLISECONDS.toSeconds(getTimeSinceStartOfMap());
    }

    public static double getTimeSinceStartOfMapInDays() {
        if (!isPointsEnabled())
            return -1.0D;
        return TimeUnit.MILLISECONDS.toDays(getTimeSinceStartOfMap());
    }

    public static double getTimeSinceStartOfMapInWeeks() {
        if (!isPointsEnabled())
            return -1.0D;
        return getTimeSinceStartOfMapInDays() / 7.0D;
    }

    public static boolean isPointItem(ItemStack item) {
        if (!isPointsEnabled())
            return false;
        return FactionsPointsAPI.isPointItem(new NBTWrapper(item));
    }

    public static boolean isPointItem(NBTWrapper wrapper) {
        if (!isPointsEnabled())
            return false;
        return FactionsPointsAPI.isPointItem(wrapper);
    }

    public static Long getFactionPoints(Faction faction) {
        if (!isPointsEnabled())
            return Long.valueOf(0L);
        return FactionsPointsAPI.getPoints(faction);
    }

    public static Long giveFactionPoints(Faction faction, Player player, String eventName, Object data) {
        if (!isPointsEnabled())
            return Long.valueOf(0L);
        return FactionsPointsAPI.giveFactionPoints(faction, player, eventName, data);
    }

    public static Long giveFactionPoints(Faction faction, Player player, String eventName, int points, Object data) {
        if (!isPointsEnabled())
            return Long.valueOf(0L);
        return FactionsPointsAPI.giveFactionPoints(faction, player, eventName, points, data);
    }

    public static ItemStack getFactionPointsItem(Faction faction, Player player, String eventName, Object data) {
        if (!isPointsEnabled())
            return null;
        return FactionsPointsAPI.getFactionPointItem(faction, player, eventName, data);
    }

    public static ItemStack getFactionPointsItem(Faction faction, Player player, String eventName, int points, Object data) {
        if (!isPointsEnabled())
            return null;
        return FactionsPointsAPI.getFactionPointItem(faction, player, eventName, points, data);
    }

    public static ItemStack getPointItem(double amount, boolean applyIID) {
        if (!isPointsEnabled())
            return null;
        return FactionsPointsAPI.createPointItem(amount, applyIID);
    }
}
