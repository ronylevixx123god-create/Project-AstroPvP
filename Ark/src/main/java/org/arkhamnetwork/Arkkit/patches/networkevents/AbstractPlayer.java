/*
 * Decompiled with CFR 0.150.
 */
package org.arkhamnetwork.Arkkit.patches.networkevents;

import java.util.UUID;

public class AbstractPlayer {
    private String playerName;
    private UUID playerUUID;
    private String playerIP;

    public AbstractPlayer(String playerName, UUID playerUUID, String playerIP) {
        this.playerName = playerName;
        this.playerUUID = playerUUID;
        this.playerIP = playerIP;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public UUID getPlayerUUID() {
        return this.playerUUID;
    }

    public String getPlayerIP() {
        return this.playerIP;
    }
}

