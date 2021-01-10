package com.compilex.CosmicDungeons.dungeons.events.destroyedoutpost;

import com.compilex.CosmicDungeons.dungeons.events.RegionEvent;
import com.compilex.CosmicDungeons.dungeons.libs.Dungeon;
import com.compilex.CosmicDungeons.utils.WorldUtils;
import com.cosmicpvp.cosmicutils.utils.CC;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0016\u00a8\u0006\t"}, d2={"Lcom/compilex/CosmicDungeons/dungeons/events/destroyedoutpost/AirLockBRegionEvent;", "Lcom/compilex/CosmicDungeons/dungeons/events/RegionEvent;", "d", "Lcom/compilex/CosmicDungeons/dungeons/libs/Dungeon;", "regionName", "", "(Lcom/compilex/CosmicDungeons/dungeons/libs/Dungeon;Ljava/lang/String;)V", "run", "", "AstroDungeons"})
public final class AirLockBRegionEvent
extends RegionEvent {
    @Override
    public void run() {
        WorldUtils.Companion.scanBlocks(new Location(this.dungeon.world, 216.0, 109.0, -15.0), 8);
        this.dungeon.broadcastMessage(CC.White + "Yijki is responsible for what has become of his hellish world... you must locate the portal the miners uncovered that leads to Yijki's dimension... perhaps you should look for additional clues in the buildings around the dig site.");
        this.dungeon.updateTimeRestrictionTask(System.currentTimeMillis() + 900000L, "Locate Yijki's Portal");
    }

    public AirLockBRegionEvent(@NotNull Dungeon d, @NotNull String regionName) {
        super(d, regionName);
        Intrinsics.checkParameterIsNotNull(d, "d");
        Intrinsics.checkParameterIsNotNull(regionName, "regionName");
    }
}

