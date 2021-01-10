/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 */
package com.compilex.CosmicDungeons.dungeons.events.destroyedoutpost;

import com.compilex.CosmicDungeons.dungeons.events.RegionEvent;
import com.compilex.CosmicDungeons.dungeons.libs.Dungeon;
import com.cosmicpvp.cosmicutils.utils.CC;

public final class YijkiLairSpawnRegionEvent
extends RegionEvent {
    public YijkiLairSpawnRegionEvent(Dungeon d, String regionName) {
        super(d, regionName);
        if (d == null) {
            YijkiLairSpawnRegionEvent.$$$reportNull$$$0(0);
        }
        if (regionName == null) {
            YijkiLairSpawnRegionEvent.$$$reportNull$$$0(1);
        }
    }

    @Override
    public void run() {
        this.dungeon.broadcastMessage(CC.White + "The time has come. You have arrived at Yijki's home dimension. If the Goddess is slain here, she will die once and for all. ");
        this.dungeon.broadcastMessage("When you are ready, step off the spawn platform and Yijki will sense your presence.");
        this.dungeon.updateTimeRestrictionTask(System.currentTimeMillis() + 1200000L, "Kill Yijki");
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
        args[1] = "com/compilex/CosmicDungeons/dungeons/events/destroyedoutpost/YijkiLairSpawnRegionEvent";
        args[2] = "<init>";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", args));
    }
}

