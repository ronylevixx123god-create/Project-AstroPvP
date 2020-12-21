/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.worldguard.bukkit.commands;

class RegionListEntry
implements Comparable<RegionListEntry> {
    private final String id;
    private final int index;
    boolean isOwner;
    boolean isMember;

    public RegionListEntry(String id, int index) {
        this.id = id;
        this.index = index;
    }

    @Override
    public int compareTo(RegionListEntry o) {
        if (this.isOwner != o.isOwner) {
            return this.isOwner ? 1 : -1;
        }
        if (this.isMember != o.isMember) {
            return this.isMember ? 1 : -1;
        }
        return this.id.compareTo(o.id);
    }

    public String toString() {
        if (this.isOwner) {
            return this.index + 1 + ". +" + this.id;
        }
        if (this.isMember) {
            return this.index + 1 + ". -" + this.id;
        }
        return this.index + 1 + ". " + this.id;
    }
}

