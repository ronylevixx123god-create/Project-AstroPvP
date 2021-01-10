/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package com.compilex.CosmicDungeons.listeners;

import com.compilex.CosmicDungeons.CosmicDungeonsPlugin;
import com.compilex.CosmicDungeons.DungeonsAPI;
import com.compilex.CosmicDungeons.dungeons.libs.Dungeon;
import com.compilex.CosmicDungeons.dungeons.libs.DungeonPlayer;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class DungeonPlayerDeathListener
implements Listener {
    @EventHandler(priority=EventPriority.LOWEST)
    public void onPlayerDeath(PlayerDeathEvent e) {
        DungeonPlayer dp = DungeonsAPI.get().getPlayerDungeonData(e.getEntity());
        if (e.getEntity().hasMetadata("inDungeonParkour")) {
            e.getEntity().removeMetadata("inDungeonParkour", (Plugin)CosmicDungeonsPlugin.get_());
        }
        if (dp != null && dp.getCurrentDungeon() != null) {
            DungeonsAPI.clearDungeonItemsFromInventory(e.getEntity());
            if (!e.getEntity().getWorld().equals((Object)dp.getCurrentDungeon().getWorld())) {
                Bukkit.getLogger().info("[CosmicDungeons] Player " + e.getEntity().getName() + " was marked as in a dungeon, but not in dungeon world? (DEATH)");
                dp.setCurrentDungeon(null);
                if (e.getEntity().hasMetadata("inDungeonParkour")) {
                    e.getEntity().removeMetadata("inDungeonParkour", (Plugin)CosmicDungeonsPlugin.get_());
                }
                return;
            }
            e.setKeepInventory(true);
            e.setKeepLevel(true);
            e.getDrops().clear();
            e.setDroppedExp(0);
            Dungeon dungeon = dp.getCurrentDungeon();
            if (dungeon != null && dungeon.isBossSpawned()) {
                dungeon.setPlayersDied(true);
            }
            dp.getBukkitPlayer().setMetadata("lastDungeonDeath", (MetadataValue)new FixedMetadataValue((Plugin)CosmicDungeonsPlugin.get_(), (Object)System.currentTimeMillis()));
            if (e.getEntity().hasMetadata("inDungeonParkour")) {
                e.getEntity().removeMetadata("inDungeonParkour", (Plugin)CosmicDungeonsPlugin.get_());
            }
            List<Player> players = dungeon.getPlayers();
            players.remove((Object)e.getEntity());
            if (players.isEmpty()) {
                dungeon.setPortalPlaceCooldown(120000L);
            }
        }
    }
}

