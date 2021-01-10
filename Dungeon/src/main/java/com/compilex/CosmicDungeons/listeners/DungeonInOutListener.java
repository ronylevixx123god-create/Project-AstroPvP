package com.compilex.CosmicDungeons.listeners;

import com.compilex.CosmicDungeons.CosmicDungeonsPlugin;
import com.compilex.CosmicDungeons.DungeonsAPI;
import com.compilex.CosmicDungeons.dungeons.libs.Dungeon;
import com.compilex.CosmicDungeons.dungeons.libs.DungeonPlayer;
import com.compilex.CosmicDungeons.dungeons.libs.DungeonState;
import com.compilex.CosmicDungeons.dungeons.libs.DungeonType;
import com.compilex.CosmicDungeons.items.DungeonKeyItem;
import com.compilex.CosmicDungeons.utils.Logger;
import java.util.concurrent.TimeUnit;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.chunkgc.ChunkGCTask;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class DungeonInOutListener
implements Listener {
    @EventHandler(priority=EventPriority.NORMAL)
    public void onPlayerQuit(PlayerQuitEvent e) {
        DungeonPlayer dp = DungeonsAPI.get().getPlayerDungeonData(e.getPlayer());
        if (dp.getCurrentDungeon() != null) {
            if (!e.getPlayer().getWorld().equals((Object)dp.getCurrentDungeon().getWorld())) {
                Logger.debug("Player " + (Object)e.getPlayer() + " is in world " + e.getPlayer().getWorld().getName() + ", but has currentDungeon data set to: " + dp.getCurrentDungeon());
            }
            if (Arkkit.get().isRebooting() && dp.getCurrentDungeon().getDungeonStatus() != DungeonState.COMPLETE && e.getPlayer().hasMetadata("dungeonKeyUsed_" + dp.getCurrentDungeon().getDungeonType().getInternalName())) {
                try {
                    if (e.getPlayer().getInventory().firstEmpty() != -1) {
                        Bukkit.getLogger().info("[CosmicDungeons] Returning a dungeon key item to " + e.getPlayer().getName() + " for dungeon: " + dp.getCurrentDungeon().getDungeonType());
                        DungeonKeyItem item = new DungeonKeyItem(dp.getCurrentDungeon().getDungeonType(), System.currentTimeMillis() + TimeUnit.DAYS.toMillis(2L));
                        e.getPlayer().getInventory().addItem(item.build());
                    }
                }
                catch (Exception err) {
                    err.printStackTrace();
                }
            }
            dp.leaveDungeon();
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        for (Dungeon d : DungeonsAPI.get().getDungeons()) {
            if (d == null || d.getWorld() == null || !p.getWorld().getName().equals(d.getWorld().getName())) continue;
            Logger.info("Player " + p.getName() + " was in world " + (Object)p.getWorld() + " on login, forcing to /spawn.");
            p.teleport(Bukkit.getWorld((String)"world").getSpawnLocation(), PlayerTeleportEvent.TeleportCause.UNKNOWN);
            break;
        }
    }

    @EventHandler(priority=EventPriority.HIGH)
    public void onPlayerChangeWorld(PlayerChangedWorldEvent e) {
        if (e.getFrom().getName().startsWith("dungeon_")) {
            DungeonPlayer dp;
            Dungeon d;
            if (e.getPlayer().hasMetadata("inDungeonParkour")) {
                e.getPlayer().removeMetadata("inDungeonParkour", (Plugin)CosmicDungeonsPlugin.get_());
            }
            if (e.getPlayer().hasMetadata("heroicDungeon")) {
                e.getPlayer().removeMetadata("heroicDungeon", (Plugin)CosmicDungeonsPlugin.get_());
            }
            if ((d = (dp = DungeonsAPI.get().getPlayerDungeonData(e.getPlayer())).getCurrentDungeon()) != null) {
                if (dp.getCurrentDungeon() != null && dp.getCurrentDungeon().getDungeonType() == DungeonType.DESTROYED_OUTPOST) {
                    DungeonsAPI.clearDungeonItemsFromInventory(e.getPlayer());
                }
                dp.setCurrentDungeon(null);
                if (d.getPlayers().size() == 0 && d.getDungeonStatus() != DungeonState.EMPTY && d.getDungeonStatus() != DungeonState.LOADING) {
                    Logger.debug("Resetting dungeon " + d + " due to no players left and state=IN_PROGRESS...");
                    d.setPortalPlaceCooldown(System.currentTimeMillis() + 120000L);
                    DungeonState status = d.getDungeonStatus();
                    Bukkit.getScheduler().scheduleSyncDelayedTask(CosmicDungeonsPlugin.get_(), () -> {
                        if (d.getPlayers().isEmpty() && status == d.getDungeonStatus()) {
                            d.reset();
                        }
                    }, 100L);
                }
            }
        }
    }
}

