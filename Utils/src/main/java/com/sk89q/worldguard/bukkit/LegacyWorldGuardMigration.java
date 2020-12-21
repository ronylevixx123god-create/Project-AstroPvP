/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.worldguard.bukkit;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.databases.CSVDatabase;
import com.sk89q.worldguard.protection.databases.ProtectionDatabaseException;
import com.sk89q.worldguard.protection.managers.RegionManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.bukkit.World;

public class LegacyWorldGuardMigration {
    public static void migrateBlacklist(WorldGuardPlugin plugin) {
        World mainWorld = plugin.getServer().getWorlds().get(0);
        String mainWorldName = mainWorld.getName();
        String newPath = "worlds/" + mainWorldName + "/blacklist.txt";
        File oldFile = new File(plugin.getDataFolder(), "blacklist.txt");
        File newFile = new File(plugin.getDataFolder(), newPath);
        if (!newFile.exists() && oldFile.exists()) {
            plugin.getLogger().warning("WorldGuard will now update your blacklist from an older version of WorldGuard.");
            newFile.getParentFile().mkdirs();
            if (LegacyWorldGuardMigration.copyFile(oldFile, newFile)) {
                oldFile.renameTo(new File(plugin.getDataFolder(), "blacklist.txt.old"));
            } else {
                plugin.getLogger().warning("blacklist.txt has been converted for the main world at " + newPath + "");
                plugin.getLogger().warning("Your other worlds currently have no blacklist defined!");
            }
        }
    }

    public static void migrateRegions(WorldGuardPlugin plugin) {
        try {
            File oldDatabase = new File(plugin.getDataFolder(), "regions.txt");
            if (!oldDatabase.exists()) {
                return;
            }
            plugin.getLogger().info("The regions database has changed in 5.x. Your old regions database will be converted to the new format and set as your primary world's database.");
            World w = plugin.getServer().getWorlds().get(0);
            RegionManager mgr = plugin.getGlobalRegionManager().get(w);
            CSVDatabase db = new CSVDatabase(oldDatabase, plugin.getLogger());
            db.load();
            mgr.setRegions(db.getRegions());
            mgr.save();
            oldDatabase.renameTo(new File(plugin.getDataFolder(), "regions.txt.old"));
            plugin.getLogger().info("Regions database converted!");
        }
        catch (ProtectionDatabaseException e) {
            plugin.getLogger().warning("Failed to load regions: " + e.getMessage());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static boolean copyFile(File from, File to) {
        boolean bl;
        OutputStream out;
        FileInputStream in;
        block26: {
            int len;
            in = null;
            out = null;
            in = new FileInputStream(from);
            out = new FileOutputStream(to);
            byte[] buf = new byte[1024];
            while ((len = ((InputStream)in).read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            ((InputStream)in).close();
            out.close();
            bl = true;
            if (in == null) break block26;
            try {
                ((InputStream)in).close();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        if (out == null) return bl;
        try {
            out.close();
            return bl;
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return bl;
        catch (FileNotFoundException fileNotFoundException) {
            if (in != null) {
                try {
                    ((InputStream)in).close();
                }
                catch (IOException iOException) {
                    // empty catch block
                }
            }
            if (out == null) return false;
            try {
                out.close();
                return false;
            }
            catch (IOException iOException) {
                return false;
            }
            catch (IOException iOException) {
                if (in != null) {
                    try {
                        ((InputStream)in).close();
                    }
                    catch (IOException iOException2) {
                        // empty catch block
                    }
                }
                if (out == null) return false;
                try {
                    out.close();
                    return false;
                }
                catch (IOException iOException3) {
                    return false;
                }
                catch (Throwable throwable) {
                    if (in != null) {
                        try {
                            ((InputStream)in).close();
                        }
                        catch (IOException iOException4) {
                            // empty catch block
                        }
                    }
                    if (out == null) throw throwable;
                    try {
                        out.close();
                        throw throwable;
                    }
                    catch (IOException iOException5) {
                        // empty catch block
                    }
                    throw throwable;
                }
            }
        }
    }
}

