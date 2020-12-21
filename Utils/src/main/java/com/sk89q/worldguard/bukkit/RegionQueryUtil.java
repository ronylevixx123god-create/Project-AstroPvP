/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.worldedit.Vector
 */
package com.sk89q.worldguard.bukkit;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.bukkit.FlagStateManager;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class RegionQueryUtil {
    public static boolean isInvincible(WorldGuardPlugin plugin, Player player) {
        return RegionQueryUtil.isInvincible(plugin, player, null);
    }

    public static boolean isInvincible(WorldGuardPlugin plugin, Player player, ApplicableRegionSet set) {
        Location loc = player.getLocation();
        World world = player.getWorld();
        FlagStateManager.PlayerFlagState state = plugin.getFlagStateManager().getState(player);
        if (state.lastInvincibleWorld == null || !state.lastInvincibleWorld.equals(world) || state.lastInvincibleX != loc.getBlockX() || state.lastInvincibleY != loc.getBlockY() || state.lastInvincibleZ != loc.getBlockZ()) {
            state.lastInvincibleX = loc.getBlockX();
            state.lastInvincibleY = loc.getBlockY();
            state.lastInvincibleZ = loc.getBlockZ();
            state.lastInvincibleWorld = world;
            if (set == null) {
                Vector vec = new Vector(state.lastInvincibleX, state.lastInvincibleY, state.lastInvincibleZ);
                RegionManager mgr = plugin.getGlobalRegionManager().get(world);
                set = mgr.getApplicableRegions(vec);
            }
            state.wasInvincible = set.allows(DefaultFlag.INVINCIBILITY, plugin.wrapPlayer(player));
        }
        return state.wasInvincible;
    }

    public static Boolean isAllowedInvinciblity(WorldGuardPlugin plugin, Player player) {
        World world = player.getWorld();
        FlagStateManager.PlayerFlagState state = plugin.getFlagStateManager().getState(player);
        Vector vec = new Vector(state.lastInvincibleX, state.lastInvincibleY, state.lastInvincibleZ);
        StateFlag.State regionState = (StateFlag.State)((Object)plugin.getGlobalRegionManager().get(world).getApplicableRegions(vec).getFlag(DefaultFlag.INVINCIBILITY, plugin.wrapPlayer(player)));
        if (regionState == StateFlag.State.ALLOW) {
            return Boolean.TRUE;
        }
        if (regionState == StateFlag.State.DENY) {
            return Boolean.FALSE;
        }
        return null;
    }
}

