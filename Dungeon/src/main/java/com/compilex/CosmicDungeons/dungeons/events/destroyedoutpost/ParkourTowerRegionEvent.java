/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.gmail.filoghost.holograms.api.HolographicDisplaysAPI
 *  kotlin.jvm.internal.Intrinsics
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.plugin.Plugin
 */
package com.compilex.CosmicDungeons.dungeons.events.destroyedoutpost;

import com.compilex.CosmicDungeons.CosmicDungeonsPlugin;
import com.compilex.CosmicDungeons.dungeons.events.RegionEvent;
import com.compilex.CosmicDungeons.dungeons.libs.Dungeon;
import com.compilex.CosmicDungeons.utils.WorldUtils;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import kotlin.jvm.internal.Intrinsics;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public final class ParkourTowerRegionEvent
extends RegionEvent {
    public ParkourTowerRegionEvent(Dungeon d, String regionName) {
        super(d, regionName);
        if (d == null) {
            ParkourTowerRegionEvent.$$$reportNull$$$0(0);
        }
        if (regionName == null) {
            ParkourTowerRegionEvent.$$$reportNull$$$0(1);
        }
    }

    @Override
    public void run() {
        Intrinsics.checkExpressionValueIsNotNull((Object)this.dungeon.world, (String)"dungeon.world");
        Intrinsics.checkExpressionValueIsNotNull((Object)this.dungeon.world, (String)"dungeon.world");
        Bukkit.getLogger().info("World: " + this.dungeon.world.getName() + " players: " + this.dungeon.world.getPlayers().size());
        WorldUtils.Companion.scanBlocks(new Location(this.dungeon.world, 286.0, 206.0, -11.0), 3);
        this.dungeon.setObjectiveHologram(HologramsAPI.createHologram(CosmicDungeonsPlugin.get(), (Location)new Location(this.dungeon.world, 287.0, 208.0, -12.0)));
        this.dungeon.getObjectiveHologram().appendTextLine(CC.AquaB + "Parkour Start");
    }

    private static void $$$reportNull$$$0(int n) {
        String format = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] args = new Object[3];
        switch (n) {
            default: {
                args[0] = "d";
                break;
            }
            case 1: {
                args[0] = "regionName";
            }
        }
        args[1] = "com/compilex/CosmicDungeons/dungeons/events/destroyedoutpost/ParkourTowerRegionEvent";
        args[2] = "<init>";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", args));
    }
}

