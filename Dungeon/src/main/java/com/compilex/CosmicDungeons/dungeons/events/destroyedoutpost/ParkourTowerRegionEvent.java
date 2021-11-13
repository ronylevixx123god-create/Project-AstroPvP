/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.gmail.filoghost.holograms.api.HolographicDisplaysAPI
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.plugin.Plugin
 *  org.jetbrains.annotations.NotNull
 */
package com.compilex.CosmicDungeons.dungeons.events.destroyedoutpost;

import com.compilex.CosmicDungeons.CosmicDungeonsPlugin;
import com.compilex.CosmicDungeons.dungeons.events.RegionEvent;
import com.compilex.CosmicDungeons.dungeons.events.destroyedoutpost.ParkourTowerRegionEvent;
import com.compilex.CosmicDungeons.dungeons.libs.Dungeon;
import com.compilex.CosmicDungeons.utils.WorldUtils;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.gmail.filoghost.holograms.api.HolographicDisplaysAPI;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 1, 10}, bv={1, 0, 2}, k=1, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0016\u0006\t"}, d2={"Lcom/compilex/CosmicDungeons/dungeons/events/destroyedoutpost/ParkourTowerRegionEvent;", "Lcom/compilex/CosmicDungeons/dungeons/events/RegionEvent;", "d", "Lcom/compilex/CosmicDungeons/dungeons/libs/Dungeon;", "regionName", "", "(Lcom/compilex/CosmicDungeons/dungeons/libs/Dungeon;Ljava/lang/String;)V", "run", "", "CosmicDungeons"})
public final class ParkourTowerRegionEvent
extends RegionEvent {
    public ParkourTowerRegionEvent(@NotNull Dungeon d, @NotNull String regionName) {
        super(d, regionName);
    }

    @Override
    public void run() {
        Intrinsics.checkExpressionValueIsNotNull((Object)this.dungeon.world, (String)"dungeon.world");
        Intrinsics.checkExpressionValueIsNotNull((Object)this.dungeon.world, (String)"dungeon.world");
        Bukkit.getLogger().info("World: " + this.dungeon.world.getName() + " players: " + this.dungeon.world.getPlayers().size());
        WorldUtils.Companion.scanBlocks(new Location(this.dungeon.world, 286.0, 206.0, -11.0), 3, ParkourTowerRegionEvent.run.1.INSTANCE);
        this.dungeon.setObjectiveHologram(HolographicDisplaysAPI.createHologram((Plugin)CosmicDungeonsPlugin.get(), (Location)new Location(this.dungeon.world, 287.0, 208.0, -12.0), (String[])new String[]{CC.AquaB + "Parkour Start"}));
    }
}

