/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 */
package org.arkhamnetwork.permissions;

import com.compilex.CompilexPermissions.CompilexPermissions;
import com.compilex.CompilexPermissions.manager.DatabaseManager;
import com.compilex.CompilexPermissions.manager.PermissionManager;
import org.bukkit.Bukkit;

@Deprecated
public class ArkhamPermissions {
    private static ArkhamPermissions me;

    public ArkhamPermissions() {
        me = this;
    }

    @Deprecated
    public static ArkhamPermissions getInstance() {
        Bukkit.getLogger().info("[CompilexPermissions] Deprecated ArkhamPermissions getInstance() call from " + new Exception().getStackTrace()[1].getClassName());
        return me;
    }

    @Deprecated
    public DatabaseManager getDatabaseManager() {
        return CompilexPermissions.get().getDatabaseManager();
    }

    @Deprecated
    public PermissionManager getPermissionManager() {
        return CompilexPermissions.get().getPermissionManager();
    }
}

