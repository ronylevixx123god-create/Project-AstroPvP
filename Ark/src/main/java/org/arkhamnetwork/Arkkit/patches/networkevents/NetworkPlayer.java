/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang.builder.HashCodeBuilder
 */
package org.arkhamnetwork.Arkkit.patches.networkevents;

import java.util.UUID;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class NetworkPlayer {
    private UUID uuid;
    private String name;
    private String serverCurrentlyOn;
    private String ip;

    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public int hashCode() {
        return new HashCodeBuilder(17, 37).append((Object)this.uuid).append((Object)this.name).append((Object)this.ip).toHashCode();
    }

    public NetworkPlayer(UUID uuid, String name, String serverCurrentlyOn, String ip) {
        this.uuid = uuid;
        this.name = name;
        this.serverCurrentlyOn = serverCurrentlyOn;
        this.ip = ip;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public String getName() {
        return this.name;
    }

    public String getServerCurrentlyOn() {
        return this.serverCurrentlyOn;
    }

    public String getIp() {
        return this.ip;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setServerCurrentlyOn(String serverCurrentlyOn) {
        this.serverCurrentlyOn = serverCurrentlyOn;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}

