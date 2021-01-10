/*
 * Decompiled with CFR 0.150.
 */
package com.compilex.CompilexPermissions.structure;

import java.util.ArrayList;
import java.util.List;

public class PermissionGroup {
    private String name;
    private List<String> permissions = new ArrayList<String>();
    private List<String> inherits_Groups = new ArrayList<String>();
    private String prefix;
    private String suffix;
    private boolean isDefault;

    public PermissionGroup(String name, List<String> permissions, List<String> inherits_Groups, String prefix, String suffix, boolean isDefault) {
        this.name = name;
        this.permissions = permissions;
        this.inherits_Groups = inherits_Groups;
        this.prefix = prefix;
        this.suffix = suffix;
        this.isDefault = isDefault;
    }

    public String getName() {
        return this.name;
    }

    public List<String> getPermissions() {
        return this.permissions;
    }

    public List<String> getInherits_Groups() {
        return this.inherits_Groups;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public boolean isDefault() {
        return this.isDefault;
    }
}

