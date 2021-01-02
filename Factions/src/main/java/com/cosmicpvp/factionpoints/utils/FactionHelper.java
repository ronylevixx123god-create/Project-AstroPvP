/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.massivecraft.factions.Board
 *  com.massivecraft.factions.FLocation
 *  com.massivecraft.factions.Faction
 *  org.bukkit.Bukkit
 */
package com.cosmicpvp.factionpoints.utils;

import com.cosmicpvp.factionpoints.managers.CoreChunkManager;
import com.cosmicpvp.factionpoints.struct.ExpiringChunkData;
import com.google.common.collect.Lists;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import com.massivecraft.factions.P;
import org.bukkit.Bukkit;

public class FactionHelper {
    private static final long time = TimeUnit.HOURS.toMillis(24L);

    public static boolean markAllNonAttachedToCore(Faction faction, FLocation chunkCore) {
        List allClaims = Board.getAllClaimedLand(faction);
        if (allClaims == null) {
            return false;
        }
        if (!allClaims.contains(chunkCore)) {
            Bukkit.getLogger().info("Unable to find core chunk cached at " + chunkCore + " at all for " + faction.getTag() + ", inside claimList: " + allClaims.size());
            return false;
        }
        CompletableFuture.runAsync(() -> {
            ExpiringChunkData data2;
            long start = System.currentTimeMillis();
            ArrayList<FLocation> attachedToCore = Lists.newArrayList();
            FactionHelper.checkAttachedNeighbors(attachedToCore, allClaims, chunkCore);
            int count = allClaims.size();
            allClaims.remove(chunkCore);
            allClaims.removeAll(attachedToCore);
            CoreChunkManager manager = P.getP().getChunkManager();
            for (Object notAttached : allClaims) {
                data2 = manager.getChunksToExpire().get(notAttached);
                if (data2 != null && data2.getFId() != null && data2.getFId().equals(faction.getId()) && data2.getExpirationTime() > System.currentTimeMillis()) {
                    if (!data2.isConnectedToWarzone() || data2.getExpirationTime() == 0L) continue;
                    data2.setExpirationTime(0L);
                    continue;
                }
                if (data2 != null && data2.isConnectedToWarzone()) {
                    if (data2.getExpirationTime() == 0L) continue;
                    data2.setExpirationTime(0L);
                    continue;
                }
                if (data2 == null) {
                    data2 = new ExpiringChunkData(System.currentTimeMillis(), System.currentTimeMillis() + time, faction.getId());
                    manager.getChunksToExpire().put((FLocation) notAttached, data2);
                } else if (!data2.isConnectedToWarzone()) {
                    data2.setExpirationTime(data2.getTimeClaimed() + time);
                }
                P.debugCosmicFactionPoints("Marking chunk: " + notAttached.toString() + " as expiring in 24h for " + faction.getTag() + " claimed: " + data2.getTimeClaimed() + " expiring: " + data2.getExpirationTime());
            }
            for (FLocation core : attachedToCore) {
                data2 = manager.getChunksToExpire().get(core);
                if (data2 == null || data2.getFId() == null || !data2.getFId().equals(faction.getId()) || data2.getExpirationTime() <= System.currentTimeMillis() && !data2.isConnectedToWarzone()) continue;
                data2.setExpirationTime(0L);
            }
            Bukkit.getLogger().info("Scanned " + count + " claims with " + attachedToCore.size() + " chunks being attached to " + chunkCore.toString() + " in " + (System.currentTimeMillis() - start) + "ms");
        });
        return true;
    }

    private static void checkAttachedNeighbors(List<FLocation> attachedList, List<FLocation> claimList, FLocation fLocation) {
        for (int x = -1; x <= 1; ++x) {
            for (int z = -1; z <= 1; ++z) {
                FLocation neighbor;
                boolean isAlreadyMarked;
                if (z == 0 && x == 0 || (isAlreadyMarked = attachedList.contains(neighbor = fLocation.getRelative(x, z))) || !claimList.contains(neighbor)) continue;
                attachedList.add(neighbor);
                FactionHelper.checkAttachedNeighbors(attachedList, claimList, neighbor);
            }
        }
    }
}

