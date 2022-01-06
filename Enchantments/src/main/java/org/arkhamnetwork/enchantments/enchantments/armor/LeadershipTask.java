/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.WorldUtil
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.enchantments.enchantments.armor;

import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

final class LeadershipTask
extends BukkitRunnable {
    LeadershipTask() {
    }

    public void run() {
        for (Map.Entry<String, Integer> LeadershipData : Leadership.Leaderships.entrySet()) {
            String playerName = LeadershipData.getKey();
            int level = LeadershipData.getValue();
            Player p = Bukkit.getPlayer((String)playerName);
            if (p == null) {
                Leadership.Leaderships.remove(playerName);
                continue;
            }
            Location l = p.getEyeLocation().add(0.0, 0.5, 0.0);
            int x = 0;
            for (Entity ent : p.getNearbyEntities((double)(level * 2 + 3), (double)(level * 2 + 3), (double)(level * 2 + 3))) {
                if (!(ent instanceof Player) || !this.isAlly(p, (Player)ent)) continue;
                ++x;
            }
            p.setMetadata("leadershipLevel", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)x));
            if (x <= 0 || l.getWorld().getName().equals("world_duels") || l.getWorld().getName().equals("world_duels2") || l.getWorld().getName().equals("cosmic-pvparenas")) continue;
        }
    }

    private boolean isAlly(Player p1, Player p2) {
        return GeneralUtil.isAlly(p1, p2);
    }
}

