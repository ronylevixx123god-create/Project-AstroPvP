/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.TimeUtils
 *  com.massivecraft.factions.FLocation
 *  org.apache.commons.lang.builder.HashCodeBuilder
 *  org.jetbrains.annotations.NotNull
 */
package com.cosmicpvp.factionpoints.struct;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.TimeUtils;
import com.massivecraft.factions.FLocation;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.jetbrains.annotations.NotNull;

public class ChunkNotification
implements Comparable {
    private String chunkLocation;
    private int expirationTimeInSeconds;

    public ChunkNotification(FLocation chunkLocation, int expires) {
        this.chunkLocation = FLocation.chunkToBlock((int)((int)chunkLocation.getX())) + "x " + FLocation.chunkToBlock((int)((int)chunkLocation.getZ())) + "z";
        this.expirationTimeInSeconds = expires;
    }

    public int compareTo(@NotNull Object o) {
        if (o == null) {
            ChunkNotification.$$$reportNull$$$0(0);
        }
        if (o instanceof ChunkNotification) {
            ChunkNotification other = (ChunkNotification)o;
            if (other.getExpirationTimeInSeconds() > this.getExpirationTimeInSeconds()) {
                return 1;
            }
            if (other.getExpirationTimeInSeconds() == this.expirationTimeInSeconds) {
                return 0;
            }
            return -1;
        }
        return 0;
    }

    public String toString() {
        return this.toString(true);
    }

    public String toString(boolean longMsg) {
        return CC.RedB + "(!) " + CC.Red + (longMsg ? "Your faction's claim" : "Claim") + " at " + CC.RedB + this.chunkLocation + CC.Red + " will expire in " + CC.RedB + TimeUtils.formatSeconds((long)this.expirationTimeInSeconds) + CC.Red + "!";
    }

    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public int hashCode() {
        return new HashCodeBuilder(17, 37).append((Object)this.chunkLocation).append(this.expirationTimeInSeconds).toHashCode();
    }

    public String getChunkLocation() {
        return this.chunkLocation;
    }

    public int getExpirationTimeInSeconds() {
        return this.expirationTimeInSeconds;
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "o", "com/cosmicpvp/factionpoints/struct/ChunkNotification", "compareTo"));
    }
}

