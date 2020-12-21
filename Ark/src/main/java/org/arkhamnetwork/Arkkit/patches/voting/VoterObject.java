/*
 * Decompiled with CFR 0.150.
 */
package org.arkhamnetwork.Arkkit.patches.voting;

public class VoterObject {
    String p;
    String u;
    int v;

    public VoterObject(String player_name, String player_uuid, int vote_count) {
        this.p = player_name;
        this.u = player_uuid;
        this.v = vote_count;
    }

    public String getPlayerName() {
        return this.p;
    }

    public String getUUID() {
        return this.u;
    }

    public int getVoteCount() {
        return this.v;
    }
}

