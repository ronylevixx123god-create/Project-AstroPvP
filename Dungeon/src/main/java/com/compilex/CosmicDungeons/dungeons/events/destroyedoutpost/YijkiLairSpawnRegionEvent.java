/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  kotlin.Metadata
 *  org.jetbrains.annotations.NotNull
 */
package com.compilex.CosmicDungeons.dungeons.events.destroyedoutpost;

import com.compilex.CosmicDungeons.dungeons.events.RegionEvent;
import com.compilex.CosmicDungeons.dungeons.libs.Dungeon;
import com.cosmicpvp.cosmicutils.utils.CC;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 1, 10}, bv={1, 0, 2}, k=1, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0016\u0006\t"}, d2={"Lcom/compilex/CosmicDungeons/dungeons/events/destroyedoutpost/YijkiLairSpawnRegionEvent;", "Lcom/compilex/CosmicDungeons/dungeons/events/RegionEvent;", "d", "Lcom/compilex/CosmicDungeons/dungeons/libs/Dungeon;", "regionName", "", "(Lcom/compilex/CosmicDungeons/dungeons/libs/Dungeon;Ljava/lang/String;)V", "run", "", "CosmicDungeons"})
public final class YijkiLairSpawnRegionEvent
extends RegionEvent {
    public YijkiLairSpawnRegionEvent(@NotNull Dungeon d, @NotNull String regionName) {
        super(d, regionName);
    }

    @Override
    public void run() {
        this.dungeon.broadcastMessage(CC.White + "The time has come. You have arrived at Yijki's home dimension. If the Goddess is slain here, she will die once and for all. ");
        this.dungeon.broadcastMessage("When you are ready, step off the spawn platform and Yijki will sense your presence.");
        this.dungeon.updateTimeRestrictionTask(System.currentTimeMillis() + 1200000L / (long)(this.dungeon.isHalfTime() ? 2 : 1), "Kill Yijki");
    }
}

