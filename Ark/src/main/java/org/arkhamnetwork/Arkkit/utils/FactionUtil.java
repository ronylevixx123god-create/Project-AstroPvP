/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.compat.ParamBuilder
 *  com.cosmicpvp.cosmicutils.utils.FactionUtils
 *  com.cosmicpvp.cosmicutils.utils.ReflectionUtils
 *  com.massivecraft.factions.Faction
 *  com.massivecraft.factions.iface.RelationParticipator
 *  com.massivecraft.factions.struct.Relation
 *  org.bukkit.Location
 */
package org.arkhamnetwork.Arkkit.utils;

import com.cosmicpvp.cosmicutils.compat.ParamBuilder;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.cosmicpvp.cosmicutils.utils.ReflectionUtils;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Relation;
import org.bukkit.Location;

public class FactionUtil {
    public static Relation getRelation(RelationParticipator part, RelationParticipator part2) {
        return (Relation)ReflectionUtils.callReflectionMethod((Object)part, RelationParticipator.class, (String)"getRelationTo", (ParamBuilder)new ParamBuilder(RelationParticipator.class, (Object)part2));
    }

    public static boolean isNonPermFaction(Location destination) {
        Faction at = FactionUtils.getFactionAt((Location)destination);
        return at != null && FactionUtil.isNonPermFaction(at);
    }

    public static boolean isNonPermFaction(Faction at) {
        return at != null && (Boolean)FactionUtils.callFactionMethod((Faction)at, (String)"isSafeZone", null) == false && (Boolean)FactionUtils.callFactionMethod((Faction)at, (String)"isNone", null) == false && (Boolean)FactionUtils.callFactionMethod((Faction)at, (String)"isWarZone", null) == false;
    }

    public static String getTag(Faction faction) {
        return (String)FactionUtils.callFactionMethod((Faction)faction, (String)"getTag", null);
    }
}

