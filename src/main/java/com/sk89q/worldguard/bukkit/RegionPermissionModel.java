/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.worldguard.bukkit;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.AbstractPermissionModel;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RegionPermissionModel
extends AbstractPermissionModel {
    public RegionPermissionModel(WorldGuardPlugin plugin, CommandSender sender) {
        super(plugin, sender);
    }

    public boolean mayForceLoadRegions() {
        return this.hasPluginPermission("region.load");
    }

    public boolean mayForceSaveRegions() {
        return this.hasPluginPermission("region.save");
    }

    public boolean mayMigrateRegionStore() {
        return this.hasPluginPermission("region.migratedb");
    }

    public boolean mayDefine() {
        return this.hasPluginPermission("region.define");
    }

    public boolean mayRedefine(ProtectedRegion region) {
        return this.hasPatternPermission("redefine", region);
    }

    public boolean mayClaim() {
        return this.hasPluginPermission("region.claim");
    }

    public boolean mayClaimRegionsUnbounded() {
        return this.hasPluginPermission("region.unlimited");
    }

    public boolean mayDelete(ProtectedRegion region) {
        return this.hasPatternPermission("remove", region);
    }

    public boolean maySetPriority(ProtectedRegion region) {
        return this.hasPatternPermission("setpriority", region);
    }

    public boolean maySetParent(ProtectedRegion child, ProtectedRegion parent) {
        return this.hasPatternPermission("setparent", child) && (parent == null || this.hasPatternPermission("setparent", parent));
    }

    public boolean maySelect(ProtectedRegion region) {
        return this.hasPatternPermission("select", region);
    }

    public boolean mayLookup(ProtectedRegion region) {
        return this.hasPatternPermission("info", region);
    }

    public boolean mayTeleportTo(ProtectedRegion region) {
        return this.hasPatternPermission("teleport", region);
    }

    public boolean mayList() {
        return this.hasPluginPermission("region.list");
    }

    public boolean mayList(String targetPlayer) {
        if (targetPlayer == null) {
            return this.mayList();
        }
        if (targetPlayer.equalsIgnoreCase(this.getSender().getName())) {
            return this.hasPluginPermission("region.list.own");
        }
        return this.mayList();
    }

    public boolean maySetFlag(ProtectedRegion region) {
        return this.hasPatternPermission("flag.regions", region);
    }

    public boolean maySetFlag(ProtectedRegion region, Flag<?> flag) {
        return this.hasPatternPermission("flag.flags." + flag.getName().toLowerCase(), region);
    }

    private boolean hasPatternPermission(String perm, ProtectedRegion region) {
        if (!(this.getSender() instanceof Player)) {
            return true;
        }
        LocalPlayer localPlayer = this.getPlugin().wrapPlayer((Player)this.getSender());
        String idLower = region.getId().toLowerCase();
        if (region.isOwner(localPlayer)) {
            return this.hasPluginPermission("region." + perm + ".own." + idLower) || this.hasPluginPermission("region." + perm + ".member." + idLower);
        }
        if (region.isMember(localPlayer)) {
            return this.hasPluginPermission("region." + perm + ".member." + idLower);
        }
        String effectivePerm = "region." + perm + "." + idLower;
        return this.hasPluginPermission(effectivePerm);
    }
}

