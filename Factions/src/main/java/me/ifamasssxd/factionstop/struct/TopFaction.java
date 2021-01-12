/*
 * Decompiled with CFR 0.150.
 */
package me.ifamasssxd.factionstop.struct;

public class TopFaction {
    int rank;
    StoredFaction storedFaction;

    public TopFaction(StoredFaction storedFaction, int rank) {
        this.rank = rank;
        this.storedFaction = storedFaction;
    }

    public int getRank() {
        return this.rank;
    }

    public StoredFaction getStoredFaction() {
        return this.storedFaction;
    }
}

