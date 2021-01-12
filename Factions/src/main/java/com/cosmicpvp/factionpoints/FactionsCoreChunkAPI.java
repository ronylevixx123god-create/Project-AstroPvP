/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.TimeUtils
 *  com.massivecraft.factions.FLocation
 *  com.massivecraft.factions.Faction
 */
package com.cosmicpvp.factionpoints;

import com.cosmicpvp.cosmicutils.utils.TimeUtils;
import com.cosmicpvp.factionpoints.struct.CoreChunkData;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;

public class FactionsCoreChunkAPI {
    public static FLocation getCoreChunk(Faction faction) {
        return CosmicFactionPoints.get().getChunkManager().getFactionCoreChunk(faction);
    }

    public static CoreChunkData removeCoreData(Faction faction) {
        FactionsCoreChunkAPI.removeCoreChunk(faction);
        return CosmicFactionPoints.get().getChunkManager().onFactionDisband(faction);
    }

    public static boolean isCoreChunk(FLocation location) {
        boolean isCore = CosmicFactionPoints.get().getChunkManager().getFastFactionCoreMap().containsKey((Object)location);
        return isCore;
    }

    public static String getWhenRaidableString(Faction faction) {
        CoreChunkData data2 = FactionsCoreChunkAPI.getChunkData(faction);
        if (data2 == null || data2.getLastRaided() == null || data2.getLastRaided() == 0L) {
            return null;
        }
        long timeSinceRaided = System.currentTimeMillis() - data2.getLastRaided();
        long timetillraid = CoreChunkData.RAID_COOLDOWN - timeSinceRaided;
        if (timetillraid <= 0L) {
            return null;
        }
        return TimeUtils.formatSeconds((long)(timetillraid / 1000L));
    }

    public static FLocation removeCoreChunk(Faction faction) {
        FLocation val = CosmicFactionPoints.get().getChunkManager().removeFactionCoreChunk(faction);
        if (val != null) {
            CosmicFactionPoints.log("Removing faction core chunk for " + faction.getTag() + ", val=" + val.toString() + "!");
        }
        return val;
    }

    public static FLocation removeCoreChunkOnDisband(Faction faction) {
        CosmicFactionPoints.get().getChunkManager().onFactionDisband(faction);
        FLocation val = CosmicFactionPoints.get().getChunkManager().removeAllFactionCoreChunk(faction);
        if (val != null) {
            CosmicFactionPoints.log("Removing faction core chunk for " + faction.getTag() + ", val=" + val.toString() + "!");
        }
        return val;
    }

    public static void setCoreChunk(Faction faction, FLocation core) {
        CosmicFactionPoints.get().getChunkManager().setFactionCoreChunk(faction, core);
    }

    public static void storeLandClaimForFaction(Faction faction, FLocation core) {
        CosmicFactionPoints.get().getChunkManager().onFactionClaimedLand(faction, core);
    }

    public static CoreChunkData getChunkData(Faction faction) {
        return CosmicFactionPoints.get().getChunkManager().getCoreData(faction);
    }
}

