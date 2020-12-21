/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.worldguard.protection.flags;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.EnumFlag;
import com.sk89q.worldguard.protection.flags.RegionGroup;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class RegionGroupFlag
extends EnumFlag<RegionGroup> {
    private RegionGroup def;

    public RegionGroupFlag(String name, RegionGroup def) {
        super(name, RegionGroup.class, null);
        this.def = def;
    }

    public RegionGroup getDefault() {
        return this.def;
    }

    @Override
    public RegionGroup detectValue(String input) {
        if ((input = input.trim()).equalsIgnoreCase("members") || input.equalsIgnoreCase("member")) {
            return RegionGroup.MEMBERS;
        }
        if (input.equalsIgnoreCase("owners") || input.equalsIgnoreCase("owner")) {
            return RegionGroup.OWNERS;
        }
        if (input.equalsIgnoreCase("nonowners") || input.equalsIgnoreCase("nonowner")) {
            return RegionGroup.NON_OWNERS;
        }
        if (input.equalsIgnoreCase("nonmembers") || input.equalsIgnoreCase("nonmember")) {
            return RegionGroup.NON_MEMBERS;
        }
        if (input.equalsIgnoreCase("everyone") || input.equalsIgnoreCase("anyone") || input.equalsIgnoreCase("all")) {
            return RegionGroup.ALL;
        }
        if (input.equalsIgnoreCase("none") || input.equalsIgnoreCase("noone") || input.equalsIgnoreCase("deny")) {
            return RegionGroup.NONE;
        }
        return null;
    }

    public static boolean isMember(ProtectedRegion region, RegionGroup group, LocalPlayer player) {
        if (group == null || group == RegionGroup.ALL) {
            return true;
        }
        return group == RegionGroup.OWNERS ? region.isOwner(player) : (group == RegionGroup.MEMBERS ? region.isMember(player) : (group == RegionGroup.NON_OWNERS ? !region.isOwner(player) : group == RegionGroup.NON_MEMBERS && !region.isMember(player)));
    }

    public static boolean isMember(ApplicableRegionSet set, RegionGroup group, LocalPlayer player) {
        if (group == null || group == RegionGroup.ALL) {
            return true;
        }
        return group == RegionGroup.OWNERS ? set.isOwnerOfAll(player) : (group == RegionGroup.MEMBERS ? set.isMemberOfAll(player) : (group == RegionGroup.NON_OWNERS ? !set.isOwnerOfAll(player) : group == RegionGroup.NON_MEMBERS && !set.isMemberOfAll(player)));
    }
}

