/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.sun.istack.internal.NotNull
 *  kotlin.Metadata
 */
package com.compilex.CosmicDungeons.dungeons.events.destroyedoutpost;

import com.compilex.CosmicDungeons.dungeons.events.RegionEvent;
import com.compilex.CosmicDungeons.dungeons.libs.Dungeon;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.sun.istack.internal.NotNull;
import kotlin.Metadata;

@Metadata(mv={1, 1, 10}, bv={1, 0, 2}, k=1, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0016\u00a8\u0006\t"}, d2={"Lcom/compilex/CosmicDungeons/dungeons/events/destroyedoutpost/PortalRegionEvent;", "Lcom/compilex/CosmicDungeons/dungeons/events/RegionEvent;", "dungeon", "Lcom/compilex/CosmicDungeons/dungeons/libs/Dungeon;", "regionName", "", "(Lcom/compilex/CosmicDungeons/dungeons/libs/Dungeon;Ljava/lang/String;)V", "run", "", "CosmicDungeons"})
public final class PortalRegionEvent
extends RegionEvent {
    public PortalRegionEvent(@NotNull Dungeon dungeon, @NotNull String regionName) {
        super(dungeon, regionName);
    }

    @Override
    public void run() {
        this.dungeon.broadcastMessage(CC.White + "You have entered into an interdimensional rift; you are now in a place that only exists between multiple dimensions. You must navigate through these series of portals to arrive at the Mother of Yijki's home dimension where she can be killed once and for all.");
        this.dungeon.broadcastMessage(CC.Gray + "Be cautious, this reality is extremely unstable. Choosing the wrong portal could result in catastrophic events.");
        this.dungeon.updateTimeRestrictionTask(System.currentTimeMillis() + 300000L, "Travel to Yijki's Dimension");
    }
}

