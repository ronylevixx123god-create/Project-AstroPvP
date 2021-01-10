/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 */
package com.compilex.CompilexPermissions.logger;

import com.compilex.CompilexPermissions.CompilexPermissions;
import org.bukkit.ChatColor;

public class PermissionsLogger {
    private static final CompilexPermissions plugin = CompilexPermissions.getInstance();

    public static void logMessage(String message) {
        plugin.getServer().getConsoleSender().sendMessage((Object)ChatColor.AQUA + "[" + plugin.getName() + "] " + (Object)ChatColor.WHITE + message);
    }
}

