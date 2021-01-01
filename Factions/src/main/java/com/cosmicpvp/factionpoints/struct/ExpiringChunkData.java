/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.massivecraft.factions.Faction
 *  org.bukkit.craftbukkit.libs.com.google.gson.annotations.SerializedName
 */
package com.cosmicpvp.factionpoints.struct;

import com.google.gson.annotations.SerializedName;
import com.massivecraft.factions.Faction;
import org.bukkit.craftbukkit.libs.com.google.gson.annotations.SerializedName;

public class ExpiringChunkData {
    public long timeClaimed;
    public long expirationTime;
    public String fId;
    @SerializedName(value="cToWar")
    public boolean connectedToWarzone;

    public ExpiringChunkData(long timeClaimed, long expiration, String fId) {
        this.timeClaimed = timeClaimed;
        this.expirationTime = expiration;
        this.fId = fId;
    }

    public void onClaimed(Faction whoClaimed) {
        this.timeClaimed = System.currentTimeMillis();
        this.expirationTime = 0L;
        this.fId = whoClaimed.getId();
    }

    public long getTimeClaimed() {
        return this.timeClaimed;
    }

    public long getExpirationTime() {
        return this.expirationTime;
    }

    public String getFId() {
        return this.fId;
    }

    public boolean isConnectedToWarzone() {
        return this.connectedToWarzone;
    }

    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public void setConnectedToWarzone(boolean connectedToWarzone) {
        this.connectedToWarzone = connectedToWarzone;
    }
}

