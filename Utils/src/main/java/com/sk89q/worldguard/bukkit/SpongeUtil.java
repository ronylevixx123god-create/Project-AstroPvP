/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.worldguard.bukkit;

import com.sk89q.worldguard.bukkit.BukkitUtil;
import com.sk89q.worldguard.bukkit.ConfigurationManager;
import com.sk89q.worldguard.bukkit.WorldConfiguration;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.World;

public class SpongeUtil {
    public static void clearSpongeWater(WorldGuardPlugin plugin, World world, int ox, int oy, int oz) {
        ConfigurationManager cfg = plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(world);
        for (int cx = -wcfg.spongeRadius; cx <= wcfg.spongeRadius; ++cx) {
            for (int cy = -wcfg.spongeRadius; cy <= wcfg.spongeRadius; ++cy) {
                for (int cz = -wcfg.spongeRadius; cz <= wcfg.spongeRadius; ++cz) {
                    if (!BukkitUtil.isBlockWater(world, ox + cx, oy + cy, oz + cz)) continue;
                    world.getBlockAt(ox + cx, oy + cy, oz + cz).setTypeId(0);
                }
            }
        }
    }

    public static void addSpongeWater(WorldGuardPlugin plugin, World world, int ox, int oy, int oz) {
        int cz;
        int cy;
        ConfigurationManager cfg = plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(world);
        int cx = ox - wcfg.spongeRadius - 1;
        for (cy = oy - wcfg.spongeRadius - 1; cy <= oy + wcfg.spongeRadius + 1; ++cy) {
            for (cz = oz - wcfg.spongeRadius - 1; cz <= oz + wcfg.spongeRadius + 1; ++cz) {
                if (!BukkitUtil.isBlockWater(world, cx, cy, cz)) continue;
                BukkitUtil.setBlockToWater(world, cx + 1, cy, cz);
            }
        }
        cx = ox + wcfg.spongeRadius + 1;
        for (cy = oy - wcfg.spongeRadius - 1; cy <= oy + wcfg.spongeRadius + 1; ++cy) {
            for (cz = oz - wcfg.spongeRadius - 1; cz <= oz + wcfg.spongeRadius + 1; ++cz) {
                if (!BukkitUtil.isBlockWater(world, cx, cy, cz)) continue;
                BukkitUtil.setBlockToWater(world, cx - 1, cy, cz);
            }
        }
        cy = oy - wcfg.spongeRadius - 1;
        for (cx = ox - wcfg.spongeRadius - 1; cx <= ox + wcfg.spongeRadius + 1; ++cx) {
            for (cz = oz - wcfg.spongeRadius - 1; cz <= oz + wcfg.spongeRadius + 1; ++cz) {
                if (!BukkitUtil.isBlockWater(world, cx, cy, cz)) continue;
                BukkitUtil.setBlockToWater(world, cx, cy + 1, cz);
            }
        }
        cy = oy + wcfg.spongeRadius + 1;
        for (cx = ox - wcfg.spongeRadius - 1; cx <= ox + wcfg.spongeRadius + 1; ++cx) {
            for (cz = oz - wcfg.spongeRadius - 1; cz <= oz + wcfg.spongeRadius + 1; ++cz) {
                if (!BukkitUtil.isBlockWater(world, cx, cy, cz)) continue;
                BukkitUtil.setBlockToWater(world, cx, cy - 1, cz);
            }
        }
        cz = oz - wcfg.spongeRadius - 1;
        for (cx = ox - wcfg.spongeRadius - 1; cx <= ox + wcfg.spongeRadius + 1; ++cx) {
            for (cy = oy - wcfg.spongeRadius - 1; cy <= oy + wcfg.spongeRadius + 1; ++cy) {
                if (!BukkitUtil.isBlockWater(world, cx, cy, cz)) continue;
                BukkitUtil.setBlockToWater(world, cx, cy, cz + 1);
            }
        }
        cz = oz + wcfg.spongeRadius + 1;
        for (cx = ox - wcfg.spongeRadius - 1; cx <= ox + wcfg.spongeRadius + 1; ++cx) {
            for (cy = oy - wcfg.spongeRadius - 1; cy <= oy + wcfg.spongeRadius + 1; ++cy) {
                if (!BukkitUtil.isBlockWater(world, cx, cy, cz)) continue;
                BukkitUtil.setBlockToWater(world, cx, cy, cz - 1);
            }
        }
    }
}

