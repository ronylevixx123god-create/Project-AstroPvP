/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  fr.neatmonster.nocheatplus.checks.CheckType
 *  fr.neatmonster.nocheatplus.checks.ViolationData
 *  fr.neatmonster.nocheatplus.checks.access.IViolationInfo
 *  fr.neatmonster.nocheatplus.hooks.NCPHook
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.metadata.MetadataValue
 */
package com.compilex.CosmicDungeons.ncphooks;

import fr.neatmonster.nocheatplus.checks.CheckType;
import fr.neatmonster.nocheatplus.checks.ViolationData;
import fr.neatmonster.nocheatplus.checks.access.IViolationInfo;
import fr.neatmonster.nocheatplus.hooks.NCPHook;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;

public class DestroyedOutpostNCPHook
implements NCPHook {
    public String getHookName() {
        return "dungeon-outpost-hook";
    }

    public String getHookVersion() {
        return "1.0";
    }

    public boolean onCheckFailure(CheckType checkType, Player player, IViolationInfo iViolationInfo) {
        if (checkType == CheckType.MOVING_SURVIVALFLY && player.getWorld().getName().equals("dungeon_destroyed_outpost")) {
            Long timeSinceDamage = null;
            if (iViolationInfo.getAddedVl() <= 150.0 && iViolationInfo.getTotalVl() < 1000.0 && (timeSinceDamage = this.getTimeSinceRecentlyDamaged(player)) != null && timeSinceDamage > System.currentTimeMillis()) {
                if (iViolationInfo instanceof ViolationData) {
                    ((ViolationData)iViolationInfo).preventCancel();
                    Bukkit.getLogger().info("[CosmicDungeons] Preventing cancel of flag: +" + iViolationInfo.getAddedVl() + " vL damaged: " + (timeSinceDamage - System.currentTimeMillis()) + "ms ago");
                }
            } else {
                Bukkit.getLogger().info("[CosmicDungeons] SET BACK FOR " + player.getName() + " vl=" + iViolationInfo.getAddedVl() + " Total: " + iViolationInfo.getTotalVl() + " Previously Damaged: " + (timeSinceDamage == null ? "Null" : Long.valueOf(System.currentTimeMillis() - timeSinceDamage)) + "ms ago");
            }
        }
        return false;
    }

    private Long getTimeSinceRecentlyDamaged(Player player) {
        List values = player.getMetadata("lastWitherDamage");
        if (values == null || values.isEmpty()) {
            return null;
        }
        return ((MetadataValue)values.get(0)).asLong();
    }
}

