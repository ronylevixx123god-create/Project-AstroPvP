/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.massivecraft.factions.Board
 *  com.massivecraft.factions.FLocation
 *  com.massivecraft.factions.Faction
 *  org.bukkit.block.Block
 *  org.bukkit.craftbukkit.v1_7_R4.entity.CraftTNTPrimed
 *  org.bukkit.entity.TNTPrimed
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityExplodeEvent
 */
package com.cosmicpvp.factionpoints.listeners;

import com.cosmicpvp.factionpoints.FactionsCoreChunkAPI;
import com.cosmicpvp.factionpoints.struct.CoreChunkData;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftTNTPrimed;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class CoreChunkListener
implements Listener {
    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onTntExplode(EntityExplodeEvent event) {
        if (!(event.getEntity() instanceof TNTPrimed) || event.getEntity().hasMetadata("custom_tnt") || event.getEntity().hasMetadata("fromCreeper")) {
            return;
        }
        FLocation location = new FLocation(event.getLocation());
        Faction inside = Board.getFactionAt((FLocation)location);
        if (inside == null || !inside.isNormal()) {
            return;
        }
        CoreChunkData coreChunk = FactionsCoreChunkAPI.getChunkData(inside);
        if (coreChunk == null || coreChunk.getMasterChunk() == null) {
            return;
        }
        TNTPrimed primed = (TNTPrimed)event.getEntity();
        Block source = ((CraftTNTPrimed)primed).getHandle().sourceLoc.getBlock();
        Faction cameFrom = Board.getFactionAt((FLocation)new FLocation(source));
        if (cameFrom != null && cameFrom.equals((Object)inside)) {
            return;
        }
        if (coreChunk.getMasterChunk().equals((Object)location) && coreChunk.canBeRaided() && cameFrom != null && cameFrom.isNormal()) {
            coreChunk.onRaidedByFaction(inside, cameFrom, primed.getLocation(), source);
        }
    }
}

