/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.FPlayers
 */
package org.arkhamnetwork.Arkkit.patches;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;

import java.util.UUID;

final class TopPlayer {
    private final UUID uuid;
    private final int minutes;
    private FPlayer fplayer;

    public TopPlayer(UUID uuid, int minutes, FPlayer fplayer) {
        this.uuid = uuid;
        this.minutes = minutes;
        this.fplayer = fplayer;
    }

    public FPlayer getFPlayer() {
        if (this.fplayer != null) {
            return this.fplayer;
        }
        this.fplayer = FPlayers.i.get(this.uuid.toString());
        return this.fplayer;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public int getMinutes() {
        return this.minutes;
    }
}

