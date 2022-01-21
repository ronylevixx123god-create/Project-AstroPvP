/*
 * Decompiled with CFR 0.145.
 */
package com.massivecraft.factions.cmd;

import com.massivecraft.factions.perms.Relation;

public class CmdRelationTruce
        extends FRelationCommand {
    public CmdRelationTruce() {
        this.aliases.add("truce");
        this.aliases.add("TRUCE");
        this.targetRelation = Relation.TRUCE;
    }
}

