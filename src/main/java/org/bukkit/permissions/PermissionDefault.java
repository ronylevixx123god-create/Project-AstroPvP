/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.permissions;

import java.util.HashMap;
import java.util.Map;

public enum PermissionDefault {
    TRUE("true"),
    FALSE("false"),
    OP("op", "isop", "operator", "isoperator", "admin", "isadmin"),
    NOT_OP("!op", "notop", "!operator", "notoperator", "!admin", "notadmin");

    private final String[] names;
    private static final Map<String, PermissionDefault> lookup;

    private PermissionDefault(String ... names) {
        this.names = names;
    }

    public boolean getValue(boolean op) {
        switch (this) {
            case TRUE: {
                return true;
            }
            case FALSE: {
                return false;
            }
            case OP: {
                return op;
            }
            case NOT_OP: {
                return !op;
            }
        }
        return false;
    }

    public static PermissionDefault getByName(String name) {
        return lookup.get(name.toLowerCase().replaceAll("[^a-z!]", ""));
    }

    public String toString() {
        return this.names[0];
    }

    static {
        lookup = new HashMap<String, PermissionDefault>();
        for (PermissionDefault value : PermissionDefault.values()) {
            for (String name : value.names) {
                lookup.put(name, value);
            }
        }
    }
}

