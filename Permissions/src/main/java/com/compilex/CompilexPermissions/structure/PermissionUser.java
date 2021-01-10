/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.permissions.PermissionAttachment
 */
package com.compilex.CompilexPermissions.structure;

import com.compilex.CompilexPermissions.CompilexPermissions;
import com.compilex.CompilexPermissions.structure.PermissionGroup;
import com.compilex.CompilexPermissions.utils.RankItems;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import org.bukkit.permissions.PermissionAttachment;

public final class PermissionUser {
    private UUID playerUUID;
    private PermissionAttachment currentAttachment;
    private final String permissionName;
    private final List<PermissionGroup> groups = new ArrayList<PermissionGroup>();
    private final List<String> permissionNodes = new ArrayList<String>();
    private final List<String> groupNames = new ArrayList<String>();
    private CompilexPermissions plugin = CompilexPermissions.getInstance();

    public PermissionUser(UUID playerUUID, PermissionAttachment currentAttachment, List<PermissionGroup> groups, List<String> permissionNodes) {
        this.playerUUID = playerUUID;
        this.currentAttachment = currentAttachment;
        this.permissionName = "arkhampermissions.user." + playerUUID;
        HashSet<PermissionGroup> defaultGroups = new HashSet<PermissionGroup>();
        for (PermissionGroup group : groups) {
            if (group == null || group.getName() == null || group.getName().toLowerCase().equals("null")) continue;
            if (group.isDefault()) {
                defaultGroups.add(group);
                continue;
            }
            this.groups.add(group);
            this.groupNames.add(group.getName().toLowerCase());
        }
        for (PermissionGroup group : defaultGroups) {
            if (group == null || group.getName() == null || group.getName().toLowerCase().equals("null")) continue;
            this.groups.add(group);
            this.groupNames.add(group.getName().toLowerCase());
        }
        this.permissionNodes.addAll(permissionNodes);
    }

    private boolean validPrefixSuffix(PermissionGroup group) {
        return !group.isDefault() || this.groups.size() <= 1;
    }

    public String getPrefix() {
        StringBuilder prefix = new StringBuilder();
        prefix.append("");
        for (PermissionGroup group : this.groups) {
            if (group.getPrefix() == null || group.getPrefix().equals("") || group.getPrefix().toLowerCase().equals("null") || !this.validPrefixSuffix(group)) continue;
            prefix.append(group.getPrefix()).append(" ");
        }
        return prefix.toString();
    }

    public String getSuffix() {
        StringBuilder suffix = new StringBuilder();
        suffix.append("");
        for (PermissionGroup group : this.groups) {
            if (group.getSuffix() == null || group.getSuffix().equals("") || group.getSuffix().toLowerCase().equals("null") || !this.validPrefixSuffix(group)) continue;
            suffix.append(" ");
            suffix.append(group.getSuffix());
        }
        return suffix.toString();
    }

    public boolean isInGroup(String groupName) {
        return this.groupNames.contains(groupName.toLowerCase());
    }

    public String getGroup() {
        if (this.groupNames.isEmpty()) {
            return null;
        }
        for (String group : this.groupNames) {
            if (!RankItems.isAboveNormalRank(group)) continue;
            return group;
        }
        return this.groupNames.get(0);
    }

    public List<PermissionGroup> getGroups() {
        if (this.groups.isEmpty()) {
            return new ArrayList<PermissionGroup>(this.groups);
        }
        return new ArrayList<PermissionGroup>(this.groups);
    }

    public List<PermissionGroup> getGroupsEditable() {
        if (this.groups.isEmpty()) {
            return this.groups;
        }
        return this.groups;
    }

    public UUID getPlayerUUID() {
        return this.playerUUID;
    }

    public PermissionAttachment getCurrentAttachment() {
        return this.currentAttachment;
    }

    public void setCurrentAttachment(PermissionAttachment currentAttachment) {
        this.currentAttachment = currentAttachment;
    }

    public String getPermissionName() {
        return this.permissionName;
    }

    public List<String> getPermissionNodes() {
        return this.permissionNodes;
    }

    public List<String> getGroupNames() {
        return this.groupNames;
    }
}

